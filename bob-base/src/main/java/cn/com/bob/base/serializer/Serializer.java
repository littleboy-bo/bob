package cn.com.bob.base.serializer;

/**
 * 序列化接口
 *
 * @param <T>
 * @author songbo
 */
public interface Serializer<T> {

    /**
     * Serialize the given object to binary data.
     *
     * @param t object to serialize
     * @return the equivalent binary data
     * @throws SerializationException
     */
    byte[] serialize(T t) throws SerializationException;

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation
     * @return the equivalent object instance
     * @throws SerializationException
     */
    T deserialize(byte[] bytes) throws SerializationException;

}
