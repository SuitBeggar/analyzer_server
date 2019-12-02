package com.ikanalyzer.api;

import com.ikanalyzer.util.SensitiveWordUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @Author:bella
 * @Date:2019/12/223:33
 * @Version:
 **/
@RestController
@RequestMapping("/ik")
public class IKAnalyzerApi {

    @GetMapping("/analyzer")
    public String analyzer(@RequestParam("content") String content) throws IOException{
        SensitiveWordUtil sensitiveWordUtil = new SensitiveWordUtil();

        List segment = sensitiveWordUtil.segment(content);
        String word = null;
        //替换敏感词汇
        try {
            word = sensitiveWordUtil.replaceSensitiveWord(segment.toString(), "*");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return word;
    }
}
