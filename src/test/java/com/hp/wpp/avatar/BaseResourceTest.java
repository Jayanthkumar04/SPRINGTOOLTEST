package com.hp.wpp.avatar;

import com.google.common.io.ByteStreams;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public abstract class BaseResourceTest {

    private TJWSEmbeddedJaxrsServer server;
    private static final int PORT_NUMBER = 9199;
    protected static final String ROOT_URL = "http://localhost:" + PORT_NUMBER;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(PORT_NUMBER);
        server.start();
        server.getDeployment().getRegistry().addSingletonResource(getResourceClassToBeTested(testInfo.getTestMethod().get().getName()));
    }

    protected abstract Object getResourceClassToBeTested( String methodName);

    @AfterEach
    public void tearDown() {
        server.stop();
    }

    protected byte[] loadFile(String fileName) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        return ByteStreams.toByteArray(inputStream);
    }
}