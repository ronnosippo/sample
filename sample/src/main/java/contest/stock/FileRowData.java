package contest.stock;

/**
 * �e������1�����f�[�^
 * @author owner
 *
 */
public class FileRowData {

	// ���t�Ƃ������邯�ǁA�W�v�Ɏg�����ڈȊO�͊���

	/** ���l */
	private Double highPrice;

	/** ���l */
	private Double lowPrice;

	/** �o���� */
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
	 * @param highPrice �Z�b�g���� highPrice
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
	 * @param lowPrice �Z�b�g���� lowPrice
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
	 * @param tradingVolume �Z�b�g���� tradingVolume
	 */
	public void setTradingVolume(Double tradingVolume) {
		this.tradingVolume = tradingVolume;
	}

	/* (�� Javadoc)
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
