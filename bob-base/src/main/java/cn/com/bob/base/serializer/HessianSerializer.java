package cn.com.bob.base.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.caucho.hessian.io.SerializerFactory;

import java.io.*;

/**
 * 基于Hessian的序列化
 *
 * @author songbo
 */
public class HessianSerializer implements Serializer<Object> {

    static private SerializerFactory serializerFactory;

    static {
        serializerFactory = new SerializerFactory();
    }

    static public HessianOutput createHessianOutput(OutputStream out) {
        HessianOutput hout = new HessianOutput(out);
        hout.setSerializerFactory(serializerFactory);
        return hout;
    }

    static public HessianInput createHessianInput(InputStream in) {
        HessianInput hin = new HessianInput(in);
        hin.setSerializerFactory(serializerFactory);
        return hin;
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            HessianInput hin = createHessianInput(input);
            return hin.readObject();
        } catch (IOException e) {
            throw new SerializationException("Deserialized object failed.", e);
        }
    }

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            createHessianOutput(bout).writeObject(t);
            return bout.toByteArray();
        } catch (IOException e) {
            throw new SerializationException("Serialized object failed:" + t.getClass(), e);
        }
    }

}
