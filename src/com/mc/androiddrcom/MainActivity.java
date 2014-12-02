package com.mc.androiddrcom;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mc.httputil.HttpUtil;

public class MainActivity extends Activity {
	private static final int BITS_PER_UNIT = 8;//�Կ�������
	ProgressDialog progress;
	Button login = null;
	EditText username = null;
	EditText password = null;
	EditText yanzheng = null;
	ImageView tupian = null;
	public static HashMap<String, String> map;// ������ session�ļ���
	private static String usernameString = null;
	private static String passwordString = null;
	private static String serial = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		username = (EditText) findViewById(R.id.username);
		username.setText("02903105760");
		password = (EditText) findViewById(R.id.password);
		password.setText("19940123");
		yanzheng = (EditText) findViewById(R.id.zhengshu);
		tupian = (ImageView) findViewById(R.id.tupian);

		// Bitmap bitmap =
		// getBitmap("http://124.89.91.246/CreateImage?Image=aa&Rgb=255|0|0");
		GetPicUrlAndSession getPicUrlAndSession = new GetPicUrlAndSession();// ��ȡͼƬ
																			// ��ַ
																			// sesson
																			// sessionID
																			// ����ʾͼƬ
		getPicUrlAndSession.execute();

		login = (Button) findViewById(R.id.enter);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (map.get("cookieSessionID").equals("")
						|| map.get("sessionID").equals("")) {// ������
					Toast.makeText(getApplicationContext(), "����ͼƬ���»�ȡͼƬ��֤",
							1000).show();
				} else {
					usernameString = username.getText().toString().trim();
					passwordString = password.getText().toString().trim();
					serial = yanzheng.getText().toString().trim();

					LoginAsyncTask loginAsyncTask = new LoginAsyncTask();
					progress = new ProgressDialog(MainActivity.this);
					progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progress.setTitle("���Ե�");
					progress.setMessage("���ڵ�¼"); //
					progress.setIndeterminate(false); // ����ProgressDialog�Ƿ���԰��˻ؼ�ȡ��
														// progress.setCancelable(false);
					// ����ProgressDialog ��һ��Button
					progress.show();

					String[] params = new String[] { usernameString,
							passwordString, serial, map.get("cookieSessionID"),
							map.get("sessionID") };
					loginAsyncTask.execute(params);
				}

			}
		});

		// ���»�ȡͼƬ
		tupian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetPicUrlAndSession getPicUrlAndSession = new GetPicUrlAndSession();// ��ȡͼƬ
																					// ��ַ
																					// sesson
																					// sessionID
																					// ����ʾͼƬ
				getPicUrlAndSession.execute();
			}
		});
	}

	class LoginAsyncTask extends AsyncTask<String, Object, String> {
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = HttpUtil.queryStringForPost(HttpUtil.LOGIN_URL
					+ "?username=" + params[0] + "&password=" + params[1]
					+ "&serial=" + params[2] + "&session=" + params[3]
					+ "&sessionID=" + params[4]);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progress.cancel();
			System.out.println("result=" + result);
			if (result.equals("yanzhengerror")) {
				Toast.makeText(getApplicationContext(), "��֤ʧ��", 1000).show();
				GetPicUrlAndSession getPicUrlAndSession = new GetPicUrlAndSession();// ��ȡͼƬ
				// ��ַ
				// sesson
				// sessionID
				// ����ʾͼƬ
				getPicUrlAndSession.execute();
			} else if (result.equals("loginerror")) {// ��������˺Ŵ���
				Toast.makeText(getApplicationContext(), "�������", 1000).show();

			} else {// ������Ϣ
				Intent i = new Intent();
				i.setClass(getApplicationContext(), HomeActivity.class);
				Bundle b = new Bundle();
				b.putString("message", result);
				b.putString("username", usernameString);
				i.putExtras(b);
				startActivity(i);
				finish();
			}

		}
	}

	/**
	 * ��ȡ picURl��session
	 * 
	 * @author Administrator 2014-6-13
	 */
	class GetPicUrlAndSession extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = HttpUtil.queryStringForPost(HttpUtil.GET_PIC_URL);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				map = new HashMap<String, String>();
				map.put("sessionID", jsonObject.getString("sessionID"));// ��������м��ܵ��ַ���
				map.put("picurl", jsonObject.getString("picurl"));// ͼƬ��url
				System.out.println("ͼƬ��ַ:" + jsonObject.getString("picurl"));
				GetPicture getPicture = new GetPicture();// ��ʾͼƬ��֤
				getPicture.execute(new String[] { HttpUtil.PIC_URL });
				map.put("cookieSessionID",
						jsonObject.getString("cookieSessionID"));// ����Ҫʹ�õ�session

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	class GetPicture extends AsyncTask<String, Bitmap, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Bitmap bitmap = getBitmap(params[0]);
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			// TODO Auto-generated method stub
			super.onPostExecute(bitmap);
			tupian.setImageBitmap(bitmap);
		}

	}

	// ��ȡͼƬ
	private Bitmap getBitmap(String pictureUrl) {
		URL url = null;
		Bitmap bitmap = null;

		InputStream in = null;
		try {
			if (pictureUrl != null) {
				url = new URL(pictureUrl);
				HttpURLConnection httpURLConnection = (HttpURLConnection) url
						.openConnection();
				httpURLConnection.setConnectTimeout(6000);// ����ӳ�6000����
				httpURLConnection.setDoInput(true);// ���ӻ�ȡ������
				httpURLConnection.setUseCaches(true);// �û���
				in = httpURLConnection.getInputStream();
				bitmap = BitmapFactory.decodeStream(in);
			} else {
				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return bitmap;
	}
	
	private static int position(int idx){
		return 1 << (BITS_PER_UNIT -1 - (idx%BITS_PER_UNIT));
		
	}

}
