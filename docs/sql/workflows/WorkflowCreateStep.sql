create procedure ig_db.WorkflowCreateStep(@workflowDefinitionId bigint)
as
begin
	declare @lastStepNumber integer;
	declare @newStepNumber integer;
	declare @newStepName varchar(100);

	select @newStepNumber = 10;

	select 
			@lastStepNumber = max(wds.workflowDefinitionStepNumber) 
		from 
			 ig_db.WorkflowDefinitionStep wds
		where 
			wds.WorkflowDefinition_id = @workflowDefinitionId;
	
	if (@lastStepNumber is not null) 
	begin
		select @newStepNumber = @lastStepNumber + 10;
	end;

	select @newStepName = 'Step ' + cast(@newStepNumber as varchar(50));

	-- finally create the step
	insert into
		 ig_db.WorkflowDefinitionStep
		(
			WorkflowDefinition_id, WorkflowDefinitionStepNumber
			-- WorkflowDefinitionStep_name

		) values (
			@workflowDefinitionId, @newStepNumber
			-- @newStepName
		);
end;

-- select * from ig_db.WorkflowDefinitionStep