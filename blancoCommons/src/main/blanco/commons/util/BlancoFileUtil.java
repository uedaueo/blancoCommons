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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * blanco Frameworkにおけるファイル関連のユーティリティを集めたクラスです。
 * 
 * @author IGA Tosiki
 */
public class BlancoFileUtil {
    /**
     * デバッグモードで動作するかどうかのフラグ。
     */
    private static final boolean IS_DEUBG = false;

    /**
     * 与えられた入力ファイルを出力ファイルへとコピーします。
     * 
     * 入力ファイルが存在しない場合などには例外が発生します。
     * 
     * @param fileInput
     *            入力ファイル。nullは与えないでください。
     * @param fileOutput
     *            出力ファイル。nullは与えないでください。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public static final void copy(final File fileInput, final File fileOutput)
            throws IOException {
        if (IS_DEUBG) {
            System.out.println("BlancoFileUtil.copy("
                    + fileInput.getAbsolutePath() + ", "
                    + fileOutput.getAbsolutePath() + ")");
        }
        if (fileInput == null) {
            throw new IllegalArgumentException("入力ファイルにnullが与えられました。");
        }
        if (fileOutput == null) {
            throw new IllegalArgumentException("出力ファイルにnullが与えられました。");
        }
        if (fileInput.exists() == false) {
            throw new IllegalArgumentException("入力ファイル["
                    + fileInput.getAbsolutePath() + "]が見つかりません。");
        }
        if (fileInput.isFile() == false) {
            throw new IllegalArgumentException("入力ファイル["
                    + fileInput.getAbsolutePath() + "]にディレクトリを与えることはできません。");
        }
        if (fileOutput.exists()) {
            if (fileOutput.isFile() == false) {
                throw new IllegalArgumentException("出力ファイル["
                        + fileInput.getAbsolutePath() + "]にディレクトリを与えることはできません。");
            }
            if (fileOutput.canWrite() == false) {
                throw new IllegalArgumentException("出力ファイル["
                        + fileInput.getAbsolutePath()
                        + "]が存在したうえに、書き込みを行うことができません。");
            }
        }

        final File fileTargetParent = fileOutput.getParentFile();
        if (fileTargetParent.exists() == false) {
            // 存在しない場合には作成します。
            if (fileTargetParent.mkdirs() == false) {
                throw new IllegalArgumentException("出力ファイル["
                        + fileInput.getAbsolutePath() + "]の親フォルダ["
                        + fileTargetParent.getAbsolutePath()
                        + "]が存在しなかったので作成しようとしましたが作成に失敗しました。");
            }
        }

        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            inStream = new BufferedInputStream(new FileInputStream(fileInput));
            outStream = new BufferedOutputStream(new FileOutputStream(
                    fileOutput));

            // ストリームのコピーを行います。
            BlancoStreamUtil.copy(inStream, outStream);

            outStream.flush();
        } finally {
            try {
                // ストリームが開いている場合には確実にクローズします。
                if (inStream != null) {
                    inStream.close();
                }
            } finally {
                // ストリームが開いている場合には確実にクローズします。
                if (outStream != null) {
                    outStream.close();
                }
            }
        }
    }

    /**
     * 与えられた入力ストリームを出力ストリームへとコピーします。
     * 
     * このメソッドの内部ではフラッシュ処理は行いません。必要に応じて呼び出し元メソッドにおいて flush()してください。<br>
     * 内部的には 別クラスのストリームコピーを呼び出します。
     * 
     * @deprecated このメソッドの代わりに BlancoStreamUtil.copyメソッドを呼び出すことをお勧めします。
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
                    "BlancoFileUtil.copyメソッドの入力ストリームパラメータにnullが与えられました。null以外の値を指定してください。");
        }
        if (outStream == null) {
            throw new IllegalArgumentException(
                    "BlancoFileUtil.copyメソッドの出力ストリームパラメータにnullが与えられました。null以外の値を指定してください。");
        }

        BlancoStreamUtil.copy(inStream, outStream);
    }

    /**
     * ファイルを読み込み、byte配列へと展開します。
     * 
     * @param inputFile
     *            入力ファイル。
     * @return byte配列化されたファイルの内容。
     * @throws IOException
     *             入出力例外が発生した場合。
     * @throws IllegalArgumentException
     *             ファイルが存在しないなどの場合。
     */
    public static final byte[] file2Bytes(final File inputFile)
            throws IOException, IllegalArgumentException {
        if (inputFile == null) {
            throw new IllegalArgumentException(
                    "file2Bytes()メソッドに入力パラメータとしてnullが与えれられました。");
        }
        if (inputFile.exists() == false) {
            throw new IllegalArgumentException(
                    "file2Bytes()メソッドに入力パラメータとして与えられたファイルは存在しません。");
        }
        if (inputFile.canRead() == false) {
            throw new IllegalArgumentException(
                    "file2Bytes()メソッドに入力パラメータとして与えられたファイルは読み込むことができません。");
        }
        if (inputFile.isDirectory()) {
            throw new IllegalArgumentException(
                    "file2Bytes()メソッドに入力パラメータとしてディレクトリが与えられました。ディレクトリは処理できません");
        }

        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        final InputStream inStream = new BufferedInputStream(
                new FileInputStream(inputFile), BlancoStreamUtil.BUF_SIZE);
        try {
            BlancoStreamUtil.copy(inStream, outStream);
            outStream.flush();
            return outStream.toByteArray();
        } finally {
            inStream.close();
            outStream.close();
        }
    }

