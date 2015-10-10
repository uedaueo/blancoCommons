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

import java.io.OutputStream;
import java.io.Writer;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

;

/**
 * BlancoCalcParserDefSerializer具象化SAXクラス<br>
 * SAXを利用したXML書き出しをラッピングするクラスです。<br>
 * このクラスは解析用XMLファイルを入力として具象化SAXクラスとして生成されました。<br>
 * このソースコードはblancoIgにより機械的に自動生成されています。 このクラスは implements ContentHandler は行いません。<br>
 * 典型的な利用方法は下記のようになります。<br>
 * 
 * <pre>
 * BlancoCalcParserDefSerializer serializer = new BlancoCalcParserDefSerializer(
 *         outStream);
 * serializer.startDocument();
 * serializer.startElementXXXX();
 * serializer.characters(&quot;発生させたい文字列&quot;);
 * serializer.endElementXXXX();
 * serializer.endDocument();
 * </pre>
 */
public class BlancoCalcParserDefSerializer {
    /**
     * 内部的に利用する出力用SAXハンドラ<br>
     * 連結先のストリームは外部でcloseされる必要があります。
     */
    private TransformerHandler _saxHandler;

    public BlancoCalcParserDefSerializer(OutputStream outStream)
            throws TransformerConfigurationException {
        TransformerFactory tf = TransformerFactory.newInstance();
        SAXTransformerFactory saxTf = (SAXTransformerFactory) tf;
        _saxHandler = saxTf.newTransformerHandler();
        _saxHandler.setResult(new StreamResult(outStream));
    }

    public BlancoCalcParserDefSerializer(Writer writer)
            throws TransformerConfigurationException {
        TransformerFactory tf = TransformerFactory.newInstance();
        SAXTransformerFactory saxTf = (SAXTransformerFactory) tf;
        _saxHandler = saxTf.newTransformerHandler();
        _saxHandler.setResult(new StreamResult(writer));
    }

    /**
     * startDocumentを発生させます。
     * 
     * @throws SAXException
     *             SAX関連の例外が発生した場合。
     */
    public void startDocument() throws SAXException {
        _saxHandler.startDocument();
    }

    /**
     * endDocumentを発生させます。
     * 
     * @throws SAXException
     *             SAX関連の例外が発生した場合。
     */
    public void endDocument() throws SAXException {
        _saxHandler.endDocument();
    }

