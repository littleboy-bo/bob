package cn.com.bob.learn.base.serializer;

import java.io.*;

/**
 * 基于JDK的实现
 *
 * @author songbo
 */
public class JDKSerializer implements Serializer<Object> {

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(source);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
            return objectInputStream.readObject();
        } catch (Exception ex) {
            throw new SerializationException("Deserialized object failed." + ex);
        }
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires a Serializable payload " + "but received an object of type [" + object.getClass().getName() + "]");
        }
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            return byteStream.toByteArray();
        } catch (Exception e) {
            throw new SerializationException("Serialized object failed:" + object.getClass(), e);
        }
    }

}
