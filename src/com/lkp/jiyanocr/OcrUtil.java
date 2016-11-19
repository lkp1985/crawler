/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.jiyanocr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.solr.util.IOUtils;

/**
 * <p>湖南上海验证码识别<p>
 *
 * create  2016年2月23日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class OcrUtil { 
	private static Map<BufferedImage, String> trainMap1 = null;  
	private static Map<BufferedImage, String> trainMap2 = null;  
    private static int index = 0;  
    static String imagePath = "D:/workspace_lkp/crawler_task/ocr/company/jiyan/";
    public static int isBlack(int colorInt) {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {  
            return 1;  
        }  
        return 0;  
    }  
  
    public static int isWhite(int colorInt) {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() > 500 && color.getRed() + color.getGreen() + color.getBlue() < 650) {  
            return 1;  
        }  
        return 0;  
    }  
  
    public static int getStartx(BufferedImage img){
    	for(int i=0;i<img.getWidth();i++){
    		for(int j=0;j<img.getHeight();j++){
    			if(xysizeIsWhite(img,i,j)){
    				return i;
    			}
    		}
    	}
    	return 0;
    }
    public static boolean xysizeIsWhite(BufferedImage img,int x,int y){
    	try{
    		int tab = 10;
    		for(int i=x;i<x+tab;i++){
    			for(int j=y;j<y+tab;j++){
    				int col = img.getRGB(i, j);
    				Color color = new Color(col);
    				if(color.getRed() + color.getGreen() + color.getBlue() < 500){
    					return false;
    				}
    			}
    		}
    		return true;
    	}catch(Exception e){
    		return false;
    	}
    }
    public static int getColorBright(int colorInt) {  
        Color color = new Color(colorInt);  
        return color.getRed() + color.getGreen() + color.getBlue();  
  
    }  
  
    public static int isBlackOrWhite(int colorInt) {  
        if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {  
            return 1;  
        }  
        return 0;  
    }  
  
    public static BufferedImage removeBackgroud(String picFile)  
            throws Exception {  
        BufferedImage img = ImageIO.read(new File(picFile));  
        int width = img.getWidth();  
        int height = img.getHeight();  
        for (int x = 0; x < width; ++x) {  
            for (int y = 0; y < height; ++y) {  
                if (isWhite(img.getRGB(x, y)) == 1) {  
                    img.setRGB(x, y, Color.WHITE.getRGB());  
                } else {  
                    img.setRGB(x, y, Color.BLACK.getRGB());  
                }  
            }  
        }  
        return img;  
    }  
    /**
     * 去除零散的点 ,如果一个黑点周围的白点达到8个，则表明是噪点移除
     * @param image
     * @return
     */
    public static BufferedImage removeNoisePoint(BufferedImage img){
    	 int width = img.getWidth();  
         int height = img.getHeight();  
         for (int x = 0; x < width; ++x) {  
             for (int y = 0; y < height; ++y) {  
                 if (isWhite(img.getRGB(x, y)) == 0) {  //黑色则置为黑
                	 img.setRGB(x, y, Color.black.getRGB());  
                 }else{
                	 img.setRGB(x, y, Color.white.getRGB()); 
                 }
             }  
         }  
         return img;  
    }
    
   
    public static int getRoundColorNum(BufferedImage img, int x, int y,
			int colorNum, boolean less) {
		int num = 0;
		if (matchColor(img, x - 1, y - 1, colorNum, less)) {
			num++;
		}
		if (matchColor(img, x - 1, y, colorNum, less)) {
			num++;
		}
		if (matchColor(img, x - 1, y + 1, colorNum, less)) {
			num++;
		}
		if (matchColor(img, x, y - 1, colorNum, less)) {
			num++;
		}
		if (matchColor(img, x, y + 1, colorNum, less)) {
			num++;
		}
		if (matchColor(img, x + 1, y - 1, colorNum, less)) {
			num++;
		}
		if (matchColor(img, x + 1, y, colorNum, less)) {
			num++;
		}
		if (matchColor(img, x + 1, y + 1, colorNum, less)) {
			num++;
		}
		return num;
	}
    public static boolean matchColor(BufferedImage img, int x, int y,
			int colorNum, boolean less) {
		try {
			Color color = new Color(img.getRGB(x, y));
			if (less) {
				if (color.getRed() + color.getGreen() + color.getBlue() < colorNum) {
					return true;
				}
			} else {
				if (color.getRed() + color.getGreen() + color.getBlue() > colorNum) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}
    
    public static BufferedImage removeBackgroud2(String picFile)  
            throws Exception {  
        BufferedImage img = ImageIO.read(new File(picFile));  
        int width = img.getWidth();  
        int height = img.getHeight();  
        for (int x = 1; x < width - 1; ++x) {  
            for (int y = 1; y < height - 1; ++y) {  
                if (getColorBright(img.getRGB(x, y)) < 100) {  
                    if (isBlackOrWhite(img.getRGB(x - 1, y))  
                            + isBlackOrWhite(img.getRGB(x + 1, y))  
                            + isBlackOrWhite(img.getRGB(x, y - 1))  
                            + isBlackOrWhite(img.getRGB(x, y + 1)) == 4) {  
                        img.setRGB(x, y, Color.WHITE.getRGB());  
                    }  
                }  
            }  
        }  
        for (int x = 1; x < width - 1; ++x) {  
            for (int y = 1; y < height - 1; ++y) {  
                if (getColorBright(img.getRGB(x, y)) < 100) {  
                    if (isBlackOrWhite(img.getRGB(x - 1, y))  
                            + isBlackOrWhite(img.getRGB(x + 1, y))  
                            + isBlackOrWhite(img.getRGB(x, y - 1))  
                            + isBlackOrWhite(img.getRGB(x, y + 1)) == 4) {  
                        img.setRGB(x, y, Color.WHITE.getRGB());  
                    }  
                }  
            }  
        }  
        img = img.getSubimage(1, 1, img.getWidth() - 2, img.getHeight() - 2);  
        return img;  
    }  
  
    public static BufferedImage removeBlank(BufferedImage img) throws Exception {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        for(int i=0;i<width;i++){
        	for(int j=0;j<height;j++){
        		if(isBlack(img.getRGB(i, j))==1){
        			try{
        				boolean flag = true;
        				for(int k = i+1;k<i+40;k++){
        					if(isBlack(img.getRGB(k, j))==0){
        						flag = false;
        						break;
        					}
        				}
        				if(flag){
        					for(int k = i;k<i+40;k++){
        						img.setRGB(k,j, Color.WHITE.getRGB());
            				}
        				}
        			}catch(Exception e){
        				
        			}
        		}
        	}
        }
        return img;
    }  
  
    public static List<BufferedImage> splitImage(BufferedImage img)  
            throws Exception {  
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();  
        int width = img.getWidth();  
        int height = img.getHeight();  
        List<Integer> weightlist = new ArrayList<Integer>();  
        for (int x = 0; x < width; ++x) {  
            int count = 0;  
            for (int y = 0; y < height; ++y) {  
                if (isBlack(img.getRGB(x, y)) == 1) {  
                    count++;  
                }  
            }  
            weightlist.add(count);  
        }  
        for (int i = 0; i < weightlist.size(); i++) {  
            int length = 0;  
            while (i < weightlist.size() && weightlist.get(i) > 0) {  
                i++;  
                length++;  
            }  
            if (length > 18) {  
                subImgs.add(removeBlank(img.getSubimage(i - length, 0,  
                        length / 2, height)));  
                subImgs.add(removeBlank(img.getSubimage(i - length / 2, 0,  
                        length / 2, height)));  
            } else if (length > 5) {  
                subImgs.add(removeBlank(img.getSubimage(i - length, 0, length,  
                        height)));  
            }  
        }  
  
        return subImgs;  
    }  
  
    public static Map<BufferedImage, String> loadTrainData1(String path) throws Exception {  
        if (trainMap1 == null) {  
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();  
            File dir = new File(path);  
            File[] files = dir.listFiles();  
            for (File file : files) {  
                map.put(ImageIO.read(file), file.getName().charAt(0) + "");  
            }  
            trainMap1 = map;  
        }  
        return trainMap1;  
    }  
    public static Map<BufferedImage, String> loadTrainData2(String path) throws Exception {  
        if (trainMap2 == null) {  
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();  
            File dir = new File(path);  
            File[] files = dir.listFiles();  
            for (File file : files) {  
                map.put(ImageIO.read(file), file.getName().charAt(0) + "");  
            }  
            trainMap2 = map;  
        }  
        return trainMap2;  
    }  
    public static int getDistance(BufferedImage img, BufferedImage sample) {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int count = 0;  
        int widthmin = width < sample.getWidth() ? width : sample.getWidth();  
        int heightmin = height < sample.getHeight() ? height : sample  
                .getHeight();  
        for (int x = 0; x < widthmin; ++x) {  
            for (int y = 0; y < heightmin; ++y) {  
                if (isWhite(img.getRGB(x, y)) != isWhite(sample.getRGB(x, y))) {  
                    count++;  
                }  
            }  
        }  
        return count;  
    }  
  
    public static boolean isNotEight(BufferedImage img) {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int minCount = width;  
        for (int y = height / 2 - 2; y < height / 2 + 2; ++y) {  
            int count = 0;  
            for (int x = 0; x < width / 2 + 2; ++x) {  
                if (isBlack(img.getRGB(x, y)) == 1) {  
                    count++;  
                }  
            }  
            minCount = Math.min(count, minCount);  
        }  
        return minCount < 2;  
    }  
  
    public static boolean isNotThree(BufferedImage img) {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int minCount = width;  
        for (int y = height / 2 - 3; y < height / 2 + 3; ++y) {  
            int count = 0;  
            for (int x = 0; x < width / 2 + 1; ++x) {  
                if (isBlack(img.getRGB(x, y)) == 1) {  
                    count++;  
                }  
            }  
            minCount = Math.min(count, minCount);  
        }  
        return minCount > 0;  
    }  
  
    public static boolean isNotFive(BufferedImage img) {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int minCount = width;  
        for (int y = 0; y < height / 3; ++y) {  
            int count = 0;  
            for (int x = width * 2 / 3; x < width; ++x) {  
                if (isBlack(img.getRGB(x, y)) == 1) {  
                    count++;  
                }  
            }  
            minCount = Math.min(count, minCount);  
        }  
        return minCount > 0;  
    }  
  
    public static String getSingleCharOcr(BufferedImage img,  
            Map<BufferedImage, String> map) throws Exception {  
        String result = "#";  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int min = width * height;  
        boolean bNotEight = isNotEight(img);  
        boolean bNotThree = isNotThree(img);  
        boolean bNotFive = isNotFive(img);  
        for (BufferedImage bi : map.keySet()) {  
//            if (bNotThree && map.get(bi).startsWith("3"))  
//                continue;  
//            if (bNotEight && map.get(bi).startsWith("8"))  
//                continue;  
//            if (bNotFive && map.get(bi).startsWith("5"))  
//                continue;  
            double count1 = getBlackCount(img);  
            double count2 = getBlackCount(bi);  
            if (Math.abs(count1 - count2) / Math.max(count1, count2) > 0.25)  
                continue;  
            int count = 0;  
            if (width < bi.getWidth() && height < bi.getHeight()) {  
                for (int m = 0; m <= bi.getWidth() - width; m++) {  
                    for (int n = 0; n <= bi.getHeight() - height; n++) {  
                        Label1: for (int x = m; x < m + width; ++x) {  
                            for (int y = n; y < n + height; ++y) {  
                                if (isWhite(img.getRGB(x - m, y - n)) != isWhite(bi  
                                        .getRGB(x, y))) {  
                                    count++;  
                                    if (count >= min)  
                                        break Label1;  
                                }  
                            }  
                        }  
                    }  
                }  
            } else {  
                int widthmin = width < bi.getWidth() ? width : bi.getWidth();  
                int heightmin = height < bi.getHeight() ? height : bi  
                        .getHeight();  
                Label1: for (int x = 0; x < widthmin; ++x) {  
                    for (int y = 0; y < heightmin; ++y) {  
                        if (isWhite(img.getRGB(x, y)) != isWhite(bi  
                                .getRGB(x, y))) {  
                            count++;  
                            if (count >= min)  
                                break Label1;  
                        }  
                    }  
                }  
            }  
            if (count < min) {  
                min = count;  
                result = map.get(bi);  
            }  
        }  
        return result;  
    }  
  
    public static String getAllOcr(String file,String name) throws Exception {  
        BufferedImage img =  ImageIO.read(new File(file));  
        		//removeBackgroud(file);  
        img = removeNoisePoint(img); 
        drawTempImage(img, "2", img.getWidth(), img.getHeight());
        int x = getStartx(img);
        System.out.println("file.name="+name+";x="+x);
        
        //img = removeBlank(img); 
        drawTempImage(img, "tt", img.getWidth(), img.getHeight());
         
        return "";
    }  
  

    public static int getBlackCount(BufferedImage img) {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int count = 0;  
        for (int x = 0; x < width; ++x) {  
            for (int y = 0; y < height; ++y) {  
                if (isBlack(img.getRGB(x, y)) == 1) {  
                    count++;  
                }  
            }  
        }  
        return count;  
    }  
  
    /**
     * 纵向切割
     * 
     * 获取某个区域内最多的颜色及数目
     * @param img
     */
    public static List<BufferedImage> splitImageHorizontal(BufferedImage img){
    	List<BufferedImage> list = new ArrayList<BufferedImage>();
    	int splitwidth = 24;//每个汉字约24个像素
    	int splitheight = img.getHeight();
    	Map<Integer,Integer> colorNumMap = new HashMap<Integer,Integer>();
    	int maxnum = 0;
    	int maxrgb = 0;
    	Map<Integer,Integer> splitPositoinMap  = new HashMap<Integer,Integer>();
    	for(int k=0;k<img.getWidth()-splitwidth;k++){
    		for(int i=k; i<k+splitwidth; i++){
        		for(int j=0;j<splitheight; j++){
        			int rgb = img.getRGB(i, j);
        			int rgbnum  = getColorBright(rgb); 
        			if(rgbnum>600){//白点不选取
        				continue;
        			}
        			if(colorNumMap.containsKey(rgb)){
        				int num = colorNumMap.get(rgb);
            			colorNumMap.put(rgb, ++num);
        			}else{
            			colorNumMap.put(rgb, 1);
        			}
        			
        		}
        	}
    		int _maxnum = getMaxNum(colorNumMap);
    		int _maxrgb = getMaxRgb(colorNumMap);
    		//System.out.println("max rgb="+new Color(_maxrgb));
    		 
    		if((maxnum > 0 && maxnum>220) || _maxnum>220){//有足够多的点
    			if(_maxnum>maxnum){
    				maxnum = _maxnum;
        			maxrgb =  _maxrgb;
        			colorNumMap.clear();
        		}else{//表明已经找到分割的区域
        			//记下颜色与位置
        			splitPositoinMap.put(k, maxrgb);
        			BufferedImage b1 = img.getSubimage(k, 0, splitwidth,
        					splitheight);
        			//并将该子图颜色差别大的置为白色
        			int maxrgbround = getColorBright(_maxrgb);
        			for(int ii=0;ii<b1.getWidth();ii++){
        				for(int jj=0;jj<b1.getHeight();jj++){
        					int colorRound = getColorBright(b1.getRGB(ii, jj));
        					if(Math.abs(maxrgbround-colorRound)>100){
        						b1.setRGB(ii, jj, Color.white.getRGB());
        					}else{
        						b1.setRGB(ii, jj, maxrgb);
        					}
        				}
        			}
        			list.add(b1);
        			drawTempImage(b1, k + "_test", splitwidth, splitheight);
        			k = k+splitwidth;//从下一个分割区开始
        			maxnum = 0;
        			colorNumMap.clear();
        		}
    		}else{
    			if(_maxnum>maxnum){
    				maxnum = _maxnum;
        			maxrgb =  _maxrgb;
        			colorNumMap.clear();
        		}
    		}
    	}
    	//System.out.println("splitPositoinMap="+splitPositoinMap);
    	return list;
    }
    
    /**
     * 纵向切割
     * @param img
     * @return
     */
    public static BufferedImage splitImageVertical(BufferedImage img){
    	int splitwidth = img.getWidth();//每个汉字约24个像素
    	int splitheight = 24;
    	Map<Integer,Integer> colorNumMap = new HashMap<Integer,Integer>();
    	int maxnum = 0;
    	int maxrgb = 0;
    	Map<Integer,Integer> splitPositoinMap  = new HashMap<Integer,Integer>();
    	for(int k=0;k<img.getHeight()-splitheight;k++){
    		for(int j=k;j<k+splitheight;j++){
    			for(int i=0;i<splitwidth;i++){ 
        			int rgb = img.getRGB(i,j);
        			int rgbnum  = getColorBright(rgb); 
        			if(rgbnum>600){//白点不选取
        				continue;
        			}
        			if(colorNumMap.containsKey(rgb)){
        				int num = colorNumMap.get(rgb);
            			colorNumMap.put(rgb, ++num);
        			}else{
            			colorNumMap.put(rgb, 1);
        			} 
    			}
    		}
    		 
    		int _maxnum = getMaxNum(colorNumMap);
    		int _maxrgb = getMaxRgb(colorNumMap);
    		//System.out.println("max rgb="+new Color(_maxrgb));
    		if((maxnum > 0 && maxnum>192) || _maxnum>192){//有足够多的点
    			if(_maxnum>=maxnum){
    				maxnum = _maxnum;
        			maxrgb =  _maxrgb;
        			colorNumMap.clear();
        		}else{//表明已经找到分割的区域
        			//记下颜色与位置
        			splitPositoinMap.put(k, maxrgb);
        			BufferedImage b1 = img.getSubimage(0, k, splitwidth,
        					splitheight);
        			//并将该子图颜色差别大的置为白色
        			int maxrgbround = getColorBright(_maxrgb);
        			for(int ii=0;ii<b1.getWidth();ii++){
        				for(int jj=0;jj<b1.getHeight();jj++){
        					int colorRound = getColorBright(b1.getRGB(ii, jj));
        					if(Math.abs(maxrgbround-colorRound)>100){
        						b1.setRGB(ii, jj, Color.white.getRGB());
        					}else{
        						b1.setRGB(ii, jj, Color.black.getRGB());
        					}
        				}
        			}
        			
        			drawTempImage(b1, k + "_test", splitwidth, splitheight);
        			k = k+splitwidth;//从下一个分割区开始
        			maxnum = 0;
        			colorNumMap.clear();
        			return b1;
        		}
    		}else{
    			if(_maxnum>maxnum){
    				maxnum = _maxnum;
        			maxrgb =  _maxrgb;
        			colorNumMap.clear();
        		}
    		}
    	} 
    	return null;
    }
    
    /**
     * 取相似最多的点
     * @param colorNumMap
     * @return
     */
    public static int getMaxNum(Map<Integer,Integer> colorNumMap){
    	int max = 0;
    	int key = 0;
    
    	for(int rgb : colorNumMap.keySet()){
    		int num = colorNumMap.get(rgb);//取该rgb的个数
    		for(int rgb2: colorNumMap.keySet()){
    			if(rgb2==rgb){
    				continue;
    			}
    			int colorbright1 = getColorBright(rgb);
    			int colorbright2 = getColorBright(rgb2);
    			if(Math.abs(colorbright2-colorbright1)<100){//绝对值小于100，则表明是相似值
    				num = num+1;//该rgb的个数加1
    				colorNumMap.put(rgb, num);
    			}
    		}
    	}
    	for(int rgb : colorNumMap.keySet()){
    		int num = colorNumMap.get(rgb);//取该rgb的个数
    		if(num >max){
    			max = num;
    			key = rgb;
    		}
    	} 
    	return max;
    }
    public static int getMaxRgb(Map<Integer,Integer> colorNumMap){
    	int max = 0;
    	int key = 0;
    
    	for(int rgb : colorNumMap.keySet()){
    		int num = colorNumMap.get(rgb);//取该rgb的个数
    		for(int rgb2: colorNumMap.keySet()){
    			if(rgb2==rgb){
    				continue;
    			}
    			int colorbright1 = getColorBright(rgb);
    			int colorbright2 = getColorBright(rgb2);
    			if(Math.abs(colorbright2-colorbright1)<50){//绝对值小于50，则表明是相似值
    				num = num+1;//该rgb的个数加1
    				colorNumMap.put(rgb, num);
    			}
    		}
    	}
    	for(int rgb : colorNumMap.keySet()){
    		int num = colorNumMap.get(rgb);//取该rgb的个数
    		if(num >max){
    			max = num;
    			key = rgb;
    		}
    	} 
    	return key;
    }
    public static void drawTempImage(BufferedImage img, String name, int width,
			int height) {
		BufferedImage inputbig = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		inputbig.getGraphics().drawImage(img, 0, 0, width, height, null); // 画图
		try {
			ImageIO.write(inputbig, "jpg", new File(imagePath+"temp/" + name + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public static void drawTempImage2(BufferedImage img, String name, int width,
			int height) {
		BufferedImage inputbig = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_BGR);
		inputbig.getGraphics().drawImage(img, 0, 0, width, height, null); // 画图
		try {
			ImageIO.write(inputbig, "jpg", new File(imagePath+"test/" + name + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public static void downloadImage() {  
//        HttpClient httpClient = new HttpClient();  
//        GetMethod getMethod = new GetMethod(  
//                "http://reg.keepc.com/getcode/getCode.php");  
//        for (int i = 0; i < 30; i++) {  
//  
//            try {  
//                // 执行getMethod  
//                int statusCode = httpClient.executeMethod(getMethod);  
//                if (statusCode != HttpStatus.SC_OK) {  
//                    System.err.println("Method failed: "  
//                            + getMethod.getStatusLine());  
//                }  
//                // 读取内容  
//                String picName = "img4//" + i + ".jpg";  
//                InputStream inputStream = getMethod.getResponseBodyAsStream();  
//                OutputStream outStream = new FileOutputStream(picName);  
//                IOUtils.copy(inputStream, outStream);  
//                outStream.close();  
//                System.out.println(i + "OK!");  
//            } catch (Exception e) {  
//                e.printStackTrace();  
//            } finally {  
//                // 释放连接  
//                getMethod.releaseConnection();  
//            }  
//        }  
    }  
  
    public static void trainData() throws Exception {  
        File dir = new File("temp4");  
        File[] files = dir.listFiles();  
        for (File file : files) {  
            BufferedImage img = removeBackgroud("temp4//" + file.getName());  
            List<BufferedImage> listImg = splitImage(img);  
            if (listImg.size() == 4) {  
                for (int j = 0; j < listImg.size(); ++j) {  
                    ImageIO.write(listImg.get(j), "JPG", new File("train4//"  
                            + file.getName().charAt(j) + "-" + (index++)  
                            + ".jpg"));  
                }  
            }  
        }  
    }  
  
    /** 
     * @param args 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {  
        // downloadImage();  
        // trainData();  
    	File f = new File("D:/workspace_lkp/crawler_task/ocr/company/jiyan/test/");
    	for (String fname : f.list()){
    		if(fname.length()<=14){
    			
    			System.out.println("fname="+fname);
    			String text = getAllOcr("D:/workspace_lkp/crawler_task/ocr/company/jiyan/test/" + fname,fname);  
    			
    		}
    	}
        for (int i = 1; i <= 7; ++i) {  
        	//D:\workspace_lkp\crawler_task\ocr\company\jiyan\sample
              
        }  
    } 
}
