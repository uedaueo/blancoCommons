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
package blanco.commons.calc.parser.concretesax;

import java.io.CharArrayWriter;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * BlancoCalcTransformerDefHandler具象化SAXクラス<br>
 * このクラスは解析用XMLファイルを入力として具象化SAXクラスとして生成されました。<br>
 * このソースコードはblancoIgにより機械的に自動生成されています。<br>
 * 典型的な利用方法は下記のようになります。<br>
 * 
 * <pre>
 * TransformerFactory tf = TransformerFactory.newInstance();
 * Transformer transformer = tf.newTransformer();
 * transformer.transform(new StreamSource(inStream), new SAXResult(
 *         new BlancoCalcParserDefHandler())); // 実際には実装クラスのHandlerを与えます。
 * </pre>
 */
public abstract class BlancoCalcTransformerDefHandler implements ContentHandler {
    /**
     * エレメントのスタックです<br>
     * XMLの階層構造を記憶します。
     */
    private Stack<java.lang.String> _elementStack = new Stack<java.lang.String>();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerBlanco = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerTarget = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerBlancocalcparser = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerPropertyblock = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerStartstring = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerPropertykey = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerValue = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。(追加)
     */
    private CharArrayWriter _writerPropertydata = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。(追加)
     */
    private CharArrayWriter _writerColumndata = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerValuemapping = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerResult = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerSource = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerTableblock = new CharArrayWriter();

    /**
     * charactersに渡すためのキャッシュです<br>
     * 文字列データを蓄積します。
     */
    private CharArrayWriter _writerTablecolumn = new CharArrayWriter();

