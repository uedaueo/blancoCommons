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

/**
 * blanco Frameworkにおける、文字列に関するユーティリティの内部処理が含まれます。
 * 
 * このクラスはパッケージ外非公開と設定しています。
 * 
 * @author IGA Tosiki
 */
class BlancoStringUtilTrim {
    /**
     * 与えられた文字列について、左側に半角空白があれば これを除去します。
     * 
     * 半角空白のみ除去します。全角空白は処理しません。
     * 
     * @param originalString
     *            処理を行いたい文字列。
     * @return 半角空白が切り詰められた後の文字列。
     */
    public static final String trimLeft(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "文字列切り詰め(左)メソッドに、変換元文字列としてnullが与えられました。null以外の値を与えてください。");
        }
        int lastSpace = -1;
        for (int index = 0; index < originalString.length(); index++) {
            if (originalString.charAt(index) != ' ') {
                break;
            }
            lastSpace = index;
        }
        if (lastSpace < 0) {
            return originalString;
        }
        return originalString.substring(lastSpace + 1);
    }

    /**
     * 与えられた文字列について、右側に半角空白があれば これを除去します。
     * 
     * 半角空白のみ除去します。全角空白は処理しません。
     * 
     * @param originalString
     *            処理を行いたい文字列。
     * @return 半角空白が切り詰められた後の文字列。
     */
    public static final String trimRight(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "文字列切り詰め(右)メソッドに、変換元文字列としてnullが与えられました。null以外の値を与えてください。");
        }
        int lastSpace = -1;
        for (int index = originalString.length() - 1; index >= 0; index--) {
            if (originalString.charAt(index) != ' ') {
                break;
            }
            lastSpace = index;
        }
        if (lastSpace < 0) {
            return originalString;
        }
        return originalString.substring(0, lastSpace);
    }

    /**
     * 与えられた文字列について、右側および左側に半角空白があれば これを除去します。
     * 
     * 半角空白のみ除去します。全角空白は処理しません。
     * 
     * @param originalString
     *            処理を行いたい文字列。
     * @return 半角空白が切り詰められた後の文字列。
     */
    public static final String trim(final String originalString) {
        if (originalString == null) {
            throw new IllegalArgumentException(
                    "文字列切り詰め(左右)メソッドに、変換元文字列としてnullが与えられました。null以外の値を与えてください。");
        }
        return trimRight(trimLeft(originalString));
    }
}
