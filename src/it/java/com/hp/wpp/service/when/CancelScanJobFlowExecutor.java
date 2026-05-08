package com.hp.wpp.service.when;
import com.hp.wpp.avatar.service.AvatarService;
import com.hp.wpp.avatar.service.impl.AvatarServiceImpl;
import com.hp.wpp.service.tests.ComponentTestResourceLoader;
import io.restassured.response.Response;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by singhdev on 7/21/2016.
 */
public class CancelScanJobFlowExecutor extends AvatarScanFlowAbstractExecutor {

    private static final Logger logger = LoggerFactory.getLogger(CancelScanJobFlowExecutor.class);

    private static Response response;
    private static String SERVICE_URL = "/eSCL/ScanJobs/";

    @When("^A (.*) Cancel job rest call is made on AvatarScan with cloudId (.*) and with (.*)$")
    public void rest_call_for_avatar_scan_cancel_job_api(String urlState, String cloudId, String contentState) {
        try {
            response = cancel_job_rest_call_for_avatar_scan(urlState, contentState, cloudId);
            logger.debug("[CTF]Successfully fired DELETE Job Call to Avatar Scan");
        } catch (Exception e) {
            logger.error("[CTF]Exception caught while firing a DELETE Job Call to Avatar Scan");
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
            return SERVICE_URL.replace("ScanJobs", "badScanJobs");
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