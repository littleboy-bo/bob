package cn.com.bob.learn.base.util;



import cn.com.bob.learn.base.BobContext;
import cn.com.bob.learn.base.baseData.BaseGenerator;
import cn.com.bob.learn.base.bean.result.Result;
import cn.com.bob.learn.base.bean.result.Results;
import cn.com.bob.learn.base.constants.BobConstants;
import cn.com.bob.learn.base.exception.BobException;
import cn.com.bob.learn.base.exception.BusinessException;
import cn.com.bob.learn.base.serializer.JDKSerializer;
import cn.com.bob.learn.base.serializer.Serializer;
import cn.com.bob.learn.base.tuple.TwoTuple;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;
import java.util.Map;


public class BusinessUtils {

    /**
     * 取绝对值
     *
     * @param value
     * @return
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
     */
    public static BigDecimal abs(int value) {
        return BigDecimal.valueOf(Math.abs(value));
    }

    /**
     * 取绝对值
     *
     * @param value
     * @return
     */
    public static BigDecimal abs(long value) {
        return BigDecimal.valueOf(Math.abs(value));
    }

    /**
     * 取绝对值
     *
     * @param value
     * @return
     */
    public static BigDecimal abs(float value) {
        return BigDecimal.valueOf(Math.abs(value));
    }

    /**
     * 取绝对值
     *
     * @param value
     * @return
     */
    public static BigDecimal abs(double value) {
        return BigDecimal.valueOf(Math.abs(value));
    }

    /**
     * 字符串拼接
     *
     * @param s
     * @return
     */
    public static StringBuffer append(String s) {
        StringBuffer sb = new StringBuffer();
        sb.append(s);
        return sb;
    }

    /**
     * 创建错误结果
     *
     * @param errCode
     * @return
     */
    public static Result createResult(String errCode) {
        return new Result(errCode, getErrorMsg(errCode));
    }

    /**
     * 创建错误结果
     *
     * @param errCode
     * @param args
     * @return
     */
    public static Result createResult(String errCode, String... args) {
        return new Result(errCode, getErrorMsg(errCode, args));
    }

    /**
     * 创建错误结果
     *
     * @param errCode
     * @return
     */
    public static Results createResults(String... errCode) {
        Results rss = new Results();
        for (String err : errCode) {
            rss.addResult(createResult(err));
        }
        return rss;
    }

    /**
     * 创建错误结果
     *
     * @param errCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Results createResults(TwoTuple<String, String[]>... errCode) {
        Results rss = new Results();
        for (TwoTuple<String, String[]> tuple : errCode) {
            rss.addResult(createResult(tuple.first, tuple.second));
        }
        return rss;
    }

    /**
     * 获取异常
     *
     * @param errCode
     * @return
     */
    public static BusinessException createBusinessException(String errCode) {
        return createException(BusinessException.class, new Results(
                createResult(errCode)));
    }

    /**
     * 获取异常
     *
     * @param errCode
     * @param args
     * @return
     */
    public static BusinessException createBusinessException(String errCode,
                                                            String... args) {
        return createException(BusinessException.class, new Results(
                createResult(errCode, args)));
    }

    public static <T extends BusinessException> T createException(
            Class<T> clazz, Results rss) {
        T e = BaseGenerator.create(clazz).next(
                TupleUtils.getTwoTuple(new Class<?>[]{Results.class},
                        new Object[]{rss}));
        return e;
    }
    /**
     * 获取异常
     *
     * @param errCode
     */
    public static void throwException(String errCode) {
        TwoTuple<ErrorUtils.PlaceHolder, String> errs = ErrorUtils.getPlaceHolder(getErrorMsg(errCode));
        throwException(errCode, errs);
    }

    /**
     * 获取异常
     *
     * @param errCode
     * @param args
     */
    public static void throwException(String errCode,
                                      String... args) {
        TwoTuple<ErrorUtils.PlaceHolder, String> errs = ErrorUtils.getPlaceHolder(getErrorMsg(errCode, args));
        throwException(errCode, errs);
    }

