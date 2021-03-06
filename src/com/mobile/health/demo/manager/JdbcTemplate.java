package com.mobile.health.demo.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
	private static JdbcTemplate jdbcTemplate;
	private Connection connection;

	private JdbcTemplate() {
	}

	public synchronized static JdbcTemplate getMySQLJdbcTemplate() {
		if (jdbcTemplate == null)
			jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.initializeConnection();
		return jdbcTemplate;
	}

	private void initializeConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost/publichealthportal", "postgres", "postgres");//5432
		} catch (SQLException e) {
			System.out.println("ERROR: failed to connect to the databse");
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ERROR: failed to load MySQL JDBC driver.");
			cnfe.printStackTrace();
		}
	}

	public int executeUpdate(String sqlQuery) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sqlQuery);
			return ps.executeUpdate(); // executes the insert query
		} catch (Exception e) {
			System.out.println("ERROR executing query: ");
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public ResultSet executeQuery(String sqlQuery) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sqlQuery);
			return ps.executeQuery(); // executes the insert query
		} catch (Exception e) {
			System.out.println("ERROR executing query: ");
			e.printStackTrace();
		}
		return null;
	}
}
