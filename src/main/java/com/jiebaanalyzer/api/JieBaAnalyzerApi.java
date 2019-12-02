package com.jiebaanalyzer.api;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.jiebaanalyzer.tfidf.Keyword;
import com.jiebaanalyzer.tfidf.TFIDFAnalyzer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**jieba分词器
 * @description:
 * @Author:bella
 * @Date:2019/12/222:53
 * @Version:
 **/
@RestController
@RequestMapping("/jieba")
public class JieBaAnalyzerApi {

    /**
     * jieba中文分词
     * @param content
     * @return
     */
    @GetMapping("/analyzer")
    public String analyzer(@RequestParam("content") String content) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> contents = segmenter.sentenceProcess(content);
        return contents.toString();
    }

    @GetMapping("/analyzerTfidf")
    public String analyzerTfidf(@RequestParam("content") String content) {
        TFIDFAnalyzer tfidfAnalyzer=new TFIDFAnalyzer();
        List<Keyword> keywords = tfidfAnalyzer.analyze(content, content.length());
        List<String> wordAndScores = new ArrayList<>();

        for (Keyword keyword:keywords) {
            String wordAndScore = keyword.getName()+":"+keyword.getTfidfvalue();
            wordAndScores.add(wordAndScore);
        }
        return wordAndScores.toString();
    }
}
