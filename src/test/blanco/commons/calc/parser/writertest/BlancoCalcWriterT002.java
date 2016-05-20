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
package blanco.commons.calc.parser.writertest;

import blanco.commons.calc.parser.BlancoCalcWriter;
import junit.framework.TestCase;

import java.io.*;

/**
 * blancoResourceBundle <br>
 * メタ情報からプロパティファイルやリソースバンドルアクセサJavaソースコードを自動生成 <br>
 * 
 * メタ情報を入力してXMLを出力(XMLへ変換)します。 <br>
 * 
 * @author IGA Tosiki
 */
public class BlancoCalcWriterT002 extends TestCase {

    public void testBlancoCalcWriter() throws Exception {
        new File("tmp/test/calc/parser/writertest").mkdirs();

        InputStream inStreamDef = null;
        InputStream inStream = null;
        OutputStream outStream = null;
        OutputStream outStreamExcel = null;
        try {
            inStreamDef = new BufferedInputStream(new FileInputStream(
                    "./test/calc/parser/writertest/BlancoCarecoApiTestMeta2MetaT002.xml"));
            inStream = new BufferedInputStream(new FileInputStream(
                    "./test/calc/parser/writertest/templateT002.xlsx"));
            outStream = new BufferedOutputStream(new FileOutputStream(
                    "./tmp/test/calc/parser/writertest/t002x.out.xml"));
            outStreamExcel = new BufferedOutputStream(new FileOutputStream(
                    "./tmp/test/calc/parser/writertest/t002x.out.xlsx"));

            new BlancoCalcWriter().process(inStreamDef, inStream, outStream, outStreamExcel);
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