create or alter procedure  ig_db.WorkflowValidation(@workflowDefinitionId bigint, @errors varchar(max) out) 
as
begin
	declare @newLine varchar(10);
	declare @count int;

	declare @WorkflowDefinitionStepName varchar(100);
	declare @TriggerWorkflowDefinitionCode varchar(100);

	select @newLine = '\n';
	select @errors = '';

	-- does the workflow def exist
	select @count = count(1) from  ig_db.WorkflowDefinition where WorkflowDefinition_id = @workflowDefinitionId;
	if (@count != 1) 
	begin
		select @errors = @errors + 'The workflow definition does not exist' + @newLine;
	end;
	
	-- does the wfdef have steps attached
	select @count = count(1) from  ig_db.WorkflowDefinitionStep where WorkflowDefinition_id = @workflowDefinitionId;
	if (@count !> 1)
	begin
		select @errors = @errors + 'The workflow definition does not have any steps' + @newLine;
	end;

	declare @numConditionSteps int;
	declare @numNullTrueSteps int;
	declare @numNullFalseSteps int;
	declare @numMissingTrueSteps int;
	declare @numMissingFalseSteps int;

	select
		@numConditionSteps = count(1),
		@numNullTrueSteps = (select count(1) from  ig_db.WorkflowDefinitionStep b where 
		                            b.WorkflowDefinition_id = @workflowDefinitionId
									and (b.TestField is not null and b.TestField != '')
									and b.IfTrueStepNumber is null),
		@numNullFalseSteps = (select count(1) from  ig_db.WorkflowDefinitionStep b where 
		                            b.WorkflowDefinition_id = @workflowDefinitionId
									and (b.TestField is not null and b.TestField != '')
									and b.IfFalseStepNumber is null),
		@numMissingTrueSteps = (select count(1) from  ig_db.WorkflowDefinitionStep b where 
		                            b.WorkflowDefinition_id = @workflowDefinitionId
									and (b.TestField is not null and b.TestField != '')
									and b.IfTrueStepNumber not in (select WorkflowDefinitionStepNumber from  ig_db.WorkflowDefinitionStep c 
									                                   where c.WorkflowDefinition_id = @workflowDefinitionId
																	   --and c.WorkflowDefinitionStepNumber = b.WorkflowDefinitionStepNumber
									                               ) ),
		@numMissingFalseSteps = (select count(1) from  ig_db.WorkflowDefinitionStep b where 
		                            b.WorkflowDefinition_id = @workflowDefinitionId
									and (b.TestField is not null and b.TestField != '')
									and b.IfFalseStepNumber not in (select WorkflowDefinitionStepNumber from  ig_db.WorkflowDefinitionStep c 
									                                   where c.WorkflowDefinition_id = @workflowDefinitionId
																	   --and c.WorkflowDefinitionStepNumber = b.WorkflowDefinitionStepNumber
									                               ) ) 
	from  ig_db.WorkflowDefinitionStep wds
	where wds.WorkflowDefinition_id = @workflowDefinitionId
	    and (wds.TestField is not null and wds.TestField != '')
		
	-- test steps: do all conditions have true and false steps that exist
	if (@numNullTrueSteps > 0) 
	begin
		select @errors = @errors + 
		                 'The workflow definition contains one or more conditional steps with empty True steps' + @newLine;
	end;

	if (@numNullFalseSteps > 0) 
	begin
		select @errors = @errors + 
		                 'The workflow definition contains one or more conditional steps with empty False steps' + @newLine;
	end;

	if (@numMissingTrueSteps > 0) 
	begin
		select @errors = @errors + 
		                 'The workflow definition contains conditional steps where the True test points to non existant steps' + @newLine;
	end;

	if (@numMissingFalseSteps > 0) 
	begin
		select @errors = @errors + 
		                 'The workflow definition contains conditional steps where the False test points to non existant steps' + @newLine;
	end;

	-- test steps: are there missing goto steps
	select 
			@count = count(1)
		from 
			 ig_db.WorkflowDefinitionStep 
		where 
			WorkflowDefinition_id = 10
			and (
				((Mailbox is not null) and (Mailbox != ''))
				or ((Sql_text is not null) and (Sql_text != ''))
				)
			and
				(
					(GotoStepNumber is null)
					or
					(GotoStepNumber not in (select WorkflowDefinitionStepNumber 
											from
											 ig_db.WorkflowDefinitionStep
											where WorkflowDefinition_id = 10
											))
				);

	if (@count != 0) 
	begin
		select @errors = @errors + 
		                 'The workflow definition contains steps where the goto step number is required but is not set or does not exist' + @newLine;
	end;

	-- test steps: are there orphan steps
	declare @startStep bigint;
	select @startStep = GotoStepNumber from  ig_db.WorkflowDefinitionStep
					where WorkflowDefinition_id = @workflowDefinitionId and WorkflowDefinitionStepNumber = 0;

	if (@startStep is null) 
	begin
		select @startStep = min(WorkflowDefinitionStepNumber) from  ig_db.WorkflowDefinitionStep 
					where WorkflowDefinition_id = @workflowDefinitionId;
	end;

	select @count = count(1) from  ig_db.WorkflowDefinitionStep
		where
			WorkflowDefinition_id = @workflowDefinitionId
			and WorkflowDefinitionStepNumber != 0
			and WorkflowDefinitionStepNumber != @startStep
			and WorkflowDefinitionStepNumber not in (
				select distinct IfTrueStepNumber from  ig_db.WorkflowDefinitionStep where WorkflowDefinition_id = @workflowDefinitionId and IfTrueStepNumber is not null
				union
				select distinct IfFalseStepNumber from  ig_db.WorkflowDefinitionStep where WorkflowDefinition_id = @workflowDefinitionId and IfFalseStepNumber is not null
				union
				select distinct GotoStepNumber from  ig_db.WorkflowDefinitionStep where WorkflowDefinition_id = @workflowDefinitionId and GotoStepNumber is not null
			)

	if (@count != 0) 
	begin
		select @errors = @errors + 
		                 'The workflow definition contains orphan steps' + @newLine;
	end;

	-- test steps: is there a complete step
	select @count = count(1) from  ig_db.WorkflowDefinitionStep where WorkflowDefinition_id = @workflowDefinitionId
	                                                                 and MarkCompleted_flag = 'Y';
	if (@count = 0)
	begin
		select @errors = @errors + 'The workflow definition does not have a complete step' + @newLine;
	end;

	-- test steps: is there a failed step
	select @count = count(1) from  ig_db.WorkflowDefinitionStep where WorkflowDefinition_id = @workflowDefinitionId
	                                                                 and MarkFailed_flag = 'Y';
	if (@count = 0)
	begin
		-- if there are no conditions and the only errors is a missing failure then its ok
		declare @addFailure int;
		select @addFailure = 1;

		if ((@errors = '') and (@numConditionSteps = 0)) 
		begin
			select @addFailure = 0;
		end;

		if (@addFailure = 1) 
		begin
			--select @errors = @errors + 'The workflow definition does not have a failure step' + @newLine;
			print 'Disabled check for endpoints because there could be workflow starts or alternatives without endings';
		end;
	end;

	-- Ensure that all trigger workflows exist (we should really check that they are active and valid too?!)
	declare WorkflowDefinitionStep_cursor cursor for
		select 
				wds.WorkflowDefinitionStep_name, 
				wds.TriggerWorkflowDefinitionCode
			from 
				 ig_db.WorkflowDefinitionStep wds
			where 
				wds.TriggerWorkflowDefinitionCode is not null
				and wds.TriggerWorkflowDefinitionCode <> ''
				and wds.TriggerWorkflowDefinitionCode not in (select WorkflowDefinition_code from  ig_db.WorkflowDefinition where 
													 WorkflowDefinition_code = wds.TriggerWorkflowDefinitionCode);
	
	open WorkflowDefinitionStep_cursor
	fetch next from WorkflowDefinitionStep_cursor into
		@WorkflowDefinitionStepName,
		@TriggerWorkflowDefinitionCode;

	while @@FETCH_STATUS = 0
	begin
		select @errors = @errors + 
		                 'The step ' + @WorkflowDefinitionStepName + 
						 ' references a missing workflow definition: ' + @TriggerWorkflowDefinitionCode + @newLine;

		fetch next from WorkflowDefinitionStep_cursor into
			@WorkflowDefinitionStepName,
			@TriggerWorkflowDefinitionCode;
	end;

	close WorkflowDefinitionStep_cursor;
	deallocate WorkflowDefinitionStep_cursor;


	-- TODO: if this is condition check for test field and test value
	-- TODO: if this is an email check email subject and email body
	-- TODO: if this is a sql check that there is a sql statement

end;

/*
declare @workflowDefinitionId bigint;
declare @errors varchar(max);
select @workflowDefinitionId = 7;
exec  ig_db.WorkflowValidation @workflowDefinitionId, @errors out;
if (@errors = '') 
begin
	select @errors = 'Workflow definition is valid';
end;
print @errors;
*/

