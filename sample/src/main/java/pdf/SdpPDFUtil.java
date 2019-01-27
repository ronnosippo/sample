package pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

/**
 * PDF操作用のUtil<br>
 *
 * @author dcs
 * @version 1.0
 *
 */
public class SdpPDFUtil {
	static final Logger log = Logger.getLogger("test");

	/** 不可視のコンストラクタ */
	private SdpPDFUtil() {
		// インスタンス化禁止

	}

	public static void main(String... args) throws Exception {
		File dir = new File("A:\\tmp");
		File[] files = dir.listFiles();
		for (File file : files) {
			log.info(file.getName());
		}

		log.info("マージ処理開始");
		long start = System.nanoTime();
		merge(Paths.get(dir.getAbsolutePath(), "merged_" + UUID.randomUUID() + ".pdf").toFile(), files);


		log.info(("Time:" + (System.nanoTime() - start) / 1000000f + "ms"));
	}

	/**
	 * PDFファイルを結合してファイル出力する
	 * @param out 結合後ファイル
	 * @param in 結合元ファイル(複数)
	 */
	public static void merge(File out, File... in) {
		Document document = null;
		PdfCopy copy = null;
		try {
			for (File f : in) {
				// メモリを効率的に利用するため、partialモード有効
				PdfReader reader = new PdfReader(f.getAbsolutePath(), null, true);
				reader.consolidateNamedDestinations();
				// コピー後ファイルのページサイズを最初にコピーするPDFファイルと合わせる
				if (document == null) {
					document = new Document(reader.getPageSizeWithRotation(1));
					copy = new PdfCopy(document, new FileOutputStream(out));
					document.open();

				}
				// 開いたPDFから1ページづつコピーする

				for (int i = 1; i <= reader.getNumberOfPages(); i++) {
					copy.addPage(copy.getImportedPage(reader, i));
				}
				reader.close();
			}
		} catch (IOException | DocumentException e) {
			throw new RuntimeException("PDFの結合処理中に予期しない例外が発生しました。", e);
		} finally {
			if (document != null) {
				document.close();
			}
			if (copy != null) {
				copy.close();
			}
		}
	}

	/*	<!-- PDF操作用ライブラリ ※LGPLライセンスで使用するため、バージョンは4 -->
		<dependency>
			<groupId>com.lowagie</groupId>
			<artifactId>itext</artifactId>
			<version>4.2.1</version>
		</dependency>
		<dependency>
			<groupId>org.bouncycastle</groupId>
			<artifactId>bcprov-jdk15on</artifactId>
			<version>1.50</version>
		</dependency>
		<dependency>
			<groupId>org.bouncycastle</groupId>
			<artifactId>bcpkix-jdk15on</artifactId>
			<version>1.50</version>
		</dependency>*/

	//
	//	public void merge(OutputStream os, File out, File... inputFiles) {
	//		try {
	//
	//			PDFMergerUtility2 mergedDoc = new PDFMergerUtility2();
	//			for (File file : inputFiles) {
	//				log.info(file.getName());
	//				mergedDoc.addSource(file);
	//			}
	//			mergedDoc.setDestinationFileName(out.getAbsolutePath());
	//			// サイズ無制限でメインメモリ（一時ファイルなし）のみを使用する
	//			MemoryUsageSetting setting = MemoryUsageSetting.setupMixed(1000000);
	//			mergedDoc.mergeDocuments(setting);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//	}
	//
	//	/**
	//	* 複数のPDFを１つに結合する
	//	* @param inputFiles
	//	* @return 結合結果をバイナリで返す
	//	 * @throws Exception
	//	*/
	//	public OutputStream pdfCombine(OutputStream os, File output, File... inputFiles) throws Exception {
	//		List<PDDocument> pddHolder = new ArrayList<>();
	//		FileOutputStream fos = new FileOutputStream(output);
	//
	//		try (PDDocument combiledPDFdoc = new PDDocument()) {
	//			for (File f : inputFiles) {
	//				PDDocument pf = PDDocument.load(f);
	//				for (PDPage p : pf.getPages())
	//					combiledPDFdoc.addPage(p);
	//				pddHolder.add(pf);
	//			}
	//			combiledPDFdoc.save(fos);
	//			// Save後に開放可能、Save前に開放すると上手く動かない
	//			pddHolder.stream().sequential().forEach(item -> {
	//				try {
	//					item.close();
	//				} catch (IOException ex) {
	//				}
	//			});
	//		} catch (IOException ex) {
	//			ex.printStackTrace();
	//		}
	//		return fos;
	//	}
}
