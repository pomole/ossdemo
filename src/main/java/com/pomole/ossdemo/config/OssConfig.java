package com.pomole.ossdemo.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Auther: nemo
 * @Date: 19-10-26 06:29
 * @Description: oss配置
 */
@Configuration
public class OssConfig implements InitializingBean {

    @Value("${aliyun.oss.endpoint}")
    private String oss_endpoint;

    @Value("${aliyun.oss.keyid}")
    private String oss_keyid;

    @Value("${aliyun.oss.keysecret}")
    private String oss_keysecret;

    @Value("${aliyun.oss.filehost}")
    private String oss_filehost;

    @Value("${aliyun.oss.bucketname}")
    private String oss_bucketname;


    public static String POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME1;
    public static String HOST;

    @Override
    public void afterPropertiesSet() throws Exception {
        POINT = oss_endpoint;
        KEY_ID = oss_keyid;
        KEY_SECRET = oss_keysecret;
        HOST = oss_filehost;
        BUCKET_NAME1 = oss_bucketname;
    }
}