package cn.jarkata.dubbo.ext.facade;

import cn.jarkata.commons.utils.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.SslConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dubbo泛化调用
 */
public class DubboGenericFacade {

    private static final ConcurrentHashMap<String, GenericService> cache = new ConcurrentHashMap<>();
    private static final String SSL_ENABLED_Y = "Y";
    private static final String SSL_ENABLED = "ssl-enabled";

    /**
     * 调用Dubbo接口
     *
     * @param interfaceConfig 接口配置
     * @param data            接口请求数据
     * @return Dubbo接口响应结果
     */
    public Object invokeMethod(DubboInterfaceConfig interfaceConfig, List<Object> data) {
        data = Optional.ofNullable(data).orElse(new ArrayList<>(0));
        return invokeMethod(interfaceConfig, data.toArray());
    }

    public Object invokeMethod(DubboInterfaceConfig interfaceConfig, Object[] data) {
        Objects.requireNonNull(interfaceConfig, "DubboInterface Null");
        Objects.requireNonNull(StringUtils.trimToNull(interfaceConfig.getMethodName()), "MethodName Empty");
        Objects.requireNonNull(StringUtils.trimToNull(interfaceConfig.getServiceName()), "ServiceName Empty");
        Objects.requireNonNull(StringUtils.trimToNull(interfaceConfig.getUrl()), "Url Empty");
        String[] parameterTypeArray = interfaceConfig.getParameterType();

        parameterTypeArray = Optional.ofNullable(parameterTypeArray).orElse(new String[0]);
        data = Optional.ofNullable(data).orElse(new Object[0]);

        GenericService genericService = getGenericService(interfaceConfig);
        return genericService.$invoke(interfaceConfig.getMethodName(), parameterTypeArray, data);
    }

    private GenericService getGenericService(DubboInterfaceConfig interfaceConfig) {

        String cacheKey = buildCacheKey(interfaceConfig);
        GenericService genericService = cache.get(cacheKey);
        if (Objects.nonNull(genericService)) {
            return genericService;
        }

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setGeneric("true");
        referenceConfig.setCheck(interfaceConfig.isCheck());
        referenceConfig.setGroup(interfaceConfig.getGroup());
        referenceConfig.setVersion(interfaceConfig.getVersion());
        referenceConfig.setInterface(interfaceConfig.getServiceName());
        referenceConfig.setTimeout(interfaceConfig.getTimeout());

        String configUrl = interfaceConfig.getUrl();

        DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance();
        dubboBootstrap.application(interfaceConfig.getApplication());

        ProtocolConfig protocolConfig = new ProtocolConfig();
        if (SSL_ENABLED_Y.equalsIgnoreCase(interfaceConfig.getEnableSsl())) {
            protocolConfig.setSslEnabled(true);
            String path = System.getProperty("dubbo.ssl.client-trust-cert-collection-path");
            SslConfig sslConfig = new SslConfig();
            sslConfig.setClientTrustCertCollectionPath(path);
            dubboBootstrap.ssl(sslConfig);
        }
        dubboBootstrap.protocol(protocolConfig);
        referenceConfig.setBootstrap(dubboBootstrap);

        if (interfaceConfig.isUseRegister()) {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress(configUrl);
            referenceConfig.setRegistry(registryConfig);
        } else {
            URL url = URL.valueOf(configUrl);
            if (SSL_ENABLED_Y.equalsIgnoreCase(interfaceConfig.getEnableSsl())) {
                url = url.addParameter(SSL_ENABLED, SSL_ENABLED_Y);
            }
            referenceConfig.setUrl(url.toFullString());
        }
        genericService = referenceConfig.get();
        if (Objects.nonNull(genericService)) {
            cache.putIfAbsent(cacheKey, genericService);
            Runtime.getRuntime().addShutdownHook(new Thread(referenceConfig::destroy));
        }
        return genericService;
    }

    private String buildCacheKey(DubboInterfaceConfig interfaceConfig) {
        return interfaceConfig.getUrl() + interfaceConfig.getApplication() + interfaceConfig.getServiceName() + interfaceConfig.getGroup() + interfaceConfig.getVersion();
    }

}
