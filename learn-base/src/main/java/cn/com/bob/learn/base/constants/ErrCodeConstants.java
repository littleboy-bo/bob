package cn.com.bob.learn.base.constants;



/**
 * 错误码常量
 * @author songbo
 */
public class ErrCodeConstants {


    /**
     * 错误码规则：
     *           系统简称（3位）+ 微服务简称（3位）+ 内部错误码（6位） 中间以半角点号分隔
     *
     *           内部错误码规则：
     *                    通用错误代码：C + 模块（2位）+序号（3位）
     *                    技术错误代码：T + 模块（2位）+序号（3位）
     *                    业务错误代码：B + 模块（2位）+序号（3位）
     *                    业务提示代码：W + 模块（2位）+序号（3位）
     *
     */

    //系统简称
    //private static final String SYSTEM_CODE = SpringApplicationContext.getContext().getEnvironment().getProperty("gravity.application.systemCode");
    //微服务简称
    //private static final String SERVER_CODE = SpringApplicationContext.getContext().getEnvironment().getProperty("gravity.application.requestServerCode");



    public static int DIRECT_PAGE = 0;
    public static int DIRECT_PROTOCOL = 1;
    public static String MSG_LEVEL_SUCCESS = "success";
    public static String MSG_LEVEL_INFO = "info";
    public static String MSG_LEVEL_WARN = "warning";
    public static String MSG_LEVEL_ERROR = "error";
    public static String RET_CODE_SUCCESS = "000000";
    public static String RET_CODE_EXCEPTION = "T99000";
    public static String RET_CODE_COMMUNICATION_PRIORITY_EXCEPTION = "T99001";
    public static String RET_CODE_COMMUNICATION_CONNECTING_EXCEPTION = "T99002";
    public static String RET_CODE_COMMUNICATION_AFTER_EXCEPTION = "T99003";
    public static String RET_CODE_SQLEXCEPTION = "T99100";
    public static String RET_CODE_METHOD_ARG_VALID = "T99200";
    public static String RET_CODE_DUPLICATE_KEY = "T99201";
    public static String RET_CODE_CHK_INVALIDATE = "T99202";
    public static String RET_CODE_INVALID_TOKEN = "T99203";
    public static String RET_CODE_INVALID_PASSWORD = "T99204";
    public static String RET_CODE_INVALID_PASSWORD_TIMES = "T99205";
    public static String RET_CODE_CODE_ERROR = "T99206";
    public static String RET_STATUS_SUCCESS = "S";
    public static String RET_STATUS_FAILE = "F";
    public static String RET_STATUS_TRANS_O = "O";
    public static String RET_STATUS_TRANS_C = "C";
    public static String RET_STATUS_TRANS_B = "B";
    public static String RET_STATUS_TRANS_X = "X";
}
