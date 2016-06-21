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

import java.io.*;

import java.util.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.ss.usermodel.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import blanco.commons.calc.BlancoCalcUtil;
import blanco.commons.calc.parser.block.*;
import blanco.commons.calc.parser.concretesax.BlancoCalcTransformerDefHandler;
import blanco.commons.calc.parser.constants.BlancoCommonsConstantsConstants;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * Calcを読み取るためのSAX2パーサです。 <br>
 * 開始文字列とエンティティ名との関連づけなどを実現します。
 * 
 * @author IGA Tosiki
 */
public class BlancoCalcTransformer extends AbstractBlancoCalcParser {
    /**
     * デバッグモードで動作しているかどうか。
     */
    private static final boolean IS_DEBUG = true;

    /**
     * 出力したいエクセルのタブを取得(追加)
     */
    List <String> strTabSheetName = new ArrayList<String>();

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
    private BlancoCalcTransformerPropertyKey currentKeyMapItem = null;

    private int waitForValueX = -1;

    private int waitForValueY = -1;

    private int waitForIteratorTitleSearchY = -1;

    private boolean isNoCellExistOnRow = true;

    private boolean isFirstIteratorRowItem = true;

    private static Workbook workbook = null;

    /**
     * 現在は BlancoCalcTransformerの起動エントリポイントとなっています。<br>
     * 例:
     * <code>BlancoCalcTransformer ./meta/BlancoCalcTransformerDef.xml ./meta/blancoCsvTemplate.xls ./output.xml</code>
     *
     * @param args
     *            0番目:設定ファイル 1番目:入力ファイル 2番目:出力xmlファイル 3番目:出力excelファイル(追加)
     */
    public static final void main(final String[] args) {
        if (args.length < 4) {
            System.out.println("usage: BlancoCalcTransformer 設定ファイル 入力ファイル 出力xmlファイル 出力excelファイル");
            return;
        }

        InputStream inStreamDef = null;
        InputStream inStream = null;
        OutputStream outStream = null;
        OutputStream outStreamExcel = null;
        try {
            inStreamDef = new BufferedInputStream(new FileInputStream(
                    args[0]));
            inStream = new BufferedInputStream(new FileInputStream(
                    args[1]));
            outStream = new BufferedOutputStream(new FileOutputStream(
                    args[2]));
            outStreamExcel = new BufferedOutputStream(new FileOutputStream(
                    args[3]));
            new BlancoCalcTransformer().process(inStreamDef, inStream, outStream, outStreamExcel);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
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
            if (outStreamExcel != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * BlancoCalcTransformerのコンストラクタ。
     */
    public BlancoCalcTransformer() {
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
     * @param outStreamMeta
     *            Excel出力ストリーム
     * @throws TransformerException
     */
    public void process(final InputStream inStreamDef,
            final InputStream inStreamCalc, final OutputStream outStreamXml, final OutputStream outStreamMeta)
            throws TransformerException, IOException, InvalidFormatException {

        final BlancoCalcTransformer parser = new BlancoCalcTransformer();


        parser.readDef(inStreamDef);

        final SAXSource source = new SAXSource(parser, new InputSource(
                inStreamCalc));

        final TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(source, new StreamResult(outStreamXml));

        //(追加)
        //パース後、ここでエクセル出力します。xmlはtransformerで出力されます。
        try{
            workbook.write(outStreamMeta);
        }catch(IOException e){
            System.out.println(e.toString());
        }finally{
            try {
                outStreamMeta.close();
            }catch(IOException e){
                System.out.println(e.toString());
            }
        }

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
        SAXResult result = new SAXResult(new BlancoCalcTransformerDefHandler() {

            /**
             * ブロックの抽象クラス
             */
            private AbstractBlancoCalcParserBlock blockHeader = null;

            /**
             * 開始文字列を記憶
             */
            private List<String> startStringList = new ArrayList<String>();

            /**
             * PropertyKey関連
             */
            private BlancoCalcTransformerPropertyKey propKey = null;

            /**
             * Valueの配列を作成するためのもの
             */
            private List<String> valueList = new ArrayList<String>();

            /**
             * Valueの配列を作成するためのもの(追加)
             */
            private List<String> propertydataList = new ArrayList<String>();

            /**
             * Valueの配列を作成するためのもの(追加)
             */
            private List<String> columndataList = new ArrayList<String>();
            //private Map<String,String> columndataList = new TreeMap<String,String>();
            /**
             * ValuMapping関連
             */
            private List<BlancoCalcParserValueMapping> valueMappingList = new ArrayList<BlancoCalcParserValueMapping>();

            private List<String> sourceList = new ArrayList<String>();

            private String result = null;

            /**
             * TableColumn関連
             */
            private BlancoCalcTransformerTableColumn tableColumn = null;

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

            @Override
            public void startElementBlancocalcparser(String uri, String localName, String qName, String attrName) throws SAXException {

            }

            @Override
            public void endElementBlancocalcparser(String uri, String localName, String qName) throws SAXException {

            }

            @Override
            public void charactersBlancocalcparser(char[] ch, int start, int length) throws SAXException {

            }

            @Override
            public void ignorableWhitespaceBlancocalcparser(char[] ch, int start, int length) throws SAXException {

            }

            public void startElementPropertyblock(String uri, String localName,
                    String qName, String attrName, String attrWaitY)
                    throws SAXException {
                // ブロックの新規作成。
                blockHeader = new BlancoCalcTransformerPropertyBlock(attrName);
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
                propKey = new BlancoCalcTransformerPropertyKey(attrName);
                propKey.setSearchRangeX(Integer.parseInt(attrWaitX));

                ((BlancoCalcTransformerPropertyBlock) blockHeader).add(propKey);
            }

            public void endElementPropertykey(String uri, String localName,
                    String qName) throws SAXException {
                final String[] values = new String[valueList.size()];
                valueList.toArray(values);
                valueList.clear();
                propKey.setKeyString(values);

                //add
                final String[] data = new String[propertydataList.size()];
                propertydataList.toArray(data);
                propertydataList.clear();
                propKey.setPropertyData(data);

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

            //追加
            public void startElementPropertyData(String uri, String localName,
                                          String qName) throws SAXException {
            }

            //追加
            public void startElementColumnData(String uri, String localName,
                                                 String qName) throws SAXException {
            }

            public void endElementValue(String uri, String localName,
                    String qName) throws SAXException {
            }

            //追加
            public void endElementPropertyData(String uri, String localName,
                                        String qName) throws SAXException {
            }

            //追加
            public void endElementColumnData(String uri, String localName,
                                               String qName) throws SAXException {
            }

            public void charactersValue(char[] ch, int start, int length)
                    throws SAXException {
                valueList.add(new String(ch, start, length));
            }

            //追加
            public void charactersPropertyData(char[] ch, int start, int length)
                    throws SAXException {
                propertydataList.add(new String(ch, start, length));
            }

            //追加
            public void charactersColumnData(char[] ch, int start, int length)
                    throws SAXException {
                columndataList.add(new String(ch, start, length));
            }

            public void ignorableWhitespaceValue(char[] ch, int start,
                    int length) throws SAXException {
            }

            //追加
            public void ignorableWhitespacePropertyData(char[] ch, int start,
                                                 int length) throws SAXException {
            }

            //追加
            public void ignorableWhitespaceColumnData(char[] ch, int start,
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
                blockHeader = new BlancoCalcTransformerTableBlock(attrName);
                blockHeader.setSearchRangeY(Integer.parseInt(attrWaitY));
                ((BlancoCalcTransformerTableBlock) blockHeader)
                        .setSearchRangeForTitleY(Integer
                                .parseInt(attrTitleheight));
                ((BlancoCalcTransformerTableBlock) blockHeader)
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
                tableColumn = new BlancoCalcTransformerTableColumn(attrName);
            }

            public void endElementTablecolumn(String uri, String localName,
                    String qName) throws SAXException {
                final String[] values = new String[valueList.size()];
                valueList.toArray(values);
                valueList.clear();
                tableColumn.setColumnString(values);

                //追加
                final String[] data = new String[columndataList.size()];
                columndataList.toArray(data);
                columndataList.clear();
                tableColumn.setColumnData(data);

                ((BlancoCalcTransformerTableBlock) blockHeader).add(tableColumn);
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
     * パースを行います。
     *
     * @param inputSource
     *            解析対象となる入力ソース。
     * @see org.xml.sax.XMLReader#parse(org.xml.sax.InputSource)
     */
    public final void parse(final InputSource inputSource) throws IOException,
            SAXException {

        System.out.println("BlancoCalcTransformer#:parse");


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

            //出力したいエクセルのタブを取得
            for (int index = 0; index < listBlock.size(); index++) {

                AbstractBlancoCalcParserBlock blockItem = (AbstractBlancoCalcParserBlock) listBlock
                        .get(index);

                if (blockItem instanceof BlancoCalcTransformerPropertyBlock) {

                    if(blockItem.getName().equals(BlancoCommonsConstantsConstants.SHEETNAME_META2XML)){

                        strTabSheetName.add(blockItem.getName());
                        System.out.println(blockItem.getName());

                    }
                    if(blockItem.getName().startsWith(BlancoCommonsConstantsConstants.SHEET_PREFIX_PROPERTY)){

                        strTabSheetName.add(blockItem.getName().substring(22));
                        System.out.println(blockItem.getName().substring(22));

                    }

                }
            }

            //エクセルのシートタブ名変更
            for (int indexSheet = 0; indexSheet < strTabSheetName.size(); indexSheet++) {
                if(indexSheet < workbook.getNumberOfSheets()){
                    Sheet sheet = workbook.getSheetAt(indexSheet);
                    //blancotelegramでmeta2xmlでないなら変更
                    if(sheet.getSheetName().equals(BlancoCommonsConstantsConstants.SHEETNAME_TEMPLATE) && !strTabSheetName.get(indexSheet).equals(BlancoCommonsConstantsConstants.SHEETNAME_META2XML)){
                        workbook.setSheetName(indexSheet, strTabSheetName.get(indexSheet));
                    }
                }else {
                    //エクセルのタブを増やさねばならない場合、前のシートをコピー追加
                    workbook.cloneSheet(indexSheet-1);
                    //名前変更
                    workbook.setSheetName(indexSheet, strTabSheetName.get(indexSheet));
                }
            }


            // ここから本当のパースが始まります。
            // 今回はパースと同時に割り込み値を挿入します。
            parseWorkbook(workbook);


        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("予期せぬ例外が発生しました.: " + e.toString());
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new IOException("予期せぬ例外が発生しました.: " + e.toString());
        } finally {
            //ここで行わない。→process()にて。
//            if (workbook != null) {
//                workbook.close();
//            }

            // InputSourceのクローズは外部で行われます。
            // この中では 明示的に開いたストリームのみ処理します。
            if (inStream != null) {
                inStream.close();
            }
        }
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
    protected void endRow(int row) throws SAXException{

    };

    protected void endRow(final Sheet sheet,int row) throws SAXException {

        waitForValueX = -1;

        if (waitForIteratorTitleSearchY >= 0) {
            // タイトル行サーチの場合には、ここに入る。
            // 状態が何であれ、カウントダウンは実施します。
            waitForIteratorTitleSearchY--;
        }

        if (currentBlock instanceof BlancoCalcTransformerTableBlock) {
            if (isFirstIteratorRowItem == false) {
                final BlancoCalcTransformerTableBlock blockLook = (BlancoCalcTransformerTableBlock) currentBlock;
                if (blockLook.getRowName() != null
                        && blockLook.getRowName().length() > 0) {
                    // ブロックの繰り返し項目にエンティティを追加
                    getContentHandler().endElement("", blockLook.getRowName(),
                            blockLook.getRowName());
                }
            }
        }

        //その行でcell値が一つも見つからない場合
        if (isNoCellExistOnRow) {
            if (waitForValueY >= 0) {
                waitForValueY--;
            }
            if (waitForValueY <= 0) {
                if (currentBlock != null) {

                    //テーブルブロック値挿入処理 追加
                    if (currentBlock instanceof BlancoCalcTransformerTableBlock) {

                        final BlancoCalcTransformerTableBlock blockLook = (BlancoCalcTransformerTableBlock) currentBlock;

                        TreeMap <Integer,LinkedHashMap<String, String>> mapTableData = new TreeMap <Integer,LinkedHashMap<String, String>>();

                        for (int intColumn = 0; intColumn < blockLook.getListSize(); intColumn++) {

                            final BlancoCalcTransformerTableColumn item = blockLook
                                    .findByColumnPosition(intColumn + 1);//ColumnPositionは、1から始まる。

                            //System.out.println("BlancoCalcTransformer size:" + item.getColumnData().length);
                            for(int i = 0; i < item.getColumnData().length; i++){
                                Row wbRow = sheet.getRow(row + i);
                                Cell wbCell = wbRow.getCell(intColumn);
                                String itemData = item.getColumnDataByIndex(i);
                                System.out.println("BlancoCalcTransformer data:" + itemData);
                                //excel出力
                                if(isNum(itemData)) {
                                    //整数の場合
                                    wbCell.setCellValue(Integer.valueOf(itemData).intValue());
                                }else if(isDouble(itemData)){
                                    //Doubleの場合
                                    wbCell.setCellValue(Double.parseDouble(itemData));
                                }else{
                                    wbCell.setCellValue(itemData);
                                }

                                //xml出力処理の為に取っておく
                                if(!mapTableData.containsKey(i)){
                                    mapTableData.put(i, new LinkedHashMap<String, String>());
                                }
                                mapTableData.get(i).put(item.getName(), itemData);

                            }
                            //System.out.println("BlancoCalcTransformer:" + currentKeyMapItem.getName() + "(" + row + "," + column + ",test)");
                        }
                        //xml出力の為に保存
                        if(!mapTableData.isEmpty()){

                            for (int intRowData = 0; intRowData < mapTableData.size(); intRowData++) {
                                if (blockLook.getRowName() != null
                                        && blockLook.getRowName().length() > 0) {
                                    // ブロックの繰り返し項目にエンティティを追加
                                    getContentHandler().startElement("",
                                            blockLook.getRowName(),
                                            blockLook.getRowName(),
                                            new AttributesImpl());
                                }
                                Iterator entries = mapTableData.get(intRowData).entrySet().iterator();
                                while (entries.hasNext()) {
                                    Map.Entry entry= (Map.Entry) entries.next();
                                    String keyName = (String) entry.getKey();
                                    String valName = (String) entry.getValue();
                                    // ここで何かしらの記憶処理
                                    saveNode(keyName, valName);
                                }
                                if (blockLook.getRowName() != null
                                        && blockLook.getRowName().length() > 0) {
                                    // ブロックの繰り返し項目にエンティティを追加
                                    getContentHandler().endElement("", blockLook.getRowName(),
                                            blockLook.getRowName());
                                }
                            }
                        }
                    }
                    if (IS_DEBUG)
                        System.out.println("ブロック[" + currentBlock.getName()
                                + "]を終了します.");
                    getContentHandler().endElement("", currentBlock.getName(),
                            currentBlock.getName());

                    currentBlock = null;
                }
            }
        }

        if (currentBlock instanceof BlancoCalcTransformerPropertyBlock) {
            currentKeyMapItem = null;
        }
    }

    static boolean isNum(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isDouble(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
            if (currentBlock instanceof BlancoCalcTransformerPropertyBlock) {
                currentKeyMapItem = null;
            }
        }
    }

    protected void endColumn(final int column, final int row,
                             final Cell cell) throws SAXException {
        if (waitForValueX >= 0) {
            waitForValueX--;
        }

        if (currentBlock == null) {
            return;
        }

        if (waitForValueX < 0) {
            if (currentBlock instanceof BlancoCalcTransformerPropertyBlock) {

                //プロパティブロック値挿入処理 追加
                //キーがある際に、Cellに割り込み値を代入
                if(currentKeyMapItem != null){

                    if(isNum(currentKeyMapItem.getConcatenatedPropertyData())) {
                        //整数の場合
                        cell.setCellValue(Integer.valueOf(currentKeyMapItem.getConcatenatedPropertyData()).intValue());
                    }else if(isDouble(currentKeyMapItem.getConcatenatedPropertyData())){
                        //Doubleの場合
                        cell.setCellValue(Double.parseDouble(currentKeyMapItem.getConcatenatedPropertyData()));
                    }else{
                        cell.setCellValue(currentKeyMapItem.getConcatenatedPropertyData());
                    }

                    //System.out.println("BlancoCalcTransformer:" + currentKeyMapItem.getName() + "(" + row + "," + column + "," + currentKeyMapItem.getConcatenatedPropertyData() + ")");
                    // ここで何かしらの記憶処理
                    saveNode(currentKeyMapItem.getName(), currentKeyMapItem.getConcatenatedPropertyData());

                }

                currentKeyMapItem = null;
            }
        }
    }

    @Override
    protected void fireCell(final int column, final int row,final String cellValue) throws SAXException {}

    /**
     * セルがパースされた際に呼び出されます。
     * 
     * @param column
     *            ここでは1オリジンで呼び出されます。
     * @param row
     *            ここでは1オリジンで呼び出されます。
     * @param cellValue
     *            セルの値。
     * @param strSheetname
     *            現在のシート名
     * @throws SAXException
     *             SAX例外が発生した場合。
     */
    @SuppressWarnings("deprecation")
    protected void fireCell(final int column, final int row,
                            final String cellValue,
                            final String strSheetname
                            ) throws SAXException {

        //セル値がないならリターン
        if (cellValue.length() == 0) {
            return;
        }

        // 少なくとも実体を発見しました。
        isNoCellExistOnRow = false;

        //currentBlock = スタートキーワード(startstringタグ)によって、
        // エクセルを探索し見つかった時の業務ブロック(xml定義ファイルのブロック)。
        if (currentBlock != null) {
            //セル値がエンドキーワード(endstringタグ)だったら
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

        //値の保存
        if (currentBlock != null) {
            //プロパティブロックの時
            if (currentBlock instanceof BlancoCalcTransformerPropertyBlock) {
                //キーが見つかっている時
                if (currentKeyMapItem != null) {
                    //セル値を読み替えて違う値にマッピングする。
                    // マッピングが行われなかった場合には入力値そのもの。
                    final String value = BlancoCalcParserValueMapping.mapping(
                            cellValue, currentBlock.getValueMapping());
                    if (IS_DEBUG)
                        System.out.println("キー[" + currentKeyMapItem.getName()
                                + "] = 値[" + value + "]");
                    // ここで何かしらの記憶処理
                    saveNode(currentKeyMapItem.getName(), value);
                    return;
                }
                //テーブルブロックの時
            } else if (currentBlock instanceof BlancoCalcTransformerTableBlock) {
                if (waitForIteratorTitleSearchY <= 0) {
                    final BlancoCalcTransformerTableBlock blockLook = (BlancoCalcTransformerTableBlock) currentBlock;
                    final BlancoCalcTransformerTableColumn item = blockLook
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

        //"業務"を発見する
        for (int index = 0; index < listBlock.size(); index++) {
            AbstractBlancoCalcParserBlock blockItem = (AbstractBlancoCalcParserBlock) listBlock
                    .get(index);
            if(blockItem.getName().equals(strSheetname)
                    || blockItem.getName().equals(BlancoCommonsConstantsConstants.SHEET_PREFIX_PROPERTY + strSheetname)
                    || blockItem.getName().equals(BlancoCommonsConstantsConstants.SHEET_PREFIX_TABLE + strSheetname)) {//(追加)

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
                    if (currentBlock instanceof BlancoCalcTransformerTableBlock) {
                        BlancoCalcTransformerTableBlock block = (BlancoCalcTransformerTableBlock) currentBlock;
                        waitForIteratorTitleSearchY = block
                                .getSearchRangeForTitleY();
                    }
                }
            }
        }

        if (currentBlock == null) {
            return;
        }

        //プロパティブロックまたはテーブルブロックのキー定義の中に、セル値があるかを検証
        if (currentBlock instanceof BlancoCalcTransformerPropertyBlock) {
            // キーを探す処理を行います。
            final BlancoCalcTransformerPropertyKey item = ((BlancoCalcTransformerPropertyBlock) currentBlock)
                    .findByStartString(cellValue);
            if (item != null) {
                currentKeyMapItem = item;
                waitForValueX = item.getSearchRangeX();
            }
        } else if (currentBlock instanceof BlancoCalcTransformerTableBlock) {
            final BlancoCalcTransformerTableBlock block = (BlancoCalcTransformerTableBlock) currentBlock;

            // タイトルを探す処理を行います
            final BlancoCalcTransformerTableColumn item = (block)
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

    public void parseSheet(final Sheet sheet) throws SAXException {
        // シートのエレメントは上位クラスで処理
        System.out.println("ama debug:parseSheet:" + sheet.getSheetName());

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

                    fireCell(column + 1, row + 1, value,sheet.getSheetName());
                    endColumn(column , row , cell);
                }
            }
            endRow(sheet, row);
        }

        endSheet(sheet);

        // シートのエレメントは上位クラスで処理
        getContentHandler().endElement("",
                (String) getProperty(URI_PROPERTY_NAME_SHEET),
                (String) getProperty(URI_PROPERTY_NAME_SHEET));
    }


}
