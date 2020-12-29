package cn.com.bob.base;


import cn.com.bob.base.bean.result.Result;
import cn.com.bob.base.bean.result.Results;
import cn.com.bob.base.constants.BobConstants;
import cn.com.bob.base.exception.BobException;
import cn.com.bob.base.exception.BusinessException;
import cn.com.bob.base.exception.RepeatException;
import cn.com.bob.base.exception.RootException;
import cn.com.bob.base.util.BusiUtils;
import cn.com.bob.base.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 供应用框架处理流程节点结果
 *
 * @author songbo
 */
public class BobResult {

    private static final Logger logger = LoggerFactory.getLogger(BobResult.class);

    private String retStatus;

    private Results rs;

    private Map out;

    private Set<String> retStatusSet = new HashSet();

    private Throwable throwable;

    public static BobResult newInstance(Throwable throwable, String retStatus) {
        if (logger.isErrorEnabled()) {
            logger.error("[{}] detailMessage is: " + throwable.getMessage(), throwable.getClass().getName());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("StackTrace:", throwable);
        }
        throwable = getRootCause(throwable);
        if(throwable instanceof BobException){
            if(StringUtils.isNotEmpty(((BobException) throwable).getRetStatus())) {
                retStatus = ((BobException) throwable).getRetStatus();
            }
        }
        Results rs;
        if(throwable instanceof RepeatException){
            if("R".equals(BobContext.getInstance().getSysHeadData("repeatFlag"))){
                return new BobResult((RepeatException)throwable);
            }
            try {
                rs = new Results("B00409", "交易重复！");
            }catch (Exception e){
                logger.error(e.getMessage());
                retStatus = BobConstants.STATUS_FAILED;
                rs = new Results("B00409", "交易重复！");
            }
        }else if(throwable instanceof BusinessException) {
            rs = ((BusinessException) throwable).getRets();
        }else if(throwable instanceof RootException){
            rs = new Results(((RootException) throwable).getRetCode(),((RootException) throwable).getRetMsg());
        }else {
            StringBuffer retMsg = new StringBuffer();
            retMsg.append(throwable.getClass().getSimpleName()).append(":");
            retMsg.append(throwable.getMessage());
            rs = new Results("999999", retMsg.toString());
            retStatus = BobConstants.STATUS_FAILED;
        }
        BobResult result = new BobResult(rs, retStatus);
        result.setThrowable(throwable);
        return result;
    }

    private static Throwable getRootCause(Throwable t){
        while(t.getCause() != null){
            t = t.getCause();
        }
        return t;
    }

    public static BobResult newInstance(Throwable throwable) {
        return newInstance(throwable, BobConstants.STATUS_FAILED);
    }

    /**
     * 无返回body的成功结果<br>
     * 用于成功场景
     */
    public BobResult() {
        addResult(BobConstants.CODE_SUCCESS, BobConstants.MESSAGE_SUCCESS);
        setRetStatus();
    }

    /**
     * 成功结果含结果Body<br>
     * 用于成功场景
     *
     * @param out 响应报文体数据
     */
    public BobResult(Map out) {
        addResult(BobConstants.CODE_SUCCESS, BobConstants.MESSAGE_SUCCESS);
        setRetStatus();
        this.out = out;
    }

    /**
     * 设置结果集合，根据结合结果判断状态
     *
     * @param rs 响应结果
     */
    public BobResult(Results rs) {
        this.rs = rs;
        setRetStatus();
    }

    /**
     * 设置结果集合，自定义结果状态
     *
     * @param rs        响应结果
     * @param retStatus 响应状态
     */
    public BobResult(Results rs, String retStatus) {
        this.rs = rs;
        this.retStatus = retStatus;
        retStatusSet.add(retStatus);
    }

    /**
     * 根据防重异常返回相应结果
     *
     * @param throwable
     */
    public BobResult(RepeatException throwable) {
        this.rs = throwable.getRs();
        this.retStatus = throwable.getRetStatus();
        this.out = throwable.getOut();
    }

    /**
     * 指定错误码的返回结果<br>
     * 用于失败场景
     *
     * @param errCode 响应码
     * @param errMsg  响应信息
     */
    public BobResult(String errCode, String errMsg) {
        addResult(errCode, errMsg);
        setRetStatus();
    }

    /**
     * 指定错误码集合返回结果<br>
     * 用于失败场景
     *
     * @param errCode 响应码集合
     * @param errMsg  响应信息集合
     */
    public BobResult(String[] errCode, String[] errMsg) {
        if (null != errCode && errCode.length > 0 && null != errMsg && errMsg.length > 0
                && errMsg.length == errMsg.length) {
            for (int i = 0; i < errCode.length; i++) {
                addResult(errCode[i], errMsg[i]);
            }
            setRetStatus();
        }
    }

