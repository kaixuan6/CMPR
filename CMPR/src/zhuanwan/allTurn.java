package zhuanwan;

import java.util.List;

//存储转弯数据
public class allTurn {
	
	public int gpsStartID;//转弯起始点ID
	public int gpsEndID;//转弯终止点ID	
	
	public int maxGPSID;//转弯最大距离GPS点ID
    public double maxPointDis;//转弯最大距离点的距离
	public double sumAngle;//转弯角度之和
	public double sumAngle2;//方位角角度差
	public double sumAngle3;//累积转弯角度
    public double startLongTime;//转弯起始时间戳
    public double endLongTime;//转弯终止时间戳
    public String startTime;//转弯起始时间
    public String endTime;//转弯终止时间戳
    public String turnType;//转弯类型
    public List<Integer> fpoints;//转弯特征点
    public boolean isSave;
  
	
}
