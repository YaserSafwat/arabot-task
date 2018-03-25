package com.arabot.wikiArticles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.arabot.dao.DatabaseUtilities;

@Path("")
public class ArticleSearchService {

	@GET
	@Path("articles")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArticleBean> fetchData(@QueryParam("snippet") String snippet, @QueryParam("title") String title) {

		String snippetToSearch = null;
		String titleToSearch = null;
		String query = null;

		if (snippet != null || snippet != "") {
			snippetToSearch = snippet;
			query = "select * from article where snippet = '" + snippetToSearch + "'";
		}
		if (title != null || title != "") {
			titleToSearch = title;
			query = "select * from article where title = '" + titleToSearch + "'";
		}
		if (title != null && title != "" && snippet != null && snippet != "") {
			snippetToSearch = snippet;
			titleToSearch = title;
			query = "select * from article where snippet = '" + snippetToSearch + "' and title = '" + titleToSearch
					+ "'";
		}

		List<ArticleBean> articles = new ArrayList<>();

		Connection con = DatabaseUtilities.getConnection();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArticleBean article = new ArticleBean();
				article.setSize(rs.getInt("size"));
				article.setSnippet(rs.getString("snippet"));
				article.setTitle(rs.getString("title"));
				article.setWordCount(rs.getInt("word_count"));
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				DatabaseUtilities.closeConnection(con);
			}
		}
		return articles;
	}

	@GET
	@Path("largest-article")
	@Produces(MediaType.APPLICATION_JSON)
	public ArticleBean getLargestArticle() {

		String query = "select * from article where word_count = (select max(word_count) from article)";
		Connection con = DatabaseUtilities.getConnection();
		ArticleBean largestArticle = new ArticleBean();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				largestArticle.setSize(rs.getInt("size"));
				largestArticle.setSnippet(rs.getString("snippet"));
				largestArticle.setTitle(rs.getString("title"));
				largestArticle.setWordCount(rs.getInt("word_count"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				DatabaseUtilities.closeConnection(con);
			}
		}
		return largestArticle;

	}

	@GET
	@Path("smallest-article")
	@Produces(MediaType.APPLICATION_JSON)
	public ArticleBean getSmallestArticle() {

		String query = "select * from article where word_count = (select min(word_count) from article)";
		Connection con = DatabaseUtilities.getConnection();
		ArticleBean smallestArticle = new ArticleBean();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				smallestArticle.setSize(rs.getInt("size"));
				smallestArticle.setSnippet(rs.getString("snippet"));
				smallestArticle.setTitle(rs.getString("title"));
				smallestArticle.setWordCount(rs.getInt("word_count"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				DatabaseUtilities.closeConnection(con);
			}
		}
		return smallestArticle;

	}

}
