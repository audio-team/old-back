package org.soundwhere.backend.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class COSConfig {

    @Value("${soundwhere.secretId}")
    private String secretId;
    @Value("${soundwhere.secretKey}")
    private String secretKey;
    @Value("${soundwhere.bucketRegion}")
    private String bucketRegion;

    @Bean
    public COSClient createClient() {
        var credentials = new BasicCOSCredentials(secretId, secretKey);
        var region = new Region(bucketRegion);
        var config = new ClientConfig(region);
        return new COSClient(credentials, config);
    }

    @Bean
    public TransferManager createManager(COSClient client) {
        var threadPool = Executors.newFixedThreadPool(8);
        var transferManager = new TransferManager(client, threadPool);
        var config = new TransferManagerConfiguration();
        transferManager.setConfiguration(config);
        return transferManager;
    }
}
