package com.wordanalyzer.api;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**Word分词器
 * @description:
 * @Author:bella
 * @Date:2019/12/223:25
 * @Version:
 **/
@RestController
@RequestMapping("/word")
public class WordAnalyzerApi {
    /**
     * word中文分词
     * @param content
     * @return
     */
    @GetMapping("/analyzer")
    public String analyzer(@RequestParam("content") String content) {
        //移除停用词：
        List<Word> contents = WordSegmenter.seg(content);
        //保留停用词：
        //List<Word> contents = WordSegmenter.segWithStopWords(content);
        return contents.toString();
    }
}
