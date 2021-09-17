package zhuanwan;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;





public class readData {

	static List<GPSData> gpsPoints = new ArrayList<GPSData>();   //记录GPS点串
	static List<SenserData> sencePoints = new ArrayList<SenserData>();   //记录原始传感器点串
	//读取原始GPS文本数据
	public static List<GPSData> getGPSContext(String path) throws Exception {

		FileReader fileReader =new FileReader(path);
		BufferedReader bufferedReader =new BufferedReader(fileReader);
		List<String> list =new ArrayList<String>();
		String str=null;
		while((str=bufferedReader.readLine())!=null) {
			if(str.trim().length()>2) {
				list.add(str);
			}
		}
		bufferedReader.close();
		String[] list1  = null;

		GPSData gpspoint;
		for (String st1 : list) {
			list1=st1.split(" ");
			gpspoint=new GPSData(list1[2],Double.valueOf(list1[0]),Double.valueOf(list1[3]),Double.valueOf(list1[4]),Double.valueOf(list1[5]));  
			gpspoint.GPSID=gpsPoints.size(); 	 
			gpsPoints.add(gpspoint);  	

		}     
		return gpsPoints; 
	}

	
	//读取原始GPS文本数据
		public static List<GPSData> getGPSContext2(String path) throws Exception {

			FileReader fileReader =new FileReader(path);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
			List<String> list =new ArrayList<String>();
			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>2) {
					list.add(str);
				}
			}
			bufferedReader.close();
			String[] list1  = null;

			GPSData gpspoint;
			for (String st1 : list) {
				list1=st1.split(" ");
				
				 String res;
			        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			        Date date = simpleDateFormat.parse(list1[0]);
			        long ts = date.getTime();
			        res = String.valueOf(ts);
				gpspoint=new GPSData(Double.valueOf(res),list1[0],Double.valueOf(list1[1]),Double.valueOf(list1[2]));  
				gpspoint.GPSID=gpsPoints.size(); 	 
				gpsPoints.add(gpspoint);  	

			}     
			return gpsPoints; 
		}
		
		//读取出租车数据
				public static List<GPSData> getGPSTaxi(String path) throws Exception {

					FileReader fileReader =new FileReader(path);
					BufferedReader bufferedReader =new BufferedReader(fileReader);
					List<String> list =new ArrayList<String>();
					String str=null;
					while((str=bufferedReader.readLine())!=null) {
						if(str.trim().length()>2) {
							list.add(str);
						}
					}
					bufferedReader.close();
					String[] list1  = null;

					GPSData gpspoint;
					for (String st1 : list) {
						list1=st1.split(",");	
						String res;
						  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					        Date date = simpleDateFormat.parse(list1[1]);
					        long ts = date.getTime();
					        res = String.valueOf(ts);
					        
						gpspoint=new GPSData(Double.valueOf(res),Double.valueOf(list1[2]),Double.valueOf(list1[3]));  
						gpspoint.GPSID=gpsPoints.size(); 	 
						gpsPoints.add(gpspoint);  	

					}     
					return gpsPoints; 

			
				}
				
				
				
	//读取原始传感器数据
	public static List<SenserData> getSceneContext(String path) throws Exception {

		FileReader fileReader =new FileReader(path);
		BufferedReader bufferedReader =new BufferedReader(fileReader);
		List<String> list =new ArrayList<String>();
		String str=null;
		while((str=bufferedReader.readLine())!=null) {
			if(str.trim().length()>2) {
				list.add(str);
			}
		}
		bufferedReader.close();
		String[] list1 = null ;
		SenserData sencepoint;
		for (String st1 : list) {
			list1=st1.split(" ");
			sencepoint=new SenserData(Double.valueOf(list1[0]),Double.valueOf(list1[15]),Double.valueOf(list1[16]));  
			sencepoint.SenserID=sencePoints.size();
			sencePoints.add(sencepoint);  	

		}     
		
		for(int i=0;i<sencePoints.size();i++)
		{
			if(sencePoints.get(i).orix==0)
			{
				sencePoints.remove(i);
				i--;
			}
			
		}

		for(int i=1;i<sencePoints.size();i++)
		{
			if(sencePoints.get(i).orix-sencePoints.get(i-1).orix>180)
			{
				sencePoints.get(i).orix-=360;
			}
			else if (sencePoints.get(i).orix-sencePoints.get(i-1).orix<-180) {
				sencePoints.get(i).orix+=360;
			}
		}
		return sencePoints; 
	}
	
	//读取原始传感器数据
		public static List<SenserData> getSceneContext2(String path) throws Exception {

			FileReader fileReader =new FileReader(path);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
			List<String> list =new ArrayList<String>();
			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>2) {
					list.add(str);
				}
			}
			bufferedReader.close();
			String[] list1 = null ;
			SenserData sencepoint;
			for (String st1 : list) {
				list1=st1.split(" ");
				 String res;
			        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
			        Date date = simpleDateFormat.parse(list1[0]);
			        long ts = date.getTime();
			        res = String.valueOf(ts);
				sencepoint=new SenserData(Double.valueOf(res),Double.valueOf(list1[1]),Double.valueOf(list1[2]));  
				sencepoint.SenserID=sencePoints.size();
				sencePoints.add(sencepoint);  	

			}     
			
			for(int i=0;i<sencePoints.size();i++)
			{
				if(sencePoints.get(i).orix==0)
				{
					sencePoints.remove(i);
					i--;
				}
				
			}

			for(int i=1;i<sencePoints.size();i++)
			{
				if(sencePoints.get(i).orix-sencePoints.get(i-1).orix>180)
				{
					sencePoints.get(i).orix-=360;
				}
				else if (sencePoints.get(i).orix-sencePoints.get(i-1).orix<-180) {
					sencePoints.get(i).orix+=360;
				}
			}
			return sencePoints; 
		}

		
		public static double[][] gpsdata(FileReader file) throws NumberFormatException, IOException, ParseException{
			BufferedReader br = new BufferedReader(file);//读取文件
			String line ;         
			String []A3 = new String[40000];//单独数组存string时间
			String [][]C3 = new String[40000][2];//存string经纬度
			double [][]DD3 = new double[40000][3];//存double经纬度
			int count=0;
			while(( line=br.readLine())!=null) {//按行读取为字符串
				String [] sp = line.split(" ");//按空格进行分割
				for(int i=1;i<sp.length;i++){
					A3[count] = sp[0];  
					SimpleDateFormat cdf=new SimpleDateFormat("HH:mm:ss");
					Date b=cdf.parse(A3[count]);
					long zhi2=b.getTime(); 
					DD3[count][0]=zhi2;//将string日期转为double数据
					C3[count][i-1] = sp[i];
					DD3[count][i] = Double.parseDouble(C3[count][i-1]);//输入一行的double数据  
				}
				count++;
			}
			double BB3[][]=new double[count][3];
			for (int i = 0; i < count; i++) {
				for (int j = 0; j < DD3[i].length; j++) {
					BB3[i][j] = DD3[i][j];
				}
			}
			return BB3;
		}
		
		public static double[][] gpsdata(List<GPSData> g1) {
			
			double BB3[][]=new double[g1.size()][3];
			for (int i = 0; i < g1.size(); i++) {
				
				BB3[i][0] = g1.get(i).longTime;
				BB3[i][1] = g1.get(i).lon;
				BB3[i][2] =g1.get(i).lat;
			}
			return BB3;
		}
		
		
	
	//合并GPS和传感器数据    
    public static List<GPSData> addData() {
    	  int cursor = 0, k = 0 ;
          double startTime, endTime, a,b;
          for (int i = 1; i < gpsPoints.size(); ++i)
          {
              
              a = 0;//累积方向角      
              b=0;
              k = 0;//累积个数
              //记录当前段的起始及终止时间
              startTime = gpsPoints.get(i-1).longTime;
              endTime = gpsPoints.get(i).longTime;
              //对当前段进行处理，附加上传感器数据

              //寻找到第一个大于起始时间的传感器点
              while (sencePoints.get(cursor).longTime < startTime)
              {
                  ++cursor;
              }
              if (cursor >=  sencePoints.size())
              {               
                  break;
              }
              List<Double> d1=new ArrayList<Double>();//存储方向角数据，计算方差，过滤停留点
              List<Double> d2=new ArrayList<Double>();//存储方向角数据，计算方差，过滤停留点
              //将位于该段时间内的合线性加速度累加求平均
              while (sencePoints.get(cursor).longTime >= startTime && sencePoints.get(cursor).longTime < endTime)
              {
            	  d1.add(sencePoints.get(cursor).orix);
            	  d2.add(sencePoints.get(cursor).acc);
                  a += sencePoints.get(cursor).orix;
                  b+=sencePoints.get(cursor).acc;
                  ++k;
                  ++cursor;
              }         
              gpsPoints.get(i).angle2=( a / k);
              gpsPoints.get(i).acc=( b / k);
              
              //取左不取
              --cursor;
          }
          
          
          for (int i = 1; i < gpsPoints.size()-1; ++i)
          {
              double a1=gpsPoints.get(i+1).angle2-gpsPoints.get(i).angle2;
              gpsPoints.get(i).angle3=a1;
            
          }
          return gpsPoints;
          

    }
   

}
