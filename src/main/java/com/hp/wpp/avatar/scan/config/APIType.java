package com.hp.wpp.avatar.scan.config;

/**
 * Created by karanam on 8/29/2017.
 */
public enum APIType {
    eSCLScan("eSCLScan"),
    getESCLScannerStatus("getESCLScannerStatus"),
    getESCLScannerCapabilities("getESCLScannerCapabilities"),
    cancelESCLScanJob("cancelESCLScanJob");

    private String value;
    APIType(String value) {
        this.value=value;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
