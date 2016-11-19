/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */
package com.lkp.companycredit.ocr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * <p>安徽验证码识别<p>
 *
 * create  2016年2月23日<br>
 * @author  lkp<br> 
 * @version 1.0
 * @since   1.0
 */
public class OcrUtil_anhui { 
	private static Map<BufferedImage, String> trainMap1 = null;  
	private static Map<BufferedImage, String> trainMap2 = null;  
    private static int index = 0;  
    static String imagePath = "D:/workspace_lkp/crawler_task/ocr/company/anhui/";
    public static int isBlack(int colorInt) {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {  
            return 1;  
        }  
        return 0;  
    }  
  
    public static int isWhite(int colorInt) {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() > 500) {  
            return 1;  
        }  
        return 0;  
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
  
    public static BufferedImage removeBackgroud(BufferedImage img)  
            throws Exception {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        for (int x = 0; x < width; ++x) {  
            for (int y = 0; y < height; ++y) {  
                if (isWhite(img.getRGB(x, y)) == 1) {  
                    img.setRGB(x, y, Color.WHITE.getRGB());  
                } else {  
                    //img.setRGB(x, y, Color.BLACK.getRGB());  
                }  
            }  
        }  
        return img;  
    }  
    /**
     * 二值化，只保留黑和白
     * @param img
     * @return
     * @throws Exception
     */
    public static BufferedImage setToBlackAndWhite(BufferedImage img)  
            throws Exception {  
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
     * 如果是白点，置为黑，如果是黑点，置为白
     * @param img
     * @return
     * @throws Exception
     */
    public static BufferedImage revertBlackAndWhite(BufferedImage img)  
            throws Exception {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        for (int x = 0; x < width; ++x) {  
            for (int y = 0; y < height; ++y) {  
                if (isWhite(img.getRGB(x, y)) == 1) {  
                    img.setRGB(x, y, Color.BLACK.getRGB());  
                } else {  
                     img.setRGB(x, y, Color.WHITE.getRGB());  
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
    public static BufferedImage removeBlank(BufferedImage img){
    	 int width = img.getWidth();  
         int height = img.getHeight();  
         for (int x = 0; x < width; ++x) {  
             for (int y = 0; y < height; ++y) {  //每个都大于100 小于130
            	 if(new Color(img.getRGB(x, y)).getBlue()>100 && new Color(img.getRGB(x, y)).getBlue()<130 && 
            			 new Color(img.getRGB(x, y)).getBlue()>100 && new Color(img.getRGB(x, y)).getRed()<130 && 
            			 new Color(img.getRGB(x, y)).getBlue()>100 && new Color(img.getRGB(x, y)).getGreen()<130 )
            	 img.setRGB(x, y, Color.white.getRGB());
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
  
    public static BufferedImage removeNoise(BufferedImage img) throws Exception {  
    	int width = img.getWidth();  
        int height = img.getHeight();  
        for (int x = 0; x < width; ++x) {  
            for (int y = 0; y < height; ++y) {  
                if (isWhite(img.getRGB(x, y)) == 0) {  //黑色
               	 int whitenum = getRoundColorNum(img, x, y, 600, false);
               	 if(whitenum>=7){
               		 img.setRGB(x, y, Color.WHITE.getRGB());  
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
  
    public static String getAllOcr(String file,int i) throws Exception {  
        BufferedImage img =  ImageIO.read(new File(file));  
        		//removeBackgroud(file);  
      //  img = setToBlack(img);
        
//        img = revertBlackAndWhite(img);
//        drawTempImage(img, "black", img.getWidth(), img.getHeight());
//        img = Rotate(img, 30);
//        img = revertBlackAndWhite(img);
//        drawTempImage(img, "whiteToRotate", img.getWidth(), img.getHeight());
         img = removeBlank(img); 
       //  drawTempImage(img, "noise1"+i, img.getWidth(), img.getHeight());
         img = removeBackgroud(img);
       //  drawTempImage(img, "noise2"+i, img.getWidth(), img.getHeight());
         img = removeNoise(img);
      //   drawTempImage(img, "noise3"+i, img.getWidth(), img.getHeight());
         img = setToBlackAndWhite(img);
         drawTempImage(img, "noise"+i, img.getWidth(), img.getHeight());
       
       
        //img = removeBlank(img); 
        
      //  List<BufferedImage> listImg = new ArrayList<BufferedImage>();
      //  BufferedImage split1 = splitImageHorizontal(img); 
      //  BufferedImage split2 = splitImageHorizontalRight(img); 
//        BufferedImage split2 = splitImageHorizontal(split1); 
//        BufferedImage split3 = splitImageHorizontal(split2); 
//        BufferedImage split4 = splitImageHorizontal(split3); 
//        BufferedImage split5 = splitImageHorizontal(split4);
//        for(BufferedImage splitimg : splitHorList){
//        	BufferedImage splitVerimg = splitImageVertical(splitimg);
//        	listImg.add(splitVerimg);
//        	
//        } 
//        Map<BufferedImage, String> map = loadTrainData1(imagePath+"train2");  //数字
//        Map<BufferedImage, String> map2 = loadTrainData2(imagePath+"train1");  //运算符
//        String result = "";  
//        
//        
//        BufferedImage bi1 = listImg.get(0);
//        BufferedImage bi2 = listImg.get(1);
//        BufferedImage bi3 = listImg.get(2);
//        
//        drawTempImage2(bi1, "1", bi1.getWidth(), bi1.getHeight());
//        result += getSingleCharOcr(bi1, map);  
//        drawTempImage2(bi2, "2", bi2.getWidth(), bi2.getHeight());
//        result += getSingleCharOcr(bi2, map2);  
//        drawTempImage2(bi3, "3", bi2.getWidth(), bi3.getHeight());
//        result += getSingleCharOcr(bi3, map);  
        
       
   //     System.out.println(result);  
      //  ImageIO.write(img, "JPG", new File("result4//" + result + ".jpg"));  
        return null;// result;  
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
    public static BufferedImage splitImageHorizontal(BufferedImage img){
    	List<BufferedImage> list = new ArrayList<BufferedImage>();
    	BufferedImage copyImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_BGR);
    	for(int i=0;i<img.getWidth();i++){
    		for(int j=0;j<img.getHeight();j++){
    			copyImg.setRGB(i, j, img.getRGB(i, j));
    		}
    	}
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
        			 
        			if(rgbnum>600){//白点不选取,以及已经选取过的最大值不选
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
        						copyImg.setRGB(ii+k, jj, Color.white.getRGB());
        					}
        				}
        			}
        			list.add(b1);
        			drawTempImage(b1, k + "_test", splitwidth, splitheight);
        			drawTempImage(copyImg, k + "_test2", copyImg.getWidth(), copyImg.getHeight());
        			return copyImg;
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
    	return null;
    }
    /**
     * 从右边纵向切割
     * 
     * 获取某个区域内最多的颜色及数目
     * @param img
     */
    public static BufferedImage splitImageHorizontalRight(BufferedImage img){
    	List<BufferedImage> list = new ArrayList<BufferedImage>();
    	BufferedImage copyImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_BGR);
    	for(int i=0;i<img.getWidth();i++){
    		for(int j=0;j<img.getHeight();j++){
    			copyImg.setRGB(i, j, img.getRGB(i, j));
    		}
    	}
    	System.out.println("width1="+img.getWidth());
    	//将img从中切断，然后从右边开始分割
    	if(img.getWidth()%2==0){
    		img = img.getSubimage(0,0,img.getWidth()/2, img.getHeight());
    	}else{
    		img = img.getSubimage(0,0,img.getWidth()/2+1, img.getHeight());
    	}
    	System.out.println("width2="+img.getWidth());
    	drawTempImage(img, "subimg", img.getWidth(), img.getHeight());
    	int splitwidth = 24;//每个汉字约24个像素
    	int splitheight = img.getHeight();
    	Map<Integer,Integer> colorNumMap = new HashMap<Integer,Integer>();
    	int maxnum = 0;
    	int maxrgb = 0;
    	Map<Integer,Integer> splitPositoinMap  = new HashMap<Integer,Integer>();
    	for(int k=img.getWidth()-1;k>img.getWidth()-splitwidth;k--){
    		for(int i=k; i>k-splitwidth; i--){
        		for(int j=0;j<splitheight; j++){
        			int rgb = img.getRGB(i, j);
        			int rgbnum  = getColorBright(rgb); 
        			 
        			if(rgbnum>600){//白点不选取,以及已经选取过的最大值不选
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
        			BufferedImage b1 = img.getSubimage(k-splitwidth, 0, splitwidth,
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
        						copyImg.setRGB(ii+k, jj, Color.white.getRGB());
        					}
        				}
        			}
        			list.add(b1);
        			drawTempImage(b1, k + "_test", splitwidth, splitheight);
        			drawTempImage(copyImg, k + "_test2", copyImg.getWidth(), copyImg.getHeight());
        			return copyImg;
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
    	return null;
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
    
    public static BufferedImage Rotate(Image src, int angel) {  
        int src_width = src.getWidth(null);  
        int src_height = src.getHeight(null);  
        // calculate the new image size  
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(  
                src_width, src_height)), angel);  
  
        BufferedImage res = null;  
        res = new BufferedImage(rect_des.width, rect_des.height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g2 = res.createGraphics();  
        
        g2.setBackground(Color.white);
        g2.setPaint(null);
        // transform  
        g2.translate((rect_des.width - src_width) / 2,  
                (rect_des.height - src_height) / 2);  
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);  
  
        g2.drawImage(src, null, null);  
        return res;  
    }  
    
    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {  
        // if angel is greater than 90 degree, we need to do some conversion  
        if (angel >= 90) {  
            if(angel / 90 % 2 == 1){  
                int temp = src.height;  
                src.height = src.width;  
                src.width = temp;  
            }  
            angel = angel % 90;  
        }  
  
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;  
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;  
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;  
        double angel_dalta_width = Math.atan((double) src.height / src.width);  
        double angel_dalta_height = Math.atan((double) src.width / src.height);  
  
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha  
                - angel_dalta_width));  
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha  
                - angel_dalta_height));  
        int des_width = src.width + len_dalta_width * 2;  
        int des_height = src.height + len_dalta_height * 2;  
        return new java.awt.Rectangle(new Dimension(des_width, des_height));  
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
//            BufferedImage img = removeBackgroud("temp4//" + file.getName());  
//            List<BufferedImage> listImg = splitImage(img);  
//            if (listImg.size() == 4) {  
//                for (int j = 0; j < listImg.size(); ++j) {  
//                    ImageIO.write(listImg.get(j), "JPG", new File("train4//"  
//                            + file.getName().charAt(j) + "-" + (index++)  
//                            + ".jpg"));  
//                }  
//            }  
        }  
    }  
  
    
    public static BufferedImage copyImage(BufferedImage img1){
    	BufferedImage copyImg = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_BGR);
    	for(int i=0;i<img1.getWidth();i++){
    		for(int j=0;j<img1.getHeight();j++){
    			copyImg.setRGB(i, j, img1.getRGB(i, j));
    		}
    	}
    	return copyImg;
    }
    /**
     * 比较im2中和img1最相似的字符，并返回起始位置x|y
     * @param img1
     * @param img2
     * @return
     */
    public static String getMaxMatch(BufferedImage img1,BufferedImage img2) throws Exception{
    	BufferedImage copyImg = copyImage(img1);
    	 
    	
    	//img1从270度到90度旋转
    	int rotate = 0;
    	String flag = "0|0|0";
		int max = 0;
    	for(int i=28;i<45;i++){
    		if(i<0){
    			rotate = i+360;
    		}else{
    			rotate = i;
    		} 
//        	img = Rotate(img, 330);
    		BufferedImage copyImg2  = copyImage(copyImg);
    		copyImg2=   revertBlackAndWhite(copyImg2);
    		img1 = Rotate(copyImg2, rotate);
           	img1 = revertBlackAndWhite(img1);
    	 	drawTempImage(img1, "rotate"+i, img1.getWidth(), img1.getHeight());
    		//System.out.println("after rototeimg1.width="+img1.getWidth()+";img1.height="+img1.getHeight());
    		for(int x=0;x<img2.getWidth();x++){
    			for(int y=0;y<img2.getHeight();y++){
    				int samenum = 0;
    				String matchflag = x+"|"+y+"|"+rotate;
    				//开始逐个比较
    				for(int j=0;j<img1.getWidth();j++){
    	    			if(j>=img2.getWidth()-x){
    	    				break;
    	    			}
    	    			for(int k=0;k<img1.getHeight();k++){
    	    				if(k>=img2.getHeight()-y){
    	    					break;
    	    				}
    	    				try{
    	    					if((isBlack(img1.getRGB(j, k)) ==1) &&  (isBlack(img2.getRGB(x+j, y+k))==1)){
        	    					samenum++;
        	    				}
    	    				}catch(Exception e){
    	    					System.out.println("x="+x+";y="+y+";j="+j+";k="+k);
    	    					break;
    	    				}
    	    				
    	    			}
    	    		}
    				if(samenum>max){
    					max = samenum;
    					flag = matchflag;
    					System.out.println("flag="+flag+";max="+max);
    				}
    			}
    		}
    		
    	}
    	return flag+"|"+max;
    }
    /** 
     * @param args 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {  
        // downloadImage();  
        // trainData();  
        for (int i =11; i <= 11; ++i) {  
          	//String text = getAllOcr(imagePath+"sample/" +i+ ".jpg",i);  
           BufferedImage img =  ImageIO.read(new File(imagePath+"split/" +5+ ".jpg"));  
        	img = revertBlackAndWhite(img);
        	img = Rotate(img, 28);
        	img = revertBlackAndWhite(img);
        	drawTempImage(img, "temp1", img.getWidth(), img.getHeight());
//        	drawTempImage(img, "temp2", img.getWidth(), img.getHeight());
           //String text = getAllOcr(imagePath+"sample/" +"4"+ ".jpg");  
         	BufferedImage img1 =  ImageIO.read(new File(imagePath+"split/"+5+".jpg"));
        	BufferedImage img2 =  ImageIO.read(new File(imagePath+"temp/noise7.jpg"));
        	System.out.println("getMaxMatch="+getMaxMatch(img1,img2));
            //System.out.println(i + ".jpg = " + text);  
        }  
    } 
}
