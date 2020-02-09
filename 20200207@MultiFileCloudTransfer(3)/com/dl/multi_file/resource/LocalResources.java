package com.dl.multi_file.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parser_reflect.util.PropertiesParser;

/**
 * 扫描文件，读写文件的基本类<br>
 * 1、给定默认根路径，也可通过properties文件配置；<br>
 * 2、根据根路径读取路径下的文件，将扫描到的资源保存在map中；<br>
 * 3、提供RandomAccessFile的随机文件读写；
 * @author dl
 *
 */
public class LocalResources {
	public static final String DEFAULT_PATH = "N:\\LocalResources";

	// 以文件名、起始地址与文件长字符串为键，以文件描述对象为值
	private static final Map<String, SectionInfo> systemMap = new HashMap<>();
	private String rootPath;
	
	public LocalResources() {
		rootPath = DEFAULT_PATH;
	}
	
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
	public void readConfig(String path) {
		PropertiesParser.load(path);
		
		String rootPath = PropertiesParser.findElement("rootPath");
		if (rootPath != null && !rootPath.equals("")) {
			this.rootPath = rootPath;
		}
	}
	
	/**
	 * 根据根目录读取本地资源；
	 */
	public Map<String, SectionInfo> scanLocalResource() {
		File rootFile = new File(rootPath);
		scanLocalResource(rootFile);
		
		return new HashMap<>(systemMap);
	}
	
	/**
	 * 带有递归的文件扫描；
	 * @param rootFile
	 */
	private void scanLocalResource(File rootFile) {
		File[] files = rootFile.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				String filePath = file.getAbsolutePath();
				int len = rootPath.length();
				String path = filePath.substring(len);
				int length = (int) file.length();
				SectionInfo info = new SectionInfo(path, 0, length);
				systemMap.put(info.toString(), info);
			} else {
				scanLocalResource(file);
			}
		}		
	}
	
	/**
	 * 返回map的键集，以便上层使用
	 * @return
	 */
	public List<String> resourceList() {
		return new ArrayList<>(systemMap.keySet());
	}
	
	/**
	 * 根据文件名读取指定偏移量和长度的字节数据；
	 * @param name
	 * @param offset
	 * @param size
	 */
	public void readFromLocal(SectionInfo sectionInfo) {
		String path = sectionInfo.getFileName();
		int offset = sectionInfo.getOffset();
		int size = sectionInfo.getSize();
		String fileName = rootPath + "\\" + path;
		File file = new File(fileName);
		int length = (int) file.length();
		
		byte[] datas = new byte[length];
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(file, "r");
			randomAccessFile.read(datas, offset, size);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将字节数据写入文件，由读写对象、偏移地址和长度决定；
	 * @param randomAccessFile
	 * @param datas
	 * @param offset
	 * @param size
	 */
	public void writeInLocal(RandomAccessFile randomAccessFile
			, byte[] datas, int offset, int size) {
		try {
			randomAccessFile.write(datas, offset, size);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提供带有根路径的文件读写对象；
	 * @param name
	 * @return
	 */
	public RandomAccessFile getRandomAccessFile(String path) {
		String fileName = rootPath + "\\" + path;
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
			return randomAccessFile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addResource(SectionInfo sectionInfo) {
		systemMap.put(sectionInfo.toString(), sectionInfo);
	}

}
