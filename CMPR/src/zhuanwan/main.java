package zhuanwan;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//Copyright (c) 2021 NCWU All rights reserved.
//Author: Dongbao Zhao; Kaixuan Zhang; Wenkai Liu
//Institution：College of Surveying and Geo-Informatics, North China University of Water Resources and Electric Power;State Key Laboratory of Geo-Information Engineering

public class main {
	
	static List<GPSData> allgpsPoints = new ArrayList<GPSData>(); 
	static List<List<GPSData>> trackList = new ArrayList<List<GPSData>>(); 
	static List<SenserData> sencePoints = new ArrayList<SenserData>();
	static List<GPSData> gpsPoints = new ArrayList<GPSData>(); 
	static List<GPSData> gpsPoints2 = new ArrayList<GPSData>();
	static List<GPSData> gpsPoints3 = new ArrayList<GPSData>(); 
	static List<allTurn> allTurns = new ArrayList<allTurn>(); 
	static List<allTurn> allTurns2 = new ArrayList<allTurn>(); 
	static List<List<GPSData>> stayPointList = new ArrayList<List<GPSData>>(); 
	public static void main(String[] args) throws NumberFormatException, IOException, ParseException {

		
		String txtGPSPath = "C:\\Users\\32762\\Desktop\\1.txt";
		String txtScenePath = "C:\\Users\\32762\\Desktop\\2.txt";
		try {
			gpsPoints = readData.getGPSContext2(txtGPSPath);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		try {
			sencePoints = readData.getSceneContext2(txtScenePath);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		gpsPoints = readData.addData();

	
		double a6 = 0;

		double c1 = 5;

		int all = gpsPoints.size();
		int reduceC1 = (int) (all / c1);
		double low = 0.1;
		double middle = 0;
		double high = 1000;
		int c = 0;


		
		low = 0.1;
		middle = 0;
		high = 100;
		c = 0;
		pretreatedGPSData.initGpsDatas(gpsPoints);
		gpsPoints2 = new ArrayList<GPSData>();
		while (Math.abs(gpsPoints2.size() - reduceC1) > 0) {

			c++;
			if (c < 50) {
				middle = (low + high) / 2;
				pretreatedGPSData.initGpsDatas(gpsPoints);
				gpsPoints2 = new ArrayList<GPSData>();
							
				gpsPoints2 = judgeDir.getAngal3(gpsPoints, middle);
				
				a6 = culErrorDis.getTimeDistance(gpsPoints, gpsPoints2);
				if (gpsPoints2.size() > reduceC1) {

					low = middle;
				} else if (gpsPoints2.size() < reduceC1) {

					high = middle;
				}
			} else {
				break;
			}
		}
		System.out.println( gpsPoints2.size() + "  " + a6);


	}
	
	
	

}
