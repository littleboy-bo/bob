/**
 * <p>Title: BasicGenerator.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: dcits</p>
 *
 * @author songbo
 * @date 2015年1月7日 上午10:28:21
 * @version V1.0
 */
package cn.com.bob.base.baseData;

import cn.com.bob.base.tuple.TwoTuple;
import cn.com.bob.base.exception.BobException;
import cn.com.bob.base.tuple.TwoTuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author songbo
 * @version V1.0
 * @date 2015年1月7日 上午10:28:21
 */

public class BaseGenerator<T> implements Generator<T> {

    private Class<T> type;

    public BaseGenerator(Class<T> type) {
        this.type = type;
    }

    @Override
    public T next() {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BobException(e);
        }
    }

    @Override
    public T next(TwoTuple<Class<?>[], Object[]> parameter) {
        try {
            Constructor<T> constructor = type.getConstructor(parameter.first);
            return constructor.newInstance(parameter.second);
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new BobException(e);
        }

    }

    public static <T> Generator<T> create(Class<T> type) {
        return new BaseGenerator<T>(type);
    }

}
