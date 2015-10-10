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
package blanco.commons.calc.parser.t001;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import blanco.commons.calc.parser.BlancoCalcParser;

/**
 * blancoResourceBundle <br>
 * メタ情報からプロパティファイルやリソースバンドルアクセサJavaソースコードを自動生成 <br>
 * 
 * メタ情報を入力してXMLを出力(XMLへ変換)します。 <br>
 * 
 * @author IGA Tosiki
 */
public class BlancoCalcParserT001 extends TestCase {

    public void testBlancoCalcParser() throws Exception {
        new File("tmp/test/calc/parser").mkdirs();

        InputStream inStream = null;
        OutputStream outStream = null;
        InputStream inStreamDef = null;
        try {
            inStream = new BufferedInputStream(new FileInputStream(
                    "./test/calc/parser/t001/t001.xls"));
            outStream = new BufferedOutputStream(new FileOutputStream(
                    "./tmp/test/calc/parser/t001.out.xml"));
            inStreamDef = new BufferedInputStream(new FileInputStream(
                    "./test/calc/parser/t001/t001.xml"));
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