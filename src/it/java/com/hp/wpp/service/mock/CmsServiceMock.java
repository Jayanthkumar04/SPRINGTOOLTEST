package com.hp.wpp.service.mock;

import com.google.common.io.CharStreams;
import com.hp.wpp.service.tests.ComponentTestResourceLoader;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.matchesUri;
import static com.xebialabs.restito.semantics.Condition.method;
/**
 * Created by skpu on 7/12/2016.
 */
@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class CmsServiceMock {


    private static String CMS_BASE_URL = "/cms/v1/entities";

    private static String CMS_SET_SIGNAL = "/signal";

    private static String CMS_GET_TUNNEL_CONFIG = "/tunnel_config/start_tunnel_2";



    @Given("^CMS for set signal returns status (.*)$")
    public void setSignalOnCmsStub(int statusCode) throws Throwable {
        whenHttp(ComponentTestResourceLoader.getCmsStubServer())
                .match(method(Method.PATCH), matchesUri(Pattern.compile(CMS_BASE_URL+CMS_SET_SIGNAL+"/[A-Za-z0-9]+",Pattern.CASE_INSENSITIVE)))
                .then(status(HttpStatus.getHttpStatus(statusCode)));

    }

    @Given("^CMS returns the tunnel config for the printer returns status (.*)$")
    public void getTunnelConfigFromCmsStub(int statusCode) throws Throwable {

        String tunnelResponse = loadFile("samples/sampleTunnelConfigResponse.txt").replace("websocket_mock","localhost:9194");
        whenHttp(ComponentTestResourceLoader.getCmsStubServer())
                .match(method(Method.GET), matchesUri(Pattern.compile(CMS_BASE_URL+"/[A-Za-z0-9]+"+ CMS_GET_TUNNEL_CONFIG, Pattern.CASE_INSENSITIVE)))
                .then(status(HttpStatus.getHttpStatus(statusCode)),stringContent(tunnelResponse));

    }


    protected String loadFile(String fileName) throws IOException {
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            return CharStreams.toString(new InputStreamReader(inputStream));
        }catch (IOException e){
            throw new IOException(e);
        }
    }


}
