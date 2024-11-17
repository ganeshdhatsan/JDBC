package JDBC_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.testng.annotations.Test;

public class DataFetingOverTheJDBCConnectionFromDB {
	static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String USERNAME = "hr";
	static final String PASSWORD = "hr";
	String sqlQuery = "select * from employees";

	@Test
	private void jdbcSetup() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaDta = null;

		try {
			// Establishing connection to Oracle database
			connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

			// Creating statement
			statement = connection.createStatement();

			// SQL query to retrieve data
//	              String sqlQuery = "select first_name from employees where manager_id = 114";		

			// Executing SQL query
			resultSet = statement.executeQuery(sqlQuery);

			// Convert ResultSet to List of Maps
			resultSetMetaDta = resultSet.getMetaData();
			int columnCount = resultSetMetaDta.getColumnCount();
			System.out.println("columnCount --> " + columnCount);
			List<Map<String, Object>> resultList = new ArrayList<>();
			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = resultSetMetaDta.getColumnName(i);
					Object columnValue = resultSet.getObject(i);
					row.put(columnName, columnValue);
				}
				resultList.add(row);

			}
			System.out.println("resultList " + resultList);
			// Printing the result
			for (Map<String, Object> row : resultList) {
				for (Entry<String, Object> entry : row.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Closing resources
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	private void testJDBC() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
			statement = connection.createStatement();
			String query = "select * from employees ";
			resultSet = statement.executeQuery(query);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int count = metaData.getColumnCount();
			List<Map<String, Object>> listMap = new LinkedList<>();
			while (resultSet.next()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					String key = metaData.getColumnName(i);
					Object value = resultSet.getObject(i);
					map.put(key, value);
				}
				listMap.add(map);
			}
			for (Map<String, Object> map : listMap) {
				Set<Entry<String, Object>> entrySet = map.entrySet();
				for (Entry<String, Object> entry : entrySet) {

					System.out.print("columnname : " + entry.getKey());
					System.out.print("value : " + entry.getValue());

				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
	}

	
}




