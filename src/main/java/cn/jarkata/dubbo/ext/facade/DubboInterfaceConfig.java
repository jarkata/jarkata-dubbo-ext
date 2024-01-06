package cn.jarkata.dubbo.ext.facade;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class DubboInterfaceConfig {
    private List<String> parameterType;
    private String methodName;
    private String url;
    private String serviceName;
    private String group;
    private String version;
    private String enableSsl;
    private boolean check;
    private Map<String, Object> dataMap;
    private int timeout = 2000;
    private String application;
    private boolean useRegister;
}
