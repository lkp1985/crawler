package com.lkp.project.qyshudu;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SearchTerm {
	public static Set<String> termSet = new HashSet<String>();
	public static String searchterm = "从事游戏制作运营的技术开发技术服务技术咨询技术转让设计制作代理发布广告电脑动画设计产品设计对石油液化气行业煤炭及洁净能源的投资机电设备及四技服务非专控通讯设备计算机软硬件及网络设备计算机系统及网络安装工程金属建筑装潢材料非危险品化学原料百货参与投资经营【依法须经批准的项目经相关部门批准后方可开展经营活动电视剧专题综艺动画等节目制作发行以广播电视节目制作经营许可证为准商务信息咨询劳务服务不含中介广告计算机软硬件机电一体化技术开发咨询服务转让计算机及外围设备电器设备计算机软件交电文化办公用机械文教用品服装鞋帽批发兼零售软件制作照相器材摄影器材电视机计算机租赁货物和技术进出口业务以上经营范围涉及行业许可的凭许可证件在有效期限内经营国家有专项专营规定的按规定执行利用互联网经营游戏产品运营网络游戏虚拟货币发行技术开发技术推广技术服务计算机系统服务应用软件服务数据处理第二类增值电信业务中的信息服务业务仅限互联网信息服务增值电信业务经营许可证有效期至一般经营项目游戏软件的研发网络计算机技术领域内的技术开发技术转让技术咨询技术服务计算机系统集成动漫设计图文设计计算机计算机软件及辅助设备的销售。以上经营范围除国家规定的专控及前置许可项目投资与游戏等相关产业组织文化艺术交流活动不含演出影视策划投资咨询设计制作发布代理国内各类广告会议会展活动策划及承办市场调查商务信息咨询企业营销策划游戏的开发发行代理及销售技术推广服务游戏产品运营";
	public static String keywords = "游戏";
	public static String getSearchTerm(){
		try{
			int length = searchterm.length();
			int ran = new Random().nextInt(length-1);
			int len = new Random().nextInt(5)+1;
			String condition = keywords;
			if(ran+len >= length){
				len = 1;
			}  
			condition = searchterm.substring(ran, ran+len);
			while(termSet.contains(condition)){
				ran = new Random().nextInt(length-1);
				condition = searchterm.substring(ran, ran+len);
			} 
			return keywords+ condition;
		}catch(Exception e){
			
		}
		return keywords;
	//	System.out.println("get search condition "+condition);
		
	}
}
