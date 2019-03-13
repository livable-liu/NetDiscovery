package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.selenium.Utils;
import com.cv4j.netdiscovery.selenium.action.SeleniumAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;


/**
 * Created by tony on 2018/6/12.
 */
public class BrowserAction extends SeleniumAction{

    @Override
    public SeleniumAction perform(WebDriver driver) {

        try {
            String searchText = "D:\\20180822110051301923310051301246514.gif";
            String searchInput = "//*[@id=\"selectfiles\"]";
            Utils.clickElement(driver, By.xpath(searchInput));

            Thread.sleep(3000);

            Runtime.getRuntime().exec("D:\\FileUpload.exe");

            String saleSortBtn = "//*[@id=\"mubiaogeshi_xlsx\"]//a";
            Utils.clickElement(driver, By.xpath(saleSortBtn));
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e0) {
            e0.printStackTrace();
        }

        return null;
    }
}
