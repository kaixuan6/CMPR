
package zhuanwan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.imageio.event.IIOReadWarningListener;
import javax.swing.event.AncestorEvent;
import javax.xml.crypto.Data;

public class judgeDir {

	
	public static List<GPSData> getAngal3(List<GPSData> gg, double middle) {

		// Calculation speed
		double speed1 = 0;
		for (int i = 1; i < gg.size(); i++) {
			speed1 = (Mathf.GetDistance(gg.get(i - 1).lon, gg.get(i - 1).lat, gg.get(i).lon, gg.get(i).lat)
					/ (gg.get(i).longTime - gg.get(i - 1).longTime)) * 1000;
			if (speed1 < 1) {
				gg.get(i).speed = 0;
			} else {
				gg.get(i).speed = speed1;
			}
		}
		//the turning mode of each point is calculated 
		double ax, ay, bx, by;
		for (int i = 1; i < gg.size() - 1; i++) {
			if (gg.get(i).speed != 0) {
				ax = gg.get(i).lon - gg.get(i - 1).lon;
				ay = gg.get(i).lat - gg.get(i - 1).lat;
				bx = gg.get(i + 1).lon - gg.get(i - 1).lon;
				by = gg.get(i + 1).lat - gg.get(i - 1).lat;
				if ((ax * by - ay * bx) < 0) {
					gg.get(i).angle1 = -1;
				} else {
					gg.get(i).angle1 = 1;
				}
			} else {
				gg.get(i).angle1 = 0;
			}
		}

		List<Integer> d3 = new ArrayList<Integer>();
		int label1 = 0;
		int label2 = 0;
		int lastFeaturePoint = 0;
		List<Integer> fPoints = new ArrayList<Integer>();
		fPoints.add(0);
		double x2 = 0, y2 = 0;
		lastFeaturePoint = fPoints.get(fPoints.size() - 1);
		List<Integer> A = new ArrayList<Integer>();
		List<Integer> B = new ArrayList<Integer>();

		List<GPSData> youzhuan = new ArrayList<GPSData>();
		List<GPSData> zuozhuan = new ArrayList<GPSData>();
		List<GPSData> diaotou = new ArrayList<GPSData>();

		List<GPSData> jiasu = new ArrayList<GPSData>();
		List<GPSData> jiansu = new ArrayList<GPSData>();
		List<GPSData> tingliu = new ArrayList<GPSData>();

		List<allTurn> aq1 = new ArrayList<allTurn>();
		for (int i = 1; i < gg.size() - 1; i++) {

			//he consecutive trajectory points with the same turning mode are recorded as a turning sequence
			if (gg.get(i).angle1 != 0) {
				if ((gg.get(i).angle1 * gg.get(i + 1).angle1) > 0) {
					d3.add(i);
				} else {
					d3.add(i);
					label1 = 1;
				}
			} else if (gg.get(i).angle1 == 0) {
				if (gg.get(i + 1).angle1 == 0) {
					d3.add(i);
				} else {
					d3.add(i);
					label1 = 1;
				}
			}
// If the current turning sequence is finished 
			if (label1 == 1) {

				allTurn d2 = new allTurn();
				double totalspan, span, ratio, x, y;

				d2.gpsStartID = d3.get(0) - 1;
				d2.gpsEndID = d3.get(d3.size() - 1) + 1;
				aq1.add(d2);
				totalspan = gg.get(d2.gpsEndID).longTime - gg.get(lastFeaturePoint).longTime;
				double dis = -1, dis1 = 0;

				for (int j = lastFeaturePoint + 1; j < d2.gpsEndID; j++) {
					span = gg.get(d2.gpsEndID).longTime - gg.get(j).longTime;
					ratio = 1 - span / totalspan; 
					x = gg.get(lastFeaturePoint).lon + ratio * (gg.get(d2.gpsEndID).lon - gg.get(lastFeaturePoint).lon);
					y = gg.get(lastFeaturePoint).lat + ratio * (gg.get(d2.gpsEndID).lat - gg.get(lastFeaturePoint).lat);
					dis1 = (gg.get(j).lon - x) * (gg.get(j).lon - x) + (gg.get(j).lat - y) * (gg.get(j).lat - y);

					if (dis1 > dis) {
						dis = dis1;
						x2 = x;
						y2 = y;
						d2.maxGPSID = j;
					}
				}
				dis1 = Mathf.GetDistance(gg.get(d2.maxGPSID).lon, gg.get(d2.maxGPSID).lat, x2, y2);
				if (dis1 > middle) {
//the turning feature points are extracted
					fPoints.add(d2.maxGPSID);
					lastFeaturePoint = d2.maxGPSID;
				}
				d3.clear();
				label1 = 0;
				d2 = new allTurn();
			}

			if (A.size() < 2) {
				A.add(i);
			} else {
// the speed increase sequence  and the speed decrease sequence  are extracted 
				if (label2 == 0) {
					if ((gg.get(i).speed - gg.get(A.get(A.size() - 1)).speed)
							* (gg.get(i + 1).speed - gg.get(i).speed) >= 0) {
						A.add(i);
					} else {
						A.add(i);
						B.add(i);
						label2++;
					}
				} else if (label2 == 1) {
					if ((gg.get(i).speed - gg.get(B.get(B.size() - 1)).speed)
							* (gg.get(i + 1).speed - gg.get(i).speed) >= 0) {
						B.add(i);
					} else {
						B.add(i);
						label2++;
					}
				}

			}

			//If the current variable speed sequence is finished
			if (label2 == 2) {

				allTurn d2 = new allTurn();
				double totalspan, span, ratio, x, y;
				d2.gpsStartID = A.get(0);
				d2.gpsEndID = B.get(B.size() - 1);
				totalspan = gg.get(d2.gpsEndID).longTime - gg.get(d2.gpsStartID).longTime;
				double dis = -1, dis1 = 0;
				for (int j = d2.gpsStartID + 1; j < d2.gpsEndID; j++) {
					span = gg.get(d2.gpsEndID).longTime - gg.get(j).longTime;
					ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
					x = gg.get(d2.gpsStartID).lon + ratio * (gg.get(d2.gpsEndID).lon - gg.get(d2.gpsStartID).lon);
					y = gg.get(d2.gpsStartID).lat + ratio * (gg.get(d2.gpsEndID).lat - gg.get(d2.gpsStartID).lat);
					dis1 = (gg.get(j).lon - x) * (gg.get(j).lon - x) + (gg.get(j).lat - y) * (gg.get(j).lat - y);

					if (dis1 > dis) {
						dis = dis1;
						d2.maxGPSID = j;
					}
				}
				if (lastFeaturePoint < d2.maxGPSID) {
					totalspan = gg.get(d2.gpsEndID).longTime - gg.get(lastFeaturePoint).longTime;
					span = gg.get(d2.gpsEndID).longTime - gg.get(d2.maxGPSID).longTime;
					ratio = 1 - span / totalspan; // 获得该点到起始前一段的时间比例
					x = gg.get(lastFeaturePoint).lon + ratio * (gg.get(d2.gpsEndID).lon - gg.get(lastFeaturePoint).lon);
					y = gg.get(lastFeaturePoint).lat + ratio * (gg.get(d2.gpsEndID).lat - gg.get(lastFeaturePoint).lat);
					dis1 = Mathf.GetDistance(gg.get(d2.maxGPSID).lon, gg.get(d2.maxGPSID).lat, x, y);
					if (dis1 > middle) {
					//	the variable speed feature points is extracted from the current variable speed sequence,
						fPoints.add(d2.maxGPSID);
						double anglw = Mathf.getDegree2(gg.get(A.get(0)).lon, gg.get(A.get(0)).lat,
								gg.get(A.get(1)).lon, gg.get(A.get(1)).lat, gg.get(B.get(B.size() - 2)).lon,
								gg.get(B.get(B.size() - 2)).lat, gg.get(B.get(B.size() - 1)).lon,
								gg.get(B.get(B.size() - 1)).lat);
						lastFeaturePoint = d2.maxGPSID;
					
						if (Math.abs(anglw) < 10) {
							if (gg.get(A.get(0)).speed >= gg.get(A.get(1)).speed) {
								// 先减速
								if (gg.get(d2.maxGPSID).speed != 0) {
									jiansu.add(gg.get(d2.maxGPSID));
								} else {
									tingliu.add(gg.get(d2.maxGPSID));
								}

							} else {
								if (gg.get(d2.maxGPSID).speed != 0) {
									jiasu.add(gg.get(d2.maxGPSID));
								} else {
									tingliu.add(gg.get(d2.maxGPSID));
								}
							}
						}
						else if (Math.abs(anglw) > 20)  {
							if (anglw > 20 && anglw < 150) {
								youzhuan.add(gg.get(d2.maxGPSID));
							} else if (anglw < -20 && anglw > -150) {
								zuozhuan.add(gg.get(d2.maxGPSID));
							} else if (Math.abs(anglw) > 150) {
								diaotou.add(gg.get(d2.maxGPSID));
							}
						}
						

					}
				}

				A.clear();
				A.addAll(B);
				B.clear();
				B.add(A.get(A.size() - 1));
				label2 = 1;
				d2 = new allTurn();
			}

		}

		fPoints.add(gg.size() - 1);

		for (int j = 0; j < fPoints.size() - 1; j++) {
			Reduce.TDDR(gg, fPoints.get(j), fPoints.get(j + 1), middle);
		}

		List<GPSData> g2 = new ArrayList<GPSData>();
		for (int k = 0; k < gg.size(); k++) {
			if (gg.get(k).isSave == true) {
				g2.add(gg.get(k));
			}
		}

		for (int i = 1; i < g2.size() - 1; i++) {
			if (g2.get(i).speed == 0) {
				tingliu.add(g2.get(i));
				continue;
			}

			for (int j = 0; j < aq1.size(); j++) {
				if (g2.get(i).GPSID < aq1.get(j).maxGPSID && g2.get(i).GPSID > aq1.get(j).gpsStartID) {
					double anglw = Mathf.getDegree2(gg.get(aq1.get(j).gpsStartID).lon,
							gg.get(aq1.get(j).gpsStartID).lat, gg.get(aq1.get(j).gpsStartID + 1).lon,
							gg.get(aq1.get(j).gpsStartID + 1).lat, gg.get(aq1.get(j).gpsEndID - 1).lon,
							gg.get(aq1.get(j).gpsEndID - 1).lat, gg.get(aq1.get(j).gpsEndID).lon,
							gg.get(aq1.get(j).gpsEndID).lat);
					if (anglw > 10 && anglw < 150) {
						youzhuan.add(g2.get(i));
					} else if (anglw < -10 && anglw > -150) {
						zuozhuan.add(g2.get(i));
					} else if (Math.abs(anglw) > 150) {
						diaotou.add(g2.get(i));
					}
				}
			}

		}
//		consoleData.getTDGPSPoints0(zuozhuan, "左转.txt");
//		consoleData.getTDGPSPoints0(youzhuan, "右转.txt");
//		consoleData.getTDGPSPoints0(diaotou, "掉头.txt");
//		consoleData.getTDGPSPoints0(jiasu, "加速.txt");
//		consoleData.getTDGPSPoints0(jiansu, "减速.txt");
//		consoleData.getTDGPSPoints0(tingliu, "停留.txt");

		return g2;
	}



}
