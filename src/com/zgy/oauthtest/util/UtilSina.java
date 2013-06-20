package com.zgy.oauthtest.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zgy.oauthtest.AuthActivity;
import com.zgy.oauthtest.ConfigConstants;
import com.zgy.oauthtest.PlatformAccount;

/**
 * 新浪微博API调用
 * 
 * 详见：http://open.weibo.com/wiki/API%E6%96%87%E6%A1%A3_V2
 * 
 * @Description:
 * @author: zhuanggy
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-4-9
 */
public class UtilSina {

	/**
	 * 取消授权
	 * 
	 * @Description:
	 * @param token
	 * @return true取消成功，false取消失败
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	public static boolean disableToken(String token) {

		try {
			HttpResponse resposne;
			InputStream inputStream;
			String result;
			String url = "https://api.weibo.com/oauth2/revokeoauth2?access_token=" + token;
			Log.e("disableToken", "request url=" + url);
			HttpGet httpGet = new HttpGet(new URI(url));
			HttpClient request = HttpUtil.getNewHttpClient();
			resposne = request.execute(httpGet);
			Log.e("disableToken", "resposne=" + resposne.toString());
			if (200 == resposne.getStatusLine().getStatusCode()) {
				inputStream = resposne.getEntity().getContent();
				result = new String(HttpUtil.readStream(inputStream));
				Log.e("disableToken", "result=" + result);

				JSONObject jsonObj = new JSONObject(result);
				if (jsonObj.getString("result").equals("true")) {
					return true;
				}

				/**
				 * { "result":"true" }
				 * 
				 */
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
	 * 判断token是否可用
	 * 
	 * @Description:
	 * @param token
	 * @return true可用，false过期
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	public static boolean isTokenEnabled(String token) {

		try {
			String url = "https://api.weibo.com/oauth2/get_token_info";
			Log.e("isTokenEnabled", "request url=" + url);
			HttpClient request = HttpUtil.getNewHttpClient();
			HttpPost httpPost = new HttpPost(new URI(url));
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("access_token", token));
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resposne = request.execute(httpPost);
			Log.e("isTokenEnabled", "resposne=" + resposne);
			if (200 == resposne.getStatusLine().getStatusCode()) {
				InputStream inputStream = resposne.getEntity().getContent();
				String result = new String(HttpUtil.readStream(inputStream));

				Log.e("isTokenEnabled", "result=" + result);

				JSONObject jsonObj = new JSONObject(result);
				long leftTime = jsonObj.getLong("expire_in");

				if (leftTime > 60) {
					return true;
				}

				/**
				 * uid string 授权用户的uid。
				 * 
				 * appkey string access_token所属的应用appkey。
				 * 
				 * scope string 用户授权的scope权限。
				 * 
				 * create_at string access_token的创建时间，从1970年到创建时间的秒数。
				 * 
				 * expire_in string access_token的剩余时间，单位是秒数。
				 * 
				 */
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
	 * 获取新浪用户授权的token
	 * 
	 * @Description:
	 * @param context
	 * @param code
	 * @param handle
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-10
	 */
	public static void getSinaToken(final Context context, final String code, final Handler handler) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				
				Message msg = new Message();
				msg.what = AuthActivity.MSG_OAUTH_FINISH;
				msg.arg1 = 0;

				String access_url = "https://api.weibo.com/oauth2/access_token";
				HttpClient request = HttpUtil.getNewHttpClient();
				HttpPost httpPost;
				try {
					httpPost = new HttpPost(new URI(access_url));
					ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					params.add(new BasicNameValuePair("client_id", ConfigConstants.SINA_APP_KEY));
					params.add(new BasicNameValuePair("client_secret", ConfigConstants.SINA_APP_SECRET));
					params.add(new BasicNameValuePair("grant_type", "authorization_code"));
					params.add(new BasicNameValuePair("redirect_uri", ConfigConstants.SINA_OAUTH_CALLBACK));
					params.add(new BasicNameValuePair("code", code));
					httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

					HttpResponse resposne = request.execute(httpPost);
					if (200 == resposne.getStatusLine().getStatusCode()) {
						InputStream inputStream = resposne.getEntity().getContent();
						String result = new String(HttpUtil.readStream(inputStream));

						Log.e("getSinaToken", "result=" + result);

						JSONObject obj = new JSONObject(result);
						String accessToken = obj.getString("access_token");
						// String expires_in = obj.getString("expires_in");
						String uid = obj.getString("uid");

						// 保存accessToken
						SharedPreferences sp = context.getSharedPreferences(ConfigConstants.PREF_FILE, Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = sp.edit();
						if (sp.contains(ConfigConstants.KEY_SINA_TOKEN)) {
							editor.remove(ConfigConstants.KEY_SINA_TOKEN);
						}
						editor.putString(ConfigConstants.KEY_SINA_TOKEN, accessToken);
						editor.commit();
						msg.arg1 = 1;
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
				handler.sendMessage(msg);
				
				
			}
		}).start();

		
	}

	/**
	 * 获得用户信息
	 * 
	 * @Description:
	 * @param request
	 * @param accessToken
	 * @param expires_in
	 * @param uid
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 * @throws JSONException
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	public static void getUserInfo(String accessToken, String uid) {

		try {
			HttpResponse resposne;
			InputStream inputStream;
			String result;
			String url = "https://api.weibo.com/2/users/show.json?access_token=" + accessToken + "&uid=" + uid;
			Log.e("getUserInfo", "request url=" + url);
			HttpGet httpGet = new HttpGet(new URI(url));
			HttpClient request = HttpUtil.getNewHttpClient();
			resposne = request.execute(httpGet);
			Log.e("getUserInfo", "resposne=" + resposne.toString());
			if (200 == resposne.getStatusLine().getStatusCode()) {
				inputStream = resposne.getEntity().getContent();
				result = new String(HttpUtil.readStream(inputStream));

				Log.e("getUserInfo", "result=" + result);

				JSONObject object = new JSONObject(result);
				PlatformAccount account = new PlatformAccount();
				account.setAccessToken(accessToken);
				account.setNickName(object.getString("screen_name"));
				account.setOpenAvatar(object.getString("profile_image_url"));
				String sexStr = object.getString("gender");
				int sex = 0;
				if (sexStr != null) {
					if (sexStr.equals("m")) {
						sex = 1;
					} else if (sexStr.equals("f")) {
						sex = 2;
					} else {
						sex = 0;
					}
				}
				account.setOpenSex(sex);
				account.setOpenType(1);
				account.setOpenUid(object.getString("id"));
				// account.setOpenExpire(expires_in);
				// Message msg = Message.obtain(handler, MainActivity.oauth_sucess, account);
				// handler.sendMessage(msg);
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

	}

	/**
	 * 更新一条微博
	 * 
	 * @Description:
	 * @param accessToken
	 * @param content
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 * @throws JSONException
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-4-9
	 */
	public static boolean updateOneStatues(String accessToken, String content) {

		try {
			String url = "https://api.weibo.com/2/statuses/update.json";
			Log.e("isTokenEnabled", "request url=" + url);
			HttpClient request = HttpUtil.getNewHttpClient();
			HttpPost httpPost = new HttpPost(new URI(url));
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("status", content));
			params.add(new BasicNameValuePair("access_token", accessToken));
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resposne = request.execute(httpPost);
			if (200 == resposne.getStatusLine().getStatusCode()) {
				InputStream inputStream = resposne.getEntity().getContent();
				String result = new String(HttpUtil.readStream(inputStream));
				Log.e("updateOne", "result=" + result);
				return true;// TODO 判断是否更新成功
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

}
