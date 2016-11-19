package com.httpclient.utils;

import java.util.ArrayList;
import java.util.List;

public class test {
	
	public static String[] $SoGou0$={"6","b","t","f","2","z","l","5","w","h","q","i","s","e","c","p","m","u","9","8","y","k","j","r","x","n","-","0","3","4","d","1"
								,"a","o","7","v","g"};
	public static String[] $SoGou1$={"1","8","o","s","z","u","n","v","m","b","9","f","d","7","h","c","p","y","2","0","3","j","-","i","l","k","t","q","4","6","r",
								"a","w","5","e","x","g"};
	public static String[] $SoGou2$={"s","6","h","0","p","g","3","n","m","y","l","d","x","e","a","k","z","u","f","4","r","b","-","7","o","c","i","8","v","2","1",
							"9","q","w","t","j","5"};
	public static String[] $SoGou3$={"x","7","0","d","i","g","a","c","t","h","u","p","f","6","v","e","q","4","b","5","k","w","9","s","-","j","l","y","3","o","n",
							"z","m","2","1","r","8"};
	public static String[] $SoGou4$={"z","j","3","l","1","u","s","4","5","g","c","h","7","o","t","2","k","a","-","e","x","y","b","n","8","i","6","q","p","0","d",
							"r","v","m","w","f","9"};
	public static String[] $SoGou5$={"j","h","p","x","3","d","6","5","8","k","t","l","z","b","4","n","r","v","y","m","g","a","0","1","c","9","-","2","7","q","e",
							"w","u","s","f","o","i"};
	public static String[] $SoGou6$={"8","q","-","u","d","k","7","t","z","4","x","f","v","w","p","2","e","9","o","m","5","g","1","j","i","n","6","3","r","l","b",
							"h","y","c","a","s","0"};
	public static String[] $SoGou7$={"d","4","9","m","o","i","5","k","q","n","c","s","6","b","j","y","x","l","a","v","3","t","u","h","-","r","z","2","0","7","g",
							"p","8","f","1","w","e"};
	public static String[] $SoGou8$={"7","-","g","x","6","5","n","u","q","z","w","t","m","0","h","o","y","p","i","f","k","s","9","l","r","1","2","v","4","e","8",
							"c","b","a","d","j","3"};
	public static String[] $SoGou9$={"1","t","8","z","o","f","l","5","2","y","q","9","p","g","r","x","e","s","d","4","n","b","u","a","m","c","h","j","3","v","i",
							"0","-","w","7","k","6"};
	
	
	/**
	 * 把ASCII码或Unicode码转为字符 会忽略不可打印字符
	 */
	public static String fromCharCode(String[] chars) {

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			int code = Integer.parseInt(chars[i]);

			if (0x4E00 <= code && code <= 0x9FA5) {
				builder.append((char) Integer.parseInt((chars[i]), 16));
			} else if (code >= 32) {
				builder.append((char) code);
			}
		}

		return builder.toString();
	}
	public static void main(String[] args) {
		String str="33,102,117,110,99,116,105,111,110,40,110,41,123,100,111,99,117,109,101,110,116,46,99,111,111,107,105,101,61,39,116,111,107,101,110,61,51,100,98,49,56,56,100,50,52,53,50,55,52,55,99,52,97,56,102,51,50,56,48,56,54,53,51,55,53,52,98,56,59,112,97,116,104,61,47,59,39,59,110,46,119,116,102,61,102,117,110,99,116,105,111,110,40,41,123,114,101,116,117,114,110,39,51,50,44,48,44,51,48,44,55,44,48,44,48,44,48,44,51,48,44,51,50,44,49,56,44,49,57,44,49,56,44,50,57,44,49,56,44,52,44,50,56,44,49,44,51,44,49,51,44,51,50,44,50,57,44,50,55,44,50,55,44,50,57,44,51,44,50,57,44,50,55,44,49,52,44,51,50,44,51,48,44,51,48,44,50,56,39,125,125,40,119,105,110,100,111,119,41,59";	
		String script=fromCharCode(str.split(","));
		System.out.println("script:"+script);
		String token = script.substring(script.indexOf("token=") + "token=".length(), script.indexOf(";path="));
		System.out.println("解析token==" + token);

		String[] wtfFnReturn = script.substring(script.indexOf("return'") + "return'".length(), script.indexOf("'}}")).split(",");

		String[] $SoGou$ = { "j", "p", "7", "o", "3", "w", "0", "-", "2", "e", "9", "z", "f", "m", "b", "k", "u", "8", "g", "s", "a", "1", "d", "y",
				"r", "i", "h", "q", "l", "n", "v", "6", "5", "c", "x", "4", "t" };
		List<String[]> liststr=new ArrayList<String[]>();
		liststr.add($SoGou0$);
		liststr.add($SoGou1$);
		liststr.add($SoGou2$);
		liststr.add($SoGou3$);
		liststr.add($SoGou4$);
		liststr.add($SoGou5$);
		liststr.add($SoGou6$);
		liststr.add($SoGou7$);
		liststr.add($SoGou8$);
		liststr.add($SoGou9$);
		String key="24416401";
		if(key==null||key.equals(""))return;
		String index=key.codePointAt(0)+"";
		char[] indexChar= index.toCharArray();
		char index2=indexChar.length>1?indexChar[1]:indexChar[0];
		System.out.println("index:"+index2);
		$SoGou$=liststr.get(Integer.parseInt(String.valueOf(index2)));
		
		
		StringBuilder _utm = new StringBuilder();
		for (int i = 0; i < wtfFnReturn.length; i++) {
			_utm.append($SoGou$[Integer.parseInt(wtfFnReturn[i])]);
		}

		System.out.println("解析_utm===" + _utm);
	}

}
