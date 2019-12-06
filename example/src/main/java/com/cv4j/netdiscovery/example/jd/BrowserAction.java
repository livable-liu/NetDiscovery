package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.selenium.Utils;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * Created by tony on 2018/6/12.
 */
public class BrowserAction extends SeleniumAction{

    private String index;
    private String serial;

    public BrowserAction() {

    }

    public BrowserAction(String serial, String index) {
        this.serial = serial;
        this.index = index;
    }
    @Override
    public SeleniumAction perform(WebDriver driver) {

        try {
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//            driver.manage().window().fullscreen();
            Thread.sleep(3000);
            WebElement element = Utils.getWebElementByXpath(driver, "//*[@id=\"" + serial + "\"]");
            driver.manage().window().setPosition(element.getLocation());
//            Utils.clickElement(driver, By.xpath("//*[@id=\"" + serial + "\"]"));
            String searchInput = "//*[@id=\"" + index + "\"]/div[1]/a[1]";
            Utils.clickElement(driver, By.xpath(searchInput));
            String currentWindow = driver.getWindowHandle();//获取当前窗口句柄
            Set<String> handles = driver.getWindowHandles();//获取所有窗口句柄
            Iterator<String> it = handles.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (currentWindow.equals(next)) {
                    continue;
                }
                driver.switchTo().window(next);
                break;
            }
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
