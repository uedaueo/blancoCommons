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

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import blanco.commons.calc.BlancoCalcUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Calcを読み取ってみるための超簡単なSAX2パーサです。 <br>
 * 極めて単純なSAXパース実装としてのサンプルです。
 * 
 * @author IGA Tosiki
 */
public class BlancoSimpleCalcParser extends AbstractBlancoCalcParser {

    protected void startSheet(final String sheetName) throws SAXException {
        System.out.println("シート[" + sheetName + "]を処理...");
        AttributesImpl attrImpl = new AttributesImpl();
        attrImpl.addAttribute("", "name", "name", "CDATA", sheetName);
        getContentHandler().startElement("", "sheet", "sheet", attrImpl);
    }

    protected void endSheet(final Sheet sheet) throws SAXException {
        getContentHandler().endElement("", "sheet", "sheet");
    }

    protected void startRow(final int row) throws SAXException {
    }

    protected void endRow(final int row) throws SAXException {
    }

    protected void startColumn(final int column) throws SAXException {
    }

    protected void endColumn(final int column) throws SAXException {
    }

    /**
     * セルがパースされた際に呼び出されます。
     * 
     * @param column
     *            ここでは1オリジンで呼び出されます。
     * @param row
     *            ここでは1オリジンで呼び出されます。
     * @param cellValue
     *            セルの値。
     * @throws SAXException
     */
    protected void fireCell(final int column, final int row,
            final String cellValue) throws SAXException {
        if (cellValue.length() == 0) {
            return;
        }

        final StringBuffer strResult = new StringBuffer(256);
        strResult.append("  (");
        strResult.append(BlancoCalcUtil.columnToLabel(column));
        strResult.append(row);
        strResult.append(")  ");
        strResult.append(cellValue);
        System.out.println(strResult.toString());

        final AttributesImpl attrImpl = new AttributesImpl();
        attrImpl.addAttribute("", "position", "position", "CDATA",
                BlancoCalcUtil.columnToLabel(column) + row);
        getContentHandler().startElement("", "cell", "cell", attrImpl);
        final char[] charArray = cellValue.toCharArray();
        getContentHandler().characters(charArray, 0, charArray.length);
        getContentHandler().endElement("", "cell", "cell");
    }

    /**
     * パースを行います。
     *
     * @param inputSource
     *            解析対象となる入力ソース。
     * @see org.xml.sax.XMLReader#parse(org.xml.sax.InputSource)
     */
    public final void parse(final InputSource inputSource) throws IOException,
            SAXException {

        System.out.println("AbstractBlancoCalcParser#:parse");

        Workbook workbook = null;

        InputStream inStream = null;
        try {
            if (inputSource.getByteStream() != null) {
                // OKです。このまま処理を進めます。
            } else if (inputSource.getSystemId() != null
                    && inputSource.getSystemId().length() > 0) {
                inStream = new FileInputStream(inputSource.getSystemId());
                inputSource.setByteStream(inStream);
            } else {
                throw new IOException("指定されたInputSourceは処理できません.");
            }
            workbook = WorkbookFactory.create(inputSource.getByteStream());

            // ここから本当のパースが始まります。
            parseWorkbook(workbook);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("予期せぬ例外が発生しました.: " + e.toString());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new IOException("予期せぬ例外が発生しました.: " + e.toString());
        } finally {
            if (workbook != null) {
                workbook.close();
            }

            // InputSourceのクローズは外部で行われます。
            // この中では 明示的に開いたストリームのみ処理します。
            if (inStream != null) {
                inStream.close();
            }
        }
    }

    /**
     * シートをパースします。
     *
     * @param sheet
     *            シートオブジェクト。
     * @throws SAXException
     *             SAX例外が発生した場合。
     */
    protected final void parseSheet(final Sheet sheet) throws SAXException {
        // シートのエレメントは上位クラスで処理
        AttributesImpl attrImpl = new AttributesImpl();
        attrImpl.addAttribute("", "name", "name", "CDATA", sheet.getSheetName());
        getContentHandler().startElement("",
                (String) getProperty(URI_PROPERTY_NAME_SHEET),
                (String) getProperty(URI_PROPERTY_NAME_SHEET), attrImpl);

        startSheet(sheet.getSheetName());

        //getLastRowNum()は、0から数えるので +1する。
        int maxRows = sheet.getLastRowNum() + 1;

        for (int row = 0; row < maxRows; row++) {
            startRow(row + 1);
            Row line = sheet.getRow(row);
            if (line != null) {
                for (int column = 0; column < line.getLastCellNum(); column++) {

                    startColumn(column + 1);
                    Cell cell = line.getCell(column);
                    // コンテンツはtrim()せずに、そのままわたします。
                    String value = getCellValue(cell);
                    fireCell(column + 1, row + 1, value);
                    endColumn(column + 1);
                }
            }
            endRow(row + 1);
        }

        endSheet(sheet);

        // シートのエレメントは上位クラスで処理
        getContentHandler().endElement("",
                (String) getProperty(URI_PROPERTY_NAME_SHEET),
                (String) getProperty(URI_PROPERTY_NAME_SHEET));
    }

}
