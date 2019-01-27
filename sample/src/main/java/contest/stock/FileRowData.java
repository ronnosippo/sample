package contest.stock;

/**
 * 各銘柄の1日分データ
 * @author owner
 *
 */
public class FileRowData {

	// 日付とかもあるけど、集計に使う項目以外は割愛

	/** 高値 */
	private Double highPrice;

	/** 安値 */
	private Double lowPrice;

	/** 出来高 */
	private Double tradingVolume;

	/**
	 * @param highPrice
	 * @param lowPrice
	 * @param tradingVolume
	 */
	public FileRowData(Double highPrice, Double lowPrice, Double tradingVolume) {
		super();
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.tradingVolume = tradingVolume;
	}

	/**
	 * @return highPrice
	 */
	public Double getHighPrice() {
		return highPrice;
	}

	/**
	 * @param highPrice セットする highPrice
	 */
	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	/**
	 * @return lowPrice
	 */
	public Double getLowPrice() {
		return lowPrice;
	}

	/**
	 * @param lowPrice セットする lowPrice
	 */
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	/**
	 * @return tradingVolume
	 */
	public Double getTradingVolume() {
		return tradingVolume;
	}

	/**
	 * @param tradingVolume セットする tradingVolume
	 */
	public void setTradingVolume(Double tradingVolume) {
		this.tradingVolume = tradingVolume;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RowData [highPrice=").append(highPrice).append(", lowPrice=").append(lowPrice)
				.append(", tradingVolume=").append(tradingVolume).append("]");
		return builder.toString();
	}

}
