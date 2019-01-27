package pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class PDFJoiner {




	public void join(File outputPath, File... inputPaths) {
		Document document = null;
		PdfCopy copy = null;

		try {
			for (File inputPath : inputPaths) {
				PdfReader reader = new PdfReader(inputPath.getAbsolutePath(), null, true); // ここがポイント
				reader.consolidateNamedDestinations();

				if (document == null) {
					document = new Document(reader.getPageSizeWithRotation(1));
					copy = new PdfCopy(document, new FileOutputStream(outputPath));
					document.open();
				}

				PdfImportedPage page;
				int numberOfPages = reader.getNumberOfPages();
				for (int i = 1; i <= numberOfPages; i++) {
					page = copy.getImportedPage(reader, i);
					copy.addPage(page);
				}
				reader.close();
			}
			copy.close();
			document.close();

		} catch (IOException e) {
			throw new RuntimeException(e);

		} catch (DocumentException e) {
			throw new RuntimeException(e);

		} finally {
			if (document != null) {
				document.close();
			}
			if (copy != null) {
				copy.close();
			}
		}
	}
}
