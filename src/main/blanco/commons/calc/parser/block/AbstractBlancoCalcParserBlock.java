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

/**
 * 抽象的なブロックを表現します。
 * 
 * @author IGA Tosiki
 */
public abstract class AbstractBlancoCalcParserBlock {

    /**
     * Y方向の我慢する回数 (除くタイトル行のカウント処理)
     */
    private int waitForValueY = 1;

    /**
     * ブロックの開始文字列群
     */
    private String[] startString = null;

    /**
     * ブロックの終了文字列群
     */
    private String[] endString = null;

    /**
     * ブロックに与えられた名前(ID)
     */
    private String blockName = "name";

    /**
     * 値マッピング情報
     */
    private BlancoCalcParserValueMapping[] valueMapping = null;

    /**
     * ブロックの名前を取得します。
     * 
     * @return ブロック名。
     */
    public String getName() {
        return blockName;
    }

    /**
     * ブロックの名前を設定します。
     * 
     * @param arg
     */
    public void setName(String arg) {
        blockName = arg;
    }

    /**
     * 開始文字列群をセットします
     * 
     * @param arg
     *            開始文字列の配列。
     */
    public void setStartString(String[] arg) {
        startString = arg;
    }

    /**
     * 開始文字列にヒットするかどうか調査します。
     * 
     * @param arg
     *            チェックを行いたい文字列。
     * @return 開始文字列にヒットしたかどうか。
     */
    public boolean isStartString(String arg) {
        if (startString == null) {
            return false;
        }

        final int startStringLength = startString.length;
        for (int index = 0; index < startStringLength; index++) {
            if (startString[index].equals(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 終了文字列群をセットします
     * 
     * @deprecated 終了文字列のチェックは推奨されません。
     * @param arg
     *            終了文字列の配列。
     */
    public void setEndString(String[] arg) {
        endString = arg;
    }

    /**
     * 終了文字列にヒットするかどうか調査します。
     * 
     * @deprecated 終了文字列のチェックは推奨されません。
     * @param arg
     *            チェックを行いたい文字列。
     * @return ヒットしたかどうか。
     */
    public boolean isEndString(String arg) {
        if (endString == null) {
            return false;
        }

        final int endStringLength = endString.length;
        for (int index = 0; index < endStringLength; index++) {
            if (endString[index].equals(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ｙ方向の検索範囲を指定します。
     * 
     * @param arg
     *            Ｙ方向の検索範囲。
     */
    public void setSearchRangeY(int arg) {
        waitForValueY = arg;
    }

    /**
     * Ｙ方向の検索範囲を取得します。
     * 
     * @return Ｙ方向の検索範囲。
     */
    public int getSearchRangeY() {
        return waitForValueY;
    }

    /**
     * 値の読み替えマッピングを指定します。
     * 
     * @param mapping
     *            値の読み替えマッピング。
     */
    public void setValueMapping(BlancoCalcParserValueMapping[] mapping) {
        valueMapping = mapping;
    }

    /**
     * 値の読み替えマッピングを取得します。
     * 
     * @return 値の読み替えマッピング。
     */
    public BlancoCalcParserValueMapping[] getValueMapping() {
        return valueMapping;
    }
}
