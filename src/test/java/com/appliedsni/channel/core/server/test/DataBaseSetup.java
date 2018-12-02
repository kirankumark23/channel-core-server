package com.appliedsni.channel.core.server.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appliedsni.channel.core.server.common.utils.PropertiesCache;


public class DataBaseSetup {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseSetup.class);
	
	public static boolean dbSetup=false;
	public static void test() {
		LOGGER.warn("-------- PostgreSQL " + "JDBC Connection Testing ------------");
		String filePath=System.getenv("FILE_PATH");
		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			LOGGER.warn("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return;

		}

		LOGGER.warn("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;
		Statement stmt = null;
		try {
			String user = PropertiesCache.getInstance().getProperty("postgresUser");
			String url = PropertiesCache.getInstance().getProperty("url");
			String pwd = PropertiesCache.getInstance().getProperty("password");
			connection = DriverManager.getConnection(url, user, pwd);

			stmt = connection.createStatement();

			LOGGER.warn("Creating tables in given database...");

			File tableFile = new File(filePath+"\\Tables.sql");
			String tableFilestr = FileUtils.readFileToString(tableFile);
			stmt.executeUpdate(tableFilestr);
			LOGGER.warn("created  table in given database...");
			
			LOGGER.warn("inserting data in given database...");
			
			File dataFile = new File(filePath+"\\MasterData.sql");
			String dataFilestr = FileUtils.readFileToString(dataFile);
			stmt.executeUpdate(dataFilestr);

			dataFile = new File(filePath+"\\ProductData.sql");
			dataFilestr = FileUtils.readFileToString(dataFile);
			stmt.executeUpdate(dataFilestr);

			dataFile = new File(filePath+"\\Queries.sql");
			dataFilestr = FileUtils.readFileToString(dataFile);
			stmt.executeUpdate(dataFilestr);
			
			LOGGER.warn("inserted data in given database...");
		} catch (SQLException e) {

			LOGGER.warn("Connection Failed! Check output console");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (stmt != null) {
				try {
					dbSetup=true;
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					dbSetup=true;
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
