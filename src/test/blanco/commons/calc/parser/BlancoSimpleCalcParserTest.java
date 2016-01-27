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
package blanco.commons.calc.parser;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.TestCase;

import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * @author iga
 */
public class BlancoSimpleCalcParserTest extends TestCase {

    /*
     * void parse のテスト中のクラス(String)
     */
    public void testParseString() {
        OutputStream outStream = null;
        try {
            BlancoSimpleCalcParser parser = new BlancoSimpleCalcParser();
            // ルートノード名を指定。
            parser.setProperty(
                    BlancoSimpleCalcParser.URI_PROPERTY_NAME_WORKBOOK,
                    "rootNode");
            outStream = new BufferedOutputStream(new FileOutputStream(
                    "./meta/blancoCalcParserTestData.xml"));
            BlancoCalcParser.getTransformer().transform(
                    new SAXSource(parser, new InputSource(
                            "./meta/blancoCalcParserTestData.xlsx")),
                    new StreamResult(outStream));
            outStream.flush();
            outStream.close();
            outStream = null;
        } catch (TransformerException ex) {
            System.out.println("XMLドキュメント保存時に変換例外が発生しました.:" + ex.toString());
            ex.printStackTrace();
            return;
        } catch (IOException ex3) {
            System.out.println("XMLドキュメント保存時に入出力例外が発生しました.:" + ex3.toString());
            ex3.printStackTrace();
            return;
        } catch (SAXNotRecognizedException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (SAXNotSupportedException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
