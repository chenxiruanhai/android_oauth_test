package com.zgy.oauthtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zgy.oauthtest.util.UtilRenRen;
import com.zgy.oauthtest.util.UtilSina;
import com.zgy.oauthtest.util.UtilTecentWeiBo;

public class AuthActivity extends Activity {

	private WebView webView;

	private int platform = -1;

	private Handler mHandler;
	public static final int MSG_OAUTH_FINISH = 111;
	public static final int OAUTH_SUCCESS = 1;
	public static final int OAUTH_FAIL = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS); // 设置Activity显示进度条
		setContentView(R.layout.activity_auth);

		mHandler = new AuthHandler();

		webView = (WebView) this.findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);

		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				AuthActivity.this.setProgress(newProgress * 100);
			}
		});

		Bundle extras = getIntent().getExtras();
		String url = "";
		if (extras != null) {
			if (extras.containsKey("url")) {
				platform = extras.getInt("platform");
				url = extras.getString("url");
				// if (ServiceUtils.isConnectInternet(this)) {
				// Log.e("WebViewActivity", "url=" + url.toString());
				webView.loadUrl(url);
				// } else {
				// Toast.makeText(this, "网络连接失败", Toast.LENGTH_LONG).show();
				// return;
				// }
			}
		}
		// webView.addJavascriptInterface(new JavaScriptInterface(), "Methods");
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, final String url, Bitmap favicon) {
				Log.e("onPageStarted", "url= " + url);

				switch (platform) {
				case ConfigConstants.PLATFORM_SINA:
					if (url.contains("code=")) {
						String code = url.substring(url.indexOf("code=") + 5, url.length());
						UtilSina.getSinaToken(AuthActivity.this, code, mHandler);
					}
					break;
				case ConfigConstants.PLATFORM_RENREN:
					if (url.contains("code=")) {
						String code = url.substring(url.indexOf("code=") + 5, url.length());
						UtilRenRen.getRenRenToken(AuthActivity.this, code, mHandler);
					}
					break;
				case ConfigConstants.PLATFORM_QQWEIBO:
					if (url.contains("code=") && url.contains("&openid=") && url.contains("&openkey=")) {
						String code = url.substring(url.indexOf("code=") + 5, url.indexOf("&openid="));
						String openid = url.substring(url.indexOf("&openid=") + 8, url.indexOf("&openkey="));
						String openkey = url.substring(url.indexOf("&openkey=") + 9, url.length());
						if (code != null && !code.equals("")) {
							UtilTecentWeiBo.getTecentWeiBoToken(AuthActivity.this, code, openid, openkey, mHandler);
						}
					}
					break;
				case ConfigConstants.PLATFORM_QQSPACE:
					/**QQ或Facebook*/
					if (url.contains("#access_token=") || url.contains("&expires_in=")) {
						String accessToken = url.substring(url.indexOf("#access_token=") + 14, url.indexOf("&expires_in="));
						String expires = url.substring(url.indexOf("&expires_in=") + 12, url.length());
						
					 
					}
					
					break;
				default:
					break;
				}

				// /** 豆瓣 */
				// if (url.indexOf(PlatformBindConfig.Callback + "/?oauth_token=") != -1) {
				// // String oauthToken = url.substring(url.indexOf("/?oauth_token=") + 14, url.length());
				// Intent intent = new Intent();
				// intent.setAction("oauth_verifier");
				// Bundle extras = new Bundle();
				// intent.putExtras(extras);
				// sendBroadcast(intent);
				// AuthActivity.this.finish();
				// return;
				// }

				// /** 腾讯 */
				// if (url.contains("code=") && url.contains("&openid=") && url.contains("&openkey=")) {
				// String code = url.substring(url.indexOf("code=") + 5, url.indexOf("&openid="));
				// String openid = url.substring(url.indexOf("&openid=") + 8, url.indexOf("&openkey="));
				// String openkey = url.substring(url.indexOf("&openkey=") + 9, url.length());
				// if (!code.equals("")) {
				// Intent intent = new Intent();
				// intent.setAction("oauth_verifier");
				// Bundle extras = new Bundle();
				// extras.putString("code", code);
				// extras.putString("openid", openid);
				// extras.putString("openkey", openkey);
				// intent.putExtras(extras);
				// sendBroadcast(intent);
				// AuthActivity.this.finish();
				// return;
				// }
				// }

				// /** QQ或Facebook */
				// if (url.contains("#access_token=") || url.contains("&expires_in=")) {
				// String accessToken = url.substring(url.indexOf("#access_token=") + 14,
				// url.indexOf("&expires_in="));
				// String expires = url.substring(url.indexOf("&expires_in=") + 12, url.length());
				//
				// System.out.println(accessToken);
				// System.out.println(expires);
				// if (!accessToken.equals("")) {
				// Intent intent = new Intent();
				// intent.setAction("oauth_verifier");
				// Bundle extras = new Bundle();
				// extras.putString("accessToken", accessToken);
				// extras.putString("expires", expires);
				// intent.putExtras(extras);
				// sendBroadcast(intent);
				// AuthActivity.this.finish();
				// return;
				// }
				// }
			}

			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				handler.proceed();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// view.loadUrl("javascript:window.Methods.getHTML('<div>'+document.getElementById('oauth_pin').innerHTML+'</div>');");
				super.onPageFinished(view, url);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			// if (webView.canGoBack()) {
			// webView.goBack();
			// return true;
			// }
		}
		return super.onKeyDown(keyCode, event);
	}

	private class AuthHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case MSG_OAUTH_FINISH:
				if (msg.arg1 == OAUTH_SUCCESS) {
					Toast.makeText(AuthActivity.this, "绑定成功!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AuthActivity.this, "绑定失败!", Toast.LENGTH_SHORT).show();
				}
				finish();
				break;

			default:
				break;
			}
		}

	}
}
