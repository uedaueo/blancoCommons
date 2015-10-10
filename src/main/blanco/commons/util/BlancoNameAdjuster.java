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

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * blanco Frameworkの名前変形に関するユーティリティを集めたクラスです。
 * 
 * @author IGA Tosiki
 */
public class BlancoNameAdjuster {
    /**
     * blanco Frameworkの名前変形として、調整すべき文字であるかどうかをチェックします。
     * 
     * Javaのクラス名やメソッド名として利用が不適切と考えられる文字であるかどうかを判断しています。<br>
     * 言語仕様上利用可能な文字であっても、このblanco Frameworkとしては利用をすすめない文字は調整文字として判断します。
     * 
     * @param checkChar
     *            チェックを行いたい入力文字。
     * @return 調整すべき文字と判断した場合にはtrueを戻します。
     */
    public static final boolean checkAdjustChar(final char checkChar) {
        switch (checkChar) {
        case '!':
        case '"':
        case '#':
        case '%':
        case '&':
        case '\'':
        case '(':
        case ')':
        case '-':
        case '=':
        case '^':
        case '~':
        case '\\':
        case '|':
        case '@':
        case '[':
        case ']':
        case '{':
        case '}':
        case ';':
        case '+':
        case ':':
        case '*':
        case '<':
        case '>':
        case ',':
        case '.':
        case '/':
        case '?':
        case '_':
        case ' ':
            // 調整すべき文字であると判断しました。
            return true;
        default:
            return false;
        }
    }

    /**
     * blanco Frameworkの名前変形として、調整すべき文字であるかどうかをチェックします。
     * 
     * Javaのクラス名やメソッド名として利用が不適切と考えられる文字が含まれているかどうかを判断しています。<br>
     * 言語仕様上利用可能な文字であっても、このblanco Frameworkとしては利用をすすめない文字は調整文字として判断します。
     * 
     * @param checkChar
     *            チェックを行いたい入力文字列。
     * @return 調整すべき文字と判断した場合にはtrueを戻します。
     */
    public static final boolean checkAdjustCharExist(final String checkChar) {
        if (checkChar == null) {
            throw new IllegalArgumentException(
                    "調整文字列が含まれるかどうかをチェックするメソッド (BlancoNameAdjuster.checkAdjustCharExist)に nullが与えられましたが、このメソッドにnullを与えることはできません。");
        }

        for (int index = 0; index < checkChar.length(); index++) {
            if (checkAdjustChar(checkChar.charAt(index))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 与えられた文字列の先頭の文字が大文字になるように名前変形します。
     * 
     * @param originalString
     *            入力文字列。null以外の文字列を与えます。長さ0の文字列を与えることは許可されています。
     * @return 変形後の文字列
     */
    public static final String toUpperCaseTitle(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "文字列の先頭文字を大文字に変換するルーチンにnullが与えられました。");
        }
        if (originalString.length() == 0) {
            // 長さ0の文字列については、これを許可しています。そのまま戻します。
            return originalString;
        }

        final char[] buf = originalString.toCharArray();
        if (buf[0] != Character.toUpperCase(buf[0])) {
            // 先頭文字が大文字では無いので、先頭文字を大文字に変換します。
            buf[0] = Character.toUpperCase(buf[0]);
            // 変形後の文字列を戻します。
            return new String(buf);
        } else {
            // 変形の必要が無いので、入力文字列をそのまま戻します。
            return originalString;
        }
    }

    /**
     * 与えられた文字列の先頭の文字が小文字になるように名前変形します。
     * 
     * 
     * @param originalString
     *            入力文字列。null以外の文字列を与えます。長さ0の文字列を与えることは許可されています。
     * @return 変形後の文字列
     */
    public static final String toLowerCaseTitle(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "文字列の先頭文字を小文字に変換するルーチンにnullが与えられました。");
        }
        if (originalString.length() == 0) {
            // 長さ0の文字列については、これを許可しています。そのまま戻します。
            return originalString;
        }

