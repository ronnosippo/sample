# ファイルIO編

## お題概要
「src/main/resource/input」配下に格納されているすべてのCSVファイルから，CSVファイル単位に「高値の最大値」，「安値の最小値」，「出来高の最大値」を計算し，全CSVファイル単位でランク付けした後，上位5件をコンソールに出力する。

### ランク付け仕様
* 高値の最大値　：　降順
* 安値の最小値　：　昇順
* 出来高の最大値：　降順

### 順位が同一の場合
仮に一位のレコードだけで5件を超える場合などは，5位以上のレコードをすべて出力する。

