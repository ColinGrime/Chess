package me.scill.chess.utilities;

import java.io.InputStream;

public class FileUtility {

	public static InputStream getStream(String path) {
		InputStream inputStream = FileUtility.class.getResourceAsStream(path);

		if (inputStream == null)
			System.out.println("Error: Path \"" + path + "\" does not exist.");

		return inputStream;
	}
}
