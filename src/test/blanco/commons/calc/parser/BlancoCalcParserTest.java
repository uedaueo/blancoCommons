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

import blanco.commons.calc.parser.block.BlancoCalcParserPropertyBlock;
import blanco.commons.calc.parser.block.BlancoCalcParserPropertyKey;
import blanco.commons.calc.parser.block.BlancoCalcParserTableBlock;
import blanco.commons.calc.parser.block.BlancoCalcParserTableColumn;
import blanco.commons.calc.parser.block.BlancoCalcParserValueMapping;
import blanco.commons.parser.SystemOutContentHandler;

/**
 * @author iga
 */
public class BlancoCalcParserTest extends TestCase {

    /*
     * void parse のテスト中のクラス(String)
     */
    @SuppressWarnings("deprecation")
    public void testParseString() {
        OutputStream outStream = null;

        BlancoCalcParserPropertyBlock propertyBlock = new BlancoCalcParserPropertyBlock(
                "attribute");
        propertyBlock.setStartString(new String[] { "業務" });
        propertyBlock
                .setValueMapping(new BlancoCalcParserValueMapping[] {
                        new BlancoCalcParserValueMapping(new String[] { "○",
                                "あり" }, "true"),
                        new BlancoCalcParserValueMapping(new String[] { "なし" },
                                "false"),
                        new BlancoCalcParserValueMapping(
                                new String[] { "検索型" }, "iterator"),
                        new BlancoCalcParserValueMapping(
                                new String[] { "実行型" }, "invoker"),
                        new BlancoCalcParserValueMapping(
                                new String[] { "必ず１件処理" }, "true"),
                        new BlancoCalcParserValueMapping(
                                new String[] { "複数件(0を含む)" }, "false") });

        BlancoCalcParserPropertyKey propertyTitle = new BlancoCalcParserPropertyKey(
                "gamen-id", new String[] { "画面ID" });
        propertyTitle.setSearchRangeX(4);
        propertyBlock.add(propertyTitle);

        propertyTitle = new BlancoCalcParserPropertyKey("name",
                new String[] { "クエリ名" });
        propertyTitle.setSearchRangeX(4);
        propertyBlock.add(propertyTitle);

        propertyTitle = new BlancoCalcParserPropertyKey("query-type",
                new String[] { "SQLタイプ" });
        propertyTitle.setSearchRangeX(8);
        propertyBlock.add(propertyTitle);

        propertyTitle = new BlancoCalcParserPropertyKey("single",
                new String[] { "期待する処理件数" });
        propertyTitle.setSearchRangeX(8);
        propertyBlock.add(propertyTitle);

        propertyTitle = new BlancoCalcParserPropertyKey("scroll",
                new String[] { "スクロール属性" });
        propertyTitle.setSearchRangeX(8);
        propertyBlock.add(propertyTitle);

        propertyTitle = new BlancoCalcParserPropertyKey("updatable",
                new String[] { "更新可能属性" });
        propertyTitle.setSearchRangeX(8);
        propertyBlock.add(propertyTitle);

        BlancoCalcParser parser = new BlancoCalcParser();
        parser.add(propertyBlock);

        // 別のブロック
        BlancoCalcParserTableBlock tableBlock = new BlancoCalcParserTableBlock(
                "parameters");
        tableBlock.setStartString(new String[] { "SQL入力パラメータ" });
        tableBlock.setRowName("parameter");
        tableBlock.setSearchRangeForTitleY(2);
        BlancoCalcParserTableColumn tableTitle = new BlancoCalcParserTableColumn(
                "name", new String[] { "パラメータID" });
        tableBlock.add(tableTitle);

        tableTitle = new BlancoCalcParserTableColumn("type",
                new String[] { "タイプ" });
        tableBlock.add(tableTitle);

        tableTitle = new BlancoCalcParserTableColumn("パラメータ名",
                new String[] { "パラメータ名" });
        tableBlock.add(tableTitle);

        parser.add(tableBlock);

        // 別のブロック
        tableBlock = new BlancoCalcParserTableBlock("query");
        tableBlock.setStartString(new String[] { "SQL文" });
        tableBlock.setEndString(new String[] { "SQLエリア終了" });
        tableBlock.setSearchRangeForTitleY(1);
        tableBlock.setSearchRangeY(100);
        tableTitle = new BlancoCalcParserTableColumn("query-line",
                new String[] { "SQL文" });
        tableBlock.add(tableTitle);
        parser.add(tableBlock);

        // 具体的なハンドラは このテストからは除去します。
        parser.chainContentHandlerStream(new BlancoDbExcelContentHandler(
                new SystemOutContentHandler()));

        try {
            // ルートノード名を指定。
            parser.setProperty(BlancoCalcParser.URI_PROPERTY_NAME_WORKBOOK,
                    "blanco-db");
            outStream = new BufferedOutputStream(new FileOutputStream(
                    "./meta/blancoCalcParserTestData.xml"));
            BlancoCalcParser.getTransformer().transform(
                    new SAXSource(parser, new InputSource(
                            "./meta/blancoCalcParserTestData.xls")),
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
