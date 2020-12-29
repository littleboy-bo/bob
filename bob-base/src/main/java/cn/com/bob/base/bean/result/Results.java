package cn.com.bob.base.bean.result;

import cn.com.bob.base.bean.AbstractBean;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 面向三方的结果集集合
 *
 * @author songbo
 */
public class Results extends AbstractBean {

    private static final long serialVersionUID = -2921211860791873384L;


    public void setRet(List<Result> rets) {
        this.rets = rets;
    }

    public List<Result> getRet() {
        return rets;
    }

    List<Result> rets = new ArrayList<Result>();

    public Results() {
    }

    public Results(String retCode, String retMsg) {
        this.addResult(new Result(retCode, retMsg));
    }

    public Results(Result ret) {
        this.addResult(ret);
    }

    public Results(List<Result> rets) {
        this.rets = rets;
    }

    public List<Result> getRets() {
        return rets;
    }

    public Results addResult(Result ret) {
        this.rets.add(ret);
        return this;
    }

    public boolean isEmpty() {
        return this.rets.size() == 0;
    }


    @Override
    public String toString() {
        return JSONArray.toJSONString(rets);
    }
}
