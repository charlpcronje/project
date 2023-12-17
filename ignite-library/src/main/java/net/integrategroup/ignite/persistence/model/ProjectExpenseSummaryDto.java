package net.integrategroup.ignite.persistence.model;

// @author Johannes Marais

public class ProjectExpenseSummaryDto {

	private Long projectId;
	private String projectName;
	private String subProjNumber;
	private Long expenseTypeId;
	private Long expenseTypeParentId;
	private String expenseTypeParentName;
	private String expenseTypeName;
	private Double theSum;


	public ProjectExpenseSummaryDto() {
		// nothing
	}

	public ProjectExpenseSummaryDto
			(
					Long projectId,
					String projectName,
					String subProjNumber,
					Long expenseTypeId,
					Long expenseTypeParentId,
					String expenseTypeParentName,
					String expenseTypeName,
					Double theSum
			) {

		this();
		this.projectId = projectId;
		this.projectName = projectName;
		this.subProjNumber = subProjNumber;
		this.expenseTypeId = expenseTypeId;
		this.expenseTypeParentId = expenseTypeParentId;
		this.expenseTypeParentName = expenseTypeParentName;
		this.expenseTypeName = expenseTypeName;
		this.theSum = theSum;

	}


	public String getSubProjNumber() {
		return subProjNumber;
	}

	public void setSubProjNumber(String subProjNumber) {
		this.subProjNumber = subProjNumber;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public Long getExpenseTypeParentId() {
		return expenseTypeParentId;
	}

	public void setExpenseTypeParentId(Long expenseTypeParentId) {
		this.expenseTypeParentId = expenseTypeParentId;
	}

	public String getExpenseTypeParentName() {
		return expenseTypeParentName;
	}

	public void setExpenseTypeParentName(String expenseTypeParentName) {
		this.expenseTypeParentName = expenseTypeParentName;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public Double getTheSum() {
		return theSum;
	}

	public void setTheSum(Double theSum) {
		this.theSum = theSum;
	}

}
