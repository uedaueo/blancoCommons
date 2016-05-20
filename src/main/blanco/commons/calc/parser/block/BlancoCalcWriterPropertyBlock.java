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
 * キーマップのブロックを実装します。
 * 
 * @author IGA Tosiki
 */
public class BlancoCalcWriterPropertyBlock extends
        AbstractBlancoCalcParserBlock {

    private List<BlancoCalcWriterPropertyKey> list = new ArrayList<BlancoCalcWriterPropertyKey>();

    /**
     * プロパティブロックのコンストラクタ。
     *
     * @param name
     *            ブロック名。
     */
    public BlancoCalcWriterPropertyBlock(String name) {
        setName(name);
    }

    public List<BlancoCalcWriterPropertyKey> getList() {
        return list;
    }

    /**
     * プロパティブロックにプロパティキーを追加します。
     *
     * @param item
     *            プロパティキーオブジェクト。
     */
    public void add(BlancoCalcWriterPropertyKey item) {
        list.add(item);
    }

    /**
     * 開始文字列をきっかけに列アイテムを検索します。
     *
     * @param startString
     *            開始文字列。
     * @return 検索されたプロパティキーオブジェクト。
     */
    public BlancoCalcWriterPropertyKey findByStartString(String startString) {
        final int listSize = list.size();
        for (int index = 0; index < listSize; index++) {
            final BlancoCalcWriterPropertyKey item = (BlancoCalcWriterPropertyKey) list
                    .get(index);
            if (item.isStartString(startString)) {
                return item;
            }
        }
        return null;
    }
}
