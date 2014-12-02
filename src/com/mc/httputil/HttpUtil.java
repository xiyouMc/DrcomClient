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
 * @description �ǵ��޸Ĵ��룬���ͷ�������Ӧ��ʱ����Ҫ fall back ����
 */
public class HttpUtil {
	// ����URL
	private static final String BASE_IP = "http://222.24.63.101/Drcom/";// ����IP
	public static final String GET_PIC_URL = BASE_IP + "GetPic.jsp";// ��ȡͼƬ
	public static final String LOGIN_URL = BASE_IP + "LoginServlet.jsp";//��¼
	public static final String MAIN_URL = BASE_IP + "ServiceMain.jsp";// ���˵�
	public static final String CHANGE_PW_URL = BASE_IP + "SelfServiceChangePW";//�޸�����
	public static final String PIC_URL = BASE_IP  + "2.jpg";
	public static String CONNECT_EXCEPTION = "�����쳣��";

	// ���Get�������request
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}

	// ���Post�������request
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	// ������������Ӧ����response
	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// ������������Ӧ����response
	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// ����Post���󣬻����Ӧ��ѯ���
	public static String queryStringForPost(String url) {
		// ����url���HttpPost����
		// for test remove , if run server ,need fall back

		HttpPost request = HttpUtil.getHttpPost(url);
		System.out.println("request==========" + request);
		String result = null;
		// for test remove , if run server ,need fall back
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtil.getHttpResponse(request);

			System.out.println("response==========" + response);

			System.out
					.println("response.getStatusLine().getStatusCode()=========="
							+ response.getStatusLine().getStatusCode());
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("��Ӧ�ɹ�");
				System.out.println("response.getEntity()=========="
						+ response.getEntity());

				// �����Ӧ
				result = EntityUtils.toString(response.getEntity(), "utf-8");// ��ֹ��������
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

	// �����Ӧ��ѯ���
	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				// �����Ӧ
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

	// ����Get���󣬻����Ӧ��ѯ���
	public static String queryStringForGet(String url) {
		// ���HttpGet����
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			// �����Ӧ����
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// �ж��Ƿ�����ɹ�
			if (response.getStatusLine().getStatusCode() == 200) {
				// �����Ӧ
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