    private static void throwException(String errCode, TwoTuple<ErrorUtils.PlaceHolder, String> errs) {
        if (ErrorUtils.PlaceHolder.A == errs.first || null == errs.first) {
            throw new BusinessException(errCode, errs.second);
        } /*else if (ErrorUtils.PlaceHolder.D == errs.first) {
            String authFlag = BobContext.getInstance().getSysHeadData("authFlag");
            if (authFlag != null && "N".equals(authFlag)) {
                throw new WithoutConfirmationException(errCode, errs.second);
            }
        } else if (ErrorUtils.PlaceHolder.O == errs.first) {
            String authFlag = GravityContext.getInstance().getSysHeadData("authFlag");
            if (authFlag != null && "N".equals(authFlag)) {
                throw new WithoutAuthorizationException(errCode, errs.second);
            }
        }*/ else if (ErrorUtils.PlaceHolder.IGNORE == errs.first) {
            // none
        }
    }

    /**
     * 将字符String类型转换为日期Date类型,默认转换为yyyyMMdd
     *
     * @param date
     * @return
     */
    public static Date convertStr2Date(String date) {
        return DateUtils.parse(date, DateUtils.DEFAULT_DATE_FORMAT);
    }

    /**
     * 将日期转为String类型，Date如果为null，返回null，默认转换为yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String convertDate2Str(Date date) {
        return convertDate2Str(date, DateUtils.DEFAULT_DATE_FORMAT);
    }

    /**
     * 将日期转为String类型
     *
     * @param date
     * @param pattern
     * @return
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
     * @param <T>
     * @return
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
     * @param <T>
     * @return
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
     * @param <T>
     * @return
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
     * @param <T>
     * @return
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
     * 获取错误描述
     *
     * @param errCode
     * @return
     */
    public static String getErrorMsg(String errCode) {
        return ErrorUtils.getErrorDesc(errCode, BobContext.getInstance().getSysHeadData(BobConstants.USER_LANGRAGE));
    }

    /**
     * 字符串为空或者null<br>
     * 对象为null
     *
     * @param obj
     * @return
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
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 获取错误描述
     *
     * @param errCode
     * @param args
     * @return
     */
    public static String getErrorMsg(String errCode, String... args) {
        return ErrorUtils.getParseErrorDesc(errCode, BobContext.getInstance().getSysHead().get(BobConstants.USER_LANGRAGE).toString(), args);
    }

    /**
     * 如果数据大于0，返回1<br>
     * 小于0返回-1<br>
     * 等于0，返回0
     *
     * @param value
     * @return
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
     */
    public static int sign(float value) {
        return sign(new BigDecimal(value));
    }

    /**
     * 如果数据大于0，返回1<br>
     * 小于0返回-1<br>
     * 等于0，返回0
     *
     * @param value
     * @return
     */
    public static int sign(double value) {
        return sign(new BigDecimal(value));
    }

    /**
     * 检查字符串是否在in中
     *
     * @param str
     * @param in
     * @return
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
     * @param <T>
     * @return
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
     * @param <T>
     * @return
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
     * 输入流反序列化为Object
     *
     * @param is
     * @param <T>
     * @return
     */
    public static <T> T streamToObject(InputStream is) {
        try {

            ObjectInputStream ois = new ObjectInputStream(is);
            T t = (T) ois.readObject();
            ois.close();
            return t;
        } catch (Exception e) {
            throw new BobException(e);
        }
    }

    /**
     * 应用头与RowAags转换方法
     *
     * @param appHead 应用头
     * @return 根据业务头计算翻页的参数；如果AppHead中的TotalNum为-1返回Null
     */
    /*public static RowArgs convertAppHead(Map appHead) {
        if (appHead == null) {
            throw createBusinessException("100311");
        }
        //上翻或者下翻  1：下翻 0：上翻
        int pgupOrPgdn = (Integer) appHead.get("pgupOrPgdn");
        //每页记录总数，即请求查询记录数
        int totalNum = (Integer) appHead.get("totalNum");
        //当前记录数
        int currentNum = (Integer) appHead.get("currentNum");
        // 约定totalNum=-1时，默认不翻页，返回NUll
        if (totalNum == -1) {
            return null;
        }
        // 每页记录数不能小于-1或者等于0
        if (totalNum <= 0 || currentNum < 0) {
            throw createBusinessException("100312");
        }
        //当前页数，（0~totalNum-1为第一页，currentNum=totalNum时为第二页）
        int currentPage = (currentNum / totalNum) + 1;
        //当前页首记录
        int start = (currentPage - 1) * totalNum;
        if (pgupOrPgdn == 1) {
            return new RowArgs(start, totalNum);
        } else {
            start -= totalNum;
            if (start < 0) {
                start = 0;
            }

            return new RowArgs(start, totalNum);
        }
    }*/

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