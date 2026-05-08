package com.hp.wpp.avatar.scan.resources;


import com.hp.wpp.avatar.exception.*;
import com.hp.wpp.avatar.scan.config.APIType;
import com.hp.wpp.avatar.scan.config.AvatarConfig;
import com.hp.wpp.avatar.service.AvatarService;
import com.hp.wpp.avatar.service.DataDogMetricsUtil;
import com.hp.wpp.avatar.service.impl.AvatarServiceImpl;
import com.hp.wpp.logger.MDCConstants;
import com.hp.wpp.logger.WPPLogger;
import com.hp.wpp.logger.impl.WPPLoggerFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by skpu/mangesh
 */

@Component
@Path("/v1")
public class AvatarScanResource {

    private WPPLogger logger = WPPLoggerFactory.getLogger(AvatarScanResource.class);

    private static final String SCAN_JOB_URL = "/{deviceId}/eSCL/ScanJobs";

    private static final String SCAN_STATUS = "/{deviceId}/eSCL/ScannerStatus";

    private static final String CANCEL_SCAN_JOB = "/{deviceId}/eSCL/ScanJobs/{jobId}";

    private static final String SCAN_CAPABILITIES = "/{deviceId}/eSCL/ScannerCapabilities";

    private static final String LOCATION_HEADER = "Location";

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private AvatarConfig avatarConfig;

    @Autowired(required = false)
    private DataDogMetricsUtil dataDogMetricsUtil;

    /**
     * Signal printer to connect on websocket and process eSCLSCan submit request
     *
     * @param headers
     * @param reqBody
     * @param deviceId
     * @return
     */
    @POST
    @Path(AvatarScanResource.SCAN_JOB_URL)
    public Response handlePostRequest(@Context HttpHeaders headers,
                                      InputStream reqBody,
                                      @PathParam("deviceId") String deviceId) {
        MDC.put(MDCConstants.UID, MDC.get(MDCConstants.WPP_TRACE_KEY));
        HttpResponse responseData = null;
        logger.info("method=POST; executionType=eSCLScan; cloudId={}; executionState=STARTED", deviceId);
        int contentLengthLimit = getAvatarConfig().getContentLengthLimit();
        /*
        Check content length
         */
        try {
            if (null != headers.getRequestHeader(HttpHeaders.CONTENT_LENGTH) &&
                    !"".equals(headers.getRequestHeader(HttpHeaders.CONTENT_LENGTH).getFirst())) {
                long contentLength = Long.parseLong(headers.getRequestHeader(HttpHeaders.CONTENT_LENGTH).getFirst());
                if (contentLength > contentLengthLimit) {
                    throw new AVSCBadRequestException("AVS000002", "Request payload more than the defined limit" + contentLengthLimit / (1024 * 1024) + " MB");
                }
            } else {
                logger.debug("Content-length header missing");
                /*
                Content length is missing
                 */
                throw new AVSCBadRequestException("AVS000001", "Content length header is missing");
            }
            responseData = getResponse(deviceId,null,reqBody,APIType.eSCLScan);
            pushApiStatusMetric(APIType.eSCLScan, responseData.getStatusLine().getStatusCode());
            logger.info("method=POST; executionType=eSCLScan; cloudId={}; executionState=COMPLETED; status=SUCCESS", deviceId);
            return convertToResponse(responseData).build();

        }catch (Exception e){
            pushErrorMetrics(APIType.eSCLScan, e);
            logger.error("method=POST; executionType=eSCLScan; cloudId={}; executionState=COMPLETED; status=FAILURE; failureReason=\"{}\";", deviceId, e.getMessage());
            return generateErrorResponse(e);
        } finally {
            MDC.clear();
        }
    }

