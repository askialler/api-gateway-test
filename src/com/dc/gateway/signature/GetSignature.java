package com.dc.gateway.signature;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GetSignature {

	public static final String SIGN_PREFIX = "dc";
	public static final String MSG_SPLITOR = "\n";

	/**
	 * 生成电子签名
	 * 
	 * @param appkey
	 *            公钥
	 * @param secretkey
	 *            私钥
	 * @param data
	 *            签名数据。由buildSignData方法生成。
	 * @return 签名字符串
	 */
	public static String buildSignature(String appkey, String secretkey, String data) {

		String encodeData = Base64.encode(HMACSHA1.encode(data, secretkey));
		String sign = String.format("%s:%s:%s", SIGN_PREFIX, appkey, encodeData);

		return sign;
	}

	public static String buildSignData(String userParams, String date) {

		try {
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			sFormat.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("The date is not valid!");
		}

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(userParams).append(MSG_SPLITOR).append(date).append(MSG_SPLITOR);

		return sbBuffer.toString();

	}

	public static void main(String[] args) {
		String appkey = "234567";
		String secretKey = "secretKey";
		String userParams = null;
		String date = "201701091111111";

		String data = buildSignData(userParams, date);
		String sign = buildSignature(appkey, secretKey, data);

		System.out.println(sign);
	}
}
