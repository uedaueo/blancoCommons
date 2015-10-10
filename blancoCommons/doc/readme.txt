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
 1.JExcelApi - Java Excel API - A Java API to read, write and modify Excel spreadsheets
     http://jexcelapi.sourceforge.net/
     http://sourceforge.net/projects/jexcelapi/
     http://www.andykhan.com/jexcelapi/ 
   概要: JavaからExcelブック形式を読み書きするためのライブラリです。
   ライセンス: GNU Lesser General Public License
