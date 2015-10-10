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

/**
 * blanco Frameworkにおける、文字列に関するユーティリティが含まれます。
 * 
 * 原則としてほとんどのメソッドはstaticメソッドとして提供されます。
 * 
 * @author IGA Tosiki
 */
public class BlancoStringUtil {
    /**
     * 与えられたbyte配列の16進表記を取得します。小文字で戻ります。
     * 
     * @param arg
     *            入力となるバイト配列。
     * @return 16進表記の文字列。
     */
    public static final String toHexString(final byte[] arg) {
        final StringBuffer buf = new StringBuffer();
        for (int index = 0; index < arg.length; index++) {
            buf.append(toHexString(arg[index]));
        }
        return buf.toString();
    }

    /**
     * 与えられたbyteの16進表記を取得します。小文字で戻ります。
     * 
     * @param arg
     *            バイト。
     * @return 16進表記の文字列。
     */
    public static final String toHexString(final byte arg) {
        // 正の値にしたうえで16進表記を取得します。
        String strResult = Integer.toHexString(arg & 0xff);
        for (; strResult.length() < 2;) {
            strResult = "0" + strResult;
        }
        return strResult;
    }

    /**
     * 与えられたchar文字を16進表記に変換します。小文字で戻ります。
     * 
     * @param arg
     *            文字。
     * @return 16進表記の文字列。
     */
    public static final String toHexString(final char arg) {
        String strResult = Integer.toHexString(arg);
        for (; strResult.length() < 4;) {
            strResult = "0" + strResult;
        }
        return strResult;
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
     *            置換元文字。
     * @param replaceTo
     *            置換先文字。
     * @return 変換後の文字列。
     */
    public static final String replaceAll(final String source,
            final char replaceFrom, final char replaceTo) {
        return BlancoStringUtilReplace.replaceAll(source, replaceFrom,
                replaceTo);
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
     * @return 変換後の文字列。
     */
    public static final String replaceAll(final String source,
            final String replaceFrom, final String replaceTo) {
        return BlancoStringUtilReplace.replace(source, replaceFrom, replaceTo,
                true);
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
        return BlancoStringUtilReplace.replace(source, replaceFrom, replaceTo,
                isReplaceAll);
    }

    /**
     * 与えられた文字列がnullの場合に、長さ0の文字列へと変換します。そうで無い場合には、そのままの文字列が戻ります。
     * 
     * nullを長さ0の文字列に置き換えたい場合に、このメソッドを利用します。<br>
     * 文字列処理の過程で nullが入りうるのだが それは長さ0の文字列とみなしたい場合にこのメソッドを利用することを想定します。
     * 
     * @param originalString
     *            入力文字列。ここにnullを与えると 長さ0の文字列に変換されます。
     * @return 変換後の文字列。必ず null以外が戻ります。
     */
    public static final String null2Blank(final String originalString) {
        if (originalString == null) {
            // nullの場合には、長さ0の文字列に置き換えられます。
            return "";
        }
        // そうでない場合には、もとの文字列がそのまま戻ります。
        return originalString;
    }

    /**
     * 文字列の長さを取得します。nullの場合には 長さ0とみなします。
     * 
     * nullが入りうる文字列の長さを取得したい場合に利用されることを想定しています。
     * 
     * @deprecated 自明さの確保のために、null2Blankとlength()を直接呼び出すことをお勧めします。
     * @param sourceString
     *            長さを取得したい文字列。
     * @return 文字列の長さ。nullが与えられた場合には0が戻ります。
     */
    public static final int getLengthNullable(final String sourceString) {
        // nullが与えられても長さ0であると判断します。
        return null2Blank(sourceString).length();
    }

    /**
     * 与えられた文字列について、左側に半角空白があれば これを除去します。
     * 
     * 半角空白のみ除去します。全角空白は処理しません。
     * 
     * @param originalString
     *            処理を行いたい文字列。
     * @return 半角空白が切り詰められた後の文字列。
     */
    public static final String trimLeft(final String originalString) {
        return BlancoStringUtilTrim.trimLeft(originalString);
    }

    /**
     * 与えられた文字列について、右側に半角空白があれば これを除去します。
     * 
     * 半角空白のみ除去します。全角空白は処理しません。
     * 
     * @param originalString
     *            処理を行いたい文字列。
     * @return 半角空白が切り詰められた後の文字列。
     */
    public static final String trimRight(final String originalString) {
        return BlancoStringUtilTrim.trimRight(originalString);
    }

    /**
     * 与えられた文字列について、右側および左側に半角空白があれば これを除去します。
     * 
     * 半角空白のみ除去します。全角空白は処理しません。
     * 
     * @param originalString
     *            処理を行いたい文字列。
     * @return 半角空白が切り詰められた後の文字列。
     */
    public static final String trim(final String originalString) {
        return BlancoStringUtilTrim.trim(originalString);
    }

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
        return BlancoStringUtilPad.padRight(argSource, argLength, argPadChar);
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
        return BlancoStringUtilPad.padLeft(argSource, argLength, argPadChar);
    }

    /**
     * Windows 31J換算で指定の長さになるまで、文字列の右側に文字を追加します。
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
        return BlancoStringUtilPad.padRightWindows31J(argSource, argLength,
                argPadChar);
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
        return BlancoStringUtilPad.padLeftWindows31J(argSource, argLength,
                argPadChar);
    }
}
