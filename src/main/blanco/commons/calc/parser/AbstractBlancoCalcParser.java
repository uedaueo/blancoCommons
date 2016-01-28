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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

import blanco.commons.parser.ContentHandlerStream;

/**
 * Calcを読み取るためのSAX2パーサです。 <br>
 * 表計算ファイル読み取りの際の汎用的で抽象的なSAX2ハンドラを提供します。
 * 
 * @author IGA Tosiki
 */
public abstract class AbstractBlancoCalcParser implements XMLReader {
    /**
     * コンテンツハンドラを記憶します。
     */
    private ContentHandler contentHandler = null;

    private ContentHandlerStream chainedContentHandler = null;

    public static final String URI_PROPERTY_NAME_WORKBOOK = "http://blanco/commons/calc/parser/workbook";

    public static final String URI_PROPERTY_NAME_SHEET = "http://blanco/commons/calc/parser/sheet";

    private String _propertyNameWorkbook = "workbook";

    private String _propertyNameSheet = "sheet";

    /**
     * フィーチャーを取得します。
     * 
     * @param arg0
     *            フィーチャー。
     * @see org.xml.sax.XMLReader#getFeature(java.lang.String)
     */
    public final boolean getFeature(final String arg0)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        return false;
    }

    /**
     * フィーチャーを設定します。
     * 
     * @param name
     *            フィーチャー名。
     * @param value
     *            フィーチャーの値。
     * @see org.xml.sax.XMLReader#setFeature(java.lang.String, boolean)
     */
    public final void setFeature(final String name, boolean value)
            throws SAXNotRecognizedException, SAXNotSupportedException {
    }

    /**
     * プロパティを取得します。
     */
    public final Object getProperty(final String name)
            throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name.equals(URI_PROPERTY_NAME_WORKBOOK)) {
            return _propertyNameWorkbook;
        } else if (name.equals(URI_PROPERTY_NAME_SHEET)) {
            return _propertyNameSheet;
        } else {
            throw new SAXNotRecognizedException("この名称はハンドリングできません." + name);
        }
    }

    /**
     * プロパティを設定します。
     */
    public final void setProperty(final String name, final Object value)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        if (name.equals(URI_PROPERTY_NAME_WORKBOOK)) {
            _propertyNameWorkbook = (String) value;
        } else if (name.equals(URI_PROPERTY_NAME_SHEET)) {
            _propertyNameSheet = (String) value;
        } else {
            throw new SAXNotRecognizedException("この名称はハンドリングできません." + name);
        }
    }

    /**
     * エンティティリゾルバを設定します。
     * 
     * @param arg0
     *            エンティティリゾルバのオブジェクト。
     * @see org.xml.sax.XMLReader#setEntityResolver(org.xml.sax.EntityResolver)
     */
    public final void setEntityResolver(final EntityResolver arg0) {
    }

    /**
     * エンティティリゾルバを取得します。
     * 
     * @return エンティティリゾルバのオブジェクト。
     * @see org.xml.sax.XMLReader#getEntityResolver()
     */
    public final EntityResolver getEntityResolver() {
        return null;
    }

    /**
     * DTDハンドラを設定します。
     * 
     * @param arg0
     *            DTDハンドラのオブジェクト。
     * @see org.xml.sax.XMLReader#setDTDHandler(org.xml.sax.DTDHandler)
     */
    public final void setDTDHandler(final DTDHandler arg0) {
    }

    /**
     * DTDハンドラのオブジェクトを取得します。
     * 
     * @return DTDハンドラのオブジェクト。
     * @see org.xml.sax.XMLReader#getDTDHandler()
     */
    public final DTDHandler getDTDHandler() {
        return null;
    }

    /**
     * コンテンツハンドラをセットします。
     */
    public final void setContentHandler(ContentHandler arg0) {
        contentHandler = arg0;
        if (chainedContentHandler != null) {
            chainedContentHandler.setContentHandler(arg0);
        }
    }

    /**
     * コンテンツハンドラを取得します。
     */
    public final ContentHandler getContentHandler() {
        if (chainedContentHandler == null) {
            return contentHandler;
        } else {
            return chainedContentHandler;
        }
    }

    public final void chainContentHandlerStream(ContentHandlerStream arg0) {
        chainedContentHandler = arg0;
    }

    /**
     * エラーハンドラーを設定します。
     * 
     * @param arg0
     *            エラーハンドラーオブジェクト。
     * @see org.xml.sax.XMLReader#setErrorHandler(org.xml.sax.ErrorHandler)
     */
    public final void setErrorHandler(ErrorHandler arg0) {
    }

    /**
     * エラーハンドラーを取得します。
     * 
     * @return エラーハンドラーオブジェクト。
     * @see org.xml.sax.XMLReader#getErrorHandler()
     */
    public final ErrorHandler getErrorHandler() {
        return null;
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
     * 与えられたファイルをパースします。
     * 
     * 基本的には inputSource版のパースを推奨します。
     * 
     * @param arg0
     *            ファイルのパス。
     */
    public final void parse(final String arg0) throws IOException, SAXException {
        InputSource inputSource = new InputSource(arg0);
        inputSource.setByteStream(new FileInputStream(arg0));
        parse(inputSource);
    }

    /**
     * ワークブックをパースします
     * 
     * @param workbook
     *            ワークブック
     * @throws SAXException
     *             SAX例外が発生した場合。
     */
    private void parseWorkbook(final Workbook workbook) throws SAXException {
        getContentHandler().startDocument();
        getContentHandler().startElement("",
                (String) getProperty(URI_PROPERTY_NAME_WORKBOOK),
                (String) getProperty(URI_PROPERTY_NAME_WORKBOOK),
                new AttributesImpl());

        for (int indexSheet = 0; indexSheet < workbook.getNumberOfSheets(); indexSheet++) {
            Sheet sheet = workbook.getSheetAt(indexSheet);
            parseSheet(sheet);
        }
        getContentHandler().endElement("",
                (String) getProperty(URI_PROPERTY_NAME_WORKBOOK),
                (String) getProperty(URI_PROPERTY_NAME_WORKBOOK));
        getContentHandler().endDocument();
    }

    /**
     * シートをパースします。
     * 
     * @param sheet
     *            シートオブジェクト。
     * @throws SAXException
     *             SAX例外が発生した場合。
     */
    private final void parseSheet(final Sheet sheet) throws SAXException {
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

    public static String getCellValue(Cell cell) {
        // 2016.01.20 j.amano
        // 今回のjxl to poi においての仕様
        //------------------------
        //セルの書式:\-1,000
        //jxl:($1,000)←$になってしまっている
        //poi:-1000
        //------------------------
        //セルの書式:2016/1/20
        //jxl:0020, 1月 20, 2016
        //poi:2016/01/20 00:00:00
        //------------------------
        //セルの書式:#REF!←エラーの場合
        //jxl:#REF!
        //poi:#REF!
        //------------------------
        //セルの書式:▲1,000
        //jxl:"▲ "1,000
        //poi:-1000
        //------------------------

        if(cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    return "";
                case Cell.CELL_TYPE_STRING:
                    return cell.getRichStringCellValue().getString();
                case Cell.CELL_TYPE_BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case Cell.CELL_TYPE_NUMERIC:
                    // 日付・整数・少数の判別を行う
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 日付型として値を取得
                        Date dt =cell.getDateCellValue();
                        // 変換後の日付文字列の書式を指定
                        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String sDate = df.format(dt);
                        return sDate;
                    }
                    // 整数の場合、.0を除去
                    DecimalFormat format = new DecimalFormat("0.#");
                    return format.format(cell.getNumericCellValue());
                case Cell.CELL_TYPE_FORMULA:
                    Workbook wb = cell.getSheet().getWorkbook();
                    CreationHelper crateHelper = wb.getCreationHelper();
                    FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
                    return getCellValue(evaluator.evaluateInCell(cell));
                case Cell.CELL_TYPE_ERROR:
                    byte errorCode = cell.getErrorCellValue();
                    FormulaError error = FormulaError.forInt(errorCode);
                    String errorText = error.getString();
                    return errorText;
                default:
                    return "";
            }
        }
        return "";
    }

    /**
     * シートが開始される際に呼び出されます。
     * 
     * @param sheetName
     *            シート名。
     * @throws SAXException
     *             SAX例外が発生した場合。
     */
    protected abstract void startSheet(String sheetName) throws SAXException;

    /**
     * シートが終了される際に呼び出されます。
     * 
     * @param sheet
     *            シートオブジェクト。
     * @throws SAXException
     *             SAX例外が発生した場合。
     */
    protected abstract void endSheet(final Sheet sheet) throws SAXException;

    protected abstract void startRow(int row) throws SAXException;

    protected abstract void endRow(int row) throws SAXException;

    protected abstract void startColumn(int column) throws SAXException;

    protected abstract void endColumn(int column) throws SAXException;

    /**
     * セルがある際に呼び出されます。
     * 
     * @param column
     * @param row
     * @param cellValue
     * @throws SAXException
     */
    protected abstract void fireCell(int column, int row, String cellValue)
            throws SAXException;

    /**
     * Transformerを取得します。
     * 
     * @return トランスフォーまーオブジェクト。
     * @throws TransformerFactoryConfigurationError
     *             トランスフォーマーファクトリーのコンフィグレーションの例外が発生した場合。
     * @throws TransformerConfigurationException
     *             トランスフォーマーコンフィグレーションの例外が発生した場合。
     */
    public static final Transformer getTransformer()
            throws TransformerFactoryConfigurationError,
            TransformerConfigurationException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty("standalone", "yes");
        transformer.setOutputProperty("indent", "yes");
        return transformer;
    }
}
