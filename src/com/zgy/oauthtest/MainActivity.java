package com.zgy.oauthtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.zgy.oauthtest.util.TimeUtil;
import com.zgy.oauthtest.util.UtilRenRen;
import com.zgy.oauthtest.util.UtilSina;
import com.zgy.oauthtest.util.UtilTecentWeiBo;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnSinaAuth;
	private Button btnSinaUpdate;
	private Button btnSinaUnAuth;
	private String sinaToken = "";

	private Button btnRenRenAuth;
	private Button btnRenRenUnAuth;
	private Button btnRenRenUpdate;
	private String renrenToken = "";

	private Button btnTencentWeiBoAuth;
	private Button btnTencentWeiBoUpdate;
	private Button btnTencentWeiboUnAuth;
	private String tencentWeiboToken = "";
	private String tencentWeiboOpenid = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnSinaAuth = (Button) findViewById(R.id.btn_sina_auth);
		btnSinaUnAuth = (Button) findViewById(R.id.btn_sina_unauth);
		btnSinaUpdate = (Button) findViewById(R.id.btn_sina_update);
		btnRenRenAuth = (Button) findViewById(R.id.btn_renren_auth);
		btnRenRenUnAuth = (Button) findViewById(R.id.btn_renren_unauth);
		btnRenRenUpdate = (Button) findViewById(R.id.btn_renren_update);
		btnTencentWeiBoAuth = (Button) findViewById(R.id.btn_tencentweibo_auth);
		btnTencentWeiboUnAuth = (Button) findViewById(R.id.btn_tencentweibo_unauth);
		btnTencentWeiBoUpdate = (Button) findViewById(R.id.btn_tencentweibo_update);

		btnSinaAuth.setOnClickListener(this);
		btnSinaUnAuth.setOnClickListener(this);
		btnSinaUpdate.setOnClickListener(this);

		btnRenRenAuth.setOnClickListener(this);
		btnRenRenUnAuth.setOnClickListener(this);
		btnRenRenUpdate.setOnClickListener(this);

		btnTencentWeiBoAuth.setOnClickListener(this);
		btnTencentWeiboUnAuth.setOnClickListener(this);
		btnTencentWeiBoUpdate.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();

		// ProgressDialog mpDialog = ProgressDialog.show(MainActivity.this, null, "正在检查绑定状态，请等待...", true);
		// mpDialog.setCancelable(false);
		// mpDialog.show();

		checkSinaState();
		checkRenRenState();
		checkTecentWeiboState();
		// mpDialog.cancel();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sina_auth:
			Intent intent = new Intent();
			Bundle extras = new Bundle();
			extras.putString("url", ConfigConstants.Sina_Authorize2);
			extras.putInt("platform", ConfigConstants.PLATFORM_SINA);
			intent.setClass(MainActivity.this, AuthActivity.class);
			intent.putExtras(extras);
			startActivity(intent);
			break;
		case R.id.btn_sina_update:
			if (UtilSina.updateOneStatues(sinaToken, "Test From Sina API!" + TimeUtil.longToDateTimeString(System.currentTimeMillis()))) {
				Toast.makeText(MainActivity.this, "发布成功!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MainActivity.this, "发布失败!", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_sina_unauth:
			if (UtilSina.disableToken(sinaToken)) {
				checkSinaState();
			} else {
				Toast.makeText(MainActivity.this, "解绑失败!", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.btn_renren_auth:
			Intent intent2 = new Intent();
			Bundle extras2 = new Bundle();
			extras2.putString("url", ConfigConstants.Renren_Authorize2);
			extras2.putInt("platform", ConfigConstants.PLATFORM_RENREN);
			intent2.setClass(MainActivity.this, AuthActivity.class);
			intent2.putExtras(extras2);
			startActivity(intent2);
			break;
		case R.id.btn_renren_update:
			if (UtilRenRen.updateOneStatues(renrenToken, "Test From Ren Ren API!" + TimeUtil.longToDateTimeString(System.currentTimeMillis()))) {
				Toast.makeText(MainActivity.this, "发布成功!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MainActivity.this, "发布失败!", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_renren_unauth:
			if (UtilRenRen.disableToken(MainActivity.this)) {
				checkRenRenState();
			} else {
				Toast.makeText(MainActivity.this, "解绑失败!", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.btn_tencentweibo_auth:
			// 认证方式详见： http://wiki.open.t.qq.com/index.php/OAuth2.0%E9%89%B4%E6%9D%83
			Intent intent3 = new Intent();
			Bundle extras3 = new Bundle();
			extras3.putString("url", ConfigConstants.QQWEIBO_Authorize_2);
			extras3.putInt("platform", ConfigConstants.PLATFORM_QQWEIBO);
			intent3.setClass(MainActivity.this, AuthActivity.class);
			intent3.putExtras(extras3);
			startActivity(intent3);

			break;
		case R.id.btn_tencentweibo_update:
			UtilTecentWeiBo.updateOneStatues(getApplicationContext(), tencentWeiboToken, tencentWeiboOpenid, "Test from weibo API!" + TimeUtil.longToDateTimeString(System.currentTimeMillis()));
			break;
		case R.id.btn_tencentweibo_unauth:
			if (UtilTecentWeiBo.disableToken(getApplicationContext(), tencentWeiboToken, tencentWeiboOpenid)) {
				checkTecentWeiboState();
			} else {
				Toast.makeText(MainActivity.this, "解绑失败!", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}

	}

	/**
	 * 判断新浪账号的授权状态并控制ui显示
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	private void checkSinaState() {
		btnSinaAuth.setEnabled(true);
		btnSinaUpdate.setEnabled(false);
		btnSinaUnAuth.setEnabled(false);
		SharedPreferences sp = getSharedPreferences(ConfigConstants.PREF_FILE, Activity.MODE_PRIVATE);
		if (sp.contains(ConfigConstants.KEY_SINA_TOKEN)) {
			sinaToken = sp.getString(ConfigConstants.KEY_SINA_TOKEN, "");
			if (UtilSina.isTokenEnabled(sinaToken)) {
				// token有效
				btnSinaAuth.setEnabled(false);
				btnSinaUpdate.setEnabled(true);
				btnSinaUnAuth.setEnabled(true);
			}
		} else {
			Log.e("", ConfigConstants.KEY_SINA_TOKEN + " sharepreferences not saved!!");
		}
	}

	/**
	 * 判断人人账号的授权状态并控制ui显示
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	private void checkRenRenState() {
		btnRenRenAuth.setEnabled(true);
		btnRenRenUpdate.setEnabled(false);
		btnRenRenUnAuth.setEnabled(false);
		SharedPreferences sp = getSharedPreferences(ConfigConstants.PREF_FILE, Activity.MODE_PRIVATE);
		if (sp.contains(ConfigConstants.KEY_RENREN_TOKEN)) {
			renrenToken = sp.getString(ConfigConstants.KEY_RENREN_TOKEN, "");
			if (UtilRenRen.isTokenEnabled(renrenToken)) {
				// token有效
				btnRenRenAuth.setEnabled(false);
				btnRenRenUpdate.setEnabled(true);
				btnRenRenUnAuth.setEnabled(true);
			}
		} else {
			Log.e("", ConfigConstants.KEY_RENREN_TOKEN + " sharepreferences not saved!!");
		}
	}

	/**
	 * 判断腾讯微博账号的授权状态并控制ui显示
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	private void checkTecentWeiboState() {
		btnTencentWeiBoAuth.setEnabled(true);
		btnTencentWeiBoUpdate.setEnabled(false);
		btnTencentWeiboUnAuth.setEnabled(false);
		SharedPreferences sp = getSharedPreferences(ConfigConstants.PREF_FILE, Activity.MODE_PRIVATE);
		if (sp.contains(ConfigConstants.KEY_TENCENTWEOBO_TOKEN)) {
			tencentWeiboToken = sp.getString(ConfigConstants.KEY_TENCENTWEOBO_TOKEN, "");
			tencentWeiboOpenid = sp.getString(ConfigConstants.KEY_TENCENTWEOBO_OPEN_ID, "");
			if (UtilTecentWeiBo.isTokenEnabled(sp.getString(ConfigConstants.KEY_TENCENTWEOBO_EXPIRES_IN, "0"), sp.getLong(ConfigConstants.KEY_TENCENTWEOBO_REQUEST_TIME, 0))) {
				// token有效
				btnTencentWeiBoAuth.setEnabled(false);
				btnTencentWeiBoUpdate.setEnabled(true);
				btnTencentWeiboUnAuth.setEnabled(true);
			}
		} else {
			Log.e("", ConfigConstants.KEY_TENCENTWEOBO_TOKEN + " sharepreferences not saved!!");
		}
	}
}
