package cn.com.bob.base.util;


import cn.com.bob.base.exception.ExceptionUtils;
import cn.com.bob.base.serializer.JDKSerializer;
import cn.com.bob.base.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class BusiUtils {

    private static final Logger log = LoggerFactory.getLogger(BusiUtils.class);
    /**
     * 取绝对值
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午3:14:45
     */
    public static BigDecimal abs(BigDecimal value) {
        if (null == value) {
            return null;
        }
        return BigDecimal.valueOf(Math.abs(value.doubleValue()));
    }

    /**
     * 取绝对值
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午3:14:45
     */
    public static BigDecimal abs(int value) {
        return BigDecimal.valueOf(Math.abs(value));
    }

    /**
     * 取绝对值
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午3:14:45
     */
    public static BigDecimal abs(long value) {
        return BigDecimal.valueOf(Math.abs(value));
    }

    /**
     * 取绝对值
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午3:14:45
     */
    public static BigDecimal abs(float value) {
        return BigDecimal.valueOf(Math.abs(value));
    }

    /**
     * 取绝对值
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午3:14:45
     */
    public static BigDecimal abs(double value) {
        return BigDecimal.valueOf(Math.abs(value));
    }

    /**
     * 字符串拼接
     *
     * @param s
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月12日 下午3:13:05
     */
    public static StringBuffer append(String s) {
        StringBuffer sb = new StringBuffer();
        sb.append(s);
        return sb;
    }

    /**
     * 将字符String类型转换为日期Date类型,默认转换为yyyyMMdd
     *
     * @param date
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月5日 下午4:00:16
     */
    public static Date convertStr2Date(String date) {
    	if (null == date) {
            return null;
        }
        return DateUtils.parse(date, DateUtils.DEFAULT_DATE_FORMAT);
    }
    
    /**
     * 将字符String类型转换为日期Date类型,默认转换为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年11月25日18:10:17
     */
    public static Date convertStr3Date(String date) {
    	return DateUtils.parse(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 将字符String类型转换为日期Date类型,默认转换为yyyy-MM-dd
     *
     * @param date
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年11月25日18:10:17
     */
    public static Date convertStr4Date(String date) {
    	return DateUtils.parse(date, "yyyy-MM-dd");
    }

    /**
     * 将日期转为String类型，Date如果为null，返回null，默认转换为yyyyMMdd
     *
     * @param date
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 上午11:04:07
     */
    public static String convertDate2Str(Date date) {
        return convertDate2Str(date, DateUtils.DEFAULT_DATE_FORMAT);
    }
    /**
     * 将日期转为String类型，Date如果为null，返回null，默认转换为yyyyMMdd HH:mm:ss
     *
     * @param date
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2016年5月5日 
     */
    public static String convertDate3Str(Date date) {
        return convertDate2Str(date, DateUtils.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 将日期转为String类型
     *
     * @param date
     * @param pattern
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 上午11:03:04
     */
    public static String convertDate2Str(Date date, String pattern) {
        if (null == date) {
            return null;
        }
        return DateUtils.getDateTime(date, pattern);
    }

    /**
     * 当source等于condition时，return value<br>
     * 否则return value2
     *
     * @param source
     * @param condition
     * @param value
     * @param value1
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午1:38:46
     */
    public static <T> T decode(String source, String condition, T value,
                               T value1) {
        if (condition.equals(source)) {
            return value;
        }
        return value1;
    }

    /**
     * 当source等于condition1时，return value1<br>
     * 当source等于condition2时，return value2<br>
     * 否则return value3
     *
     * @param source
     * @param condition1
     * @param value1
     * @param condition2
     * @param value2
     * @param value3
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午1:38:46
     */
    public static <T> T decode(String source, String condition1, T value1,
                               String condition2, T value2, T value3) {
        if (condition1.equals(source)) {
            return value1;
        } else if (condition2.equals(source)) {
            return value2;
        }
        return value3;
    }

    /**
     * 当source等于condition时，return value<br>
     * 否则return value2
     *
     * @param source
     * @param condition
     * @param value
     * @param value1
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午1:38:46
     */
    public static <T> T decode(int source, int condition, T value, T value1) {
        return decode(String.valueOf(source), String.valueOf(condition), value,
                value1);
    }

    /**
     * 当source等于condition1时，return value1<br>
     * 当source等于condition2时，return value2<br>
     * 否则return value3
     *
     * @param source
     * @param condition1
     * @param value1
     * @param condition2
     * @param value2
     * @param value3
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午1:38:46
     */
    public static <T> T decode(int source, int condition1, T value1,
                               int condition2, T value2, T value3) {
        return decode(String.valueOf(source), String.valueOf(condition1),
                value1, String.valueOf(condition2), value2, value3);
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(String one, String two) {
        return DateUtils.diffDays(
                DateUtils.parse(one, DateUtils.DEFAULT_DATE_FORMAT),
                DateUtils.parse(two, DateUtils.DEFAULT_DATE_FORMAT));
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(Date one, Date two) {
        return DateUtils.diffDays(one, two);
    }

    /**
     * 获取生效利率
     *
     * @param actualRate
     * @param minRate
     * @param maxRate
     * @return BigDecimal
     */
    public static BigDecimal getActualRate(BigDecimal actualRate,
                                           BigDecimal minRate, BigDecimal maxRate) {
        BigDecimal actRate;
        if (actualRate.compareTo(minRate) < 0
                && actualRate.compareTo(maxRate) < 0
                && minRate.compareTo(BigDecimal.ZERO) != 0) {
            actRate = minRate;
        } else if (actualRate.compareTo(minRate) > 0
                && actualRate.compareTo(maxRate) > 0
                && maxRate.compareTo(BigDecimal.ZERO) != 0) {
            actRate = maxRate;
        } else {
            actRate = actualRate;
        }
        return actRate;
    }

    /**
     * 字符串为空或者null<br>
     * 对象为null
     *
     * @param obj
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午1:23:51
     */
    public static boolean isNull(Object obj) {
        if (String.class.isInstance(obj)) {
            return StringUtils.isEmpty((String) obj);
        }
        return obj == null;
    }

    /**
     * 字符串不为空或者null 对象不为null
     *
     * @param obj
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月12日 下午5:56:53
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 如果数据大于0，返回1<br>
     * 小于0返回-1<br>
     * 等于0，返回0
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午2:43:45
     */
    public static int sign(BigDecimal value) {
        if (null == value) {
            return 0;
        }
        if (value.doubleValue() > 0) {
            return 1;
        } else if (value.doubleValue() < 0) {
            return -1;
        }
        return 0;
    }

    /**
     * 如果数据大于0，返回1<br>
     * 小于0返回-1<br>
     * 等于0，返回0
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午2:43:45
     */
    public static int sign(int value) {
        return sign(new BigDecimal(value));
    }

    /**
     * 如果数据大于0，返回1<br>
     * 小于0返回-1<br>
     * 等于0，返回0
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午2:43:45
     */
    public static int sign(long value) {
        return sign(new BigDecimal(value));
    }

    /**
     * 如果数据大于0，返回1<br>
     * 小于0返回-1<br>
     * 等于0，返回0
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午2:43:45
     */
    public static int sign(float value) {
        return sign(BigDecimal.valueOf(value));
    }

    /**
     * 如果数据大于0，返回1<br>
     * 小于0返回-1<br>
     * 等于0，返回0
     *
     * @param value
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午2:43:45
     */
    public static int sign(double value) {
        return sign(BigDecimal.valueOf(value));
    }

    /**
     * 检查字符串是否在in中
     *
     * @param str
     * @param in
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午1:27:56
     */
    public static boolean strIn(String str, String... in) {
        for (String i : in) {
            if (i.equals(str)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 检查字符串是否在in中
     *
     * @param str
     * @param in
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午1:27:56
     */
    public static boolean strNotIn(String str, String... in) {
        boolean isIn = false;
        for (String i : in) {
            if (i.equals(str)) {
                isIn = true;
            }
        }

        return !isIn;
    }

    /**
     * 当source不为null时，返回source，否则返回dest
     *
     * @param source
     * @param dest
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年2月4日 下午1:24:29
     */
    public static <T> T nvl(T source, T dest) {
        if (null == source) {
            return dest;
        }
        return source;
    }

    /**
     * 当source不为null时，返回source，否则返回dest
     *
     * @param source
     * @param dest
     * @return
     * @description
     * @version 1.0
     * @author songbo
     * @update 2015年4月2日 上午9:22:32
     */
    public static <T> T nvlNull(T source, T dest) {
        if (null == source || "null".equals(source)) {
            return dest;
        }
        return source;
    }

    /**
     * 字符串转换为数字类型，为空时返回null
     *
     * @param str
     * @return
     * @author songbo
     */
    public static BigDecimal str2Decimal(String str) {
        if (isNull(str)) {
            return null;
        }
        return new BigDecimal(str);
    }

    /**
     * 字符类型比较是否相等
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isEquals(String s, String t) {
        return StringUtils.isEquals(s, t);
    }
    
   /**
    * 产生随机字符串
    * @param length
    * @return
    */
    public static String getRandomString(int length) { //length表示生成字符串的长度  
        String base = "abcdefghijklmnopqrstuvwxyz";     
        Random random = new Random();     
        StringBuffer sb = new StringBuffer();     
        for (int i = 0; i < length; i++) {     
            int number = random.nextInt(base.length());     
            sb.append(base.charAt(number));     
        }     
        return sb.toString();     
     } 
    
    /**
     * 获取日期
     * @param dateStr
     * @return
     */
    public static Date getDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.DEFAULT_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            // TODO 错误码定义 日期格式转换异常
            //throw BusinessUtils.createBusinessException(IasErrCodeConstants.T00003);
            log.error(ExceptionUtils.getStackTrace(e));
		}
        return date;
    }
    
    /**
	 * 对金额精度进行处理
	 * @param type
	 * @param amt
	 * @param scale
	 * @return BigDecimal
	 */
	public static BigDecimal processAmt(String type, BigDecimal amt, Integer scale) {
        Integer scaleInt = 0;
		if (scale != null && scale > 0) {
            scaleInt = scale;
		}
		BigDecimal result = null;
		if ("R".equals(type)) {
			result = amt.setScale(scaleInt, BigDecimal.ROUND_HALF_UP);
		}
		if ("T".equals(type)) {
			result = amt.setScale(scaleInt, BigDecimal.ROUND_FLOOR);
		}
		return result;
	}

    /**
     * 根据日期计算星期几
     * @param dateString
     * @return
     */
    public static int dateForWeekday(String dateString){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(dateString);
        } catch (ParseException e) {
            // TODO 错误码定义 日期格式转换异常
            //throw BusinessUtils.createBusinessException(IasErrCodeConstants.T00003);
            log.error(ExceptionUtils.getStackTrace(e));
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 比较两个double类型的数字是否相等
     * @param a
     * @param b
     * @return
     */
    public static boolean doubleIsEquals(double a, double b){
        if(Double.isNaN(a) || Double.isNaN(b) || Double.isInfinite(a) || Double.isInfinite(b)){
            return false;
        }
        return (a - b) < 0.001d;
    }

    public static <T> byte[] serialize(T obj) {
        Serializer serializer = new JDKSerializer();
        return serializer.serialize(obj);
    }

    private static byte[] blobToBytes(Blob blob) {
        if (blob == null) {
            return null;
        }
        InputStream is = null;
        try {
            is = blob.getBinaryStream();
            byte[] bytes = new byte[(int) blob.length()];
            is.read(bytes);
            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static <T> T deserialize(Object obj) {
        if (obj == null) {
            return null;
        }
        byte[] bytes = null;
        InputStream is;
        if (obj instanceof Blob) {
            bytes = blobToBytes((Blob) obj);
        } else {
            bytes = (byte[]) obj;
        }
        Serializer serializer = new JDKSerializer();
        return (T) serializer.deserialize(bytes);

    }
}