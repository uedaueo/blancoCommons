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

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * blanco Frameworkにおける、文字列に関するユーティリティの内部処理が含まれます。
 * 
 * このクラスはパッケージ外非公開と設定しています。
 * 
 * @author IGA Tosiki
 */
class BlancoStringUtilReplace {
    /**
     * 正規表現とは関係なく文字列の置換を行います。
     * 
     * このメソッドでは java.lang.Stringとは異なり正規表現は関与しません。<br>
     * 正規表現が機能してほしくない文字列置換の際に利用します。
     * 
     * @param source
     *            変換前の文字列。
     * @param replaceFrom
     *            置換元文字。
     * @param replaceTo
     *            置換先文字。
     * @return 変換後の文字列。
     */
    public static final String replaceAll(final String source,
            final char replaceFrom, final char replaceTo) {
        if (source == null) {
            throw new IllegalArgumentException(
                    "文字列置換のメソッドに、変換元文字列としてnullが与えられました。null以外の値を与えてください。");
        }

        try {
            final StringReader reader = new StringReader(source);
            final StringWriter writer = new StringWriter();
            try {
                for (;;) {
                    int iRead = reader.read();
                    if (iRead < 0) {
                        break;
                    }
                    if (iRead == replaceFrom) {
                        iRead = replaceTo;
                    }
                    writer.write(iRead);
                }
                return writer.toString();
            } finally {
                reader.close();
                writer.close();
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(
                    "文字列置換のメソッドの処理中に、想定されない例外が発生しました。処理中断します。" + ex.toString());
        }
    }

    /**
     * 正規表現とは関係なく文字列の置換を行います。
     * 
     * このメソッドでは java.lang.Stringとは異なり正規表現は関与しません。<br>
     * 正規表現が機能してほしくない文字列置換の際に利用します。
     * 
     * @param source
     *            変換前の文字列。
     * @param replaceFrom
     *            置換元文字列。
     * @param replaceTo
     *            置換先文字列。
     * @param isReplaceAll
     *            全て置換するかどうか。
     * @return 変換後の文字列。
     */
    public static final String replace(final String source,
            final String replaceFrom, final String replaceTo,
            final boolean isReplaceAll) {
        if (source == null) {
            throw new IllegalArgumentException(
                    "文字列置換のメソッドに、変換元文字列としてnullが与えられました。null以外の値を与えてください。");
        }
        if (replaceFrom == null) {
            throw new IllegalArgumentException(
                    "文字列置換のメソッドに、置換元文字としてnullが与えられました。");
        }
        if (replaceFrom.length() == 0) {
            throw new IllegalArgumentException(
                    "文字列置換のメソッドに、置換元文字として長さ0の文字列が与えられました。");
        }
        if (replaceTo == null) {
            throw new IllegalArgumentException(
                    "文字列置換のメソッドに、置換先文字としてnullが与えられました。");
        }

        final int indexFound = source.indexOf(replaceFrom);
        if (indexFound < 0) {
            return source;
        }
        final String pre = source.substring(0, indexFound);

        String suffix = source.substring(indexFound + replaceFrom.length());
        if (isReplaceAll) {
            final String suffixCheck = replace(suffix, replaceFrom, replaceTo,
                    false);
            if (suffix.equals(suffixCheck) == false) {
                suffix = replace(suffix, replaceFrom, replaceTo, true);
            }
        }

        return pre + replaceTo + suffix;
    }
}
