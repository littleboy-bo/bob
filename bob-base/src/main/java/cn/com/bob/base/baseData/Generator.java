package cn.com.bob.base.baseData;


import cn.com.bob.base.tuple.TwoTuple;
import cn.com.bob.base.tuple.TwoTuple;

/**
 * 生成器负责创建对象
 *
 * @author songbo
 * @version V1.0
 * @date 2015年1月7日 上午10:17:07
 */

public interface Generator<T> {

    /**
     * @return
     */
    T next();

    /**
     * @param parameter
     * @return
     */
    T next(TwoTuple<Class<?>[], Object[]> parameter);

}
