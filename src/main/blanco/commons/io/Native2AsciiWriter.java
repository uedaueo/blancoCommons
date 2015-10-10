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
package blanco.commons.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import blanco.commons.util.BlancoStringUtil;

/**
 * Native2Asciiライター
 * 
 * java.util.Propertiesの力を使って Native2Asciiを行います。
 * 
 * @author IGA Tosiki
 * @see java.util.Properties#load(java.io.InputStream)
 */
public class Native2AsciiWriter {
    private BufferedWriter fWriter;

    /**
     * 基本的には、8859_1エンコードのライターを渡してください。
     * 
     * @param writer
     *            出力先ライター。
     */
    public Native2AsciiWriter(final BufferedWriter writer) {
        this.fWriter = writer;
    }

    /**
     * ライターをフラッシュします。
     * 
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public void flush() throws IOException {
        fWriter.flush();
    }

    /**
     * ライターをクローズします。
     * 
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public void close() throws IOException {
        fWriter.close();
    }

    /**
     * ライターにコメントを出力します。<br>
     * スペースが欲しい場合には、与える文字列の先頭にスペースを加えてください。
     * 
     * @param comment
     * @throws IOException
     */
    public void writeComment(final String comment) throws IOException {
        fWriter.write("#" + encodeNative2AsciiComment(comment));
        fWriter.newLine();
    }

    /**
     * ライターにプロパティを出力します。
     * 
     * @param key
     *            キー。
     * @param value
     *            値。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public void writeProperty(final String key, final String value)
            throws IOException {
        fWriter.write(encodeNative2AsciiKey(key));
        fWriter.write("=");
        fWriter.write(encodeNative2AsciiValue(value));
        fWriter.newLine();
    }

    /**
     * 与えられたネイティブ文字列を仮想プロパティファイルのbyte配列に変換します。
     * 
     * @param nativeString
     *            ネイティブ文字列。
     * @return バイト配列。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    private static final byte[] encodeNative2AsciiKeyByteArray(
            final String nativeString) throws IOException {
        final Properties prop = new Properties();
        prop.setProperty(nativeString, "value");
        final ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        try {
            prop.store(byteOutStream, "dummy");
            byteOutStream.flush();
        } finally {
            byteOutStream.close();
        }
        return byteOutStream.toByteArray();
    }

    /**
     * 与えられたネイティブ文字列を仮想プロパティファイルのbyte配列に変換します。
     * 
     * @param nativeString
     *            変換もとの文字列。
     * @return 変換後のバイト配列。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    private static final byte[] encodeNative2AsciiValueByteArray(
            final String nativeString) throws IOException {
        final Properties prop = new Properties();
        prop.setProperty("key", nativeString);
        final ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        try {
            prop.store(byteOutStream, "dummy");
            byteOutStream.flush();
        } finally {
            byteOutStream.close();
        }
        return byteOutStream.toByteArray();
    }

    /**
     * native2asciiを実行します。
     * 
     * 詳しくは
     * http://java.sun.com/j2se/1.4/ja/docs/ja/api/java/util/Properties.html
     * #load(java.io.InputStream) を参照ください。
     * 
     * @param nativeString
     *            変換もとの文字列。
     * @return 変換後の文字列。
     * @see java.util.Properties#load(java.io.InputStream)
     */
    public static final String encodeNative2AsciiKey(final String nativeString) {
        try {
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(
                            encodeNative2AsciiKeyByteArray(nativeString)),
                            "8859_1"));
            for (;;) {
                final String look = reader.readLine();
                if (look == null) {
                    throw new IllegalArgumentException(
                            "encodeNative2Ascii("
                                    + nativeString
                                    + ")の処理の過程において、中間メモリファイルの予期せぬ終端が発生しました。このケースはありえません。");
                }
                if (look.startsWith("#")) {
                    // コメント行は読み飛ばします。
                    continue;
                }
                reader.close();
                if (look.endsWith("=value")) {
                    return look.substring(0, look.lastIndexOf("=value"));
                }
                throw new IllegalArgumentException("encodeNative2AsciiKey("
                        + nativeString + ")の処理の過程において、中間メモリファイルの読み込みの結果、予期せぬ行["
                        + look + "]が戻りました。このケースはありえません。");
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("encodeNative2Ascii("
                    + nativeString + ")の処理の過程において、予期せぬ例外が発生しました。このケースはありえません。:"
                    + ex.toString());
        }
    }

    /**
     * native2asciiを実行します。
     * 
     * 詳しくは
     * http://java.sun.com/j2se/1.4/ja/docs/ja/api/java/util/Properties.html
     * #load(java.io.InputStream) を参照してください。
     * 
     * @param nativeString
     *            変換もとの文字列。
     * @return 変換後の文字列。
     * @see java.util.Properties#load(java.io.InputStream)
     */
    public static final String encodeNative2AsciiValue(final String nativeString) {
        try {
            final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new ByteArrayInputStream(
                            encodeNative2AsciiValueByteArray(nativeString)),
                            "8859_1"));
            for (;;) {
                final String look = reader.readLine();
                if (look == null) {
                    throw new IllegalArgumentException(
                            "encodeNative2Ascii("
                                    + nativeString
                                    + ")の処理の過程において、中間メモリファイルの予期せぬ終端が発生しました。このケースはありえません。");
                }
                if (look.startsWith("#")) {
                    // コメント行は読み飛ばします。
                    continue;
                }
                reader.close();
                if (look.startsWith("key=")) {
                    return look.substring("key=".length());
                }
                throw new IllegalArgumentException("encodeNative2Ascii("
                        + nativeString + ")の処理の過程において、中間メモリファイルの読み込みの結果、予期せぬ行["
                        + look + "]が戻りました。このケースはありえません。");
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("encodeNative2Ascii("
                    + nativeString + ")の処理の過程において、予期せぬ例外が発生しました。このケースはありえません。:"
                    + ex.toString());
        }
    }

    /**
     * 与えられた文字列がコメントであるものとしてNative2Asciiを実行します。
     * 
     * @param nativeString
     *            変換を行いたい対象の文字列。
     * @return asciiコメントとして変換後の文字列。
     */
    public static final String encodeNative2AsciiComment(
            final String nativeString) {
        final StringReader reader = new StringReader(nativeString);
        final StringWriter writer = new StringWriter();
        try {
            for (;;) {
                final int iRead = reader.read();
                if (iRead < 0) {
                    break;
                }
                if ((iRead < 0x0020) || (iRead > 0x007E)) {
                    // よりも小さいものと \u007Eよりも大きいものは
                    // \\uxxxxのように16進表示を行います。
                    writer.write(toHexString((char) iRead));
                } else {
                    // 何もせず、そのまま書き出します。
                    writer.write((char) iRead);
                }
            }
            writer.flush();
            return writer.toString();
        } catch (IOException e) {
            // まずこれはありえません。
            e.printStackTrace();
            return null;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // まずこれはありえません。
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * 与えられたchar文字をUnicode表記に変換します。<br>
     * native2ascii.exeの仕様とは異なりますが、java.util.Propertiesの仕様に従い、大文字に変換します。<br>
     * このルーチンは、Native2AsciiWriterのコメントエンコードに利用されます。
     * 
     * @param arg
     *            HEX表記をおこないたい文字列。
     * @return HEX表記に変換後の文字列。
     */
    private static final String toHexString(final char arg) {
        return "\\u" + BlancoStringUtil.toHexString(arg).toUpperCase();
    }
}
