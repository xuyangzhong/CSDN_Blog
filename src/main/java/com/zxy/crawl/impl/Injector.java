package com.zxy.crawl.impl;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import com.zxy.crawl.model.Queue;
/**
 * 管理新的url和旧的url的注射器
 * @author tinytail
 *
 */
public class Injector {
	Queue queue=new Queue();
	
	public void addUsefulUrl(String url){
		if(url==null||url.equals("")){
			System.out.println("注入新链接失败！");
			return;
		}
		queue.addNextUrl(url);
	}
	
	public void addUsefulUrl(ArrayList<String> urls){
		if(urls==null){
			System.out.println("注入新链接失败！");
			return;
		}
		queue.addNextUrl(urls);
	}
	
	public void addUselessUrl(String url){
		if(url==null||url.equals("")){
			System.out.println("注入旧链接失败！");
			return;
		}
		queue.addVisitedUrl(url);
	}
	
	public String getUrl(){
		String url=queue.next();
		System.out.println("next url is :"+url);
		return url;
	}
	
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	
	public int getUselessUrlNum(){
		return queue.getVisitedNum();
	}
	
	public int getUsefulUrlNum(){
		return queue.getNextNum();
	}
}
