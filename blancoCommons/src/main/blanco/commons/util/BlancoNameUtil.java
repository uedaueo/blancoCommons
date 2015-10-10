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
import java.util.ArrayList;
import java.util.List;

/**
 * blanco Frameworkの名前に関するユーティリティを集めたクラスです。
 * 
 * @author IGA Tosiki
 */
public class BlancoNameUtil {
    /**
     * 与えられた名前がファイル名として利用が妥当かどうかを判断します。
     * 
     * ／￥：；＊？”＞＜｜が与えられたらfalseを戻します。
     * 
     * @param checkString
     *            ファイル名として判定する文字列。
     * @return ファイル名として妥当であればtrueを、妥当でなければ falseを戻します。
     */
    public static final boolean isValidFileName(final String checkString) {
        if (checkString == null) {
            throw new IllegalArgumentException(
                    "ファイル名として妥当かどうか判定するメソッドにnullが与えられました。null以外の値を与えてください。");
        }

        if (checkString.indexOf('/') >= 0 || checkString.indexOf('\\') >= 0
                || checkString.indexOf(':') >= 0
                || checkString.indexOf(';') >= 0
                || checkString.indexOf('*') >= 0
                || checkString.indexOf('?') >= 0
                || checkString.indexOf('\"') >= 0
                || checkString.indexOf('>') >= 0
                || checkString.indexOf('<') >= 0
                || checkString.indexOf('|') >= 0) {
            // これらの文字が含まれていたらファイル名としては不適切です。
            return false;
        }
        return true;
    }

    /**
     * ファイル名として利用すべきでない文字の一覧を表示形式で取得します。
     * 
     * @return 利用すべきでない文字列の一覧を表示形式であらわしたもの。
     */
    public static final String invalidFileNameChar() {
        return "/ \\ : ; * ? \" > < |";
    }

    /**
     * ファイル名(ディレクトリ名は含まない状態で与えます)から拡張子を除去します。
     * 
     * ドットは拡張子と同じく除去されます。<br>
     * もし .cvsignoreのようなファイル名が与えられた場合には、そのまま戻ります。
     * 
     * @param checkString
     *            ファイル名(ディレクトリを含まないもの)
     * @return 拡張子を含まないファイル名
     */
    public static final String trimFileExtension(final String checkString) {
        if (checkString == null) {
            throw new IllegalArgumentException(
                    "ファイル名から拡張子を取り除くメソッドにnullが与えられました。null以外の値を与えてください。");
        }

        // 最後の拡張子を探し出します。
        final int posDot = checkString.lastIndexOf(".");
        if (posDot == 0) {
            // このファイル名はドットから開始されるファイル名です。
            // 拡張子の切り取りは行わずに、そのまま返します。
            return checkString;
        }
        if (posDot > 0) {
            // 拡張子を除去します。
            return checkString.substring(0, posDot);
        }

        return checkString;
    }

