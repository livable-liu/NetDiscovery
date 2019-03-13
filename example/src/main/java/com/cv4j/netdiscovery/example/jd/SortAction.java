package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.selenium.Utils;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 按照销量进行排序
 * Created by tony on 2018/6/12.
 */
public class SortAction extends SeleniumAction{

    @Override
    public SeleniumAction perform(WebDriver driver) {

        try {
            String saleSortBtn = "//*[@id=\"free_convert\"]";
            Utils.clickElement(driver, By.xpath(saleSortBtn));
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
