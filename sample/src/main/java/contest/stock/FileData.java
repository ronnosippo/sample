package contest.stock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 各銘柄データ全量
 * @author owner
 *
 */
public class FileData {

	/** 元ファイル */
	private File stockFile;

	/** データ */
	private List<FileRowData> allData = new ArrayList<>();

	/**
	 * @param stockFile
	 */
	public FileData(File stockFile) {
		super();
		this.stockFile = stockFile;
	}

	/**
	 * 行データの追加
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
	 * @param stockFile セットする stockFile
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
	 * @param allData セットする allData
	 */
	public void setAllData(List<FileRowData> allData) {
		this.allData = allData;
	}

}
