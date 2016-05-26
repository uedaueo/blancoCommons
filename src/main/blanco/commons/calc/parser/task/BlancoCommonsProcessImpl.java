/*
 * blanco Framework
 * Copyright (C) 2004-2008 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package blanco.commons.calc.parser.task;

import blanco.commons.calc.parser.BlancoCalcTransformer;
import blanco.commons.calc.parser.constants.BlancoCommonsConstantsConstants;
import blanco.commons.calc.parser.message.BlancoCommonsMessage;
import blanco.commons.calc.parser.task.valueobject.BlancoCommonsProcessInput;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.xml.transform.TransformerException;
import java.io.*;

public class BlancoCommonsProcessImpl implements BlancoCommonsProcess {
    /**
     * メッセージ。
     */
    private final BlancoCommonsMessage fMsg = new BlancoCommonsMessage();

    /**
     * {@inheritDoc}
     */
    public int execute(final BlancoCommonsProcessInput input)
            throws IOException, IllegalArgumentException {
        System.out.println("- " + BlancoCommonsConstantsConstants.PRODUCT_NAME
                + " (" + BlancoCommonsConstantsConstants.VERSION + ")");

            final File fileMetadir = new File(input.getMetadir());
            if (fileMetadir.exists() == false) {
                throw new IllegalArgumentException(fMsg.getMbvoja01(input
                        .getMetadir()));
            }

                // テンポラリディレクトリを作成。
                new File(input.getTargetdir()
                        + BlancoCommonsConstantsConstants.TARGET_SUBDIRECTORY).mkdirs();

            InputStream inStream = null;
            OutputStream outStream = null;
            InputStream inStreamDef = null;
            OutputStream outStreamExcel = null;
            try {
                inStream = new BufferedInputStream(new FileInputStream(
                        input.getMetadir() + input.getInStream()));
                outStream = new BufferedOutputStream(new FileOutputStream(
                        input.getTargetdir()
                                + BlancoCommonsConstantsConstants.TARGET_SUBDIRECTORY
                                + "/"
                                + input.getOutStream()));
                inStreamDef = new BufferedInputStream(new FileInputStream(
                        input.getMetadir() + input.getInStreamDef()));
                outStreamExcel = new BufferedOutputStream(new FileOutputStream(
                        input.getTargetdir()
                                + BlancoCommonsConstantsConstants.TARGET_SUBDIRECTORY
                                + "/"
                                + input.getOutStreamExcel()));
                new BlancoCalcTransformer().process(inStreamDef, inStream, outStream, outStreamExcel);
                outStream.flush();
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } finally {
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
                if (inStreamDef != null) {
                    inStreamDef.close();
                }
                if (outStreamExcel != null) {
                    outStreamExcel.close();
                }
            }

            return BlancoCommonsBatchProcess.END_SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    public boolean progress(final String argProgressMessage) {
        System.out.println(argProgressMessage);
        return false;
    }
}
