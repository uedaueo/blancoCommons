package blanco.commons.calc.parser.task.valueobject;

/**
 * 処理クラス [BlancoCommonsProcess]の入力バリューオブジェクトクラスです。
 */
public class BlancoCommonsProcessInput {
    /**
     * verboseモードで動作させるかどうか。
     *
     * フィールド: [verbose]。
     * デフォルト: [false]。
     */
    private boolean fVerbose = false;

    /**
     * ひな形メタファイル
     *
     * フィールド: [inStream]。
     */
    private String fInStream;

    /**
     * 出力される中間xml
     *
     * フィールド: [outStream]。
     */
    private String fOutStream;

    /**
     * ひな形定義xml
     *
     * フィールド: [inStreamDef]。
     */
    private String fInStreamDef;

    /**
     * 出力されるひな形メタファイル
     *
     * フィールド: [outStreamExcel]。
     */
    private String fOutStreamExcel;

    /**
     * メタディレクトリ
     *
     * フィールド: [metadir]。
     * デフォルト: [blanco]。
     */
    private String fMetadir = "blanco";

    /**
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。
     *
     * フィールド: [targetdir]。
     * デフォルト: [blanco]。
     */
    private String fTargetdir = "blanco";

    /**
     * フィールド: [sheetType]。
     * デフォルト: [java]。
     */
    private String fSheetType = "java";

    /**
     * フィールド [verbose] の値を設定します。
     *
     * フィールドの説明: [verboseモードで動作させるかどうか。]。
     *
     * @param argVerbose フィールド[verbose]に設定する値。
     */
    public void setVerbose(final boolean argVerbose) {
        fVerbose = argVerbose;
    }

    /**
     * フィールド [verbose] の値を取得します。
     *
     * フィールドの説明: [verboseモードで動作させるかどうか。]。
     * デフォルト: [false]。
     *
     * @return フィールド[verbose]から取得した値。
     */
    public boolean getVerbose() {
        return fVerbose;
    }

    /**
     * フィールド [inStream] の値を設定します。
     *
     * フィールドの説明: [ひな形メタファイル]。
     *
     * @param argInStream フィールド[inStream]に設定する値。
     */
    public void setInStream(final String argInStream) {
        fInStream = argInStream;
    }

    /**
     * フィールド [inStream] の値を取得します。
     *
     * フィールドの説明: [ひな形メタファイル]。
     *
     * @return フィールド[inStream]から取得した値。
     */
    public String getInStream() {
        return fInStream;
    }

    /**
     * フィールド [outStream] の値を設定します。
     *
     * フィールドの説明: [出力される中間xml]。
     *
     * @param argOutStream フィールド[outStream]に設定する値。
     */
    public void setOutStream(final String argOutStream) {
        fOutStream = argOutStream;
    }

    /**
     * フィールド [outStream] の値を取得します。
     *
     * フィールドの説明: [出力される中間xml]。
     *
     * @return フィールド[outStream]から取得した値。
     */
    public String getOutStream() {
        return fOutStream;
    }

    /**
     * フィールド [inStreamDef] の値を設定します。
     *
     * フィールドの説明: [ひな形定義xml]。
     *
     * @param argInStreamDef フィールド[inStreamDef]に設定する値。
     */
    public void setInStreamDef(final String argInStreamDef) {
        fInStreamDef = argInStreamDef;
    }

    /**
     * フィールド [inStreamDef] の値を取得します。
     *
     * フィールドの説明: [ひな形定義xml]。
     *
     * @return フィールド[inStreamDef]から取得した値。
     */
    public String getInStreamDef() {
        return fInStreamDef;
    }

    /**
     * フィールド [outStreamExcel] の値を設定します。
     *
     * フィールドの説明: [出力されるひな形メタファイル]。
     *
     * @param argOutStreamExcel フィールド[outStreamExcel]に設定する値。
     */
    public void setOutStreamExcel(final String argOutStreamExcel) {
        fOutStreamExcel = argOutStreamExcel;
    }

