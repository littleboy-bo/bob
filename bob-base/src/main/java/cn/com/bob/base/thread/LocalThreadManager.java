package cn.com.bob.base.thread;

import java.util.HashMap;
import java.util.Map;

/**
 * @author songbo
 */
public class LocalThreadManager {

    public static ThreadLocal<Map<String, Object>> LOCAL = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    public static final String BOB_UID_KEY = "BOB_UID";

    public static final String TRAN_CONTEXT_KEY = "TRAN_CONTEXT";

    /**
     * 设置交易UID，跨线程、跨实例有效
     *
     * @param uid
     */
    public static void setUID(String uid) {
        // 线程中的platformId必须唯一，直到被清理
        if (!LOCAL.get().containsKey(BOB_UID_KEY)) {
            LOCAL.get().put(BOB_UID_KEY, uid);
        }
    }

    /**
     * 获取交易UID
     *
     * @return
     */
    public static String getUID() {
        return (String) LOCAL.get().get(BOB_UID_KEY);
    }

    /**
     * 设置交易上下文
     *
     * @param tranContext
     */
    public static <T> void setTranContext(T tranContext) {
        LOCAL.get().put(TRAN_CONTEXT_KEY, tranContext);
    }

    /**
     * 获取交易上下文
     *
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getTranContext() {
        return (T) LOCAL.get().get(TRAN_CONTEXT_KEY);
    }

    /**
     * 设置线程级的K/V信息
     *
     * @param key
     * @param vlaue
     */
    public static void put(String key, Object vlaue) {
        LOCAL.get().put(key, vlaue);
    }

    /**
     * 获取线程级的K/V信息
     *
     * @return
     */
    public static Object get(String key) {
        return LOCAL.get().get(key);
    }

    /**
     * 获取所有线程级的信息
     *
     * @return
     */
    public static Map<String, Object> getALlInfo() {
        return LOCAL.get();
    }

    /**
     * 清空线程
     */
    public static void remove() {
        LOCAL.remove();
    }

}
