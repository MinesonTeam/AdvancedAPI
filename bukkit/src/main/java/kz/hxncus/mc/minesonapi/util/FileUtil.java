package kz.hxncus.mc.minesonapi.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileUtil {
	public void deleteFolder(final File file) throws IOException {
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
