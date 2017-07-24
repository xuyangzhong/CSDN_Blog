package com.zxy.crawl.model;

import java.util.ArrayList;

/**
 * 存放URL的队列
 * @author tinytail
 *
 */
public class Queue {
	
	//未爬取的Url
	ArrayList<String> nextUrl = new ArrayList<String>();
	//已经爬取的Url
	ArrayList<String> visitedUrl = new ArrayList<String>();
	
	//添加进未爬取队列
	public void addNextUrl(String url){
		if(url.equals("")||url==null){
			//System.out.println(url+": 该url为空，跳过...");
			return;
		}
		if(isVisited(url)){
			//System.out.println(url+": 该url已经被爬取，跳过...");
			return;
		}
		if(nextUrl.contains(url)){
			//System.out.println(url+": 该url已经在队列，跳过...");
			return;
		}
		nextUrl.add(url);
	}
	
	public void addNextUrl(ArrayList<String> urls){
		for(String url : urls){
			if(url.equals("")||url==null){
				//System.out.println(url+": 该url为空，跳过...");
				continue;
			}
			if(isVisited(url)){
				//System.out.println(url+": 该url已经被爬取，跳过...");
				continue;
			}
			if(nextUrl.contains(url)){
				//System.out.println(url+": 该url已经进入队列，跳过...");
				continue;
			}
			nextUrl.add(url);
		}
	}
	
	//添加进已爬取队列
	public void addVisitedUrl(String url){
		visitedUrl.add(url);
	}
	
	//检查是否是已经爬取的Url
	public boolean isVisited(String url){
		return visitedUrl.contains(url);
	}
	
	//未爬取队列出一个
	public String next(){
		if(nextUrl.size()==0){
			System.out.println("未爬取队列已空");
			return null;
		}
		return nextUrl.remove(0);
	}
	
	//判断未爬取队列是否为空
	public boolean isEmpty(){
		return nextUrl.size()==0;
	}
	
	//获得已爬取Url的数量
	public int getVisitedNum(){
		return visitedUrl.size();
	}
	
	//获得队列中Url数量
	public int getNextNum(){
		return nextUrl.size();
	}
}
