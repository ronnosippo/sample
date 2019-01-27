package contest.stock;

import java.io.File;

/**
 * �e�����f�[�^�T�v�Ƃ��āA�����_�𒊏o�����N���X
 * @author owner
 *
 */
public class FileDataDigest {

	/** ���t�@�C�� */
	private File stockFile;

	/** ���l�̍ő�l */
	private FileRowData maxHighPriceRow;

	/** ���l�̍ŏ��l */
	private FileRowData minLowPriceRow;

	/** �o�����̍ő�l */
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
	 * @param maxHighPriceRow �Z�b�g���� maxHighPriceRow
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
	 * @param minLowPriceRow �Z�b�g���� minLowPriceRow
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
	 * @param maxTradingVolumeRow �Z�b�g���� maxTradingVolumeRow
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
	 * @param stockFile �Z�b�g���� stockFile
	 */
	public void setStockFile(File stockFile) {
		this.stockFile = stockFile;
	}

	/* (�� Javadoc)
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
