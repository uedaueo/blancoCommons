blancoCommons は Java言語用の共通クラスライブラリ実装です。
blanco Frameworkの中から特に呼び出されがちなクラス群を提供します。

[blancoCalcParser]
  blanco Framework間で利用する、Calc (Excel)設定シートの入力処理で利用される
  共通的な機能を提供します。SAX2パーサとして実装されています。
  mainから設定をxmlファイルから読み込んで単体で駆動できるようにもなっています。

[ライセンス]
 1.ライセンス として GNU Lesser General Public License と Eclipse Public License 1.0 をデュアルライセンスで採用しています。
   いずれかのライセンスを、または全てのライセンスを適用します。

[依存するライブラリ]
blancoCommonsは下記のライブラリを利用しています。
 1.Apache POI - the Java API for Microsoft Documents
     https://poi.apache.org/
     https://ja.wikipedia.org/wiki/Apache_POI
   概要: JavaからExcelブック形式を読み書きするためのライブラリです。
   ライセンス: Apache License 2.0

[blancoCalcWriter](ver2.0.2以降)
  blanco Framework間で利用するCalc (Excel)設定シートに、
  値付き定義xmlで定義した値をプロットする機能を提供します。
  mainから設定をひな形excelとひな形xmlファイルから読み込んで単体で駆動できるようにもなっています。
  ビルドには、
  * ant clean
  * ant meta
  * ant compile
  * ant jar
  を行い、実行には、
  * ant -f writer.xml BlancoCalcWriterEXE001
  などと行って下さい。