    /**
     * startElementが接頭辞付きの修飾名[blanco]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     * @param attrVersion
     *            アトリビュート[version]の値が渡されます。
     */
    public abstract void startElementBlanco(String uri, String localName,
            String qName, String attrVersion) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[blanco]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementBlanco(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[blanco]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersBlanco(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[blanco]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceBlanco(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[target]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     * @param attrName
     *            アトリビュート[name]の値が渡されます。
     */
    public abstract void startElementTarget(String uri, String localName,
            String qName, String attrName) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[target]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementTarget(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[target]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersTarget(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[target]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceTarget(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[blancocalcparser]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     * @param attrName
     *            アトリビュート[name]の値が渡されます。
     */
    public abstract void startElementBlancocalcparser(String uri,
            String localName, String qName, String attrName)
            throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[blancocalcparser]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementBlancocalcparser(String uri,
            String localName, String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[blancocalcparser]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersBlancocalcparser(char[] ch, int start,
            int length) throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[blancocalcparser]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceBlancocalcparser(char[] ch,
            int start, int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[propertyblock]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     * @param attrName
     *            アトリビュート[name]の値が渡されます。
     * @param attrWaitY
     *            アトリビュート[waitY]の値が渡されます。
     */
    public abstract void startElementPropertyblock(String uri,
            String localName, String qName, String attrName, String attrWaitY)
            throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[propertyblock]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementPropertyblock(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[propertyblock]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersPropertyblock(char[] ch, int start,
            int length) throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[propertyblock]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespacePropertyblock(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[startstring]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void startElementStartstring(String uri, String localName,
            String qName) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[startstring]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementStartstring(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[startstring]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersStartstring(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[startstring]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceStartstring(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[propertykey]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     * @param attrName
     *            アトリビュート[name]の値が渡されます。
     * @param attrWaitX
     *            アトリビュート[waitX]の値が渡されます。
     */
    public abstract void startElementPropertykey(String uri, String localName,
            String qName, String attrName, String attrWaitX)
            throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[propertykey]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementPropertykey(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[propertykey]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersPropertykey(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[propertykey]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespacePropertykey(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[value]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void startElementValue(String uri, String localName,
            String qName) throws SAXException;

    //追加
    public abstract void startElementPropertyData(String uri, String localName,
                                           String qName) throws SAXException;

    //追加
    public abstract void startElementColumnData(String uri, String localName,
                                                  String qName) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[value]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementValue(String uri, String localName,
            String qName) throws SAXException;

    //追加
    public abstract void endElementPropertyData(String uri, String localName,
                                         String qName) throws SAXException;

    //追加
    public abstract void endElementColumnData(String uri, String localName,
                                                String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[value]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersValue(char[] ch, int start, int length)
            throws SAXException;

    //追加
    public abstract void charactersPropertyData(char[] ch, int start, int length)
            throws SAXException;

    //追加
    public abstract void charactersColumnData(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[value]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceValue(char[] ch, int start,
            int length) throws SAXException;

    //追加
    public abstract void ignorableWhitespacePropertyData(char[] ch, int start,
                                                  int length) throws SAXException;

    //追加
    public abstract void ignorableWhitespaceColumnData(char[] ch, int start,
                                                         int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[valuemapping]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void startElementValuemapping(String uri, String localName,
            String qName) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[valuemapping]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementValuemapping(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[valuemapping]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersValuemapping(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[valuemapping]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceValuemapping(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[result]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void startElementResult(String uri, String localName,
            String qName) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[result]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementResult(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[result]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersResult(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[result]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceResult(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[source]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void startElementSource(String uri, String localName,
            String qName) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[source]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementSource(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[source]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersSource(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[source]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceSource(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[tableblock]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     * @param attrName
     *            アトリビュート[name]の値が渡されます。
     * @param attrWaitY
     *            アトリビュート[waitY]の値が渡されます。
     * @param attrTitleheight
     *            アトリビュート[titleheight]の値が渡されます。
     * @param attrRowname
     *            アトリビュート[rowname]の値が渡されます。
     */
    public abstract void startElementTableblock(String uri, String localName,
            String qName, String attrName, String attrWaitY,
            String attrTitleheight, String attrRowname) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[tableblock]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementTableblock(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[tableblock]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersTableblock(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[tableblock]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceTableblock(char[] ch, int start,
            int length) throws SAXException;

    /**
     * startElementが接頭辞付きの修飾名[tablecolumn]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     * @param attrName
     *            アトリビュート[name]の値が渡されます。
     */
    public abstract void startElementTablecolumn(String uri, String localName,
            String qName, String attrName) throws SAXException;

    /**
     * endElementが接頭辞付きの修飾名[tablecolumn]で呼び出されました。<br>
     * ※接頭辞付きの修飾名はメソッド名に含まれるものと同等のものが与えられます。
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public abstract void endElementTablecolumn(String uri, String localName,
            String qName) throws SAXException;

    /**
     * charactersが接頭辞付きの修飾名[tablecolumn]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void charactersTablecolumn(char[] ch, int start, int length)
            throws SAXException;

    /**
     * ignorableWhitespaceが接頭辞付きの修飾名[tablecolumn]で呼び出されました。<br>
     * もとのcharactersメソッドを集約した上でメソッドが呼び出されます。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public abstract void ignorableWhitespaceTablecolumn(char[] ch, int start,
            int length) throws SAXException;

    /**
     * オリジナルのstartElementが呼び出されたので、具象メソッドに呼びわけます
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     * @param atts
     *            アトリビュートのリスト
     */
    public final void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        if (_elementStack.empty() == false) {
            final String previousElementOnStack = (String) _elementStack.peek();
            if (previousElementOnStack.equals("source")) {
                _writerSource.flush();
                char[] wrk = _writerSource.toCharArray();
                _writerSource.reset();
                if (wrk.length > 0) {
                    charactersSource(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("blanco")) {
                _writerBlanco.flush();
                char[] wrk = _writerBlanco.toCharArray();
                _writerBlanco.reset();
                if (wrk.length > 0) {
                    charactersBlanco(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("propertyblock")) {
                _writerPropertyblock.flush();
                char[] wrk = _writerPropertyblock.toCharArray();
                _writerPropertyblock.reset();
                if (wrk.length > 0) {
                    charactersPropertyblock(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("propertykey")) {
                _writerPropertykey.flush();
                char[] wrk = _writerPropertykey.toCharArray();
                _writerPropertykey.reset();
                if (wrk.length > 0) {
                    charactersPropertykey(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("startstring")) {
                _writerStartstring.flush();
                char[] wrk = _writerStartstring.toCharArray();
                _writerStartstring.reset();
                if (wrk.length > 0) {
                    charactersStartstring(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("tablecolumn")) {
                _writerTablecolumn.flush();
                char[] wrk = _writerTablecolumn.toCharArray();
                _writerTablecolumn.reset();
                if (wrk.length > 0) {
                    charactersTablecolumn(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("target")) {
                _writerTarget.flush();
                char[] wrk = _writerTarget.toCharArray();
                _writerTarget.reset();
                if (wrk.length > 0) {
                    charactersTarget(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("blancocalcparser")) {
                _writerBlancocalcparser.flush();
                char[] wrk = _writerBlancocalcparser.toCharArray();
                _writerBlancocalcparser.reset();
                if (wrk.length > 0) {
                    charactersBlancocalcparser(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("value")) {
                _writerValue.flush();
                char[] wrk = _writerValue.toCharArray();
                _writerValue.reset();
                if (wrk.length > 0) {
                    charactersValue(wrk, 0, wrk.length);
                }
            } else if (previousElementOnStack.equals("propertydata")) {//追加
                _writerPropertydata.flush();
                char[] wrk = _writerPropertydata.toCharArray();
                _writerPropertydata.reset();
                if (wrk.length > 0) {
                    charactersPropertyData(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("columndata")) {//追加
                _writerColumndata.flush();
                char[] wrk = _writerColumndata.toCharArray();
                _writerColumndata.reset();
                if (wrk.length > 0) {
                    charactersColumnData(wrk, 0, wrk.length);
                }


            } else if (previousElementOnStack.equals("valuemapping")) {
                _writerValuemapping.flush();
                char[] wrk = _writerValuemapping.toCharArray();
                _writerValuemapping.reset();
                if (wrk.length > 0) {
                    charactersValuemapping(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("tableblock")) {
                _writerTableblock.flush();
                char[] wrk = _writerTableblock.toCharArray();
                _writerTableblock.reset();
                if (wrk.length > 0) {
                    charactersTableblock(wrk, 0, wrk.length);
                }

            } else if (previousElementOnStack.equals("result")) {
                _writerResult.flush();
                char[] wrk = _writerResult.toCharArray();
                _writerResult.reset();
                if (wrk.length > 0) {
                    charactersResult(wrk, 0, wrk.length);
                }

            }
        }
        _elementStack.push(qName);
        if (qName.equals("source")) {
            startElementSource(uri, localName, qName);
        } else if (qName.equals("blanco")) {
            startElementBlanco(uri, localName, qName, atts.getValue("version"));
        } else if (qName.equals("propertyblock")) {
            startElementPropertyblock(uri, localName, qName, atts
                    .getValue("name"), atts.getValue("waitY"));
        } else if (qName.equals("propertykey")) {
            startElementPropertykey(uri, localName, qName, atts
                    .getValue("name"), atts.getValue("waitX"));
        } else if (qName.equals("startstring")) {
            startElementStartstring(uri, localName, qName);
        } else if (qName.equals("tablecolumn")) {
            startElementTablecolumn(uri, localName, qName, atts
                    .getValue("name"));
        } else if (qName.equals("target")) {
            startElementTarget(uri, localName, qName, atts.getValue("name"));
        } else if (qName.equals("blancocalcparser")) {
            startElementBlancocalcparser(uri, localName, qName, atts
                    .getValue("name"));
        } else if (qName.equals("value")) {
            startElementValue(uri, localName, qName);
        } else if (qName.equals("propertydata")) {//追加
            startElementPropertyData(uri, localName, qName);
        } else if (qName.equals("columndata")) {//追加
            startElementColumnData(uri, localName, qName);
        } else if (qName.equals("valuemapping")) {
            startElementValuemapping(uri, localName, qName);
        } else if (qName.equals("tableblock")) {
            startElementTableblock(uri, localName, qName,
                    atts.getValue("name"), atts.getValue("waitY"), atts
                            .getValue("titleheight"), atts.getValue("rowname"));
        } else if (qName.equals("result")) {
            startElementResult(uri, localName, qName);
        }
    }

    /**
     * オリジナルのendElementが呼び出されたので、具象メソッドに呼びわけます
     * 
     * @param uri
     *            名前空間URI
     * @param localName
     *            ローカル名
     * @param qName
     *            接頭辞付きの修飾名
     */
    public final void endElement(String uri, String localName, String qName)
            throws SAXException {
        final String currentElementOnStack = (String) _elementStack.peek();
        if (currentElementOnStack.equals(qName) == false) {
            throw new SAXException("XML異常。期待するエレメント[" + currentElementOnStack
                    + "]と接頭辞付きの修飾名[" + qName + "]とがずれています.");
        }
        if (qName.equals("source")) {
            _writerSource.flush();
            char[] wrk = _writerSource.toCharArray();
            _writerSource.reset();
            if (wrk.length > 0) {
                charactersSource(wrk, 0, wrk.length);
            }
            endElementSource(uri, localName, qName);
        } else if (qName.equals("blanco")) {
            _writerBlanco.flush();
            char[] wrk = _writerBlanco.toCharArray();
            _writerBlanco.reset();
            if (wrk.length > 0) {
                charactersBlanco(wrk, 0, wrk.length);
            }
            endElementBlanco(uri, localName, qName);
        } else if (qName.equals("propertyblock")) {
            _writerPropertyblock.flush();
            char[] wrk = _writerPropertyblock.toCharArray();
            _writerPropertyblock.reset();
            if (wrk.length > 0) {
                charactersPropertyblock(wrk, 0, wrk.length);
            }
            endElementPropertyblock(uri, localName, qName);
        } else if (qName.equals("propertykey")) {
            _writerPropertykey.flush();
            char[] wrk = _writerPropertykey.toCharArray();
            _writerPropertykey.reset();
            if (wrk.length > 0) {
                charactersPropertykey(wrk, 0, wrk.length);
            }
            endElementPropertykey(uri, localName, qName);
        } else if (qName.equals("startstring")) {
            _writerStartstring.flush();
            char[] wrk = _writerStartstring.toCharArray();
            _writerStartstring.reset();
            if (wrk.length > 0) {
                charactersStartstring(wrk, 0, wrk.length);
            }
            endElementStartstring(uri, localName, qName);
        } else if (qName.equals("tablecolumn")) {
            _writerTablecolumn.flush();
            char[] wrk = _writerTablecolumn.toCharArray();
            _writerTablecolumn.reset();
            if (wrk.length > 0) {
                charactersTablecolumn(wrk, 0, wrk.length);
            }
            endElementTablecolumn(uri, localName, qName);
        } else if (qName.equals("target")) {
            _writerTarget.flush();
            char[] wrk = _writerTarget.toCharArray();
            _writerTarget.reset();
            if (wrk.length > 0) {
                charactersTarget(wrk, 0, wrk.length);
            }
            endElementTarget(uri, localName, qName);
        } else if (qName.equals("blancocalcparser")) {
            _writerBlancocalcparser.flush();
            char[] wrk = _writerBlancocalcparser.toCharArray();
            _writerBlancocalcparser.reset();
            if (wrk.length > 0) {
                charactersBlancocalcparser(wrk, 0, wrk.length);
            }
            endElementBlancocalcparser(uri, localName, qName);
        } else if (qName.equals("value")) {
            _writerValue.flush();
            char[] wrk = _writerValue.toCharArray();
            _writerValue.reset();
            if (wrk.length > 0) {
                charactersValue(wrk, 0, wrk.length);
            }
            endElementValue(uri, localName, qName);
        } else if (qName.equals("propertydata")) {//追加
            _writerPropertydata.flush();
            char[] wrk = _writerPropertydata.toCharArray();
            _writerPropertydata.reset();
            if (wrk.length > 0) {
                charactersPropertyData(wrk, 0, wrk.length);
            }
            endElementPropertyData(uri, localName, qName);
        } else if (qName.equals("columndata")) {//追加
            _writerColumndata.flush();
            char[] wrk = _writerColumndata.toCharArray();
            _writerColumndata.reset();
            //空白も数える
            //if (wrk.length > 0) {
                charactersColumnData(wrk, 0, wrk.length);
            //}
            endElementColumnData(uri, localName, qName);
        } else if (qName.equals("valuemapping")) {
            _writerValuemapping.flush();
            char[] wrk = _writerValuemapping.toCharArray();
            _writerValuemapping.reset();
            if (wrk.length > 0) {
                charactersValuemapping(wrk, 0, wrk.length);
            }
            endElementValuemapping(uri, localName, qName);
        } else if (qName.equals("tableblock")) {
            _writerTableblock.flush();
            char[] wrk = _writerTableblock.toCharArray();
            _writerTableblock.reset();
            if (wrk.length > 0) {
                charactersTableblock(wrk, 0, wrk.length);
            }
            endElementTableblock(uri, localName, qName);
        } else if (qName.equals("result")) {
            _writerResult.flush();
            char[] wrk = _writerResult.toCharArray();
            _writerResult.reset();
            if (wrk.length > 0) {
                charactersResult(wrk, 0, wrk.length);
            }
            endElementResult(uri, localName, qName);
        }
        // 最後にポップして階層をひとつ戻します。
        _elementStack.pop();
    }

    /**
     * オリジナルのcharactersが呼び出されたので、具象メソッドに呼びわけます 集約した上で呼びわけを行います。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public final void characters(char[] ch, int start, int length)
            throws SAXException {
        final String currentElementOnStack = (String) _elementStack.peek();
        if (currentElementOnStack.equals("source")) {
            // charactersSource(ch, start, length);
            _writerSource.write(ch, start, length);
        } else if (currentElementOnStack.equals("blanco")) {
            // charactersBlanco(ch, start, length);
            _writerBlanco.write(ch, start, length);
        } else if (currentElementOnStack.equals("propertyblock")) {
            // charactersPropertyblock(ch, start, length);
            _writerPropertyblock.write(ch, start, length);
        } else if (currentElementOnStack.equals("propertykey")) {
            // charactersPropertykey(ch, start, length);
            _writerPropertykey.write(ch, start, length);
        } else if (currentElementOnStack.equals("startstring")) {
            // charactersStartstring(ch, start, length);
            _writerStartstring.write(ch, start, length);
        } else if (currentElementOnStack.equals("tablecolumn")) {
            // charactersTablecolumn(ch, start, length);
            _writerTablecolumn.write(ch, start, length);
        } else if (currentElementOnStack.equals("target")) {
            // charactersTarget(ch, start, length);
            _writerTarget.write(ch, start, length);
        } else if (currentElementOnStack.equals("blancocalcparser")) {
            // charactersBlancocalcparser(ch, start, length);
            _writerBlancocalcparser.write(ch, start, length);
        } else if (currentElementOnStack.equals("value")) {
            // charactersValue(ch, start, length);
            _writerValue.write(ch, start, length);
        } else if (currentElementOnStack.equals("propertydata")) {//追加
            // charactersPropertyData(ch, start, length);
            _writerPropertydata.write(ch, start, length);
        } else if (currentElementOnStack.equals("columndata")) {//追加
            // charactersColumnData(ch, start, length);
            _writerColumndata.write(ch, start, length);
        } else if (currentElementOnStack.equals("valuemapping")) {
            // charactersValuemapping(ch, start, length);
            _writerValuemapping.write(ch, start, length);
        } else if (currentElementOnStack.equals("tableblock")) {
            // charactersTableblock(ch, start, length);
            _writerTableblock.write(ch, start, length);
        } else if (currentElementOnStack.equals("result")) {
            // charactersResult(ch, start, length);
            _writerResult.write(ch, start, length);
        }
    }

    /**
     * オリジナルのignorableWhitespaceが呼び出されたので、具象メソッドに呼びわけます 集約した上で呼びわけを行います。
     * 
     * @param ch
     *            XML文書の文字
     * @param start
     *            配列内の開始位置
     * @param length
     *            配列から読み取る文字数
     */
    public final void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
        final String currentElementOnStack = (String) _elementStack.peek();
        if (currentElementOnStack.equals("source")) {
            ignorableWhitespaceSource(ch, start, length);
        } else if (currentElementOnStack.equals("blanco")) {
            ignorableWhitespaceBlanco(ch, start, length);
        } else if (currentElementOnStack.equals("propertyblock")) {
            ignorableWhitespacePropertyblock(ch, start, length);
        } else if (currentElementOnStack.equals("propertykey")) {
            ignorableWhitespacePropertykey(ch, start, length);
        } else if (currentElementOnStack.equals("startstring")) {
            ignorableWhitespaceStartstring(ch, start, length);
        } else if (currentElementOnStack.equals("tablecolumn")) {
            ignorableWhitespaceTablecolumn(ch, start, length);
        } else if (currentElementOnStack.equals("target")) {
            ignorableWhitespaceTarget(ch, start, length);
        } else if (currentElementOnStack.equals("blancocalcparser")) {
            ignorableWhitespaceBlancocalcparser(ch, start, length);
        } else if (currentElementOnStack.equals("value")) {
            ignorableWhitespaceValue(ch, start, length);
        } else if (currentElementOnStack.equals("propertydata")) {//追加
            ignorableWhitespacePropertyData(ch, start, length);
        } else if (currentElementOnStack.equals("columndata")) {//追加
            ignorableWhitespaceColumnData(ch, start, length);
        } else if (currentElementOnStack.equals("valuemapping")) {
            ignorableWhitespaceValuemapping(ch, start, length);
        } else if (currentElementOnStack.equals("tableblock")) {
            ignorableWhitespaceTableblock(ch, start, length);
        } else if (currentElementOnStack.equals("result")) {
            ignorableWhitespaceResult(ch, start, length);
        }
    }

    /** このメソッドを無視するためのメソッドです。 */
    public void setDocumentLocator(Locator locator) {
    }

    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
    }

    public void endPrefixMapping(String prefix) throws SAXException {
    }

    public void processingInstruction(String target, String data)
            throws SAXException {
    }

    public void skippedEntity(String name) throws SAXException {
    }
}
