package cn.com.bob.learn.base.constants;

/**
 * 框架流水登记、更新使用常量
 * @author songbo
 */
public class TranInfoConstants {

    /**
     * 处理状态：P - 执行中
     */
    public static final String TRAN_STATUS = "P";

    /**
     * 重发标识：R - 重发
     */
    public static final String REPEAT_FLAG = "R";

    /**
     * 冲正标识：R - 已被冲正
     */
    public static final String REVERSAL_FLAG = "R";

    /**
     * 防重校验：PASS - 校验通过
     */
    public static final String ANTI_CHECK = "PASS";

    /**
     * 请求报文头字段：发起方微服务的服务名
     */
    public static final String REQUEST_SERVER = "requestServer";

    /**
     * 请求报文头字段：交易请求时间
     */
    public static final String TRAN_TIME = "tranTime";

    /**
     * 请求报文头字段：安全节点编号
     */
    public static final String SECURITY_NODE_NO= "securityNodeNo";

    /**
     * 请求报文头字段：重发标识
     */
    public static final String REPEAT ="repeatFlag";
}
