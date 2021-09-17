package zhuanwan;

import java.util.List;

public class culErrorDis {

	// 垂直偏差距离
	public static double getDeDistance(List<GPSData> d1, List<GPSData> d2) {

		double deviationDistance1 = 0;
		double deviationDistance2 = 0;
		double deviationDistance3 = 0;
		for (int i = 1; i < d2.size(); i++) {
			int a = d2.get(i - 1).GPSID;
			int b = d2.get(i).GPSID;

			double A = d2.get(i - 1).lon - d2.get(i).lon;
			double B = d2.get(i).lat - d2.get(i - 1).lat;
			double C = d2.get(i - 1).lat * d2.get(i).lon - d2.get(i).lat * d2.get(i - 1).lon;

			for (int j = a + 1; j < b; j++) {
				double x, y;
				if (A == 0 && B == 0) {

					x = d2.get(i - 1).lon;
					y = d2.get(i - 1).lat;
				} else {
					x = (double) (A * A * d1.get(j).lon - A * B * d1.get(j).lat - B * C) / (A * A + B * B);// 经度
					y = (double) (B * B * d1.get(j).lat - A * B * d1.get(j).lon - A * C) / (A * A + B * B);// 纬度

				}

				deviationDistance1 += Mathf.GetDistance(d1.get(j).lon, d1.get(j).lat, x, y);
				deviationDistance3 = Mathf.GetDistance(d1.get(j).lon, d1.get(j).lat, x, y);

				if (deviationDistance2 < deviationDistance3) {
					deviationDistance2 = deviationDistance3;
				}
			}
		}

		double a3 = 0;
		a3 = deviationDistance1 / d1.size();
		return a3;

	}

	// 计算时空偏差距离
	public static double getTimeDistance(List<GPSData> d1, List<GPSData> d2) {

		double deviationDistance1 = 0;
		double deviationDistance2 = 0;
		double deviationDistance3 = 0;
		GPSData gg1 = new GPSData();
		for (int i = 0; i < d2.size() - 1; i++) {
			int a = d2.get(i).GPSID;
			int b = d2.get(i + 1).GPSID;
			double ratio = 0;
			double span = 0;
			double totalspan = 0;

			totalspan = d1.get(b).longTime - d1.get(a).longTime;// 获得从fromID到toID所经过的总的时间间隔

			for (int j = a + 1; j < b; j++) {

				span = d1.get(b).longTime - d1.get(j).longTime;
				ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例

				gg1.lon = d1.get(a).lon + ratio * (d1.get(b).lon - d1.get(a).lon);
				gg1.lat = d1.get(a).lat + ratio * (d1.get(b).lat - d1.get(a).lat);
				deviationDistance1 += Mathf.GetDistance(d1.get(j).lon, d1.get(j).lat, gg1.lon, gg1.lat);
				deviationDistance3 = Mathf.GetDistance(d1.get(j).lon, d1.get(j).lat, gg1.lon, gg1.lat);
				if (deviationDistance2 < deviationDistance3) {
					deviationDistance2 = deviationDistance3;
				}
			}
		}
		double a3 = 0;
		a3 = deviationDistance1 / d1.size();
		return a3;

	}

}
