package com.example.taskEvdokimov;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;

public class DBConnection {

    private static DBConnection instance;

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    private String jdbcURL = "jdbc:mysql://localhost:3306/";
    private String username = "root";
    private String password = "root";
    private Connection connection;

    public DBConnection() {
        this.connectToDB();     //to connect DB
        this.runScript();       //to run script to generate schema and tables
    }

    public void insertUsers(String application, String name, boolean isActive, String jobTitle, String department) throws SQLException {
        String sql = "INSERT INTO users (application, name, is_active, job_title, department) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, application);
        statement.setString(2, name);
        statement.setBoolean(3, isActive);
        statement.setString(4, jobTitle);
        statement.setString(5, department);

        statement.executeUpdate();
        statement.close();
        connection.commit();
    }

    public int insertProduct(String nameProduct, double amount, String unitMeasure) throws SQLException {
        String sql = "INSERT INTO product (name_product, amount, unit_measure) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, nameProduct);
        statement.setDouble(2, amount);
        statement.setString(3, unitMeasure);

        statement.executeUpdate();
        return this.getId(statement);
    }

    public void insertSupply(long id, String dateDoc, String datePosting, String nameUser, int idProduct, int quantity) throws SQLException {

        int idUser = this.getIdUserByName(nameUser);
        PreparedStatement statement;
        long idSupply = this.isExistIdSupply(id);

        if (idUser > 0) {

            if (idSupply == 0) {
                String sql = "INSERT INTO supply (id_doc, doc_date, posting_date, name_user, is_authorized, id_user) VALUES (?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sql);

                statement.setLong(1, id);
                statement.setDate(2, java.sql.Date.valueOf(dateDoc));
                statement.setDate(3, java.sql.Date.valueOf(datePosting));
                statement.setString(4, nameUser);
                statement.setBoolean(5, true);
                statement.setInt(6, idUser);
                statement.executeUpdate();
                connection.commit();

            }
        } else {

            if (idSupply == 0) {
                String sql = "INSERT INTO supply (id_doc, doc_date, posting_date, name_user, is_authorized) VALUES (?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                statement.setLong(1, id);
                statement.setDate(2, java.sql.Date.valueOf(String.valueOf(dateDoc)));
                statement.setDate(3, java.sql.Date.valueOf(String.valueOf(datePosting)));
                statement.setString(4, nameUser);
                statement.setBoolean(5, false);

                statement.executeUpdate();
                connection.commit();
            }
        }

        this.insertSupplyProduct(id,idProduct, quantity);
    }

    private void connectToDB() {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException throwables) {
            System.out.println("Can't connect to DB");
            throwables.printStackTrace();
        }
    }

    private void runScript() {
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = null;
        try {
            URL res = getClass().getClassLoader().getResource("script.sql");
            File file = Paths.get(res.toURI()).toFile();
            String absolutePath = file.getAbsolutePath();
            reader = new BufferedReader(new FileReader(absolutePath));
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        sr.runScript(reader);
    }

    private int getId(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.getGeneratedKeys();
        int generatedKey = 0;
        if (rs.next()) {
            generatedKey = rs.getInt(1);
        }
        statement.close();
        return generatedKey;
    }

    private int getIdUserByName(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setString(1,  username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.first()) {
            return resultSet.getInt("id");
        } else {
            return 0;
        }
    }

    private Long isExistIdSupply(long id) throws SQLException {
        String sql = "SELECT * FROM supply WHERE id_doc = ?";
        PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.first()) {
            return resultSet.getLong("id_doc");
        } else {
            return 0L;
        }
    }

    private void insertSupplyProduct(long idSupply, int idProduct, int quantity) throws SQLException {
        String sqlCheck = "SET FOREIGN_KEY_CHECKS=0";
        PreparedStatement statementFK = connection.prepareStatement(sqlCheck);
        statementFK.execute();
        connection.commit();

        String sql = "INSERT INTO supply_product (id_supply, id_product, quantity) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setLong(1, idSupply);
        statement.setInt(2, idProduct);
        statement.setInt(3, quantity);

        statement.executeUpdate();
        connection.commit();

        String sqlCheckTwo = "SET FOREIGN_KEY_CHECKS=1";
        statementFK = connection.prepareStatement(sqlCheckTwo);
        statementFK.execute();
        connection.commit();
    }
}

