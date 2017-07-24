package com.zxy.crawl.impl;

import com.zxy.crawl.util.Regex;

public class MyCrawl {
	public static void main(String[]args){
		Injector inj=new Injector();
		inj.addUsefulUrl("http://blog.csdn.net/");
//		inj.addUsefulUrl("http://blog.csdn.net/growing_tree/article/details/68958109");
		Regex reg=new Regex();
		reg.addRule("http://blog.csdn.net/.*?/article/details/[0-9]*");
		reg.addRule("http://blog.csdn.net/.*?page=[0-9]*");
		Handle.setInj(inj);
		while(!inj.isEmpty()&&inj.getUselessUrlNum()<=4000){
			System.out.println("-----------------------------");
			System.out.println("队列中等待爬取Url数量:"+inj.getUsefulUrlNum());
			System.out.println("已经完成爬取的Url数量:"+inj.getUselessUrlNum());
			System.out.println("-----------------------------");
			
			Thread []t=new Thread[1];
			for(int i=0;i<t.length;i++){
				Handle handle=new Handle(reg);
				t[i]=new Thread(handle);
				t[i].start();
				try {				
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
			//留给主程序5秒钟时间，防止上列第一个线程执行时inj已空立即退出的情况
			try {				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			
		}
//		Handle.setInj(inj);
//		Handle handle=new Handle(reg);
//		Thread t=new Thread(handle);
//		t.start();
//		
//		
		System.out.println("------------end!-------------");
	}
}







