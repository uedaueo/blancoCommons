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
 * 表示用の値と実際の値とをマッピングします。
 * 
 * @author IGA Tosiki
 */
public class BlancoCalcParserValueMapping {
    private String[] source = null;

    private String target = null;

    public BlancoCalcParserValueMapping(final String[] source,
            final String target) {
        this.source = source;
        this.target = target;
    }

    public void setSource(final String[] args) {
        source = args;
    }

    public String[] getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    /**
     * 文字列マッピングを行います。マッピングが失敗した場合にはnullを戻します。
     * 
     * @param value
     *            入力値。
     * @return マッピングにヒットしたら変換後の値を戻します。マッピングにヒットしなかった場合には入力値をそのまま戻します。
     */
    private String mapping(final String value) {
        if (source == null) {
            return null;
        }

        final int sourceLength = source.length;
        for (int index = 0; index < sourceLength; index++) {
            if (source[index].equals(value)) {
                return target;
            }
        }
        return null;
    }

    /**
     * 値のマッピングを行います。マッピングできなかった場合には 入力値をそのまま戻します。
     * 
     * @param value
     *            マッピングのマッチングを行いたい入力値。
     * @param mappings
     *            マッピング表。
     * @return マッピング後の値。マッピングが行われなかった場合には入力値そのもの。
     */
    public static final String mapping(final String value,
            final BlancoCalcParserValueMapping[] mappings) {
        if (mappings == null) {
            return value;
        }

        final int mappingCount = mappings.length;
        for (int index = 0; index < mappingCount; index++) {
            String result = mappings[index].mapping(value);
            if (result != null) {
                return result;
            }
        }
        return value;
    }
}
