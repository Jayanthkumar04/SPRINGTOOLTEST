package com.hp.wpp.service.mock;


import com.google.common.io.CharStreams;
import com.hp.wpp.service.tests.ComponentTestResourceLoader;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;

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
 * Created by sundharb on 9/29/2015.
 */
public class WebsocketMock {

    private static String WEBSOCKET_BASE_URL = "/websocket";


    @Given("^Websocket tunnel returns the (.*) ESCL response with status (.*)$")
    public void getResponseFromWebsocketForSubmitJob(String responseType, int statusCode) throws Throwable {
        String printerResponse = null;
        if (responseType.equals("Submit")) {
            printerResponse = loadFile("samples/sampleTestOutput.txt");
        } else if (responseType.equals("Status")) {
            printerResponse = loadFile("samples/sampleTestOutputForStatus.txt");
        } else if (responseType.equals("Capability")) {
            printerResponse = loadFile("samples/sampleTestOutputForCapability.txt");
        } else if (responseType.equals("Cancel")) {
            printerResponse = loadFile("samples/sampleTestOutputForCancel.txt");
        }
        if(statusCode==404)
            printerResponse = new String("HTTP/1.1 404");
        else if(statusCode==401)
            printerResponse = new String("HTTP/1.1 401");
        else if(statusCode==500)
            printerResponse = new String("HTTP/1.1 500");
        else if(statusCode==403)
            printerResponse = new String("HTTP/1.1 403");
        else if(statusCode==400)
            printerResponse = new String("HTTP/1.1 400");
        else if(statusCode==503)
            printerResponse = new String("HTTP/1.1 503");

        if(printerResponse != null)
            whenHttp(ComponentTestResourceLoader.getCmsStubServer())
                .match(method(Method.PUT), matchesUri(Pattern.compile(WEBSOCKET_BASE_URL, Pattern.CASE_INSENSITIVE)))
                .then(status(HttpStatus.getHttpStatus(statusCode)), stringContent(printerResponse));

    }

    protected String loadFile(String fileName) throws IOException {
        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName)) {
            return CharStreams.toString(new InputStreamReader(inputStream));
        }catch (IOException e){
            throw new IOException(e);
        }
    }

}
