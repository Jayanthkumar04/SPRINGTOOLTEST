package com.hp.wpp.service.tests;

import com.hp.wpp.avatar.scan.resources.AvatarScanResource;
import com.xebialabs.restito.server.StubServer;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


/**
 * Created by singhdev on 7/19/2016.
 */
public class ComponentTestResourceLoader {

    @Autowired
    private AvatarScanResource avatarScanConfigResource;

    public static final int PORT_NUMBER_FOR_AVATAR_SCAN_FLOW = 9193;
    public static final int PORT_NUMBER_FOR_CMS = 9194;

    private static final Logger logger = LoggerFactory.getLogger(ComponentTestResourceLoader.class);
    private TJWSEmbeddedJaxrsServer server;

    public static StubServer getCmsStubServer() {
        return stubServer;
    }

    private static StubServer stubServer;

    @PostConstruct
    public void setup() {
        startServerForAvatarScanFlow();
        startCmsStubServer();
    }

    private void startCmsStubServer() {
        stubServer = new StubServer(PORT_NUMBER_FOR_CMS);
        stubServer.start();
        System.out.println("CMS Stub started");
    }

    private void startServerForAvatarScanFlow() {
        TJWSEmbeddedJaxrsServer server = new TJWSEmbeddedJaxrsServer();
        server.setPort(PORT_NUMBER_FOR_AVATAR_SCAN_FLOW);
        server.start();
        server.getDeployment().getRegistry().addSingletonResource(avatarScanConfigResource);
        logger.debug("[CTF] Embedded Server for avatar scan flow up on port: ", PORT_NUMBER_FOR_AVATAR_SCAN_FLOW);
    }

}
