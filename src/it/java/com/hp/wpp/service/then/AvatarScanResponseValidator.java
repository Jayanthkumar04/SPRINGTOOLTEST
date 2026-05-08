package com.hp.wpp.service.then;

import com.hp.wpp.service.when.CancelScanJobFlowExecutor;
import com.hp.wpp.service.when.ScannerCapabilityJobFlowExecutor;
import com.hp.wpp.service.when.StatusScanJobFlowExecutor;
import com.hp.wpp.service.when.SubmitScanJobFlowExecutor;
import io.restassured.response.Response;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created by singhdev on 7/21/2016.
 */
public class AvatarScanResponseValidator {

    private static final Logger logger = LoggerFactory.getLogger(AvatarScanResponseValidator.class);

    @Then("^Avatar Scan service responds with status code (\\d+) for Submit$")
    public void response_code_validations_for_submit(int responseCode) {
        Response response = SubmitScanJobFlowExecutor.getResponse();
        response.then().assertThat().statusCode(responseCode);
        if (responseCode == 201) {
            String locationHeader = response.getHeaders().get("Location").getValue();
            Assert.notNull(locationHeader);
        }
        logger.debug("[CTF] Response to POST call on Avatar Scan", response.getBody());
        logger.debug("[CTF] Response Headers", response.getHeaders().get("Location"));
    }

    @Then("^Avatar Scan service responds with response code (\\d+) for Submit and internalErrorCode (.*)$")
    public void response_errorCode_validations_for_submit(int responseCode, String errorCode) {
        Response response = SubmitScanJobFlowExecutor.getResponse();
        response.then().assertThat().statusCode(responseCode);
        if (errorCode != null && !errorCode.isEmpty()) {
            String actualErrorCode = response.getHeader("Internal-Error-Code");
            Assert.isTrue(errorCode.equals(actualErrorCode), "Expected error code: " + errorCode + " but got: " + actualErrorCode);
        }
    }

    @Then("^Avatar Scan service responds with status code (\\d+) for Cancel$")
    public void response_code_validations_for_cancel(int responseCode) {
        Response response = CancelScanJobFlowExecutor.getResponse();
        response.then().assertThat().statusCode(responseCode);
    }

    @Then("^Avatar Scan service responds with response code (\\d+) for Cancel and internalErrorCode (.*)$")
    public void response_errorCode_validations_for_cancel(int responseCode, String errorCode) {
        Response response = CancelScanJobFlowExecutor.getResponse();
        response.then().assertThat().statusCode(responseCode);
        if (errorCode != null && !errorCode.isEmpty()) {
            String actualErrorCode = response.getHeader("Internal-Error-Code");
            Assert.isTrue(errorCode.equals(actualErrorCode), "Expected error code: " + errorCode + " but got: " + actualErrorCode);
        }
    }

    @Then("^Avatar Scan service responds with status code (\\d+) for Status")
    public void response_code_validations_for_status(int responseCode) {
        Response response = StatusScanJobFlowExecutor.getResponse();
        response.then().assertThat().statusCode(responseCode);
    }

    @Then("^Avatar Scan service responds with response code (\\d+) for Status and internalErrorCode (.*)$")
    public void response_errorCode_validations_for_status(int responseCode, String errorCode) {
        Response response = StatusScanJobFlowExecutor.getResponse();
        response.then().assertThat().statusCode(responseCode);
        if (errorCode != null && !errorCode.isEmpty()) {
            String actualErrorCode = response.getHeader("Internal-Error-Code");
            Assert.isTrue(errorCode.equals(actualErrorCode), "Expected error code: " + errorCode + " but got: " + actualErrorCode);
        }
    }

    @Then("^Avatar Scan service responds with status code (\\d+) for Scanner Capabilities")
    public void response_code_validations_for_scanner_capabilities(int responseCode) {
        Response response = ScannerCapabilityJobFlowExecutor.getResponse();
        response.then().assertThat().statusCode(responseCode);
    }

    @Then("^Avatar Scan service responds with response code (\\d+) for Scanner Capabilities and internalErrorCode (.*)$")
    public void response_errorCode_validations_scanner_capabilities(int responseCode, String errorCode) {
        Response response = ScannerCapabilityJobFlowExecutor.getResponse();
        response.then().assertThat().statusCode(responseCode);
        if(errorCode != null && !errorCode.isEmpty()){
            String actualErrorCode = response.getHeader("Internal-Error-Code");
            Assert.isTrue(errorCode.equals(actualErrorCode), "Expected error code: " + errorCode + " but got: " + actualErrorCode);
        }
    }
}
