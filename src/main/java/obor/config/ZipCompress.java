package obor.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.web.multipart.MultipartFile;

public class ZipCompress {

	/**
	 * 
	 * @Description: 解压缩
	 * @author liang_bo
	 * @date 2016年8月26日
	 *
	 * @param file
	 * @param sDestPath
	 * @return ArrayList  
	 * @throws
	 */
	public static List<String> Decompression(MultipartFile file, String sDestPath) {
		List<String> allFileName = new ArrayList<String>();
		try {
			// 先指定压缩档的位置和档名，建立FileInputStream对象
			FileInputStream fins = (FileInputStream)file.getInputStream();
			// 将fins传入ZipInputStream中
			ZipInputStream zins = new ZipInputStream(fins);
			ZipEntry ze = null;
			byte[] ch = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {
				File zfile = new File(sDestPath + ze.getName());
				File fpath = new File(zfile.getParentFile().getPath());
				if (ze.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
					zins.closeEntry();
				} else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					allFileName.add(zfile.getAbsolutePath());
					while ((i = zins.read(ch)) != -1)
						fouts.write(ch, 0, i);
					zins.closeEntry();
					fouts.close();
				}
			}
			fins.close();
			zins.close();
		} catch (Exception e) {
			System.err.println("Extract error:" + e.getMessage());
		}
		return allFileName;
	}

}
