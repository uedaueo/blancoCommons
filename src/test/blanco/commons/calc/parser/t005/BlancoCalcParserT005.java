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
package blanco.commons.calc.parser.t005;

import blanco.commons.calc.parser.BlancoCalcParser;
import junit.framework.TestCase;

import java.io.*;

/**
 * blancoResourceBundle <br>
 * メタ情報からプロパティファイルやリソースバンドルアクセサJavaソースコードを自動生成 <br>
 *
 * メタ情報を入力してXMLを出力(XMLへ変換)します。 <br>
 *     ※ これは異常系のテストです！(by tueda)
 *     ※ poi 4.1.2, xmlbeans 3.1.0 で、式の評価に失敗するサンプルをおいています。
 *
 * @author IGA Tosiki
 */
public class BlancoCalcParserT005 extends TestCase {

    public void testBlancoCalcParser() throws Exception {
        new File("tmp/test/calc/parser").mkdirs();

        InputStream inStream = null;
        OutputStream outStream = null;
        InputStream inStreamDef = null;
        try {
            inStream = new BufferedInputStream(new FileInputStream(
                    "./test/calc/parser/t005/t005.xlsx"));
            outStream = new BufferedOutputStream(new FileOutputStream(
                    "./tmp/test/calc/parser/t005.out.xml"));
            inStreamDef = new BufferedInputStream(new FileInputStream(
                    "./test/calc/parser/t005/t005.xml"));
            new BlancoCalcParser().process(inStreamDef, inStream, outStream);
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
        }
    }
}
