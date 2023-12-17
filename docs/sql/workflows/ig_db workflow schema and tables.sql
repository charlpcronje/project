drop procedure if exists ig_db.WorkflowSendMail;
drop procedure if exists ig_db.WorkflowPoller;
drop procedure if exists ig_db.WorkflowDefinitionStepsToGraph;
drop procedure if exists ig_db.WorkflowRenumber;
drop procedure if exists ig_db.WorkflowInitialize;
drop procedure if exists ig_db.WorkflowCreateStep;
drop procedure if exists ig_db.WorkflowValidation;
drop procedure if exists ig_db.WorkflowProcessLogEvent;
drop table if exists ig_db.WorkflowProcess;
drop table if exists ig_db.WorkflowProcessLog;
drop table if exists ig_db.WorkflowDefinitionStep;
drop table if exists ig_db.WorkflowDefinition;
drop view if exists ig_db.v_WorkflowDefinition;

create table ig_db.WorkflowDefinition (
	WorkflowDefinitionId bigint not null,		-- unique key
	WorkflowDefinitionCode varchar(50) not null,				-- code identifying this EscalationWorkflow, eg SUBMISSION_UPLOAD_FAILED
	RecordObjectName varchar(1000) null,                        -- the database table associated with this workflowassetcondition
	WorkflowDefinitionName varchar(100) not null,				-- name of workflow, eg. Submission Structure failure
	WorkflowDefinitionDescription varchar(4000) not null,		-- description
	SlaMinutes integer not null default 120,					-- sla in minutes before escalating to the next step
	constraint pk_WorkflowDefinition primary key (WorkflowDefinitionId asc)	
);

create table ig_db.WorkflowDefinitionStep (
	WorkflowDefinitionId bigint not null,
	WorkflowDefinitionStepNumber integer not null,
	WorkflowDefinitionStepName varchar(200) not null,
	Mailbox	varchar(500) null,
	TestField varchar(500) null,
	TestValue varchar(100) null,
	IfTrueStepNumber integer null,
	IfFalseStepNumber integer null,
	MarkFailedFlag varchar(1) null,
	MarkCompletedFlag varchar(1) null,
	PauseWorkflowFlag varchar(1) null,
	MailSubject varchar(1000) null,
	MailBodyText varchar(65535) null,
	SqlText varchar(65535) null,
	GotoStepNumber integer null,
	TriggerWorkflowDefinitionCode varchar(200) null,
	constraint pk_WorkflowDefinitionStep primary key (WorkflowDefinitionId asc, WorkflowDefinitionStepNumber asc)
);

alter table ig_db.WorkflowDefinitionStep add constraint fk_WorkflowDefinitionStep_WorkflowDefinition 
                                                        foreign key (WorkflowDefinitionId)
														references ig_db.WorkflowDefinition (WorkflowDefinitionId);

create table ig_db.WorkflowProcess (
	WorkflowProcessId bigint not null,
	WorkflowDefinitionId bigint not null,
	RecordObjectName varchar(1000) not null,
	RecordWhereClause varchar(5000) not null,
	LastStep integer null,
	NextStep integer null,
	StartDateTime datetime default current_timestamp,
	ExecuteDateTime datetime null,
	ActiveFlag varchar(1) not null default 'Y',
	StatusCode varchar(50) not null default 'Processing',
	constraint pk_WorkflowProcess primary key (WorkflowProcessId asc)
);

alter table ig_db.WorkflowProcess add constraint fk_WorkflowProcess_WorkflowDefinition 
                                                        foreign key (WorkflowDefinitionId)
														references ig_db.WorkflowDefinition (WorkflowDefinitionId);


create table ig_db.WorkflowProcessLog(
	WorkflowProcessLogId bigint not null,
	WorkflowProcessId bigint not null,
	WorkflowDefinitionStepNumber integer null,
	WorkflowDefinitionStepName varchar(200) null,
	Log_Message varchar(65535) not null,
	WorkflowProcessStatus varchar(100) not null,
	Log_Timestamp DateTime not null default current_timestamp,
	constraint pk_WorkflowProcessLog primary key (WorkflowProcessLogId asc)	
);

alter table ig_db.WorkflowProcessLog add constraint fk_WorkflowProcessLog_WorkflowProcess
                                                        foreign key (WorkflowProcessId)
														references ig_db.WorkflowProcess (WorkflowProcessId);


