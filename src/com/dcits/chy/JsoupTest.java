package com.dcits.chy;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void main(String[] args) {
		String url = "http://bbs.55bbs.com/forum-33-2-dateline.html";
		// String str =
		JsoupTest test = new JsoupTest();
		try {

			test.parse(url);

			// test.parseString(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse(String urlStr) throws IOException {
		String baseUrl="http://bbs.55bbs.com/";
		// Jsoup.connect(urlStr).userAgent("Mozilla").get();
		Connection conn = Jsoup.connect(urlStr).userAgent("Mozilla").timeout(10000);
		Document doc = conn.get();
		// System.out.println(conn.request().header("User-Agent"));
		System.out.println(doc.title());
//		System.out.println("****************************************************************************************************");
//		Elements elements = doc.getElementsByTag("div.t_msgfont");
//		System.out.println("isEmpty:"+elements.isEmpty());
//		Element table=doc.getElementById("forum_33");
		Elements table=doc.select("#forum_33");
//		System.out.println(table.size());
		Elements list=table.select("tbody span[id^=thread]>a");
		//a[href^=thread-]
		long beginTime=System.currentTimeMillis();
		for(int i=0;i<5;i++){
			
			new Thread(new BBSThreadRunnable(list.get(i))).start();
//			System.out.println("****************************************************************************************************");
//			String threadUrl=list.get(i).attr("href");
//			Document thread=Jsoup.connect(baseUrl+threadUrl).get();
//			System.out.println("thread_title: "+thread.title());
//			Elements links = thread.select("div.t_msgfont");
//			for(Element ele:links){
//				System.out.println(ele.text());
//			}
		}
		long endTime=System.currentTimeMillis();
		System.out.println("used time"+(endTime-beginTime));
//		for(Element el:list){
////			System.out.println(el);
////			System.out.println(el.attr("href"));
//			System.out.println("****************************************************************************************************");
//			String threadUrl=el.attr("href");
//			Document thread=conn.url(baseUrl+threadUrl).get();
//			System.out.println(thread.title());
//			Elements links = thread.select("div.t_msgfont");
////			System.out.println(links.last());
////			String[] strs=links.last().text().split(":");
////			System.out.println(strs[strs.length-1]);
//			for(Element ele:links){
//				System.out.println(ele.text());
//			}
//		}
		

		// for(Element ele:elements){
		// if(ele.hasAttr("href")){
		// System.out.println(ele.attr("href"));
		// }
		// System.out.println(ele.text());
		// }
		// Element element=doc.getElementById("password1");
//		System.out.println(links);
//		System.out.println("*********************************************************************************");

		// String unsafe =
		// "<p><a href='http://www.oschina.net/' onclick='stealCookies()'>开源中国社区</a></p>";
		// String safe = Jsoup.clean(unsafe, Whitelist.basic());
		// System.out.println("safe: "+safe);
	}

	public void parseString(String str) {
		Document doc = Jsoup.parse(str);
		System.out.println(doc.title());
		System.out.println(doc.outerHtml());
	}
	
	class BBSThreadRunnable implements Runnable {

		private Element element=null;
		private String baseUrl="http://bbs.55bbs.com/";
		public BBSThreadRunnable(Element element) {
			this.element=element;
		}
		
		@Override
		public void run() {
			System.out.println("****************************************************************************************************");
			System.out.println("begin time:"+System.currentTimeMillis());

			String threadUrl=element.attr("href");
			Document thread=null;
			try {
				thread = Jsoup.connect(baseUrl+threadUrl).get();
				System.out.println("thread_title: "+thread.title());
				Elements links = thread.select("div.t_msgfont");
//				System.out.println(links.last());
//				String[] strs=links.last().text().split(":");
//				System.out.println(strs[strs.length-1]);
				for(Element ele:links){
					System.out.println(ele.text());
				}
				System.out.println("end time: "+System.currentTimeMillis());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}



