package com.hp.wpp.service.when;

import com.hp.wpp.service.tests.ComponentTestResourceLoader;
import io.restassured.response.Response;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by singhdev on 7/19/2016.
 */
public class SubmitScanJobFlowExecutor extends AvatarScanFlowAbstractExecutor {

    private static final Logger logger = LoggerFactory.getLogger(SubmitScanJobFlowExecutor.class);

    private static Response response;
    private static String SERVICE_URL = "/eSCL/ScanJobs";

    @When("^A (.*) POST Submit Scan Job rest call is made on AvatarScan with cloudId (.*) and with (.*)$")
    public void rest_call_for_avatar_scan_submit_job_api(String urlState, String cloudId, String contentState) {
        try {
            response = submit_job_rest_call_for_avatar_scan(urlState, contentState, cloudId);
            logger.debug("[CTF]Successfully fired POST Call to Avatar Scan");
        } catch (Exception e) {
            logger.error("[CTF]Exception caught while firing a POST Call to Avatar Scan");
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
        if ("VALID_CONTENT".equalsIgnoreCase(contentState)) {
            return "{<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "\n" +
                    "<sc1:ScanSettings xmlns:sc1=\"http://schemas.hp.com/imaging/escl/2011/05/03\"\n" +
                    "\n" +
                    "    xmlns:pwg=\"http://www.pwg.org/schemas/2010/12/sm\" xmlns:dest=\"http://schemas.hp.com/imaging/httpdestination/2011/10/13\"\n" +
                    "\n" +
                    "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "\n" +
                    "    xsi:schemaLocation=\"http://schemas.hp.com/imaging/escl/2011/05/03../../schemas/eSCL.xsd\">\n" +
                    "\n" +
                    "    <pwg:Version>2.0</pwg:Version>\n" +
                    "\n" +
                    "    <sc1:Intent>Document</sc1:Intent>\n" +
                    "\n" +
                    "    <pwg:ScanRegions>\n" +
                    "\n" +
                    "        <pwg:ScanRegion>\n" +
                    "\n" +
                    "            <pwg:Height>3300</pwg:Height>\n" +
                    "\n" +
                    "            <pwg:ContentRegionUnits>escl:ThreeHundredthsOfInches</pwg:ContentRegionUnits>\n" +
                    "\n" +
                    "            <pwg:Width>2550</pwg:Width>\n" +
                    "\n" +
                    "            <pwg:XOffset>0</pwg:XOffset>\n" +
                    "\n" +
                    "            <pwg:YOffset>0</pwg:YOffset>\n" +
                    "\n" +
                    "        </pwg:ScanRegion>\n" +
                    "\n" +
                    "    </pwg:ScanRegions>\n" +
                    "\n" +
                    "    <pwg:DocumentFormat>application/pdf</pwg:DocumentFormat>\n" +
                    "\n" +
                    "    <sc1:XResolution>300</sc1:XResolution>\n" +
                    "\n" +
                    "    <sc1:YResolution>300</sc1:YResolution>\n" +
                    "\n" +
                    "    <sc1:ColorMode>RGB24</sc1:ColorMode>\n" +
                    "\n" +
                    "    <sc1:Duplex>false</sc1:Duplex>\n" +
                    "\n" +
                    "    <sc1:Brightness>1000</sc1:Brightness>\n" +
                    "\n" +
                    "    <sc1:CompressionFactor>25</sc1:CompressionFactor>\n" +
                    "\n" +
                    "    <sc1:Contrast>1000</sc1:Contrast>\n" +
                    "\n" +
                    "    <sc1:Highlight>179</sc1:Highlight>\n" +
                    "\n" +
                    "    <sc1:NoiseRemoval>0</sc1:NoiseRemoval>\n" +
                    "\n" +
                    "    <sc1:Shadow>25</sc1:Shadow>\n" +
                    "\n" +
                    "    <sc1:Threshold>0</sc1:Threshold>\n" +
                    "\n" +
                    "    <sc1:ContextID>{ContextID}</sc1:ContextID>\n" +
                    "\n" +
                    "    <sc1:ScanDestinations>\n" +
                    "\n" +
                    "        <dest:HttpDestination>\n" +
                    "\n" +
                    "            <pwg:DestinationUri>https://{hostname}/{service name}/jobs/{jobid}/upload</pwg:DestinationUri>\n" +
                    "\n" +
                    "            <dest:FilenamePrefix>0</dest:FilenamePrefix>\n" +
                    "\n" +
                    "            <dest:HttpMethod>POST</dest:HttpMethod>\n" +
                    "\n" +
                    "        </dest:HttpDestination>\n" +
                    "\n" +
                    "    </sc1:ScanDestinations>\n" +
                    "\n" +
                    "</sc1:ScanSettings>\n}";
        }
        return "";
    }

    public static Response getResponse() {
        return response;
    }
}
