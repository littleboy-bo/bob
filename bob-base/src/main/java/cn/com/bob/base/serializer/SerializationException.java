package cn.com.bob.base.serializer;

import cn.com.bob.base.exception.BobException;

/**
 * 序列化异常
 *
 * @author songbo
 */
public class SerializationException extends BobException {

    private static final long serialVersionUID = -8561411072499373859L;

    /**
     * Constructs a new <code>SerializationException</code> instance.
     *
     * @param msg
     * @param cause
     */
    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructs a new <code>SerializationException</code> instance.
     *
     * @param msg
     */
    public SerializationException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new <code>SerializationException</code> instance.
     *
     * @param cause
     */
    public SerializationException(Throwable cause) {
        super(cause);
    }

}
