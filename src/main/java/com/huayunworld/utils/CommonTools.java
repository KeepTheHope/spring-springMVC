package com.huayunworld.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.github.pagehelper.StringUtil;

/**
 * 工具类
 *
 */
public class CommonTools {
	private static Logger logger = Logger.getLogger(CommonTools.class);
	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 读取配置文件项
	 * @param fileName
	 * @param item
	 * @return
	 * @throws ConfigurationException
	 */
	public static String readFile(String fileName, String item){
		try {
			CompositeConfiguration config = new CompositeConfiguration();  
	        config.addConfiguration(new PropertiesConfiguration(new ClassPathResource(fileName).getPath()));  
	        return config.getString(item);
		} catch (Exception e) {
			logger.error("读取配置文件异常fileName=" + fileName + ",item" + item);
			return null;
		}
	}
	
	
	/**
	 * 读取application.properties中的配置项
	 * @param item
	 * @return
	 * @throws ConfigurationException 
	 */
	public static String getApplicationConf(String item){
		return readFile("application.properties", item);
	}
	
	
	/**
	 * 获取UUID 
	 * @return 32位
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 获取随机数
	 * @return 32位
	 */
	public static String getRandomId(){
		return RandomStringUtils.randomNumeric(32);
	}
	
	/**
	 * 获取随机数
	 * @return 8位
	 */
	public static String getRandomId8(){
		return RandomStringUtils.randomNumeric(8);
	}
	

	/**
	 * 获取随机数
	 */
	public static String getRandomId(int count){
		return RandomStringUtils.randomNumeric(count);
	}
	
	public static String getFormatDate(Date d){
		DateFormat format2 = new SimpleDateFormat(DATE_FORMAT);  
		return format2.format(d);
	}
	
	public static String getFormatDate(Date d, String f){
		DateFormat format2 = new SimpleDateFormat(f);  
		return format2.format(d);
	}
	/**
	 *  url解码
	 * @param context
	 * @return
	 */
    public static String decodeContext(String context){
    	try {
			context =  java.net.URLDecoder.decode(context,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("解码失败！");
		}
    	return context;
    }
    /**
     *  集合按照集合内部对象的某属性字段排序，注意：属性字段的值只能是基本类型
     * @param <T>
     * @param context
     * @return
     */
    public static <T> void orderByPropertyName(List<T> list,final String propertyName){
    	Collections.sort(list, new Comparator<T>(){
    		public int compare(T o1,T o2) {
    			try {
					Method method = o1.getClass().getMethod(
							"get"+(propertyName.charAt(0)+"").toUpperCase()
							+propertyName.substring(1, propertyName.length()));
					return (method.invoke(o1).toString()).compareTo(method.invoke(o2).toString());
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("集合按照"+propertyName+"排序异常！");
				}
				
			}
		});
    }
 /**
  * 在很多应用下都可能有需要将用户的真实IP记录下来，这时就要获得用户的真实IP地址，在JSP里，获取客户端的IP地
  * 址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。但是在通过了Apache,Squid等
  * 反向代理软件就不能获取到客户端的真实IP地址了。
  * 但是在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR信息。用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。
  * @param request
  * @return
  */

public static String getClientIp(HttpServletRequest request) {

           String ip = request.getHeader("x-forwarded-for");


           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("Proxy-Client-IP"); 
           }
           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("WL-Proxy-Client-IP");
           }
           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getRemoteAddr();
           }
           if(StringUtil.isNotEmpty(ip)) {
               ip = ip.split(",")[0];
           }
           return ip;

       }
	/**
	 * 多表查询时，根据时间封装需要查询的表 sjh 2017-12-13
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<String>  getTableNameTimes(Date startTime,Date endTime) {
		Calendar caS = Calendar.getInstance();
		Calendar caE = Calendar.getInstance();
		caS.setTime(startTime);
		caE.setTime(endTime);
		int sYear = caS.get(Calendar.YEAR);
		int eYear = caE.get(Calendar.YEAR);
		int sMonth = caS.get(Calendar.MONTH)+1;
		int eMonth = caE.get(Calendar.MONTH)+1;
		List<String > tableNameTime = new ArrayList<String>();
		if(sYear!=eYear) {//跨年
			int nY = eYear-sYear;//跨两年及以上
			if(nY>1) for(int n = nY-1;n>0;n--) for(int m=1;m<=12;m++) tableNameTime.add("_"+(sYear+n)+""+ ( m>9?m:("0"+m)));
			for(int s = 12;s-sMonth>=0;s--) tableNameTime.add("_"+sYear+""+ ( s>9?s:("0"+s)));	
			for(int s = 1;s<=eMonth;s++) tableNameTime.add("_"+eYear+""+ ( s>9?s:("0"+s)));	
		}else {//不跨年，跨月
			for(int s = sMonth;eMonth>=s;s++) tableNameTime.add("_"+sYear+""+ ( s>9?s:("0"+s)));
		} 
		return tableNameTime;
	}
	
	
	
	/**
	 * 复制对象属性
	 * @param dest
	 * @param orig
	 */
	public static void BeanCopyProperties(Object dest, Object orig){
		try {
			BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			logger.error("复制对象属性异常", e);
		}
	}
	
	

}


