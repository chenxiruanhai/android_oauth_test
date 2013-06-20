package com.zgy.oauthtest.util;

import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.zgy.oauthtest.AuthActivity;
import com.zgy.oauthtest.MainActivity;


public class UtilTecentSpace {

	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void getQzoneToken(Context context, String accessToken, String expires, Handler handler) {
		
		String urlPath = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
		try {
			HttpClient request = HttpUtil.getNewHttpClient();
			HttpGet httpGet = new HttpGet(new URI(urlPath));
			HttpResponse resposne = request.execute(httpGet);
			if (200 == resposne.getStatusLine().getStatusCode()) {
				InputStream inputStream = resposne.getEntity().getContent();
				String result = new String(HttpUtil.readStream(inputStream));
				result = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
				JSONObject object = new JSONObject(result);
				// String client_id = object.getString("client_id");
				String openid = object.getString("openid");
				
				
				Message msg = new Message();
				msg.what = AuthActivity.MSG_OAUTH_FINISH;
				msg.arg1 = AuthActivity.OAUTH_SUCCESS;
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getUserInfo() {
		
		
//		String userUrl = "https://graph.qq.com/user/get_user_info?access_token=" + accessToken + "&oauth_consumer_key=" + PlatformBindConfig.QQ_AppKey + "&openid=" + openid;
//		httpGet = new HttpGet(new URI(userUrl));
//		resposne = request.execute(httpGet);
//		if (200 == resposne.getStatusLine().getStatusCode()) {
//			inputStream = resposne.getEntity().getContent();
//			result = new String(ServiceUtils.readStream(inputStream));
//			object = new JSONObject(result);
//		}
		
		
	}
	
}
