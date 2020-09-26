package blanco.commons.calc.parser.task;

import java.io.IOException;

import blanco.commons.calc.parser.task.valueobject.BlancoCommonsProcessInput;

/**
 * バッチ処理クラス [BlancoCommonsBatchProcess]。
 *
 * <P>バッチ処理の呼び出し例。</P>
 * <code>
 * java -classpath (クラスパス) blanco.commons.calc.parser.task.BlancoCommonsBatchProcess -help
 * </code>
 */
public class BlancoCommonsBatchProcess {
    /**
     * 正常終了。
     */
    public static final int END_SUCCESS = 0;

    /**
     * 入力異常終了。内部的にjava.lang.IllegalArgumentExceptionが発生した場合。
     */
    public static final int END_ILLEGAL_ARGUMENT_EXCEPTION = 7;

    /**
     * 入出力例外終了。内部的にjava.io.IOExceptionが発生した場合。
     */
    public static final int END_IO_EXCEPTION = 8;

    /**
     * 異常終了。バッチの処理開始に失敗した場合、および内部的にjava.lang.Errorまたはjava.lang.RuntimeExceptionが発生した場合。
     */
    public static final int END_ERROR = 9;

    /**
     * コマンドラインから実行された際のエントリポイントです。
     *
     * @param args コンソールから引き継がれた引数。
     */
    public static final void main(final String[] args) {
        final BlancoCommonsBatchProcess batchProcess = new BlancoCommonsBatchProcess();

        // バッチ処理の引数。
        final BlancoCommonsProcessInput input = new BlancoCommonsProcessInput();

        boolean isNeedUsage = false;
        boolean isFieldInStreamProcessed = false;
        boolean isFieldOutStreamProcessed = false;
        boolean isFieldInStreamDefProcessed = false;
        boolean isFieldOutStreamExcelProcessed = false;

        // コマンドライン引数の解析をおこないます。
        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            if (arg.startsWith("-verbose=")) {
                input.setVerbose(Boolean.valueOf(arg.substring(9)).booleanValue());
            } else if (arg.startsWith("-inStream=")) {
                input.setInStream(arg.substring(10));
                isFieldInStreamProcessed = true;
            } else if (arg.startsWith("-outStream=")) {
                input.setOutStream(arg.substring(11));
                isFieldOutStreamProcessed = true;
            } else if (arg.startsWith("-inStreamDef=")) {
                input.setInStreamDef(arg.substring(13));
                isFieldInStreamDefProcessed = true;
            } else if (arg.startsWith("-outStreamExcel=")) {
                input.setOutStreamExcel(arg.substring(16));
                isFieldOutStreamExcelProcessed = true;
            } else if (arg.startsWith("-metadir=")) {
                input.setMetadir(arg.substring(9));
            } else if (arg.startsWith("-targetdir=")) {
                input.setTargetdir(arg.substring(11));
            } else if (arg.startsWith("-sheetType=")) {
                input.setSheetType(arg.substring(11));
            } else if (arg.equals("-?") || arg.equals("-help")) {
                usage();
                System.exit(END_SUCCESS);
            } else {
                System.out.println("BlancoCommonsBatchProcess: 入力パラメータ[" + arg + "]は無視されました。");
                isNeedUsage = true;
            }
        }

        if (isNeedUsage) {
            usage();
        }

