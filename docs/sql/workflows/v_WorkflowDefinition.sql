CREATE OR ALTER view [ig_db].[v_WorkflowDefinition]
as 
select 
		wd.WorkflowDefinition_id,
		wd.WorkflowDefinition_code,
		wd.WorkflowDefinitionName,
		wd.WorkflowDefinitionDescription,
		wd.SlaMinutes,
		wd.FailoverMailbox,
		wd.RecordObjectName,
		(select max(WorkflowDefinitionStepNumber) 
		     from 
				 ig_db.WorkflowDefinitionStep wds 
		     where 
			    wds.WorkflowDefinition_id = wd.WorkflowDefinition_id) WorkflowDefinitionStep_count,
		(select 
			case 
				when count(1) = 0 then 'N'
				else 'Y'
			end 
		    from 
				 ig_db.WorkflowProcess wp
		     where 
			    wp.WorkflowDefinition_id = wd.WorkflowDefinition_id
				and Active_flag = 'Y'
		) InUse_flag  
	from 
		 ig_db.WorkflowDefinition wd
GO
