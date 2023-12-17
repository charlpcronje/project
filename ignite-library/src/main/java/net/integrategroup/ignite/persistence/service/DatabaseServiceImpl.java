package net.integrategroup.ignite.persistence.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ForeignKeyColumn;

/**
 * Database specific services.
 *
 * Note: The SQL code snippets are Microsoft SQL Server specific.
 *
 * @author Tony De Buys
 *
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

	private Logger logger = Logger.getLogger(DatabaseServiceImpl.class.getName());

	private static final String END_OF_LINE_MARKER = "\r\n";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> getSchemaNames()  {
		String sql = "SELECT " +
				"	  s.name schemaName"+
				"  FROM "+
				"    sys.schemas s " +
				"  ORDER BY s.name";

		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getTableNames()  {
		String sql = "SELECT " +
				"    s.name schemaName,"+
				"    t.name tableName " +
				"  FROM "+
				"    sys.tables t,"+
				"    sys.schemas s " +
				"  WHERE" +
				"    t.schema_id = s.schema_id" +
				"  ORDER BY t.name";

		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getTableNamesForSchema(String schemaName)  {
		String sql = "SELECT " +
				"    s.name schemaName,"+
				"    t.name tableName " +
				"  FROM "+
				"    sys.tables t,"+
				"    sys.schemas s " +
				"  WHERE" +
				"    t.schema_id = s.schema_id" +
				"    AND s.name = '" + schemaName + "'"  +
				"  ORDER BY " +
				"    t.name";

		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getFieldNames(String schemaName, String tableName) {
		String sql = "SELECT " +
				"	  column_name columnName," +
				"	  column_default defaultValue," +
				"	  is_nullable nullable," +
				"    data_type dataType," +
				"    columnproperty(object_id('" + schemaName + "." + tableName + "'), column_name, 'IsIdentity') isIdentity" +
				"  FROM " +
				"	  information_schema.columns " +
				"  WHERE " +
				"	  table_schema = '" + schemaName + "' and" +
				"	  table_name = '" + tableName + "'" +
				"  ORDER BY" +
				"    ordinal_position";
		// Changed sort order so that columns appear alphabetically rather than the definition order
		// The definition order will not make sense to business
		//"	  ordinal_position ASC";

		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<ForeignKeyColumn> getForeignKeyColumnsForTable(String schemaName, String tableName) {
		if (tableName == null) {
			return null;
		}

		List<ForeignKeyColumn> fkColumnSet = new ArrayList<>();

		String sql = "select" +
				"    obj.name as FK_NAME," +
				"    sch.name as [schema_name]," +
				"    tab1.name as [table]," +
				"    col1.name as [column]," +
				"    tab2.name as [referenced_table]," +
				"    col2.name as [referenced_column]" +
				"  from sys.foreign_key_columns fkc" +
				"  inner join sys.objects obj" +
				"    on obj.object_id = fkc.constraint_object_id" +
				"  inner join sys.tables tab1" +
				"    on tab1.object_id = fkc.parent_object_id" +
				"  inner join sys.schemas sch" +
				"    on tab1.schema_id = sch.schema_id" +
				"  inner join sys.columns col1" +
				"    on col1.column_id = parent_column_id AND col1.object_id = tab1.object_id" +
				"  inner join sys.tables tab2" +
				"    on tab2.object_id = fkc.referenced_object_id" +
				"  inner join sys.columns col2" +
				"    on col2.column_id = referenced_column_id AND col2.object_id = tab2.object_id" +
				"  where" +
				"    sch.name = '" + schemaName + "'" +
				"    and tab1.name = '" + tableName + "'";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> item: list) {
			ForeignKeyColumn fk = new ForeignKeyColumn((String) item.get("column"),
					(String) item.get("schema_name"),
					(String) item.get("referenced_table"),
					(String) item.get("referenced_column"));

			fkColumnSet.add(fk);
		}

		return fkColumnSet;
	}

	@Override
	public List<String> getPrimaryKeyColumnsForTable(String schemaName, String tableName) {
		List<String> pkColumnSet = new ArrayList<>();

		if (tableName == null) {
			return pkColumnSet;
		}

		String sql = "select " +
				"    column_name" +
				" from" +
				"    information_schema.key_column_usage" +
				" where " +
				"    objectproperty(object_id(CONSTRAINT_SCHEMA + '.' + quotename(CONSTRAINT_NAME)), 'IsPrimaryKey') = 1" +
				"    and table_name = '" + tableName + "' " +
				"    and table_schema = '" + schemaName + "'";

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> item: list) {
			pkColumnSet.add((String) item.get("column_name"));
		}

		return pkColumnSet;
	}

	@Override
	public Map<String, Object> executeQuery(String sql) {
		return executeQuery(null, sql);
	}

	@Override
	public Map<String, Object> executeQuery(String tableName, String sql) {
		boolean hasResultset = true;
		List<Map<String, Object>> columnNames = new ArrayList<>();

		String schemaName = null;
		if ((tableName != null) && (tableName.contains("."))) {
			schemaName = tableName.substring(0, tableName.indexOf("."));
			tableName = tableName.substring(schemaName.length() + 1);
		}

		final List<String> primaryKeys = getPrimaryKeyColumnsForTable(schemaName, tableName);
		final List<ForeignKeyColumn> foreignKeys = getForeignKeyColumnsForTable(schemaName, tableName);

		logger.info(sql);
		try {
			// ******************************************************************************************
			/*
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			SQLServerConnection sqlConnection = (SQLServerConnection) connection;

			sqlConnection. // connection log???
			 */

			// ******************************************************************************************

			jdbcTemplate.query(sql, new ResultSetExtractor<Integer>() {

				@Override
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {

					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();

					for(int foo = 1; foo <= columnCount ; foo++) {
						String columnName = rsmd.getColumnName(foo);

						Map<String, Object> column = new HashMap<>();
						column.put("name", columnName);
						column.put("type", rsmd.getColumnTypeName(foo));
						column.put("autoincrement", rsmd.isAutoIncrement(foo));
						column.put("nullable", rsmd.isNullable(foo) == 1);

						if (primaryKeys != null) {
							column.put("primarykey", primaryKeys.contains(columnName));
						}

						columnNames.add(column);
					}

					return columnCount;
				}
			});
		} catch (Exception e) {
			boolean allowContinue = false;
			if ((e instanceof UncategorizedSQLException) &&
					(e.getCause() != null) &&
					("The statement did not return a result set.").equals(e.getCause().getMessage())) {
				hasResultset = false;
				allowContinue = true;
			}

			if (!allowContinue) {
				throw e;
			}
		}

		List<Map<String, Object>> data = null;

		try {
			if (hasResultset) {
				data = jdbcTemplate.queryForList(sql);
			} else {
				jdbcTemplate.execute(sql);
			}

			if (data != null) {
				// Convert longs to strings to prevent Javascript rounding issues on large numbers
				for (Map<String, Object> row: data) {
					for (String key :row.keySet()) {
						Object o = row.get(key);

						if (o instanceof Long) {
							String value = Long.toString((Long) o);

							row.put(key, value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> result = new HashMap<>();
		result.put("columns", columnNames);
		result.put("resultset", data);

		if (tableName != null) {
			result.put("foreignkeys", foreignKeys);
		}

		return result;
	}

	@Override
	public void execute(String sql) throws Exception {
		logger.info(sql);
		jdbcTemplate.execute(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQueryAsCsv(String sql) throws Exception {
		String header = "";
		String body = "";

		Map<String, Object> data = executeQuery(sql);
		List<Map<String, Object>> columns = (ArrayList<Map<String, Object>>) data.get("columns");
		List<Map<String, Object>> rowData = (ArrayList<Map<String, Object>>) data.get("resultset");

		// Create a header row
		for (Map<String, Object> column : columns) {
			if (header.length() > 0) {
				header += ",";
			}
			header += "\"" + column.get("name") + "\"";
		}
		header += END_OF_LINE_MARKER;

		// export the data, row by row...
		for (Map<String, Object> element : rowData) {
			String row = "";
			// column by column...
			for (Map<String, Object> column : columns) {
				String colName = (String) column.get("name");
				Object fieldValue = element.get(colName);

				if (fieldValue == null) {
					fieldValue = "";
				} else {
					// quote strings
					if (fieldValue instanceof String) {
						fieldValue = ((String) fieldValue).replaceAll("\"", "\"\"");
						fieldValue = "\"" + ((String) fieldValue) + "\"";
					}
				}

				if (row.length() > 0) {
					row += ",";
				}

				row += fieldValue;
			}
			body += row + END_OF_LINE_MARKER;
		}

		return header + body;
	}
	
	@Override
	public void executeStoredProc(String sql, Object[] parameters) throws SQLException {
		Connection conn = null;
		CallableStatement cstm = null;
		
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			cstm = conn.prepareCall(sql);
					
			if (parameters != null) {
				for (int foo = 0; foo < parameters.length; foo++) {
					Object param = parameters[foo];
					boolean handled = false;
					
					if (param instanceof Integer) {
						cstm.setInt(foo + 1, (Integer) param);
						handled = true;
					}

					if (param instanceof Long) {
						cstm.setLong(foo + 1, (Long) param);
						handled = true;
					}
					
					if (param instanceof String) {
						cstm.setString(foo + 1, (String) param);
						handled = true;
					}

					// log if there is an unsupported object type here
					if (!handled) {
						String className = param.getClass().getName();
						
						logger.severe("The parameter with the class '" + className + "' and value '" + param.toString() + "' was not handled.");

						throw new SQLException("Unhandled Stored Procedure parameter.");
					}
				}
			}
			
			cstm.execute();
		} finally {
			if (cstm != null) {
				cstm.close();
			}
			
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return jdbcTemplate.getDataSource().getConnection();
	}

}
