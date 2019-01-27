package contest;

import static java.util.Comparator.*;

import java.util.ArrayList;
import java.util.List;

import contest.stock.FileData;
import contest.stock.FileDataDigest;
import contest.stock.FileRowData;

/**
 * �����f�[�^���W�v�E�����N�t�����邽�߂̃N���X
 * @author owner
 *
 */
public class StockAnalyzer {

	/**
	 * �������Ƃ̏W�v
	 *
	 * @param resourceFile
	 *            �t�@�C����
	 * @return �v���p�e�B�I�u�W�F�N�g
	 */
	public static FileDataDigest aggregate(FileData stockData) {
		FileDataDigest digest = new FileDataDigest();
		digest.setStockFile(stockData.getStockFile());

		List<FileRowData> allData = stockData.getAllData();
		// �ō��l�̍s�f�[�^�擾
		FileRowData maxHighRow = allData.stream().sorted(comparing(FileRowData::getHighPrice).reversed()).findFirst()
				.get();
		digest.setMaxHighPriceRow(maxHighRow);
		// �ň��l�̍s�f�[�^�擾
		FileRowData minLowRow = allData.stream().sorted(comparing(FileRowData::getLowPrice)).findFirst().get();
		digest.setMinLowPriceRow(minLowRow);
		// �ō��o�����̍s�f�[�^�擾
		FileRowData maxVolRow = allData.stream().sorted(comparing(FileRowData::getTradingVolume).reversed()).findFirst()
				.get();
		digest.setMaxTradingVolumeRow(maxVolRow);

		return digest;
	}

	/**
	 * �S�����ŁAHighPrice�̏��X�����擾
	 *
	 * @param allStockData
	 * @param rank
	 * @return
	 */
	public static List<FileDataDigest> rankByHighPrice(List<FileDataDigest> allStockData, int rank) {
		List<FileDataDigest> result = new ArrayList<>();

		allStockData.sort(comparing(FileDataDigest::getMaxHighPrice).reversed());
		// �������܂߂ď��X����FileDigest���i�[
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
	 * �S�����ŁALowPrice�̏��X�����擾
	 *
	 * @param allStockData
	 * @param rank
	 * @return
	 */
	public static List<FileDataDigest> rankByLowPrice(List<FileDataDigest> allStockData, int rank) {
		List<FileDataDigest> result = new ArrayList<>();

		allStockData.sort(comparing(FileDataDigest::getMinLowPrice));
		// �������܂߂ď��X����FileDigest���i�[
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
	 * �S�����ŁATradingVolume�̏��X�����擾
	 *
	 * @param allStockData
	 * @param rank
	 * @return
	 */
	public static List<FileDataDigest> rankByTradingVolume(List<FileDataDigest> allStockData, int rank) {
		List<FileDataDigest> result = new ArrayList<>();

		allStockData.sort(comparing(FileDataDigest::getMaxTradingVolume).reversed());
		// �������܂߂ď��X����FileDigest���i�[
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
