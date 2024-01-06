package cn.jarkata.dubbo.ext.facade;

import cn.jarkata.commons.utils.StringUtils;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.SslConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Dubbo泛化调用
 */
public class GenericFacade {

    /**
     * 调用Dubbo接口
     *
     * @param interfaceConfig 接口配置
     * @param data            接口请求数据
     * @return Dubbo接口响应结果
     */
    public Object invokeMethod(DubboInterfaceConfig interfaceConfig, List<Object> data) {
        Objects.requireNonNull(interfaceConfig, "DubboInterface Null");
        Objects.requireNonNull(StringUtils.trimToNull(interfaceConfig.getMethodName()), "MethodName Empty");
        Objects.requireNonNull(StringUtils.trimToNull(interfaceConfig.getServiceName()), "ServiceName Empty");
        Objects.requireNonNull(StringUtils.trimToNull(interfaceConfig.getUrl()), "Url Empty");

        List<String> parameterType = Optional.ofNullable(interfaceConfig.getParameterType()).orElse(new ArrayList<>(0));
        String[] parameterTypeArray = parameterType.toArray(new String[0]);

        data = Optional.ofNullable(data).orElse(new ArrayList<>(0));

        GenericService genericService = getGenericService(interfaceConfig);
        return genericService.$invoke(interfaceConfig.getMethodName(), parameterTypeArray, data.toArray());
    }


    private GenericService getGenericService(DubboInterfaceConfig interfaceConfig) {
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setGeneric("true");
        referenceConfig.setCheck(interfaceConfig.isCheck());
        referenceConfig.setGroup(interfaceConfig.getGroup());
        referenceConfig.setVersion(interfaceConfig.getVersion());
        referenceConfig.setInterface(interfaceConfig.getServiceName());
        referenceConfig.setTimeout(interfaceConfig.getTimeout());

        String configUrl = interfaceConfig.getUrl();
        if (interfaceConfig.isUseRegister()) {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress(configUrl);
            referenceConfig.setRegistry(registryConfig);
        } else {
            referenceConfig.setUrl(configUrl);
        }

        DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance();
        dubboBootstrap.application(interfaceConfig.getApplication());

        ProtocolConfig protocolConfig = new ProtocolConfig();
        if ("Y".equalsIgnoreCase(interfaceConfig.getEnableSsl())) {
            protocolConfig.setSslEnabled(true);
            String path = System.getProperty("dubbo.ssl.client-trust-cert-collection-path");
            SslConfig sslConfig = new SslConfig();
            sslConfig.setClientTrustCertCollectionPath(path);
            dubboBootstrap.ssl(sslConfig);
        }

        dubboBootstrap.protocol(protocolConfig);
        referenceConfig.setBootstrap(dubboBootstrap);
        return referenceConfig.get();
    }
}
