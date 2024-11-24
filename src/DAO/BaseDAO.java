package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DBNAME = "HearDrop";
    private static final String USER = "root";
    private static final String PASSWORD = "dormantpig1";

    protected Connection connection;

    public BaseDAO() {
        try {
            connection = getConnectionToServer();
            createDatabaseIfNeeded(connection);
            connection = DriverManager.getConnection(URL + DBNAME, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error: Unable to establish a connection to the database. Please check your database settings.");
            logError("Database connection error", e);
        }
    }

    private static Connection getConnectionToServer() throws SQLException {
        String url = "jdbc:mysql://localhost:3306";
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    private void createDatabaseIfNeeded(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DBNAME;
            stmt.execute(createDatabaseSQL);
            stmt.execute("USE " + DBNAME);
        } catch (SQLException e) {
            System.out.println("Error: Unable to create or access the database '" + DBNAME + "'.");
            logError("Database creation error", e);
            throw e;
        }
    }

    protected abstract T mapResultSetToObject(ResultSet rs) throws SQLException;

    public boolean add(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: Unable to execute the add operation.");
            logError("Add operation error", e);
        }
        return false;
    }

    public T getById(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToObject(rs);
            } else {
                System.out.println("No record found for the given criteria.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve the record.");
            logError("GetById operation error", e);
        }
        return null;
    }

    public List<T> getList(String query, Object... params) {
        List<T> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToObject(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve the list of records.");
            logError("GetList operation error", e);
        }
        return list;
    }

    public boolean update(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: Unable to execute the update operation.");
            logError("Update operation error", e);
        }
        return false;
    }

    public boolean remove(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            setParameters(stmt, params);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: Unable to execute the remove operation.");
            logError("Remove operation error", e);
        }
        return false;
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    private void logError(String context, Exception e) {
        try (java.io.FileWriter fw = new java.io.FileWriter("errors.log", true);
             java.io.PrintWriter pw = new java.io.PrintWriter(fw)) {
            pw.println("Error Context: " + context);
            pw.println("Message: " + e.getMessage());
            e.printStackTrace(pw);
            pw.println("------------------------------------");
        } catch (Exception ex) {
            System.out.println("Error: Unable to write to the log file.");
        }
    }
}
