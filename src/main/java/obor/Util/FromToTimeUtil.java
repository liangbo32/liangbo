package obor.Util;

import org.springframework.util.StringUtils;

public class FromToTimeUtil {
	public static String fromTime(String fromTime){
		if(!StringUtils.isEmpty(fromTime)){
			fromTime = fromTime + " 00:00:00";
		}
		return fromTime;
	}
	
	public static String toTime(String toTime){
		if(!StringUtils.isEmpty(toTime)){
			toTime = toTime + " 23:59:59";
		}
		return toTime;
	}
}
