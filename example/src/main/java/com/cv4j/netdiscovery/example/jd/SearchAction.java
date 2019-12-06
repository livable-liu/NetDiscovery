package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.selenium.Utils;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by tony on 2018/6/12.
 */
@Slf4j
public class SearchAction extends SeleniumAction {

    @Override
    public SeleniumAction perform(WebDriver driver) {

        try {
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            Thread.sleep(3000);
            String searchBtn = "//nav//a[contains(text(),\" 配置表 \")]";
            Utils.clickElement(driver, By.xpath(searchBtn));
            Thread.sleep(3000);

            String currentWindow = driver.getWindowHandle();//获取当前窗口句柄
            Set<String> handles = driver.getWindowHandles();//获取所有窗口句柄
            Iterator<String> it = handles.iterator();
            String next = null;
            String jumpTo = null;
            while (it.hasNext()) {
                next = it.next();
                if (currentWindow.equals(next)) {
                    continue;
                }
                else {
                    jumpTo = next;
                }
            }
            driver.switchTo().window(jumpTo);
            Thread.sleep(3000);
        } catch (Exception e) {
            log.error("url=" + driver.getCurrentUrl());
            e.printStackTrace();
        }

        return null;
    }
}