    /**
     * Get eSCLScan job status list
     *
     * @param headers
     * @param deviceId
     * @return
     */
    @GET
    @Path(AvatarScanResource.SCAN_STATUS)
    public Response handleGetRequest(@Context HttpHeaders headers,
                                     @PathParam("deviceId") String deviceId){
        MDC.put(MDCConstants.UID, MDC.get(MDCConstants.WPP_TRACE_KEY));
        HttpResponse responseData = null;
        logger.info("method=GET; executionType=getESCLScannerStatus; cloudId={}; executionState=STARTED", deviceId);
        try {
            responseData = getResponse(deviceId,null,null,APIType.getESCLScannerStatus);
            pushApiStatusMetric(APIType.getESCLScannerStatus, responseData.getStatusLine().getStatusCode());
            logger.info("method=GET; executionType=getESCLScannerStatus; cloudId={}; executionState=COMPLETED; status=SUCCESS", deviceId);
            return convertToResponse(responseData).build();
        } catch (Exception e) {
            pushErrorMetrics(APIType.getESCLScannerStatus, e);
            logger.error("method=GET; executionType=getESCLScannerStatus; cloudId={}; executionState=COMPLETED; status=FAILURE; failureReason=\"{}\";", deviceId, e.getMessage());
            return generateErrorResponse(e);
        } finally {
            MDC.clear();
        }
    }

    /**
     * Get eSCLScannerCapabilities
     *
     * @param headers
     * @param deviceId
     * @return
     */

    @GET
    @Path(AvatarScanResource.SCAN_CAPABILITIES)
    public Response handleGetScannerCapabilitiesRequest(@Context HttpHeaders headers,
                                                        @PathParam("deviceId") String deviceId) {
        MDC.put(MDCConstants.UID, MDC.get(MDCConstants.WPP_TRACE_KEY));
        HttpResponse responseData = null;
        logger.info("method=GET; executionType=getESCLScannerCapabilities; cloudId={}; executionState=STARTED", deviceId);
        try {
            responseData = getResponse(deviceId,null,null,APIType.getESCLScannerCapabilities);
            pushApiStatusMetric(APIType.getESCLScannerCapabilities, responseData.getStatusLine().getStatusCode());
            logger.info("method=GET; executionType=getESCLScannerCapabilities; cloudId={}; executionState=COMPLETED; status=SUCCESS", deviceId);
            return convertToResponse(responseData).build();
        }
        catch(Exception e) {
            pushErrorMetrics(APIType.getESCLScannerCapabilities, e);
            logger.error("method=GET; executionType=getESCLScannerCapabilities; cloudId={}; executionState=COMPLETED; status=FAILURE; failureReason=\"{}\";", deviceId, e.getMessage());
            return generateErrorResponse(e);
        }finally {
            MDC.clear();
        }
    }

    /**
     * Cancel eSCLScan job based on job Id.
     *
     * @param headers
     * @param deviceId
     * @param jobId
     * @return
     */
    @DELETE
    @Path(AvatarScanResource.CANCEL_SCAN_JOB)
    public Response handleCancelJobRequest(@Context HttpHeaders headers,
                                           @PathParam("deviceId") String deviceId,@PathParam("jobId") String jobId){
        MDC.put(MDCConstants.UID, MDC.get(MDCConstants.WPP_TRACE_KEY));
        HttpResponse responseData = null;
        logger.info("method=DELETE; executionType=cancelESCLScan; cloudId={}; executionState=STARTED", deviceId);
        try {
            responseData = getResponse(deviceId,jobId,null,APIType.cancelESCLScanJob);
            pushApiStatusMetric(APIType.cancelESCLScanJob, responseData.getStatusLine().getStatusCode());
            logger.info("method=DELETE; executionType=cancelESCLScan; cloudId={}; executionState=COMPLETED; status=SUCCESS", deviceId);
            return convertToResponse(responseData).build();
        } catch (Exception e) {
            pushErrorMetrics(APIType.cancelESCLScanJob, e);
            logger.error("method=DELETE; executionType=cancelESCLScan; cloudId={}; executionState=COMPLETED; status=FAILURE; failureReason=\"{}\";", deviceId, e.getMessage());
            return generateErrorResponse(e);
        } finally {
            MDC.clear();
        }
    }

