create or alter procedure WorkflowDefinitionStepRenumber(@workflowDefinitionId bigint) 
as begin
	declare @highestStep int;
	declare @workflowDefinitionStepNumber int;

	declare @arr table(stepNumber int, newNumber int);
	
	/*
	-- example usage
	insert into @arr (stepNumber, newNumber) values (1, 10);
	select * from @arr;
	*/

	select @highestStep = 10;

	-- declare cursor for
	-- select * from WorkflowDefinitionSteps order by WorkflowDefinitionStepNumber where WorkflowDefinition_id = @workflowDefinitionId

	-- open cursor
	-- fetch into ...

	-- while cursor has records 
	-- begin
			if (@workflowDefinitionStepNumber not in @arr) 
			begin
				insert into @arr(@workflowDefinitionStepNumber, @highestStep);
				select = @highestStep + 10;
			end;

	--		fetch into ....
	-- end;

	-- close cursor
	-- deallocate cursor

	-- create cursor
	-- select * from  ig_db.WorkflowDefinitionStep where WorkflowDefinition_id = @workflowDefinitionId;

	-- open cursor
	-- fetch into

	-- whiole fetch result
	-- begin
	--   update the row with the looked up values from our array
	-- end
end;
