package blanco.commons.calc.parser.task;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import blanco.commons.calc.parser.task.valueobject.BlancoCommonsProcessInput;

/**
 * Apache Antタスク [blancoCommons]のクラス。
 *
 * blancoCommonsのAntTaskです。<br>
 * このクラスでは、Apache Antタスクで一般的に必要なチェックなどのコーディングを肩代わりします。
 * 実際の処理は パッケージ[blanco.commons.calc.parser.task]にBlancoCommonsBatchProcessクラスを作成して記述してください。<br>
 * <br>
 * Antタスクへの組み込み例<br>
 * <pre>
 * &lt;taskdef name=&quot;blancocommons&quot; classname=&quot;blanco.commons.calc.parser.task.BlancoCommonsTask">
 *     &lt;classpath&gt;
 *         &lt;fileset dir=&quot;lib&quot; includes=&quot;*.jar&quot; /&gt;
 *         &lt;fileset dir=&quot;lib.ant&quot; includes=&quot;*.jar&quot; /&gt;
 *     &lt;/classpath&gt;
 * &lt;/taskdef&gt;
 * </pre>
 */
public class BlancoCommonsTask extends Task {
    /**
     * blancoCommonsのAntTaskです。
     */
    protected BlancoCommonsProcessInput fInput = new BlancoCommonsProcessInput();

    /**
     * フィールド [inStream] に値がセットされたかどうか。
     */
    protected boolean fIsFieldInStreamProcessed = false;

    /**
     * フィールド [outStream] に値がセットされたかどうか。
     */
    protected boolean fIsFieldOutStreamProcessed = false;

    /**
     * フィールド [inStreamDef] に値がセットされたかどうか。
     */
    protected boolean fIsFieldInStreamDefProcessed = false;

    /**
     * フィールド [outStreamExcel] に値がセットされたかどうか。
     */
    protected boolean fIsFieldOutStreamExcelProcessed = false;

    /**
     * フィールド [metadir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldMetadirProcessed = false;

    /**
     * フィールド [targetdir] に値がセットされたかどうか。
     */
    protected boolean fIsFieldTargetdirProcessed = false;

    /**
     * フィールド [sheetType] に値がセットされたかどうか。
     */
    protected boolean fIsFieldSheetTypeProcessed = false;

    /**
     * verboseモードで動作させるかどうか。
     *
     * @param arg verboseモードで動作させるかどうか。
     */
    public void setVerbose(final boolean arg) {
        fInput.setVerbose(arg);
    }

    /**
     * verboseモードで動作させるかどうか。
     *
     * @return verboseモードで動作させるかどうか。
     */
    public boolean getVerbose() {
        return fInput.getVerbose();
    }

    /**
     * Antタスクの[inStream]アトリビュートのセッターメソッド。
     *
     * 項目番号: 1<br>
     * ひな形メタファイル<br>
     *
     * @param arg セットしたい値
     */
    public void setInStream(final String arg) {
        fInput.setInStream(arg);
        fIsFieldInStreamProcessed = true;
    }

    /**
     * Antタスクの[inStream]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 1<br>
     * ひな形メタファイル<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getInStream() {
        return fInput.getInStream();
    }

    /**
     * Antタスクの[outStream]アトリビュートのセッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力される中間xml<br>
     *
     * @param arg セットしたい値
     */
    public void setOutStream(final String arg) {
        fInput.setOutStream(arg);
        fIsFieldOutStreamProcessed = true;
    }

    /**
     * Antタスクの[outStream]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 2<br>
     * 出力される中間xml<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getOutStream() {
        return fInput.getOutStream();
    }

    /**
     * Antタスクの[inStreamDef]アトリビュートのセッターメソッド。
     *
     * 項目番号: 3<br>
     * ひな形定義xml<br>
     *
     * @param arg セットしたい値
     */
    public void setInStreamDef(final String arg) {
        fInput.setInStreamDef(arg);
        fIsFieldInStreamDefProcessed = true;
    }

    /**
     * Antタスクの[inStreamDef]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 3<br>
     * ひな形定義xml<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getInStreamDef() {
        return fInput.getInStreamDef();
    }

    /**
     * Antタスクの[outStreamExcel]アトリビュートのセッターメソッド。
     *
     * 項目番号: 4<br>
     * 出力されるひな形メタファイル<br>
     *
     * @param arg セットしたい値
     */
    public void setOutStreamExcel(final String arg) {
        fInput.setOutStreamExcel(arg);
        fIsFieldOutStreamExcelProcessed = true;
    }

