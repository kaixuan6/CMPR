package zhuanwan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Compress {



	public static List<GPSData> myCompress1(List<allTurn> a1, List<List<GPSData>> sp, List<GPSData> g1, double r1) {

		// 获取特征点序号
		List<Integer> b1 = new ArrayList<Integer>();
		List<Integer> b2 = new ArrayList<Integer>();

		List<GPSData> g2 = new ArrayList<GPSData>();

		// 添加转向特征点ID
		b1.add(0);
		for (int i = 0; i < a1.size(); i++) {
			b1.add(a1.get(i).maxGPSID);	
		}
		b1.add(g1.size() - 1);

		// 添加停留点ID
		for (int i = 0; i < sp.size(); i++) {
			b1.add(sp.get(i).get(0).GPSID);
			b1.add(sp.get(i).get(sp.get(i).size() - 1).GPSID);
		}

		// 判断停留点是否保留
		double totalspan, span, ratio, x, y, timeratiodis;
		for (int i = 0; i < sp.size(); i++) {
			int a = sp.get(i).get(0).GPSID;// 起点ID
			int b = sp.get(i).get(sp.get(i).size() - 1).GPSID;// 终止点
			int c = 0;// 上一转弯点
			int d = 0;// 下一转弯点

			for (int j = 0; j < b1.size(); j++) {

				if (b1.get(j) < a) {
					c = b1.get(j);
				}
				if (b1.get(j) > b) {
					d = b1.get(j);
					break;
				}
			}
			for (int j = a+1; j < b; j++) {
				totalspan = g1.get(j).longTime - g1.get(c).longTime;
				span = g1.get(j).longTime - g1.get(a).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
				x = g1.get(c).lon + ratio * (g1.get(j).lon - g1.get(c).lon);
				y = g1.get(c).lat + ratio * (g1.get(j).lat - g1.get(c).lat);
				timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);

				if (timeratiodis > r1) {
					b2.add(a);
					break;
				}

			}
			for (int j = b+1; j < d; j++) {

				totalspan = g1.get(j).longTime - g1.get(a).longTime;
				span = g1.get(j).longTime - g1.get(b).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
				x = g1.get(a).lon + ratio * (g1.get(j).lon - g1.get(a).lon);
				y = g1.get(a).lat + ratio * (g1.get(j).lat - g1.get(a).lat);
				timeratiodis = Mathf.GetDistance(g1.get(b).lon, g1.get(b).lat, x, y);

				if (timeratiodis > r1) {
					b2.add(b);
					break;
				}

			}

			//			totalspan = g1.get(b).longTime - g1.get(c).longTime;// 获得从fromID到toID所经过的总的时间间隔
			//			span = g1.get(b).longTime - g1.get(a).longTime;
			//
			//			ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
			//
			//			x = g1.get(c).lon + ratio * (g1.get(b).lon - g1.get(c).lon);
			//			y = g1.get(c).lat + ratio * (g1.get(b).lat - g1.get(c).lat);
			//			// 线性插值出对应点坐标
			//			timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);
			//			if (timeratiodis > r1) {
			//				b2.add(a);
			//			}
			//
			//			totalspan = g1.get(d).longTime - g1.get(a).longTime;// 获得从fromID到toID所经过的总的时间间隔
			//			span = g1.get(d).longTime - g1.get(b).longTime;
			//
			//			ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
			//
			//			x = g1.get(a).lon + ratio * (g1.get(d).lon - g1.get(a).lon);
			//			y = g1.get(a).lat + ratio * (g1.get(d).lat - g1.get(a).lat);
			//			// 线性插值出对应点坐标
			//			timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);
			//			if (timeratiodis > r1) {
			//				b2.add(b);
			//			}
		}
		b1.clear();
		for (int i = 0; i < a1.size(); i++) {
			b1.add(a1.get(i).maxGPSID);
		}
		b1.add(0);
		b1.add(g1.size() - 1);
		b1.addAll(b2);
		Collections.sort(b1);
		for (int j = 0; j < b1.size() - 1; j++) {
			Reduce.TDDR(g1, b1.get(j), b1.get(j + 1), r1);
		}

		for (int k = 0; k < g1.size(); k++) {
			if (g1.get(k).isSave == true) {
				g2.add(g1.get(k));
			}
		}

		return g2;
	}



	//计算延时

	public static double myCompress2(List<allTurn> a1, List<List<GPSData>> sp, List<GPSData> g1, double r1) {

		// 获取特征点序号
		double stimeBen=0;
		List<Integer> b1 = new ArrayList<Integer>();
		List<Integer> b2 = new ArrayList<Integer>();

		List<GPSData> g2 = new ArrayList<GPSData>();

		// 添加转向特征点ID
		b1.add(0);
		for (int i = 0; i < a1.size(); i++) {
			b1.add(a1.get(i).maxGPSID);
			g1.get(a1.get(i).maxGPSID).sgpstime=g1.get(a1.get(i).gpsEndID).longTime-g1.get(a1.get(i).maxGPSID).longTime;
			stimeBen+=g1.get(a1.get(i).gpsEndID).longTime-g1.get(a1.get(i).maxGPSID).longTime;
		}
		b1.add(g1.size() - 1);

		// 添加停留点ID
		for (int i = 0; i < sp.size(); i++) {
			b1.add(sp.get(i).get(0).GPSID);
			b1.add(sp.get(i).get(sp.get(i).size() - 1).GPSID);
		}

		// 判断停留点是否保留

		double totalspan, span, ratio, x, y, timeratiodis;
		for (int i = 0; i < sp.size(); i++) {
			int a = sp.get(i).get(0).GPSID;// 起点ID
			int b = sp.get(i).get(sp.get(i).size() - 1).GPSID;// 终止点
			int c = 0;// 上一转弯点
			int d = 0;// 下一转弯点

			for (int j = 0; j < b1.size(); j++) {

				if (b1.get(j) < a) {
					c = b1.get(j);
				}
				if (b1.get(j) > b) {
					d = b1.get(j);
					break;
				}
			}

			for (int j = a+1; j < b; j++) {
				totalspan = g1.get(j).longTime - g1.get(c).longTime;
				span = g1.get(j).longTime - g1.get(a).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
				x = g1.get(c).lon + ratio * (g1.get(j).lon - g1.get(c).lon);
				y = g1.get(c).lat + ratio * (g1.get(j).lat - g1.get(c).lat);
				timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);

				if (timeratiodis > r1) {
					b2.add(a);
					g1.get(a).sgpstime=g1.get(j).longTime-g1.get(a).longTime;
					stimeBen+=g1.get(j).longTime-g1.get(a).longTime;
					break;
				}

			}
			for (int j = b+1; j < d; j++) {

				totalspan = g1.get(j).longTime - g1.get(a).longTime;
				span = g1.get(j).longTime - g1.get(b).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
				x = g1.get(a).lon + ratio * (g1.get(j).lon - g1.get(a).lon);
				y = g1.get(a).lat + ratio * (g1.get(j).lat - g1.get(a).lat);
				timeratiodis = Mathf.GetDistance(g1.get(b).lon, g1.get(b).lat, x, y);

				if (timeratiodis > r1) {
					b2.add(b);
					g1.get(b).sgpstime=g1.get(j).longTime-g1.get(b).longTime;
					stimeBen+=g1.get(j).longTime-g1.get(b).longTime;
					break;
				}

			}

			//			totalspan = g1.get(b).longTime - g1.get(c).longTime;// 获得从fromID到toID所经过的总的时间间隔
			//			span = g1.get(b).longTime - g1.get(a).longTime;
			//
			//			ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
			//
			//			x = g1.get(c).lon + ratio * (g1.get(b).lon - g1.get(c).lon);
			//			y = g1.get(c).lat + ratio * (g1.get(b).lat - g1.get(c).lat);
			//			// 线性插值出对应点坐标
			//			timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);
			//			if (timeratiodis > r1) {
			//				b2.add(a);
			//			}
			//
			//			totalspan = g1.get(d).longTime - g1.get(a).longTime;// 获得从fromID到toID所经过的总的时间间隔
			//			span = g1.get(d).longTime - g1.get(b).longTime;
			//
			//			ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
			//
			//			x = g1.get(a).lon + ratio * (g1.get(d).lon - g1.get(a).lon);
			//			y = g1.get(a).lat + ratio * (g1.get(d).lat - g1.get(a).lat);
			//			// 线性插值出对应点坐标
			//			timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);
			//			if (timeratiodis > r1) {
			//				b2.add(b);
			//			}	

		}
		b1.clear();
		for (int i = 0; i < a1.size(); i++) {
			b1.add(a1.get(i).maxGPSID);
		}
		b1.add(0);
		b1.add(g1.size() - 1);
		b1.addAll(b2);
		Collections.sort(b1);
		Reduce.initstime();
		for (int j = 0; j < b1.size() - 1; j++) {
			Reduce.getstime( b1.get(j + 1));
			Reduce.TDDR2(g1, b1.get(j), b1.get(j + 1), r1);
		}

		stimeBen+=Reduce.returnStime();

		for (int k = 0; k < g1.size(); k++) {
			if (g1.get(k).isSave == true) {
				g2.add(g1.get(k));
			}
		}

		return stimeBen;
	}


	//不加三度   opw 计算延时
	public static double myCompress22(List<allTurn> a1, List<List<GPSData>> sp, List<GPSData> g1, double r1) {

		// 获取特征点序号
		double stimeBen=0;
		List<Integer> b1 = new ArrayList<Integer>();
		List<Integer> b2 = new ArrayList<Integer>();

		List<GPSData> g2 = new ArrayList<GPSData>();

		// 添加转向特征点ID
		b1.add(0);
		for (int i = 0; i < a1.size(); i++) {
			b1.add(a1.get(i).maxGPSID);
			stimeBen+=g1.get(a1.get(i).maxGPSID).sgpstime;
		}
		b1.add(g1.size() - 1);

		// 添加停留点ID
		for (int i = 0; i < sp.size(); i++) {
			b1.add(sp.get(i).get(0).GPSID);
			b1.add(sp.get(i).get(sp.get(i).size() - 1).GPSID);
		}

		// 判断停留点是否保留

		double totalspan, span, ratio, x, y, timeratiodis;
		for (int i = 0; i < sp.size(); i++) {
			int a = sp.get(i).get(0).GPSID;// 起点ID
			int b = sp.get(i).get(sp.get(i).size() - 1).GPSID;// 终止点
			int c = 0;// 上一转弯点
			int d = 0;// 下一转弯点

			for (int j = 0; j < b1.size(); j++) {

				if (b1.get(j) < a) {
					c = b1.get(j);
				}
				if (b1.get(j) > b) {
					d = b1.get(j);
					break;
				}
			}

			for (int j = a+1; j < b; j++) {
				totalspan = g1.get(j).longTime - g1.get(c).longTime;
				span = g1.get(j).longTime - g1.get(a).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
				x = g1.get(c).lon + ratio * (g1.get(j).lon - g1.get(c).lon);
				y = g1.get(c).lat + ratio * (g1.get(j).lat - g1.get(c).lat);
				timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);

				if (timeratiodis > r1) {
					b2.add(a);
					g1.get(a).sgpstime=g1.get(j).longTime-g1.get(a).longTime;
					stimeBen+=g1.get(j).longTime-g1.get(a).longTime;
					break;
				}

			}
			for (int j = b+1; j < d; j++) {

				totalspan = g1.get(j).longTime - g1.get(a).longTime;
				span = g1.get(j).longTime - g1.get(b).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
				x = g1.get(a).lon + ratio * (g1.get(j).lon - g1.get(a).lon);
				y = g1.get(a).lat + ratio * (g1.get(j).lat - g1.get(a).lat);
				timeratiodis = Mathf.GetDistance(g1.get(b).lon, g1.get(b).lat, x, y);

				if (timeratiodis > r1) {
					b2.add(b);
					g1.get(b).sgpstime=g1.get(j).longTime-g1.get(b).longTime;
					stimeBen+=g1.get(j).longTime-g1.get(b).longTime;
					break;
				}

			}

			//			totalspan = g1.get(b).longTime - g1.get(c).longTime;// 获得从fromID到toID所经过的总的时间间隔
			//			span = g1.get(b).longTime - g1.get(a).longTime;
			//
			//			ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
			//
			//			x = g1.get(c).lon + ratio * (g1.get(b).lon - g1.get(c).lon);
			//			y = g1.get(c).lat + ratio * (g1.get(b).lat - g1.get(c).lat);
			//			// 线性插值出对应点坐标
			//			timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);
			//			if (timeratiodis > r1) {
			//				b2.add(a);
			//			}
			//
			//			totalspan = g1.get(d).longTime - g1.get(a).longTime;// 获得从fromID到toID所经过的总的时间间隔
			//			span = g1.get(d).longTime - g1.get(b).longTime;
			//
			//			ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
			//
			//			x = g1.get(a).lon + ratio * (g1.get(d).lon - g1.get(a).lon);
			//			y = g1.get(a).lat + ratio * (g1.get(d).lat - g1.get(a).lat);
			//			// 线性插值出对应点坐标
			//			timeratiodis = Mathf.GetDistance(g1.get(a).lon, g1.get(a).lat, x, y);
			//			if (timeratiodis > r1) {
			//				b2.add(b);
			//			}	

		}
		b1.clear();
		for (int i = 0; i < a1.size(); i++) {
			b1.add(a1.get(i).maxGPSID);
		}
		b1.add(0);
		b1.add(g1.size() - 1);
		b1.addAll(b2);
		Collections.sort(b1);
		for (int j = 0; j < b1.size() - 1; j++) {
			Reduce.getstime( b1.get(j + 1));
			Reduce.TDDR2(g1, b1.get(j), b1.get(j + 1), r1);
		}

		stimeBen+=Reduce.returnStime();

		for (int k = 0; k < g1.size(); k++) {
			if (g1.get(k).isSave == true) {
				g2.add(g1.get(k));
			}
		}

		return stimeBen;
	}

	//突变点   转弯点
	public static List<GPSData> myCompress13(List<allTurn> a1,List<allTurn> a2, List<GPSData> g1, double r1) {

		// 获取特征点序号
		List<Integer> b1 = new ArrayList<Integer>();
		List<Integer> b2 = new ArrayList<Integer>();

		List<GPSData> g2 = new ArrayList<GPSData>();

		// 添加转向特征点ID
		b1.add(0);
		for (int i = 0; i < a1.size(); i++) {
			b1.add(a1.get(i).maxGPSID);	
		}
		b1.add(g1.size() - 1);

		for (int i = 0; i < a2.size(); i++) {
			b1.add(a2.get(i).maxGPSID);	
		}

		Collections.sort(b1);
		for (int j = 0; j < b1.size() - 1; j++) {
			Reduce.TDDR(g1, b1.get(j), b1.get(j + 1), r1);
		}

		for (int k = 0; k < g1.size(); k++) {
			if (g1.get(k).isSave == true) {
				g2.add(g1.get(k));
			}
		}

		return g2;
	}


	//变速点、转弯点计算延时
	public static double myCompress23(List<allTurn> a1,List<allTurn> a2, List<GPSData> g1, double r1) {

		// 获取特征点序号
		double stimeBen=0;
		List<Integer> b1 = new ArrayList<Integer>();
		List<Integer> b2 = new ArrayList<Integer>();

		List<GPSData> g2 = new ArrayList<GPSData>();

		// 添加转向特征点ID
		b1.add(0);
		for (int i = 0; i < a1.size(); i++) {
			b1.add(a1.get(i).maxGPSID);
			g1.get(a1.get(i).maxGPSID).sgpstime=g1.get(a1.get(i).gpsEndID).longTime-g1.get(a1.get(i).maxGPSID).longTime;
			stimeBen+=g1.get(a1.get(i).gpsEndID).longTime-g1.get(a1.get(i).maxGPSID).longTime;
		}
		b1.add(g1.size() - 1);

		for (int i = 0; i < a2.size(); i++) {

			b1.add(a2.get(i).maxGPSID);	
			stimeBen+=g1.get(a2.get(i).maxGPSID).sgpstime;
		}
		// 判断停留点是否保留



		Collections.sort(b1);
		Reduce.initstime();
		for (int j = 0; j < b1.size() - 1; j++) {
			Reduce.getstime( b1.get(j + 1));
			Reduce.TDDR2(g1, b1.get(j), b1.get(j + 1), r1);
		}

		stimeBen+=Reduce.returnStime();

		for (int k = 0; k < g1.size(); k++) {
			if (g1.get(k).isSave == true) {
				g2.add(g1.get(k));
			}
		}

		return stimeBen;
	}



}
