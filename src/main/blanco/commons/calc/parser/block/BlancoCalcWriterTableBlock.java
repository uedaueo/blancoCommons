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

import java.util.ArrayList;
import java.util.List;

/**
 * 繰り返し型のブロックを実装します。
 * 
 * @author IGA Tosiki
 */
public class BlancoCalcWriterTableBlock extends AbstractBlancoCalcParserBlock {
    private List<BlancoCalcWriterTableColumn> list = new ArrayList<BlancoCalcWriterTableColumn>();

    private String blockRowName = "";

    /**
     * タイトルをサーチする際に、タイトルの行数（通常は１行） の数を提供します。
     */
    private int waitForIteratorTitleSearchY = 1;

    /**
     * 繰り返し型のブロックオブジェクトのコンストラクタです。
     *
     * @param name
     *            ブロック名。
     */
    public BlancoCalcWriterTableBlock(String name) {
        setName(name);
    }

    /**
     * 繰り返し型のブロックオブジェクトにカラムを追加します。
     * 
     * @param item
     *            ブロックへと追加したいカラム。
     */
    public void add(BlancoCalcWriterTableColumn item) {
        list.add(item);
    }

    /**
     * タイトル行をきっかけに、列アイテムを検索します。
     * 
     * @param titleString
     *            タイトル文字列。
     * @return 発見された列アイテム。
     */
    public BlancoCalcWriterTableColumn findByTitleString(String titleString) {
        final int listSize = list.size();
        for (int index = 0; index < listSize; index++) {
            final BlancoCalcWriterTableColumn item = (BlancoCalcWriterTableColumn) list
                    .get(index);
            if (item.isStartString(titleString)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 列番号をきっかけに、列アイテムを検索します。
     * 
     * @param pos
     *            列の番号。
     * @return 発見された列アイテム。
     */
    public BlancoCalcWriterTableColumn findByColumnPosition(int pos) {
        final int listSize = list.size();
        for (int index = 0; index < listSize; index++) {
            final BlancoCalcWriterTableColumn item = (BlancoCalcWriterTableColumn) list
                    .get(index);
            if (item.getColumnPosition() == pos) {
                return item;
            }
        }
        return null;
    }

    /**
     * Ｙ方向の検索範囲を取得します。
     * 
     * @return Ｙ方向の検索範囲。
     */
    public int getSearchRangeForTitleY() {
        return waitForIteratorTitleSearchY;
    }

    /**
     * タイトル文字列からのＸ方向の検索範囲を取得します。
     * 
     * @param arg
     *            Ｘ方向の検索範囲。
     */
    public void setSearchRangeForTitleY(int arg) {
        waitForIteratorTitleSearchY = arg;
    }

    /**
     * 列の名前を設定します。
     * 
     * @param arg
     *            列の名前。
     */
    public void setRowName(String arg) {
        blockRowName = arg;
    }

    /**
     * 列の名前を取得します。
     * 
     * @return 列の名前。
     */
    public String getRowName() {
        return blockRowName;
    }

    /**
     * 列のサイズを取得します。
     *
     * @return 列のサイズ。
     */
    public int getListSize() {
        return list.size();
    }

}
