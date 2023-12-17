create or alter procedure  ig_db.WorkflowPoller
as
begin
	declare @s varchar(max);
	declare @slaDat datetime;

	declare @WorkflowProcessId bigint;
	declare @WorkflowDefinitionId bigint;
	declare @DataProviderId bigint;
	declare @slaMinutes integer;
	declare @RecordObjectName varchar(1000);
	declare @RecordWhereClause varchar(5000);
	declare @LastStep integer;
	declare @NextStep integer;
	declare @StartDateTime datetime;

	declare @WorkflowDefinitionStepNumber integer;
	declare @WorkflowDefinitionStepName varchar(200);
	declare @StepMailbox varchar(500);
	declare @TestField varchar(100);
	declare @TestValue varchar(100);
	declare @IfTrueStepNumber integer;
	declare @IfFalseStepNumber integer;
	declare @MarkFailedFlag varchar(1);
	declare @MarkCompletedFlag varchar(1);
	declare @PauseWorkflowFlag varchar(1);
	declare @SqlText varchar(max);
	declare @MailSubject varchar(1000);
	declare @MailBodyText varchar(max);
	declare @GotoStepNumber integer;
	declare @TriggerWorkflowDefinitionCode varchar(200);
	
	declare @TempSql nvarchar(max);
	declare @TempParamDef nvarchar(max);
	declare @TestResult varchar(1000);
	declare @SourceCount integer;

	-- Check if our processing has been paused by the ApplicationControlVariable value
	declare @pollerPaused varchar(100);
	select @pollerPaused = ControlVariableValue_text 
		from 
			idi.ApplicationControlVariable 
		where 
			ControlVariableName = 'UI.workflow.pollingPaused';

	if (@pollerPaused = 'Y') 
	begin
		return;
	end;

	-- declare a cursor for workflow processes that we need to execute in this loop
	declare WorkflowProcess_cursor cursor for
		select 
			wp.WorkflowProcess_id,
			wp.WorkflowDefinition_id, 
			wp.DataProvider_id,
			wp.RecordObjectName,
			wp.RecordWhereClause,
			wp.LastStep,
			wp.NextStep,
			wp.StartDateTime
		from
			 ig_db.WorkflowProcess wp
		where 
			((wp.ExecuteDateTime <= getDate()) OR (wp.ExecuteDateTime IS NULL))
			and wp.Active_flag = 'Y';

	open WorkflowProcess_cursor;
	fetch next from WorkflowProcess_cursor into @WorkflowProcessId, @WorkflowDefinitionId, @DataProviderId,
	                                            @RecordObjectName, @RecordWhereClause, @LastStep, @NextStep, @StartDateTime;
	
	while @@FETCH_STATUS = 0
	begin
		-- Process this record
		print ('Processing WorkflowProcessId: ' + cast(@workflowProcessId as varchar(100)));

		select 
				@slaMinutes = SlaMinutes
				-- TODO: also select failover mailbox?
			from
				 ig_db.WorkflowDefinition
			where 
				WorkflowDefinition_id = @WorkflowDefinitionId;
				
		-- see if the record still exists, it may have been fixed/deleted
		set @TempSql = 'select @RetValOut = count(1) from ' + @RecordObjectName + ' where ' + @RecordWhereClause;
		set @TempParamDef = N'@RetValOut integer OUTPUT';

		exec sp_executesql @TempSql, @TempParamDef, @RetValOut=@SourceCount output;
		if (@SourceCount != 1)
		begin
			-- cancel this workflow because we cannot find the "master" record anymore...
			exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
			                                   null,
										       null,
											   'Canceling workflowProcess because the source record cannot be found', 
										       'SourceAltered';

			update 
				 ig_db.WorkflowProcess
			set
				Active_flag = 'N',
				ExecuteDateTime = CURRENT_TIMESTAMP,
				Status_code = 'SourceAltered'
			where
				WorkflowProcess_id = @WorkflowProcessId;

			-- NOTE: need to refetch so that we don't sit on the same record in the next loop iteration!!!
			fetch next from WorkflowProcess_cursor into @WorkflowProcessId, @WorkflowDefinitionId, @DataProviderId,
													    @RecordObjectName, @RecordWhereClause, @LastStep, @NextStep, @StartDateTime;
			continue;
		end;

		if (@nextStep is null) 
		begin
			-- We don't have a nextStep number... get the first number from the definition
			
			-- try looking for a step number 0 and then get its gotoStepNumber...
			select @nextStep = GotoStepNumber 
				from 
					 ig_db.WorkflowDefinitionStep
				where
					WorkflowDefinition_id = @WorkflowDefinitionId
					and WorkflowDefinitionStepNumber = 0;
				
			if (@nextStep is null) 
			begin
				select 
					top 1 @nextStep = WorkflowDefinitionStepNumber
					from 
						 ig_db.WorkflowDefinitionStep
					where 
						WorkflowDefinition_id = @WorkflowDefinitionId;
			end;
		end;

		-- get the step to execute...
		select 
				@WorkflowDefinitionStepNumber = WorkflowDefinitionStepNumber,
				@WorkflowDefinitionStepName = WorkflowDefinitionStep_name,
				@StepMailbox = MailBox,
				@TestField = TestField, 
				@TestValue = TestValue, 
				@IfTrueStepNumber = IfTrueStepNumber, 
				@IfFalseStepNumber = IfFalseStepNumber, 
				@MarkFailedFlag = MarkFailed_flag, 
				@MarkCompletedFlag = MarkCompleted_flag, 
				@PauseWorkflowFlag = PauseWorkflow_flag,
				@MailSubject = MailSubject,
				@MailBodyText = MailBody_text,
				@SqlText = Sql_text,
				@GotoStepNumber = GotoStepNumber,
				@TriggerWorkflowDefinitionCode = TriggerWorkflowDefinitionCode
			from 
				 ig_db.WorkflowDefinitionStep
			where 
				WorkflowDefinition_id = @WorkflowDefinitionId and WorkflowDefinitionStepNumber = @NextStep;

		-- ***********************************************************
		-- Send Mail step
		-- ***********************************************************
		if ((@StepMailbox is not null) and (@StepMailbox != ''))
		begin
			begin try
				exec  ig_db.WorkflowSendMail @DataProviderId, 
				                            @WorkflowProcessId,
											@WorkflowDefinitionStepNumber,
											@WorkflowDefinitionStepName,
											@StepMailBox, 
											@MailSubject, 
											@MailBodyText;
			end try
			begin catch 
				declare @errorMessage varchar(max);

				-- Source: https://docs.microsoft.com/en-us/sql/t-sql/language-elements/try-catch-transact-sql?view=sql-server-2017
				set @errorMessage = error_message();

				exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
				                                   @WorkflowDefinitionStepNumber,
											       @WorkflowDefinitionStepName,
			                                       @errorMessage,
										           'MailException';

				-- Could not send mail... Update the WorkflowProcess
				update 
					 ig_db.WorkflowProcess
				set
					Active_flag = 'N',
					ExecuteDateTime = CURRENT_TIMESTAMP,
					Status_code = 'MailException'
				where
					WorkflowProcess_id = @workflowProcessId;
			end catch;

			exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
			                                   @WorkflowDefinitionStepNumber,
										       @WorkflowDefinitionStepName,
			                                   'Executing next step', 
										       'Processing...';

			update 
					 ig_db.WorkflowProcess 
				set 
					LastStep = @NextStep,
					NextStep = @GotoStepNumber,
					ExecuteDateTime = CURRENT_TIMESTAMP
				where 
					WorkflowProcess_id = @WorkflowProcessId;
		end;
			
		-- ***********************************************************
		-- Trigger Workflow step
		-- ***********************************************************
		if ((@TriggerWorkflowDefinitionCode is not null) and (@TriggerWorkflowDefinitionCode != ''))
		begin
			-- trigger the new workflow by executing workflowInitialize
			exec  ig_db.WorkflowInitialize null, 
			                              @TriggerWorkflowDefinitionCode, 
										  @dataProviderId, 
										  @RecordObjectName, 
										  @RecordWhereClause;

			-- Log the fact that we triggered an event...
			set @s = concat('Launching workflow: ', @TriggerWorkflowDefinitionCode, ' and marking this workflow as successfully completed');
			exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
			                                   @WorkflowDefinitionStepNumber,
										       @WorkflowDefinitionStepName,
			                                   @s, 
										       'Successful';

			-- update this workflow process to set it to completed
			update 
				 ig_db.WorkflowProcess
			set
				Active_flag = 'N',
				ExecuteDateTime = CURRENT_TIMESTAMP,
				Status_code = 'Successful'
			where
				WorkflowProcess_id = @WorkflowProcessId;
		end;

		-- ***********************************************************
		-- Sql_text Statement Step
		-- ***********************************************************
		if ((@SqlText is not null) and (@SqlText <> ''))
		begin
			-- TODO: have an option on the step to say whether or not we should pass the tablename and where clause 
			-- TODO: this will mean a new field on WorkflowDefinitionStep

			-- TODO: need to mark this more robust. catch exceptions
			exec sp_executesql @SqlText;

			set @s = concat('Executed SQL: ', @SqlText);

			exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
			                                   @WorkflowDefinitionStepNumber,
										       @WorkflowDefinitionStepName,
										       @s, 
									           'Processing...';
										
			update 
					 ig_db.WorkflowProcess 
				set 
					LastStep = @NextStep,
					NextStep = @GotoStepNumber,
					ExecuteDateTime = CURRENT_TIMESTAMP
				where 
					WorkflowProcess_id = @WorkflowProcessId;
		end;

		-- ***********************************************************
		-- If Statement Step
		-- ***********************************************************
		if (@TestField IS NOT NULL) and 
			(@TestValue IS NOT NULL) and 
			(@IfTrueStepNumber IS NOT NULL) and 
			(@IfFalseStepNumber IS NOT NULL)
		begin
			set @TempSql = 'SELECT @RetValOut=' + @TestField + ' FROM ' + @RecordObjectName + ' WHERE ' + @RecordWhereClause;
			set @TempParamDef = N'@RetValOut varchar(100) OUTPUT';

			-- TODO: need to mark this more robust. catch exceptions
			print ('Executing: ' + @TempSql);
			exec sp_executesql @TempSql, @TempParamDef, @RetValOut=@TestResult output;

			if (@TestResult = @TestValue) 
			begin
				set @s = concat(@TestField, ' = ', @TestValue, ': Executing TRUE step');

				-- insert/update our step to go to the successful step
				exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
				                                   @WorkflowDefinitionStepNumber,
										           @WorkflowDefinitionStepName,
											       @s,
											       'Processing...';

				update 
					 ig_db.WorkflowProcess 
						set 
							LastStep = @NextStep,
							NextStep = @IfTrueStepNumber,
							ExecuteDateTime = CURRENT_TIMESTAMP
					where 
						WorkflowProcess_id = @WorkflowProcessId
			end else begin
				set @s = concat(@TestField, ' <> ', @TestValue, ': Executing FALSE step');

				exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
				                                   @WorkflowDefinitionStepNumber,
											       @WorkflowDefinitionStepName,
											       @s,
										           'Processing...';

				update 
					 ig_db.WorkflowProcess 
						set 
							LastStep = @NextStep,
							NextStep = @IfFalseStepNumber,
							ExecuteDateTime = CURRENT_TIMESTAMP
					where 
						WorkflowProcess_id = @WorkflowProcessId
			end;
		end;
		
		-- ***********************************************************
		-- Mark failed step
		-- ***********************************************************
		if ((@MarkFailedFlag IS NOT NULL) AND (@MarkFailedFlag = 'Y'))
		begin
			exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
			                                   @WorkflowDefinitionStepNumber,
										       @WorkflowDefinitionStepName,
										       'Marking workflow as failed',
										       'Failed';

			update 
				 ig_db.WorkflowProcess
			set
				Active_flag = 'N',
				Status_code = 'Failed'
			where
				WorkflowProcess_id = @WorkflowProcessId;
		end;

		-- ***********************************************************
		-- Mark complete step
		-- ***********************************************************
		if ((@MarkCompletedFlag IS NOT NULL) AND (@MarkCompletedFlag = 'Y'))
		begin
			exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
			                                   @WorkflowDefinitionStepNumber,
										       @WorkflowDefinitionStepName,
										       'Marking workflow as successfully completed',
										       'Successful';

			update 
				 ig_db.WorkflowProcess
			set
				Active_flag = 'N',
				ExecuteDateTime = CURRENT_TIMESTAMP,
				Status_code = 'Successful'
			where
				WorkflowProcess_id = @WorkflowProcessId;
		end;

		if ((@PauseWorkflowFlag = 'Y') and (@slaMinutes is not null)) 
		begin
			-- update the WorkflowProcess table, set execution time = current_timestamp + SLA time
			-- source https://stackoverflow.com/questions/33760529/how-to-add-minutes-to-the-time-part-of-datetime
			set @slaDat = dateadd(minute, @slaMinutes, current_timestamp);
			set @s = concat('Waiting for SLA: ', format(@slaDat, 'dd/MM/yyyy HH:mm:ss'));

			exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
			                                   @WorkflowDefinitionStepNumber,
										       @WorkflowDefinitionStepName,
										       @s,
										       'Processing...';

			update 
				 ig_db.WorkflowProcess
			set
				ExecuteDateTime = @slaDat
			where
				WorkflowProcess_id = @WorkflowProcessId
		end;

		fetch next from WorkflowProcess_cursor into @WorkflowProcessId, @WorkflowDefinitionId, @DataProviderId,
													@RecordObjectName, @RecordWhereClause, @LastStep, @NextStep, @StartDateTime;
	end;
	
	close WorkflowProcess_cursor;
	deallocate WorkflowProcess_cursor;
end;
