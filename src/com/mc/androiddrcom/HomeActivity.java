package com.mc.androiddrcom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private TextView showMessage;
	private LinearLayout changePW;
	private LinearLayout ud_button;// 详细记录
	private LinearLayout paid_button;// 缴费记录
	private LinearLayout zhanghaoxvfei;//账号续费
	private LinearLayout about;//关于
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		Intent i = getIntent();
		username = i.getStringExtra("username");
		this.setTitle(username);
		this.setTitleColor(Color.BLACK);
		showMessage.setText("");
		showMessage.setText(i.getStringExtra("message"));
		changePW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ChangePW.class);
				Bundle b = new Bundle();
				b.putString("username", username);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
		ud_button.setOnClickListener(new click());
		paid_button.setOnClickListener(new click());
		zhanghaoxvfei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://upay.10010.com/npfweb/npffixweb/fixed_telephone_recharge_fill.htm"));    
		        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");    
		        startActivity(it); 
			}
		});
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getApplicationContext(), AboutActivity.class);
				startActivity(i);
			}
		});
	}

	class click implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent();
			i.setClass(getApplicationContext(), UDAndPAIDActivity.class);
			Bundle b = new Bundle();
			switch (v.getId()) {
			case R.id.ud:
                b.putString("displaymode", "UD");
				break;
			case R.id.paid:
				 b.putString("displaymode", "PAID");
				break;
			default:
				break;
			}
			i.putExtras(b);
			startActivity(i);
		}

	}

	private void init() {
		// TODO Auto-generated method stub
		changePW = (LinearLayout) findViewById(R.id.changePW);
		showMessage = (TextView) findViewById(R.id.gerenxinxi);
		ud_button = (LinearLayout) findViewById(R.id.ud);
		paid_button = (LinearLayout) findViewById(R.id.paid);
		zhanghaoxvfei = (LinearLayout)findViewById(R.id.zhanghaoxvfei);
		about = (LinearLayout)findViewById(R.id.about);
	}

}