    /**
     * フィールド [outStreamExcel] の値を取得します。
     *
     * フィールドの説明: [出力されるひな形メタファイル]。
     *
     * @return フィールド[outStreamExcel]から取得した値。
     */
    public String getOutStreamExcel() {
        return fOutStreamExcel;
    }

    /**
     * フィールド [metadir] の値を設定します。
     *
     * フィールドの説明: [メタディレクトリ]。
     *
     * @param argMetadir フィールド[metadir]に設定する値。
     */
    public void setMetadir(final String argMetadir) {
        fMetadir = argMetadir;
    }

    /**
     * フィールド [metadir] の値を取得します。
     *
     * フィールドの説明: [メタディレクトリ]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[metadir]から取得した値。
     */
    public String getMetadir() {
        return fMetadir;
    }

    /**
     * フィールド [targetdir] の値を設定します。
     *
     * フィールドの説明: [出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]。
     *
     * @param argTargetdir フィールド[targetdir]に設定する値。
     */
    public void setTargetdir(final String argTargetdir) {
        fTargetdir = argTargetdir;
    }

    /**
     * フィールド [targetdir] の値を取得します。
     *
     * フィールドの説明: [出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]。
     * デフォルト: [blanco]。
     *
     * @return フィールド[targetdir]から取得した値。
     */
    public String getTargetdir() {
        return fTargetdir;
    }

    /**
     * フィールド [sheetType] の値を設定します。
     *
     * フィールドの説明: []。
     *
     * @param argSheetType フィールド[sheetType]に設定する値。
     */
    public void setSheetType(final String argSheetType) {
        fSheetType = argSheetType;
    }

    /**
     * フィールド [sheetType] の値を取得します。
     *
     * フィールドの説明: []。
     * デフォルト: [java]。
     *
     * @return フィールド[sheetType]から取得した値。
     */
    public String getSheetType() {
        return fSheetType;
    }

    /**
     * このバリューオブジェクトの文字列表現を取得します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ文字列化の処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @return バリューオブジェクトの文字列表現。
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("blanco.commons.calc.parser.task.valueobject.BlancoCommonsProcessInput[");
        buf.append("verbose=" + fVerbose);
        buf.append(",inStream=" + fInStream);
        buf.append(",outStream=" + fOutStream);
        buf.append(",inStreamDef=" + fInStreamDef);
        buf.append(",outStreamExcel=" + fOutStreamExcel);
        buf.append(",metadir=" + fMetadir);
        buf.append(",targetdir=" + fTargetdir);
        buf.append(",sheetType=" + fSheetType);
        buf.append("]");
        return buf.toString();
    }

    /**
     * このバリューオブジェクトを指定のターゲットに複写します。
     *
     * <P>使用上の注意</P>
     * <UL>
     * <LI>オブジェクトのシャロー範囲のみ複写処理対象となります。
     * <LI>オブジェクトが循環参照している場合には、このメソッドは使わないでください。
     * </UL>
     *
     * @param target target value object.
     */
    public void copyTo(final BlancoCommonsProcessInput target) {
        if (target == null) {
            throw new IllegalArgumentException("Bug: BlancoCommonsProcessInput#copyTo(target): argument 'target' is null");
        }

        // No needs to copy parent class.

        // Name: fVerbose
        // Type: boolean
        target.fVerbose = this.fVerbose;
        // Name: fInStream
        // Type: java.lang.String
        target.fInStream = this.fInStream;
        // Name: fOutStream
        // Type: java.lang.String
        target.fOutStream = this.fOutStream;
        // Name: fInStreamDef
        // Type: java.lang.String
        target.fInStreamDef = this.fInStreamDef;
        // Name: fOutStreamExcel
        // Type: java.lang.String
        target.fOutStreamExcel = this.fOutStreamExcel;
        // Name: fMetadir
        // Type: java.lang.String
        target.fMetadir = this.fMetadir;
        // Name: fTargetdir
        // Type: java.lang.String
        target.fTargetdir = this.fTargetdir;
        // Name: fSheetType
        // Type: java.lang.String
        target.fSheetType = this.fSheetType;
    }
}
