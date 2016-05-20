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
package blanco.commons.calc.parser.block;

import blanco.commons.calc.parser.constants.BlancoCommonsConstantsConstants;

/**
 * プロパティキーを表現します。
 * 
 * @author j-amano
 */
public class BlancoCalcWriterPropertyKey {

    private String keyName = "name";

    private String[] startString = null;

    private String[] propertyData = null;

    /**
     * デフォルトで1列のみ読み込みます。
     */
    private int waitForValueX = 1;

    /**
     * プロパティキーオブジェクトのコンストラクタ。
     *
     * @param name
     *            プロパティキーの名前。
     */
    public BlancoCalcWriterPropertyKey(final String name) {
        this.keyName = name;
    }

    /**
     * プロパティキーオブジェクトのコンストラクタ。
     *
     * @param name
     *            プロパティキーの名前。
     * @param startString
     *            開始文字列の配列。
     */
    public BlancoCalcWriterPropertyKey(final String name,
                                       final String[] startString
                                       ) {
        this.keyName = name;
        this.startString = startString;
    }

    /**
     * プロパティキーオブジェクトのコンストラクタ。
     *
     * @param name
     *            プロパティキーの名前。
     * @param startString
     *            開始文字列の配列。
     * @param propertyData
     *            挿入する文字列の配列。
     */
    public BlancoCalcWriterPropertyKey(final String name,
                                        final String[] startString,
                                        final String[] propertyData) {
        this.keyName = name;
        this.startString = startString;
        this.propertyData = propertyData;
    }
    /**
     * キーの名前を取得します。
     *
     * @return キーの名前。
     */
    public String getName() {
        return keyName;
    }

    /**
     * キーの名前を設定します。
     *
     * @param arg
     *            キーの名前。
     */
    public void setName(String arg) {
        keyName = arg;
    }

    /**
     * 開始文字列群をセットします。
     *
     * @param arg
     *            開始文字列の配列。
     */
    public void setKeyString(String[] arg) {
        startString = arg;
    }

    /**
     * 埋め込み文字列群をセットします。(追加)
     *
     * @param arg
     *            埋め込み文字列の配列。
     */
    public void setPropertyData(String[] arg) {
        propertyData = arg;
    }

    /**
     * 埋め込み文字列群を取得します。(追加)
     *
     */
    public String[] getPropertyData() {
        return propertyData;
    }

    /**
     * 埋め込み文字列群を取得します。(追加)
     *
     */
    public String getConcatenatedPropertyData() {

        String strTmpData = "";
        for(int i = 0; i<propertyData.length; i++){
            if(i != 0){
                strTmpData += BlancoCommonsConstantsConstants.STR_DELIMITER;
            }
            strTmpData += propertyData[i];
        }
        return strTmpData;
    }

    /**
     * 開始文字列にヒットするかどうか調査します。
     *
     * @param arg
     *            チェックを行いたい文字列。
     * @return ヒットしたかどうか。
     */
    public boolean isStartString(String arg) {
        final int startStringLength = startString.length;
        for (int index = 0; index < startStringLength; index++) {
            if (startString[index].equals(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ｘ方向の検索範囲を設定します。
     *
     * @param arg
     *            Ｘ方向の検索範囲。
     */
    public void setSearchRangeX(int arg) {
        waitForValueX = arg;
    }

    /**
     * Ｘ方向の検索範囲を取得します。
     *
     * @return Ｘ方向の検索範囲。
     */
    public int getSearchRangeX() {
        return waitForValueX;
    }
}
