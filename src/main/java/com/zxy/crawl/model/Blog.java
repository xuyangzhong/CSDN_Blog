package com.zxy.crawl.model;

public class Blog {
	String title="";
	String url="";
	String content="";
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString(){
		return "title = \n"+title+"content = \n"+content+"url = \n"+url;
	}
}
