package com.zxy.crawl.util;

import java.util.ArrayList;
import java.util.regex.Pattern;
/**
 * 存放正则表达式来过滤出需要的Url
 * @author tinytail
 *
 */
public class Regex {
	ArrayList<String> regex=new ArrayList<String>();
	
	public Regex(){
		
	}
	
	public Regex(String rule){
		addRule(rule);
	}
	
	public boolean isEmpty(){
		return regex.size()==0;
	}
	
	public void addRule(String rule){
		if(rule.equals("")||rule==null){
			System.out.println("添加正则表达式错误！");
			return;
		}
		regex.add(rule);
	}
	
	//判断str是否满足正则表达式
	public boolean satisfy(String str){
		if(isEmpty()){
			System.out.println("请添加至少一个正则表达式来过滤！");
			return false;
		}
		for(String pat : regex){
			if(Pattern.matches(pat, str)){
				return true;
			}
		}
		return false;
	}
	
	//指定Url是否满足某正则表达式
	public boolean matchUrl(String url,String regRule){
		return Pattern.matches(regRule, url);
	}
	
	public ArrayList<String> getRegex() {
		return regex;
	}

	public void setRegex(ArrayList<String> regex) {
		this.regex = regex;
	}
	
	
}