        final char[] buf = originalString.toCharArray();
        if (buf[0] != Character.toLowerCase(buf[0])) {
            // 先頭文字が小文字では無いので、先頭文字を小文字に変換します。
            buf[0] = Character.toLowerCase(buf[0]);
            // 変形後の文字列を戻します。
            return new String(buf);
        } else {
            // 変形の必要が無いので、入力文字列をそのまま戻します。
            return originalString;
        }
    }

    /**
     * 与えられた文字が全て大文字である場合には、先頭文字以外の文字を全て小文字に変換します。
     * 
     * 全て大文字か、そうではなくて大文字小文字混じりかによって動作を変えています。<br>
     * このメソッドは blanco Frameworkの内部から呼び出されることを想定しています。
     * 
     * @param originalString
     *            入力文字列。
     * @return 変形後の文字列
     */
    public static final String weakenUpperCase(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "文字列が全て大文字であった場合に、先頭文字以外を小文字に変換するルーチンにnullが与えられました。");
        }

        if (originalString.equals(originalString.toUpperCase())) {
            // 与えられた文字列の全てが大文字です。
            // 先頭文字のみを大文字で、それ以外は小文字になるように変換します。
            return toUpperCaseTitle(originalString.toLowerCase());
        } else {
            // 変形の必要が無いので入力文字列をそのまま戻します。
            return originalString;
        }
    }

    /**
     * 与えられた文字列を、blanco Frameworkにおける調整すべき文字に関して着眼して複数の文字列へと分割します。
     * 
     * 全て大文字という文字があった場合には、先頭文字以外は小文字に変換するという処理が内部的に含まれています。<br>
     * 文字列分割の結果、配列の中には長さ0の文字列が含まれる可能性がある点に注意して利用してください。
     * 
     * @param originalString
     *            入力文字列。
     * @return 調整文字で分割された文字列の配列。
     */
    public static final String[] splitByAdjustChar(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "調整文字列をもとに文字列を分割するメソッド (BlancoNameAdjuster.splitByAdjustChar)に nullが与えられました。このメソッドにnullを与えることはできません。");
        }

        final List<java.lang.String> result = new ArrayList<java.lang.String>();
        final char[] bufRead = originalString.toCharArray();
        final CharArrayWriter writerWork = new CharArrayWriter();
        for (int index = 0; index < bufRead.length; index++) {
            if (checkAdjustChar(bufRead[index])) {
                // 調整すべき文字があらわれました。分割処理を行います。
                writerWork.flush();
                result.add(weakenUpperCase(writerWork.toString()));
                writerWork.reset();
            } else {
                // 調整文字以外の文字です。溜め込みを行います。
                writerWork.write(bufRead[index]);
            }
        }
        writerWork.flush();
        result.add(weakenUpperCase(writerWork.toString()));
        final String[] ret = new String[result.size()];
        return (String[]) result.toArray(ret);
    }

    /**
     * 与えられた文字列を blanco Frameworkとしての名前変形ルールに従って妥当なクラス名へと名前変形します。
     * 
     * このメソッドは blanco Frameworkのなかからよく利用されるメソッドです。クラス名へと名前変形が必要な局面でよく利用されます。
     * 
     * @param originalString
     *            入力文字列。
     * @return クラス名として変形された後の文字列。
     */
    public static final String toClassName(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "クラス名へと名前変形するメソッド (BlancoNameAdjuster.toClassName)に nullが与えられました。このメソッドにnullを与えることはできません。");
        }

        final StringWriter result = new StringWriter();
        // 文字列を調整文字で区切ります。
        // 調整文字で区切った後の文字が大文字だけの場合には、先頭以外は小文字の文字へと変換します。
        final String[] work = splitByAdjustChar(originalString);
        for (int index = 0; index < work.length; index++) {
            if (work[index].length() == 0) {
                // 長さが0の場合は処理をスキップします。
                continue;
            }
            // 調整文字で区切られたあとの文字列について、先頭文字を大文字へと変形します。
            result.write(toUpperCaseTitle(work[index]));
        }
        try {
            result.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "文字列をクラス名に調整するルーチンにおいて、発生するはずのない例外が発生しました。:" + e.toString());
        }
        return result.toString();
    }

    /**
     * 与えられた文字列を blanco Frameworkとしての名前変形ルールに従って妥当なパラメータ名へと名前変形します。
     * 
     * 内部的には、toClassNameを呼び出した上で 先頭文字を小文字へと変形させています。
     * 
     * @param originalString
     *            入力文字列。
     * @return パラメータ名として変形された後の文字列。
     */
    public static final String toParameterName(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "パラメータ名へと名前変形するメソッド (BlancoNameAdjuster.toParameterName)に nullが与えられました。このメソッドにnullを与えることはできません。");
        }

        // クラス名へと名前変形させた後に先頭文字列を小文字へと変形させます。
        return toLowerCaseTitle(toClassName(originalString));
    }
}