    /**
     * 与えられたバイト配列を、与えられたファイルに出力します。
     * 
     * @param inputBytes
     *            入力となるバイト配列。
     * @param outputFile
     *            出力となるファイル。
     * @throws IOException
     *             入出力例外が発生した場合。
     * @throws IllegalArgumentException
     *             ファイルにnullが与えられたなどの場合。
     */
    public static final void bytes2File(final byte[] inputBytes,
            final File outputFile) throws IOException, IllegalArgumentException {
        if (inputBytes == null) {
            throw new IllegalArgumentException(
                    "bytes2File()メソッドに入力パラメータの入力バイト配列としてnullが与えれられました。");
        }
        if (outputFile == null) {
            throw new IllegalArgumentException(
                    "bytes2File()メソッドに入力パラメータの出力ファイルとしてnullが与えれられました。");
        }
        if (outputFile.exists()) {
            if (outputFile.isDirectory()) {
                throw new IllegalArgumentException(
                        "bytes2File()メソッドに入力パラメータとして出力先ファイルにディレクトリが与えられました。ディレクトリは処理できません");
            }
            if (outputFile.canWrite() == false) {
                throw new IllegalArgumentException(
                        "bytes2File()メソッドに入力パラメータとして与えられたファイルは存在する上に書き込みできません。");
            }
        } else {
            if (outputFile.createNewFile() == false) {
                throw new IllegalArgumentException(
                        "bytes2File()メソッドに入力パラメータとして出力先ファイルは生成することができません。");
            }
        }

        final ByteArrayInputStream inStream = new ByteArrayInputStream(
                inputBytes);
        final OutputStream outStream = new BufferedOutputStream(
                new FileOutputStream(outputFile), BlancoStreamUtil.BUF_SIZE);
        try {
            BlancoStreamUtil.copy(inStream, outStream);
            outStream.flush();
        } finally {
            outStream.close();
            inStream.close();
        }
    }

    /**
     * 必要に応じて、バイト配列をファイルに出力します。
     * 
     * @param inputBytes
     *            入力バイト配列。
     * @param outputFile
     *            出力先ファイル。
     * @return 0:作成も更新もおこなわなかった。1:ファイルを新規に作成した。2:ファイルを更新した。
     * @throws IOException
     *             入出力例外が発生した場合。
     */
    public static final int bytes2FileIfNecessary(final byte[] inputBytes,
            final File outputFile) throws IOException {

        byte[] originalFileImage = null;
        if (outputFile.exists()) {
            // オリジナルのファイルイメージを取得しておきます。
            originalFileImage = file2Bytes(outputFile);
        }

        if (originalFileImage == null) {
            // 有無を言わさず新規書き込み。
            bytes2File(inputBytes, outputFile);
            return 1;
        } else {
            if (BlancoByteUtil.compare(originalFileImage, inputBytes) == 0) {
                // 書き込む必要なし。
                return 0;
            } else {
                // 更新書き込み。
                bytes2File(inputBytes, outputFile);
                return 2;
            }
        }
    }

    /**
     * 改行を取得します。
     * 
     * @return 改行。Windows なら \n
     */
    public static final String getNewLine() {
        return System.getProperty("line.separator");
    }
}
