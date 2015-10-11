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
 * テーブル列を表現します。
 * 
 * @author IGA Tosiki
 */
public class BlancoCalcParserTableColumn {

    private String _columnName = "name";

    private String[] _columnString = null;

    private int columnPosition = -1;

    /**
     * テーブルのカラムオブジェクトのコンストラクタ。
     * 
     * @param name
     *            カラム名。
     */
    public BlancoCalcParserTableColumn(final String name) {
        this._columnName = name;
    }

    /**
     * テーブルの絡むオブジェクトのコンストラクタ。
     * 
     * @param name
     *            カラム名。
     * @param columnString
     *            カラム文字列の配列。
     */
    public BlancoCalcParserTableColumn(final String name,
            final String[] columnString) {
        this._columnName = name;
        this._columnString = columnString;
    }

    /**
     * 列の名前を取得します。
     * 
     * @return 列の名前。
     */
    public String getName() {
        return _columnName;
    }

    /**
     * 列の名前を設定します。
     * 
     * @param arg
     *            列の名前。
     */
    public void setName(final String arg) {
        _columnName = arg;
    }

    /**
     * カラム文字列を記憶します。
     * 
     * @param arg
     *            カラム文字列の配列。
     */
    public void setColumnString(final String[] arg) {
        _columnString = arg;
    }

    /**
     * 開始文字列にヒットするかどうか調査します。
     * 
     * @param arg
     *            開始文字列。
     * @return ヒットしたかどうか。
     */
    public boolean isStartString(String arg) {
        final int columnStringLength = _columnString.length;
        for (int index = 0; index < columnStringLength; index++) {
            if (_columnString[index].equals(arg)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 列のカラム番号を取得します。
     * 
     * @return 列のカラム番号。
     */
    public int getColumnPosition() {
        return columnPosition;
    }

    /**
     * 列のカラム番号を記憶します。
     * 
     * @param arg
     *            列のカラム番号。
     */
    public void setColumnPosition(int arg) {
        columnPosition = arg;
    }
}
