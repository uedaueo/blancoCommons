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

import java.io.UnsupportedEncodingException;

/**
 * blanco Frameworkにおける、文字列に関するユーティリティの内部処理が含まれます。
 * 
 * このクラスはパッケージ外非公開と設定しています。
 * 
 * @author IGA Tosiki
 */
class BlancoStringUtilPad {
    /**
     * 指定の長さになるまで、文字列の右側に文字を追加します。
     * 
     * @param argSource
     *            オリジナル文字列。
     * @param argLength
     *            希望する長さ。
     * @param argPadChar
     *            詰め込みに利用する文字。
     * @return 指定の長さに加工後の文字列。指定の長さよりもオリジナル文字列が長い場合には、オリジナル文字列がそのまま戻ります。
     */
    public static final String padRight(final String argSource,
            final int argLength, final char argPadChar) {
        if (argSource == null) {
            throw new IllegalArgumentException(
                    "BlancoStringUtil.padRightの入力文字列にnullが与えられました。");
        }

        final StringBuffer buf = new StringBuffer();
        buf.append(argSource);

        for (; buf.length() < argLength;) {
            // 一文字ずつ増やしていきます。
            buf.append(argPadChar);
        }

        return buf.toString();
    }

    /**
     * 指定の長さになるまで、文字列の左側に文字を追加します。
     * 
     * @param argSource
     *            オリジナル文字列。
     * @param argLength
     *            希望する長さ。
     * @param argPadChar
     *            詰め込みに利用する文字。
     * @return 指定の長さに加工後の文字列。指定の長さよりもオリジナル文字列が長い場合には、オリジナル文字列がそのまま戻ります。
     */
    public static final String padLeft(final String argSource,
            final int argLength, final char argPadChar) {
        if (argSource == null) {
            throw new IllegalArgumentException(
                    "BlancoStringUtil.padLefoの入力文字列にnullが与えられました。");
        }

        final int originalLength = argSource.length();
        final StringBuffer buf = new StringBuffer();

        for (; buf.length() + originalLength < argLength;) {
            // 一文字ずつ増やしていきます。
            buf.append(argPadChar);
        }

        return buf.toString() + argSource;
    }

    /**
     * Windows-31J 換算で指定の長さになるまで、文字列の右側に文字を追加します。
     * 
     * @param argSource
     *            オリジナル文字列。
     * @param argLength
     *            希望する長さ。
     * @param argPadChar
     *            詰め込みに利用する文字。
     * @return 指定の長さに加工後の文字列。指定の長さよりもオリジナル文字列が長い場合には、オリジナル文字列がそのまま戻ります。
     */
    public static final String padRightWindows31J(final String argSource,
            final int argLength, final char argPadChar) {
        if (argSource == null) {
            throw new IllegalArgumentException(
                    "BlancoStringUtil.padRightの入力文字列にnullが与えられました。");
        }

        final StringBuffer buf = new StringBuffer();
        buf.append(argSource);

        try {
            for (; buf.toString().getBytes("Windows-31J").length < argLength;) {
                // 一文字ずつ増やしていきます。
                buf.append(argPadChar);
            }
            return buf.toString();
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(
                    "想定しない例外が発生しました。Windows-31J エンコーディングが取得できませんでした。", ex);
        }
    }

    /**
     * Windows-31J 換算で指定の長さになるまで、文字列の左側に文字を追加します。
     * 
     * @param argSource
     *            オリジナル文字列。
     * @param argLength
     *            希望する長さ。
     * @param argPadChar
     *            詰め込みに利用する文字。
     * @return 指定の長さに加工後の文字列。指定の長さよりもオリジナル文字列が長い場合には、オリジナル文字列がそのまま戻ります。
     */
    public static final String padLeftWindows31J(final String argSource,
            final int argLength, final char argPadChar) {
        if (argSource == null) {
            throw new IllegalArgumentException(
                    "BlancoStringUtil.padLefoの入力文字列にnullが与えられました。");
        }

        try {
            final int originalLength = argSource.getBytes("Windows-31J").length;
            final StringBuffer buf = new StringBuffer();

            for (; buf.toString().getBytes("Windows-31J").length
                    + originalLength < argLength;) {
                // 一文字ずつ増やしていきます。
                buf.append(argPadChar);
            }
            return buf.toString() + argSource;
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(
                    "想定しない例外が発生しました。Windows-31J エンコーディングが取得できませんでした。", ex);
        }
    }
}
