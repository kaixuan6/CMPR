package zhuanwan;

import java.util.ArrayList;
import java.util.List;

//压缩算法
public class Reduce {

	// 道格拉斯算法
	public static void DP(List<GPSData> g1, int fromID, int toID, double threshold) // fromID 起始点序号，toID 终止点序号
	// tempGpoints 轨迹中的一系列GPS点
	{
		int maxindex = -1;
		double dis = 0, maxtimeratiodis = threshold;
		g1.get(fromID).isSave = true;
		g1.get(toID).isSave = true;
		double A = g1.get(fromID).lat - g1.get(toID).lat;
		double B = g1.get(toID).lon - g1.get(fromID).lon;
		double C = g1.get(fromID).lon * g1.get(toID).lat - g1.get(toID).lon * g1.get(fromID).lat;

		for (int i = fromID + 1; i < toID; i++) {
			double x = (double) (B * B * g1.get(i).lon - A * C - A * B * g1.get(i).lat) / (double) (A * A + B * B);
			double y = (double) (-A * x - C) / (double) B;
			dis = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, x, y);
			if (dis > maxtimeratiodis) {
				maxtimeratiodis = dis;
				maxindex = i; // maxindex用以记录具有最大距离的点的序号
			}
		}
		if (maxindex == -1) // 没有结果，则返回
			return;
		else if (maxtimeratiodis > threshold) // 如果最大距离超过规定限差，下面进行递归调用。
		{

			g1.get(maxindex).isSave = true;
			DP(g1, fromID, maxindex, threshold);
			DP(g1, maxindex, toID, threshold);
		}
	}
	
	
	
	// 道格拉斯算法
		public static void DP2(List<GPSData> g1, int fromID, int toID, double threshold) // fromID 起始点序号，toID 终止点序号
		// tempGpoints 轨迹中的一系列GPS点
		{
			int maxindex = -1;
			double dis = 0, maxtimeratiodis = threshold;
			g1.get(fromID).isSave = true;
			g1.get(toID).isSave = true;
			double A = g1.get(fromID).lat - g1.get(toID).lat;
			double B = g1.get(toID).lon - g1.get(fromID).lon;
			double C = g1.get(fromID).lon * g1.get(toID).lat - g1.get(toID).lon * g1.get(fromID).lat;

			for (int i = fromID + 1; i < toID; i++) {
				double x = (double) (B * B * g1.get(i).lon - A * C - A * B * g1.get(i).lat) / (double) (A * A + B * B);
				double y = (double) (-A * x - C) / (double) B;
				dis = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, x, y);
				if (dis > maxtimeratiodis) {
					maxtimeratiodis = dis;
					maxindex = i; // maxindex用以记录具有最大距离的点的序号
				}
			}
			if (maxindex == -1) // 没有结果，则返回
				return;
			else if (maxtimeratiodis > threshold) // 如果最大距离超过规定限差，下面进行递归调用。
			{

				g1.get(maxindex).isSave = true;
				stimeBenTR += g1.get(end1).longTime - g1.get(maxindex).longTime+g1.get(end1).sgpstime;

				DP2(g1, fromID, maxindex, threshold);
				DP2(g1, maxindex, toID, threshold);
			}
		}

	/**
	 * TDDR算法
	 * 
	 * @param tempGpoints
	 * @param fromID
	 * @param toID
	 */
	public static void TDDR(List<GPSData> g1, int fromID, int toID, double threshold1) // fromID 起始点序号，toID 终止点序号
	// tempGpoints 轨//迹中的一系列GPS点
	{
		int i, maxindex = -1;

		double timeratiodis, maxtimeratiodis = -1;
		double x, y;
		double ratio;// 比例

		double totalspan = g1.get(toID).longTime - g1.get(fromID).longTime;// 获得从fromID到toID所经过的总的时间间隔
		double span;

		g1.get(fromID).isSave = true;// 该点被保留
		g1.get(toID).isSave = true; // 该点被保留
		for (i = fromID + 1; i < toID; i++) {

			span = g1.get(toID).longTime - g1.get(i).longTime;

			ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例

			x = g1.get(fromID).lon + ratio * (g1.get(toID).lon - g1.get(fromID).lon);
			y = g1.get(fromID).lat + ratio * (g1.get(toID).lat - g1.get(fromID).lat);
			// 线性插值出对应点坐标
			timeratiodis = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, x, y);// 获得时间同步欧式距离，GetDistance是一个简单的根据两点坐标计算距离的函数
			if (timeratiodis > maxtimeratiodis) {
				maxtimeratiodis = timeratiodis;
				maxindex = i; // maxindex用以记录具有最大距离的点的序号
			}
		}
		if (maxindex == -1) // 没有结果，则返回
			return;
		else if (maxtimeratiodis > threshold1) // 如果最大距离超过规定限差，下面进行递归调用。
		{
			g1.get(maxindex).isSave = true;
			TDDR(g1, fromID, maxindex, threshold1);
			TDDR(g1, maxindex, toID, threshold1);
		}
	}

	static double stimeBenTR = 0;
	

	// 计算延时
	public static void TDDR2(List<GPSData> g1, int fromID, int toID, double threshold1) // fromID 起始点序号，toID 终止点序号
	// tempGpoints 轨//迹中的一系列GPS点
	{
		
		int i, maxindex = -1;

		double timeratiodis, maxtimeratiodis = -1;
		double x, y;
		double ratio;// 比例

		double totalspan = g1.get(toID).longTime - g1.get(fromID).longTime;// 获得从fromID到toID所经过的总的时间间隔
		double span;

		g1.get(fromID).isSave = true;// 该点被保留
		g1.get(toID).isSave = true; // 该点被保留
		for (i = fromID + 1; i < toID; i++) {

			span = g1.get(toID).longTime - g1.get(i).longTime;

			ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例

			x = g1.get(fromID).lon + ratio * (g1.get(toID).lon - g1.get(fromID).lon);
			y = g1.get(fromID).lat + ratio * (g1.get(toID).lat - g1.get(fromID).lat);
			// 线性插值出对应点坐标
			timeratiodis = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, x, y);// 获得时间同步欧式距离，GetDistance是一个简单的根据两点坐标计算距离的函数
			if (timeratiodis > maxtimeratiodis) {
				maxtimeratiodis = timeratiodis;
				maxindex = i; // maxindex用以记录具有最大距离的点的序号
			}
		}
		if (maxindex == -1) // 没有结果，则返回
			return;
		else if (maxtimeratiodis > threshold1) // 如果最大距离超过规定限差，下面进行递归调用。
		{
			g1.get(maxindex).isSave = true;
			stimeBenTR += g1.get(end1).longTime - g1.get(maxindex).longTime+g1.get(end1).sgpstime;
			TDDR2(g1, fromID, maxindex, threshold1);
			TDDR2(g1, maxindex, toID, threshold1);
		}
	}

	public static double returnStime() {
		return stimeBenTR;
	}
	static int end1;
	public static void getstime(int sd) {
		 end1=sd;
	}
	

	public static void initstime() {
		stimeBenTR=0;
		
	}
	public static void removeSimplifyPoint(List<GPSData> points) {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).isSave == false) {
				points.remove(points.get(i));
				i--;
			}
		}

	}

	/*
	 * 标准开窗算法NOPW
	 */
	public static void disOPW(List<GPSData> g1, double threshold1) {
		int fromID = 0;// 起始ID
		int toID = 2;// 终止ID

		int maxindex = -1;
		double timeratiodis, maxtimeratiodis = 0;
		double x, y;
		g1.get(0).isSave = true;
		g1.get(g1.size() - 1).isSave = true;

		while (toID < g1.size()) {
			for (int i = fromID; i < toID; i++) {
				double A = g1.get(fromID).lat - g1.get(toID).lat;
				double B = g1.get(toID).lon - g1.get(fromID).lon;
				double C = g1.get(fromID).lon * g1.get(toID).lat - g1.get(toID).lon * g1.get(fromID).lat;
				x = (double) (B * B * g1.get(i).lon - A * C - A * B * g1.get(i).lat) / (double) (A * A + B * B);
				y = (double) (-A * x - C) / (double) B;

				timeratiodis = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, x, y);

				if (timeratiodis > maxtimeratiodis) {
					maxtimeratiodis = timeratiodis;
					maxindex = i; // maxindex用以记录具有最大距离的点的序号
				}
			}

			if (maxtimeratiodis < threshold1) { // 没有结果，则移动窗口
				toID++;
			} else if (maxtimeratiodis >= threshold1) { // 如果最大距离超过规定限差
				g1.get(maxindex).isSave = true;
				maxtimeratiodis = 0;
				fromID = maxindex;
				toID = fromID + 2;
			}
		}
	}
	
	
	/*
	 * 标准开窗算法NOPW
	 */
	public static double disOPW2(List<GPSData> g1, double threshold1) {
		int fromID = 0;// 起始ID
		int toID = 2;// 终止ID
		double stime = 0;
		int maxindex = -1;
		double timeratiodis, maxtimeratiodis = 0;
		double x, y;
		g1.get(0).isSave = true;
		g1.get(g1.size() - 1).isSave = true;

		while (toID < g1.size()) {
			for (int i = fromID; i < toID; i++) {
				double A = g1.get(fromID).lat - g1.get(toID).lat;
				double B = g1.get(toID).lon - g1.get(fromID).lon;
				double C = g1.get(fromID).lon * g1.get(toID).lat - g1.get(toID).lon * g1.get(fromID).lat;
				x = (double) (B * B * g1.get(i).lon - A * C - A * B * g1.get(i).lat) / (double) (A * A + B * B);
				y = (double) (-A * x - C) / (double) B;

				timeratiodis = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, x, y);

				if (timeratiodis > maxtimeratiodis) {
					maxtimeratiodis = timeratiodis;
					maxindex = i; // maxindex用以记录具有最大距离的点的序号
				}
			}

			if (maxtimeratiodis < threshold1) { // 没有结果，则移动窗口
				toID++;
			} else if (maxtimeratiodis >= threshold1) { // 如果最大距离超过规定限差
				g1.get(maxindex).isSave = true;
				stime += g1.get(toID).longTime - g1.get(maxindex).longTime;
				maxtimeratiodis = 0;
				fromID = maxindex;
				toID = fromID + 2;
			}
		}
		return stime;
	}

	/*
	 * 标准开窗算法NOPW
	 */
	public static void timwOPW(List<GPSData> g1, double threshold1) {
		int fromID = 0;// 起始ID
		int toID = 2;// 终止ID

		int maxindex = -1;
		double timeratiodis, maxtimeratiodis = 0;
		double x, y;
		double ratio;
		double span;
		g1.get(0).isSave = true;
		g1.get(g1.size() - 1).isSave = true;

		while (toID < g1.size()) {
			for (int i = fromID; i < toID; i++) {
				double totalspan = g1.get(toID).longTime - g1.get(fromID).longTime;// 获得从fromID到toID所经过的总的时间间隔
				span = g1.get(toID).longTime - g1.get(i).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
				x = g1.get(fromID).lon + ratio * (g1.get(toID).lon - g1.get(fromID).lon);
				y = g1.get(fromID).lat + ratio * (g1.get(toID).lat - g1.get(fromID).lat);
				timeratiodis = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, x, y);

				if (timeratiodis > maxtimeratiodis) {
					maxtimeratiodis = timeratiodis;
					maxindex = i; // maxindex用以记录具有最大距离的点的序号
				}
			}

			if (maxtimeratiodis < threshold1) { // 没有结果，则移动窗口
				toID++;
			} else if (maxtimeratiodis >= threshold1) { // 如果最大距离超过规定限差
				g1.get(maxindex).isSave = true;

				maxtimeratiodis = 0;
				fromID = maxindex;
				toID = fromID + 2;
			}
		}

	}

	public static double timwOPW1(List<GPSData> g1, double threshold1) {
		int fromID = 0;// 起始ID
		int toID = 2;// 终止ID
		double stime = 0;
		int maxindex = -1;
		double timeratiodis, maxtimeratiodis = 0;
		double x, y;
		double ratio;
		double span;
		g1.get(0).isSave = true;
		g1.get(g1.size() - 1).isSave = true;

		while (toID < g1.size()) {
			for (int i = fromID; i < toID; i++) {
				double totalspan = g1.get(toID).longTime - g1.get(fromID).longTime;// 获得从fromID到toID所经过的总的时间间隔
				span = g1.get(toID).longTime - g1.get(i).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
				x = g1.get(fromID).lon + ratio * (g1.get(toID).lon - g1.get(fromID).lon);
				y = g1.get(fromID).lat + ratio * (g1.get(toID).lat - g1.get(fromID).lat);
				timeratiodis = Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat, x, y);

				if (timeratiodis > maxtimeratiodis) {
					maxtimeratiodis = timeratiodis;
					maxindex = i; // maxindex用以记录具有最大距离的点的序号
				}
			}

			if (maxtimeratiodis < threshold1) { // 没有结果，则移动窗口
				toID++;
			} else if (maxtimeratiodis >= threshold1) { // 如果最大距离超过规定限差
				g1.get(maxindex).isSave = true;
				stime += g1.get(toID).longTime - g1.get(maxindex).longTime;
				maxtimeratiodis = 0;
				fromID = maxindex;
				toID = fromID + 2;
			}
		}

		return stime/1000;

	}

	public static List<GPSData> SQUISH2(List<GPSData> g1, double radio, double threshold1) {

		int B = 4;
		List<Double> Q = new ArrayList<Double>();// 优先级队列
		List<Integer> id = new ArrayList<Integer>();

		List<GPSData> g2 = new ArrayList<GPSData>();
		GPSData g3 = new GPSData();
		for (int i = 0; i < g1.size(); i++) {
			if ((i + 1) / radio >= B) {
				B++;
			}
			Q.add(1000000.0);
			id.add(i);
			g1.get(i).pi = 0;
			if (i > 0) {
				g1.get(i - 1).succ = g1.get(i);
				g1.get(i).pred = g1.get(i - 1);
				double a = Reduce.adjustPriority1(g1.get(i - 1), g1.get(i - 1).pred, g1.get(i - 1).succ);
				if (g1.get(i - 1).pred != null && g1.get(i - 1).succ != null) {

					if (i < B) {
						Q.set(i - 1, Reduce.adjustPriority(g1.get(i - 1), g1.get(i - 1).pred, g1.get(i - 1).succ));
					} else {
						Q.set(Q.size() - 2, a + g1.get(i - 1).pi);
					}

				}

			}
			if (Q.size() == B) {
				Reduce.reduce(Q, id, g1);
			}
		}
		double p = Q.get(0);
		for (int a = 0; a < Q.size(); a++) {// 循环输出sed最小值
			if (p > Q.get(a)) {
				p = Q.get(a);
			}
		}
		while (p <= threshold1) {// 所有数据处理完毕后，使用小于μ的算法
			Reduce.reduce(Q, id, g1);
			p = Q.get(0);
			for (int a = 0; a < Q.size(); a++) {// 循环输出sed最小值
				if (p > Q.get(a)) {
					p = Q.get(a);
				}
			}
		}

		for (int i = 0; i < id.size(); i++) {
			g3 = g1.get(id.get(i));
			g2.add(g3);
		}

		return g2;

	}

	public static void reduce(List<Double> Q, List<Integer> id, List<GPSData> g1) {
		double MIN = Q.get(0);
		int minid = 0;
		for (int i = 0; i < Q.size(); i++) {// 循环找最小sed
			if (MIN > Q.get(i)) {
				MIN = Q.get(i);
				minid = i;
			}
		}
		int pointID = id.get(minid);
		g1.get(pointID).succ.pi = Math.max(Q.get(minid), g1.get(pointID).succ.pi);
		g1.get(pointID).pred.pi = Math.max(Q.get(minid), g1.get(pointID).pred.pi);
		(g1.get(pointID).pred).succ = g1.get(pointID).succ;
		(g1.get(pointID).succ).pred = g1.get(pointID).pred;

		if (minid - 1 == 0) {// 删除的是第二个点
			Q.set(minid + 1, Reduce.adjustPriority(g1.get(pointID).succ, (g1.get(pointID).succ).pred,
					(g1.get(pointID).succ).succ));
		} else if (minid == Q.size() - 2) {
			Q.set(minid - 1, Reduce.adjustPriority(g1.get(pointID).pred, (g1.get(pointID).pred).pred,
					(g1.get(pointID).pred).succ));
		} else {
			Q.set(minid - 1, Reduce.adjustPriority(g1.get(pointID).pred, (g1.get(pointID).pred).pred,
					(g1.get(pointID).pred).succ));
			Q.set(minid + 1, Reduce.adjustPriority(g1.get(pointID).succ, (g1.get(pointID).succ).pred,
					(g1.get(pointID).succ).succ));
		}

		Q.remove(minid);
		id.remove(minid);

	}

	public static double adjustPriority(GPSData gg, GPSData gg2, GPSData gg3) {
		double p = 0;
		if (gg.pred != null && gg.succ != null) {
			double radio = (gg.longTime - gg2.longTime) / (gg3.longTime - gg2.longTime);
			double x = gg.lon + radio * (gg3.lon - gg2.lon);
			double y = gg.lat + radio * (gg3.lat - gg2.lat);
			double dis = Mathf.GetDistance(x, y, gg.lon, gg.lat);
			p = gg.pi + dis;
		}

		return p;

	}

	public static double adjustPriority1(GPSData gg, GPSData gg2, GPSData gg3) {
		double dis = 0;
		if (gg.pred != null && gg.succ != null) {
			double radio = (gg.longTime - gg2.longTime) / (gg3.longTime - gg2.longTime);
			double x = gg.lon + radio * (gg3.lon - gg2.lon);
			double y = gg.lat + radio * (gg3.lat - gg2.lat);
			dis = Mathf.GetDistance(x, y, gg.lon, gg.lat);

		}

		return dis;

	}

	// SQUISH算法
	public static void SQUISH1(double[][] EE, boolean[] BR, double radio, double μ) {
		int i = 0, l = 0;
		int β = 4;
		int line = EE.length;
		int L = (int) (line / radio);// 缓冲区大小
		ArrayList<Double> Q = new ArrayList<Double>(β);// 存储各个点的sed值
		ArrayList<Double> PI = new ArrayList<Double>(line);// 各个点的Pi值
		ArrayList<Integer> poi = new ArrayList<Integer>(L);// 存储Q中各点对应的轨迹点位置
		int[] succ = new int[line];// 各个点的后继点是谁
		int[] pred = new int[line];// 各个点的前继点是谁

		while (i < line) {
			if ((i + 1) / radio >= β) {
				β = β + 1;
			}
			Q.add(1000000.0);
			poi.add(i);
			PI.add(0.0);
			succ[i] = i;
			pred[i] = i;
			if (i == 1) {
				succ[0] = 1;
				pred[1] = 0;
			} else if (i > 1) {
				succ[i - 1] = i;
				pred[i] = i - 1;
				double a = Reduce.adjust1(i - 1, EE, succ, pred, PI);
				if (i < β) {
					Q.set(i - 1, a + PI.get(i - 1));
				} else {
					Q.set(Q.size() - 2, a + PI.get(PI.size() - 2));
				}

			}
			if (β == Q.size()) {
				Reduce.reduce1(Q, EE, succ, pred, PI, poi);
			}
			i++;
		}
		double p = Q.get(0);
		for (int a = 0; a < Q.size(); a++) {// 循环输出sed最小值
			if (p > Q.get(a)) {
				p = Q.get(a);
			}
		}
		while (p < μ) {// 所有数据处理完毕后，使用小于μ的算法
			Reduce.reduce1(Q, EE, succ, pred, PI, poi);
			p = Q.get(0);
			for (int a = 0; a < Q.size(); a++) {// 循环输出sed最小值
				if (p > Q.get(a)) {
					p = Q.get(a);
				}
			}
		}

		for (int k = 0; k < Q.size(); k++) {// 设置保留点为真
			BR[poi.get(k)] = true;
		}
	}

	private static double adjust1(int j, double[][] EE, int[] succ, int[] pred, ArrayList<Double> PI) {
		double dis = 0;
		if (pred[j] >= 0 && succ[j] < EE.length) {
			double radio = (EE[j][0] - EE[pred[j]][0]) / (EE[succ[j]][0] - EE[pred[j]][0]);
			double x = EE[pred[j]][1] + radio * (EE[succ[j]][1] - EE[pred[j]][1]);
			double y = EE[pred[j]][2] + radio * (EE[succ[j]][2] - EE[pred[j]][2]);
			dis = Mathf.GetDistance(x, y, EE[j][1], EE[j][2]);
		}
		return dis;
	}

	private static void reduce1(ArrayList<Double> Q, double[][] EE, int[] succ, int[] pred, ArrayList<Double> PI,
			ArrayList<Integer> poi) {
		double MIN = Q.get(0);
		int min = 0;
		for (int i = 0; i < Q.size(); i++) {// 循环找最小sed
			// System.out.print(PI.get(i)+",");
			if (MIN > Q.get(i)) {
				MIN = Q.get(i);
				min = i;
			}
		}
		succ[poi.get(min - 1)] = poi.get(min + 1);
		pred[poi.get(min + 1)] = poi.get(min - 1);
		if (min - 1 == 0) {// 删除的是第二个点
			// System.out.println("1DIS后:"+Guaiwan.adjust(poi.get(min+1), EE, succ, pred,
			// PI)+","+succ[poi.get(min+1)]+","+pred[poi.get(min+1)]);
			if (Q.get(min) >= PI.get(min + 1)) {
				PI.set(min + 1, Q.get(min));
			}
			Q.set(min + 1, PI.get(min + 1) + Reduce.adjust1(poi.get(min + 1), EE, succ, pred, PI));
			// Q.set(min + 1, Reduce.adjust(poi.get(min + 1), EE, succ, pred, PI));
		} else if (min == (Q.size() - 2)) {// 删除倒数第二个点
			// System.out.println("2DIS前:"+Guaiwan.adjust(poi.get(min-1), EE, succ, pred,
			// PI)+","+succ[poi.get(min-1)]+","+pred[poi.get(min-1)]);
			if (Q.get(min) >= PI.get(min + 1)) {
				PI.set(min + 1, Q.get(min));
			}
			if (Q.get(min) >= PI.get(min - 1)) {
				PI.set(min - 1, Q.get(min));
			}
			Q.set(min - 1, PI.get(min - 1) + Reduce.adjust1(poi.get(min - 1), EE, succ, pred, PI));
			// Q.set(min - 1, Reduce.adjust(poi.get(min - 1), EE, succ, pred, PI));
		} else {
			// System.out.println("3DIS前:"+Guaiwan.adjust(poi.get(min-1), EE, succ, pred,
			// PI)+","+succ[poi.get(min-1)]+","+pred[poi.get(min-1)]);
			// System.out.println("3DIS后:"+Guaiwan.adjust(poi.get(min+1), EE, succ, pred,
			// PI)+","+succ[poi.get(min+1)]+","+pred[poi.get(min+1)]);
			if (Q.get(min) >= PI.get(min + 1)) {
				PI.set(min + 1, Q.get(min));
			}
			if (Q.get(min) >= PI.get(min - 1)) {
				PI.set(min - 1, Q.get(min));
			}
			Q.set(min - 1, PI.get(min - 1) + Reduce.adjust1(poi.get(min - 1), EE, succ, pred, PI));
			Q.set(min + 1, PI.get(min + 1) + Reduce.adjust1(poi.get(min + 1), EE, succ, pred, PI));
			// Q.set(min - 1, Reduce.adjust(poi.get(min - 1), EE, succ, pred, PI));
			// Q.set(min + 1, Reduce.adjust(poi.get(min + 1), EE, succ, pred, PI));
		}
		// SUMM=poi.get(Q.size()-1)-poi.get(min);//计算延迟时间
		// System.out.println(SUMM);
		Q.remove(min);
		PI.remove(min);
		poi.remove(min);
	}

	// SQUISH算法
	public static void SQUISH2(double[][] EE, boolean[] BR, double radio, double μ) {
		int i = 0, l = 0;
		int β = 4;
		int line = EE.length;
		int L = (int) (line / radio);// 缓冲区大小
		ArrayList<Double> Q = new ArrayList<Double>(β);// 存储各个点的sed值
		ArrayList<Double> PI = new ArrayList<Double>(line);// 各个点的Pi值
		ArrayList<Integer> poi = new ArrayList<Integer>(L);// 存储Q中各点对应的轨迹点位置
		int[] succ = new int[line];// 各个点的后继点是谁
		int[] pred = new int[line];// 各个点的前继点是谁

		while (i < line) {
			if ((i + 1) / radio >= β) {
				β = β + 1;
			}
			Q.add(1000000.0);
			poi.add(i);
			PI.add(0.0);
			succ[i] = i;
			pred[i] = i;
			if (i == 1) {
				succ[0] = 1;
				pred[1] = 0;
			} else if (i > 1) {
				succ[i - 1] = i;
				pred[i] = i - 1;
				double a = Reduce.adjust2(i - 1, EE, succ, pred, PI);
				if (i < β) {
					Q.set(i - 1, a + PI.get(i - 1));
				} else {
					Q.set(Q.size() - 2, a + PI.get(PI.size() - 2));
				}

			}
			if (β == Q.size()) {
				Reduce.reduce2(Q, EE, succ, pred, PI, poi);
			}
			i++;
		}
		double p = Q.get(0);
		for (int a = 0; a < Q.size(); a++) {// 循环输出sed最小值
			if (p > Q.get(a)) {
				p = Q.get(a);
			}
		}
		while (p < μ) {// 所有数据处理完毕后，使用小于μ的算法
			Reduce.reduce2(Q, EE, succ, pred, PI, poi);
			p = Q.get(0);
			for (int a = 0; a < Q.size(); a++) {// 循环输出sed最小值
				if (p > Q.get(a)) {
					p = Q.get(a);
				}
			}
		}

		for (int k = 0; k < Q.size(); k++) {// 设置保留点为真
			BR[poi.get(k)] = true;
		}
	}

	private static double adjust2(int j, double[][] EE, int[] succ, int[] pred, ArrayList<Double> PI) {
		double dis = 0;
		if (pred[j] >= 0 && succ[j] < EE.length) {
			double radio = (EE[j][0] - EE[pred[j]][0]) / (EE[succ[j]][0] - EE[pred[j]][0]);
			double x = EE[pred[j]][1] + radio * (EE[succ[j]][1] - EE[pred[j]][1]);
			double y = EE[pred[j]][2] + radio * (EE[succ[j]][2] - EE[pred[j]][2]);
			dis = Mathf.GetDistance(x, y, EE[j][1], EE[j][2]);
		}
		return dis;
	}

	private static void reduce2(ArrayList<Double> Q, double[][] EE, int[] succ, int[] pred, ArrayList<Double> PI,
			ArrayList<Integer> poi) {
		double MIN = Q.get(0);
		int min = 0;
		for (int i = 0; i < Q.size(); i++) {// 循环找最小sed
			// System.out.print(PI.get(i)+",");
			if (MIN > Q.get(i)) {
				MIN = Q.get(i);
				min = i;
			}
		}
		succ[poi.get(min - 1)] = poi.get(min + 1);
		pred[poi.get(min + 1)] = poi.get(min - 1);
		if (min - 1 == 0) {// 删除的是第二个点
			// System.out.println("1DIS后:"+Guaiwan.adjust(poi.get(min+1), EE, succ, pred,
			// PI)+","+succ[poi.get(min+1)]+","+pred[poi.get(min+1)]);
			if (Q.get(min) >= PI.get(min + 1)) {
				PI.set(min + 1, Q.get(min));
			}
			// Q.set(min+1, PI.get(min+1)+Reduce.adjust(poi.get(min+1), EE, succ, pred,
			// PI));
			Q.set(min + 1, Reduce.adjust2(poi.get(min + 1), EE, succ, pred, PI));
		} else if (min == (Q.size() - 2)) {// 删除倒数第二个点
			// System.out.println("2DIS前:"+Guaiwan.adjust(poi.get(min-1), EE, succ, pred,
			// PI)+","+succ[poi.get(min-1)]+","+pred[poi.get(min-1)]);
			if (Q.get(min) >= PI.get(min + 1)) {
				PI.set(min + 1, Q.get(min));
			}
			if (Q.get(min) >= PI.get(min - 1)) {
				PI.set(min - 1, Q.get(min));
			}
			// Q.set(min-1, PI.get(min-1)+Reduce.adjust(poi.get(min-1), EE, succ, pred,
			// PI));
			Q.set(min - 1, Reduce.adjust2(poi.get(min - 1), EE, succ, pred, PI));
		} else {
			// System.out.println("3DIS前:"+Guaiwan.adjust(poi.get(min-1), EE, succ, pred,
			// PI)+","+succ[poi.get(min-1)]+","+pred[poi.get(min-1)]);
			// System.out.println("3DIS后:"+Guaiwan.adjust(poi.get(min+1), EE, succ, pred,
			// PI)+","+succ[poi.get(min+1)]+","+pred[poi.get(min+1)]);
			if (Q.get(min) >= PI.get(min + 1)) {
				PI.set(min + 1, Q.get(min));
			}
			if (Q.get(min) >= PI.get(min - 1)) {
				PI.set(min - 1, Q.get(min));
			}
//							Q.set(min-1, PI.get(min-1)+Reduce.adjust(poi.get(min-1), EE, succ, pred, PI));
//							Q.set(min+1, PI.get(min+1)+Reduce.adjust(poi.get(min+1), EE, succ, pred, PI));			    
			Q.set(min - 1, Reduce.adjust2(poi.get(min - 1), EE, succ, pred, PI));
			Q.set(min + 1, Reduce.adjust2(poi.get(min + 1), EE, succ, pred, PI));
		}
		// SUMM=poi.get(Q.size()-1)-poi.get(min);//计算延迟时间
		// System.out.println(SUMM);
		Q.remove(min);
		PI.remove(min);
		poi.remove(min);
	}

	public static List<GPSData> getSQPoints(double[][] EE, boolean[] BR) {
		List<GPSData> g2 = new ArrayList<GPSData>();
		GPSData g3 = new GPSData();
		for (int i = 0; i < BR.length; i++) {
			if (BR[i] == true) {
				g3.longTime = EE[i][0];
				g3.lon = EE[i][1];
				g3.lat = EE[i][2];
				g3.GPSID = i;
				g2.add(g3);
				g3 = new GPSData();
			}
		}

		return g2;

	}




}
