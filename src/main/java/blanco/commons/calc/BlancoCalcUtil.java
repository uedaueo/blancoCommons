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

/**
 * 表計算としてカラム番号からラベル文字列を取得します。
 * 
 * @author IGA Tosiki
 */
public final class BlancoCalcUtil {
    private BlancoCalcUtil() {
        // コンストラクタは無効化されています。
    }

    /**
     * 与えられたカラム番号をカラムのラベル文字列に変換します。
     * 
     * @param column
     *            カラムを表す数値。１オリジンで指定します。
     * @return 例：26=Z, 27=AA
     */
    public static final String columnToLabel(int column) {
        String output = "";
        for (;;) {
            if (column > 26) {
                output = columnToLabel((column - 1) % 26 + 1) + output;
                column = (column - 1) / 26;
            } else {
                return Character.toString((char) ('A' + column - 1)) + output;
            }
        }
    }
}
