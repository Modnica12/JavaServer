package com.faceless.sql;

import com.faceless.sql.migration.CreateVmAndUsersTables;

import java.sql.*;

public class Database
{
	private final String     DB_URL   = "jdbc:mysql://localhost?login=root&password=link&useUnicode=yes&characterEncoding=UTF-8";
	private final String     username = "root";
	private final String     password = "link";
	private       Connection connection;
	private       Statement  statement;

	public int executeUpdate(String stmt)
	{
		try
		{
			return statement.executeUpdate(stmt);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	public ResultSet executeQuery(String stmt)
	{
		try
		{
			return statement.executeQuery(stmt);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean databaseExist(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SHOW DATABASES LIKE 'VMDB';").executeUpdate() == 1;
	}

	public void connect() throws ClassNotFoundException
	{
		//Тут можно взаимодействовать с ней, добавлять таблицы, заполнять их, получать значения и тд
		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		//STEP 3: Open a connection
		System.out.println("Connecting to database...");
		try
		{
			boolean           dbExisted = false;
			Connection        conn      = DriverManager.getConnection(DB_URL, username, password);
			PreparedStatement stmt      = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS VMDB;");
			stmt.execute();
			PreparedStatement stmtUTF      =
					conn.prepareStatement("ALTER DATABASE VMDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
			stmtUTF.execute();
			statement = conn.createStatement();
			connection = conn;
			System.out.println("Database created successfully...");

			CreateVmAndUsersTables c = new CreateVmAndUsersTables(this);
			c.up();
		}
		catch (Exception se)
		{
			//Handle errors for JDBC
			se.printStackTrace();
		}//Handle errors for Class.forName
	}
}