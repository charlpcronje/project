package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.integrategroup.ignite.persistence.model.ForeignKeyColumn;

public interface DatabaseService {

	List<Map<String, Object>> getSchemaNames();

	List<Map<String, Object>> getTableNames();

	List<Map<String, Object>> getTableNamesForSchema(String schemaName);

	List<Map<String, Object>> getFieldNames(String schemaName, String tableName);

	List<String> getPrimaryKeyColumnsForTable(String schemaName, String tableName);

	List<ForeignKeyColumn> getForeignKeyColumnsForTable(String schemaName, String tableName);

	void execute(String sql) throws Exception;

	Map<String, Object> executeQuery(String sql);

	Map<String, Object> executeQuery(String tableName, String sql);

	String executeQueryAsCsv(String sql) throws Exception;

	void executeStoredProc(String sql, Object[] parameters) throws SQLException;
	
	Connection getConnection() throws SQLException;

}
