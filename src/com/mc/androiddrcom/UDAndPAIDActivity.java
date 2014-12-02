package com.mc.androiddrcom;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

import com.mc.httputil.HttpUtil;

public class UDAndPAIDActivity extends Activity {

	private TextView show_message,tittle;
	private String displaymode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ud_paid);
		init();
		Intent i = getIntent();
		displaymode = i.getStringExtra("displaymode");
		if (displaymode.equals("UD")) {
			tittle.setText("账号信息");
		}else {
			tittle.setText("缴费记录");
		}
		GetAsyncTask getAsyncTask = new GetAsyncTask();
		getAsyncTask.execute(new String[]{displaymode,MainActivity.map.get("cookieSessionID")});
	}

	private void init() {
		// TODO Auto-generated method stub
		show_message = (TextView)findViewById(R.id.message);
		tittle = (TextView)findViewById(R.id.tittle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.udand_paid, menu);
		return true;
	}
	class GetAsyncTask extends AsyncTask<String, Object, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			String result = HttpUtil.queryStringForPost(HttpUtil.MAIN_URL
					+ "?displaymode=" + params[0]  +"&session=" + params[1] );
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("result=" + result);
			show_message.setText(result);
		}

	}
}
