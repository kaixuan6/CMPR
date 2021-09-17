package zhuanwan;


import java.util.List;



//与计算有关的方法
public class Mathf {
	//由经纬度计算距离
	public static double GetDistance(double lon1,double lat1,double lon2, double lat2)
	{
		final double EARTH_RADIS = 6378137;  //地球半径，单位米
		double radlng1 = Rad(lon1);
		double radlat1 = Rad(lat1);
		double radlng2 = Rad(lon2);
		double radlat2 = Rad(lat2);
		double a = radlat1 - radlat2;
		double b = radlng1 - radlng2;
		double result = (float) ( 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radlat2) * Math.cos(radlat2) * Math.pow(Math.sin(b / 2), 2))) * EARTH_RADIS);
		return result;
	}
	public static double GetDistance2(double lon1,double lat1,double lon2, double lat2)
	{
		double a1=(lon1-lon2);
		double a2=(lat1-lat2);
		return a1*a1+a2*a2;
	}
	//根据四点坐标计算两向量夹角
			public static double getDegree3(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

				double ax=x2-x1;
				double ay=y2-y1;
				double bx= x4-x3;
				double by=y4-y3;

				double value = (ax * bx + ay * by) / (Math.sqrt(ax * ax + ay * ay) * Math.sqrt(bx * bx + by * by)); // 余弦值
				return Math.toDegrees(Math.acos(value));   // 角度	
			

			}
	//将经纬度转化为弧度
	public static double Rad(double d)
	{
		return (double) d * Math.PI / 180d;
	}

	//根据三点坐标计算中间点夹角
	public static double getDegree(double vertexPointX, double vertexPointY, double point0X, double point0Y, double point1X, double point1Y) {

		double ax=vertexPointX-point0X;
		double ay=vertexPointY-point0Y;
		double bx= point1X-vertexPointX;
		double by=point1Y-vertexPointY;

		//向量的点乘
		double vector = (point0X - vertexPointX) * (point1X - vertexPointX) + (point0Y - vertexPointY) * (point1Y - vertexPointY);
		//向量的模乘
		double sqrt = Math.sqrt((Math.abs((point0X - vertexPointX) * (point0X - vertexPointX)) + Math.abs((point0Y - vertexPointY) * (point0Y - vertexPointY)))* (Math.abs((point1X - vertexPointX) * (point1X - vertexPointX)) + Math.abs((point1Y - vertexPointY) * (point1Y - vertexPointY))));
		//反余弦计算弧度
		double radian = Math.acos(vector / sqrt);
		//弧度转角度制
		if( (ax * by - ay * bx)>0)
		{
			return 180 * radian / Math.PI;//右转
		}
		else {
			return -180 * radian / Math.PI;//左转
		}

	}

	//根据四点坐标计算两向量夹角
	public static double getDegree2(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

		double ax=x2-x1;
		double ay=y2-y1;
		double bx= x4-x3;
		double by=y4-y3;

		double value = (ax * bx + ay * by) / (Math.sqrt(ax * ax + ay * ay) * Math.sqrt(bx * bx + by * by)); // 余弦值
		double angle = Math.toDegrees(Math.acos(value));   // 角度	
		if( (ax * by - ay * bx)>0)
		{
			return -angle;//左转
		}
		else {
			return angle;//右转
		}

	}
	
	//根据四点坐标计算两直线的交点
	////效果不好
	public static  GPSData IntersectionPoint(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4)
    {
    	GPSData g1=new GPSData();
    	double x = ((x2 - x1) * (x3 - x4) * (y3 - y1) - x3 * (x2 - x1) * (y3 - y4) + x1 * (y2 - y1) * (x3 - x4)) /  ((y2 - y1) * (x3 - x4) - (x2 - x1) * (y3 - y4));
        double y = ((y2 - y1) * (y3 - y4) * (x3 - x1) - y3 * (y2 - y1) * (x3 - x4) + y1 * (x2 - x1) * (y3 - y4)) / ((y2 - y1) * (y3 - y4) - (y2 - y1) * (x3 - x4));
		g1.lon=x;
		g1.lat=y;
        return g1;
    }

	static double Sum(List<Double> data) {
	        double sum = 0;
	        for (int i = 0; i < data.size(); i++)
	            sum = sum + data.get(i);
	        return sum;
	    }

	    static double Mean(List<Double> data) {
	        double mean = 0;
	        mean = Sum(data) / data.size();
	        return mean;
	    }

	    // population variance 总体方差
	    static double POP_Variance(List<Double> data) {
	        double variance = 0;
	        for (int i = 0; i < data.size(); i++) {
	            variance = variance + (Math.pow((data.get(i) - Mean(data)), 2));
	        }
	        variance = variance / data.size();
	        return variance;
	    }

	    // population standard deviation 总体标准差
	    static  double POP_STD_dev(List<Double> data) {
	        double std_dev;
	        std_dev = Math.sqrt(POP_Variance(data));
	        return std_dev;
	    }

	  //平滑方法1： 该点值与最近五个点相关
		public static List <GPSData> smooth(List <GPSData> g1)
		{
			return g1;
		}
		public static double getAllDis(List<GPSData> g1) {
			double alldis=0;
			for (int i = 0; i < g1.size()-1; i++) {
				alldis+=Mathf.GetDistance(g1.get(i).lon, g1.get(i).lat,g1.get(i+1).lon, g1.get(i+1).lat);
				
			}
			return alldis;
			
		}
		
		/// <summary>
	    ///  点到线段最短距离的那条直线与线段的交点，{x=...,y=...}
	    /// </summary>
	    /// <param name="x">线段外的点的x坐标</param>
	    /// <param name="y">线段外的点的y坐标</param>
	    /// <param name="x1">线段顶点1的x坐标</param>
	    /// <param name="y1">线段顶点1的y坐标</param>
	    /// <param name="x2">线段顶点2的x坐标</param>
	    /// <param name="y2">线段顶点2的y坐标</param>
	    /// <returns></returns>
	    public static GPSData PointForPointToABLine(double x, double y, double x1, double y1, double x2, double y2)
	    {
	    	GPSData reVal = new GPSData();
	        // 直线方程的两点式转换成一般式
	        // A = Y2 - Y1
	        // B = X1 - X2
	        // C = X2*Y1 - X1*Y2
	        double a1 = y2 - y1;
	        double b1 = x1 - x2;
	        double c1 = x2 * y1 - x1 * y2;
	        double x3, y3;
	        if (a1 == 0)
	        {
	            // 线段与x轴平行
	            reVal = new GPSData(x, y1);
	            x3 = x;
	            y3 = y1;
	        }
	        else if (b1 == 0)
	        {
	            // 线段与y轴平行
	            reVal = new GPSData(x1, y);
	            x3 = x1;
	            y3 = y;
	        }
	        else
	        {
	            // 普通线段
	        	double k1 = -a1 / b1;
	        	double k2 = -1 / k1;
	        	double a2 = k2;
	        	double b2 = -1;
	        	double c2 = y - k2 * x;
	            // 直线一般式和二元一次方程的一般式转换
	            // 直线的一般式为 Ax+By+C=0
	            // 二元一次方程的一般式为 Ax+By=C
	            c1 = -c1;
	            c2 = -c2;
	 
	            // 二元一次方程求解(Ax+By=C)
	            // a=a1,b=b1,c=c1,d=a2,e=b2,f=c2;
	            // X=(ce-bf)/(ae-bd)
	            // Y=(af-cd)/(ae-bd)
	            x3 = (c1 * b2 - b1 * c2) / (a1 * b2 - b1 * a2);
	            y3 = (a1 * c2 - c1 * a2) / (a1 * b2 - b1 * a2);
	        }
	        // 点(x3,y3)作为点(x,y)到(x1,y1)和(x2,y2)组成的直线距离最近的点,那(x3,y3)是否在(x1,y1)和(x2,y2)的线段之内(包含(x1,y1)和(x2,y2))
	        if (((x3 > x1) != (x3 > x2) || x3 == x1 || x3 == x2) && ((y3 > y1) != (y3 > y2) || y3 == y1 || y3 == y2))
	        {
	            // (x3,y3)在线段上
	            reVal = new GPSData(x3, y3);
	        }
	        else
	        {
	            // (x3,y3)在线段外
	        	double d1_quadratic = (x - x1) * (x - x1) + (y - y1) * (y - y1);
	        	double d2_quadratic = (x - x2) * (x - x2) + (y - y2) * (y - y2);
	            if (d1_quadratic <= d2_quadratic)
	            {
	                reVal = new GPSData(x1, y1);
	            }
	            else
	            {
	                reVal = new GPSData(x2, y2);
	            }
	        }
	        return reVal;
	    }

	    /**
	     * 计算点到直线的距离
	     * 
	     * @param p
	     * @param s
	     * @param e
	     * @return
	     */
	    
	    public static double disPointtoline(GPSData p, GPSData start, GPSData end) {
	    	double A=start.lat-end.lat;
			double B = end.lon-start.lon;
			double C=start.lon*end.lat-end.lon*start.lat;
			double x,y;
			if(A==0&&B==0){
				
				x=start.lon;
				y=start.lat;
			}else{
				x = (double)(A*A*p.lon-A*B*p.lat-B*C)/(A*A+B*B);//经度			
				y = (double)(B*B*p.lat-A*B*p.lon-A*C)/(A*A+B*B);;//纬度			

			}

			
			double dis =Mathf.GetDistance(p.lon,p.lat, x, y);
			return dis;
	   
	    }

	
}
