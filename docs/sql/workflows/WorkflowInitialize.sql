create or alter procedure  ig_db.WorkflowInitialize(@className varchar(100),
                                                   @processName varchar(100),
                                                   @dataProviderId bigint,
                                                   @sourceEntity varchar(1000),
                                                   @sourceWhereClause varchar(max)
)
as
begin
	declare @workflowDefinitionCode varchar(100);
	declare @workflowDefinitionName varchar(100);
	declare @workflowProcessId bigint;
	declare @workflowDefinitionId bigint;
	
	declare @count integer;

	-- build up @workflowDefinitionCode and @workflowDefinitionName
	select @workflowDefinitionCode = concat(@className, '_', @processName);
	select @workflowDefinitionName = concat(@className, ' ', @processName);

	-- if they have not specified a className we need to look for a workflow with the @processName
	if ((@className is null) or (@className = ''))
	begin
		set @workflowDefinitionCode = @processName;
		set @workflowDefinitionName = @processName;
	end;

	print 'Starting workflow: ' + @workflowDefinitionCode + '...';

	select @workflowDefinitionId = WorkflowDefinition_id 
		from 
			 ig_db.WorkflowDefinition 
		where 
			WorkflowDefinition_code = @workflowDefinitionCode and
			((DataProvider_id = @dataProviderId) or (DataProvider_id is null));

	if (@workflowDefinitionId is null) 
	begin
		print 'Not starting workflow: ' + @workflowDefinitionCode + ' because no such workflow exists.';

		declare @defaultSla integer;
		declare @defaultFailoverMailbox varchar(500);

		-- Get a default SLA
		select @defaultSla = cast(isnull(ControlVariableValue_text, '60') as integer)
			from 
				idi.ApplicationControlVariable 
			where 
				ControlVariableName = 'UI.workflow.defaultSLA';

		-- Get a default failover mailbox
		select @defaultFailoverMailbox = isnull(ControlVariableValue_text, 'ROLE_SYS_ADMIN_CONFIG')
			from 
				idi.ApplicationControlVariable 
			where 
				ControlVariableName = 'UI.workflow.defaultFailoverMailbox';

		-- Insert a blank definition so that the user can see it and maybe then create steps for it?
		insert into
			 ig_db.WorkflowDefinition
			(WorkflowDefinition_code, WorkflowDefinitionName, DataProvider_id, 
			 WorkflowDefinitionDescription, SlaMinutes, FailoverMailbox, RecordObjectName
			) values (
				@workflowDefinitionCode,
				@workflowDefinitionName,
				null,
				'Automatically created within GDI for ' + @workflowDefinitionCode,
				@defaultSla,
				@defaultFailoverMailbox,
				@ClassName
			);

		return;
	end;


	-- after all other validations...
	
	-- look for active workflows for this record already
	-- Note: We also check the WorkflowDefinition_id otherwise we find this instance of the workflow process 
	select 
			@count = count(1) 
		from 
			 ig_db.WorkflowProcess 
		where 
			WorkflowDefinition_id = @workflowDefinitionId
			and RecordObjectName = @sourceEntity 
			and RecordWhereClause = @sourceWhereClause
			and Active_flag = 'Y';

	if (@count > 0) 
	begin
		print 'An active workflow for the source record was found.  A new workflow will not be initialized.';
	end;

	if (@count = 0)
	begin
		-- does the workflow have steps?
		select 
				@count = count(1) 
			from
				 ig_db.WorkflowDefinitionStep
			where
				WorkflowDefinition_id = @workflowDefinitionId

		if (@count = 0)
		begin
			print 'The workflow definition does not have any steps.  No workflow will be executed';
		end;

		-- only proceed if we have workflows steps
		if (@count > 0) 
		begin

			-- TODO: should also check that the workflow steps are valid!!!!

			if (@dataProviderId is null)
			begin
				print 'Cannot initialize workflow because the data provider was  not specified';
			end else begin
				-- insert and make the workflow process active
				insert into  ig_db.WorkflowProcess
					(WorkflowDefinition_id, DataProvider_id, RecordObjectName, RecordWhereClause, Active_flag) 
					values 
					(@workflowDefinitionId, @dataProviderId, @sourceEntity, @sourceWhereClause, 'Y');

				-- get the ID of the newly inserted WorkflowProcess
				select @workflowProcessId = SCOPE_IDENTITY();

				-- Now log the event so that we have a trace of it
				exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, null, null, 'Workflow initialized', 'Created';

				print('Workflow ' + @workflowDefinitionCode + ' successfully initialized.');
			end;
		end;
	end;
end;

