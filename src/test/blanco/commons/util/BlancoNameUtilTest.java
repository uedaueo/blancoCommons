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
public class BlancoNameUtilTest extends TestCase {
    public void testTrimFileExtention() throws Exception {
        assertEquals("abc", BlancoNameUtil.trimFileExtension("abc.txt"));
        assertEquals("abc.def", BlancoNameUtil.trimFileExtension("abc.def.txt"));
        assertEquals(".cvsignore", BlancoNameUtil
                .trimFileExtension(".cvsignore"));
        assertEquals(".cvsignore", BlancoNameUtil
                .trimFileExtension(".cvsignore.txt"));
        assertEquals(".", BlancoNameUtil.trimFileExtension(".."));
        assertEquals("aaa", BlancoNameUtil.trimFileExtension("aaa."));
        assertEquals("aaa.", BlancoNameUtil.trimFileExtension("aaa.."));
    }

    public void testSplitPath() throws Exception {
        // 一般的な値の試験
        assertEqualsStringArray(new String[] { "aaa" }, BlancoNameUtil
                .splitPath("aaa"));
        assertEqualsStringArray(new String[] { "aaa", "bbb", "ccc" },
                BlancoNameUtil.splitPath("aaa/bbb/ccc"));
        assertEqualsStringArray(new String[] { "aaa" }, BlancoNameUtil
                .splitPath("aaa"));

        // やや境界値的な試験
        assertEqualsStringArray(new String[] { "aaa", "", "ccc" },
                BlancoNameUtil.splitPath("aaa//ccc"));
        // ポイント：ルートはルートとしてカウントアップされます。
        assertEqualsStringArray(new String[] { "", "aaa", "bbb" },
                BlancoNameUtil.splitPath("/aaa/bbb"));
        // ポイント：最後のバックスラッシュは無視されます。
        assertEqualsStringArray(new String[] { "", "aaa", "bbb" },
                BlancoNameUtil.splitPath("/aaa/bbb/"));

        // ここからは特殊なケース
        assertEqualsStringArray(new String[] { "", "", "" }, BlancoNameUtil
                .splitPath("///"));
        assertEqualsStringArray(new String[] { "", "" }, BlancoNameUtil
                .splitPath("//"));
        assertEqualsStringArray(new String[] { "" }, BlancoNameUtil
                .splitPath("/"));

        // ここからは境界の試験
        // ポイント："" は無いものとして処理されます。
        assertEqualsStringArray(new String[0], BlancoNameUtil.splitPath(""));
        // ポイント：nullは 無いものとして処理されます。
        assertEqualsStringArray(new String[0], BlancoNameUtil.splitPath(null));
    }

    /**
     * URIがパッケージ名に変形するのを試験します。
     * 
     * @throws Exception
     */
    public void testUri2JavaPackage() throws Exception {
        assertEquals("ccc.bbb.aaa.ddd.eee", BlancoNameUtil
                .uri2JavaPackage("http://aaa.bbb.ccc/ddd/eee"));
        assertEquals("ccc.bbb.aaa.ddd.eee", BlancoNameUtil
                .uri2JavaPackage("https://aaa.bbb.ccc/ddd/eee"));
        assertEquals("ccc.bbb.aaa.ddd.eee", BlancoNameUtil
                .uri2JavaPackage("https://aaa.bbb.ccc:8080/ddd/eee"));
        assertEquals("ccc.bbb.aaa.ddd.eee", BlancoNameUtil
                .uri2JavaPackage("ftp://aaa.bbb.ccc:43/ddd/eee"));
        assertEquals("aaa", BlancoNameUtil.uri2JavaPackage("http://aaa"));
        assertEquals("aaa.bbb", BlancoNameUtil
                .uri2JavaPackage("http://aaa/bbb"));
        assertEquals("aaa.bbb.ccc", BlancoNameUtil
                .uri2JavaPackage("http://aaa/bbb/ccc"));
        assertEquals("org.aaa", BlancoNameUtil
                .uri2JavaPackage("http://aaa.org"));
        assertEquals("org.aaa", BlancoNameUtil
                .uri2JavaPackage("http://aaa.org/"));
        try {
            BlancoNameUtil.uri2JavaPackage("abc.def");
            fail("例外が発生すべきところで、例外が発生しませんでした。");
        } catch (IllegalArgumentException e) {
        }
    }

    private void assertEqualsStringArray(String[] wish, String[] target)
            throws Exception {
        assertEquals(wish.length, target.length);
        for (int index = 0; index < wish.length; index++) {
            assertEquals(wish[index], target[index]);
        }
    }
}
