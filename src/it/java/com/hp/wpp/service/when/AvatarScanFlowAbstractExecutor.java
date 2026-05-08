package com.hp.wpp.service.when;

import io.cucumber.java.en.When;
import io.restassured.http.Header;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Created by singhdev on 7/21/2016.
 */
public abstract class AvatarScanFlowAbstractExecutor {

    static final String validContentType = "application/xml";

    abstract String getServiceUrl(String urlState);

    abstract String getRequestPayload(String contentState);

    abstract String getRootUrl(String urlState);

    Header getHeader(String headerType, String value) {
        return new Header(headerType, value);
    }

    public Response submit_job_rest_call_for_avatar_scan(String urlState, String contentState, String cloudId) {
        return given()
                .body(getRequestPayload(contentState))
                .header(getHeader("Content-type", validContentType))
                .post(getRootUrl(urlState) + "/" + cloudId + getServiceUrl(urlState));
    }

    public Response scanner_status_job_rest_call_for_avatar_scan(String urlState, String contentState, String cloudId) {
        return given()
                .header(getHeader("Content-type", validContentType))
                .get(getRootUrl(urlState) + "/" + cloudId + getServiceUrl(urlState));
    }

    public Response scanner_capabilities_job_rest_call_for_avatar_scan(String urlState, String contentState, String cloudId) {
        return given()
                .header(getHeader("Content-type", validContentType))
                .get(getRootUrl(urlState) + "/" + cloudId + getServiceUrl(urlState));
    }

    public Response cancel_job_rest_call_for_avatar_scan(String urlState, String contentState, String cloudId) {
        if ("EMPTY_JOB_ID".equalsIgnoreCase(contentState))
            return given()
                    .header(getHeader("Content-type", validContentType))
                    .delete(getRootUrl(urlState) + "/" + cloudId + getServiceUrl(urlState) + "");
        else {
            System.out.println("caling delete");
            Response response = given()
                    .header(getHeader("Content-type", validContentType))
                    .delete(getRootUrl(urlState) + "/" + cloudId + getServiceUrl(urlState) + "ea895c86-07aa-11e6-836f-000c29d168c5");

            System.out.println("responsestate: "+response.statusCode());
            return response;
        }
    }
}
