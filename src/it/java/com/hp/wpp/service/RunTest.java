package com.hp.wpp.service;

import com.hp.wpp.service.tests.ComponentTestResourceLoader;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by dsingh on 7/19/2016.
 */

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin={"pretty","html:target/cucumber"},
        features = "src/it/resources/features"
)
@CucumberContextConfiguration
@ContextConfiguration(locations = {"classpath:cucumber.xml"})
public class RunTest {


    @Autowired
    com.hp.wpp.service.tests.ComponentTestResourceLoader ComponentTestResourceLoader;


}

