package cn.jarkata.dubbo.ext.facade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class DubboInterfaceConfig {
    private String application = "jarkata-dubbo-ext";
    private String url;
    private String serviceName;
    private String group;
    private String version;
    private String methodName;
    private List<String> parameterType;
    private String enableSsl = "N";
    private boolean check = false;
    private Map<String, Object> dataMap;
    private int timeout = 2000;
    private boolean useRegister = true;

    public DubboInterfaceConfig(String url, String serviceName, String group, String version, String methodName) {
        this.url = url;
        this.serviceName = serviceName;
        this.group = group;
        this.version = version;
        this.methodName = methodName;
    }

    public DubboInterfaceConfig(String url, String serviceName, String group, String version, String methodName, List<String> parameterType) {
        this.url = url;
        this.serviceName = serviceName;
        this.group = group;
        this.version = version;
        this.methodName = methodName;
        this.parameterType = parameterType;
    }

    public DubboInterfaceConfig(String application, String url, String serviceName, String group, String version, String methodName) {
        this.application = application;
        this.url = url;
        this.serviceName = serviceName;
        this.group = group;
        this.version = version;
        this.methodName = methodName;
    }

    public DubboInterfaceConfig(String application, String url, String serviceName, String group, String version, String methodName, List<String> parameterType) {
        this.application = application;
        this.url = url;
        this.serviceName = serviceName;
        this.group = group;
        this.version = version;
        this.methodName = methodName;
        this.parameterType = parameterType;
    }

    public DubboInterfaceConfig(String application, String url, String serviceName, String group, String version, String methodName, List<String> parameterType, String enableSsl) {
        this.application = application;
        this.url = url;
        this.serviceName = serviceName;
        this.group = group;
        this.version = version;
        this.methodName = methodName;
        this.parameterType = parameterType;
        this.enableSsl = enableSsl;
    }
}
