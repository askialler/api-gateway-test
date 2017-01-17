package com.dc.gateway;

import java.io.IOException;
import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.dc.gateway.exceptions.GetUserException;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class User {

	public static String USER_LINK = "http://mscx-uc-api.eastdc.cn:82/user/info/api/key.do?userId=";

	private String userId;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String apiKey;

	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey
	 *            the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	private String secretKey;

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey
	 *            the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	private String userName = "";

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User(String userId, String appkey, String secretKey) {
		this.userId = userId;
		this.apiKey = appkey;
		this.secretKey = secretKey;
	}

	public User() {
	}

	public static User getUser(String userId, HttpClient client) throws GetUserException{
		String address = USER_LINK + userId;
		HttpGet get = new HttpGet(address);
		String result = null;
		CloseableHttpResponse resp = null;
		try {
			resp = (CloseableHttpResponse) client.execute(get);
			String statusLine=resp.getStatusLine().toString();
			if (resp.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				result = EntityUtils.toString(resp.getEntity(), "utf-8");
				System.out.println(result);
			}else{
				throw new GetUserException("get user failed. http status line: "+ statusLine);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				resp.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Gson gson=new Gson();
		User user=null;
		
		JsonReader reader = new JsonReader(new StringReader(result));
		try {
			reader.beginObject();
			String strCode=null;
			String msg=null;
			while (reader.hasNext()) {

				String name = reader.nextName();
				if(name.equals("code")){
					strCode= reader.nextString();
//					System.out.println(strCode);
				}
				if(name.equals("message")){
					msg=reader.nextString();
//					System.out.println(msg);
				}
				if (name.equals("result")) {
					if(strCode.equals("999999")){
						reader.close();
						throw new GetUserException("get user failed,message: "+ msg);
					}
					user=gson.fromJson(reader, User.class);
					break;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

//		JsonReader reader = new JsonReader(new StringReader(result));
//		try {
//			reader.beginObject();
//			while (reader.hasNext()) {
//				String name = reader.nextName();
//				System.out.println(name + " " + name.length());
//				if (name.equals("result")) {
//					reader.beginObject();
//					while (reader.hasNext()) {
//						if (reader.nextName().equals("userId")) {
//							user.setUserId(reader.nextString());
//						}
//						if (reader.nextName().equals("apiKey")) {
//							user.setApiKey(reader.nextString());
//						}
//						if (reader.nextName().equals("secretKey")) {
//							user.setSecretKey(reader.nextString());
//						}
//						if (reader.nextName().equals("userName")) {
//							user.setUserName(reader.nextString());
//						}
//					}
//					reader.endObject();
//					break;
//				}
//				reader.nextString();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return user;

	}

	public static void main(String[] args) {
		HttpClient client = HttpClients.createDefault();
		User user = getUser("000001000002321", client);
		System.out.println(user.getApiKey());
	}

}
