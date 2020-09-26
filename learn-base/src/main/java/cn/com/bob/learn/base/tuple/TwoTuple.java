/**
 * <p>Title: TwoTuple.java</p>
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
 * 二元组
 *
 * @author songbo
 * @version V1.0
 * @date 2015年2月12日 上午10:46:51
 */

public class TwoTuple<A, B> extends Tuple {

    public final A first;

    public final B second;

    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }
}
