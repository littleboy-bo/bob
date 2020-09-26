package cn.com.bob.learn.base.constants;

/**
 * 常量
 *
 * @author songbo
 */
public class BobConstants {

    public static final String SYS_HEAD = "BOB-Head";
    public static final String APP_HEAD = "appHead";
    public static final String LOCAL_HEAD = "localHead";
    public static final String IN = "in";
    public static final String BODY = "body";
    public static final String TRAN_CODE = "tranCode";
    public static final String SEQ_NO = "seqNo";
    public static final String SUB_SEQ_NO = "subSeqNo";
    public static final String TRAN_TIMESTAMP = "tranTimestamp";
    public static final String RET_STATUS = "retStatus";
    public static final String RETS = "retArray";
    public static final String CONFIRM_RETS = "comfirmRet";
    public static final String RET_CODE = "retCode";
    public static final String RET_MSG = "retMsg";
    public static final String GLOBAL = "global";
    public static final String IN_MSG = "inMsg";
    public static final String VAR = "variables";
    public static final String TOKEN_ID = "UserAuthorization";
    public static final String MAC = "Mac";
    public static final String RESPONSE_AOP = "ResponseAop";
    public static final String ERR_LOG = "errLog";
    public static final String URI = "url";
    /**
     * 操作员语言<br>
     *  CHINESE－中文；<br>
     *  AMERICAN/ENGLISH－英文；
     */
    public static final String USER_LANGRAGE = "userLang";


    /**
     * 联机交易模式
     */
    public static final String TRAN_MODE_ONLINE = "online";
    /**
     * 批处理交易模式
     */
    public static final String TRAN_MODE_BATCH = "batch";
    /**
     * 定时任务模式
     */
    public static final String TRAN_MODE_TIMER = "timer";
    /**
     * 异步交易模式
     */
    public static final String TRAN_MODE_ASYNC = "async";
    /**
     * 执行成功时，默认返回的RET_CODE的值
     */
    public static final String CODE_SUCCESS = "000000";
    /**
     * 执行失败时，默认返回的RET_CODE的值
     */
    public static final String CODE_FAILED = "999999";
    /**
     * 执行成功时，默认返回的RET_MSG的值
     */
    public static final String MESSAGE_SUCCESS = "SUCCESS";
    /**
     * 执行成功时，返回报文中的RET_STATUS的值
     */
    public static final String STATUS_SUCCESS = "S";
    /**
     * 执行失败时，返回报文中的RET_STATUS的值
     */
    public static final String STATUS_FAILED = "F";
    /**
     * 通讯异常时，返回报文中的RET_STATUS的值
     */
    public static final String STATUS_UNKNOW = "X";
    /**
     * 需要授权时，返回报文中的RET_STATUS的值
     */
    public static final String STATUS_AUTH = "O";
    /**
     * 需要确认时，返回报文中的RET_STATUS的值
     */
    public static final String STATUS_CONF = "C";
    /**
     * 需要授权&确认时，返回报文中的RET_STATUS的值
     */
    public static final String STATUS_AUTH_CONF = "B";
    /**
     * 前置全局处理
     */
    public static final String PRE_GLOBAL_PROCESSING = "proGlobalProcessing";
    /**
     * 前置全局处理
     */
    public static final String POST_GLOBAL_PROCESSING = "postGlobalProcessing";

    public static final String POINT = ".";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String MID_LINE = "-";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String CHARSET = "UTF-8";

}
