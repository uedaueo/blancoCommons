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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ファイルからストリームへの入出力ラッパー<br>
 * java.io.Fileからjava.io.XXPutStream系の入出力へと変換を行います。
 * 
 * @author IGA Tosiki
 */
public abstract class File2StreamWrapper {
    private InputStream inStream = null;

    private OutputStream outStream = null;

    /**
     * java.io.Fileで与えられた入出力をストリームベースに変換します。<br>
     * もし片方だけストリームがある場合には、別の経路にて実行箇所に与えてください。
     * 
     * @param fileInput
     * @param fileOutput
     * @throws Exception
     */
    public File2StreamWrapper(final File fileInput, final File fileOutput)
            throws Exception {
        if (fileInput != null) {
            inStream = new BufferedInputStream(new FileInputStream(fileInput));
        }
        if (fileOutput != null) {
            outStream = new BufferedOutputStream(new FileOutputStream(
                    fileOutput));
        }
    }

    /**
     * 入出力処理を実際に実行します。
     * 
     * @throws Exception
     *             何か例外が発生した場合。
     */
    public void run() throws Exception {
        try {
            process(inStream, outStream);

            // 処理が正常に終了した場合にはflushを呼び出して保留の書き込みを実行します。
            if (outStream != null) {
                outStream.flush();
            }
        } finally {
            // 常にcloseは通すようにします。
            closeStream();
        }
    }

    /**
     * 実際の入出力処理をここに記載します。
     * 
     * @param inStream
     * @param outStream
     * @throws Exception
     */
    protected abstract void process(final InputStream inStream,
            final OutputStream outStream) throws Exception;

    /**
     * ストリームをクローズします。このメソッドはコンストラクタ内から自動的に呼び出されます。
     * 
     * @throws IOException
     */
    protected void closeStream() throws IOException {
        try {
            if (inStream != null) {
                inStream.close();
                inStream = null;
            }
        } finally {
            if (outStream != null) {
                outStream.close();
                outStream = null;
            }
        }
    }
}
