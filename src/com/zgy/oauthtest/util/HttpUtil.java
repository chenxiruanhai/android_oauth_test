package com.zgy.oauthtest.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.zgy.oauthtest.signature.SSLSocketFactoryEx;

public class HttpUtil {

//	
//	public static HttpURLConnection getConnection(String uri) {
//		HttpURLConnection httpConn = null;
//		URL url = null;
//		try {
//			url = new URL(uri);
//			httpConn = (HttpURLConnection) url.openConnection();
//			httpConn.setConnectTimeout(50000);
//			httpConn.setReadTimeout(50000);
//			// 打开读写属性，默认均为false
//			httpConn.setDoOutput(true);
//			httpConn.setDoInput(true);
//			httpConn.setInstanceFollowRedirects(true);
//		} catch (IOException ie) {
//			ie.printStackTrace();
//		}
//		return httpConn;
//	}
	
	
	/**
	 * 解析服务器返回数据
	 * 
	 * @Description:
	 * @param is
	 * @param isGzip
	 * @return
	 * @throws IOException
	 * @see:
	 * @since:
	 * @author: huangyongxing
	 * @date:2012-8-8
	 */
	public static String requestResult(InputStream is, boolean isGzip) throws IOException {
		BufferedReader bufferedReader = null;
		StringBuilder builder = new StringBuilder();
		try {
			if (isGzip) {
				is = new GZIPInputStream(is);
			}
			bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			// 读取服务器返回数据，转换成BufferedReader
			for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
				builder.append(s);
			}
		} finally {
			is.close();
			bufferedReader.close();
		}
		return builder.toString();
	}
	
	public static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	public static byte[] readStream(InputStream is) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[2048];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		is.close();
		return os.toByteArray();
	}
}
