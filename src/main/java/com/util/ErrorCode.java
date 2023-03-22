package com.util;

/**
 * 系统错误编码，根据业务定义如下
 * 用户部分编码以10000开始
 *
 *
 */
public class ErrorCode {

	/*认证模块错误码-start*/
	public final static String AUTH_UNKNOWN="10000";//注册失败(数据库注入失败)
	public final static String AUTH_USER_ALREADY_EXISTS="10001";//注册失败，数据库链接异常
	public final static String AUTH_AUTHENTICATION_FAILED="10002";//认证失败
    public final static String AUTH_AUTHENTICATION_UPDATE="10003";//注册失败
	public final static String AUTH_PARAMETER_ERROR="10004";//用户名密码参数错误，为空
	public final static String AUTH_ACTIVATE_FAILED="10005";//邮件注册，激活失败
	public final static String AUTH_REPLACEMENT_FAILED="10006";//置换token失败
	public final static String AUTH_TOKEN_INVALID="10007";//token无效
	public static final String AUTH_ILLEGAL_USERCODE = "10008";//非法的用户名
	/*认证模块错误码-end*/
}
