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
package blanco.commons.calc.parser.transformer;

import blanco.commons.calc.parser.BlancoCalcTransformer;
import junit.framework.TestCase;

import java.io.*;

/**
 * blancoResourceBundle <br>
 * メタ情報からプロパティファイルやリソースバンドルアクセサJavaソースコードを自動生成 <br>
 * 
 * メタ情報を入力してXMLを出力(XMLへ変換)します。 <br>
 *
 * 「テスト電文定義・一覧」に既存値がある場合は、既存値下に追記します。
 * 「テスト電文定義・共通」は上書きされます。
 *
 * @author IGA Tosiki
 */
public class BlancoCalcTransformerT002 extends TestCase {

    public void testBlancoCalcTransformer() throws Exception {
        new File("tmp/test/calc/parser/transformer").mkdirs();

        InputStream inStreamDef = null;
        InputStream inStream = null;
        OutputStream outStream = null;
        OutputStream outStreamExcel = null;
        try {
            inStreamDef = new BufferedInputStream(new FileInputStream(
                    "./test/calc/parser/transformer/BlancoCarecoApiTestMeta2MetaT002.xml"));
            inStream = new BufferedInputStream(new FileInputStream(
                    "./test/calc/parser/transformer/templateT002.xlsx"));
            outStream = new BufferedOutputStream(new FileOutputStream(
                    "./tmp/test/calc/parser/transformer/t002x.out.xml"));
            outStreamExcel = new BufferedOutputStream(new FileOutputStream(
                    "./tmp/test/calc/parser/transformer/t002x.out.xlsx"));

            new BlancoCalcTransformer().process(inStreamDef, inStream, outStream, outStreamExcel);
            outStream.flush();
        } finally {
            if (inStream != null) {
                inStream.close();
            }
            if (outStream != null) {
                outStream.close();
            }
            if (inStreamDef != null) {
                inStreamDef.close();
            }
            if (outStreamExcel != null) {
                outStreamExcel.close();
            }
        }
    }
}