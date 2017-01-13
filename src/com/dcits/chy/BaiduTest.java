package com.dcits.chy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sun.net.util.URLUtil;

public class BaiduTest {

	public static void main(String[] args) throws URISyntaxException {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setDefaultMaxPerRoute(20);
		connManager.setMaxTotal(50);
		CloseableHttpClient client = HttpClients.custom()
				.setConnectionManager(connManager).build();
		String wd = "httpclient";
		String ie = "utf-8";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("wd", wd));
		params.add(new BasicNameValuePair("ie", ie));
		// URLEncoder.encode(s)
		URIBuilder uriBuilder = new URIBuilder();
		String url = uriBuilder.setScheme("http").setHost("www.baidu.com")
				.setPath("/s").setParameters(params).build().toString();
		System.out.println("request url: " + url);
		HttpGet get1 = new HttpGet(url);

		HttpClientContext context = HttpClientContext.create();

		ResponseHandler<String> handler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {

				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					return EntityUtils.toString(response.getEntity(), "utf-8");
				} else {
					throw new ClientProtocolException(
							"unexpected response status :" + status + "...");
				}

				// return null;
			}
		};

		try { // CloseableHttpResponse resp=client.execute(get1, context);
			String content = client.execute(get1, handler, context);
//			System.out.println(content);
			
			Document doc= Jsoup.parse(content);
			Elements elements=doc.select("h3.t>a");
			
			if(elements!=null){
				Iterator<Element> it=elements.iterator();
				while(it.hasNext()){
					Element ele=it.next();
					System.out.println(ele.text());
					System.out.println(ele.attr("href"));
				}
			}
			
			
		} catch (ClientProtocolException e) { //
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}

	}
}
