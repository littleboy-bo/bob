/**
 * <p>Title: BobContext.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: dcits</p>
 *
 * @author songbo
 * @date 2014年11月13日 下午4:36:49
 * @version V1.0
 */
package cn.com.bob.learn.base;

import cn.com.bob.learn.base.thread.LocalThreadManager;
import cn.com.bob.learn.base.util.BusiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author songbo
 * @version V1.0
 * 基于统一缓存实现的交易全局上下文
 * @date 2020-01-03
 */
public class BobContext {

    private static Logger logger = LoggerFactory.getLogger(BobContext.class);

    private Map sysHead;

    private Map appHead;

    private Map localHead;

    private Map retInfo;

    /**
     * 全局事务控制开关
     */
    private boolean dtpFlag;

    /**
     * Spring事务控制开关
     */
    private boolean txFlag;

    /**
     * FlowEngine使用，是否是子服务请求
     */
    private boolean subRequest;

    /**
     * 是否批处理
     */
    private boolean isBatch;

    /**
     * 百信项目要求，上下文增加数据路由关键字段 20170410
     */
    private String routerKey;

    /**
     * @fields platformId
     */
    private String platformId = LocalThreadManager.getUID();

    /**
     * @fields properties
     */
    private Properties properties = new Properties();

    /**
     * @fields map
     */
    private Map map = new HashMap();
    /**
     * 是否登记恢复流程日志
     */
    private boolean recordCompensateFlow = false;
    /**
     * 恢复流程关键字
     */
    private String compensateFlowKey;

    /**
     * 错误节点异常信息
     */
    //private List<ErrorLog> errorLogs = new ArrayList<>();


    /**
     * 获取上下文
     *
     * @return
     */
    public static BobContext getInstance() {
        Stack<BobContext> contexts = getContextStack();
        if (contexts.empty()) {
            pushContext(new BobContext());
        }
        return contexts.peek();
    }

    /**
     * 获取上下文栈对象
     *
     * @return
     */
    private static Stack<BobContext> getContextStack() {
        Stack<BobContext> contexts = LocalThreadManager.getTranContext();
        if (null == contexts) {
            contexts = new Stack<>();
            LocalThreadManager.setTranContext(contexts);
        }
        return contexts;
    }

    /**
     * 将上下文压入栈顶
     *
     * @param context
     */
    public static BobContext pushContext(BobContext context) {
        if (logger.isInfoEnabled()) {
            logger.info("Press the context into the top of the stack!");
        }
        Stack<BobContext> contexts = getContextStack();
        return contexts.push(context);
    }

    /**
     * 移除栈顶对象
     *
     * @return
     */
    public static BobContext popContext() {
        if (logger.isInfoEnabled()) {
            logger.info("Remove the context from the top of the stack!");
        }
        Stack<BobContext> contexts = getContextStack();
        if (contexts.size() == 0) {
            return null;
        }
        return contexts.pop();
    }

    /**
     * 清空上下文
     *
     * @version 1.0
     * @author songbo
     * @date 2015年1月19日 下午3:20:27
     */
    public static void clear() {
        if (logger.isInfoEnabled()) {
            logger.info("Clear all context from the stack!");
        }
        getContextStack().clear();
    }

    /**
     * 复制上下文
     *
     * @return
     */
    public static BobContext copyBobContext() {
        Stack<BobContext> contexts = getContextStack();
        if (contexts.size() == 0) {
            return new BobContext();
        }
        BobContext context = contexts.peek();
        Map head = context.getSysHead();
        if (null != head) {
            head = BusiUtils.deserialize(BusiUtils.serialize(head));
        }
        Map appHead = context.getAppHead();
        if (null != appHead) {
            appHead = BusiUtils.deserialize(BusiUtils.serialize(appHead));
        }
        Map localHead = context.getLocalHead();
        if (null != localHead) {
            localHead = BusiUtils.deserialize(BusiUtils.serialize(localHead));
        }
        BobContext copyContext = new BobContext();
        copyContext.setSysHead(head);
        //copyContext.setLocalHead(localHead);
        //copyContext.setAppHead(appHead);
        //copyContext.setTxFlag(context.txFlag);
        //copyContext.setDtpFlag(context.dtpFlag);
        //copyContext.setRouterKey(context.routerKey);
        copyContext.setPlatformId(context.platformId);
        //copyContext.setSubRequest(context.subRequest);
        //opyContext.setIsBatch(context.isBatch);
        return copyContext;
    }

    public Map getSysHead() {
        return sysHead;
    }
    public Map getRetInfo() {
        return retInfo;
    }

    public String getSysHeadData(String key) {
        if (sysHead == null) {
            return null;
        }
        return (String) sysHead.get(key);
    }

    public String getPlatformId() {
        // 获取平台ID
        return platformId;
    }

    public void setPlatformId(String platformId) {
        // 获取平台ID
        this.platformId = platformId;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public Object getObject(String key) {
        return map.get(key);
    }

    public void setObject(String key, Object value) {
        map.put(key, value);
    }

    /**
     * 获取全局事务控制开关
     *
     * @return
     */
    public boolean isDtpFlag() {
        return dtpFlag;
    }

    /**
     * 设置全局事务控制开关
     *
     * @param dtpFlag
     */
    public void setDtpFlag(boolean dtpFlag) {
        this.dtpFlag = dtpFlag;
    }

    public boolean isBatch() {
        return isBatch;
    }

    public void setIsBatch(boolean isBatch) {
        this.isBatch = isBatch;
    }

    /**
     * 百信项目要求，上下文增加数据路由关键字段 20170410
     */
    public String getRouterKey() {
        return routerKey;
    }

    /**
     * 百信项目要求，上下文增加数据路由关键字段 20170410
     */
    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }

    public boolean isTxFlag() {
        return txFlag;
    }

    public void setTxFlag(boolean txFlag) {
        this.txFlag = txFlag;
    }

    public boolean txIsOpen() {
        return txFlag;
    }

    public void setSysHead(Map sysHead) {
        this.sysHead = sysHead;
    }

    public void setRetInfo(Map retInfo) {
        this.retInfo = retInfo;
    }

    public boolean isSubRequest() {
        return subRequest;
    }

    public void setSubRequest(boolean subRequest) {
        this.subRequest = subRequest;
    }

    public Map getAppHead() {
        return appHead;
    }

    public void setAppHead(Map appHead) {
        this.appHead = appHead;
    }

    public Map getLocalHead() {
        return localHead;
    }

    public void setLocalHead(Map localHead) {
        this.localHead = localHead;
    }

    public boolean isRecordCompensateFlow() {
        return recordCompensateFlow;
    }

    public void recordCompensateFlow() {
        this.recordCompensateFlow = true;
    }

    public String getCompensateFlowKey() {
        return compensateFlowKey;
    }

    public void setCompensateFlowKey(String compensateFlowKey) {
        this.compensateFlowKey = compensateFlowKey;
    }

    /**
     *
     * 设置成功提示码和提示信息
     * @param retCode
     * @param retMsg
     */
    public void addRetInfo(String retCode,String retMsg) {
        Map map = this.getRetInfo();
        if (BusiUtils.isNull(map)){
            map = new HashMap();
            map.put(retCode,retMsg);
        }else{
            map.put(retCode,retMsg);
        }
        this.setRetInfo(map);
    }

    /*public List<ErrorLog> getErrorLogs() {
        return errorLogs;
    }

    public void setErrorLogs(List<ErrorLog> errorLogs) {
        this.errorLogs = errorLogs;
    }*/
}