    private HttpResponse getResponse(String deviceId, String jobId, InputStream reqBody, APIType apiType){
        try {
            if (apiType == APIType.eSCLScan) {
                return getAvatarService().eSCLScan(deviceId, reqBody);
            } else if (apiType == APIType.getESCLScannerStatus) {
                return getAvatarService().getESCLScannerStatus(deviceId);
            } else if (apiType == APIType.getESCLScannerCapabilities) {
                return getAvatarService().getESCLScannerCapabilities(deviceId);
            } else if (apiType == APIType.cancelESCLScanJob) {
                return getAvatarService().cancelESCLScanJob(deviceId, jobId);
            } else {
                logger.error("The apiType does not match existing API's, apiType={}" + apiType.getValue());
                throw new AVSCInternalErrorException("AVS000207", "internal error while getting response");
            }
        }catch(AVSCIOException e){
            throw new AVSCServiceException("AVS100109",e.getMessage());
        }catch(AVSCInvalidUrlException e){
            throw new AVSCBadRequestException("AVS000005", e.getMessage());
        }catch(AvatarSignalForbiddenException e){
            throw new AVSCInternalErrorException("AVS000202",e.getMessage());
        }catch(AvatarSignalHTTPIOException e){
            throw new AVSCServiceException("AVS100108",e.getMessage());
        }catch(AvatarSignalInternalErrorException e){
            throw new AVSCInternalErrorException("AVS000205", e.getMessage());
        }catch(AvatarSignalIOException e){
            throw new AVSCServiceException("AVS100102",e.getMessage());
        }catch(AvatarSignalServiceException e){
            throw new AVSCServiceException("AVS100101",e.getMessage());
        }catch(AvatarSignalInvalidRequestException e){
            throw new AVSCBadRequestException("AVS000003",e.getMessage());
        }catch(AvatarSignalNotFoundException e){
            throw new AVSCGoneException("AVS000101",e.getMessage());
        }catch(AvatarSignalUnauthorizedException e){
            throw new AVSCInternalErrorException("AVS000201",e.getMessage());
        }catch(AvatarTunnelHTTPIOException e){
            throw new AVSCServiceException("AVS100107",e.getMessage());
        }catch(AvatarTunnelInternalErrorException e){
            throw new AVSCInternalErrorException("AVS000203",e.getMessage());
        }catch(AvatarTunnelIOException e){
            throw new AVSCServiceException("AVS100104",e.getMessage());
        }catch(AvatarTunnelServiceException e){
            throw new AVSCServiceException("AVS100103",e.getMessage());
        }catch(AvatarTunnelInvalidRequestException e){
            throw new AVSCBadRequestException("AVS000004",e.getMessage());
        }catch(AvatarTunnelNotFoundException e){
            throw new AVSCGoneException("AVS000102",e.getMessage());
        }catch(AVSCConnectionUnavailableException e){
            throw new AVSCClientConflictException("AVS100001",e.getMessage());
        }catch(AVSCRetryInterruptedException e){
            throw new AVSCServiceException("AVS100106",e.getMessage());
        }catch(AVSCWebsocketNotFoundException e){
            throw new AVSCClientConflictException("AVS100002",e.getMessage());
        }catch(AVSCWebsocketBadRequestException e){
            throw new AVSCInternalErrorException("AVS000204",e.getMessage());
        }catch(AVSCWebsocketServiceException e){
            throw new AVSCServiceException("AVS100105",e.getMessage());
        }catch(AVSCWebsocketInternalErrorException e){
            throw new AVSCInternalErrorException("AVS000206", e.getMessage());
        }catch(AvatarSignalOverloadedException e){
            throw new AVSCServiceException("AVS100112",e.getMessage());
        }
    }

    private Response.ResponseBuilder convertToResponse(HttpResponse httpResponse)  {

        Response.ResponseBuilder builder = Response.status(Response.Status.fromStatusCode(httpResponse.getStatusLine().getStatusCode()));
        try {
            if (null != httpResponse.getEntity())
                builder.entity(httpResponse.getEntity().getContent());

            for (Header header : httpResponse.getAllHeaders()) {
                if (header.getName().equals(LOCATION_HEADER)) {
                    URI uri = new URI(new String(header.getValue()));
                    String finalURL = getAvatarConfig().getScanURL() + uri.getPath();
                    builder.header(header.getName(), finalURL);
                } else if (header.getName().equals(org.apache.http.HttpHeaders.TRANSFER_ENCODING)) {
                    builder.header(HttpHeaders.CONTENT_LENGTH, httpResponse.getEntity().getContentLength());
                } else if (header.getName().equals(org.apache.http.HttpHeaders.CONTENT_ENCODING) && header.getValue().equals("gzip")) {

                } else {
                    builder.header(header.getName(), header.getValue());
                }
            }
            return builder;
        } catch (IOException exc) {
            throw new AVSCServiceException("AVS100110", "IO exception occured while building response");
        } catch (URISyntaxException e) {
            throw new AVSCServiceException("AVS100111","URI Exception while translating request stream to bytes"+e.getMessage());
        }
    }

