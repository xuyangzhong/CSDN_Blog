package com.zxy.crawl.dao;


import java.sql.*;

import com.zxy.crawl.model.Blog;

public class Dao {
 	static String driver="com.mysql.jdbc.Driver";
 	static String url="jdbc:mysql://localhost:3306/crawl?useSSL=false";
 	static String user="root";
 	static String password="1111";
 	
 	
 	String title="";
 	String nurl="";
 	String content="";
 	
	public synchronized boolean addTo(Blog blog){
		boolean result = true;
		title=blog.getTitle();
		nurl=blog.getUrl();
		content=blog.getContent();
		String sql="insert into csdn_blog (title,content,url) value(?,?,?)";
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url, user, password);
			ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setString(3, nurl);
			ps.executeUpdate();
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		} finally {
			if(ps!=null){
				try {
					ps.close();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return result;
		
	}
	
	
}






