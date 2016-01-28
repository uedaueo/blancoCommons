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

import org.apache.poi.ss.usermodel.Sheet;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import blanco.commons.calc.BlancoCalcUtil;

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
}
