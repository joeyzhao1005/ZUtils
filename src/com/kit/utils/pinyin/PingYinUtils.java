package com.kit.utils.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类
 *
 * @author 龚刚
 */
public class PingYinUtils {


    private static PingYinUtils pingYinUtils;


    public static PingYinUtils getInstance() {
        if (pingYinUtils == null)
            pingYinUtils = new PingYinUtils();


        return pingYinUtils;
    }

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     *
     * @param inputString
     * @return
     */
    public String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new
                HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (int i = 0; i < input.length; i++) {
                if (java.lang.Character.toString(input[i]).
                        matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.
                            toHanyuPinyinStringArray(input[i],
                                    format);
                    output += temp[0];
                } else
                    output += java.lang.Character.toString(
                            input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }



    // 返回中文的首字母
    /**
     * 首字母
     *
     * @param str
     * @return
     */
    public  String getPinYinHeadChar(String str) {

        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    // 将字符串转移为ASCII码
    public  String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

}