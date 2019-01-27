package contest.stock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * �e�����f�[�^�S��
 * @author owner
 *
 */
public class FileData {

	/** ���t�@�C�� */
	private File stockFile;

	/** �f�[�^ */
	private List<FileRowData> allData = new ArrayList<>();

	/**
	 * @param stockFile
	 */
	public FileData(File stockFile) {
		super();
		this.stockFile = stockFile;
	}

	/**
	 * �s�f�[�^�̒ǉ�
	 * @param rowData
	 */
	public void addRow(FileRowData rowData) {
		allData.add(rowData);
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

	/**
	 * @return allData
	 */
	public List<FileRowData> getAllData() {
		return allData;
	}

	/**
	 * @param allData �Z�b�g���� allData
	 */
	public void setAllData(List<FileRowData> allData) {
		this.allData = allData;
	}

}
