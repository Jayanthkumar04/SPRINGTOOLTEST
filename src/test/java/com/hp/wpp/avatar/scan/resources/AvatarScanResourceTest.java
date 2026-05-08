
package com.hp.wpp.avatar.scan.resources;


import com.hp.wpp.avatar.BaseResourceTest;
import com.hp.wpp.avatar.exception.*;
import com.hp.wpp.avatar.scan.config.AvatarConfig;
import com.hp.wpp.avatar.service.AvatarService;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.*;


/**
 * Created by skpu on 7/21/2016.
 */

public class AvatarScanResourceTest extends BaseResourceTest{

    private byte[] scanRequest;

    private AvatarService avatarService;

    private AvatarConfig avatarConfig;
    private TJWSEmbeddedJaxrsServer server;
    private static final int PORT_NUMBER = 9199;
    protected static final String ROOT_URL = "http://localhost:" + PORT_NUMBER;

    @BeforeEach
    public void setUpTest(){
        try {
            scanRequest = loadFile("sampleTestInput.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHandlePostRequestForSuccessStatus() {
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        Mockito.when(statusLine.getStatusCode()).thenReturn(201);

        Header header =Mockito.mock(Header.class);
        Mockito.when(header.getName()).thenReturn("LOCATION");
        Mockito.when(header.getValue()).thenReturn("http://localhost:8080/eSCL/ScanJobs/68b1272c-0843-11e6-8a18-000c29d168c5");

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getAllHeaders()).thenReturn(new Header[]{header});

        Mockito.when(avatarService.eSCLScan(Mockito.anyString(),Mockito.any(InputStream.class))).thenReturn(httpResponse);
        Mockito.when(avatarConfig.getContentLengthLimit()).thenReturn(5242880);
        given()
                .body(scanRequest)
                .post(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs", "testPrinterId")
                .then().assertThat().
                statusCode(CREATED.getStatusCode());
    }

    @Test
    public void testHandlePostRequestForContentLengthLimitError(){
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);

        Header header =Mockito.mock(Header.class);
        Mockito.when(header.getName()).thenReturn("LOCATION");
        Mockito.when(header.getValue()).thenReturn("http://localhost:8080/eSCL/ScanJobs/68b1272c-0843-11e6-8a18-000c29d168c5");

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getAllHeaders()).thenReturn(new Header[]{header});

        Mockito.when(avatarService.eSCLScan(Mockito.anyString(),Mockito.any(InputStream.class))).thenReturn(httpResponse);
        Mockito.when(avatarConfig.getContentLengthLimit()).thenReturn(1500);
        given()
                .body(scanRequest)
                .post(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs", "testPrinterId")
                .then().assertThat().
                statusCode(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testHandlePostRequestForErrorStatus(){
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        Mockito.when(statusLine.getStatusCode()).thenReturn(400);

        Header header =Mockito.mock(Header.class);
        Mockito.when(header.getName()).thenReturn("LOCATION");
        Mockito.when(header.getValue()).thenReturn("http://localhost:8080/eSCL/ScanJobs/68b1272c-0843-11e6-8a18-000c29d168c5");

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getAllHeaders()).thenReturn(new Header[]{header});

        Mockito.when(avatarService.eSCLScan(Mockito.anyString(),Mockito.any(InputStream.class))).thenReturn(httpResponse);
        Mockito.when(avatarConfig.getContentLengthLimit()).thenReturn(5242880);
        given()
                .body(scanRequest)
                .post(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs", "testPrinterId")
                .then().assertThat().
                statusCode(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testHandlePostRequestWithAvatarNonRetriableExceptionForPrinterNotFound(){


        Mockito.when(avatarService.eSCLScan(Mockito.anyString(),Mockito.any(InputStream.class))).thenThrow(new AVSCWebsocketNotFoundException("Entity not found in websocket"));
        Mockito.when(avatarConfig.getContentLengthLimit()).thenReturn(5242880);
        given()
                .body(scanRequest)
                .post(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs", "testPrinterId")
                .then().assertThat().
                statusCode(CONFLICT.getStatusCode());
    }

    @Test
    public void testHandlePostRequestWithAvatarNonRetriableExceptionForInternalError(){

        AvatarNonRetriableException avatarNonRetriableException = new AvatarNonRetriableException(null,null,  "Bad request from CMS Service");
        Mockito.when(avatarService.eSCLScan(Mockito.anyString(),Mockito.any(InputStream.class))).thenThrow(avatarNonRetriableException);
        Mockito.when(avatarConfig.getContentLengthLimit()).thenReturn(5242880);
        given()
                .body(scanRequest)
                .post(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs", "testPrinterId")
                .then().assertThat().
                statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    public void testHandlePostRequestWithAvatarRetriableExceptionForInternalError(){

        AvatarRetriableException avatarRetriableException = new AvatarRetriableException(null, null,"Bad request from CMS Service");
        Mockito.when(avatarService.eSCLScan(Mockito.anyString(),Mockito.any(InputStream.class))).thenThrow(avatarRetriableException);
        Mockito.when(avatarConfig.getContentLengthLimit()).thenReturn(5242880);
        given()
                .body(scanRequest)
                .post(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs", "testPrinterId")
                .then().assertThat().
                statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }


    @Test
    public void testHandleGetScannerStatusRequestForSuccessStatus(){
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);

        Header header =Mockito.mock(Header.class);
        Mockito.when(header.getName()).thenReturn("Content-Length");
        Mockito.when(header.getValue()).thenReturn("0");

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getAllHeaders()).thenReturn(new Header[]{header});

        Mockito.when(avatarService.getESCLScannerStatus(Mockito.anyString())).thenReturn(httpResponse);

        given()
                .get(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScannerStatus", "testPrinterId")
                .then().assertThat().
                statusCode(OK.getStatusCode());
    }

    @Test
    public void testHandleGetScannerStatusRequestWithAvatarNonRetriableExceptionForPrinterNotFound(){

        Mockito.when(avatarService.getESCLScannerStatus(Mockito.anyString())).thenThrow(new AVSCWebsocketNotFoundException("Printer not found"));

        given()
                .get(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScannerStatus", "testPrinterId")
                .then().assertThat().
                statusCode(CONFLICT.getStatusCode());
    }


    @Test
    public void testHandleGetScannerStatusRequestWithAvatarRetriableExceptionForInternalError(){

        AvatarRetriableException avatarRetriableException = new AvatarRetriableException(null, null,"Bad request from CMS Service");
        Mockito.when(avatarService.getESCLScannerStatus(Mockito.anyString())).thenThrow(avatarRetriableException);

        given()
                .get(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScannerStatus", "testPrinterId")
                .then().assertThat().
                statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    public void testHandleGetScannerCapabilitiesRequestForSuccess(){
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        Header header = Mockito.mock(Header.class);

        Mockito.when(statusLine.getStatusCode()).thenReturn(200);
        Mockito.when(header.getName()).thenReturn("Content-Length");
        Mockito.when(header.getValue()).thenReturn("0");
        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getAllHeaders()).thenReturn(new Header[] {header});

        Mockito.when(avatarService.getESCLScannerCapabilities(Mockito.anyString())).thenReturn(httpResponse);

        given()
                .get(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScannerCapabilities", "ValidPrinter")
                .then().assertThat().statusCode(OK.getStatusCode());
    }

    @Test
    public void testHandleGetScannerCapabilitiesRequestForInvalidPrinterWithAvatarNonRetriableException(){

        Mockito.when(avatarService.getESCLScannerCapabilities(Mockito.anyString())).thenThrow(new AVSCWebsocketNotFoundException("Tunnel Url is nul"));

        given()
                .get(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScannerCapabilities", "InvalidPrinter")
                .then().assertThat().statusCode(CONFLICT.getStatusCode());
    }

    @Test
    public void testHandleGetScannerCapabilitiesRequestForValidPrinterWithAvatarRetriableException(){
        AvatarRetriableException avatarRetriableException = new AvatarRetriableException(null,null,"Bad Request from CMS" );

        Mockito.when(avatarService.getESCLScannerCapabilities(Mockito.anyString())).thenThrow(avatarRetriableException);

        given()
                .get(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScannerCapabilities", "ValidPrinter")
                .then().assertThat().statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }


    @Test
    public void testHandleDeleteJobRequestForSuccessStatus(){
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        Mockito.when(statusLine.getStatusCode()).thenReturn(200);

        Header header =Mockito.mock(Header.class);
        Mockito.when(header.getName()).thenReturn("Content-Length");
        Mockito.when(header.getValue()).thenReturn("0");

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getAllHeaders()).thenReturn(new Header[]{header});

        Mockito.when(avatarService.cancelESCLScanJob(Mockito.anyString(),Mockito.anyString())).thenReturn(httpResponse);

        given()
                .delete(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs/{jobId}", "testPrinterId","jobid123")
                .then().assertThat().
                statusCode(OK.getStatusCode());
    }

    @Test
    public void testHandleDeleteJobRequestForJobNotFound(){
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        Mockito.when(statusLine.getStatusCode()).thenReturn(404);

        Header header =Mockito.mock(Header.class);
        Mockito.when(header.getName()).thenReturn("Content-Length");
        Mockito.when(header.getValue()).thenReturn("0");

        Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
        Mockito.when(httpResponse.getAllHeaders()).thenReturn(new Header[]{header});

        Mockito.when(avatarService.cancelESCLScanJob(Mockito.anyString(),Mockito.anyString())).thenReturn(httpResponse);

        given()
                .delete(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs/{jobId}", "testPrinterId","jobid123")
                .then().assertThat().
                statusCode(NOT_FOUND.getStatusCode());
    }

    @Test
    public void testHandleDeleteJobRequestWithAvatarNonRetriableExceptionForPrinterNotFound(){

        Mockito.when(avatarService.cancelESCLScanJob(Mockito.anyString(),Mockito.anyString())).thenThrow(new AVSCWebsocketNotFoundException("Websocket returned not found"));

        given()
                .delete(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs/{jobId}", "testPrinterId","jobid123")
                .then().assertThat().
                statusCode(CONFLICT.getStatusCode());
    }


    @Test
    public void testHandleDeleteJobRequestWithAvatarRetriableExceptionForInternalError(){

        AvatarRetriableException avatarRetriableException = new AvatarRetriableException(null,null, "Bad request from CMS Service");
        Mockito.when(avatarService.cancelESCLScanJob(Mockito.anyString(),Mockito.anyString())).thenThrow(avatarRetriableException);

        given()
                .delete(ROOT_URL + "/v1" + "/{deviceId}/eSCL/ScanJobs/{jobId}", "testPrinterId","jobid123")
                .then().assertThat().
                statusCode(INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Override
    protected Object getResourceClassToBeTested(String methodName) {
        avatarConfig = Mockito.mock(AvatarConfig.class);
        avatarService = Mockito.mock(AvatarService.class);
        AvatarScanResource avatarScanResource = new AvatarScanResource(){
            @Override
            public AvatarService getAvatarService() {
                return avatarService;
            }

            public AvatarConfig getAvatarConfig(){
                return avatarConfig;
            }
        };
        return avatarScanResource;
    }
}
