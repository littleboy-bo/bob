/**
 * <p>Title: TupleUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: dcits</p>
 *
 * @author songbo
 * @date 2015年2月12日 上午11:04:04
 * @version V1.0
 */
package cn.com.bob.learn.base.util;

import cn.com.bob.learn.base.tuple.*;

/**
 * 元组工具类
 *
 * @author songbo
 * @version V1.0
 * @date 2015年2月12日 上午11:04:04
 */

public class TupleUtils {

    public static <A, B> Tuple getTuple(A first, B second) {
        return new TwoTuple<A, B>(first, second);
    }

    public static <A, B, C> Tuple getTuple(A first, B second, C three) {
        return new ThreeTuple<A, B, C>(first, second, three);
    }

    public static <A, B, C, D> Tuple getTuple(A first, B second, C three, D four) {
        return new FourTuple<A, B, C, D>(first, second, three, four);
    }

    public static <A, B, C, D, F> Tuple getTuple(A first, B second, C three,
                                                 D four, F five) {
        return new FiveTuple<A, B, C, D, F>(first, second, three, four, five);
    }

    public static <A, B, C, D, F, E> Tuple getTuple(A first, B second, C three,
                                                    D four, F five, E six) {
        return new SixTuple<A, B, C, D, F, E>(first, second, three, four, five,
                six);
    }

    public static <A, B> TwoTuple<A, B> getTwoTuple(A first, B second) {
        return new TwoTuple<A, B>(first, second);
    }

    public static <A, B, C> ThreeTuple<A, B, C> getThreeTuple(A first,
                                                              B second, C three) {
        return new ThreeTuple<A, B, C>(first, second, three);
    }

    public static <A, B, C, D> FourTuple<A, B, C, D> getFourTuple(A first,
                                                                  B second, C three, D four) {
        return new FourTuple<A, B, C, D>(first, second, three, four);
    }

    public static <A, B, C, D, F> FiveTuple<A, B, C, D, F> getFiveTuple(
            A first, B second, C three, D four, F five) {
        return new FiveTuple<A, B, C, D, F>(first, second, three, four, five);
    }

    public static <A, B, C, D, F, E> SixTuple<A, B, C, D, F, E> getSixTuple(
            A first, B second, C three, D four, F five, E six) {
        return new SixTuple<A, B, C, D, F, E>(first, second, three, four, five,
                six);
    }

}
