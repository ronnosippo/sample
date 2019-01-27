package contest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import contest.stock.FileData;
import contest.stock.FileDataDigest;

public class RankingMain {

	private final static int RANK_LIMIT = 5;
	private final static String RESOURCE_DIR = "input";

	public static void main(String... args) throws IOException {

		// 個別銘柄ファイルの集計
		List<FileDataDigest> stockDigestList = new ArrayList<>();
		for (File f : getResourceFolderFiles(RESOURCE_DIR)) {
			FileData allData = StockFileReader.read(f);
			FileDataDigest digestData = StockAnalyzer.aggregate(allData);
			stockDigestList.add(digestData);
		}

		// 全銘柄のランキング作成
		List<FileDataDigest> highPriceRank = StockAnalyzer.rankByHighPrice(stockDigestList, RANK_LIMIT);
		System.out.println("■高値の最大値ランキング");
		for (FileDataDigest f : highPriceRank) {
			System.out.println(String.format("%s : %,10d", f.getStockFile().getName(), f.getMaxHighPrice().intValue()));
		}

		List<FileDataDigest> lowPriceRank = StockAnalyzer.rankByLowPrice(stockDigestList, RANK_LIMIT);
		System.out.println("■安値の最小値ランキング");
		for (FileDataDigest f : lowPriceRank) {
			System.out.println(String.format("%s : %,10d", f.getStockFile().getName(), f.getMinLowPrice().intValue()));
		}

		List<FileDataDigest> tradingRank = StockAnalyzer.rankByTradingVolume(stockDigestList, RANK_LIMIT);
		System.out.println("■出来高の最大値ランキング");
		for (FileDataDigest f : tradingRank) {
			System.out.println(
					String.format("%s : %,10d", f.getStockFile().getName(), f.getMaxTradingVolume().intValue()));
		}
	}

	/**
	 * リソース配下のファイル取得
	 * @param folder
	 * @return
	 */
	private static File[] getResourceFolderFiles(String folder) {
		String path = Thread.currentThread().getContextClassLoader().getResource(folder).getPath();
		return new File(path).listFiles();
	}
}
