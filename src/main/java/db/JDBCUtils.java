package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kobis on 15 May, 2022
 */
public class JDBCUtils {


    private static ResultSet createResultSet(PreparedStatement statement) throws SQLException {

        return statement.executeQuery();

    }

    private static PreparedStatement createStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    private static void closeResources(Connection conn, PreparedStatement statement) throws SQLException {
        ConnectionPool.getInstance().returnConnection(conn);
        statement.close();
    }


    private static void closeResources(Connection conn, PreparedStatement statement, ResultSet resultSet) throws SQLException {

        ConnectionPool.getInstance().returnConnection(conn);
        statement.close();
        resultSet.close();

    }

    public static void executeQuery(String sql) throws InterruptedException, SQLException {


        //Step 1 - Not relevant anyMore since we're using JDBC Drive Type 4

        //Step 2 - get Resources
        Connection conn = ConnectionPool.getInstance().getConnection();

        //Step 3 - Prepare your Statement & execute it
        PreparedStatement statement = JDBCUtils.createStatement(conn, sql);
        statement.execute();

        //Step 4 -  Not relevant it's not DQL - Result Set

        //Step 5 - Close Resources
        closeResources(conn, statement);

    }


    public static void executeQuery(String sql, Map<Integer, Object> params) throws SQLException, InterruptedException {

        //Step 1 - Not relevant anyMore since we're using JDBC Drive Type 4

        //Step 2 - get Resources
        Connection conn = ConnectionPool.getInstance().getConnection();

        //Step 3 - Prepare your Statement & execute it
        PreparedStatement statement = JDBCUtils.createStatement(conn, sql);
        addParams(statement, params);
        statement.execute();

        //Step 4 -  Not relevant it's not DQL - Result Set

        //Step 5 - Close Resources
        closeResources(conn, statement);

    }


    public static List<?> executeQueryWithResults(String sql) throws SQLException, InterruptedException {

        List<?> rows = new ArrayList<>();
        //Step 1 - Not relevant anyMore since we're using JDBC Drive Type 4

        //Step 2 - get Resources
        Connection conn = ConnectionPool.getInstance().getConnection();

        //Step 3 - Prepare your Statement & execute it
        PreparedStatement statement = JDBCUtils.createStatement(conn, sql);

        //Step 4 -  Now It's  relevant since it's a DQL
        ResultSet resultSet = JDBCUtils.createResultSet(statement);
        rows = getRows(resultSet);

        //Step 5 - Close Resources
        closeResources(conn, statement, resultSet);
        return rows;

    }


    public static List<?> executeQueryWithResults(String sql, Map<Integer, Object> params) throws SQLException, InterruptedException {

        List<?> rows = new ArrayList<>();
        //Step 1 - Not relevant anyMore since we're using JDBC Drive Type 4

        //Step 2 - get Resources
        Connection conn = ConnectionPool.getInstance().getConnection();

        //Step 3 - Prepare your Statement & execute it
        PreparedStatement statement = JDBCUtils.createStatement(conn, sql);
        addParams(statement, params);

        ResultSet resultSet = JDBCUtils.createResultSet(statement);

        //Step 4 -  Now It's  relevant since it's a DQL
        rows = getRows(resultSet);

        //Step 5 - Close Resources
        closeResources(conn, statement, resultSet);

        return rows;

    }


    private static PreparedStatement addParams(PreparedStatement statement, Map<Integer, Object> params) throws SQLException {

        for (Map.Entry<Integer, Object> entry : params.entrySet()) {
            int key = entry.getKey();
            Object val = entry.getValue();
            if (val instanceof Integer) {
                statement.setInt(key, (Integer) val);
                continue;
            }
            if (val instanceof String) {
                statement.setString(key, (String) val);
                continue;
            }
            if (val instanceof Date) {
                statement.setDate(key, (Date) val);
                continue;
            }
            if (val instanceof Double) {
                statement.setDouble(key, (Double) val);
                continue;
            }
            if (val instanceof Boolean) {
                statement.setBoolean(key, (Boolean) val);
                continue;
            }

        }

        return statement;

    }


    private static List<?> getRows(ResultSet resultSet) throws SQLException {

        ResultSetMetaData md = resultSet.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> results = new ArrayList<>();

        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i < columns + 1; i++) {
                row.put(md.getColumnName(i), resultSet.getObject(i));
            }
            results.add(row);
        }

        return results;

    }


}
