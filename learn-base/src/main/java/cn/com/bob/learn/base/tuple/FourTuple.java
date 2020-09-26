/**
 * <p>Title: FourTuple.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014-2015</p>
 * <p>Company: dcits</p>
 *
 * @author songbo
 * @date 2015年2月12日 上午10:46:51
 * @version V1.0
 */
package cn.com.bob.learn.base.tuple;

/**
 * 四元组
 *
 * @author songbo
 * @version V1.0
 * @date 2015年2月12日 上午10:46:51
 */

public class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {

    public final D four;

    public FourTuple(A first, B second, C three, D four) {
        super(first, second, three);
        this.four = four;
    }
}
