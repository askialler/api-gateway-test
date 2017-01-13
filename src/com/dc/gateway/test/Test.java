package com.dc.gateway.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		Date d1=new Date();
		System.out.println(d1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str=sdf.format(d1);
		System.out.println(str);
		
		String a="/chy/aaa";
		System.out.println(a.substring(1).replaceAll("/", "slash"));
	}
}
