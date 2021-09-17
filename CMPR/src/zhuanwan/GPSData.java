package zhuanwan;

import java.util.List;

public class GPSData {
	public int traID;

	public String traID1;
	public int GPSID;//ID
	public double dateGPS;//日期
	public String timeGPS;//时间
	public double longTime;//时间戳
	public boolean isSave;//该点是否保存
	public double sgpstime;//该点确定延时
	public double lon; //经度
	public double lat; //维度
	public double alt;//海拔
	public double angle1;//根据经纬度计算该点夹角
	public double angle2;//该秒内方向角数据
	public double angle3;//该秒内方向角数据
	public double acc;//该秒内加速度
	public double speed;//该点速度
	public List<Double> ddd;//存储方向角数据

	public boolean isStayPoint;//是否是停留点
	public boolean isVisit;//是否访问过该点
	public int cluster;//类别
	public boolean isNoised;//是否是噪音点


	public double priority;//优先级
	public GPSData succ;//后继
	public GPSData pred;//前驱
	public double pi;

	public GPSData() {

	}

	public  GPSData(String timeGPS, double longTime,double lon ,double lat,double alt) {

		this.timeGPS=timeGPS;
		this.longTime=longTime;
		this.lon=lon;
		this.lat=lat;
		this.alt=alt;

	}
	public  GPSData(double longTime,String timeGPS,double lon ,double lat) {

		this.timeGPS=timeGPS;
		this.longTime=longTime;
		this.lon=lon;
		this.lat=lat;	
	}

	public GPSData(double x, double y1) {
		// TODO 自动生成的构造函数存根
		this.lon=x;
		this.lat=y1;
	}
	public  GPSData(Integer traID, double longTime,double lon ,double lat) {

		this.traID=traID;
		this.longTime=longTime;
		this.lon=lon;
		this.lat=lat;	
	}
	public  GPSData(String traID1, double longTime,double lon ,double lat) {

		this.traID1=traID1;
		this.longTime=longTime;
		this.lon=lon;
		this.lat=lat;	
	}



	public  GPSData(double longTime,double lon ,double lat) {


		this.longTime=longTime;
		this.lon=lon;
		this.lat=lat;	
	}




}
