package com.jumper.weight.common.util;

import java.util.List;
import java.util.Map;

/**
 * 数组处理工具类
 * @Description TODO
 * @author fangxilin
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class ArrayUtils {

	/**
	 * 将枚举类型的List转换为逗号分隔开的字符串
	 * @param list
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String listToString(List list) {
	    StringBuilder sb = new StringBuilder();
	    if (list != null && list.size() > 0) {
	        for (int i = 0; i < list.size(); i++) {
	            if (i < list.size() - 1) {
	                sb.append(list.get(i).toString() + "、");
	            } else {
	                sb.append(list.get(i).toString());
	            }
	        }
	    }
	    return sb.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String listToString(List list, String seq){
        if (list==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (Object obj : list) {
            if (flag) {
                result.append(seq);
            }else {
                flag=true;
            }
            result.append(obj);
        }
        return result.toString();
    }
	
	/**
	 * 判断list是否为空
	 * @param list 判断参数
	 * @return boolean true | false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(List list){
		if(list == null || list.size() < 1){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断list是否为空
	 * @param list 判断参数
	 * @return boolean true | false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list){
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断map是否为空
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Map map){
		if(map == null || map.size() < 1){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断map是否为空
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Map map){
		if(map != null && map.size() > 0){
			return true;
		}
		return false;
	}
}
