package cn.com.bob.learn.base.exception;


import cn.com.bob.learn.base.constants.BobConstants;

/**
 * BOB的根异常
 *
 * @author songbo
 */
public class BobException extends RootException {

    /**
     *
     */
    private static final long serialVersionUID = 5480079865489577671L;


    /**
     * Constructs a new BobException.
     *
     * @param message the reason for the exception
     */
    public BobException(String message) {
        super(message);
    }

    /**
     * Constructs a new BobException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public BobException(Throwable cause) {
        super(BobConstants.STATUS_FAILED,"999999","SystemError",  cause);
    }


    /**
     * Constructs a new BobException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public BobException(String message, Throwable cause) {
        super(BobConstants.STATUS_FAILED,"999999",message, cause);
    }

    public BobException(String code, String msg) {
        super(BobConstants.STATUS_FAILED,code,msg);
    }

}
