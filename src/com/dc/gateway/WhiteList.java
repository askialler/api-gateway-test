package com.dc.gateway;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;

import com.dc.gateway.test.APITest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.org.apache.bcel.internal.generic.JsrInstruction;

public class WhiteList {

	public static String ADD_WL_ADDR = "http://mscx-gateway.hanlnk.com:82/gateway-web-1.8.0/addWhiteList";
	private String apiUri;

	/**
	 * @return the apiUri
	 */
	public String getApiUri() {
		return apiUri;
	}

	/**
	 * @param apiUri
	 *            the apiUri to set
	 */
	public void setApiUri(String apiUri) {
		this.apiUri = apiUri;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String appName;
	private String ip;
	private String remark;

	public WhiteList(String apiUri, String ip) {
		this.apiUri = apiUri;
		this.ip = ip;
		String name = apiUri.substring(1).replaceAll("/", "splash");
		this.appName = name;
		this.remark = "chytest";

	}



	public static boolean addWhiteList(String apiUri, String ip) {

		WhiteList wl = new WhiteList("/chyres41/api4", "192.168.1.10");
//		System.out.println(wl.getAppName());
		execRequest(wl);
		return false;
	}

	private static void execRequest(WhiteList wl) {
		Gson gson = new Gson();
		ArrayList<WhiteList> list=new ArrayList<WhiteList>();
		list.add(wl);
//		gson.toJson(list);
//		JsonElement jsObj= gson.toJsonTree(wl);
		String jsonWL = gson.toJson(list);
		System.out.println(jsonWL);
		
		HttpPost post = new HttpPost(ADD_WL_ADDR);
		try {
//			StringEntity entity=new StringEntity(jsonWL);
			HttpEntity entity=MultipartEntityBuilder.create()
					.addTextBody("data", jsonWL).build();
//			post.setHeader("Content-Type", "multipart/form-data; boundary=\"----=_Part_13_1180664.1484720865654\"");
			post.setEntity(entity);
			HttpClient client=APITest.getClient();
			CloseableHttpResponse resp=(CloseableHttpResponse) client.execute(post);
			HttpEntity respEntity=resp.getEntity();
//			respEntity.writeTo(System.out);
			System.out.println(EntityUtils.toString(respEntity, "utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean addWhiteList(WhiteList wl) {

		execRequest(wl);
		return false;
	}
	
	
	public static void main(String[] args) {
		WhiteList wl1= new WhiteList("/chyres21/api1", "172.16.57.100");
		WhiteList wl2 = new WhiteList("/chyres22/api2", "172.16.57.100");
		WhiteList wl3 = new WhiteList("/chyres23/api3", "172.16.57.100");
		execRequest(wl1);
		execRequest(wl2);
		execRequest(wl3);
	}

}
