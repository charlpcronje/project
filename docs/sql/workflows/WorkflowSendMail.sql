create or alter procedure  ig_db.WorkflowSendMail(
	@dataProviderId bigint,
	@WorkflowProcessId bigint,
	@WorkflowDefinitionStepNumber integer,
	@WorkflowDefinitionStepName varchar(200),
	@mailbox varchar(500),
	@subject varchar(1000), 
	@body varchar(5000))
as
begin
	declare @s varchar(max);
	declare @recipients varchar(500);

	declare @sql nvarchar(max) = '';
	declare @paramDef nvarchar(200);

	declare @recordObjectName varchar(500);
	declare @recordWhereClause varchar(max);
	declare @schemaName varchar(200);
	declare @tableName varchar(300);
	declare @columnName varchar(200);
	declare @columnValue varchar(4000);

	set @recipients = '';

	if (charindex('@', @mailbox) > 0) 
	begin
		-- We have email addresses in the mailbox... simply set the recipients
		select @recipients = @mailbox;
	end else begin
		-- note: does not do multiple addresses
		declare @emailAddress varchar(200);

		declare roleToEmail_cursor cursor for
			select 
					au.emailAddress
				from 
					idi.DataProviderApplicationUser dpau,
					idi.ApplicationUser au,
					idi.Role r
				where 
					dpau.DataProvider_id = @dataProviderId
					and au.ApplicationUser_id = dpau.ApplicationUser_id
					and au.UserActive_flag = 'Y'
					and ((getDate() between dpau.UserEffectiveDate and dpau.UserLapsedDate) or
						 (getDate() >= dpau.UserEffectiveDate and dpau.UserLapsedDate is null)
						)
					and r.Role_id = dpau.Role_id
					and r.RoleDescription = @mailbox;

		open roleToEmail_cursor;
		fetch roleToEmail_cursor into @emailAddress;

		while (@@FETCH_STATUS = 0) 
		begin
			if (@emailAddress is not null) 
			begin
				if (len(@recipients) > 0) 
				begin
					select @recipients = @recipients + ';';
				end;

				select @recipients = @recipients + @emailAddress;
			end;
		
			fetch roleToEmail_cursor into @emailAddress;
		end;

		close roleToEmail_cursor;
		deallocate roleToEmail_cursor;				
	end;

	if (len(@recipients) = 0) 
	begin
		declare @msg varchar(max);
		select @msg = 'No email address found for the role: ' + @mailbox + ' (' + cast(@dataProviderId as varchar(10)) + ')';
		throw 51000, @msg, 1;
	end;

	-- *********************************************************************************************************
	-- TODO: Update the header
	-- select @subject = replace filename
	-- select @subject = replace gdi link
	-- need to think about this because we need to be generic, this proc doesn't know about submissions, etc...

	-- *********************************************************************************************************
	-- TODO: Update the body
	-- select @body = replace filename
	-- select @body = replace gdi link
	-- need to think about this because we need to be generic, this proc doesn't know about submissions, etc...

	
	-- ##fieldname##

	select 
			@recordObjectName = wp.RecordObjectName,
			@recordWhereClause = wp.RecordWhereClause
		from 
			 ig_db.WorkflowProcess wp
		where
			wp.WorkflowProcess_id = @WorkflowProcessId;

	set @schemaName = null;
	set @tableName = @recordObjectName;

	-- split into schema and  table name
	if (charindex('.', @recordObjectName) > 0) 
	begin
		set @schemaName = substring(@recordObjectName, 0, charindex('.', @recordObjectName));
		set @tableName = substring(@recordObjectName, charindex('.', @recordObjectName) + 1, len(@recordObjectName));
	end;

	declare column_cursor cursor for
		select 
				c.name 
			from 
				sys.columns c,
				sys.tables t ,
				sys.schemas s
			where 
				t.schema_id = s.schema_id
				and c.object_id = t.object_id
				and s.name = case
					when @schemaName is null then 
						s.name
					else
						@schemaName
					end
				and	s.name = @schemaName
				and t.name = @tableName;
	 
	open column_cursor;
	fetch column_cursor into @columnName;

	while (@@FETCH_STATUS = 0) 
	begin
		-- if we don't have the field in our subject or body...
		if ((charindex('##' + @columnName + '##', @subject) = 0) and
		     (charindex('##' + @columnName + '##', @body) = 0)) 
		begin
			-- get the next record...
			fetch column_cursor into @columnName;

			-- continue to the next field...
			continue;
		end;
		
		-- some dynamic sql to select this value...
		set @sql = 'SELECT @RetValOut=' + @columnName + ' FROM ' + @RecordObjectName + ' WHERE ' + @RecordWhereClause;
		set @paramDef = N'@RetValOut varchar(4000) OUTPUT';

		print ('Executing: ' + @sql);
		exec sp_executesql @sql, @paramDef, @RetValOut=@ColumnValue output;

		set @subject = replace(@subject, '##' + @ColumnName + '##', @columnValue);
		set @body = replace(@body, '##' + @ColumnName + '##', @columnValue);

		fetch column_cursor into @columnName;
	end;
	
	close column_cursor;
	deallocate column_cursor;

	print '------------------------------------------------------------------------------------------------';		
	print 'Subject: ' + @subject;
	print 'Recipients: ' + @recipients;
	print 'Body: ' + @body;

	exec msdb.dbo.sp_send_dbmail  
		@recipients = @recipients,
		@from_address = 'do_no_reply@santam.co.za',
		@subject = @subject,
		@body = @body;

	-- NOTE: to send mail as HTML add parameter to the SP:
	--       @body_format = 'HTML'

	set @s = concat(@subject, ' sent to ', @recipients);

	-- mail send was successful...
	exec  ig_db.WorkflowProcessLogEvent @WorkflowProcessId, 
	                                   @WorkflowDefinitionStepNumber,
						               @WorkflowDefinitionStepName,
	                                   @s,
							           'Mail Sent';
end;
go

/*
declare @subject varchar(2000);
declare @body varchar(5000);
set @subject = 'Test: ##OriginalFilename##';
set @body = '<b>Test</b>: ##OriginalFilename##';
exec  ig_db.WorkflowSendMail 5, 226, 20, 'Send Mail (1)', 'tony.debuys@santam.co.za', @subject, @body;

*/