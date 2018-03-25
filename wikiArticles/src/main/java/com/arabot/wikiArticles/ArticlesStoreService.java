package com.arabot.wikiArticles;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.arabot.dao.DatabaseUtilities;


@Path("add-data")
public class ArticlesStoreService {

	@GET
	public void addDataFromWiki() {

		ArticlesStoreService articlesLookupService = new ArticlesStoreService();
		List<ArticleBean> list = articlesLookupService.getArticles();
		articlesLookupService.addListOfArticlesToTheDatabase(list);
	}

	public void addListOfArticlesToTheDatabase(List<ArticleBean> list) {

		for (ArticleBean article : list) {

			try {
				DatabaseUtilities.insertArticle(article);
			} catch (SQLException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	public List<ArticleBean> getArticles() {

		try {
			ArticlesStoreService articlesLookupService = new ArticlesStoreService();
			String JsonResponseAsString = articlesLookupService.doRequestAndGetResponseAsString();

			JSONObject jsonObject = (JSONObject) new JSONObject(JsonResponseAsString);
			return articlesLookupService.getListOfArticlesAsJavaBeanObejcts(jsonObject);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String doRequestAndGetResponseAsString() throws Exception {

		URL AmmanJordanUrl = new URL(
				"https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=Amman%20Jordan&format=json");

		HttpURLConnection connection = null;
		BufferedReader br = null;
		try {
			connection = (HttpURLConnection) AmmanJordanUrl.openConnection();
			connection.setDoOutput(true);

			InputStream is = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			return response.toString();
		} catch (Exception e) {
			throw new Exception("Faile to send request due to" + e.getMessage());
		} finally {
			if (br != null) {
				br.close();
			}
		}

	}

	public List<ArticleBean> getListOfArticlesAsJavaBeanObejcts(JSONObject json) throws JSONException {

		JSONObject queryObject = (JSONObject) json.get("query");
		JSONArray searchArray = queryObject.getJSONArray("search");
		List<ArticleBean> listOfArticlesAsJavaBeanObejcts = new ArrayList<>();

		for ( int i=0; i<searchArray.length();i++) {
			JSONObject articleObject = searchArray.getJSONObject(i);
			JSONObject articleAsJsonObj = (JSONObject) articleObject;
			ArticleBean articleAsJavaObj = new ArticleBean();
			articleAsJavaObj.setTitle(articleAsJsonObj.getString("title"));
			articleAsJavaObj.setSize(articleAsJsonObj.getLong("size"));
			articleAsJavaObj.setWordCount(articleAsJsonObj.getLong("wordcount"));
			articleAsJavaObj.setSnippet(articleAsJsonObj.getString("snippet"));

			listOfArticlesAsJavaBeanObejcts.add(articleAsJavaObj);

		}

		return listOfArticlesAsJavaBeanObejcts;

	}
}
