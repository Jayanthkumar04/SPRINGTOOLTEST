package com.hp.wpp.service.given;

import java.util.UUID;

/**
 * Created by singhdev on 7/19/2016.
 */

public class Printer {
    UUID printerUUID;

    public Printer(){
        this.printerUUID=UUID.randomUUID();
    }

    public UUID getPrinterUUID() {
        return printerUUID;
    }

}
