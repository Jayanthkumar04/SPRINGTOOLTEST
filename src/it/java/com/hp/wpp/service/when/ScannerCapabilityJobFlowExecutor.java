package com.hp.wpp.service.when;

import com.hp.wpp.avatar.scan.config.AvatarConfig;
import com.hp.wpp.avatar.service.AvatarService;
import com.hp.wpp.avatar.service.impl.AvatarServiceImpl;
import com.hp.wpp.service.tests.ComponentTestResourceLoader;
import io.restassured.response.Response;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by singhdev on 7/27/2016.
 */
public class ScannerCapabilityJobFlowExecutor extends AvatarScanFlowAbstractExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ScannerCapabilityJobFlowExecutor.class);

    private static Response response;
    private static String SERVICE_URL = "/eSCL/ScannerCapabilities";

    @When("^A (.*) GET Scanner Capabilities rest call is made on AvatarScan with cloudId (.*) and with (.*)$")
    public void rest_call_for_avatar_scan_scanner_capabilities_job_api(String urlState, String cloudId, String contentState) {
        try {
            response = scanner_capabilities_job_rest_call_for_avatar_scan(urlState, contentState, cloudId);
            logger.debug("[CTF]Successfully fired GET scanner capabilities Call to Avatar Scan");
        } catch (Exception e) {
            logger.error("[CTF]Exception caught while firing a GET scanner capabilities Call to Avatar Scan");
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
            return SERVICE_URL.replace("ScannerCapabilities", "badScannerCapabilities");
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
