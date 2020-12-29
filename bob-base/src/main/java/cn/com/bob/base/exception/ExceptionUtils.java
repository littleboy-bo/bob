/**
 * <p>Title: Strings.java</p>
 * <p>Description: 字符串工具类
 * 目前实现了左右填充字符和Exception堆栈转为字符串</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: dcits</p>
 *
 * @author songbo
 * @date 2014年9月15日 下午2:02:19
 * @version V1.0
 */
package cn.com.bob.base.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author songbo
 * @version V1.0
 * 异常工具类
 * @date 2020-01-13
 */

public class ExceptionUtils {

    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return "NULL";
        }
        StringBuffer b = new StringBuffer();
        b.append(t.getMessage());
        b.append("\n");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        t.printStackTrace(ps);
        b.append(baos.toString());
        return b.toString();
    }

    public static Throwable getRootCause(Exception e) {
        Object cause;
        for(cause = e; ((Throwable)cause).getCause() != null; cause = ((Throwable)cause).getCause()) {
            ;
        }

        return (Throwable)cause;
    }
}
