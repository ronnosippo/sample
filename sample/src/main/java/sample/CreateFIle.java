package sample;

import java.io.File;

public class CreateFIle {


	public static void main(String...args) throws Exception {
		String newFilePath = "/tmp/newFile";

		while(true) {
			File file = new File(newFilePath);
			file.delete();
			file.createNewFile();
			Thread.sleep(10000);
		}

	}
}
