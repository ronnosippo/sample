package contest.stock;

import java.io.File;

/**
 * 各銘柄データ概要として、特徴点を抽出したクラス
 * @author owner
 *
 */
public class FileDataDigest {

	/** 元ファイル */
	private File stockFile;

	/** 高値の最大値 */
	private FileRowData maxHighPriceRow;

	/** 安値の最小値 */
	private FileRowData minLowPriceRow;

	/** 出来高の最大値 */
	private FileRowData maxTradingVolumeRow;

	/**
	 * @return maxHighPrice
	 */
	public Double getMaxHighPrice() {
		return maxHighPriceRow.getHighPrice();
	}

	/**
	 * @return minLowPrice
	 */
	public Double getMinLowPrice() {
		return minLowPriceRow.getLowPrice();
	}

	/**
	 * @return maxHighPrice
	 */
	public Double getMaxTradingVolume() {
		return maxTradingVolumeRow.getTradingVolume();
	}

	/**
	 * @return maxHighPriceRow
	 */
	public FileRowData getMaxHighPriceRow() {
		return maxHighPriceRow;
	}

	/**
	 * @param maxHighPriceRow セットする maxHighPriceRow
	 */
	public void setMaxHighPriceRow(FileRowData maxHighPriceRow) {
		this.maxHighPriceRow = maxHighPriceRow;
	}

	/**
	 * @return minLowPriceRow
	 */
	public FileRowData getMinLowPriceRow() {
		return minLowPriceRow;
	}

	/**
	 * @param minLowPriceRow セットする minLowPriceRow
	 */
	public void setMinLowPriceRow(FileRowData minLowPriceRow) {
		this.minLowPriceRow = minLowPriceRow;
	}

	/**
	 * @return maxTradingVolumeRow
	 */
	public FileRowData getMaxTradingVolumeRow() {
		return maxTradingVolumeRow;
	}

	/**
	 * @param maxTradingVolumeRow セットする maxTradingVolumeRow
	 */
	public void setMaxTradingVolumeRow(FileRowData maxTradingVolumeRow) {
		this.maxTradingVolumeRow = maxTradingVolumeRow;
	}

	/**
	 * @return stockFile
	 */
	public File getStockFile() {
		return stockFile;
	}

	/**
	 * @param stockFile セットする stockFile
	 */
	public void setStockFile(File stockFile) {
		this.stockFile = stockFile;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileDataDigest [stockFile=").append(stockFile).append(", maxHighPriceRow=")
				.append(maxHighPriceRow).append(", minLowPriceRow=").append(minLowPriceRow)
				.append(", maxTradingVolumeRow=").append(maxTradingVolumeRow).append("]");
		return builder.toString();
	}


}
