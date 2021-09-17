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


//最新方法测试单条轨迹测试
public class main {
	
	static List<GPSData> allgpsPoints = new ArrayList<GPSData>(); // 所有GPS点串
	static List<List<GPSData>> trackList = new ArrayList<List<GPSData>>(); // 轨迹集合
	static List<SenserData> sencePoints = new ArrayList<SenserData>(); // 记录原始传感器点串
	static List<GPSData> gpsPoints = new ArrayList<GPSData>(); // 原始GPS点串
	static List<GPSData> gpsPoints2 = new ArrayList<GPSData>(); // 特征点
	static List<GPSData> gpsPoints3 = new ArrayList<GPSData>(); // 特征点
	static List<allTurn> allTurns = new ArrayList<allTurn>(); // 记录转弯序列
	static List<allTurn> allTurns2 = new ArrayList<allTurn>(); // 记录转弯序列
	static List<List<GPSData>> stayPointList = new ArrayList<List<GPSData>>(); // 获取停留点点簇
	public static void main(String[] args) throws NumberFormatException, IOException, ParseException {

		// TODO 自动生成的方法存根
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		gpsPoints = readData.addData();

	
		double a6 = 0;

		double c1 = 5;// 压缩比

		int all = gpsPoints.size();// 轨迹点总数
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
				gpsPoints2 = new ArrayList<GPSData>(); // 特征点
							
				gpsPoints2 = judgeDir.getAngal3(gpsPoints, middle); // 根据GPS相邻的三点坐标获取中间点的夹角
				
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
		System.out.println("新方法： " + gpsPoints2.size() + "  " + a6);


	}
	
	
	

}
