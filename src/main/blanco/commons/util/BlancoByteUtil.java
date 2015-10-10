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
 * blanco Frameworkにおける、バイトに関するユーティリティが含まれます。
 * 
 * 原則としてほとんどのメソッドはstaticメソッドとして提供されます。
 * 
 * @author IGA Tosiki
 */
public class BlancoByteUtil {
    /**
     * バイト配列の内容を比較します。
     * 
     * @param arg0
     *            左辺値。
     * @param arg1
     *            右辺値。
     * @return 一致した場合は0。それ以外の場合には左辺値-右辺値。
     */
    public static final int compare(final byte[] arg0, final byte[] arg1) {
        if (arg0 == null) {
            throw new IllegalArgumentException(
                    "BlancoStreamUtil.compareの入力パラメータの1番目にnullが与えられました。");
        }
        if (arg1 == null) {
            throw new IllegalArgumentException(
                    "BlancoStreamUtil.compareの入力パラメータの2番目にnullが与えられました。");
        }

        for (int index = 0;; index++) {
            // 最初に長さチェックをおこないます。
            if (index == arg0.length) {
                // 長さを超えてしまいました。
                if (index == arg1.length) {
                    // 最後の最後まで一致していました。
                    return 0;
                } else {
                    // 右辺値が大きいです。
                    return -1;
                }
            } else if (index == arg1.length) {
                // 長さを超えてしまいました。
                // 左辺値が大きいです。
                return 1;
            }
            if (arg0[index] == arg1[index]) {
                continue;
            } else {
                // 一致しませんでした。
                return arg0[index] - arg1[index];
            }
        }
    }
}
