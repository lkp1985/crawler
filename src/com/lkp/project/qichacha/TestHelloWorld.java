package com.lkp.project.qichacha;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestHelloWorld {

    public static void main(String[] args) {
        
    	try{
    		System.out.println(Utils.dama2());
    		 //如果火狐浏览器没有默认安装在C盘，需要制定其路径
        	System.setProperty("webdriver.firefox.bin", "d:/Program Files (x86)/Mozilla Firefox/firefox.exe"); 
        //	System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe"); 
        	//C:\Program Files (x86)\Google\Chrome\Application
            WebDriver driver = new FirefoxDriver();
           // driver.get("http://www.tianyancha.com");
            driver.get("http://xj.tianyancha.com/search/软件开发.json?&pn=2&category=19&type=scope");
            
            //driver.manage().window();
            
//            WebElement txtbox = driver.findElement(By.name("wd"));
//            txtbox.sendKeys("Glen");
//            
//            driver.getTitle()
//            WebElement btn = driver.findElements(By.className("company_info ")).get(0);
            System.out.println("btn="+  driver.getTitle());
            
            for (Cookie ck : driver.manage().getCookies()) {
            	System.out.println(ck.getName() +";" + ck.getValue() + ";"+ ck.getDomain() + ";" + ck.getPath() + ";"                        + ck.getExpiry() + ";" + ck.isSecure());
            }
            System.out.println("content="+driver.getPageSource());
           // System.out.println("content="+driver.findElements(By.className("company-main")).get(0).getText());
            
            driver.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
       

    }

}