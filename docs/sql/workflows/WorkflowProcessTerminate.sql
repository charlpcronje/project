create or alter procedure  ig_db.WorkflowProcessTerminate (
	@WorkflowProcessId bigint,
	@Username varchar(100)
)
as
begin
	declare @s varchar(max);

	select @s = concat('Workflow process terminated by ', @Username);

	update
		 ig_db.WorkflowProcess
	set
		Active_flag = 'N',
		ExecuteDateTime = CURRENT_TIMESTAMP,
		Status_code = 'Terminated'
	where
		WorkflowProcess_id = @WorkflowProcessId;

	exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, null, null, @s, 'Terminated';
end;