    private void addResult(String errCode, String errMsg) {
        if (null == rs) {
            rs = new Results();
        }
        if (null == errCode && null == errMsg) {
            return;
        }
        // 检查错误码是否存在重复
        boolean exist = false;
        for (Result result : rs.getRets()) {
            if (result.getRetCode().equals(errCode) && result.getRetMsg().equals(errMsg)) {
                exist = true;
            }
            break;
        }
        if (exist) {
            return;
        }
        List<Result> result = rs.getRets();
        result.add(new Result(errCode, errMsg));
    }

    public void mergeResult(BobResult br) {
        if (null == br || null == br.getRs()) {
            return;
        }
        for (Result r : br.getRs().getRets()) {
            addResult(r.getRetCode(), r.getRetMsg());
        }
        if (!retStatusSet.contains(br.retStatus)){
            retStatusSet.add(br.retStatus);
        }
        setRetStatus();
        if (BobConstants.STATUS_SUCCESS.equals(this.retStatus)) {
            // 后面组件的out会覆盖原来原子服务的结果
            this.out = br.out;
            this.throwable = null;
        } else {
            // 如果失败，清空当前的响应
            this.out = null;
            if (br.throwable != null) {
                this.throwable = br.throwable;
            }
        }
    }

    public Results getRs() {
        return rs;
    }

    public void setRs(Results rs) {
        this.rs = rs;
        setRetStatus();
    }

    public boolean isSucces() {
        return BobConstants.STATUS_SUCCESS.equals(retStatus);
    }

    public String getRetStatus() {
        if (null == retStatus) {
            setRetStatus();
        }
        return retStatus;
    }

    public void setRetStatus(String retStatus) {
        this.retStatus = retStatus;
    }

    private void setRetStatus() {

        if (retStatusSet.isEmpty())
        {
            // modify for sonar
            if (null == rs) {
                return;
            }
            // 一个错误结果时
            if (!rs.isEmpty() && rs.getRets().size() == 1) {
                // 有且仅有一个错误码为000000；则成功，否则失败
                if (BobConstants.CODE_SUCCESS.equals(rs.getRets().get(0).getRetCode())) {
                    retStatus = BobConstants.STATUS_SUCCESS;
                } else {
                    retStatus = BobConstants.STATUS_FAILED;
                }
            } else {
                // 检查是否全部成功
                boolean success = false;
                boolean fail = false;
                List<Result> failResults = new ArrayList<Result>();
                for (Result result : rs.getRets()) {
                    // 检查是否含有000000结果
                    if (BobConstants.CODE_SUCCESS.equals(result.getRetCode())) {
                        success = true;
                    } else {
                        fail = true;
                        failResults.add(result);
                    }
                }

                // 全错
                if (!success && fail) {
                    retStatus = BobConstants.STATUS_FAILED;
                    // 全成功
                } else if (success && !fail) {
                    retStatus = BobConstants.STATUS_SUCCESS;
                } else if (success && fail) {
                    // 过滤掉000000错误码
                    rs = new Results(failResults);
                    retStatus = BobConstants.STATUS_FAILED;
                }
            }
        }
        else {
            if (retStatusSet.size() == 1){
                retStatus = retStatusSet.iterator().next();
            }
            else {
                if (retStatusSet.contains(BobConstants.STATUS_FAILED)){
                    retStatus = BobConstants.STATUS_FAILED;
                }
                else {
                    retStatus = BobConstants.STATUS_AUTH_CONF;
                }
            }
        }
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Map getOut() {
        return out;
    }

    public void setOut(Map out){
        this.out = out;
    }

    @Override
    public String toString() {
        return "RetStatus [" + retStatus + "] " + rs + " Out [" + (null == out ? "" : out) + "]";
    }

    public Map getReturnMap() {
        Map out = new HashMap();
        if (null != getOut()) {
            out.putAll(getOut());
        }
        Map sysHead = BobContext.getInstance().getSysHead();
        if(sysHead==null){
            sysHead = new HashMap();
        }
        out.put(BobConstants.SYS_HEAD, sysHead);
        List<Map> rets = new ArrayList<>();

        Map<String,String> retInfo = BobContext.getInstance().getRetInfo();
        if (BobConstants.STATUS_SUCCESS.equals(getRetStatus()) && BusiUtils.isNotNull(retInfo)){
            for (Map.Entry<String,String> entry : retInfo.entrySet()){
                Map newRet = new HashMap();
                newRet.put(BobConstants.RET_CODE, entry.getKey());
                newRet.put(BobConstants.RET_MSG, entry.getValue());
                rets.add(newRet);
            }
        }else{
            for (Result result : this.rs.getRets()) {
                Map ret = new HashMap();
                ret.put(BobConstants.RET_CODE, result.getRetCode());
                ret.put(BobConstants.RET_MSG, result.getRetMsg());
                rets.add(ret);
            }
        }
        sysHead.put(BobConstants.RETS, rets);
        sysHead.put(BobConstants.RET_STATUS, getRetStatus());
        return out;
    }
}