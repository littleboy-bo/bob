package cn.com.bob.base.util;

import cn.com.bob.base.exception.BobException;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtils
 *
 * @author songbo
 */
public final class StringUtils {

    private static String localIp = null;

    private static ReentrantLock lock = new ReentrantLock();

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    private static final Pattern KVP_PATTERN = Pattern
            .compile("([_.a-zA-Z0-9][-_.a-zA-Z0-9]*)[=](.*)");

    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    public static boolean isBlank(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * is empty string.
     *
     * @param str source string.
     * @return is empty.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * is not empty string.
     *
     * @param str source string.
     * @return is not empty.
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * @param s1
     * @param s2
     * @return equals
     */
    public static boolean isEquals(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }

    /**
     * is integer string.
     *
     * @param str
     * @return is integer
     */
    public static boolean isInteger(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return INT_PATTERN.matcher(str).matches();
    }

    public static int parseInteger(String str) {
        if (!isInteger(str)) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    /**
     * Returns true if s is a legal Java identifier.
     *
     * <a href="http://www.exampledepot.com/egs/java.lang/IsJavaId.html">more
     * info.</a>
     */
    public static boolean isJavaIdentifier(String s) {
        if (s.length() == 0 || !Character.isJavaIdentifierStart(s.charAt(0))) {
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if (!Character.isJavaIdentifierPart(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param values
     * @param value
     * @return contains
     */
    public static boolean isContains(String[] values, String value) {
        if (value != null && value.length() > 0 && values != null
                && values.length > 0) {
            for (String v : values) {
                if (value.equals(v)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * translat.
     *
     * @param src  source string.
     * @param from src char table.
     * @param to   target char table.
     * @return String.
     */
    public static String translat(String src, String from, String to) {
        if (isEmpty(src)) {
            return src;
        }
        StringBuilder sb = null;
        int ix;
        char c;
        for (int i = 0, len = src.length(); i < len; i++) {
            c = src.charAt(i);
            ix = from.indexOf(c);
            if (ix == -1) {
                if (sb != null) {
                    sb.append(c);
                }
            } else {
                if (sb == null) {
                    sb = new StringBuilder(len);
                    sb.append(src, 0, i);
                }
                if (ix < to.length()) {
                    sb.append(to.charAt(ix));
                }
            }
        }
        return sb == null ? src : sb.toString();
    }

    /**
     * split.
     *
     * @param ch char.
     * @return string array.
     */
    public static String[] split(String str, char ch) {
        List<String> list = null;
        char c;
        int ix = 0, len = str.length();
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (c == ch) {
                if (list == null) {
                    list = new ArrayList<String>();
                }
                list.add(str.substring(ix, i));
                ix = i + 1;
            }
        }
        if (ix > 0 && null != list) {
            list.add(str.substring(ix));
        }
        return list == null ? EMPTY_STRING_ARRAY : list
                .toArray(EMPTY_STRING_ARRAY);
    }

    /**
     * join string.
     *
     * @param array String array.
     * @return String.
     */
    public static String join(String[] array) {
        if (array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * join string like javascript.
     *
     * @param array String array.
     * @param split split
     * @return String.
     */
    public static String join(String[] array, char split) {
        if (array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(split);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * join string like javascript.
     *
     * @param array String array.
     * @param split split
     * @return String.
     */
    public static String join(String[] array, String split) {
        if (array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                sb.append(split);
            }
            sb.append(array[i]);
        }
        return sb.toString();
    }

    public static String join(Collection<String> coll, String split) {
        if (coll.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String s : coll) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(split);
            }
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * data key-value pair.
     *
     * @param str           string.
     * @param itemSeparator item separator.
     * @return key-value map;
     */
    private static Map<String, String> parseKeyValuePair(String str,
                                                         String itemSeparator) {
        String[] tmp = str.split(itemSeparator);
        Map<String, String> map = new HashMap<String, String>(tmp.length);
        for (int i = 0; i < tmp.length; i++) {
            Matcher matcher = KVP_PATTERN.matcher(tmp[i]);
            if (matcher.matches() == false) {
                continue;
            }
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }

    public static String getQueryStringValue(String qs, String key) {
        Map<String, String> map = StringUtils.parseQueryString(qs);
        return map.get(key);
    }

    /**
     * data query string to Parameters.
     *
     * @param qs query string.
     * @return Parameters instance.
     */
    public static Map<String, String> parseQueryString(String qs) {
        if (qs == null || qs.length() == 0) {
            return new HashMap<String, String>();
        }
        return parseKeyValuePair(qs, "\\&");
    }

    public static String toQueryString(Map<String, String> ps) {
        StringBuilder buf = new StringBuilder();
        if (ps != null && ps.size() > 0) {
            for (Map.Entry<String, String> entry : new TreeMap<String, String>(
                    ps).entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null && key.length() > 0 && value != null
                        && value.length() > 0) {
                    if (buf.length() > 0) {
                        buf.append("&");
                    }
                    buf.append(key);
                    buf.append("=");
                    buf.append(value);
                }
            }
        }
        return buf.toString();
    }

    public static String camelToSplitName(String camelName, String split) {
        if (camelName == null || camelName.length() == 0) {
            return camelName;
        }
        StringBuilder buf = null;
        for (int i = 0; i < camelName.length(); i++) {
            char ch = camelName.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                if (buf == null) {
                    buf = new StringBuilder();
                    if (i > 0) {
                        buf.append(camelName, 0, i);
                    }
                }
                if (i > 0) {
                    buf.append(split);
                }
                buf.append(Character.toLowerCase(ch));
            } else if (buf != null) {
                buf.append(ch);
            }
        }
        return buf == null ? camelName : buf.toString();
    }

    /**
     * 获取本地IP
     *
     * @return
     */
    public static final String getLocalHostIP() {
        try {
            lock.lock();
            if (StringUtils.isBlank(localIp)) {
                try {
                    localIp = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(
                            "[local-ip] an exception occured when get local ip address",
                            e);
                }
                StringBuffer sb = new StringBuffer();
                Random random = new Random();
                sb.append(localIp).append("-");
                for (int i = 0; i < 16; i++) {
                    sb.append(random.nextInt(10));
                }
                localIp = sb.toString();
            }
        } finally {
            lock.unlock();
        }
        return localIp;
    }

    /**
     * 判断是否为不适用标志
     *
     * @param str
     * @return
     */
    public static boolean isNotApplicable(String str) {
        return !isBlank(str) && "N/A".equalsIgnoreCase(str);
    }

    /**
     * 获取值，为空的化以默认值为准
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static String getValue(String str, String defaultValue) {
        if (isBlank(str)) {
            return defaultValue;
        }
        return str;
    }

    public static String rfillStr(String str, int len, String fillStr) {
        // modify for sonar
        StringBuilder sb = new StringBuilder().append(str);
        for (int i = 0; i < (len - str.length()); i++) {
            sb.append(fillStr);
        }
        return sb.toString();
    }

    public static String lfillStr(String str, int len, String fillStr) {
        // modify for sonar
        StringBuilder sb = new StringBuilder().append(str);
        for (int i = 0; i < (len - str.length()); i++) {
            sb.insert(0, fillStr);
        }
        return sb.toString();
    }

    /**
     * 定长报文补长
     *
     * @param str
     * @param lr
     * @param pd
     * @param length
     * @return
     */
    public static String pd(String str, String lr, String pd, int length) {
        if (pd.length() != 1) {
            throw new BobException("The complement character can only be one character.");
        }
        int strLength = str.length();
        if (strLength > length) {
            throw new BobException("The length of the string exceeds the specified length.");
        }
        if (length == strLength) {
            return str;
        }
        int pdCount = length - strLength;
        StringBuffer sb = new StringBuffer();
        if ("R".equals(lr)) {
            sb.append(str);
        }
        for (int i = 0; i < pdCount; i++) {
            sb.append(pd);
        }
        if ("L".equals(lr)) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static char[] getBr(String br) {
        if ("\\r".equals(br)) {
            return new char[]{'\r'};
        } else if ("\\r\\n".equals(br)) {
            return new char[]{'\r', '\n'};
        } else if ("\\n".equals(br)) {
            return new char[]{'\n'};
        } else {
            throw new BobException("Line breaks are incorrectly defined.");
        }
    }

    public static ByteBuffer getBytesByChars(char[] chars, String encoding) {
        Charset cs = Charset.forName(encoding);
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb;

    }

    public static int getByteLength(String str, String encoding) {
        if (str == null) {
            return 0;
        } else {
            byte[] bytes = new byte[0];
            try {
                bytes = str.getBytes(encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return bytes.length;
        }

    }

    public static int getByteLength(String str) {
        return getByteLength(str, "UTF-8");
    }

    public static String byteSubString(String str, int length) {
        return byteSubString(str, "UTF-8", length);
    }

    public static String byteSubString(String str, String encoding, int length) {
        if (str == null || str.length() == 0) {
            return str;
        }
        byte[] bytes = new byte[0];
        try {
            bytes = str.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int strLeng = bytes.length;
        if (length >= strLeng) {
            return str;
        } else {
            String newString = null;
            try {
                newString = new String(bytes, 0, length, encoding);
                int splitLength = newString.length();
                if (str.charAt(splitLength - 1) != newString.charAt(splitLength - 1)) {
                    if (splitLength < 2) {
                        newString = "";
                    } else {
                        newString = newString.substring(0, splitLength - 1);
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return newString;
        }
    }

    public static String append(String... args) {
        if (null == args) {
            return null;
        }
        if (args.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg);
            }
            return sb.toString();
        }
        return args[0];
    }
}