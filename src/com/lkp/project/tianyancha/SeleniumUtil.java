package com.lkp.project.tianyancha;

import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumUtil {
	static WebDriver driver  = null;
	static String currentwin = null;
	static Set<String> read =new HashSet<String>();
	static WebElement btnEle = null;
	public static void init(){
		System.setProperty("webdriver.firefox.bin", "d:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		  //driver = new FirefoxDriver();
		 driver = new FirefoxDriver();
			driver.get("http://www.tianyancha.com/search");
			for(String winHandleBefore : driver.getWindowHandles()){
				read.add(winHandleBefore);
			}
			int waittime = 0;
			while(driver.findElements(By.className("input-search-new")).isEmpty()){
				try {
					System.out.println("等待打开浏览器");
					Thread.sleep(1000);
					if(waittime++>3){
						driver.close();
						new FirefoxDriver();
						driver.get("http://www.tianyancha.com/search");
						for(String winHandleBefore : driver.getWindowHandles()){
							read.add(winHandleBefore);
						}
						waittime=0;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("浏览器打开成功");
			currentwin = driver.getWindowHandle();
			btnEle = driver.findElements(By.className("input-search-new")).get(0);
	}
	/**
	 * 根据公司名称拿经营范围
	 * @param name
	 * @return
	 */
	public static String getJYScope(String name){
		System.out.println("in scope,name="+name+",size="+driver.getWindowHandles().size());
		String scope = "";
		try{
//			while(driver.getWindowHandles().size()>1){
//				Object[] objarr = driver.getWindowHandles().toArray();
//				WebDriver current = driver.switchTo().window(objarr[objarr.length-1].toString());
//				current.close();
//				 
//			}
			driver.switchTo().window(currentwin);
			driver.findElement(By.id("live-search")).clear();
			driver.findElement(By.id("live-search")).sendKeys(name);
			btnEle = driver.findElements(By.className("input-search-new")).get(0);
			btnEle.click();
			List<WebElement> rows = driver.findElements(By.className("query_name"));
			int watitime = 0;
			while(rows==null || rows.size() ==0){
				System.out.println("等待加载");
				
				Thread.sleep(1000);
				rows = driver.findElements(By.className("query_name"));
				if(watitime++ >3){
					watitime = 0;
					break;
				}
			}
			currentwin = driver.getWindowHandle();
			System.out.println("currentwin="+currentwin);
			watitime = 0;
			if(rows!=null && rows.size()>0){
				WebElement web = rows.get(0);
				int currentnum = driver.getWindowHandles().size();
				web.click();
				while(driver.getWindowHandles().size()==currentnum){
					System.out.println("等待加载1");
					Thread.sleep(1000);
					if(watitime++>=3){
						watitime = 0;
						break;
					}
				}
				
				for (String winHandle : driver.getWindowHandles()) {
					if(!read.contains(winHandle)){
						read.add(winHandle);
						WebDriver current = driver.switchTo().window(winHandle);
						List<WebElement>  webList = driver.findElements(By.className("break-word"));
						while(webList==null || webList.size()==0){
							System.out.println("等待加载2");
							Thread.sleep(1000);
							webList = driver.findElements(By.className("break-word"));
							if(watitime++>=3){
								watitime = 0;
								break;
							}
							webList = driver.findElements(By.className("break-word"));
						}
						if(webList!=null &&webList.size()>0){
							List<WebElement> scopeList = webList.get(0).findElements(By.className("ng-binding"));
							if(scopeList!=null &&scopeList.size()>0){
								System.out.println("url="+driver.getCurrentUrl()+"; 经营范围:"+scopeList.get(0).getText());
								scope = scopeList.get(0).getText();
								current.close();
								return scope;
							}
						}else{
							return "";
						}
						
					}
				}
			}
		}catch(Exception e){
			try{
				WebDriver temp = driver.switchTo().window(currentwin);
				temp.close();
				init();
			}catch(Exception ee){
				
			}
			
			e.printStackTrace();
		}
		return scope;
	}
	public static void main(String[] args) {

		try {
		//	System.out.println(Utils.dama2());
			// 如果火狐浏览器没有默认安装在C盘，需要制定其路径
			
			// System.setProperty("webdriver.chrome.driver", "C:/Program Files
			// (x86)/Google/Chrome/Application/chrome.exe");
			// C:\Program Files (x86)\Google\Chrome\Application
			
			driver.get("http://www.tianyancha.com/search?key=");
			// driver.get("http://xj.tianyancha.com/search/软件开发.json?&pn=2&category=19&type=scope");

		
			Thread.sleep(2000);
			WebElement txtbox = driver.findElement(By.id("live-search"));
			txtbox.sendKeys("游戏");
			// txtbox.sendKeys("Glen");
			//
			// driver.getTitle()
			WebElement btn = driver.findElements(By.className("search_button")).get(0);
			btn.click();
			Thread.sleep(2000);
			driver.manage().window().setSize(new Dimension(600, 3800)); //将浏览器的大小自定义为600*400
			 
			// System.out.println("btn="+ driver.getTitle());

			// for (Cookie ck : driver.manage().getCookies()) {
			// System.out.println(ck.getName() +";" + ck.getValue() + ";"+
			// ck.getDomain() + ";" + ck.getPath() + ";" + ck.getExpiry() + ";"
			// + ck.isSecure());
			// }
		 FileWriter fw = new FileWriter("D:\\data\\logs\\test.html");

			List<WebElement> rows = driver.findElements(By.className("query_name"));
			fw.write(driver.getPageSource());
			fw.flush();
			fw.close();
			System.out.println("rows="+rows.size());
			int index = 0;
			Set<String> read =new HashSet<String>();
			for(String winHandleBefore : driver.getWindowHandles()){
				read.add(winHandleBefore);
			}
			String winHandleBefore = driver.getWindowHandle();
			int startnum = driver.getWindowHandles().size();
			System.out.println("startnum="+startnum);
			for (WebElement web : rows) {
				driver.switchTo().window(winHandleBefore);
				web.click();
				System.out.println("click success:"+index+++";nownum="+driver.getWindowHandles().size() );
				int clicknum = 0;
//				while(driver.getWindowHandles().size() == startnum){
//					System.out.println("等待加载");
//					Thread.sleep(1000);
//					if(clicknum++>=3){
//						web.click();
//					}
//				}
				startnum = driver.getWindowHandles().size();
				for (String winHandle : driver.getWindowHandles()) {
					if(!read.contains(winHandle)){
						read.add(winHandle);
						WebDriver current = driver.switchTo().window(winHandle);
						
						List<WebElement>  webList = driver.findElements(By.className("break-word"));
						while(webList==null || webList.size()==0){
							System.out.println("等待加载2");
							Thread.sleep(1000);
							webList = driver.findElements(By.className("break-word"));
						}
						if(webList!=null &&webList.size()>0){
							List<WebElement> scopeList = webList.get(0).findElements(By.className("ng-binding"));
							if(scopeList!=null &&scopeList.size()>0){
								System.out.println("url="+driver.getCurrentUrl()+"; 经营范围:"+scopeList.get(0).getText());
								current.close();
							}
						}
					}
				}
				// fw.write(driver.getPageSource());
				// fw.flush();
				// fw.close();
			}
			// System.out.println("content="+driver.findElements(By.className("company-main")).get(0).getText());

//			System.out.println("size=="+driver.getWindowHandles().size());
//		 
//			for (String winHandle : driver.getWindowHandles()) {
//				if (winHandle.equals(winHandleBefore)) {
//					continue;
//				}
//				driver.switchTo().window(winHandle);
//				List<WebElement>  webList = driver.findElements(By.className("break-word"));
//				if(webList!=null &&webList.size()>0){
//					List<WebElement> scopeList = webList.get(0).findElements(By.className("ng-binding"));
//					if(scopeList!=null &&scopeList.size()>0){
//						System.out.println("经营范围:"+scopeList.get(0).getText());
//					}
//				}
//			//	break;
//			}
			driver.manage().window();

			//System.out.println("url=" + driver.getCurrentUrl());
			//System.out.println("content=" + driver.getPageSource());
			
			
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}