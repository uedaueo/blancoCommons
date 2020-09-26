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
package blanco.commons.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * blanco FrameworkにおけるXML関連のユーティリティを集めたクラスです。
 * 
 * @author IGA Tosiki
 */
public class BlancoXmlUtil {
    /**
     * 選択されたノード(エレメントでも可)から文字列を取得します。
     * 
     * あるノードにぶらさがっている全てのテキストデータを取得する場合に利用します。
     * 
     * @param nodeTarget
     *            対象とするターゲットノード
     * @return 取得されたテキスト文字列
     */
    public static final String getTextContent(final Node nodeTarget) {
        if (nodeTarget == null) {
            throw new IllegalArgumentException(
                    "ノードからテキストを取得するメソッドにnullが与えられました。null以外の値を与えるようにしてください。");
        }

        String result = null;
        final NodeList listText = nodeTarget.getChildNodes();
        final int sizeChildList = listText.getLength();
        for (int indexChild = 0; indexChild < sizeChildList; indexChild++) {
            final Node nodeChild = listText.item(indexChild);
            if (nodeChild instanceof Text) {
                final Text textLook = (Text) nodeChild;
                result = (result == null ? textLook.getData() : result
                        + textLook.getData());
            }
        }
        return result;
    }

    /**
     * エレメントから指定のタグ名の文字列を読み込みます。
     * 
     * @param elementTarget
     *            対象とするターゲットエレメント
     * @param tagName
     *            タグ名
     * @return 取得されたテキスト文字列
     */
    public static final String getTextContent(final Element elementTarget,
            final String tagName) {
        if (elementTarget == null) {
            throw new IllegalArgumentException(
                    "エレメントからテキストを取得するメソッドにエレメントとしてnullが与えられました。null以外の値を与えるようにしてください。");
        }
        if (tagName == null) {
            throw new IllegalArgumentException(
                    "エレメントからテキストを取得するメソッドにタグ名としてnullが与えられました。null以外の値を与えるようにしてください。");
        }

        String result = null;
        final NodeList listElementTarget = elementTarget
                .getElementsByTagName(tagName);
        final int sizeList = listElementTarget.getLength();
        for (int index = 0; index < sizeList; index++) {
            final Node nodeLook = listElementTarget.item(index);
            if (nodeLook instanceof Element) {
                final Element elementLook = (Element) nodeLook;

                final NodeList listText = elementLook.getChildNodes();
                final int sizeChildList = listText.getLength();
                for (int indexChild = 0; indexChild < sizeChildList; indexChild++) {
                    final Node nodeChild = listText.item(indexChild);
                    if (nodeChild instanceof Text) {
                        final Text textLook = (Text) nodeChild;
                        result = (result == null ? textLook.getData() : result
                                + textLook.getData());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 与えられたXMLファイルをDOMツリーに変換します。
     * 
     * 内部的には、一般的に利用されるXML変換APIを用いて変換を行います。
     * 
     * @param metaXmlSourceFile
     *            入力XMLファイル
     * @return 出力DOMツリー
     * @throws IllegalArgumentException
     *             入力ファイルが不正である場合。XML変換取得に失敗した場合など。
     */
    public static final DOMResult transformFile2Dom(final File metaXmlSourceFile) {
        if (metaXmlSourceFile == null) {
            throw new IllegalArgumentException(
                    "ファイルを入力としてDOMツリーを生成するメソッドにnullが与えられました。null以外の値を与えるようにしてください。");
        }

        if (metaXmlSourceFile.exists() == false) {
            throw new IllegalArgumentException("XMLファイルからDOMへの変換: 指定されたファイル["
                    + metaXmlSourceFile.getAbsolutePath() + "]が見つかりませんでした。");
        }
        if (metaXmlSourceFile.isFile() == false) {
            throw new IllegalArgumentException("XMLファイルからDOMへの変換: 指定されたファイル["
                    + metaXmlSourceFile.getAbsolutePath()
                    + "]が実際にはファイルではありませんでした。");
        }
        if (metaXmlSourceFile.canRead() == false) {
            throw new IllegalArgumentException("XMLファイルからDOMへの変換: 指定されたファイル["
                    + metaXmlSourceFile.getAbsolutePath() + "]が読み込み不能でした。");
        }

        try {
            byte[] fileBytes = BlancoFileUtil.file2Bytes(metaXmlSourceFile);
            return transformStream2Dom(new ByteArrayInputStream(fileBytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "想定しない例外: XML変換時にファイルが見つからない例外が発生しました。" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "想定しない例外: XML変換時にファイル入出力例外が発生しました。" + e.toString());
        }
    }

    /**
     * 与えられたXMLストリームをDOMツリーに変換します。
     * 
     * 内部的には、一般的に利用されるXML変換APIを用いて変換を行います。
     * 
     * @param inXmlSource
     *            入力XMLストリーム
     * @return 出力DOMツリー
     * @throws IllegalArgumentException
     *             入力ファイルが不正である場合。XML変換取得に失敗した場合など。
     */
    public static final DOMResult transformStream2Dom(
            final InputStream inXmlSource) {
        if (inXmlSource == null) {
            throw new IllegalArgumentException(
                    "入力XMLストリームをDOMツリーに変換する処理に、ストリームとしてnullが渡されました。ストリームにはnull以外を与えてください。");
        }

        try {
            final DOMResult result = new DOMResult();
            final TransformerFactory tf = TransformerFactory.newInstance();
            final Transformer transformer = tf.newTransformer();
            transformer.transform(new StreamSource(inXmlSource), result);
            return result;
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "想定しない例外: XML変換コンフィグレーション例外が発生しました。" + e.toString());
        } catch (TransformerException e) {
            throw new IllegalArgumentException("想定しない例外: XML変換例外が発生しました。"
                    + e.toString());
        }
    }

    /**
     * 与えられたDOMツリーをXMLファイルに変換します。
     * 
     * 内部的には、一般的に利用されるXML変換APIを用いて変換を行います。
     * 
     * @param document
     *            XMLドキュメント
     * @param metaXmlResultFile
     *            出力XMLファイル
     * @throws IllegalArgumentException
     *             入力XMLツリーが不正である場合。XML変換取得に失敗した場合など。
     */
    public static final void transformDom2File(final Document document,
            final File metaXmlResultFile) {
        if (document == null) {
            throw new IllegalArgumentException(
                    "DOMからXMLファイルへの変換: 入力XMLドキュメントにnullが与えられましたが、このパラメータにはnull以外の値を指定する必要があります。");
        }
        if (metaXmlResultFile == null) {
            throw new IllegalArgumentException(
                    "DOMからXMLファイルへの変換: 出力XMLファイルにnullが与えられましたが、このパラメータにはnull以外の値を指定する必要があります。");
        }

        if (metaXmlResultFile.exists() == true
                && metaXmlResultFile.canWrite() == false) {
            throw new IllegalArgumentException("DOMからXMLファイルへの変換: 指定されたファイル["
                    + metaXmlResultFile.getAbsolutePath() + "]は書き込みできませんでした。");
        }

        try {
            final OutputStream outStream = new BufferedOutputStream(
                    new FileOutputStream(metaXmlResultFile));
            try {
                transformDom2Stream(document, outStream);
                // 正常終了した場合にはフラッシュを行います。
                outStream.flush();
            } finally {
                outStream.close();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("DOMからXMLファイルへの変換: 指定されたファイル["
                    + metaXmlResultFile.getAbsolutePath()
                    + "]への変換の過程で入出力例外が発生しました。" + e.toString());
        }
    }

    /**
     * 与えられたDOMツリーをXMLストリームに変換します。
     * 
     * 内部的には、一般的に利用されるXML変換APIを用いて変換を行います。
     * 
     * @param document
     *            入力XMLツリー
     * @param outXmlResult
     *            出力DOMストリーム
     * @throws IllegalArgumentException
     *             パラメータが不正である場合。XML変換取得に失敗した場合など。
     */
    public static final void transformDom2Stream(final Document document,
            final OutputStream outXmlResult) {
        if (document == null) {
            throw new IllegalArgumentException(
                    "DOMツリーを出力XMLストリームに変換する処理に、XMLドキュメントとしてnullが渡されました。XMLドキュメントにはnull以外を与えてください。");
        }
        if (outXmlResult == null) {
            throw new IllegalArgumentException(
                    "DOMツリーを出力XMLストリームに変換する処理に、ストリームとしてnullが渡されました。ストリームにはnull以外を与えてください。");
        }

        try {
            final DOMSource source = new DOMSource(document);
            final TransformerFactory tf = TransformerFactory.newInstance();
            final Transformer transformer = tf.newTransformer();
            transformer.transform(source, new StreamResult(outXmlResult));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "想定しない例外: XML変換コンフィグレーション例外が発生しました。" + e.toString());
        } catch (TransformerException e) {
            throw new IllegalArgumentException("想定しない例外: XML変換例外が発生しました。"
                    + e.toString());
        }
    }

    /**
     * XMLドキュメントを新規作成します。
     * 
     * ドキュメントファクトリからドキュメントビルダーを取得し、それからXMLドキュメントを新規作成します。
     * 
     * @return 新規に作成されたXMLドキュメント・オブジェクト
     * @throws IllegalArgumentException
     *             ドキュメントビルダーの新規作成に失敗した場合。
     */
    public static final Document newDocument() {
        final DocumentBuilderFactory builderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException(
                    "ドキュメントビルダー(DocumentBuilder)の新規生成に失敗しました。" + e.toString());
        }
        return builder.newDocument();
    }

    /**
     * 指定のエレメントに指定のエレメント名による子エレメントを追加します。
     * 
     * テキストの指定があればテキストノードを追加します。テキストにnullを指定した場合には子エレメントの追加のみが行われます。
     * 
     * @param document
     *            XMLドキュメント
     * @param elementParent
     *            親エレメント
     * @param addElementName
     *            新エレメントの名前
     * @param addElementText
     *            新エレメントに追加するテキスト。nullを指定した場合にはテキスト追加は省略される。
     */
    public static final void addChildElement(final Document document,
            final Element elementParent, final String addElementName,
            final String addElementText) {
        if (document == null) {
            throw new IllegalArgumentException(
                    "エレメント追加 (addElementText) に、ドキュメントとしてnullが渡されました。null以外の値を指定してください。");
        }
        if (elementParent == null) {
            throw new IllegalArgumentException(
                    "エレメント追加 (addElementText) に、親エレメントとしてnullが渡されました。null以外の値を指定してください。");
        }
        if (addElementName == null) {
            throw new IllegalArgumentException(
                    "エレメント追加 (addElementText) に、新エレメントの名前としてnullが渡されました。null以外の値を指定してください。");
        }
        if (addElementName.length() == 0) {
            throw new IllegalArgumentException(
                    "エレメント追加 (addElementText) に、新エレメントの名前として長さ0の文字列が渡されました。1以上の長さの値を指定してください。");
        }

        // 最初に追加したいエレメントを生成します。
        final Element elementAdd = document.createElement(addElementName);
        if (addElementText != null) {
            // テキストの指定がある場合にのみ追加処理を行います。
            // テキストノードを生成します。
            final Text elemnetTextNode = document
                    .createTextNode(addElementText);
            // テキストノードをエレメントに追加します。
            elementAdd.appendChild(elemnetTextNode);
        }
        // 最後に親エレメントに新エレメントを追加します。
        elementParent.appendChild(elementAdd);
    }

    /**
     * 指定のエレメント名のエレメントを検索します。
     * 
     * @param path
     *            パス aaa/bbb のように指定します。
     * @return 取得されたエレメント
     */
    public static final Element getElement(final Node nodeTarget,
            final String path) {
        if (nodeTarget == null) {
            throw new IllegalArgumentException(
                    "指定のエレメント名からエレメントを検索するメソッドのノードにnullが与えられました。null以外の値を与えるようにしてください。");
        }
        if (nodeTarget == null) {
            throw new IllegalArgumentException(
                    "指定のエレメント名からエレメントを検索するメソッドのパス名にnullが与えられました。null以外の値を与えるようにしてください。");
        }

        final String[] splitPath = BlancoNameUtil.splitPath(path);

        final NodeList nodeList = nodeTarget.getChildNodes();
        final int nodeLength = nodeList.getLength();
        for (int index = 0; index < nodeLength; index++) {
            final Node nodeLook = nodeList.item(index);
            if (nodeLook instanceof Element) {
                final Element elementLook = (Element) nodeLook;
                if (elementLook.getNodeName().equals(splitPath[0])) {
                    // ヒットしました。
                    if (splitPath.length == 1) {
                        // System.out.println("発見しました。" +
                        // elementLook.getNodeName());
                        return elementLook;
                    } else {
                        // System.out.println("再帰を行います。" +
                        // elementLook.getNodeName());
                        return getElement(elementLook, path
                                .substring(splitPath[0].length() + 1));
                    }
                }
            }
        }
        // 発見できませんでした。
        return null;
    }
}
