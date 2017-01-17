package com.dc.gateway.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
//import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import com.dc.gateway.Resource;
import com.dc.gateway.User;
import com.dc.gateway.exceptions.GetUserException;
import com.dc.gateway.signature.GetSignature;

public class APITest {

	private User user;
	private Resource resource;

	public static String GW_ADDR = "http://api.areacode.com:48011";
	// public static String GW_ADDR="http://172.16.49.70:8080";
	// public static String GW_ADDR="http://www.baidu.com";

	public static CloseableHttpClient CLIENT = HttpClients.createDefault();
	private String apiContext = "";

	private String authorization = "";
	private String userdate = "";

	public APITest(User user, Resource resource, String apiContext) {
		this.user = user;
		this.resource = resource;
		this.apiContext = apiContext;
	}

	/**
	 * @return the apiAddr
	 */
	public String getApiContext() {
		return apiContext;
	}

	/**
	 * @param apiContext
	 *            the apiAddr to set
	 */
	public void setApiContext(String apiContext) {
		this.apiContext = apiContext;
	}

	public String getSecretKey() {
		return user.getSecretKey();
	}

	public String getUserId() {
		return user.getUserId();
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	/**
	 * @return the appkey
	 */
	public String getAppkey() {
		return user.getApiKey();
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resource.getResourceId();
	}

	/**
	 * @return the userdate
	 */
	public String getUserdate() {
		return userdate;
	}

	/**
	 * @param userdate
	 *            the userdate to set
	 */
	public void setUserdate(String userdate) {
		this.userdate = userdate;
	}

	/**
	 * ¼ÆËãÊý×ÖÇ©Ãû
	 * 
	 * @param appkey
	 * @param secretKey
	 * @param userdate
	 * @return
	 */
	public static String genAuth(String appkey, String secretKey, String userdate) {
		String userParams = null;
		String data = GetSignature.buildSignData(userParams, userdate);
		String sign = GetSignature.buildSignature(appkey, secretKey, data);
		return sign;
	}

	public void testAPI() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String userdate = sdf.format(new Date());

		System.out.println("userdate: " + userdate);

		String address = APITest.GW_ADDR + getApiContext();

		setUserdate(userdate);

		String authorization = genAuth(getAppkey(), getSecretKey(), userdate);
		setAuthorization(authorization);
		System.out.println("authorization: " + authorization);

		HttpPost post = new HttpPost(address);

		System.out.println("request address:" + address);

		// prepare headers
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("userId", getUserId()));
		headers.add(new BasicHeader("user-date", getUserdate()));
		headers.add(new BasicHeader("resourceId", getResourceId()));
		headers.add(new BasicHeader("authorization", getAuthorization()));
		post.setHeaders(headers.toArray(new Header[headers.size()]));
		CloseableHttpResponse resp = null;
		try {

			resp = APITest.CLIENT.execute(post);
			// HeaderIterator iter = resp.headerIterator();
			// while (iter.hasNext()) {
			// System.out.println(iter.next());
			// }

			HttpEntity entity = resp.getEntity();

			// System.out.println(entity.getContentType().toString());
			// System.out.println(entity.getContentEncoding());
			// System.out.println(entity.getContentLength());
			System.out.println(resp.getStatusLine().toString());

			System.out.println("http request successful.");

			char[] buf = new char[8192];

			int tempoff = 0;
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
			while (-1 != (tempoff = reader.read(buf, 0, 1024))) {
				sb.append(buf, 0, tempoff);
			}

			System.out.println(sb.toString());

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
	}

	public static void main(String[] args) {

		// User user=new User("000001000002265", "2C753112E5B12F3A",
		// "FcQ6wmOXlG7UUEzrKKBEl6XH9FjmeTGF", "2C753112E5B12F3A");
		User user = null;
		try {
			user = User.getUser("000001000002321", APITest.CLIENT);
			Resource resource = new Resource("01", "12001");
			APITest api = new APITest(user, resource, "/chyres21/api1");

			api.testAPI();
//			for (int i = 0; i < 2; i++) {
//				api.testAPI();
//			}

		} catch (GetUserException gue) {
			gue.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
