package kz.hxncus.mc.minesonapi.utility;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class File util.
 * @author Hxncus
 * @since  1.0.1
 */
@UtilityClass
public class FileUtil {
	public void copy(@NonNull InputStream inputStream, @NonNull File file) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] array = new byte[1024];
			int read;
			while ((read = inputStream.read(array)) > 0) {
				outputStream.write(array, 0, read);
			}
			outputStream.close();
			inputStream.close();
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Delete folder.
	 *
	 * @param file the file
	 * @throws IOException the io exception
	 */
	public void deleteFolder(@NonNull final File file) throws IOException {
		if (file.isDirectory()) {
			final File[] files = file.listFiles();
			if (files == null) {
				return;
			}
			for (final File subFile : files) {
				deleteFolder(subFile);
			}
		}
		if (!file.delete()) {
			throw new IOException("Failed to delete file: " + file.getAbsolutePath());
		}
	}
}
