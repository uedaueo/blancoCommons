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

import java.math.BigDecimal;

/**
 * blanco Frameworkにおける、BigDecimalに関するユーティリティが含まれます。
 * 
 * 原則としてほとんどのメソッドはstaticメソッドとして提供されます。
 * 
 * @author IGA Tosiki
 */
public class BlancoBigDecimalUtil {
    /**
     * intをBigDecimalに変換します。
     * 
     * BigDecimalのコンストラクタで数値を入力するものはJDK 1.5以降に導入されています。<br>
     * このメソッドは、1.4以前で動作させるために、BigDecimalのJDK
     * 1.5以降で導入されたコンストラクタを誤って利用してしまわないためのメソッドです。
     * 
     * @param valueSource
     *            変換もとの値。
     * @return BigDecimalに変換後の値。
     */
    public static final BigDecimal toBigDecimal(final int valueSource) {
        return new BigDecimal(String.valueOf(valueSource));
    }
}
