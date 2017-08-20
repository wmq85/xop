package com.mqoo.xop.starter.cache.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson配置文件
 * <p>
 * 属性与redisson配置属性保持一致，修改配置请参考redisson官方配置文件
 * 
 * @author mingqi.wang
 * @since 2017/08/09
 *
 */
@ConfigurationProperties(prefix = "xop.cache.redisson", ignoreUnknownFields = true)
public class RedissonProperties {
    private Map<String, Object> singleServerConfig = new HashMap<String, Object>();
    private Integer threads = 0;
    private Integer nettyThreads = 0;
    private boolean useLinuxNativeEpoll = false;
    private Map<String, String> codec = new HashMap<>();

    public Map<String, Object> getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(Map<String, Object> singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public Integer getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(Integer nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public boolean isUseLinuxNativeEpoll() {
        return useLinuxNativeEpoll;
    }

    public void setUseLinuxNativeEpoll(boolean useLinuxNativeEpoll) {
        this.useLinuxNativeEpoll = useLinuxNativeEpoll;
    }

    public Map<String, String> getCodec() {
        return codec;
    }

    public void setCodec(Map<String, String> codec) {
        this.codec = codec;
    }
}