    protected Response generateErrorResponse(Throwable t) {
        Response.ResponseBuilder builder = null;
        if (t instanceof AVSCServiceException exp) {
            return buildResponse(HttpStatus.SC_SERVICE_UNAVAILABLE, exp.getErrorCode(), exp.getErrorMessage());
        } else if (t instanceof AVSCClientConflictException exp) {
            return buildResponse(HttpStatus.SC_CONFLICT, exp.getErrorCode(), exp.getErrorMessage());
        } else if (t instanceof AVSCBadRequestException exp) {
            return buildResponse(HttpStatus.SC_BAD_REQUEST, exp.getErrorCode(), exp.getErrorMessage());
        } else if (t instanceof AVSCInternalErrorException exp) {
            return buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, exp.getErrorCode(), exp.getErrorMessage());
        } else if (t instanceof AVSCGoneException exp) {
            return buildResponse(HttpStatus.SC_GONE, exp.getErrorCode(), exp.getErrorMessage());
        } else {
            return buildResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, null, t.getMessage());
        }
    }

    private Response buildResponse(int status, String errorCode, String message) {
        Response.ResponseBuilder builder = Response.status(status);
        if (errorCode != null)
            return builder.header("Internal-Error-Code", errorCode).entity(message).build();
        else
            return builder.entity(message).build();
    }

    public AvatarService getAvatarService() {
        return avatarService;
    }

    public AvatarConfig getAvatarConfig() { return avatarConfig; }

    private void pushApiStatusMetric(APIType apiType, int statusCode) {
        if (dataDogMetricsUtil == null) {
            return;
        }

        Map<String, String> tags = new HashMap<String, String>();
        tags.put("api", apiType.getValue());
        tags.put("status_code", String.valueOf(statusCode));
        dataDogMetricsUtil.pushCustomMetrics("avatarscan.api.response.count", tags);
    }

    private void pushErrorMetrics(APIType apiType, Throwable throwable) {
        pushApiStatusMetric(apiType, resolveStatusCode(throwable));
        pushWebsocketFailureMetricIfApplicable(apiType, throwable);
    }

    private void pushWebsocketFailureMetricIfApplicable(APIType apiType, Throwable throwable) {
        if (dataDogMetricsUtil == null) {
            return;
        }

        String message = throwable != null && throwable.getMessage() != null ? throwable.getMessage() : "";
        boolean isNodeNotReachable = throwable instanceof AVSCWebsocketServiceException
            || message.contains("HttpHostConnectException")
            || message.contains("websocket PUT call");

        if (!isNodeNotReachable) {
            return;
        }

        Map<String, String> tags = new HashMap<String, String>();
        tags.put("api", apiType.getValue());
        tags.put("failure_type", "node_not_reachable");
        dataDogMetricsUtil.pushCustomMetrics("avatarscan.websocket.failure.count", tags);
    }

    private int resolveStatusCode(Throwable throwable) {
        if (throwable instanceof AVSCServiceException) {
            return HttpStatus.SC_SERVICE_UNAVAILABLE;
        }
        if (throwable instanceof AVSCClientConflictException) {
            return HttpStatus.SC_CONFLICT;
        }
        if (throwable instanceof AVSCBadRequestException) {
            return HttpStatus.SC_BAD_REQUEST;
        }
        if (throwable instanceof AVSCInternalErrorException) {
            return HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }
        if (throwable instanceof AVSCGoneException) {
            return HttpStatus.SC_GONE;
        }
        return HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

}
