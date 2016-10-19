package obor.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OborUtil {

	public static String getNowTime(){
		Date nowTime = new Date(); 
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmssms"); 
		return time.format(nowTime); 
	}

	public static String getNowTime2(){
		Date nowTime = new Date(); 
		SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss"); 
		return time.format(nowTime); 
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}
	
	public static String getUSER(){
		return "admin";
	}
}
