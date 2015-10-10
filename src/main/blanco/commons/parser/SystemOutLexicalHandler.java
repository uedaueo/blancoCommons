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

import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

/**
 * イベントをSystem.outへとリダイレクトします。
 * 
 * @author IGA Tosiki
 */
public class SystemOutLexicalHandler implements LexicalHandler {
    private LexicalHandler lexicalHandler = null;

    private static final String PREFIX = "LexicalHandler.";

    public SystemOutLexicalHandler() {
    }

    public SystemOutLexicalHandler(LexicalHandler arg0) {
        setLexicalHandler(arg0);
    }

    /**
     * ハンドラをセットします。
     */
    public final void setLexicalHandler(LexicalHandler arg0) {
        lexicalHandler = arg0;
    }

    /**
     * ハンドラを取得します。
     */
    public final LexicalHandler getLexicalHandler() {
        return lexicalHandler;
    }

    public void startDTD(String arg0, String arg1, String arg2)
            throws SAXException {
        System.out.println(PREFIX + "startDTD(" + quoteString(arg0) + ", "
                + quoteString(arg1) + ", " + quoteString(arg2) + ")");
        if (getLexicalHandler() != null) {
            getLexicalHandler().startDTD(arg0, arg1, arg2);
        }
    }

    public void endDTD() throws SAXException {
        System.out.println(PREFIX + "endDTD()");
        if (getLexicalHandler() != null) {
            getLexicalHandler().endDTD();
        }
    }

    public void startEntity(String arg0) throws SAXException {
        System.out.println(PREFIX + "startEntity(" + quoteString(arg0) + ")");
        if (getLexicalHandler() != null) {
            getLexicalHandler().startEntity(arg0);
        }
    }

    public void endEntity(String arg0) throws SAXException {
        System.out.println(PREFIX + "endEntity(" + quoteString(arg0) + ")");
        if (getLexicalHandler() != null) {
            getLexicalHandler().endEntity(arg0);
        }
    }

    public void startCDATA() throws SAXException {
        System.out.println(PREFIX + "startCDATA()");
        if (getLexicalHandler() != null) {
            getLexicalHandler().startCDATA();
        }
    }

    public void endCDATA() throws SAXException {
        System.out.println(PREFIX + "endCDATA()");
        if (getLexicalHandler() != null) {
            getLexicalHandler().endCDATA();
        }
    }

    public void comment(char[] arg0, int arg1, int arg2) throws SAXException {
        System.out.println(PREFIX + "comment("
                + quoteString(new String(arg0, arg1, arg2)) + ", " + arg1
                + ", " + arg2 + ")");
        if (getLexicalHandler() != null) {
            getLexicalHandler().comment(arg0, arg1, arg2);
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

}
