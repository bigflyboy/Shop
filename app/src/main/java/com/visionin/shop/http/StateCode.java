package com.visionin.shop.http;

public class StateCode {

	//200 : 成功
	public static int SUCCESS = 200;
	
	
	//500 : 参数不存在
	public static int PARAMETER_NULL = 500;
	
	//501 : 登陆失败
	public static int LOGIN_FAILED = 501;
	
	//502 : 权限错误
	public static int PERMISSION_ERROR = 502;
	
	//503 : 未知错误
	public static int UNKNOW_ERROR = 503;
	
	//504 : 数据操作错误 (包括未查询到，查询到多个，添加失败，删除失败)
	public static int DATA_ERROR = 504;
	
}