        if( isFieldInStreamProcessed == false) {
            System.out.println("BlancoCommonsBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[inStream]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldOutStreamProcessed == false) {
            System.out.println("BlancoCommonsBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[outStream]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldInStreamDefProcessed == false) {
            System.out.println("BlancoCommonsBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[inStreamDef]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        if( isFieldOutStreamExcelProcessed == false) {
            System.out.println("BlancoCommonsBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[outStreamExcel]に値が設定されていません。");
            System.exit(END_ILLEGAL_ARGUMENT_EXCEPTION);
        }

        int retCode = batchProcess.execute(input);

        // 終了コードを戻します。
        // ※注意：System.exit()を呼び出している点に注意してください。
        System.exit(retCode);
    }

    /**
     * 具体的なバッチ処理内容を記述するためのメソッドです。
     *
     * このメソッドに実際の処理内容を記述します。
     *
     * @param input バッチ処理の入力パラメータ。
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR のいずれかの値を戻します。
     * @throws IOException 入出力例外が発生した場合。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public int process(final BlancoCommonsProcessInput input) throws IOException, IllegalArgumentException {
        // 入力パラメータをチェックします。
        validateInput(input);

        // この箇所でコンパイルエラーが発生する場合、BlancoCommonsProcessインタフェースを実装して blanco.commons.calc.parser.taskパッケージに BlancoCommonsProcessImplクラスを作成することにより解決できる場合があります。
        final BlancoCommonsProcess process = new BlancoCommonsProcessImpl();

        // 処理の本体を実行します。
        final int retCode = process.execute(input);

        return retCode;
    }

    /**
     * クラスをインスタンス化してバッチを実行する際のエントリポイントです。
     *
     * このメソッドは下記の仕様を提供します。
     * <ul>
     * <li>メソッドの入力パラメータの内容チェック。
     * <li>IllegalArgumentException, RuntimeException, Errorなどの例外をcatchして戻り値へと変換。
     * </ul>
     *
     * @param input バッチ処理の入力パラメータ。
     * @return バッチ処理の終了コード。END_SUCCESS, END_ILLEGAL_ARGUMENT_EXCEPTION, END_IO_EXCEPTION, END_ERROR のいずれかの値を戻します。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public final int execute(final BlancoCommonsProcessInput input) throws IllegalArgumentException {
        try {
            // バッチ処理の本体を実行します。
            int retCode = process(input);

            return retCode;
        } catch (IllegalArgumentException ex) {
            System.out.println("BlancoCommonsBatchProcess: 入力例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // 入力異常終了。
            return END_ILLEGAL_ARGUMENT_EXCEPTION;
        } catch (IOException ex) {
            System.out.println("BlancoCommonsBatchProcess: 入出力例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            // 入力異常終了。
            return END_IO_EXCEPTION;
        } catch (RuntimeException ex) {
            System.out.println("BlancoCommonsBatchProcess: ランタイム例外が発生しました。バッチ処理を中断します。:" + ex.toString());
            ex.printStackTrace();
            // 異常終了。
            return END_ERROR;
        } catch (Error er) {
            System.out.println("BlancoCommonsBatchProcess: ランタイムエラーが発生しました。バッチ処理を中断します。:" + er.toString());
            er.printStackTrace();
            // 異常終了。
            return END_ERROR;
        }
    }

    /**
     * このバッチ処理クラスの使い方の説明を標準出力に示すためのメソッドです。
     */
    public static final void usage() {
        System.out.println("BlancoCommonsBatchProcess: Usage:");
        System.out.println("  java blanco.commons.calc.parser.task.BlancoCommonsBatchProcess -verbose=値1 -inStream=値2 -outStream=値3 -inStreamDef=値4 -outStreamExcel=値5 -metadir=値6 -targetdir=値7 -sheetType=値8");
        System.out.println("    -verbose");
        System.out.println("      説明[verboseモードで動作させるかどうか。]");
        System.out.println("      型[真偽]");
        System.out.println("      デフォルト値[false]");
        System.out.println("    -inStream");
        System.out.println("      説明[ひな形メタファイル]");
        System.out.println("      型[文字列]");
        System.out.println("      必須パラメータ");
        System.out.println("    -outStream");
        System.out.println("      説明[出力される中間xml]");
        System.out.println("      型[文字列]");
        System.out.println("      必須パラメータ");
        System.out.println("    -inStreamDef");
        System.out.println("      説明[ひな形定義xml]");
        System.out.println("      型[文字列]");
        System.out.println("      必須パラメータ");
        System.out.println("    -outStreamExcel");
        System.out.println("      説明[出力されるひな形メタファイル]");
        System.out.println("      型[文字列]");
        System.out.println("      必須パラメータ");
        System.out.println("    -metadir");
        System.out.println("      説明[メタディレクトリ]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[blanco]");
        System.out.println("    -targetdir");
        System.out.println("      説明[出力先フォルダを指定します。無指定の場合にはカレント直下のblancoを用います。]");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[blanco]");
        System.out.println("    -sheetType");
        System.out.println("      型[文字列]");
        System.out.println("      デフォルト値[java]");
        System.out.println("    -? , -help");
        System.out.println("      説明[使い方を表示します。]");
    }

    /**
     * このバッチ処理クラスの入力パラメータの妥当性チェックを実施するためのメソッドです。
     *
     * @param input バッチ処理の入力パラメータ。
     * @throws IllegalArgumentException 入力値に不正が見つかった場合。
     */
    public void validateInput(final BlancoCommonsProcessInput input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException("BlancoBatchProcessBatchProcess: 処理開始失敗。入力パラメータ[input]にnullが与えられました。");
        }
        if (input.getInStream() == null) {
            throw new IllegalArgumentException("BlancoCommonsBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[inStream]に値が設定されていません。");
        }
        if (input.getOutStream() == null) {
            throw new IllegalArgumentException("BlancoCommonsBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[outStream]に値が設定されていません。");
        }
        if (input.getInStreamDef() == null) {
            throw new IllegalArgumentException("BlancoCommonsBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[inStreamDef]に値が設定されていません。");
        }
        if (input.getOutStreamExcel() == null) {
            throw new IllegalArgumentException("BlancoCommonsBatchProcess: 処理開始失敗。入力パラメータ[input]の必須フィールド値[outStreamExcel]に値が設定されていません。");
        }
    }
}
