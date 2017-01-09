package com.github.utils.mycollect.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 把各种流转换成java对象
 * 
 * 1.toString :InputStream或者Reader 流转换成String
 * 
 * 2.copy 把输入流放到输出流
 * 
 * 3.readLines:把每一行放入list
 * 
 * 4.copyFile:把文件a拷贝到文件b
 * 
 */
public class IOUtils {
	private static final int EOF = -1;

	/**
	 * 把流转换成String
	 * 
	 * @param input
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String toString(InputStream input, String encoding)
			throws IOException {
		return ((null == encoding) ? toString(new InputStreamReader(input))
				: toString(new InputStreamReader(input, encoding)));
	}

	public static String toString(Reader reader) throws IOException {
		CharArrayWriter sw = new CharArrayWriter();
		copy(reader, sw);
		return sw.toString();
	}

	public static long copy(Reader input, Writer output) throws IOException {
		char[] buffer = new char[4096];
		long count = 0L;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static List<String> readLines(Reader input) throws IOException {
		BufferedReader reader = toBufferedReader(input);
		List list = new ArrayList();
		String line = null;
		while (true) {
			line = reader.readLine();
			if (null == line)
				break;
			list.add(line);
		}

		return list;
	}

	private static BufferedReader toBufferedReader(Reader reader) {
		return new BufferedReader(reader);
	}

	public static void copyFile(String source, String target)
			throws IOException {
		File sf = new File(source);
		if (!(sf.exists())) {
			throw new IllegalArgumentException("source file does not exist.");
		}
		File tf = new File(target);
		tf.getParentFile().mkdirs();
		if ((!(tf.exists())) && (!(tf.createNewFile()))) {
			throw new RuntimeException("failed to create target file.");
		}

		FileChannel sc = null;
		FileChannel tc = null;
		try {
			tc = new FileOutputStream(tf).getChannel();
			sc = new FileInputStream(sf).getChannel();
			sc.transferTo(0L, sc.size(), tc);
		} finally {
			if (null != sc) {
				sc.close();
			}
			if (null != tc)
				tc.close();
		}
	}
}
