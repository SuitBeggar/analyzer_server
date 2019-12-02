package com.ikanalyzer.util;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.*;

/**
 * @description:
 * @Author:bella
 * @Date:2019/12/223:39
 * @Version:
 **/
public class SensitiveWordUtil {
    /**
     * 敏感词集合
     */
    public static HashMap sensitiveWordMap;
    /**
     * 初始化敏感词库
     */
    public  void init() {
        Set<String> sensitiveWordSet = loadSensitiveWords(this.getClass().getResourceAsStream("/ik/sensitiveword.txt"));
        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap(sensitiveWordSet.size());
        for (String sensitiveWord : sensitiveWordSet) {
            sensitiveWordMap.put(sensitiveWord, sensitiveWord);
        }
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt 文字
     * @return 若包含返回true，否则返回false
     */
    public  boolean contains(String txt) throws Exception {
        init();
        boolean flag = false;
        List<String> wordList = segment(txt);
        for (String word : wordList) {
            if (sensitiveWordMap.get(word) != null) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt 文字
     * @return
     */
    public  Set<String> getSensitiveWord(String txt) throws IOException {
        init();
        Set<String> sensitiveWordList = new HashSet<>();

        List<String> wordList = segment(txt);
        for (String word : wordList) {
            if (sensitiveWordMap.get(word) != null) {
                sensitiveWordList.add(word);
            }
        }
        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt         文本
     * @param replaceChar 替换的字符，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符：*， 替换结果：我爱***
     * @return
     */
    public   String replaceSensitiveWord(String txt, char replaceChar) throws IOException {
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> sensitiveWordList = getSensitiveWord(txt);
        String replaceString;
        for (String sensitiveWord : sensitiveWordList) {
            replaceString = getReplaceChars(replaceChar, sensitiveWord.length());
            resultTxt = resultTxt.replaceAll(sensitiveWord, replaceString);
        }
        return resultTxt;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt        文本
     * @param replaceStr 替换的字符串，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符串：[屏蔽]，替换结果：我爱[屏蔽]
     * @return
     */
    public  String replaceSensitiveWord(String txt, String replaceStr) throws IOException {
        init();
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> sensitiveWordList = getSensitiveWord(txt);
        for (String sensitiveWord : sensitiveWordList) {
            resultTxt = resultTxt.replaceAll(sensitiveWord, replaceStr);
        }
        return resultTxt;
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar
     * @param length
     * @return
     */
    private  String getReplaceChars(char replaceChar, int length) {
        init();
        String resultReplace = String.valueOf(replaceChar);
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 对语句进行分词
     *
     * @param text 语句
     * @return 分词后的集合
     * @throws IOException
     */
    public   List segment(String text) throws IOException {
        List<String> list = new ArrayList<>();
        StringReader re = new StringReader(text);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex;
        while ((lex = ik.next()) != null) {
            list.add(lex.getLexemeText());
        }
        return list;
    }

    public static Set<String> loadSensitiveWords(InputStream in){
        Set<String> set = new HashSet<>();
        BufferedReader bufr;
        try
        {
            bufr = new BufferedReader(new InputStreamReader(in));
            String line=null;
            while((line=bufr.readLine())!=null) {
                set.add(line.trim());
            }
            try
            {
                bufr.close();
                return set;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return set;
    }
}
