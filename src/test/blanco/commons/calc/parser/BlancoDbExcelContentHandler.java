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

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import blanco.commons.parser.ContentHandlerStream;

/**
 * コンテンツハンドラ・ストリームの単体試験に利用.
 * 
 * @author iga
 */
public class BlancoDbExcelContentHandler extends ContentHandlerStream {

    public BlancoDbExcelContentHandler() {
    }

    public BlancoDbExcelContentHandler(ContentHandlerStream arg) {
        chainContentHandler(arg);
    }

    public void setDocumentLocator(Locator arg0) {
        getContentHandler().setDocumentLocator(arg0);
    }

    public void startDocument() throws SAXException {
        getContentHandler().startDocument();
    }

    public void endDocument() throws SAXException {
        getContentHandler().endDocument();
    }

    public void startPrefixMapping(String arg0, String arg1)
            throws SAXException {
        getContentHandler().startPrefixMapping(arg0, arg1);
    }

    public void endPrefixMapping(String arg0) throws SAXException {
        getContentHandler().endPrefixMapping(arg0);
    }

    public void processingInstruction(String arg0, String arg1)
            throws SAXException {
        getContentHandler().processingInstruction(arg0, arg1);
    }

    public void skippedEntity(String arg0) throws SAXException {
        getContentHandler().skippedEntity(arg0);
    }

    // ここから拡張したいポイント
    private boolean isSheetValid = false;

    private boolean isInParameters = false;

    private String currentElement = "";

    private String currentValue = "";

    private String name = "";

    @SuppressWarnings("unused")
    private String gamenId = "";

    private String queryType = "";

    private String scroll = "forward_only";

    private boolean isUpdatable = false;

    private boolean isSingle = false;

    private String query = "";

    private String parameterName = "";

    private String parameterType = "";

    public void startElement(String arg0, String arg1, String arg2,
            Attributes arg3) throws SAXException {
        currentElement = arg1;

        if (arg1.equals("blanco-db")) {
            getContentHandler().startElement(arg0, arg1, arg2, arg3);
        } else if (arg1.equals("sheet")) {
        } else if (arg1.equals("attribute")) {
        } else if (arg1.equals("query-type")) {
        } else if (arg1.equals("gamen-id")) {
        } else if (arg1.equals("single")) {
        } else if (arg1.equals("scroll")) {
        } else if (arg1.equals("updatable")) {
        } else if (arg1.equals("name")) {
        } else if (arg1.equals("type")) {
        } else if (arg1.equals("query-line")) {
        } else if (arg1.equals("parameters")) {
            isInParameters = true;
            if (isSheetValid)
                getContentHandler().startElement(arg0, arg1, arg2, arg3);
        } else if (arg1.equals("parameter")) {
            parameterName = "";
            parameterType = "";
        } else {
            if (isSheetValid)
                getContentHandler().startElement(arg0, arg1, arg2, arg3);
        }
    }

    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {
        if (arg1.equals("blanco-db")) {
            getContentHandler().endElement(arg0, arg1, arg2);
        } else if (isInParameters == false) {
            if (arg1.equals("attribute")) {
                AttributesImpl attr = new AttributesImpl();
                attr.addAttribute("", "name", "name", "CDATA", name);
                if (isSingle) {
                    attr.addAttribute("", "single", "single", "CDATA", "true");
                }
                if (queryType.equals("iterator")) {
                    if (scroll.equals("false")) {
                        scroll = "forward_only";
                    } else if (scroll.equals("true")) {
                        scroll = "insensitive";
                    }
                    attr.addAttribute("", "scroll", "scroll", "CDATA", scroll);
                    attr.addAttribute("", "updatable", "updatable", "CDATA",
                            Boolean.toString(isUpdatable));
                }

                if ((queryType.equals("iterator") || queryType
                        .equals("invoker"))
                        && name.length() > 0) {
                    isSheetValid = true;
                    if (queryType.equals("iterator")) {
                        getContentHandler().startElement("", "query-iterator",
                                "query-iterator", attr);
                    } else if (queryType.equals("invoker")) {
                        getContentHandler().startElement("", "query-invoker",
                                "query-invoker", attr);
                    }
                }
            } else if (arg1.equals("sheet")) {
                // System.out.println("query-line:" + query);
                if (isSheetValid) {
                    if (queryType.equals("iterator")) {
                        getContentHandler().endElement("", "query-iterator",
                                "query-iterator");
                    } else if (queryType.equals("invoker")) {
                        getContentHandler().endElement("", "query-invoker",
                                "query-invoker");
                    }
                }

                // リセット
                isSheetValid = false;
                name = "";
                gamenId = "";
                currentValue = "";
                queryType = "";
                query = "";
            } else if (arg1.equals("name")) {
                name = currentValue;
                currentValue = "";
            } else if (arg1.equals("gamen-id")) {
                gamenId = currentValue;
                currentValue = "";
            } else if (arg1.equals("query-type")) {
                queryType = currentValue;
                currentValue = "";
            } else if (arg1.equals("single")) {
                isSingle = currentValue.equals("true");
                currentValue = "";
            } else if (arg1.equals("scroll")) {
                scroll = currentValue;
                currentValue = "";
            } else if (arg1.equals("updatable")) {
                isUpdatable = currentValue.equals("true");
                currentValue = "";
            } else if (arg1.equals("query-line")) {
                if (query.length() > 0) {
                    query += "\n";
                }
                query += currentValue;
                currentValue = "";
            } else if (arg1.equals("query")) {
                // TODO これは最終的には除去します。
                // 無理やり最後に改行を付与しています。
                query += "\n";

                char[] charArray = query.toCharArray();
                if (isSheetValid)
                    getContentHandler().characters(charArray, 0,
                            charArray.length);
                currentValue = "";
                if (isSheetValid)
                    getContentHandler().endElement(arg0, arg1, arg2);
            } else {
                System.out.println("エレメント" + currentElement + "来てます.");
                if (isSheetValid)
                    getContentHandler().endElement(arg0, arg1, arg2);
            }
        } else {
            if (arg1.equals("parameter")) {
                AttributesImpl attr = new AttributesImpl();
                attr.addAttribute("", "name", "name", "CDATA", parameterName);
                attr.addAttribute("", "type", "type", "CDATA", parameterType);
                if (isSheetValid)
                    getContentHandler().startElement("", "parameter",
                            "parameter", attr);
                if (isSheetValid)
                    getContentHandler()
                            .endElement("", "parameter", "parameter");
                currentValue = "";
            } else if (arg1.equals("name")) {
                parameterName = currentValue;
                currentValue = "";
            } else if (arg1.equals("type")) {
                parameterType = currentValue;
                currentValue = "";
            } else if (arg1.equals("parameters")) {
                // クローズします
                isInParameters = false;
                if (isSheetValid)
                    getContentHandler().endElement(arg0, arg1, arg2);
            } else {
                if (isSheetValid)
                    getContentHandler().endElement(arg0, arg1, arg2);
            }
        }
    }

    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        String value = new String(arg0, arg1, arg2);
        currentValue += value;
    }

    public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
            throws SAXException {
    }
}
