package com.zxy.crawl.impl;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.zxy.crawl.dao.Dao;
import com.zxy.crawl.model.Blog;
import com.zxy.crawl.util.Net;
import com.zxy.crawl.util.Regex;
/**
 * 处理器，发现新的Url，抛弃旧的Url
 * @author tinytail
 *
 */
public class Handle implements Runnable{
	String html="";
	String url="";
	String encoding="utf-8";//网页默认编码为UTF-8
	Net net=null;
	Regex regex=null;
	static Injector inj=null;
	//static ThreadLocal<Injector> inj = new ThreadLocal<>();
	static WebDriver driver=new HtmlUnitDriver();
	
	public Handle(Regex regex){
		this.regex=regex;
	}
	
	public synchronized void initNet(){
		System.out.println("************** url 2 = "+url);
		net.setUrl(url);
		net.setEncoding(encoding);
	}
	
	public void addRegex(String rule){
		if(rule.equals("")||rule==null){
			System.out.println("处理器中添加正则失败！");
			return;
		}
		regex.addRule(rule);
	}
	
	//处理的主要函数
	@Override
	public void run(){
		go();
	}
	
	public synchronized void go(){
		url=inj.getUrl();
		System.out.println("************** url 1 = "+url);
		net=new Net();
//		initNet();
		net.setUrl(url);
		System.out.println("************** url 2 = "+url);
		net.setEncoding(encoding);
		System.out.println("************** url 2.1 = "+url);
		ArrayList<String> tempUrl=new ArrayList<String>();
		System.out.println("************** url 2.2 = "+url);
		if(!net.isOk()){
			System.out.println("************** url 2.3 = "+url);
			System.out.println("queue已空！");
			System.out.println("************** url 2.4 = "+url);
			return;
		}
		System.out.println("************** url 2.5 = "+url);
		driver.get(url);
		System.out.println("************** url 2.6 = "+url);
		html=driver.getPageSource();
		//html=net.getHtmlByUnitDriver(url);
		System.out.println("************** url 3 = "+url);
		if(html==null){
			return;
		}
		Document doc=Jsoup.parse(html);
		net.relativeToAbsolute(doc);
		Elements eles=doc.select("a[href]");
		for(Element ele : eles){
			String str=ele.attr("href");
			if(regex.satisfy(str)){
				tempUrl.add(str);
			}
		}
		System.out.println("************** url 4 = "+url);
		if (Pattern.matches("http://blog.csdn.net/.*?/article/details/[0-9]*",url)) {
			Blog blog = new Blog();
			String title = doc.select("div.article_title > h1").text();
			if(title==null||title.equals("")){
				title = doc.select("h3.list_c_t").text();
				if(title==null||title.equals("")){
					title="";
				}
			}
			blog.setTitle(title);
			
			String content = doc.select("div.markdown_views").text();
			
			if(content==null||content.equals("")){
				content = doc.select("div.article_content").text();
				if(content==null||content.equals("")){
					content="";
				}
			}
			blog.setContent(content);
			System.out.println("************** url 5 = "+url);
			blog.setUrl(url);
			if(url.equals("http://blog.csdn.net/growing_tree/article/details/68958109")){
				System.out.println("_________________________________________________");
				System.out.println("************** title = "+title);
				System.out.println("************** url 6 = "+url);
			}
//			Dao dao=new Dao();
//			dao.addTo(blog);
		}
		inj.addUsefulUrl(tempUrl);
		inj.addUselessUrl(url);
		
	}
	
	

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Net getNet() {
		return net;
	}

	public void setNet(Net net) {
		this.net = net;
	}

	public static Injector getInj() {
		return inj;
	}

	public static void setInj(Injector inj) {
		Handle.inj = inj;
	}

	public Regex getRegex() {
		return regex;
	}

	public void setRegex(Regex regex) {
		this.regex = regex;
	}
	
}



