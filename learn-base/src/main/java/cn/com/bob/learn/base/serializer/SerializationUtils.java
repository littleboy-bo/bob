package cn.com.bob.learn.base.serializer;

/**
 * 序列化处理工具类
 *
 * @author songbo
 */
public class SerializationUtils {

    private static final Serializer<String> STRING_SERIALIZER = new StringSerializer();

    private static final Serializer<Object> SERIALIZER = new HessianSerializer();

    /**
     * String序列化
     *
     * @param str
     * @return
     */
    public static byte[] serialize(final String str) {
        if (str == null) {
            throw new SerializationException(
                    "value sent to redis cannot be null");
        }
        return STRING_SERIALIZER.serialize(str);
    }

    /**
     * String反序列化
     *
     * @param data
     * @return
     */
    public static String deserialize(final byte[] data) {
        return STRING_SERIALIZER.deserialize(data);
    }

    /**
     * 对象序列化
     *
     * @param obj
     * @return
     */
    public static byte[] serializeObj(final Object obj) {
        return SERIALIZER.serialize(obj);
    }

    /**
     * 对象反序列化
     *
     * @param data
     * @return
     */
    public static Object deserializeObj(final byte[] data) {
        return SERIALIZER.deserialize(data);
    }

    public static Serializer<String> getStringSerializer() {
        return STRING_SERIALIZER;
    }

    public static Serializer<Object> getSerializer() {
        return SERIALIZER;
    }
}
