package com.jh.util;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

    private static HanyuPinyinOutputFormat hanyuPinyinOutputFormat;

    static {
        hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 将字符串转换成拼音
     *
     * @param content
     * @return
     */
    public static String str2Pinyin(String content) {

        if (content == null) return content;

        StringBuilder sb = new StringBuilder();

        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            //逐个字符转换
            try {
                String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, hanyuPinyinOutputFormat);
                if (pinyin != null) {
                    sb.append(pinyin[0]);
                } else {
                    //没办法正常转换成拼音的字符
                    sb.append(c);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = str2Pinyin("重庆");
        System.out.println(s);
    }
}