package com.zgy.oauthtest;

public class ConfigConstants {

	// 保存token的文件
	public static final String PREF_FILE = "pref_file"; // 配置文件
	public static final String KEY_SINA_TOKEN = "sina_token";
	public static final String KEY_RENREN_TOKEN = "renre_token";

	public static final String KEY_TENCENTWEOBO_TOKEN = "tecent_weibo_token";
	public static final String KEY_TENCENTWEOBO_REQUEST_TIME = "tecent_weibo_request_time";
	public static final String KEY_TENCENTWEOBO_OPEN_ID = "tecent_weibo_open_id";
	public static final String KEY_TENCENTWEOBO_EXPIRES_IN = "tecent_weibo_expires_in";

	public static final int PLATFORM_SINA = 0;
	public static final int PLATFORM_RENREN = 1;
	public static final int PLATFORM_QQSPACE = 2;
	public static final int PLATFORM_QQWEIBO = 3;

	// 新浪微博
	public static final String SINA_APP_KEY = "2471322837";
	public static final String SINA_APP_SECRET = "93e8d77c6f432effbd45340b35e564cf";
	public static final String SINA_OAUTH_CALLBACK = "http://craining.blog.163.com/";
	public static final String Sina_Authorize2 = "https://api.weibo.com/oauth2/authorize?client_id=" + SINA_APP_KEY + "" + "&response_type=code&redirect_uri=" + SINA_OAUTH_CALLBACK;
	// public static final String Sina_Access_Token2 = "https://api.weibo.com/oauth2/access_token?client_id="
	// + SINA_APP_KEY + "&client_secret=" + SINA_APP_SECRET + "&grant_type=authorization_code&redirect_uri=" +
	// SINA_OAUTH_CALLBACK + "&code=";

	// 人人
	public static final String RENREN_APP_ID = "231482";
	public static final String RENREN_API_KEY = "2ad4cfc7bbf34f39834f8c1c6d1da1c6";
	public static final String RENREN_SECRET_KEY = "98b41549635d41d09a76dade091db8f6";
	public static final String RENREN_OAUTH_CALLBACK = "http://graph.renren.com/oauth/login_success.html";
	// public static final String RENREN_OAUTH_CALLBACK = "http://craining.blog.163.com/";
	/**
	 * 请求code的url：注意scope参数，若使用token调用api时返回“需要用户授予权限”字样，则说明此处为添加此权限, 详见 :
	 * http://wiki.dev.renren.com/wiki/%E6%9D%83%E9%99%90%E5%88%97%E8%A1%A8
	 */
	public static final String Renren_Authorize2 = "https://graph.renren.com/oauth/authorize?client_id=" + RENREN_APP_ID + "" + "&response_type=code&redirect_uri=" + RENREN_OAUTH_CALLBACK + "&scope=" + "publish_share,status_update";//
	public static final String Renren_Access_Token = "https://graph.renren.com/oauth/token?";
	// 腾讯微博

	public static final String QQWEIBO_API_KEY = "801339872";
	public static final String QQWEIBO_SECRET_KEY = "ef4f11cfd127f72cd250f6db2b16f470";
	public static final String QQWEIBO_OAUTH_CALLBACK = "http://craining.blog.163.com/";
	public static final String QQWEIBO_Authorize_2 = "https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=" + QQWEIBO_API_KEY + "" + "&response_type=code&redirect_uri=" + QQWEIBO_OAUTH_CALLBACK;

	// QQ空间

	// 传递scope在授权时可显示权限
	public static final String QQSPACE_API_KEY = "100416907";
	public static final String QQSPACE_SECRET_KEY = "c60c99dffc74502a24b1ab64970fe7b2";
	public static final String QQ_Request_Token = "http://openapi.qzone.qq.com/oauth/qzoneoauth_request_token";
	// public static final String QQ_Authorize = "http://openapi.qzone.qq.com/oauth/qzoneoauth_authorize";
	// public static final String QQ_Access_Token =
	// "http://openapi.qzone.qq.com/oauth/qzoneoauth_access_token";
	// public static final String QQ_Authorize =
	// "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=" + QQ_AppKey +
	// "&display=mobile&redirect_uri=" + Callback;
	// 豆瓣
	// 搜狐微博
}
