/**
 * <p>Title: FiveTuple.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014-2015</p>
 * <p>Company: dcits</p>
 *
 * @author songbo
 * @date 2015年2月12日 上午10:46:51
 * @version V1.0
 */
package cn.com.bob.base.tuple;

/**
 * 五元组
 *
 * @author songbo
 * @version V1.0
 * @date 2015年2月12日 上午10:46:51
 */

public class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {

    public final E five;

    public FiveTuple(A first, B second, C three, D four, E five) {
        super(first, second, three, four);
        this.five = five;
    }
}
