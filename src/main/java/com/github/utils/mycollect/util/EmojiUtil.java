/**
 * Copyright 1999-2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com
 */
package com.github.utils.mycollect.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Emoji表情处理类
 *
 * 说明：
 * 此类用于数据库如果使用urf8 保存emoji表情报错的情况,原因是emoji是utf8但数据库的utf8太短，保存不了全部的emoji表情
 * 要么把数据库改成utf8mb4,要么使用此类进行转换，利用的是html的unicode的显示表达方式做变通处理
 *
 * 使用步骤：
 * 1.使用containsEmoji()判断是否有emoji表情
 * 2.使用convertEmojiToUnicode()转化emoji表情成unicode 显示编码
 * 3.若需求只需要纯文本，则直接使用 filterEmoji
 *
 * @author zhouwenzhe
 *
 */
public class EmojiUtil {

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return true
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {

            return false;
        }

        for (int i = 0; i < source.length(); i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 删除所有Emoji表情
     *
     * @param content
     * @return
     */
    public static String filterEmoji(String content) {

        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotBlank(content)) {
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                //从几何形状到CJK Compatibility,避开中文符号
                Integer cInt = Integer.valueOf(c);
                if (cInt > 9470 && cInt < 12288) {
                    if (Integer.valueOf(content.charAt(i + 1)) == 65039) {
                        i++;
                    }
                    continue;
                }
                if (!isEmojiCharacter(c)) {

                    result.append(c);
                }
            }

        }
        return result.toString();

    }

    /**
     * Emoji转码
     * 转码范围：特殊字符和emoji表情
     *
     * @param content
     * @param placeHolder 不能转码的是否需要占位符
     * @return
     */
    public static String convertEmojiToUnicode(String content, boolean placeHolder) {

        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotBlank(content)) {
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                //从几何形状到CJK Compatibility,避开中文符号
                Integer cInt = Integer.valueOf(c);
                if (cInt > 9470 && cInt < 12288) {
                    result.append("&#").append(Integer.valueOf(c)).append(";");
                    if (Integer.valueOf(content.charAt(i + 1)) == 65039) {
                        i++;
                    }
                    continue;
                }
                if (isEmojiCharacter(c)) {
                    Integer unicodeInt = codePointToExtUnicode(c, content.charAt(i + 1));
                    if (unicodeInt < 1) {
                        //没找到的[int]这种返回源码
                        if (placeHolder) {
                            result.append("[").append(Integer.valueOf(c)).append("]");
                        }
                        continue;
                    }
                    result.append("&#").append(unicodeInt).append(";");
                    i++;
                } else {
                    result.append(c);
                }
            }

        }
        return result.toString();
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || (
                (codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || (
                (codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    /**
     * Unicode扩展码解析
     *
     * @param one
     * @param two
     * @return 十进制codePoint
     */
    private static Integer codePointToExtUnicode(int one, int two) {

        if (one > 0 && two > 0) {
            //U+D800（55296）,U+DC00（56320）
            String binStrHigh = Integer.toBinaryString(one - 55296);
            String binStrLow = Integer.toBinaryString(two - 56320);
            if (StringUtils.isNotBlank(binStrHigh) && StringUtils.isNotBlank(binStrLow)) {
                StringBuilder binStrRes = new StringBuilder();
                binStrRes.append(StringUtils.right(binStrHigh, 10));
                if (StringUtils.length(binStrLow) < 10) {
                    binStrRes.append(StringUtils.leftPad(binStrLow, 10, "0"));
                } else {
                    binStrRes.append(StringUtils.right(binStrLow, 10));
                }
                Integer resInt = Integer.parseInt(binStrRes.toString(), 2);
                //U+10000
                return resInt + 65536;
            }
        }

        return 0;
    }

}