    /** startPrefixMappingを発生させます。 */
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        _saxHandler.startPrefixMapping(prefix, uri);
    }

    /**
     * endPrefixMappingを発生させます。
     * 
     * @throws SAXException
     *             SAX関連の例外が発生した場合。
     */
    public void endPrefixMapping(String prefix) throws SAXException {
        _saxHandler.endPrefixMapping(prefix);
    }

    /**
     * setDocumentLocatorを発生させます。。
     * 
     * @param locator
     *            ロケータ
     */
    public void setDocumentLocator(Locator locator) {
        _saxHandler.setDocumentLocator(locator);
    }

    /**
     * processingInstructionを発生させます。
     * 
     * @throws SAXException
     *             SAX関連の例外が発生した場合。
     */
    public void processingInstruction(String target, String data)
            throws SAXException {
        _saxHandler.processingInstruction(target, data);
    }

    /**
     * skippedEntityを発生させます。
     * 
     * @throws SAXException
     *             SAX関連の例外が発生した場合。
     */
    public void skippedEntity(String name) throws SAXException {
        _saxHandler.skippedEntity(name);
    }

    /**
     * charactersメソッドを呼び出します。<br>
     * 
     * @param ch
     *            出力したい文字列
     * @param start
     *            開始位置
     * @param length
     *            文字列の長さ
     * 
     * @throws SAXException
     *             SAX関連の例外が発生した場合。
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        _saxHandler.characters(ch, start, length);
    }

    /**
     * charactersメソッドを呼び出します。<br>
     * ※簡易化のために 引数を java.lang.String化したものです。
     * 
     * @param data
     *            出力したい文字列
     * 
     * @throws SAXException
     *             SAX関連の例外が発生した場合。
     */
    public void characters(String data) throws SAXException {
        final char[] chars = data.toCharArray();
        _saxHandler.characters(chars, 0, chars.length);
    }

    /**
     * ignorableWhitespaceを発生させます。
     * 
     * @throws SAXException
     *             SAX関連の例外が発生した場合。
     */
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
        _saxHandler.ignorableWhitespace(ch, start, length);
    }

    /**
     * startElementを接頭辞付きの修飾名[blanco]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [blanco]<br>
     * 接頭辞付きの修飾名[blanco]<br>
     * 
     * @param attrVersion
     *            アトリビュート[version]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     */
    public void startElementBlanco(String attrVersion) throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        if (attrVersion != null) {
            attributes.addAttribute("", "version", "version", "CDATA",
                    attrVersion);
        }
        _saxHandler.startElement("", "blanco", "blanco", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[blanco]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [blanco]<br>
     * 接頭辞付きの修飾名[blanco]<br>
     */
    public void endElementBlanco() throws SAXException {
        _saxHandler.endElement("", "blanco", "blanco");
    }

    /**
     * startElementを接頭辞付きの修飾名[target]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [target]<br>
     * 接頭辞付きの修飾名[target]<br>
     * 
     * @param attrName
     *            アトリビュート[name]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     */
    public void startElementTarget(String attrName) throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        if (attrName != null) {
            attributes.addAttribute("", "name", "name", "CDATA", attrName);
        }
        _saxHandler.startElement("", "target", "target", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[target]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [target]<br>
     * 接頭辞付きの修飾名[target]<br>
     */
    public void endElementTarget() throws SAXException {
        _saxHandler.endElement("", "target", "target");
    }

    /**
     * startElementを接頭辞付きの修飾名[blancocalcparser]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [blancocalcparser]<br>
     * 接頭辞付きの修飾名[blancocalcparser]<br>
     * 
     * @param attrName
     *            アトリビュート[name]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     */
    public void startElementBlancocalcparser(String attrName)
            throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        if (attrName != null) {
            attributes.addAttribute("", "name", "name", "CDATA", attrName);
        }
        _saxHandler.startElement("", "blancocalcparser", "blancocalcparser",
                attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[blancocalcparser]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [blancocalcparser]<br>
     * 接頭辞付きの修飾名[blancocalcparser]<br>
     */
    public void endElementBlancocalcparser() throws SAXException {
        _saxHandler.endElement("", "blancocalcparser", "blancocalcparser");
    }

    /**
     * startElementを接頭辞付きの修飾名[propertyblock]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [propertyblock]<br>
     * 接頭辞付きの修飾名[propertyblock]<br>
     * 
     * @param attrName
     *            アトリビュート[name]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     * @param attrWaitY
     *            アトリビュート[waitY]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     */
    public void startElementPropertyblock(String attrName, String attrWaitY)
            throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        if (attrName != null) {
            attributes.addAttribute("", "name", "name", "CDATA", attrName);
        }
        if (attrWaitY != null) {
            attributes.addAttribute("", "waitY", "waitY", "CDATA", attrWaitY);
        }
        _saxHandler.startElement("", "propertyblock", "propertyblock",
                attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[propertyblock]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [propertyblock]<br>
     * 接頭辞付きの修飾名[propertyblock]<br>
     */
    public void endElementPropertyblock() throws SAXException {
        _saxHandler.endElement("", "propertyblock", "propertyblock");
    }

    /**
     * startElementを接頭辞付きの修飾名[startstring]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [startstring]<br>
     * 接頭辞付きの修飾名[startstring]<br>
     */
    public void startElementStartstring() throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        _saxHandler.startElement("", "startstring", "startstring", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[startstring]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [startstring]<br>
     * 接頭辞付きの修飾名[startstring]<br>
     */
    public void endElementStartstring() throws SAXException {
        _saxHandler.endElement("", "startstring", "startstring");
    }

    /**
     * startElementを接頭辞付きの修飾名[propertykey]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [propertykey]<br>
     * 接頭辞付きの修飾名[propertykey]<br>
     * 
     * @param attrName
     *            アトリビュート[name]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     * @param attrWaitX
     *            アトリビュート[waitX]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     */
    public void startElementPropertykey(String attrName, String attrWaitX)
            throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        if (attrName != null) {
            attributes.addAttribute("", "name", "name", "CDATA", attrName);
        }
        if (attrWaitX != null) {
            attributes.addAttribute("", "waitX", "waitX", "CDATA", attrWaitX);
        }
        _saxHandler.startElement("", "propertykey", "propertykey", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[propertykey]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [propertykey]<br>
     * 接頭辞付きの修飾名[propertykey]<br>
     */
    public void endElementPropertykey() throws SAXException {
        _saxHandler.endElement("", "propertykey", "propertykey");
    }

    /**
     * startElementを接頭辞付きの修飾名[value]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [value]<br>
     * 接頭辞付きの修飾名[value]<br>
     */
    public void startElementValue() throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        _saxHandler.startElement("", "value", "value", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[value]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [value]<br>
     * 接頭辞付きの修飾名[value]<br>
     */
    public void endElementValue() throws SAXException {
        _saxHandler.endElement("", "value", "value");
    }

    /**
     * startElementを接頭辞付きの修飾名[valuemapping]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [valuemapping]<br>
     * 接頭辞付きの修飾名[valuemapping]<br>
     */
    public void startElementValuemapping() throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        _saxHandler
                .startElement("", "valuemapping", "valuemapping", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[valuemapping]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [valuemapping]<br>
     * 接頭辞付きの修飾名[valuemapping]<br>
     */
    public void endElementValuemapping() throws SAXException {
        _saxHandler.endElement("", "valuemapping", "valuemapping");
    }

    /**
     * startElementを接頭辞付きの修飾名[result]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [result]<br>
     * 接頭辞付きの修飾名[result]<br>
     */
    public void startElementResult() throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        _saxHandler.startElement("", "result", "result", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[result]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [result]<br>
     * 接頭辞付きの修飾名[result]<br>
     */
    public void endElementResult() throws SAXException {
        _saxHandler.endElement("", "result", "result");
    }

    /**
     * startElementを接頭辞付きの修飾名[source]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [source]<br>
     * 接頭辞付きの修飾名[source]<br>
     */
    public void startElementSource() throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        _saxHandler.startElement("", "source", "source", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[source]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [source]<br>
     * 接頭辞付きの修飾名[source]<br>
     */
    public void endElementSource() throws SAXException {
        _saxHandler.endElement("", "source", "source");
    }

    /**
     * startElementを接頭辞付きの修飾名[tableblock]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [tableblock]<br>
     * 接頭辞付きの修飾名[tableblock]<br>
     * 
     * @param attrName
     *            アトリビュート[name]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     * @param attrWaitY
     *            アトリビュート[waitY]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     * @param attrTitleheight
     *            アトリビュート[titleheight]の値を渡します。アトリビュートをセットしたくない場合には
     *            nullをセットしてください。
     * @param attrRowname
     *            アトリビュート[rowname]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     */
    public void startElementTableblock(String attrName, String attrWaitY,
            String attrTitleheight, String attrRowname) throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        if (attrName != null) {
            attributes.addAttribute("", "name", "name", "CDATA", attrName);
        }
        if (attrWaitY != null) {
            attributes.addAttribute("", "waitY", "waitY", "CDATA", attrWaitY);
        }
        if (attrTitleheight != null) {
            attributes.addAttribute("", "titleheight", "titleheight", "CDATA",
                    attrTitleheight);
        }
        if (attrRowname != null) {
            attributes.addAttribute("", "rowname", "rowname", "CDATA",
                    attrRowname);
        }
        _saxHandler.startElement("", "tableblock", "tableblock", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[tableblock]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [tableblock]<br>
     * 接頭辞付きの修飾名[tableblock]<br>
     */
    public void endElementTableblock() throws SAXException {
        _saxHandler.endElement("", "tableblock", "tableblock");
    }

    /**
     * startElementを接頭辞付きの修飾名[tablecolumn]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [tablecolumn]<br>
     * 接頭辞付きの修飾名[tablecolumn]<br>
     * 
     * @param attrName
     *            アトリビュート[name]の値を渡します。アトリビュートをセットしたくない場合には nullをセットしてください。
     */
    public void startElementTablecolumn(String attrName) throws SAXException {
        AttributesImpl attributes = new AttributesImpl();
        if (attrName != null) {
            attributes.addAttribute("", "name", "name", "CDATA", attrName);
        }
        _saxHandler.startElement("", "tablecolumn", "tablecolumn", attributes);
    }

    /**
     * endElementを接頭辞付きの修飾名[tablecolumn]として呼び出します。<br>
     * ※基本的な情報はメソッド実装に含まれるので引数からは除かれています。<br>
     * 名前空間URI []<br>
     * ローカル名 [tablecolumn]<br>
     * 接頭辞付きの修飾名[tablecolumn]<br>
     */
    public void endElementTablecolumn() throws SAXException {
        _saxHandler.endElement("", "tablecolumn", "tablecolumn");
    }
}
