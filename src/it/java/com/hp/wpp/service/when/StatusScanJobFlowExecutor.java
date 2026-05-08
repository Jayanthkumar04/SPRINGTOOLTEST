package com.hp.wpp.service.when;

import com.hp.wpp.service.tests.ComponentTestResourceLoader;
import io.restassured.response.Response;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by singhdev on 7/21/2016.
 */
public class StatusScanJobFlowExecutor extends AvatarScanFlowAbstractExecutor {

    private static final Logger logger = LoggerFactory.getLogger(StatusScanJobFlowExecutor.class);

    private static Response response;
    private static String SERVICE_URL = "/eSCL/ScannerStatus";

    @When("^A (.*) GET job status rest call is made on AvatarScan with cloudId (.*) and with (.*)$")
    public void rest_call_for_avatar_scan_scanner_status_job_api(String urlState, String cloudId, String contentState) {
        try {
            response = scanner_status_job_rest_call_for_avatar_scan(urlState, contentState, cloudId);
            logger.debug("[CTF]Successfully fired GET ScannerStatus Call to Avatar Scan");
        } catch (Exception e) {
            logger.error("[CTF]Exception caught while firing a GET ScannerStatus Call to Avatar Scan");
            Assert.fail(e.getMessage());
        }
    }

    String getRootUrl(String urlState) {
        if ("INCOMPATIBLE_VERSION".equalsIgnoreCase(urlState))
            return "http://localhost:" + ComponentTestResourceLoader.PORT_NUMBER_FOR_AVATAR_SCAN_FLOW + "/v3";
        else return "http://localhost:" + ComponentTestResourceLoader.PORT_NUMBER_FOR_AVATAR_SCAN_FLOW + "/v1";
    }

    String getServiceUrl(String urlState) {
        if ("VALID".equalsIgnoreCase(urlState))
            return SERVICE_URL;
        else if ("INVALID".equalsIgnoreCase(urlState))
            return SERVICE_URL.replace("ScannerStatus", "badScannerStatus");
        return SERVICE_URL;
    }

    String getRequestPayload(String contentState) {
        if ("VALID_CONTENT".equalsIgnoreCase(contentState)) return "";
        return "";
    }

    public static Response getResponse() {
        return response;
    }
}
