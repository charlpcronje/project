package net.integrategroup.ignite.persistence.model;

/**
 * @author Tony De Buys
 *
 * Used for storing Foreign Key information in the database utilities
 */
public class ForeignKeyColumn {

	private String columnName;
	private String referencedSchemaName;
	private String referencedTableName;
	private Object referencedColumnName;

	public ForeignKeyColumn(String columnName,
			String referencedSchemaName,
			String referencedTableName,
			String referencedColumnName) {
		this.columnName = columnName;
		this.referencedSchemaName = referencedSchemaName;
		this.referencedTableName = referencedTableName;
		this.referencedColumnName = referencedColumnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getReferencedSchemaName() {
		return referencedSchemaName;
	}

	public void setReferencedSchemaName(String referencedSchemaName) {
		this.referencedSchemaName = referencedSchemaName;
	}

	public String getReferencedTableName() {
		return referencedTableName;
	}

	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}

	public Object getReferencedColumnName() {
		return referencedColumnName;
	}

	public void setReferencedColumnName(Object referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}

}
