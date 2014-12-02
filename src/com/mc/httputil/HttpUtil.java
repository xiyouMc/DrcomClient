package com.mc.httputil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author Administrator
 * @description 记得修改代码，当和服务器响应的时候需要 fall back 代码
 */
public class HttpUtil {
	// 基础URL
	private static final String BASE_IP = "http://222.24.63.101/Drcom/";// 基础IP
	public static final String GET_PIC_URL = BASE_IP + "GetPic.jsp";// 获取图片
	public static final String LOGIN_URL = BASE_IP + "LoginServlet.jsp";//登录
	public static final String MAIN_URL = BASE_IP + "ServiceMain.jsp";// 主菜单
	public static final String CHANGE_PW_URL = BASE_IP + "SelfServiceChangePW";//修改密码
	public static final String PIC_URL = BASE_IP  + "2.jpg";
	public static String CONNECT_EXCEPTION = "网络异常！";

	// 获得Get请求对象request
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}

	// 获得Post请求对象request
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// 发送Post请求，获得响应查询结果
	public static String queryStringForPost(String url) {
		// 根据url获得HttpPost对象
		// for test remove , if run server ,need fall back

		HttpPost request = HttpUtil.getHttpPost(url);
		System.out.println("request==========" + request);
		String result = null;
		// for test remove , if run server ,need fall back
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);

			System.out.println("response==========" + response);

			System.out
					.println("response.getStatusLine().getStatusCode()=========="
							+ response.getStatusLine().getStatusCode());
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("响应成功");
				System.out.println("response.getEntity()=========="
						+ response.getEntity());

				// 获得响应
				result = EntityUtils.toString(response.getEntity(), "utf-8");// 防止中文乱码
				// result=new String(result.getBytes("ISO-8859-1"),"utf-8"); //
				System.out.println("result==========" + result);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = HttpUtil.CONNECT_EXCEPTION;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = HttpUtil.CONNECT_EXCEPTION;
			return result;
		}
		return null;

	}

	// 获得响应查询结果
	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = HttpUtil.CONNECT_EXCEPTION;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = HttpUtil.CONNECT_EXCEPTION;
			return result;
		}
		return null;
	}

	// 发送Get请求，获得响应查询结果
	public static String queryStringForGet(String url) {
		// 获得HttpGet对象
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = HttpUtil.CONNECT_EXCEPTION;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = HttpUtil.CONNECT_EXCEPTION;
			return result;
		}
		return null;
	}
}
