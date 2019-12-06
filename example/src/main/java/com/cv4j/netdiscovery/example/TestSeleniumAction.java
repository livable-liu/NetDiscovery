package com.cv4j.netdiscovery.example;

import com.cv4j.netdiscovery.selenium.Utils;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestSeleniumAction extends SeleniumAction {
    @Override
    public SeleniumAction perform(WebDriver driver) {
        try {
            driver.manage().window().maximize();
            Thread.sleep(2000);

            for (int i = 0; i < 10; i++) {
                Utils.scrollBy(driver, 500);
                Thread.sleep(2000);
            }

            WebElement queryInput = Utils.getWebElementByXpath(driver, "//*[@id='q']");
            queryInput.sendKeys("sinkinka");

            Utils.clickElement(driver, By.xpath("//*[@id='menu']/ul/li[3]/form/a"));

            Thread.sleep(2000);

            Utils.taskScreenShot(driver, "D:\\tmp\\test.png");
            
        } catch(InterruptedException e) {
        }

        return null;
    }
}