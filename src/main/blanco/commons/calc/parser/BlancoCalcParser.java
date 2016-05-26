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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import blanco.commons.calc.BlancoCalcUtil;
import blanco.commons.calc.parser.block.AbstractBlancoCalcParserBlock;
import blanco.commons.calc.parser.block.BlancoCalcParserPropertyBlock;
import blanco.commons.calc.parser.block.BlancoCalcParserPropertyKey;
import blanco.commons.calc.parser.block.BlancoCalcParserTableBlock;
import blanco.commons.calc.parser.block.BlancoCalcParserTableColumn;
import blanco.commons.calc.parser.block.BlancoCalcParserValueMapping;
import blanco.commons.calc.parser.concretesax.BlancoCalcParserDefHandler;

/**
 * Calcを読み取るためのSAX2パーサです。 <br>
 * 開始文字列とエンティティ名との関連づけなどを実現します。
 * 
 * @author IGA Tosiki
 */
public class BlancoCalcParser extends AbstractBlancoCalcParser {
    /**
     * デバッグモードで動作しているかどうか。
     */
    private static final boolean IS_DEBUG = false;

    /**
     * 内部的に保持しているブロック
     */
    private List<AbstractBlancoCalcParserBlock> listBlock = new ArrayList<AbstractBlancoCalcParserBlock>();

    /**
     * カレントブロック
     */
    private AbstractBlancoCalcParserBlock currentBlock = null;

    /**
     * キーマップの際のカレントアイテム
     */
    private BlancoCalcParserPropertyKey currentKeyMapItem = null;

    private int waitForValueX = -1;

    private int waitForValueY = -1;

    private int waitForIteratorTitleSearchY = -1;

    private boolean isNoCellExistOnRow = true;

    private boolean isFirstIteratorRowItem = true;

