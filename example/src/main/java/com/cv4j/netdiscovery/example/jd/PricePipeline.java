package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.domain.ResultItems;
import com.cv4j.netdiscovery.core.pipeline.Pipeline;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tony on 2018/6/12.
 */
@Slf4j
public class PricePipeline implements Pipeline {

    public static void Array2CSV(List<List<String>> data, String path)
    {
        try {
            BufferedWriter out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true),"UTF-8"));
            for (int i = 0; i < data.size(); i ++) {
                for (int j = 0; j < data.get(i).size(); j++) {
                    out.write(DelQuota(data.get(i).get(j)));
//                    out.write(data.get(i).get(j));
                    out.write(",");
                }
                out.newLine();
            }
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static String DelQuota(String str)
    {
        String result = str;
//        String[] strQuota = { "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "`", ";", "'", ",", ".", "/", ":", "/,", "<", ">", "?" };
        String[] strQuota = {","};
        for (int i = 0; i < strQuota.length; i++)
        {
            if (result.indexOf(strQuota[i]) > -1)
                result = result.replace(strQuota[i], "");
        }
        return result;
    }

    @Override
    public void process(ResultItems resultItems) {
        if (resultItems.get("result") != null) {
            List<List<String>> carList = resultItems.get("result");
            Array2CSV(carList, "D:/tmp/bmw_detail.csv");
//            Array2CSV(result, "/root/livable/result.csv");
        }
//        try {
//            BufferedWriter out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/url.log", true),"UTF-8"));
//            out.write((String)resultItems.get("url"));
//            out.newLine();
//            out.flush();
//            out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
