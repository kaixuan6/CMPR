package zhuanwan;



public class SenserData {

	public int SenserID;//ID
	public String dataSenser;//日期
	public String timeSenser;//时间
	public double longTime;//时间戳


	public double accx; //加速度x
	public double accy; //加速度y
	public double accz; //加速度z
	public double acc; //加速度x

	public double grax; //重力加速度x
	public double gray; //重力加速度y  
	public double graz; //重力加速度z

	public double line_accx;//线性加速度X
	public double line_accy;//线性加速度Y
	public double line_accz;//线性加速度Z

	public double gyrx; //陀螺仪x
	public double gyry; //陀螺仪y
	public double gyrz; //陀螺仪z

	public double orix;//方向角
	public double oriy;//俯仰角
	public double oriz;//翻转
	
	public boolean isSave;

	public SenserData() {
		// TODO 自动生成的构造函数存根
	}
	public SenserData(double longTime,double acc,double orix)
	{
		this.longTime=longTime;
		this.acc=acc;
		this.orix=orix;
	}

}
