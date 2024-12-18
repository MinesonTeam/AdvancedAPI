package kz.hxncus.mc.advancedapi.utility;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@UtilityClass
public final class ZipUtil {
	public byte[] zipWorld(File worldFolder) throws IOException {
		byte[] buffer = new byte[1024];
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
			ArrayList<String> fileList = new ArrayList<>();
			Iterator<File> iterator = FileUtils.iterateFiles(worldFolder, null, true);
			while (iterator.hasNext()) {
				fileList.add(iterator.next().getPath());
			}
			for (String file : fileList) {
				String s = file.replace(worldFolder.getName(), "world");
				if (s.equals("world" + File.separator + "uid.dat")) {
					continue;
				}
				ZipEntry ze = new ZipEntry(s);
				zipOutputStream.putNextEntry(ze);
				FileInputStream in = new FileInputStream(file);
				int len;
				while ((len = in.read(buffer)) > 0) {
					zipOutputStream.write(buffer, 0, len);
				}
				in.close();
			}
			zipOutputStream.closeEntry();
			zipOutputStream.close();
			return byteArrayOutputStream.toByteArray();
		}
	}
}