    /**
     * 与えられた文字列を指定された区切り文字で分割します。
     * 
     * 区切り文字が '/'である場合の挙動は下記の通り。<br>
     * 通常ケース1：[aaa/bbb/ccc]→{ "aaa", "bbb", "ccc" }<br>
     * 通常ケース2：[aaa//ccc]→{ "aaa", "", "ccc" }<br>
     * 通常ケース3: [/aaa/bbb]→{ "", "aaa", "bbb" }<br>
     * ※最初のスラッシュは一つとしてカウントします。<br>
     * 通常ケース4：[/aaa/bbb/]→{ "", "aaa", "bbb" }<br>
     * ※最後のスラッシュは無視します。<br>
     * <br>
     * 境界ケース1：[/]→{ "" }<br>
     * 境界ケース2：[///]→{ "", "", "" }<br>
     * 境界ケース3：[]→長さ0の配列<br>
     * 境界ケース4：null→長さ0の配列<br>
     * 
     * @param originalString
     *            区切り文字で区切られた文字列。nullを与えた場合には 長さ0のString配列が戻ります。
     * @return 分割された文字列。区切り文字そのものは含まれません。
     */
    public static final String[] splitString(final String originalString,
            final char delimiter) {
        if (originalString == null) {
            // nullに関しては、一つも項目がないものとして扱います。
            return new String[0];
        }

        final StringReader reader = new StringReader(originalString);
        StringWriter writer = null;
        final List<java.lang.String> result = new ArrayList<java.lang.String>();
        for (;;) {
            final int read;
            try {
                read = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(
                        "ワークのStringReaderからの読み込みで例外が発生しました。全く想定しないケースです。:"
                                + e.toString());
            }
            if (read < 0) {
                break;
            }
            if (writer == null) {
                writer = new StringWriter();
            }
            if ((char) read == delimiter) {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new IllegalArgumentException(
                                "ワークのStringWriterのクローズで例外が発生しました。全く想定しないケースです。:"
                                        + e.toString());
                    }
                    result.add(writer.toString());
                    // ワーク変数の中身をリセットします。
                    writer = null;
                }
            } else {
                writer.write((char) read);
            }
        }
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(
                        "全く想定しない、ありえないケースでのエラーが発生しました。:" + e.toString());
            }
            result.add(writer.toString());
            // ワーク変数の中身をリセットします。
            writer = null;
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    /**
     * 与えられた文字列をパスであると仮定し、/(スラッシュ)記号で区切って文字列を分割します。
     * 
     * 通常ケース1：[aaa/bbb/ccc]→{ "aaa", "bbb", "ccc" }<br>
     * 通常ケース2：[aaa//ccc]→{ "aaa", "", "ccc" }<br>
     * 通常ケース3: [/aaa/bbb]→{ "", "aaa", "bbb" }<br>
     * ※最初のスラッシュは一つとしてカウントします。<br>
     * 通常ケース4：[/aaa/bbb/]→{ "", "aaa", "bbb" }<br>
     * ※最後のスラッシュは無視します。<br>
     * <br>
     * 境界ケース1：[/]→{ "" }<br>
     * 境界ケース2：[///]→{ "", "", "" }<br>
     * 境界ケース3：[]→長さ0の配列<br>
     * 境界ケース4：null→長さ0の配列<br>
     * 
     * @param originalString
     *            /(スラッシュ)記号で区切られた文字列。nullを与えた場合には 長さ0のString配列が戻ります。
     * @return 分割された文字列。/(スラッシュ)記号は含まれません。
     */
    public static final String[] splitPath(final String originalString) {
        return splitString(originalString, '/');
    }

    /**
     * 与えられたクラス名からパッケージ名を除去します。
     * 
     * @param argClassName
     *            フルパッケージ名付きのクラス名。
     * @return パッケージ名無しのクラス名。
     */
    public static final String trimJavaPackage(final String argClassName) {
        if (argClassName == null) {
            throw new IllegalArgumentException(
                    "クラス名からパッケージ名を取り除くメソッドにnullが与えられました。null以外の値を与えてください。");
        }

        // ドットでフルクラス名を分割します。
        final String[] work = splitString(argClassName, '.');
        for (int index = work.length - 1; index >= 0; index--) {
            if (work[index].length() > 0) {
                // 分割後の文字列を見ていき、長さをもった最後の文字列をもってクラス名とします。
                return work[index];
            }
        }

        // ひとつも実体をもった名前が見つかりませんでした。
        throw new IllegalArgumentException("文字列[" + argClassName
                + "]がパッケージ名付きJavaクラス名として処理できません。");
    }

    /**
     * 与えられたURIから、ある命名規則に従って Javaパッケージ名を取得します。
     * 
     * @param uri
     *            入力となるURI。
     * @return Javaパッケージ名。
     */
    public static final String uri2JavaPackage(final String uri) {
        if (uri == null) {
            throw new IllegalArgumentException(
                    "URIからパッケージ名を取得するメソッドにnullが与えられました。null以外の値を与えてください。");
        }

        final StringWriter writer = new StringWriter();
        final String[] splitedUri = splitString(uri, ':');
        boolean isFirst = true;
        if (splitedUri.length < 2) {
            throw new IllegalArgumentException(
                    "与えられたURI["
                            + uri
                            + "]は形式が不正です。[http://www.w3.org/XML/Schema]のような形式で指定してください。");
        }
        final String[] splitDir = BlancoNameUtil
                .splitString(splitedUri[1], '/');
        for (int index = 0; index < splitDir.length; index++) {
            final String[] splitDomain = BlancoNameUtil.splitString(
                    splitDir[index], '.');
            for (int indexDomain = splitDomain.length - 1; indexDomain >= 0; indexDomain--) {
                // ドメインを表すと思われる箇所であるので、逆順で処理を行います。
                if (splitDomain[indexDomain].length() > 0) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        writer.write(".");
                    }
                    writer.write(splitDomain[indexDomain]);
                }
            }
        }
        for (int indexUri = 2; indexUri < splitedUri.length; indexUri++) {
            final String[] splitDirAfter = BlancoNameUtil.splitString(
                    splitedUri[indexUri], '/');
            // ポイント：最初のノードは ポート番号であろうから これを無視をします。
            for (int index = 1; index < splitDirAfter.length; index++) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    writer.write(".");
                }
                writer.write(splitDirAfter[index]);
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "ありえない例外: StringWriterのclose()の際に例外が発生しました。:"
                            + e.toString());
        }
        return writer.toString();
    }
}