    /**
     * Antタスクの[outStreamExcel]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 4<br>
     * 出力されるひな形メタファイル<br>
     * 必須アトリビュートです。Apache Antタスク上で必ず値が指定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getOutStreamExcel() {
        return fInput.getOutStreamExcel();
    }

    /**
     * Antタスクの[metadir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 5<br>
     * メタディレクトリ<br>
     *
     * @param arg セットしたい値
     */
    public void setMetadir(final String arg) {
        fInput.setMetadir(arg);
        fIsFieldMetadirProcessed = true;
    }

    /**
     * Antタスクの[metadir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 5<br>
     * メタディレクトリ<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getMetadir() {
        return fInput.getMetadir();
    }

    /**
     * Antタスクの[targetdir]アトリビュートのセッターメソッド。
     *
     * 項目番号: 6<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     *
     * @param arg セットしたい値
     */
    public void setTargetdir(final String arg) {
        fInput.setTargetdir(arg);
        fIsFieldTargetdirProcessed = true;
    }

    /**
     * Antタスクの[targetdir]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 6<br>
     * 出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。<br>
     * デフォルト値[blanco]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getTargetdir() {
        return fInput.getTargetdir();
    }

    /**
     * Antタスクの[sheetType]アトリビュートのセッターメソッド。
     *
     * 項目番号: 7<br>
     *
     * @param arg セットしたい値
     */
    public void setSheetType(final String arg) {
        fInput.setSheetType(arg);
        fIsFieldSheetTypeProcessed = true;
    }

    /**
     * Antタスクの[sheetType]アトリビュートのゲッターメソッド。
     *
     * 項目番号: 7<br>
     * デフォルト値[java]が設定されています。Apache Antタスク上でアトリビュートの指定が無い場合には、デフォルト値が設定されます。<br>
     *
     * @return このフィールドの値
     */
    public String getSheetType() {
        return fInput.getSheetType();
    }

    /**
     * Antタスクのメイン処理。Apache Antから このメソッドが呼び出されます。
     *
     * @throws BuildException タスクとしての例外が発生した場合。
     */
    @Override
    public final void execute() throws BuildException {
        System.out.println("blancoCommonsTask begin.");

        // 項目番号[1]、アトリビュート[inStream]は必須入力です。入力チェックを行います。
        if (fIsFieldInStreamProcessed == false) {
            throw new BuildException("必須アトリビュート[inStream]が設定されていません。処理を中断します。");
        }
        // 項目番号[2]、アトリビュート[outStream]は必須入力です。入力チェックを行います。
        if (fIsFieldOutStreamProcessed == false) {
            throw new BuildException("必須アトリビュート[outStream]が設定されていません。処理を中断します。");
        }
        // 項目番号[3]、アトリビュート[inStreamDef]は必須入力です。入力チェックを行います。
        if (fIsFieldInStreamDefProcessed == false) {
            throw new BuildException("必須アトリビュート[inStreamDef]が設定されていません。処理を中断します。");
        }
        // 項目番号[4]、アトリビュート[outStreamExcel]は必須入力です。入力チェックを行います。
        if (fIsFieldOutStreamExcelProcessed == false) {
            throw new BuildException("必須アトリビュート[outStreamExcel]が設定されていません。処理を中断します。");
        }

        if (getVerbose()) {
            System.out.println("- verbose:[true]");
            System.out.println("- inStream:[" + getInStream() + "]");
            System.out.println("- outStream:[" + getOutStream() + "]");
            System.out.println("- inStreamDef:[" + getInStreamDef() + "]");
            System.out.println("- outStreamExcel:[" + getOutStreamExcel() + "]");
            System.out.println("- metadir:[" + getMetadir() + "]");
            System.out.println("- targetdir:[" + getTargetdir() + "]");
            System.out.println("- sheetType:[" + getSheetType() + "]");
        }

        try {
            // 実際のAntタスクの主処理を実行します。
            // この箇所でコンパイルエラーが発生する場合、BlancoCommonsProcessインタフェースを実装して blanco.commons.calc.parser.taskパッケージに BlancoCommonsProcessImplクラスを作成することにより解決できる場合があります。
            final BlancoCommonsProcess proc = new BlancoCommonsProcessImpl();
            if (proc.execute(fInput) != BlancoCommonsBatchProcess.END_SUCCESS) {
                throw new BuildException("タスクは異常終了しました。");
            }
        } catch (IllegalArgumentException e) {
            if (getVerbose()) {
                e.printStackTrace();
            }
            throw new BuildException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中に例外が発生しました。処理を中断します。" + e.toString());
        } catch (Error e) {
            e.printStackTrace();
            throw new BuildException("タスクを処理中にエラーが発生しました。処理を中断します。" + e.toString());
        }
    }
}
