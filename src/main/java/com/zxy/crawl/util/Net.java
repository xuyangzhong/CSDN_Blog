package com.zxy.crawl.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Net {
	String url="";
	String encoding="";
	static WebDriver driver=new HtmlUnitDriver();
	
	
	public Net(){
		
	}
	
	public Net(String url,String encoding){
		this.url=url;
		this.encoding=encoding;
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
	
	//判断Net是否准备就绪
	public boolean isOk(){
		if(url!=null&&encoding!=null){
			return true;
		}else{
			return false;
		}
	}

	//根据Url和encoding将要爬取的网站输入流放入字符缓冲区
	public String getHtmlResourceByURL(){
		StringBuffer buffer = new StringBuffer();
		
		URL urlObj=null;
		HttpURLConnection conn=null;
		InputStreamReader in=null;
		BufferedReader br=null;
		
		try {
			urlObj=new URL(url);
			conn=(HttpURLConnection)urlObj.openConnection();
			//conn=(HttpURLConnection)urlObj.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("163.125.16.132",8888) ));
			in=new InputStreamReader(conn.getInputStream(), encoding);
			if(in==null||in.equals("")){
				return null;
			}
			br=new BufferedReader(in);
			String temp=null;
			while((temp=br.readLine())!=null){
				buffer.append(temp+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接超时！");
		} finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("输入流关闭错误！");
				}
			}
		}
		return buffer.toString();
	}
	
	//将html中相对地址转化为绝对地址
	public synchronized void relativeToAbsolute(Document doc){
		doc.setBaseUri(url);
		doc.traverse(new NodeVisitor() {
			
			@Override
			public void tail(Node node, int i) {		
			}
			
			@Override
			public void head(Node node, int i) {	
				if(node instanceof Element){
                    Element tag=(Element) node;
                    if(tag.hasAttr("href")){
                        String absHref=tag.attr("abs:href");
                        tag.attr("href",absHref);
                    }
                    if(tag.hasAttr("src")){
                        String absSrc=tag.attr("abs:src");
                        tag.attr("src",absSrc);
                    }
                }
			}
		});
		
	}
	
	//下载图片到指定路径
	public void downloadImgs(String imgUrl,String filePath){
		File parent=new File(filePath);
		if(!parent.exists()){
			parent.mkdirs();
		}
		//获取图片名字，即截取图片路径最后一个/后面的内容  
        String fileName=imgUrl.substring(imgUrl.lastIndexOf("/")+1);  
//        System.out.println("fileName-->"+fileName);
        //获取文件的后缀名  
        String imgType = imgUrl.substring(imgUrl.lastIndexOf(".")+1);
//        System.out.println("imgType-->"+imgType);
		try{
			BufferedImage image=ImageIO.read(new URL(imgUrl));
			System.out.println(fileName+","+imgType+","+image);
			File file=new File(parent, fileName);
			System.out.println("已存入"+file.getAbsolutePath()+",文件为"+fileName+"."+imgType);
			ImageIO.write(image, imgType, file);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("下载图片错误，图片名字为"+fileName);
		}
	}
	
	public String getHtmlByPhantomJS(){
		System.setProperty("phantomjs.binary.path","E:/crawl/phantomjs/phantomjs.exe");
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
		PhantomJSDriver driver=new PhantomJSDriver(desiredCapabilities);
		driver.get(url);
		return driver.getPageSource();
	}
	
	public String getHtmlByUnitDriver(){
		//WebDriver driver=new HtmlUnitDriver();
		System.out.println("url in net = " + url);
		driver.get(url);
		return driver.getPageSource();
	}
	
	public synchronized String getHtmlByUnitDriver(String url){
		//WebDriver driver=new HtmlUnitDriver();
		System.out.println("url in net = " + url);
		driver.get(url);
		return driver.getPageSource();
	}
	
	/*
	public static void main(String[] args){
		String imgSrc=null;
		Net net=new Net();
		net.setUrl("https://www.iis.net/");
		net.setEncoding("utf-8");
		String html=net.getHtmlResourceByURL();
		System.out.println(html);
		Document doc=Jsoup.parse(html);
		net.relativeToAbsolute(doc);
		Elements ele=doc.select("img[src]");
		for(Element t : ele){
			imgSrc=t.attr("src");
			if(imgSrc.contains("?")){
				imgSrc=imgSrc.substring(0,imgSrc.indexOf("?"));
			}
			if(imgSrc!=null){
				System.out.println("下载图片地址："+imgSrc);
				net.downloadImgs(imgSrc, "F:\\imgs");
			}
		}
	}
	*/
}







