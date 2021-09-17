package zhuanwan;

import java.util.ArrayList;
import java.util.List;

//预处理GPS数据
public class pretreatedGPSData {

	public static List<GPSData> isStaypoint(List<GPSData> g1) {

		for (int i = 1; i < g1.size()-1; i++) {
			
			double dis=1;
			if (Mathf.GetDistance(g1.get(i).lon , g1.get(i).lat, g1.get(i-1).lon , g1.get(i-1).lat)<dis || Mathf.GetDistance(g1.get(i).lon , g1.get(i).lat, g1.get(i+1).lon , g1.get(i+1).lat)<dis) {
				g1.get(i).isStayPoint=true;			
			}			

		}
		return g1;
	}
	
	
	public static List<GPSData> removePoints(List<GPSData> g1) {

		List<GPSData> g2=new ArrayList<GPSData>();
		
		for (int i = 0; i < g1.size(); i++) {
			if (g1.get(i).isSave) {
				g2.add(g1.get(i));
			}
			
		}
		return g2;
	}

	public static void initGpsDatas(List<GPSData> g1) {
		for (int i = 0; i < g1.size(); i++) {	
			g1.get(i).isSave=false;		
			g1.get(i).isStayPoint=false;		
			g1.get(i).sgpstime=0;
		}	
	}
}
