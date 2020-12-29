package cn.com.bob.base.logback;

import ch.qos.logback.core.PropertyDefinerBase;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * logback 获取主机名
 * @author songbo
 * @date
 */
public class HostNamePropertyDefiner extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        }catch (UnknownHostException e){
            hostName = "default";
        }
        return hostName;
    }
}
