package com.zgy.oauthtest;

/**
 * ����ƽ̨
 * 
 * @author 06peng
 * 
 */
public class PlatformAccount {

	/**
	 * ����ƽ̨���� QQ��0���� ����΢����1���� ��Ѷ΢����2���� ��������3���� ��������4���� twitter��5���� facebook��6����
	 */
	private int openType;

	private String accessToken;

	private String tokenSecret;

	private String nickName;

	/**
	 * ����ƽ̨uid��QQΪһ���û�ID���ַ�������Ѷ΢��Ϊ�û���¼������������ƽ̨Ϊһ������ID��
	 */
	private String openUid;

	/**
	 * ����ƽ̨�Ա�����΢������Ѷ΢�����������У�����û�У�
	 */
	private int openSex;

	/**
	 * ����ƽ̨���ڱ�ʶ�����������У�
	 */
	private String openExpire;

	private String openAvatar;

	public int getOpenType() {
		return openType;
	}

	public void setOpenType(int openType) {
		this.openType = openType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOpenUid() {
		return openUid;
	}

	public void setOpenUid(String openUid) {
		this.openUid = openUid;
	}

	public int getOpenSex() {
		return openSex;
	}

	public void setOpenSex(int openSex) {
		this.openSex = openSex;
	}

	public String getOpenExpire() {
		return openExpire;
	}

	public void setOpenExpire(String openExpire) {
		this.openExpire = openExpire;
	}

	public String getOpenAvatar() {
		return openAvatar;
	}

	public void setOpenAvatar(String openAvatar) {
		this.openAvatar = openAvatar;
	}
}
