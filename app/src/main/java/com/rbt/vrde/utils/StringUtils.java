package com.rbt.vrde.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static final int RANDOM_ALL = 1;
	public static final int RANDOM_CHARACTER = 2;
	public static final int RANDOM_DIGIT = 3;
	private static Random random = new Random();
	private static final StringBuffer D_BUFFER = new StringBuffer(com.rbt.vrde.utils.R.utils.Resource._0123456789_);
	private static final StringBuffer C_BUFFER = new StringBuffer(com.rbt.vrde.utils.R.utils.Resource._a__Z);

	private static final StringBuffer ALL_BUFFER = new StringBuffer(com.rbt.vrde.utils.R.utils.Resource._0__Z);

	public static boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return true;
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static final String byteArrayToHexString(byte[] data) {
		if ((data == null) || (data.length == 0)) {
			throw new IllegalArgumentException();
		}
		StringBuilder sb = new StringBuilder(data.length * 2);
		byte[] arrayOfByte = data;
		int j = data.length;
		for (int i = 0; i < j; i++) {
			byte b = arrayOfByte[i];
			int v = b & 0xFF;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	public static byte[] hexStringToByteArray(String s) {
		if (isBlank(s)) {
			throw new IllegalArgumentException();
		}
		s = s.trim();
		int len = s.length();
		byte[] d = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			d[(i / 2)] = ((byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16)));
		}
		return d;
	}

	public static String[] parseUrlForCmwap(String url) {
		if (isBlank(url)) {
			throw new IllegalArgumentException();
		}
		String[] ss = { "", "" };

		url = url.trim();
		int index = url.indexOf(com.rbt.vrde.utils.R.utils.Resource._http___);
		if (index >= 0) {
			url = url.substring(index + 7);
		}
		index = url.indexOf(com.rbt.vrde.utils.R.utils.Resource.___);
		if (index > 0) {
			ss[0] = url.substring(0, index);
			ss[1] = url.substring(index);
		} else {
			ss[0] = url;
			ss[1] = com.rbt.vrde.utils.R.utils.Resource.___;
		}
		/*System.out.println("BaseLib"
				+ "StringUtils.parseUrlForCmwap() => domain:" + ss[0]
				+ " | path:" + ss[1]);*/

		return ss;
	}

	public static String getFileNameFromUrl(String url) {
		if (isBlank(url)) {
			throw new IllegalArgumentException();
		}
		String filename = null;
		url = url.trim();
		int index = url.lastIndexOf(com.rbt.vrde.utils.R.utils.Resource.__q__);
		if (index > 1) {
			url = url.substring(0, index);
		}
		index = url.lastIndexOf(com.rbt.vrde.utils.R.utils.Resource.___);
		if (index >= 0)
			filename = url.substring(index + 1);
		else {
			filename = url;
		}
		return filename;
	}

	public static String removeUrlParams(String url) {
		if (isBlank(url)) {
			throw new IllegalArgumentException();
		}

		int index = url.indexOf(com.rbt.vrde.utils.R.utils.Resource.__q__);
		if (index >= 0) {
			return url.substring(0, index);
		}
		return url;
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String getRandomString(int length, int type) {
		StringBuffer sb = new StringBuffer();
		int range = 0;
		if (type == 1)
			range = ALL_BUFFER.length();
		else if (type == 2)
			range = C_BUFFER.length();
		else if (type == 3) {
			range = D_BUFFER.length();
		}

		for (int i = 0; i < length; i++) {
			if (type == 1)
				sb.append(ALL_BUFFER.charAt(random.nextInt(range)));
			else if (type == 2)
				sb.append(C_BUFFER.charAt(random.nextInt(range)));
			else if (type == 3) {
				sb.append(D_BUFFER.charAt(random.nextInt(range)));
			}
		}

		return sb.toString();
	}

	public static String encodeUTF8(String str) {
		if (str == null)
			return "";
		if ("".equals(str))
			return "";
		try {
			return URLEncoder.encode(str, com.rbt.vrde.utils.R.utils.Resource._UTF_8);
		} catch (UnsupportedEncodingException e) {
			//System.err.println("BaseLib" + e.getMessage());
		}
		return "";
	}

	public static String getFileSuffix(String fileName) {
		if (isBlank(fileName)) {
			return "";
		}

		int index = fileName.lastIndexOf(".");
		if (index >= 0) {
			return fileName.substring(index + 1);
		}
		return "";
	}

	public static int[] convertVersion(String vn) {

		String str[] = vn.split(com.rbt.vrde.utils.R.utils.Resource._split_dot_);
		/*int[] va = new int[3];
		for(int i = 0; i < str.length; i++) {
			if(i <= 3) {
				va[i] = String2Int(str[i]);
			}else{
				break;
			}
		}*/
		int[] va = new int[3];
		for (int i = 0; i < str.length; i++) {
			try {
				if (i < 3) {
					va[i] = Integer.parseInt(str[i]);
				} else {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return va;
	}

	private static int String2Int(String str){

		List<Character> lc = new ArrayList<Character>();
		for (int i = 0; i < str.toCharArray().length; i++) {
			char c = str.charAt(i);
			if (c >= 48 && c < 57) {
				lc.add(c);
			} else {
				break;
			}
		}

		char[] cc = new char[lc.size()];
		int i = 0;
		for (char c : lc) {
			cc[i] = c;
			i++;
		}

		return Integer.parseInt(new String(cc));
	}

	public static boolean isMessyCode(String strName) {
		if (isBlank(strName))
			return false;
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = 0 ;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
				chLength++;
			}
		}
		float result = count / chLength ;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
}
