package zhuanwan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class consoleData {


	/**
	 * 输出移除停留点后的数据
	 * @param gpsdata
	 * @param txtName
	 */
	public static void getTDGPSPoints0(List<GPSData> gpsdata ,String txtName) {

		String pathString="C:\\Users\\32762\\Desktop\\"+txtName;
		BufferedWriter bw;  //创建字符缓冲输出流对象
		try {
			bw = new BufferedWriter(new FileWriter(pathString));
			for(GPSData s : gpsdata) {				
				bw.write(String.valueOf(s.GPSID)+" "+String.valueOf(s.lon)+" "+String.valueOf(s.lat)+" "+String.valueOf(s.angle2));
				bw.newLine();
				bw.flush();				
			}
			//释放资源
			bw.close();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
	

	
	//输出数据
	public static void getTDGPSPoints1(List<GPSData> gpsdata ,String txtName) {

		String pathString="C:\\Users\\32762\\Desktop\\"+txtName;
		BufferedWriter bw;  //创建字符缓冲输出流对象
		try {
			bw = new BufferedWriter(new FileWriter(pathString));			
			for(int i=0;i<gpsdata.size();i++) {			
				bw.write(String.valueOf(gpsdata.get(i).GPSID)+" "+String.valueOf(gpsdata.get(i).longTime)+" "+String.valueOf(gpsdata.get(i).timeGPS)+" "+String.valueOf(gpsdata.get(i).lat)+" "+String.valueOf(gpsdata.get(i).lon)+" "+String.valueOf(gpsdata.get(i).angle1));
				bw.newLine();
				bw.flush();			
		}
			//释放资源
			bw.close();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}
	
	//输出数据
	public static void getTDGPSPoints2(List<allTurn> a1 ,String txtName) {

		String pathString="C:\\Users\\32762\\Desktop\\"+txtName;
		BufferedWriter bw;  //创建字符缓冲输出流对象
		try {
			bw = new BufferedWriter(new FileWriter(pathString));
			for(int i=0;i<a1.size();i++) {			
				bw.write(String.valueOf(i)+ " "+String.valueOf(a1.get(i).gpsStartID)+" "+String.valueOf(a1.get(i).gpsEndID)+" "+String.valueOf(a1.get(i).maxGPSID)+" "+String.valueOf(a1.get(i).maxPointDis+" "+String.valueOf(a1.get(i).sumAngle)));
				bw.newLine();
				bw.flush();			
		}
			//释放资源
			bw.close();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	//输出数据
		public static void getTDGPSPoints3(List<GPSData> gpsdata,List<Integer> f1 ,String txtName) {

			String pathString="C:\\Users\\32762\\Desktop\\"+txtName;
			BufferedWriter bw;  //创建字符缓冲输出流对象
			try {
				bw = new BufferedWriter(new FileWriter(pathString));			
				for(int i=0;i<f1.size();i++) {			
					bw.write(String.valueOf(f1.get(i)+" "+String.valueOf(gpsdata.get(f1.get(i)).lon)+" "+String.valueOf(gpsdata.get(f1.get(i)).lat)));
					bw.newLine();
					bw.flush();			
			}
				//释放资源
				bw.close();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}

	

}
