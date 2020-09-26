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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * blanco Frameworkにおける、ストリームに関するユーティリティが含まれます。
 * 
 * 原則としてほとんどのメソッドはstaticメソッドとして提供されます。
 * 
 * @author IGA Tosiki
 */
public class BlancoStreamUtil {
    /**
     * ストリームコピーなどの際のバッファサイズ。
     */
    public static final int BUF_SIZE = 8192;

    /**
     * 与えられたストリームをバイト配列に変換します。
     * 
     * ByteArrayInputStreamの逆の効果を得るためのメソッドです。
     * 
     * @param inStream
     *            入力ストリーム。
     * @return 変換後のバイト配列。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public static byte[] stream2Bytes(final InputStream inStream)
            throws IOException {
        if (inStream == null) {
            throw new IllegalArgumentException(
                    "入力ストリームからバイト配列に変換するメソッドに入力ストリームとしてnullが与えられました。null以外の値を与えてください。");
        }

        final ByteArrayOutputStream result = new ByteArrayOutputStream();

        // ストリームのコピーを行います。
        copy(inStream, result);

        result.flush();
        return result.toByteArray();
    }

    /**
     * 与えられた入力ストリームを出力ストリームへとコピーします。
     * 
     * バッファサイズ8192バイトでストリームをブロックコピーします。<br>
     * このメソッドの内部ではフラッシュ処理は行いません。必要に応じて呼び出し元メソッドにおいて flush()してください。
     * 
     * @param inStream
     *            入力ストリーム。
     * @param outStream
     *            出力ストリーム。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public static final void copy(final InputStream inStream,
            final OutputStream outStream) throws IOException {
        if (inStream == null) {
            throw new IllegalArgumentException(
                    "BlancoStreamUtil.copyメソッドの入力ストリームパラメータにnullが与えられました。null以外の値を指定してください。");
        }
        if (outStream == null) {
            throw new IllegalArgumentException(
                    "BlancoStreamUtil.copyメソッドの出力ストリームパラメータにnullが与えられました。null以外の値を指定してください。");
        }

        final byte[] buf = new byte[BUF_SIZE];
        for (;;) {
            final int length = inStream.read(buf, 0, buf.length);
            if (length < 0) {
                break;
            }
            outStream.write(buf, 0, length);
        }
    }
}
