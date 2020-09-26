/**
 * <p>Title: Tuple.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014-2015</p>
 * <p>Company: dcits</p>
 *
 * @author songbo
 * @date 2015年2月12日 上午10:46:51
 * @version V1.0
 */
package cn.com.bob.learn.base.tuple;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 元组
 *
 * @author songbo
 * @version V1.0
 * @date 2015年2月12日 上午10:46:51
 */

public class Tuple implements Serializable {
    private static final long serialVersionUID = 1085178677050530148L;

    @SuppressWarnings("unchecked")
    public <T> T getField(TupleField field) {
        T t;
        try {
            Field f = this.getClass().getField(field.toString());
            t = (T) f.get(this);
        } catch (NoSuchFieldException | SecurityException
                | IllegalArgumentException | IllegalAccessException e) {
            t = null;
        }
        return t;
    }

    @Override
    public String toString() {
        try {
            JSONObject printJson = new JSONObject();
            JSONObject objJson = new JSONObject();
            objJson.put("clazz", this.getClass().getName());
            printJson.put("obj", objJson);
            printJson.put("bean", this);
            // 通过JSON将bean的属性输出
            return JSON.toJSONString(printJson, SerializerFeature.PrettyFormat);
        } catch (Throwable t) {
            return t.toString();
        }
    }
}
