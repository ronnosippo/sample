package contest;

import static java.util.Comparator.*;

import java.util.ArrayList;
import java.util.List;

import contest.stock.FileData;
import contest.stock.FileDataDigest;
import contest.stock.FileRowData;

/**
 * 銘柄データを集計・ランク付けするためのクラス
 * @author owner
 *
 */
public class StockAnalyzer {

	/**
	 * 銘柄ごとの集計
	 *
	 * @param resourceFile
	 *            ファイル名
	 * @return プロパティオブジェクト
	 */
	public static FileDataDigest aggregate(FileData stockData) {
		FileDataDigest digest = new FileDataDigest();
		digest.setStockFile(stockData.getStockFile());

		List<FileRowData> allData = stockData.getAllData();
		// 最高値の行データ取得
		FileRowData maxHighRow = allData.stream().sorted(comparing(FileRowData::getHighPrice).reversed()).findFirst()
				.get();
		digest.setMaxHighPriceRow(maxHighRow);
		// 最安値の行データ取得
		FileRowData minLowRow = allData.stream().sorted(comparing(FileRowData::getLowPrice)).findFirst().get();
		digest.setMinLowPriceRow(minLowRow);
		// 最高出来高の行データ取得
		FileRowData maxVolRow = allData.stream().sorted(comparing(FileRowData::getTradingVolume).reversed()).findFirst()
				.get();
		digest.setMaxTradingVolumeRow(maxVolRow);

		return digest;
	}

	/**
	 * 全銘柄で、HighPriceの上位X件を取得
	 *
	 * @param allStockData
	 * @param rank
	 * @return
	 */
	public static List<FileDataDigest> rankByHighPrice(List<FileDataDigest> allStockData, int rank) {
		List<FileDataDigest> result = new ArrayList<>();

		allStockData.sort(comparing(FileDataDigest::getMaxHighPrice).reversed());
		// 同着も含めて上位X件のFileDigestを格納
		int i = 0;
		double before = -1;
		for (FileDataDigest stock : allStockData) {
			if (i++ < rank || before == stock.getMaxHighPrice()) {
				result.add(stock);
			}
			before = stock.getMaxHighPrice();
		}
		return result;
	}

	/**
	 * 全銘柄で、LowPriceの上位X件を取得
	 *
	 * @param allStockData
	 * @param rank
	 * @return
	 */
	public static List<FileDataDigest> rankByLowPrice(List<FileDataDigest> allStockData, int rank) {
		List<FileDataDigest> result = new ArrayList<>();

		allStockData.sort(comparing(FileDataDigest::getMinLowPrice));
		// 同着も含めて上位X件のFileDigestを格納
		int i = 0;
		double before = -1;
		for (FileDataDigest stock : allStockData) {
			if (i++ < rank || before == stock.getMinLowPrice()) {
				result.add(stock);
			}
			before = stock.getMinLowPrice();
		}
		return result;
	}

	/**
	 * 全銘柄で、TradingVolumeの上位X件を取得
	 *
	 * @param allStockData
	 * @param rank
	 * @return
	 */
	public static List<FileDataDigest> rankByTradingVolume(List<FileDataDigest> allStockData, int rank) {
		List<FileDataDigest> result = new ArrayList<>();

		allStockData.sort(comparing(FileDataDigest::getMaxTradingVolume).reversed());
		// 同着も含めて上位X件のFileDigestを格納
		int i = 0;
		double before = -1;
		for (FileDataDigest stock : allStockData) {
			if (i++ < rank || before == stock.getMaxTradingVolume()) {
				result.add(stock);
			}
			before = stock.getMaxTradingVolume();
		}
		return result;
	}

}
