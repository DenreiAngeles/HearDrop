package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
    private static String URL = "jdbc:mysql://localhost:3306/";
    private static String DBNAME = "HearDrop";
    private static String USER = "root";
    private static String PASSWORD = "dormantpig1";

    protected Connection connection;

    public BaseDAO() {
        try {
            
            connection = getConnectionToServer();
            createDatabaseIfNeeded(connection);
            
            connection = DriverManager.getConnection(URL + DBNAME, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error: Unable to establish a connection to the database. Please check your credentials.");
            e.printStackTrace();
        }
    }

    
    private static Connection getConnectionToServer() throws SQLException {
        String url = "jdbc:mysql://localhost:3306"; 
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    
    private void createDatabaseIfNeeded(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DBNAME;
        stmt.execute(createDatabaseSQL);
        stmt.execute("USE " + DBNAME);  
    }

    protected abstract T mapResultSetToObject(ResultSet rs) throws SQLException;

    public boolean add(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public T getById(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToObject(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> getList(String query, Object... params) {
        List<T> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToObject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean remove(String query, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
