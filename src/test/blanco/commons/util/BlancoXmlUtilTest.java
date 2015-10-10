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

import java.io.File;

import javax.xml.transform.dom.DOMResult;

import junit.framework.TestCase;

import org.w3c.dom.Element;

public class BlancoXmlUtilTest extends TestCase {

    /*
     * 'blanco.commons.util.BlancoXmlUtil.getElement(Node, String)' のためのテスト・メソッド
     */
    public void testGetElement() {
        final DOMResult result = BlancoXmlUtil.transformFile2Dom(new File(
                "./meta/BlancoCalcParserDef.xml"));
        @SuppressWarnings("unused")
        final Element element = BlancoXmlUtil.getElement(result.getNode(),
                "blanco/target/blancocalcparser/propertyblock/startstring");
    }

}
