/*
 * Copyright(C),2015-2018,中原银行
 * FileName:ObjectUtils.java
 * Author:songbo
 * History:
 *    <author> <time> <version> <desc>
 *    作者                   修改时间        版本                    描述								
 */
package cn.com.bob.learn.base.util;



import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * <b>功能描述：</b> <br>
 *     工具类 <br>
 * @author sognbo
 * @create 2019-10-17
 * @since 1.0.0
 */
public class ObjectUtils {

	private static final String SERIAL_VERSION_UID = "serialVersionUID";

	/**
	 *
	 * <b>功能描述：</b> <br>
	 *    map 转  bean <br>
	 * @param map
	 * @param clazz
	 * @return
	 * @since 1.0.0
	 * @date 2019-10-17
	 */
	public static <T> T mapToBean(Map map,Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
			
			return bean;
			
		} catch (Exception e) {
			throw BusinessUtils.createBusinessException("T00001");
		}
	}
	/**
	 * 
	 * <b>功能描述：</b> <br>
	 *    bean 转  map <br>
	 * @param obj
	 * @return
	 * @since 1.0.0
	 * @date 2019-10-17
	 * @deprecated 转换后包含object信息，不建议使用
	 */
	public static Map<?, ?> beanToMap(Object obj) {
		try {
			
			return new org.apache.commons.beanutils.BeanMap(obj);
			
		} catch (Exception e) {
			throw BusinessUtils.createBusinessException("T00002");
		}
	}
	
	/**
	 * 
	 * <b>功能描述：</b> <br>
	 *    bean 转  map <br>
	 * @param obj
	 * @return
	 * @since 1.0.0
	 * @date 2019-10-17
	 */
	public static Map<String, Object> beanToMapNew(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if(BusinessUtils.isNotNull(obj)){
		Class<?> clazz = obj.getClass();
	    for(Field field : clazz.getDeclaredFields()) {
	    	field.setAccessible(true);
	    	String filedName = field.getName();
	    	Object value;
			try {
				value = field.get(obj);
			} catch (Exception e) {
				throw BusinessUtils.createBusinessException("T00002");
			} 
			if(BusinessUtils.isNotNull(value) && !SERIAL_VERSION_UID.equals(filedName)) {
				map.put(filedName, value);
				}
			}
		}else {
			map = null;
		}

		return map;
	}
	
}
