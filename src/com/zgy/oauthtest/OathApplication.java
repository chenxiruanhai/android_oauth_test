package com.zgy.oauthtest;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.util.Log;

public class OathApplication extends Application {

	@Override
	public void onCreate() {
		// if (DEVELOPER_MODE) {
		// StrictMode.setThreadPolicy(new
		// StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork() //
		// ��������滻ΪdetectAll()
		// // �Ͱ����˴��̶�д������I/O
		// .penaltyLog() // ��ӡlogcat����ȻҲ���Զ�λ��dropbox��ͨ���ļ�������Ӧ��log
		// .build());
		// StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects() //
		// ̽��SQLite���ݿ����
		// .penaltyLog() // ��ӡlogcat
		// .penaltyDeath().build());
		// }
		super.onCreate();

//		if (Log.isLoggable(Constants.PERFORMANCE_TAG, Log.DEBUG)) {
//			Log.d(Constants.PERFORMANCE_TAG, "ContactsApplication.onCreate start");
//		}
//
//		// Priming caches to placate the StrictMode police
//		Context context = getApplicationContext();
//		PreferenceManager.getDefaultSharedPreferences(context);
//		AccountTypeManager.getInstance(context);
//		if (ENABLE_FRAGMENT_LOG)
//			FragmentManager.enableDebugLogging(true);
//		if (ENABLE_LOADER_LOG)
//			LoaderManager.enableDebugLogging(true);
//
//		if (Log.isLoggable(Constants.STRICT_MODE_TAG, Log.DEBUG)) {
//			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
//		}
//
//		if (Log.isLoggable(Constants.PERFORMANCE_TAG, Log.DEBUG)) {
//			Log.d(Constants.PERFORMANCE_TAG, "ContactsApplication.onCreate finish");
//		}

	}

}