    /**
     * 現在は BlancoCalcParserの起動エントリポイントとなっています。<br>
     * 例:
     * <code>BlancoCalcParser ./meta/BlancoCalcParserDef.xml ./meta/blancoCsvTemplate.xls ./output.xml</code>
     * 
     * @param args
     *            0番目:設定ファイル 1番目:入力ファイル 2番目:出力ファイル
     */
    public static final void main(final String[] args) {
        if (args.length < 3) {
            System.out.println("usage: BlancoCalcParser 設定ファイル 入力ファイル 出力ファイル");
            return;
        }

        InputStream inStreamDef = null;
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            inStreamDef = new FileInputStream(args[0]);
            inStream = new BufferedInputStream(new FileInputStream(new File(
                    args[1])));
            outStream = new BufferedOutputStream(new FileOutputStream(new File(
                    args[2])));
            new BlancoCalcParser().process(inStreamDef, inStream, outStream);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } finally {
            if (inStreamDef != null) {
                try {
                    inStreamDef.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * BlancoCalcParserのコンストラクタ。
     */
    public BlancoCalcParser() {
    }

    /**
     * 設定情報をもとにExcelファイルをXML化します。
     * 
     * @param inStreamDef
     *            設定情報
     * @param inStreamCalc
     *            Excel入力ストリーム
     * @param outStreamXml
     *            XML出力ストリーム
     * @throws TransformerException
     */
    public void process(final InputStream inStreamDef,
            final InputStream inStreamCalc, final OutputStream outStreamXml)
            throws TransformerException {
        final BlancoCalcParser parser = new BlancoCalcParser();
        parser.readDef(inStreamDef);

        final SAXSource source = new SAXSource(parser, new InputSource(
                inStreamCalc));

        final TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(source, new StreamResult(outStreamXml));
    }

    /**
     * 指定の定義ファイルを読み込みます
     * 
     * @param inStreamDef
     *            入力ストリーム。このストリームは内部的にクローズしません。
     * @throws TransformerException
     */
    public void readDef(final InputStream inStreamDef)
            throws TransformerException {
        SAXResult result = new SAXResult(new BlancoCalcParserDefHandler() {

            /**
             * ブロックの抽象クラス
             */
            private AbstractBlancoCalcParserBlock blockHeader = null;

            /**
             * 開始文字列を記憶
             */
            private List<java.lang.String> startStringList = new ArrayList<java.lang.String>();

            /**
             * PropertyKey関連
             */
            private BlancoCalcParserPropertyKey propKey = null;

            /**
             * Valueの配列を作成するためのもの
             */
            private List<java.lang.String> valueList = new ArrayList<java.lang.String>();

            /**
             * ValuMapping関連
             */
            private List<BlancoCalcParserValueMapping> valueMappingList = new ArrayList<BlancoCalcParserValueMapping>();

            private List<java.lang.String> sourceList = new ArrayList<java.lang.String>();

            private String result = null;

            /**
             * TableColumn関連
             */
            private BlancoCalcParserTableColumn tableColumn = null;

            public void startDocument() throws SAXException {
            }

            public void endDocument() throws SAXException {
            }

            public void startElementBlanco(String uri, String localName,
                    String qName, String attrVersion) throws SAXException {
                // System.out.println("start: blanco: version[" + attrVersion
                // + "]");
            }

            public void endElementBlanco(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void charactersBlanco(char[] ch, int start, int length)
                    throws SAXException {
            }

            public void ignorableWhitespaceBlanco(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementTarget(String uri, String localName,
                    String qName, String attrName) throws SAXException {
            }

            public void endElementTarget(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void charactersTarget(char[] ch, int start, int length)
                    throws SAXException {
            }

            public void ignorableWhitespaceTarget(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementBlancocalcparser(String uri,
                    String localName, String qName, String attrName)
                    throws SAXException {
            }

            public void endElementBlancocalcparser(String uri,
                    String localName, String qName) throws SAXException {
            }

            public void charactersBlancocalcparser(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void ignorableWhitespaceBlancocalcparser(char[] ch,
                    int start, int length) throws SAXException {
            }

            public void startElementPropertyblock(String uri, String localName,
                    String qName, String attrName, String attrWaitY)
                    throws SAXException {
                // ブロックの新規作成。
                blockHeader = new BlancoCalcParserPropertyBlock(attrName);
                blockHeader.setSearchRangeY(Integer.parseInt(attrWaitY));

                add(blockHeader);
            }

            public void endElementPropertyblock(String uri, String localName,
                    String qName) throws SAXException {
                final String[] startString = new String[startStringList.size()];
                startStringList.toArray(startString);
                startStringList.clear();

                final BlancoCalcParserValueMapping valuemappings[] = new BlancoCalcParserValueMapping[valueMappingList
                        .size()];
                valueMappingList.toArray(valuemappings);
                valueMappingList.clear();

                // ブロックの終了
                blockHeader.setStartString(startString);
                blockHeader.setValueMapping(valuemappings);
            }

            public void charactersPropertyblock(char[] ch, int start, int length)
                    throws SAXException {
            }

            public void ignorableWhitespacePropertyblock(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementStartstring(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void endElementStartstring(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void charactersStartstring(char[] ch, int start, int length)
                    throws SAXException {
                startStringList.add(new String(ch, start, length));
            }

            public void ignorableWhitespaceStartstring(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementPropertykey(String uri, String localName,
                    String qName, String attrName, String attrWaitX)
                    throws SAXException {
                propKey = new BlancoCalcParserPropertyKey(attrName);
                propKey.setSearchRangeX(Integer.parseInt(attrWaitX));

                ((BlancoCalcParserPropertyBlock) blockHeader).add(propKey);
            }

            public void endElementPropertykey(String uri, String localName,
                    String qName) throws SAXException {
                final String[] values = new String[valueList.size()];
                valueList.toArray(values);
                valueList.clear();
                propKey.setKeyString(values);
            }

            public void charactersPropertykey(char[] ch, int start, int length)
                    throws SAXException {
            }

            public void ignorableWhitespacePropertykey(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementValue(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void endElementValue(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void charactersValue(char[] ch, int start, int length)
                    throws SAXException {
                valueList.add(new String(ch, start, length));
            }

            public void ignorableWhitespaceValue(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementValuemapping(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void endElementValuemapping(String uri, String localName,
                    String qName) throws SAXException {
                final String[] sources = new String[sourceList.size()];
                sourceList.toArray(sources);
                sourceList.clear();

                valueMappingList.add(new BlancoCalcParserValueMapping(sources,
                        result));
                result = null;
            }

            public void charactersValuemapping(char[] ch, int start, int length)
                    throws SAXException {
            }

            public void ignorableWhitespaceValuemapping(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementResult(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void endElementResult(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void charactersResult(char[] ch, int start, int length)
                    throws SAXException {
                result = new String(ch, start, length);
            }

            public void ignorableWhitespaceResult(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementSource(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void endElementSource(String uri, String localName,
                    String qName) throws SAXException {
            }

            public void charactersSource(char[] ch, int start, int length)
                    throws SAXException {
                sourceList.add(new String(ch, start, length));
            }

            public void ignorableWhitespaceSource(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementTableblock(String uri, String localName,
                    String qName, String attrName, String attrWaitY,
                    String attrTitleheight, String attrRowname)
                    throws SAXException {

                // ブロックの新規作成。
                blockHeader = new BlancoCalcParserTableBlock(attrName);
                blockHeader.setSearchRangeY(Integer.parseInt(attrWaitY));
                ((BlancoCalcParserTableBlock) blockHeader)
                        .setSearchRangeForTitleY(Integer
                                .parseInt(attrTitleheight));
                ((BlancoCalcParserTableBlock) blockHeader)
                        .setRowName(attrRowname);
                add(blockHeader);
            }

            public void endElementTableblock(String uri, String localName,
                    String qName) throws SAXException {
                final String[] startString = new String[startStringList.size()];
                startStringList.toArray(startString);
                startStringList.clear();

                final BlancoCalcParserValueMapping valuemappings[] = new BlancoCalcParserValueMapping[valueMappingList
                        .size()];
                valueMappingList.toArray(valuemappings);
                valueMappingList.clear();

                // ブロックの終了
                blockHeader.setStartString(startString);
                blockHeader.setValueMapping(valuemappings);
            }

            public void charactersTableblock(char[] ch, int start, int length)
                    throws SAXException {
            }

            public void ignorableWhitespaceTableblock(char[] ch, int start,
                    int length) throws SAXException {
            }

            public void startElementTablecolumn(String uri, String localName,
                    String qName, String attrName) throws SAXException {
                tableColumn = new BlancoCalcParserTableColumn(attrName);
            }

            public void endElementTablecolumn(String uri, String localName,
                    String qName) throws SAXException {
                final String[] values = new String[valueList.size()];
                valueList.toArray(values);
                valueList.clear();
                tableColumn.setColumnString(values);

                ((BlancoCalcParserTableBlock) blockHeader).add(tableColumn);
            }

            public void charactersTablecolumn(char[] ch, int start, int length)
                    throws SAXException {
            }

            public void ignorableWhitespaceTablecolumn(char[] ch, int start,
                    int length) throws SAXException {
            }
        });

        final TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(new StreamSource(inStreamDef), result);
    }

    /**
     * ブロックを追加します。
     * 
     * @param block
     *            追加したいブロックオブジェクト。
     */
    public void add(final AbstractBlancoCalcParserBlock block) {
        listBlock.add(block);
    }

    protected void startSheet(String sheetName) throws SAXException {
        if (IS_DEBUG)
            System.out.println("シート[" + sheetName + "]を処理...");
    }

    protected void endSheet(Sheet sheet) throws SAXException {
        if (currentBlock != null) {
            getContentHandler().endElement("", currentBlock.getName(),
                    currentBlock.getName());
            currentBlock = null;
        }

        waitForValueX = -1;
        waitForValueY = -1;
        waitForIteratorTitleSearchY = -1;
        currentKeyMapItem = null;
    }

    protected void startRow(int row) throws SAXException {
        isNoCellExistOnRow = true;
        isFirstIteratorRowItem = true;
    }

    protected void endRow(int row) throws SAXException {
        waitForValueX = -1;

        if (waitForIteratorTitleSearchY >= 0) {
            // タイトル行サーチの場合には、ここに入る。
            // 状態が何であれ、カウントダウンは実施します。
            waitForIteratorTitleSearchY--;
        }

        if (currentBlock instanceof BlancoCalcParserTableBlock) {
            if (isFirstIteratorRowItem == false) {
                final BlancoCalcParserTableBlock blockLook = (BlancoCalcParserTableBlock) currentBlock;
                if (blockLook.getRowName() != null
                        && blockLook.getRowName().length() > 0) {
                    // ブロックの繰り返し項目にエンティティを追加
                    getContentHandler().endElement("", blockLook.getRowName(),
                            blockLook.getRowName());
                }
            }
        }

        if (isNoCellExistOnRow) {
            if (waitForValueY >= 0) {
                waitForValueY--;
            }
            if (waitForValueY <= 0) {
                if (currentBlock != null) {
                    if (IS_DEBUG)
                        System.out.println("ブロック[" + currentBlock.getName()
                                + "]を終了します.");
                    getContentHandler().endElement("", currentBlock.getName(),
                            currentBlock.getName());

                    currentBlock = null;
                }
            }
        }

        if (currentBlock == null) {
            return;
        }

        if (currentBlock instanceof BlancoCalcParserPropertyBlock) {
            currentKeyMapItem = null;
        }
    }

    protected void startColumn(int column) throws SAXException {
    }

    protected void endColumn(int column) throws SAXException {
        if (waitForValueX >= 0) {
            waitForValueX--;
        }

        if (currentBlock == null) {
            return;
        }

        if (waitForValueX < 0) {
            if (currentBlock instanceof BlancoCalcParserPropertyBlock) {
                currentKeyMapItem = null;
            }
        }
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
     *             SAX例外が発生した場合。
     */
    @SuppressWarnings("deprecation")
    protected void fireCell(final int column, final int row,
            final String cellValue) throws SAXException {

        if (cellValue.length() == 0) {
            return;
        }

        // 少なくとも実体を発見しました。
        isNoCellExistOnRow = false;

        if (currentBlock != null) {
            if (currentBlock.isEndString(cellValue)) {
                // "業務"を発見したので業務ブロック解析に入ります。
                if (IS_DEBUG)
                    System.out.println("ブロック[" + currentBlock.getName()
                            + "]の強制終了文字列(" + cellValue + ")を発見");

                getContentHandler().endElement("", currentBlock.getName(),
                        currentBlock.getName());

                // カレントブロックを破棄
                currentBlock = null;

            }
        }

        if (currentBlock != null) {
            if (currentBlock instanceof BlancoCalcParserPropertyBlock) {
                if (currentKeyMapItem != null) {
                    final String value = BlancoCalcParserValueMapping.mapping(
                            cellValue, currentBlock.getValueMapping());
                    if (IS_DEBUG)
                        System.out.println("キー[" + currentKeyMapItem.getName()
                                + "] = 値[" + value + "]");
                    // ここで何かしらの記憶処理
                    saveNode(currentKeyMapItem.getName(), value);
                    return;
                }
            } else if (currentBlock instanceof BlancoCalcParserTableBlock) {
                if (waitForIteratorTitleSearchY <= 0) {
                    final BlancoCalcParserTableBlock blockLook = (BlancoCalcParserTableBlock) currentBlock;
                    final BlancoCalcParserTableColumn item = blockLook
                            .findByColumnPosition(column);
                    if (item == null) {
                        if (IS_DEBUG)
                            System.out
                                    .println("該当する列(" + column + ")は発見できません.");
                        return;
                    }

                    if (isFirstIteratorRowItem) {
                        isFirstIteratorRowItem = false;
                        if (blockLook.getRowName() != null
                                && blockLook.getRowName().length() > 0) {
                            // ブロックの繰り返し項目にエンティティを追加
                            getContentHandler().startElement("",
                                    blockLook.getRowName(),
                                    blockLook.getRowName(),
                                    new AttributesImpl());
                        }
                    }

                    // ここで何かしらの記憶処理
                    final String value = BlancoCalcParserValueMapping.mapping(
                            cellValue, blockLook.getValueMapping());
                    if (IS_DEBUG)
                        System.out.println("列[" + item.getName() + "] = 値["
                                + value + "]");
                    saveNode(item.getName(), value);
                    return;
                }
            }
        }

        if (IS_DEBUG)
            System.out.println("  (" + BlancoCalcUtil.columnToLabel(column)
                    + row + ")" + cellValue);

        for (int index = 0; index < listBlock.size(); index++) {
            AbstractBlancoCalcParserBlock blockItem = (AbstractBlancoCalcParserBlock) listBlock
                    .get(index);
            if (blockItem.isStartString(cellValue)) {
                // "業務"を発見したので業務ブロック解析に入ります。
                if (IS_DEBUG)
                    System.out.println("ブロック[" + blockItem.getName() + "]を発見");

                final AttributesImpl attrImpl = new AttributesImpl();
                getContentHandler().startElement("", blockItem.getName(),
                        blockItem.getName(), attrImpl);

                // カレントブロックを記憶
                currentBlock = blockItem;
                waitForValueY = currentBlock.getSearchRangeY();
                if (currentBlock instanceof BlancoCalcParserTableBlock) {
                    BlancoCalcParserTableBlock block = (BlancoCalcParserTableBlock) currentBlock;
                    waitForIteratorTitleSearchY = block
                            .getSearchRangeForTitleY();
                }
            }
        }

        if (currentBlock == null) {
            return;
        }

        if (currentBlock instanceof BlancoCalcParserPropertyBlock) {
            // キーを探す処理を行います。
            final BlancoCalcParserPropertyKey item = ((BlancoCalcParserPropertyBlock) currentBlock)
                    .findByStartString(cellValue);
            if (item != null) {
                currentKeyMapItem = item;
                waitForValueX = item.getSearchRangeX();
            }
        } else if (currentBlock instanceof BlancoCalcParserTableBlock) {
            final BlancoCalcParserTableBlock block = (BlancoCalcParserTableBlock) currentBlock;

            // タイトルを探す処理を行います
            final BlancoCalcParserTableColumn item = (block)
                    .findByTitleString(cellValue);
            if (item != null) {
                // タイトル位置をサーチできました。
                if (IS_DEBUG)
                    System.out.println("列(" + column + ")はキー[" + cellValue
                            + "]です");
                item.setColumnPosition(column);
            } else {
                if (IS_DEBUG)
                    System.out.println("列(" + column + ")のキー[" + cellValue
                            + "]は登録されたものの中に見つかりません.");
            }
        }
    }

    /**
     * ノードを保存します。
     * 
     * @param key
     *            キー。
     * @param value
     *            値。
     * @throws SAXException
     *             SAX例外が発生した場合。
     */
    protected void saveNode(final String key, final String value)
            throws SAXException {
        getContentHandler().startElement("", key, key, new AttributesImpl());

        final char[] charArray = value.toCharArray();

        getContentHandler().characters(charArray, 0, charArray.length);
        getContentHandler().endElement("", key, key);
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
