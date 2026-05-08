package com.hp.wpp.avatar.scan.config;

import com.hp.wpp.logger.WPPLogger;
import com.hp.wpp.logger.impl.WPPLoggerFactory;
import com.netflix.config.DynamicPropertyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by sundharb on 7/8/2015.
 */
@Component
public class AvatarConfig {

    static WPPLogger logger = WPPLoggerFactory.getLogger(AvatarConfig.class);

    @Autowired
    @Qualifier("archaiusDynamicFactoryAvatar")
    private DynamicPropertyFactory dynamicPropertyFactory = null;

    public int getContentLengthLimit(){
        return dynamicPropertyFactory.getIntProperty("avatar.content.length.limit",5242880).getValue();
    }

    public String getScanURL(){
        return dynamicPropertyFactory.getStringProperty("avatar.scan.url","https://avatarscan.hp.com").getValue();
    }
}

