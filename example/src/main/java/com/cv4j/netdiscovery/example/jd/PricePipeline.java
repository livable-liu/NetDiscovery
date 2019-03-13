package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.domain.ResultItems;
import com.cv4j.netdiscovery.core.pipeline.Pipeline;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2018/6/12.
 */
@Slf4j
public class PricePipeline implements Pipeline {

    public static void Array2CSV(List<String> data, String path)
    {
        try {
            BufferedWriter out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true),"UTF-8"));
            for (int j = 0; j < data.size(); j++)
            {
                out.write(DelQuota(data.get(j)));
                out.write(",");
            }
            out.newLine();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static String DelQuota(String str)
    {
        String result = str;
        String[] strQuota = { "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "`", ";", "'", ",", ".", "/", ":", "/,", "<", ">", "?" };
        for (int i = 0; i < strQuota.length; i++)
        {
            if (result.indexOf(strQuota[i]) > -1)
                result = result.replace(strQuota[i], "");
        }
        return result;
    }

    @Override
    public void process(ResultItems resultItems) {
        List<String> result = new ArrayList<>();
        if (resultItems.get("name") != null) {
            result.add(resultItems.get("city"));
            result.add(resultItems.get("district"));
            result.add(resultItems.get("location"));
            result.add(resultItems.get("name"));
            result.add(resultItems.get("comment"));
            result.add(resultItems.get("avg"));
            result.add(resultItems.get("address"));
            result.add(resultItems.get("tel"));
            result.add(resultItems.get("url"));
            Array2CSV(result, "/home/livable/tmp/detail_url/result.csv");
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
