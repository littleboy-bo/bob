package cn.com.bob.learn.base.constants;

public class ReversalConstants {

    /**
     * 冲正交易状态：N-新建（未冲正）
     */
    public static final String STATUS_NEW = "N";
    /**
     * 冲正交易状态：S-冲正成功
     */
    public static final String STATUS_SUCCESS = "S";
    /**
     * 冲正交易状态：F-冲正失败
     */
    public static final String STATUS_FAILED = "F";
    /**
     * 冲正交易状态： C-冲正作废（无需冲正）
     */
    public static final String STATUS_CANCELLED = "C";
    /**
     * 冲正交易状态： E-当前节点不冲正（无需冲正）
     */
    public static final String STATUS_REVERSAL_END = "E";
    /**
     * 冲正类型：L - 本地
     */
    public static final String REVERSAL_TYPE_LOCAL = "L";
    /**
     * 冲正类型：L - 本地小事务提交
     */
    public static final String REVERSAL_TYPE_LOCAL_TRANSACTIONAL = "C";
    /**
     * 冲正类型：R - 远程
     */
    public static final String REVERSAL_TYPE_REMOTE = "R";
}
