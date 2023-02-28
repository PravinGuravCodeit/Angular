package com.utils;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.ByAngularModel;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumHelper {

    public  WebDriver driver;
    NgWebDriver ngWebDriver;
    JavascriptExecutor jsExecutor;

    ByAngularModel first = ByAngular.model("first");

    public void setDriver(){

        WebDriverManager.chromedriver().setup();
        this.driver =new ChromeDriver();
        jsExecutor=(JavascriptExecutor) driver;
        ngWebDriver =new NgWebDriver(jsExecutor);
        driver.get("http://localhost:4200/");
        ngWebDriver.waitForAngularRequestsToFinish();
    }
}
