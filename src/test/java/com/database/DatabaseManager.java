package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.util.ConfigManager;
import com.api.util.EnvUtility;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
	private static final String DB_URL = EnvUtility.getValue("DB_URL");
	private static final String DB_USERNAME = EnvUtility.getValue("DB_USERNAME");
	private static final String DB_PASSWORD = EnvUtility.getValue("DB_PASSWORD");
	private static final int DB_MAXIMUMPOOLSIZE = Integer.parseInt(ConfigManager.getProperty("DB_MAXIMUMPOOLSIZE"));
	private static final int DB_MINIMUMIDLE = Integer.parseInt(ConfigManager.getProperty("DB_MINIMUMIDLE"));
	private static final int DB_CONNECTIONTIMEOUT_SEC = Integer.parseInt(ConfigManager.getProperty("DB_CONNECTIONTIMEOUT_SEC"));
	private static final int DB_IDLETIMEOUT_SEC = Integer.parseInt(ConfigManager.getProperty("DB_IDLETIMEOUT_SEC"));
	private static final int DB_MAXLIFETIME_MIN = Integer.parseInt(ConfigManager.getProperty("DB_MAXLIFETIME_MIN"));
	private static final String DB_POOLNAME = ConfigManager.getProperty("DB_POOLNAME");
	private static Connection connection;
	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;

	private DatabaseManager() {

	}

	private static void instantiatePool() {// Process one request at a time
		if (hikariDataSource == null) {
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {
					hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					
					hikariConfig.setMaximumPoolSize(DB_MAXIMUMPOOLSIZE);
					hikariConfig.setMinimumIdle(DB_MINIMUMIDLE);
					hikariConfig.setConnectionTimeout(DB_CONNECTIONTIMEOUT_SEC*1000);
					hikariConfig.setIdleTimeout(DB_IDLETIMEOUT_SEC*1000);
					hikariConfig.setMaxLifetime(DB_MAXLIFETIME_MIN*60*1000);
					hikariConfig.setPoolName(DB_POOLNAME);
					hikariDataSource = new HikariDataSource(hikariConfig);
				}
			}

		}
	}
	
	public static Connection getConnection() throws SQLException {
		if (hikariDataSource == null) {
			instantiatePool();
		}else if(hikariDataSource.isClosed()) {
			throw new SQLException("Hikari Data Source is closed");
		}
		connection = hikariDataSource.getConnection();
		return connection;
	}

}
