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
package blanco.commons.calc;

import junit.framework.TestCase;

/**
 * Calcユーティリティを単体試験します。
 * 
 * @author iga
 */
public class BlancoCalcUtilTest extends TestCase {

    public void testColumnToLabel() throws Exception {
        assertEquals("A", BlancoCalcUtil.columnToLabel(1));
        assertEquals("Z", BlancoCalcUtil.columnToLabel(26));
        assertEquals("AA", BlancoCalcUtil.columnToLabel(27));
        assertEquals("AB", BlancoCalcUtil.columnToLabel(28));
        assertEquals("AC", BlancoCalcUtil.columnToLabel(29));
        assertEquals("AZ", BlancoCalcUtil.columnToLabel(52));
        assertEquals("BA", BlancoCalcUtil.columnToLabel(53));
        assertEquals("BB", BlancoCalcUtil.columnToLabel(54));
        assertEquals("BZ", BlancoCalcUtil.columnToLabel(78));
        assertEquals("CA", BlancoCalcUtil.columnToLabel(79));
        assertEquals("YZ", BlancoCalcUtil.columnToLabel(676));
        assertEquals("ZA", BlancoCalcUtil.columnToLabel(677));
        assertEquals("ZZ", BlancoCalcUtil.columnToLabel(702));
        assertEquals("AAA", BlancoCalcUtil.columnToLabel(703));
        assertEquals("AAB", BlancoCalcUtil.columnToLabel(704));
        assertEquals("AAZ", BlancoCalcUtil.columnToLabel(728));
        assertEquals("ABA", BlancoCalcUtil.columnToLabel(729));
    }
}
