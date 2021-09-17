package zhuanwan;

import java.util.List;

//坐标转换
public class coorTra {
	
	private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	//pai
	private static double pi = 3.1415926535897932384626;
	//离心率
	private static double ee = 0.00669342162296594323;
	//长半轴
	private static double a = 6378245.0;
	//百度转国测局
	public static double[] bd09togcj02(double bd_lon, double bd_lat) {
		double x = bd_lon - 0.0065;
		double y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gg_lng = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		return new double[] { gg_lng, gg_lat };
	}
	//国测局转百度
	public static double[] gcj02tobd09(double lng, double lat) {
		double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_pi);
		double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_pi);
		double bd_lng = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return new double[] { bd_lng, bd_lat };
	}
	//国测局转84
	public static double[] gcj02towgs84(double lng, double lat) {
		double dlat = transformlat(lng - 105.0, lat - 35.0);
		double dlng = transformlng(lng - 105.0, lat - 35.0);
		double radlat = lat / 180.0 * pi;
		double magic = Math.sin(radlat);
		magic = 1 - ee * magic * magic;
		double sqrtmagic = Math.sqrt(magic);
		dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * pi);
		dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * pi);
		double mglat = lat + dlat;
		double mglng = lng + dlng;
		return new double[] { lng * 2 - mglng, lat * 2 - mglat };
	}
	//经度转换
	public static double transformlat(double lng, double lat) {
		double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
		ret += (20.0 * Math.sin(6.0 * lng * pi) + 20.0 * Math.sin(2.0 * lng * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lat * pi) + 40.0 * Math.sin(lat / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(lat / 12.0 * pi) + 320 * Math.sin(lat * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}
	//纬度转换
	public static double transformlng(double lng, double lat) {
		double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
		ret += (20.0 * Math.sin(6.0 * lng * pi) + 20.0 * Math.sin(2.0 * lng * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lng * pi) + 40.0 * Math.sin(lng / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(lng / 12.0 * pi) + 300.0 * Math.sin(lng / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}


	//1 使用说明 （bd09->wgs84 ）
	public static double[] getWgs84xy(double x, double y){
		//先转 国测局坐标

		double[] doubles_gcj = bd09togcj02(x, y);//（x 117. y 36. ）

		//国测局坐标转wgs84

		double[] doubles_wgs84 =gcj02towgs84(doubles_gcj[0], doubles_gcj[1]);

		//返回 纠偏后 坐标
		return doubles_wgs84;
	}



	//输入GCJ经纬度 转WGS纬度
	public static double WGSLat (double lat,double lon) {
		double PI = 3.14159265358979324;//圆周率
		double a = 6378245.0;//克拉索夫斯基椭球参数长半轴a
		double ee = 0.00669342162296594323;//克拉索夫斯基椭球参数第一偏心率平方
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
		return (lat - dLat);
	}

	//输入GCJ经纬度 转WGS经度
	public static double WGSLon (double lat,double lon) {
		double PI = 3.14159265358979324;//圆周率
		double a = 6378245.0;//克拉索夫斯基椭球参数长半轴a
		double ee = 0.00669342162296594323;//克拉索夫斯基椭球参数第一偏心率平方
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
		return (lon - dLon);
	}

	//转换经度所需
	public static double transformLon(double x, double y) {
		double PI = 3.14159265358979324;//圆周率
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}
	//转换纬度所需
	public static double transformLat(double x, double y) {
		double PI = 3.14159265358979324;//圆周率
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}
	
	public static  List<GPSData> coorTransDatas(List<GPSData> d1 ) {
		double [] dd1;
		for (int i=0;i<d1.size();i++)
		{
			
			dd1=getWgs84xy(d1.get(i).lat,d1.get(i).lon);
			d1.get(i).lat=dd1[0];
			d1.get(i).lon=dd1[1];
		}
		
		return d1;
		
	}


}
