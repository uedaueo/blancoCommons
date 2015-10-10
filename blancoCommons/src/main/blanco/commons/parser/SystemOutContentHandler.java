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
package blanco.commons.parser;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * 単に標準出力にリダイレクトするだけのSAX2パーサです。 <br>
 * コンテンツハンドラを別途セットすることにより、SAXチェインをのぞき見することができます。
 * 
 * @author IGA Tosiki
 */
public class SystemOutContentHandler extends ContentHandlerStream {

    private static final String PREFIX = "ContentHandler.";

    public SystemOutContentHandler() {
    }

    public SystemOutContentHandler(ContentHandlerStream arg0) {
        chainContentHandler(arg0);
    }

    public void setDocumentLocator(Locator arg0) {
        System.out.println(PREFIX + "setDocumentLocator(publicId:"
                + quoteString(arg0.getPublicId()) + ", systemId:"
                + quoteString(arg0.getSystemId()) + ")");
        if (getContentHandler() != null) {
            getContentHandler().setDocumentLocator(arg0);
        }
    }

    public void startDocument() throws SAXException {
        System.out.println(PREFIX + "startDocument()");
        if (getContentHandler() != null) {
            getContentHandler().startDocument();
        }
    }

    public void endDocument() throws SAXException {
        System.out.println(PREFIX + "endDocument()");
        if (getContentHandler() != null) {
            getContentHandler().endDocument();
        }
    }

    public void startPrefixMapping(String arg0, String arg1)
            throws SAXException {
        System.out.println(PREFIX + "startPrefixMapping(" + quoteString(arg0)
                + ", " + quoteString(arg1) + ")");
        if (getContentHandler() != null) {
            getContentHandler().startPrefixMapping(arg0, arg1);
        }
    }

    public void endPrefixMapping(String arg0) throws SAXException {
        System.out.println(PREFIX + "endPrefixMapping(" + quoteString(arg0)
                + ")");
        if (getContentHandler() != null) {
            getContentHandler().endPrefixMapping(arg0);
        }
    }

    public void startElement(String arg0, String arg1, String arg2,
            Attributes arg3) throws SAXException {
        System.out.println(PREFIX + "startElement(" + quoteString(arg0) + ", "
                + quoteString(arg1) + ", " + quoteString(arg2) + ", "
                + getAttributeString(arg3) + ")");
        if (getContentHandler() != null) {
            getContentHandler().startElement(arg0, arg1, arg2, arg3);
        }
    }

    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {
        System.out.println(PREFIX + "endElement(" + quoteString(arg0) + ", "
                + quoteString(arg1) + ", " + quoteString(arg2) + ")");
        if (getContentHandler() != null) {
            getContentHandler().endElement(arg0, arg1, arg2);
        }
    }

    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        System.out.println(PREFIX + "characters("
                + quoteString(new String(arg0, arg1, arg2)) + ", " + arg1
                + ", " + arg2 + ")");
        if (getContentHandler() != null) {
            getContentHandler().characters(arg0, arg1, arg2);
        }
    }

    public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
            throws SAXException {
        System.out.println(PREFIX + "ignorableWhitespace("
                + quoteString(new String(arg0, arg1, arg2)) + ", " + arg1
                + ", " + arg2 + ")");
        if (getContentHandler() != null) {
            getContentHandler().ignorableWhitespace(arg0, arg1, arg2);
        }
    }

    public void processingInstruction(String arg0, String arg1)
            throws SAXException {
        System.out.println(PREFIX + "processingInstruction("
                + quoteString(arg0) + ", " + quoteString(arg1) + ")");
        if (getContentHandler() != null) {
            getContentHandler().processingInstruction(arg0, arg1);
        }
    }

    public void skippedEntity(String arg0) throws SAXException {
        System.out.println(PREFIX + "skippedEntity(" + quoteString(arg0) + ")");
        if (getContentHandler() != null) {
            getContentHandler().skippedEntity(arg0);
        }
    }

    /**
     * 文字列をクオート <br>
     * TODO:さすがに共通ルーチン化が必要かもです
     * 
     * @param arg
     * @return
     */
    private static final String quoteString(String arg) {
        if (arg == null) {
            return "null";
        }
        arg = arg.replaceAll("\n", "\\\\n");
        return "\"" + arg + "\"";
    }

    private static final String getAttributeString(Attributes attr) {
        if (attr == null) {
            return "null";
        } else if (attr.getLength() == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer(256);
        result.append("attr(");
        for (int index = 0; index < attr.getLength(); index++) {
            if (index != 0) {
                result.append(", ");
            }

            result.append(attr.getLocalName(index));
            result.append("=");
            result.append(quoteString(attr.getValue(index)));

            if (false) {
                result.append("(");
                result.append(attr.getURI(index));
                result.append(", ");
                result.append(attr.getLocalName(index));
                result.append(", ");
                result.append(attr.getQName(index));
                result.append(", ");
                result.append(attr.getType(index));
                result.append(", ");
                result.append(attr.getValue(index));
                result.append(")");
            }
        }
        result.append(")");
        return result.toString();
    }
}
