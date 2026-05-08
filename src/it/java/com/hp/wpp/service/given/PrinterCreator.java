package com.hp.wpp.service.given;


import io.cucumber.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by singhdev on 7/19/2016.
 */

public class PrinterCreator {
    Printer printer = null;
    private static final Logger logger = LoggerFactory.getLogger(PrinterCreator.class);

    @Given("^a valid printer is present with a UUID$")
    public void a_valid_printer_is_present_with_UUID() {
        printer = new Printer();
        logger.debug("Printer's UUID is " + printer.getPrinterUUID());

    }

}
