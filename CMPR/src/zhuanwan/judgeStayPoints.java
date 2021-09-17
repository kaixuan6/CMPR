package zhuanwan;

import java.util.ArrayList;
import java.util.List;

public class judgeStayPoints {

	public static List<List<GPSData>> getStayPointsList1(List<GPSData> g1, double dis) {
		List<List<GPSData>> stayPointList = new ArrayList<List<GPSData>>();
		List<GPSData> staylist = new ArrayList<GPSData>();
		for (int i = 0; i < g1.size() - 1; i++) {
			if (Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, g1.get(i + 1).lon, g1.get(i + 1).lat) < dis) {
				g1.get(i).isStayPoint = true;
			}
		}

		for (int j = 0; j < g1.size() - 1; j++) {
			if (g1.get(j).isStayPoint) {
				if (!g1.get(j + 1).isStayPoint) {
					staylist.add(g1.get(j));
					stayPointList.add(staylist);
					staylist = new ArrayList<GPSData>();
				} else {
					staylist.add(g1.get(j));
				}
			}

		}

		return stayPointList;

	}

	public static List<List<GPSData>> getStayPointsList2(List<GPSData> g1, double a11) {

		List<List<GPSData>> stayPointList = new ArrayList<List<GPSData>>();
		List<GPSData> staylist = new ArrayList<GPSData>();

		for (int i = 1; i < g1.size() - 1; i++) {
			if (g1.get(i).acc < a11) {
				g1.get(i).isStayPoint = true;
			}
		}

		for (int j = 0; j < g1.size() - 1; j++) {
			if (g1.get(j).isStayPoint) {
				if (!g1.get(j + 1).isStayPoint) {
					staylist.add(g1.get(j));
					stayPointList.add(staylist);
					staylist = new ArrayList<GPSData>();
				} else {
					staylist.add(g1.get(j));
				}
			}

		}
		return stayPointList;
	}

	public static List<allTurn> getSpeedList(List<GPSData> g1, double middle) {
		double speed1 = 0;
		for (int i = 1; i < g1.size() ; i++) {
			speed1 = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, g1.get(i - 1).lon, g1.get(i - 1).lat);
			if (speed1 < 1) {
				g1.get(i).speed = 0;
			} else {
				g1.get(i).speed = speed1;
			}

		}

		List<Integer> d3 = new ArrayList<Integer>();
		List<List<Integer>> d4 = new ArrayList<List<Integer>>();

		for (int j = 0; j < g1.size() - 2; j++) {
			if (g1.get(j).speed == 0) {
				if (g1.get(j + 1).speed != 0) {
					d3.add(j);
					d4.add(d3);
					d3 = new ArrayList<Integer>();
					continue;
				} else {
					d3.add(j);
				}
			}
			else if ((g1.get(j).speed - g1.get(j + 1).speed) > 0) {
				if ((g1.get(j + 1).speed - g1.get(j + 2).speed) <= 0) {
					d3.add(j);
					d3.add(j + 1);
					d4.add(d3);
					d3 = new ArrayList<Integer>();
					continue;
				} else {
					d3.add(j);
				}
			} 

			else if((g1.get(j).speed - g1.get(j + 1).speed) < 0) {
				if ((g1.get(j + 1).speed - g1.get(j + 2).speed) > 0) {
					d3.add(j);
					d3.add(j + 1);
					d4.add(d3);
					d3 = new ArrayList<Integer>();
					continue;
				} else {
					d3.add(j);
				}
			}
		}


		List<allTurn> d1 = new ArrayList<allTurn>();
		allTurn d2 = new allTurn();
		double totalspan, span, ratio, x, y;
		for (int i = 0; i < d4.size(); i++) // 初步获取转弯序列
		{
			d2.gpsStartID = g1.get(d4.get(i).get(0)).GPSID;// 获取转弯起始点ID
			d2.gpsEndID = g1.get(d4.get(i).get(d4.get(i).size() - 1)).GPSID;// 获取转弯终止点ID

			d1.add(d2);
			d2 = new allTurn();
		}
		for (int i = 0; i < d1.size() - 1; i++) {
			double angle11 = Mathf.getDegree2(g1.get(d1.get(i).gpsStartID).lon, g1.get(d1.get(i).gpsStartID).lat,
					g1.get(d1.get(i).gpsEndID).lon, g1.get(d1.get(i).gpsEndID).lat,
					g1.get(d1.get(i + 1).gpsStartID).lon, g1.get(d1.get(i + 1).gpsStartID).lat,
					g1.get(d1.get(i + 1).gpsEndID).lon, g1.get(d1.get(i + 1).gpsEndID).lat);
			if (Math.abs(angle11) > 1) {
				d1.get(i).isSave = false;
			} else {

				totalspan = g1.get(d1.get(i + 1).gpsEndID).longTime - g1.get(d1.get(i).gpsStartID).longTime;
				double dis = -1, dis1 = 0;
				double x2 = 0, y2 = 0;
				for (int j = d1.get(i).gpsStartID + 1; j < d1.get(i + 1).gpsEndID; j++) {
					span = g1.get(d1.get(i + 1).gpsEndID).longTime - g1.get(j).longTime;
					ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
					x = g1.get(d1.get(i).gpsStartID).lon
							+ ratio * (g1.get(d1.get(i + 1).gpsEndID).lon - g1.get(d1.get(i).gpsStartID).lon);
					y = g1.get(d1.get(i).gpsStartID).lat
							+ ratio * (g1.get(d1.get(i + 1).gpsEndID).lat - g1.get(d1.get(i).gpsStartID).lat);
					dis1 = (g1.get(j).lon - x) * (g1.get(j).lon - x) + (g1.get(j).lat - y) * (g1.get(j).lat - y);// 获得时间同步欧式距离，GetDistance是一个简单的根据两点坐标计算距离的函数

					if (dis1 > dis) {
						dis = dis1;
						x2 = x;
						y2 = y;
						d1.get(i).maxGPSID = j;
					}
				}
				d1.get(i).maxPointDis = Mathf.GetDistance(g1.get(d1.get(i).maxGPSID).lon,
						g1.get(d1.get(i).maxGPSID).lat, x2, y2);
				if (d1.get(i).maxPointDis > middle) {
					d1.get(i).isSave = true;
				
					
				}
			}

		}

		for (int i = 0; i < d1.size(); i++) {
			if (!d1.get(i).isSave) {
				d1.remove(i);
				i--;
			}

		}

		return d1;

	}
	
	public static List<allTurn> getSpeedList2(List<GPSData> g1, double middle) {
		double speed1 = 0;
		for (int i = 0; i < g1.size() - 1; i++) {
			speed1 = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, g1.get(i + 1).lon, g1.get(i + 1).lat);
			if (speed1 < 1) {
				g1.get(i).speed = 0;
			} else {
				g1.get(i).speed = speed1;
			}

		}

		List<Integer> d3 = new ArrayList<Integer>();
		List<List<Integer>> d4 = new ArrayList<List<Integer>>();

		for (int j = 0; j < g1.size() - 2; j++) {
			if (g1.get(j).speed == 0) {
				if (g1.get(j + 1).speed != 0) {
					d3.add(j);
					d3.add(j+1);
					d4.add(d3);
					d3 = new ArrayList<Integer>();
					continue;
				} else {
					d3.add(j);
				}
			}
			else if ((g1.get(j).speed - g1.get(j + 1).speed) > 0) {
				if ((g1.get(j + 1).speed - g1.get(j + 2).speed) <= 0) {
					d3.add(j);
					d3.add(j + 1);
					d4.add(d3);
					d3 = new ArrayList<Integer>();
					continue;
				} else {
					d3.add(j);
				}
			} 

			else if((g1.get(j).speed - g1.get(j + 1).speed) < 0) {
				if ((g1.get(j + 1).speed - g1.get(j + 2).speed) > 0) {
					d3.add(j);
					d3.add(j + 1);
					d4.add(d3);
					d3 = new ArrayList<Integer>();
					continue;
				} else {
					d3.add(j);
				}
			}
		}


		List<allTurn> d1 = new ArrayList<allTurn>();
		allTurn d2 = new allTurn();
		double totalspan, span, ratio, x, y;
		for (int i = 0; i < d4.size(); i++) // 初步获取转弯序列
		{
			d2.gpsStartID = g1.get(d4.get(i).get(0)).GPSID;// 获取转弯起始点ID
			d2.gpsEndID = g1.get(d4.get(i).get(d4.get(i).size() - 1)).GPSID;// 获取转弯终止点ID

			d1.add(d2);
			d2 = new allTurn();
		}
		for (int i = 0; i < d1.size() - 1; i++) {
			double angle11 = Mathf.getDegree2(g1.get(d1.get(i).gpsStartID).lon, g1.get(d1.get(i).gpsStartID).lat,
					g1.get(d1.get(i).gpsEndID).lon, g1.get(d1.get(i).gpsEndID).lat,
					g1.get(d1.get(i + 1).gpsStartID).lon, g1.get(d1.get(i + 1).gpsStartID).lat,
					g1.get(d1.get(i + 1).gpsEndID).lon, g1.get(d1.get(i + 1).gpsEndID).lat);
			if (Math.abs(angle11) > 3) {
				d1.get(i).isSave = false;
			} else {

				totalspan = g1.get(d1.get(i + 1).gpsEndID).longTime - g1.get(d1.get(i).gpsStartID).longTime;
				double dis = -1, dis1 = 0;
				double x2 = 0, y2 = 0;
				for (int j = d1.get(i).gpsStartID + 1; j < d1.get(i + 1).gpsEndID; j++) {
					span = g1.get(d1.get(i + 1).gpsEndID).longTime - g1.get(j).longTime;
					ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
					x = g1.get(d1.get(i).gpsStartID).lon
							+ ratio * (g1.get(d1.get(i + 1).gpsEndID).lon - g1.get(d1.get(i).gpsStartID).lon);
					y = g1.get(d1.get(i).gpsStartID).lat
							+ ratio * (g1.get(d1.get(i + 1).gpsEndID).lat - g1.get(d1.get(i).gpsStartID).lat);
					dis1 = (g1.get(j).lon - x) * (g1.get(j).lon - x) + (g1.get(j).lat - y) * (g1.get(j).lat - y);// 获得时间同步欧式距离，GetDistance是一个简单的根据两点坐标计算距离的函数

					if (dis1 > dis) {
						dis = dis1;
						x2 = x;
						y2 = y;
						d1.get(i).maxGPSID = j;
					}
				}
				d1.get(i).maxPointDis = Mathf.GetDistance(g1.get(d1.get(i).maxGPSID).lon,
						g1.get(d1.get(i).maxGPSID).lat, x2, y2);
				if (d1.get(i).maxPointDis > middle) {
					d1.get(i).isSave = true;
					g1.get(d1.get(i).maxGPSID).sgpstime=g1.get(d1.get(i+1).gpsEndID).longTime-g1.get(d1.get(i).maxGPSID).longTime;
					
				}
			}

		}

		for (int i = 0; i < d1.size(); i++) {
			if (!d1.get(i).isSave) {
				d1.remove(i);
				i--;
			}

		}

		return d1;

	}

}
