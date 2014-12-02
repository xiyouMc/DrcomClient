package com.mc.androiddrcom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mc.httputil.HttpUtil;

public class ChangePW extends Activity {

	private EditText password_o;
	private EditText password_n;
	private EditText password_co;
	private Button enter;
	private String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_pw);
		init();
		Intent i =getIntent();
		username = i.getStringExtra("username");
		
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (password_o.getText().toString()!=null&password_n.getText().toString()!=null&password_co.getText().toString()!=null) {
					String password_n_str = password_n.getText().toString().trim();
					String password_cof_str = password_co.getText().toString().trim();
					if (password_n_str.equals(password_cof_str)) {
						ChangePWAsyncTask changePWAsyncTask = new ChangePWAsyncTask();
						changePWAsyncTask.execute(new String[]{username,password_o.getText().toString().trim(),password_n_str,MainActivity.map.get("cookieSessionID")});
					}else {
						Toast.makeText(getApplicationContext(), "两次密码不正确", 1000).show();
					}
				}
				
			}
		});
		
	}
	private void init() {
		// TODO Auto-generated method stub
		password_o = (EditText)findViewById(R.id.old);
		password_n = (EditText)findViewById(R.id.newpw);
		password_co = (EditText)findViewById(R.id.cof);
		enter = (Button)findViewById(R.id.enter);
	}
	class ChangePWAsyncTask extends AsyncTask<String, Object, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			String result = HttpUtil.queryStringForPost(HttpUtil.CHANGE_PW_URL
					+ "?username=" + params[0] + "&password_o=" + params[1]
					+ "&password_n=" + params[2] +"&session=" + params[3] );
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("result=" + result);
			if (result.equals("0")) {
				Toast.makeText(getApplicationContext(), "密码错误", 1000).show();
			}else if(result.equals("change_success")) {
				Toast.makeText(getApplicationContext(), "密码修改成功", 1000).show();
				finish();
			}
		}

	}
	
}
