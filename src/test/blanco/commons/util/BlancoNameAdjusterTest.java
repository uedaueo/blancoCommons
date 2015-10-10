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
 * 名前調整に関するユーティリティのテストケースです
 * 
 * @author iga
 */
public class BlancoNameAdjusterTest extends TestCase {
    /**
     * クラス名変形の試験<br>
     * 
     * 小文字：toUpperして変化する文字 大文字：小文字以外の文字
     * 
     * 大文字のみ COLUMNNAME → Columnname (Oracleの列名が全て大文字)<br>
     * 小文字大文字入り乱れ ColumnName → ColumnName (先頭を大文字、変化なし)<br>
     * columnName → ColumnName (先頭を大文字)<br>
     * 小文字のみ columnname → Columnname<br>
     * 
     * アンダーバーなどはトークン扱いとなる<br>
     * abc_def → AbcDef<br>
     * 
     * 
     * @throws Exception
     */
    public void testToClassName() throws Exception {
        // 1文字
        assertEquals("A", BlancoNameAdjuster.toClassName("a"));
        assertEquals("A", BlancoNameAdjuster.toClassName("A"));
        assertEquals("1", BlancoNameAdjuster.toClassName("1"));
        assertEquals("亜", BlancoNameAdjuster.toClassName("亜"));

        // 1文字、アンダーバー先頭
        assertEquals("A", BlancoNameAdjuster.toClassName("_a"));
        assertEquals("A", BlancoNameAdjuster.toClassName("_A"));
        assertEquals("1", BlancoNameAdjuster.toClassName("_1"));
        assertEquals("亜", BlancoNameAdjuster.toClassName("_亜"));

        // 1文字、アンダーバー末尾
        assertEquals("A", BlancoNameAdjuster.toClassName("a_"));
        assertEquals("A", BlancoNameAdjuster.toClassName("A_"));
        assertEquals("1", BlancoNameAdjuster.toClassName("1_"));
        assertEquals("亜", BlancoNameAdjuster.toClassName("亜_"));

        // 1文字、アンダーバー中間
        assertEquals("AA", BlancoNameAdjuster.toClassName("a_a"));
        assertEquals("AA", BlancoNameAdjuster.toClassName("a_A"));
        assertEquals("A1", BlancoNameAdjuster.toClassName("a_1"));
        assertEquals("A亜", BlancoNameAdjuster.toClassName("a_亜"));

        assertEquals("AA", BlancoNameAdjuster.toClassName("A_a"));
        assertEquals("AA", BlancoNameAdjuster.toClassName("A_A"));
        assertEquals("A1", BlancoNameAdjuster.toClassName("A_1"));
        assertEquals("A亜", BlancoNameAdjuster.toClassName("A_亜"));

        assertEquals("1A", BlancoNameAdjuster.toClassName("1_a"));
        assertEquals("1A", BlancoNameAdjuster.toClassName("1_A"));
        assertEquals("11", BlancoNameAdjuster.toClassName("1_1"));
        assertEquals("1亜", BlancoNameAdjuster.toClassName("1_亜"));

        assertEquals("亜A", BlancoNameAdjuster.toClassName("亜_a"));
        assertEquals("亜A", BlancoNameAdjuster.toClassName("亜_A"));
        assertEquals("亜1", BlancoNameAdjuster.toClassName("亜_1"));
        assertEquals("亜亜", BlancoNameAdjuster.toClassName("亜_亜"));

        // ２文字
        assertEquals("Aa", BlancoNameAdjuster.toClassName("aa"));
        assertEquals("AA", BlancoNameAdjuster.toClassName("aA"));
        assertEquals("A1", BlancoNameAdjuster.toClassName("a1"));
        assertEquals("A亜", BlancoNameAdjuster.toClassName("a亜"));

        assertEquals("Aa", BlancoNameAdjuster.toClassName("Aa"));
        assertEquals("Aa", BlancoNameAdjuster.toClassName("AA"));
        assertEquals("A1", BlancoNameAdjuster.toClassName("A1"));
        assertEquals("A亜", BlancoNameAdjuster.toClassName("A亜"));

        assertEquals("1a", BlancoNameAdjuster.toClassName("1a"));
        assertEquals("1a", BlancoNameAdjuster.toClassName("1A"));
        assertEquals("11", BlancoNameAdjuster.toClassName("11"));
        assertEquals("1亜", BlancoNameAdjuster.toClassName("1亜"));

        assertEquals("亜a", BlancoNameAdjuster.toClassName("亜a"));
        assertEquals("亜a", BlancoNameAdjuster.toClassName("亜A"));
        assertEquals("亜1", BlancoNameAdjuster.toClassName("亜1"));
        assertEquals("亜亜", BlancoNameAdjuster.toClassName("亜亜"));

        // ３文字
        assertEquals("Aaa", BlancoNameAdjuster.toClassName("aaa"));
        assertEquals("AaA", BlancoNameAdjuster.toClassName("aaA"));
        assertEquals("Aa1", BlancoNameAdjuster.toClassName("aa1"));
        assertEquals("Aa亜", BlancoNameAdjuster.toClassName("aa亜"));
        assertEquals("AAa", BlancoNameAdjuster.toClassName("aAa"));
        assertEquals("AAA", BlancoNameAdjuster.toClassName("aAA"));
        assertEquals("AA1", BlancoNameAdjuster.toClassName("aA1"));
        assertEquals("AA亜", BlancoNameAdjuster.toClassName("aA亜"));
        assertEquals("A1a", BlancoNameAdjuster.toClassName("a1a"));
        assertEquals("A1A", BlancoNameAdjuster.toClassName("a1A"));
        assertEquals("A11", BlancoNameAdjuster.toClassName("a11"));
        assertEquals("A1亜", BlancoNameAdjuster.toClassName("a1亜"));
        assertEquals("A亜a", BlancoNameAdjuster.toClassName("a亜a"));
        assertEquals("A亜A", BlancoNameAdjuster.toClassName("a亜A"));
        assertEquals("A亜1", BlancoNameAdjuster.toClassName("a亜1"));
        assertEquals("A亜亜", BlancoNameAdjuster.toClassName("a亜亜"));

        assertEquals("Aaa", BlancoNameAdjuster.toClassName("Aaa"));
        assertEquals("AaA", BlancoNameAdjuster.toClassName("AaA"));
        assertEquals("Aa1", BlancoNameAdjuster.toClassName("Aa1"));
        assertEquals("Aa亜", BlancoNameAdjuster.toClassName("Aa亜"));
        assertEquals("AAa", BlancoNameAdjuster.toClassName("AAa"));
        assertEquals("Aaa", BlancoNameAdjuster.toClassName("AAA"));
        assertEquals("Aa1", BlancoNameAdjuster.toClassName("AA1"));
        assertEquals("Aa亜", BlancoNameAdjuster.toClassName("AA亜"));
        assertEquals("A1a", BlancoNameAdjuster.toClassName("A1a"));
        assertEquals("A1a", BlancoNameAdjuster.toClassName("A1A"));
        assertEquals("A11", BlancoNameAdjuster.toClassName("A11"));
        assertEquals("A1亜", BlancoNameAdjuster.toClassName("A1亜"));
        assertEquals("A亜a", BlancoNameAdjuster.toClassName("A亜a"));
        assertEquals("A亜a", BlancoNameAdjuster.toClassName("A亜A"));
        assertEquals("A亜1", BlancoNameAdjuster.toClassName("A亜1"));
        assertEquals("A亜亜", BlancoNameAdjuster.toClassName("A亜亜"));

        assertEquals("1aa", BlancoNameAdjuster.toClassName("1aa"));
        assertEquals("1aA", BlancoNameAdjuster.toClassName("1aA"));
        assertEquals("1a1", BlancoNameAdjuster.toClassName("1a1"));
        assertEquals("1a亜", BlancoNameAdjuster.toClassName("1a亜"));
        assertEquals("1Aa", BlancoNameAdjuster.toClassName("1Aa"));
        assertEquals("1aa", BlancoNameAdjuster.toClassName("1AA"));
        assertEquals("1a1", BlancoNameAdjuster.toClassName("1A1"));
        assertEquals("1a亜", BlancoNameAdjuster.toClassName("1A亜"));
        assertEquals("11a", BlancoNameAdjuster.toClassName("11a"));
        assertEquals("11a", BlancoNameAdjuster.toClassName("11A"));
        assertEquals("111", BlancoNameAdjuster.toClassName("111"));
        assertEquals("11亜", BlancoNameAdjuster.toClassName("11亜"));
        assertEquals("1亜a", BlancoNameAdjuster.toClassName("1亜a"));
        assertEquals("1亜a", BlancoNameAdjuster.toClassName("1亜A"));
        assertEquals("1亜1", BlancoNameAdjuster.toClassName("1亜1"));
        assertEquals("1亜亜", BlancoNameAdjuster.toClassName("1亜亜"));

        assertEquals("亜aa", BlancoNameAdjuster.toClassName("亜aa"));
        assertEquals("亜aA", BlancoNameAdjuster.toClassName("亜aA"));
        assertEquals("亜a1", BlancoNameAdjuster.toClassName("亜a1"));
        assertEquals("亜a亜", BlancoNameAdjuster.toClassName("亜a亜"));
        assertEquals("亜Aa", BlancoNameAdjuster.toClassName("亜Aa"));
        assertEquals("亜aa", BlancoNameAdjuster.toClassName("亜AA"));
        assertEquals("亜a1", BlancoNameAdjuster.toClassName("亜A1"));
        assertEquals("亜a亜", BlancoNameAdjuster.toClassName("亜A亜"));
        assertEquals("亜1a", BlancoNameAdjuster.toClassName("亜1a"));
        assertEquals("亜1a", BlancoNameAdjuster.toClassName("亜1A"));
        assertEquals("亜11", BlancoNameAdjuster.toClassName("亜11"));
        assertEquals("亜1亜", BlancoNameAdjuster.toClassName("亜1亜"));
        assertEquals("亜亜a", BlancoNameAdjuster.toClassName("亜亜a"));
        assertEquals("亜亜a", BlancoNameAdjuster.toClassName("亜亜A"));
        assertEquals("亜亜1", BlancoNameAdjuster.toClassName("亜亜1"));
        assertEquals("亜亜亜", BlancoNameAdjuster.toClassName("亜亜亜"));

        // やばそうなのは、一文字系
        assertEquals("ABC", BlancoNameAdjuster.toClassName("A_b_c"));
        assertEquals("ABC", BlancoNameAdjuster.toClassName("a_B_c"));
        assertEquals("ABC", BlancoNameAdjuster.toClassName("a_b_C"));
        assertEquals("ABC", BlancoNameAdjuster.toClassName("A_B_c"));
        assertEquals("ABC", BlancoNameAdjuster.toClassName("a_B_C"));
        assertEquals("ABC", BlancoNameAdjuster.toClassName("A_b_C"));
        assertEquals("ABC", BlancoNameAdjuster.toClassName("A_B_C"));

        // トークン
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("aaa_bbb"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("_aaa_bbb"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("aaa_bbb_"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("_aaa_bbb_"));

        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("aaa__bbb"));

        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("__aaa_bbb"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("_aaa__bbb"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("__aaa__bbb"));

        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("aaa__bbb_"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("aaa_bbb__"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("aaa__bbb__"));

        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("__aaa_bbb_"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("_aaa__bbb_"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("_aaa_bbb__"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("__aaa__bbb_"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("__aaa_bbb__"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("_aaa__bbb__"));
        assertEquals("AaaBbb", BlancoNameAdjuster.toClassName("__aaa__bbb__"));

        // まんがいちコロンなどが含まれた場合、アンダーバーと同じ扱いとする
        assertEquals("AbCDef", BlancoNameAdjuster.toClassName("abC:DEF"));
        assertEquals("AbCDef", BlancoNameAdjuster.toClassName("abC DEF"));

        // ランダム試験
        assertEquals("Abc", BlancoNameAdjuster.toClassName("abc"));
        assertEquals("AbcD", BlancoNameAdjuster.toClassName("abcD"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("abcDef"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("abc_def"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("abc_def "));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("_abc_def "));
        assertEquals("Abcdef", BlancoNameAdjuster.toClassName("ABCDEF"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("ABC_DEF"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("_ABC_DEF"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("_ABC DEF"));
        assertEquals("SetName", BlancoNameAdjuster.toClassName("set_NAME"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("abc_DEF"));
        assertEquals("AbCDef", BlancoNameAdjuster.toClassName("abC_DEF"));
        assertEquals("ABcDEF", BlancoNameAdjuster.toClassName("ABcDEF"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("AbcDef"));
        assertEquals("AbcDef", BlancoNameAdjuster.toClassName("Abc_def"));
        assertEquals("AbCDEF", BlancoNameAdjuster.toClassName("abCDEF"));

    }

    public void testSplitByAdjustChar() throws Exception {
        assertEquals(2, BlancoNameAdjuster.splitByAdjustChar("aa bb").length);
    }

    /**
     * パラメータ変形の試験
     * 
     * @throws Exception
     */
    public void testParameterName() throws Exception {
        assertEquals("name", BlancoNameAdjuster.toParameterName("name"));
        assertEquals("name", BlancoNameAdjuster.toParameterName("NAME"));
        assertEquals("name", BlancoNameAdjuster.toParameterName("Name"));
        assertEquals("fileName", BlancoNameAdjuster.toParameterName("FileName"));
        assertEquals("fileName", BlancoNameAdjuster
                .toParameterName("FILE_NAME"));
        assertEquals("fileName", BlancoNameAdjuster
                .toParameterName("FILE__NAME"));
        assertEquals("fileName", BlancoNameAdjuster
                .toParameterName("__FILE__NAME"));
        assertEquals("fileName", BlancoNameAdjuster
                .toParameterName("__FILE__NAME"));
        assertEquals("setName", BlancoNameAdjuster.toParameterName("set_NAME"));
    }
}
