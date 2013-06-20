package com.zgy.oauthtest.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zgy.oauthtest.AuthActivity;
import com.zgy.oauthtest.ConfigConstants;

/**
 * 人人网部分api调用
 * 
 * 详见：http://wiki.dev.renren.com/wiki/API
 * 
 * 
 * @Description:
 * @author: zhuanggy
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-4-9
 */
public class UtilRenRen {

	/**
	 * 获取人人Token
	 * 
	 * @Description:
	 * @param context
	 * @param code
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	public static void getRenRenToken(Context context, String code, Handler handler) {
		Message msg = new Message();
		msg.what = AuthActivity.MSG_OAUTH_FINISH;
		msg.arg1 = 0;
		
		try {
			String urlPath = "https://graph.renren.com/oauth/token?";
			URL url = new URL(urlPath);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.setDoOutput(true);
			OutputStream out = request.getOutputStream();
			String grant_type = "grant_type=authorization_code";
			String client_id = "&client_id=" + ConfigConstants.RENREN_API_KEY;
			String client_secret = "&client_secret=" + ConfigConstants.RENREN_SECRET_KEY;
			String redirect_uri = "&redirect_uri=" + ConfigConstants.RENREN_OAUTH_CALLBACK;
			code = "&code=" + code;
			out.write((grant_type + code + client_id + client_secret + redirect_uri).getBytes());
			out.flush();
			request.connect();
			if (200 == request.getResponseCode()) {
				InputStream inputStream = request.getInputStream();
				String result = new String(HttpUtil.readStream(inputStream));
				Log.e("getRenRenToken", "result=" + result);
				JSONObject object = new JSONObject(result);
				String openExpire = object.getString("expires_in");
				String refresh_token = object.getString("refresh_token");
				String access_token = object.getString("access_token");

				// 保存accessToken
				SharedPreferences sp = context.getSharedPreferences(ConfigConstants.PREF_FILE, Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				if (sp.contains(ConfigConstants.KEY_RENREN_TOKEN)) {
					editor.remove(ConfigConstants.KEY_RENREN_TOKEN);
				}
				editor.putString(ConfigConstants.KEY_RENREN_TOKEN, access_token);
				editor.commit();
				msg.arg1 = 1;
				// Message msg = Message.obtain(handler, MainActivity.oauth_sucess, account);
				// handler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		handler.sendMessage(msg);
	}

	/**
	 * 判断token是否失效
	 * 
	 * 详见：http://wiki.dev.renren.com/wiki/Users.isAppUser
	 * 
	 * @Description:
	 * @param token
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	public static boolean isTokenEnabled(String token) {
		try {
			String url = "https://api.renren.com/restserver.do";
			Log.e("isTokenEnabled", "request url=" + url);
			HttpClient request = HttpUtil.getNewHttpClient();
			HttpPost httpPost = new HttpPost(new URI(url));
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("method", "users.isAppUser"));
			params.add(new BasicNameValuePair("v", "1.0"));// API的版本号，固定值为1.0
			params.add(new BasicNameValuePair("access_token", token));// OAuth2.0验证授权后获得的token。同时兼容session_key方式调用
			params.add(new BasicNameValuePair("format", "JSON"));// 返回值的格式。请指定为JSON或者XML，推荐使用JSON，缺省值为XML
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resposne = request.execute(httpPost);
			Log.e("isTokenEnabled", "resposne=" + resposne);
			if (200 == resposne.getStatusLine().getStatusCode()) {
				InputStream inputStream = resposne.getEntity().getContent();
				String result = new String(HttpUtil.readStream(inputStream));

				Log.e("isTokenEnabled", "result=" + result);

				JSONObject jsonObj = new JSONObject(result);
				int resultCode = jsonObj.getInt("result");

				if (resultCode == 1) {
					return true;
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 取消人人授权
	 * 
	 * @Description:
	 * @param context
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	public static boolean disableToken(Context context) {

		// CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
		// CookieManager cookieManager = CookieManager.getInstance();
		// cookieManager.removeAllCookie();

		SharedPreferences sp = context.getSharedPreferences(ConfigConstants.PREF_FILE, Activity.MODE_PRIVATE);
		if (sp.contains(ConfigConstants.KEY_RENREN_TOKEN)) {
			SharedPreferences.Editor editor = sp.edit();
			editor.remove(ConfigConstants.KEY_RENREN_TOKEN);
			editor.commit();
		}

		return true;
	}

	/**
	 * 发布一条动态
	 * 
	 * 详见 ：http://wiki.dev.renren.com/wiki/Status.set
	 * 
	 * @Description:
	 * @param token
	 * @param content
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	public static boolean updateOneStatues(String token, String content) {

		try {
			String url = "https://api.renren.com/restserver.do";
			Log.e("updateOneStatues", "request url=" + url);
			HttpClient request = HttpUtil.getNewHttpClient();
			HttpPost httpPost = new HttpPost(new URI(url));
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("method", "status.set"));
			params.add(new BasicNameValuePair("v", "1.0"));// API的版本号，固定值为1.0
			params.add(new BasicNameValuePair("access_token", token));// OAuth2.0验证授权后获得的token。同时兼容session_key方式调用
			params.add(new BasicNameValuePair("format", "JSON"));// 返回值的格式。请指定为JSON或者XML，推荐使用JSON，缺省值为XML
			params.add(new BasicNameValuePair("status", content));// 用户更新的状态信息，最多140个字符
			// params.add(new BasicNameValuePair("place_id", ));//
			// 发状态时所在地点的ID。place_id为一个地点的Id，可以通过places.create来创建地点，生成place_id。

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resposne = request.execute(httpPost);
			Log.e("updateOneStatues", "resposne=" + resposne);
			if (200 == resposne.getStatusLine().getStatusCode()) {
				InputStream inputStream = resposne.getEntity().getContent();
				String result = new String(HttpUtil.readStream(inputStream));

				Log.e("updateOneStatues", "result=" + result);

				JSONObject jsonObj = new JSONObject(result);
				int resultCode = jsonObj.getInt("result");

				if (resultCode == 1) {
					return true;
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 分享
	 * 
	 * 详见： http://wiki.dev.renren.com/wiki/Share.share
	 * 
	 * @Description:
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */

	public static boolean shareOne() {

		return false;
	}
}
