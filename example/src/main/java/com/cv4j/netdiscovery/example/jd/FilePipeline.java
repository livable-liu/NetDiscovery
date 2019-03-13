package com.cv4j.netdiscovery.example.jd;

import com.cv4j.netdiscovery.core.domain.ResultItems;
import com.cv4j.netdiscovery.core.pipeline.Pipeline;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2018/6/12.
 */
@Slf4j
public class FilePipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems) {
        List<String> result = new ArrayList<>();
    }
}
