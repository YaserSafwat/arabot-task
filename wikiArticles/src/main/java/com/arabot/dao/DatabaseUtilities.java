package com.arabot.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.arabot.wikiArticles.ArticleBean;

public class DatabaseUtilities {

	private static String databaseIPAddress;
	private static String databasePortNumber;
	private static String databaseUserName;
	private static String databasePassword;
	private static String databaseName;

	static {

		Properties databasePropsFile = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("/src/main/webapp/WEB-INF/props//database.properties");

			databasePropsFile.load(input);

			databaseIPAddress = databasePropsFile.getProperty("databaseServerIPAddress");
			databasePortNumber = databasePropsFile.getProperty("DBPort");
			databaseUserName = databasePropsFile.getProperty("userName");
			databasePassword = databasePropsFile.getProperty("password");
			databaseName = databasePropsFile.getProperty("databaseName");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static Connection getConnection() {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection(getConnectionURLAsString());
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getConnectionURLAsString() {

		StringBuilder connectionUrl = new StringBuilder();
		connectionUrl.append("jdbc:sqlserver://");
		connectionUrl.append(databaseIPAddress);
		connectionUrl.append(":");
		connectionUrl.append(databasePortNumber);
		connectionUrl.append(";");
		connectionUrl.append("user=" + databaseUserName);
		connectionUrl.append(";");
		connectionUrl.append("password=" + databasePassword);
		connectionUrl.append(";");
		connectionUrl.append("databaseName=" + databaseName);

		return connectionUrl.toString();
	}

	public static void closeConnection(Connection con) {

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertArticle(ArticleBean article) throws SQLException {

		int articleId = getMaxArticlesId() + 1;
		if (articleId == 0) {
			articleId = 1;
		}
		String title = article.getTitle();
		String snippet = article.getSnippet().replace("'", "''");
		long size = article.getSize();
		long wordCount = article.getWordCount();

		String query = "insert into article values" + "(" + articleId + ", '" + title + "'," + size + "," + wordCount
				+ ", '" + snippet + "')";
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.execute();
		closeConnection(con);
	}

	public static int getMaxArticlesId() throws SQLException {

		String query = "select max(id) from article";
		String maxId = null;
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			if (rs.getString(1) == null) {
				return -1;
			} else {
				maxId = rs.getString(1).toString();
				return Integer.parseInt(maxId);
			}
		}
		closeConnection(con);
		return -1;
	}

}
