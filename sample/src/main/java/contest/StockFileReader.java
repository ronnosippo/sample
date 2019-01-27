package contest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import contest.stock.FileData;
import contest.stock.FileRowData;

/**
 * �����t�@�C���̓ǂݍ��݃N���X
 * @author owner
 *
 */
public class StockFileReader {

	private static final int HIGH_COLUM_INDEX = 2;
	private static final int LOW_COLUM_INDEX = 3;
	private static final int TRADING_VOL_COLUM_INDEX = 5;

	/**
	 *
	 * @param resourceFile
	 *            �t�@�C����
	 * @return �v���p�e�B�I�u�W�F�N�g
	 */
	public static FileData read(File stockFile) {
		FileData stockAllData = new FileData(stockFile);

		try (FileReader fr = new FileReader(stockFile);
				BufferedReader br = new BufferedReader(fr);) {
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				// �ŏ���2�s�͖���
				if (i++ < 2) {
					continue;
				}
				String[] data = line.replaceAll("\"", "").split(",");
				Double high = Double.valueOf(data[HIGH_COLUM_INDEX]);
				Double low = Double.valueOf(data[LOW_COLUM_INDEX]);
				Double vol = Double.valueOf(data[TRADING_VOL_COLUM_INDEX]);
				stockAllData.addRow(new FileRowData(high, low, vol));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("�t�@�C���ǂݍ��݂Ŏ��s", e);
		}
		return stockAllData;
	}

}
