package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.selenium.Utils;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by tony on 2018/6/12.
 */
public class SearchAction extends SeleniumAction {

    @Override
    public SeleniumAction perform(WebDriver driver) {

        try {
            String searchBtn = "//a[contains(text(), \"快照\")]";
            if (driver.findElement(By.xpath(searchBtn)) != null) {
                Utils.clickElement(driver, By.xpath(searchBtn));

                String currentWindow = driver.getWindowHandle();//获取当前窗口句柄
                Set<String> handles = driver.getWindowHandles();//获取所有窗口句柄
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (currentWindow == it.next()) {
                        continue;
                    }
                    driver.switchTo().window(it.next());
                    break;
                }
            }
            Thread.sleep((int) (2000 + Math.random() * (4000 - 2000 + 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
