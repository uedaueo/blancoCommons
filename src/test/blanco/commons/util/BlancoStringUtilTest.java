/*
 * blanco Framework
 * Copyright (C) 2004-2009 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
/*******************************************************************************
 * Copyright (c) 2009 IGA Tosiki, NTT DATA BUSINESS BRAINS Corp.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IGA Tosiki (NTT DATA BUSINESS BRAINS Corp.) - initial API and implementation
 *******************************************************************************/
package blanco.commons.util;

import junit.framework.TestCase;

/**
 * 名前に関するユーティリティのテストケースです
 * 
 * @author iga
 */
public class BlancoStringUtilTest extends TestCase {
    public void testReplaceAllString() throws Exception {
        assertEquals("BBB", BlancoStringUtil.replaceAll("AAA", "A", "B"));
        assertEquals("BBC", BlancoStringUtil.replaceAll("ABC", "A", "B"));
        assertEquals("隣の客は良くりんご食う客だ", BlancoStringUtil.replaceAll(
                "隣の客は良く柿食う客だ", "柿", "りんご"));
        assertEquals("隣の客は良く柿食う客ぞ", BlancoStringUtil.replaceAll("隣の客は良く柿食う客だ",
                "だ", "ぞ"));
        assertEquals("横の客は良く柿食う客だ", BlancoStringUtil.replaceAll("隣の客は良く柿食う客だ",
                "隣", "横"));
        assertEquals("隣の人は良く柿食う人だ", BlancoStringUtil.replaceAll("隣の客は良く柿食う客だ",
                "客", "人"));
        assertEquals("隣のきゃくは良く柿食うきゃくだ", BlancoStringUtil.replaceAll(
                "隣の客は良く柿食う客だ", "客", "きゃく"));
        assertEquals("隣のきゃくは良く柿食うきゃくだ", BlancoStringUtil.replaceAll(
                "隣の客は良く柿食う客だ", "客", "きゃく"));
        assertEquals("隣のは良く柿食うだ", BlancoStringUtil.replaceAll("隣の客は良く柿食う客だ",
                "客", ""));
        assertEquals("DEF", BlancoStringUtil.replaceAll("ABC", "ABC", "DEF"));
        assertEquals("ABC", BlancoStringUtil.replaceAll("ABC", "ABD", "DEF"));
        assertEquals("DEFDEF", BlancoStringUtil.replaceAll("ABCABC", "ABC",
                "DEF"));
        assertEquals("ZDEFDEFZ", BlancoStringUtil.replaceAll("ZABCABCZ", "ABC",
                "DEF"));
        assertEquals("ZDEFZDEFZ", BlancoStringUtil.replaceAll("ZABCZABCZ",
                "ABC", "DEF"));
        // 無限ループチェック用。
        assertEquals("隣のきゃく客は良く柿食うきゃく客だ", BlancoStringUtil.replaceAll(
                "隣の客は良く柿食う客だ", "客", "きゃく客"));
    }

    public void testReplaceString() throws Exception {
        assertEquals("隣のきゃく客は良く柿食うきゃく客だ", BlancoStringUtil.replace(
                "隣の客は良く柿食う客だ", "客", "きゃく客", true));
        assertEquals("隣のきゃく客は良く柿食う客だ", BlancoStringUtil.replace("隣の客は良く柿食う客だ",
                "客", "きゃく客", false));
    }

    public void testTrimLeft() throws Exception {
        assertEquals("ABC", BlancoStringUtil.trimLeft("ABC"));
        assertEquals("ABC", BlancoStringUtil.trimLeft(" ABC"));
        assertEquals("ABC ", BlancoStringUtil.trimLeft("ABC "));
        assertEquals("", BlancoStringUtil.trimLeft(" "));
        assertEquals("", BlancoStringUtil.trimLeft("  "));
        assertEquals("", BlancoStringUtil.trimLeft("   "));
        assertEquals("A", BlancoStringUtil.trimLeft("   A"));
    }

    public void testTrimRight() throws Exception {
        assertEquals("ABC", BlancoStringUtil.trimRight("ABC"));
        assertEquals(" ABC", BlancoStringUtil.trimRight(" ABC"));
        assertEquals("ABC", BlancoStringUtil.trimRight("ABC "));
        assertEquals("", BlancoStringUtil.trimRight(" "));
        assertEquals("", BlancoStringUtil.trimRight("  "));
        assertEquals("", BlancoStringUtil.trimRight("   "));
        assertEquals("A", BlancoStringUtil.trimRight("A   "));
    }

    public void testTrim() throws Exception {
        assertEquals("ABC", BlancoStringUtil.trim("ABC"));
        assertEquals("ABC", BlancoStringUtil.trim(" ABC"));
        assertEquals("ABC", BlancoStringUtil.trim("ABC "));
        assertEquals("", BlancoStringUtil.trim(" "));
        assertEquals("", BlancoStringUtil.trim("  "));
        assertEquals("", BlancoStringUtil.trim("   "));
        assertEquals("A", BlancoStringUtil.trim("A   "));
        assertEquals("A", BlancoStringUtil.trim("   A   "));
    }

    public void testPad() throws Exception {
        assertEquals("ABCZZ", BlancoStringUtil.padRight("ABC", 5, 'Z'));
        assertEquals("ZZZZZ", BlancoStringUtil.padRight("", 5, 'Z'));
        assertEquals("ABCDZ", BlancoStringUtil.padRight("ABCD", 5, 'Z'));
        assertEquals("ABCDE", BlancoStringUtil.padRight("ABCDE", 5, 'Z'));
        assertEquals("ABCDEF", BlancoStringUtil.padRight("ABCDEF", 5, 'Z'));

        assertEquals("ZZABC", BlancoStringUtil.padLeft("ABC", 5, 'Z'));
        assertEquals("ZZZZZ", BlancoStringUtil.padLeft("", 5, 'Z'));
        assertEquals("ZABCD", BlancoStringUtil.padLeft("ABCD", 5, 'Z'));
        assertEquals("ABCDE", BlancoStringUtil.padLeft("ABCDE", 5, 'Z'));
        assertEquals("ABCDEF", BlancoStringUtil.padLeft("ABCDEF", 5, 'Z'));
    }
    public void testPadWindows31J() throws Exception {
        assertEquals("あいうZ", BlancoStringUtil.padRightWindows31J("あいう", 7, 'Z'));
        assertEquals("あ", BlancoStringUtil.padRightWindows31J("あ", 1, 'Z'));
        assertEquals("あ", BlancoStringUtil.padRightWindows31J("あ", 2, 'Z'));
        assertEquals("あZ", BlancoStringUtil.padRightWindows31J("あ", 3, 'Z'));
        assertEquals("あ", BlancoStringUtil.padLeftWindows31J("あ", 1, 'Z'));
        assertEquals("あ", BlancoStringUtil.padLeftWindows31J("あ", 2, 'Z'));
        assertEquals("Zあ", BlancoStringUtil.padLeftWindows31J("あ", 3, 'Z'));
        assertEquals("Zあいう", BlancoStringUtil.padLeftWindows31J("あいう", 7, 'Z'));
    }
}
