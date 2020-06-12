package config.database;

import java.sql.*;

// This class is responsible for handling the configuration of Database Connection
public class DBConnection {
	
	//Credentials
	private static final  String DB_CONNECTION= "jdbc:postgresql://localhost:5432/recipebook";
	private static final String DB_USER = "postgres";
	private static final String DB_PASSWORD = "password";
	
	private static Connection dbConnection = null;
	
	//Constructor
	public DBConnection() {
		
	}

	//Statement Preparation
	public static PreparedStatement prepare(String stm) throws SQLException{
		PreparedStatement preparedStatement = null;
		try {
			Connection dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(stm);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return preparedStatement;
	}
	
	//Establishing the connection with DB
	private static Connection getDBConnection() {
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
			
			
			String dbUrl = System.getenv("JDBC_DATABASE_URL");
			dbConnection = DriverManager.getConnection(dbUrl);
			//dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			dbConnection.setAutoCommit(false);
			
			return dbConnection;
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Connection Problem");
		return null;
	}

	public static Connection getDbConnection() {
		return dbConnection;
	}


	public static void setDbConnection(Connection dbConnection) {
		DBConnection.dbConnection = dbConnection;
	}
}
