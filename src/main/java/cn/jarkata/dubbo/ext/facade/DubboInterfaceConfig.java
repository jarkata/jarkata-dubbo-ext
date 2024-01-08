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
    public static final String JARKATA_DUBBO_EXT = "jarkata-dubbo-ext";
    public static final String ENABLE_SSL_DEFAULT = "N";

    private String application = JARKATA_DUBBO_EXT;
    private String url;
    private String serviceName;
    private String group;
    private String version;
    private String methodName;
    private String[] parameterType;
    private String enableSsl = ENABLE_SSL_DEFAULT;
    private boolean check = false;
    private Map<String, Object> dataMap;
    private int timeout = 2000;
    private boolean useRegister = true;

    public DubboInterfaceConfig(String url, String serviceName, String group, String version, String methodName) {
        this(url, serviceName, group, version, methodName, JARKATA_DUBBO_EXT);
    }

    public DubboInterfaceConfig(String url, String serviceName, String group, String version, String methodName, String application) {
        this(url, serviceName, group, version, methodName, null, application);
    }

    public DubboInterfaceConfig(String url, String serviceName, String group, String version, String methodName, String[] parameterType) {
        this(url, serviceName, group, version, methodName, parameterType, JARKATA_DUBBO_EXT);
    }

    public DubboInterfaceConfig(String url, String serviceName, String group, String version, String methodName, String[] parameterType, String application) {
        this(url, serviceName, group, version, methodName, parameterType, application, ENABLE_SSL_DEFAULT);
    }

    public DubboInterfaceConfig(String url, String serviceName, String group, String version, String methodName, String[] parameterType, String application, String enableSsl) {
        this.url = url;
        this.serviceName = serviceName;
        this.group = group;
        this.version = version;
        this.methodName = methodName;
        this.parameterType = parameterType;
        this.application = application;
        this.enableSsl = enableSsl;
    }
}
