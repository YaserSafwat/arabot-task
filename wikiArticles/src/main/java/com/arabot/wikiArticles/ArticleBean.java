package com.arabot.wikiArticles;

public class ArticleBean {

	private String title;
	private long size;
	private long wordCount;
	private String snippet;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getWordCount() {
		return wordCount;
	}

	public void setWordCount(long wordCount) {
		this.wordCount = wordCount;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	@Override
	public String toString() {
		return "ArticleBean [title=" + title + ", size=" + size + ", wordCount=" + wordCount + ", snippet=" + snippet
				+ "]";
	}

}
