create or alter procedure  ig_db.WorkflowProcessLogEvent(@WorkflowProcessId bigint,
                                                        @WorkflowDefinitionStepNumber integer,
											            @WorkflowDefinitionStepName varchar(200),
														@Message varchar(max),
														@StatusMessage varchar(100))
as
begin
	print 'Inserting WorkflowProcessLog entry: ' + @workflowDefinitionStepName + ' - ' + @Message;

	insert into
		 ig_db.WorkflowProcessLog
	(WorkflowProcess_id, WorkflowDefinitionStepNumber, WorkflowDefinitionStep_name, Log_Message, WorkflowProcessStatus) 
	values (
		@WorkflowProcessId,
		@WorkflowDefinitionStepNumber,
		@WorkflowDefinitionStepName,
		@Message,
		@statusMessage
	);
end;

/*
select * from  ig_db.WorkflowProcessLog
select * from  ig_db.WorkflowProcess order by WorkflowProcess_id desc
select * from  ig_db.WorkflowProcess where Active_flag = 'Y'
exec  ig_db.WorkflowPoller

*/
