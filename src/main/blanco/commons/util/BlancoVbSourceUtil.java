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
 * blanco Frameworkにおける VB.NETソースコード出力のためのユーティリティを集めたクラスです。
 * 
 * @author IGA Tosiki
 */
public final class BlancoVbSourceUtil {
    /**
     * 与えられた文字列をVB.NETソースコード文字列として出力するものとしてエスケープ処理します。
     * 
     * ￥/バックスラッシュのエスケープおよび改行コードのエスケープを行います。<br>
     * それ以外の処理は行いません。たとえばインジェクション攻撃などへの耐性は、このメソッドは扱いません。
     * 
     * @param originalString
     *            入力文字列
     * @return エスケープ処理が行われた後の文字列
     */
    public static final String escapeStringAsVbSource(
            final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "BlancoVbSourceUtil.escapeStringAsVbSourceで入力違反が発生。このメソッドにnullがパラメータとして与えられました。null以外の値を入力してください。");
        }

        final StringReader reader = new StringReader(originalString);
        final StringWriter writer = new StringWriter();
        try {
            for (;;) {
                final int iRead = reader.read();
                if (iRead < 0) {
                    break;
                }
                switch (iRead) {
                case '\\':
                    writer.write("\\\\");
                    break;
                case '\n':
                    writer.write("\\n");
                    break;
                case '"':
                    writer.write("\"\"");
                    break;
                case '＂':
                    // 0xfa57 (Windows-31J)
                    // 0xff02 (UTF-16BE)
                    writer.write("\"＂");
                    break;
                default:
                    writer.write((char) iRead);
                    break;
                }
            }
            writer.flush();
        } catch (IOException e) {
            // ここに入ってくることは、ありえません。
            e.printStackTrace();
        }
        return writer.toString();
    }
}
