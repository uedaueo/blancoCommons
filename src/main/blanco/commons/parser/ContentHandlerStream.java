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

import org.xml.sax.ContentHandler;

/**
 * コンテンツハンドラによるストリームを提供します。
 * 
 * @author IGA Tosiki
 */
public abstract class ContentHandlerStream implements ContentHandler {
    private ContentHandler contentHandler = null;

    /**
     * 内部的に記憶する必要のあるコンテンツハンドラを記憶します。
     */
    private ContentHandlerStream chainedContentHandler = null;

    public ContentHandlerStream() {
    }

    /**
     * コンテンツハンドラ・チェインをつなげ併せます。
     * 
     * @param arg0
     */
    public ContentHandlerStream(ContentHandlerStream arg0) {
        chainContentHandler(arg0);
    }

    /**
     * コンテンツハンドラをセットします。
     */
    public void setContentHandler(ContentHandler arg0) {
        contentHandler = arg0;
    }

    /**
     * コンテンツハンドラをストリームチェインに設定します。
     */
    protected void chainContentHandler(ContentHandlerStream arg0) {
        chainedContentHandler = arg0;
    }

    /**
     * コンテンツハンドラを取得します。
     */
    public ContentHandler getContentHandler() {
        if (chainedContentHandler == null) {
            return contentHandler;
        } else {
            chainedContentHandler.setContentHandler(contentHandler);
            return chainedContentHandler;
        }
    }
}
