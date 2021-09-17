package zhuanwan;

import java.util.ArrayList;
import java.util.List;

public class DBScan {


    private double radius;
    private int minPts;

    public DBScan(double radius,int minPts) {
        this.radius = radius;
        this.minPts = minPts;
    }

    public void process(List<GPSData> points) {
        int size = points.size();
        int idx = 0;
        int cluster = 1;
        while (idx<size) {
        	GPSData p = points.get(idx++);
            //choose an unvisited point
            if (!p.isVisit)
            {
                p.isVisit=true;//set visited
                List<GPSData> adjacentPoints = getAdjacentPoints(p, points);
                //set the point which adjacent points less than minPts noised
                if (adjacentPoints != null && adjacentPoints.size() < minPts)
                {
                    p.isNoised=true;
                } 
                else {
                    p.cluster=cluster;
                    for (int i = 0; i < adjacentPoints.size(); i++)
                    {
                    	GPSData adjacentPoint = adjacentPoints.get(i);
                        //only check unvisited point, cause only unvisited have the chance to add new adjacent points
                        if (!adjacentPoint.isVisit) 
                        {
                            adjacentPoint.isVisit=true;
                            List<GPSData> adjacentAdjacentPoints = getAdjacentPoints(adjacentPoint, points);
                            //add point which adjacent points not less than minPts noised
                            if (adjacentAdjacentPoints != null && adjacentAdjacentPoints.size() >= minPts)
                            {
                                adjacentPoints.addAll(adjacentAdjacentPoints);
                            }
                        }
                        //add point which doest not belong to any cluster
                        if (adjacentPoint.cluster == 0) 
                        {
                            adjacentPoint.cluster=cluster;
                            //set point which marked noised before non-noised
                            if (adjacentPoint.isNoised) 
                            {
                                adjacentPoint.isNoised=false;
                            }
                        }
                    }
                    cluster++;
                }
            }
        }
    }

    private List<GPSData> getAdjacentPoints(GPSData centerPoint,List<GPSData> points) {
        List<GPSData> adjacentPoints = new ArrayList<GPSData>();
        for (GPSData p:points) {
            //include centerPoint itself
            double distance = Mathf.GetDistance(centerPoint.lon, centerPoint.lat, p.lon, p.lat);
            if (distance<=radius) {
                adjacentPoints.add(p);
            }
        }
        return adjacentPoints;
    }
    
    public static void removeStayPoint(List<GPSData> points ) {
//    	List<GPSData> d11=new ArrayList<GPSData>();
//    	GPSData d12=new GPSData();
//    	for (int i=0;i<points.size();i++) {
//			if (points.get(i).cluster!=0) {
//				d11.add(points.get(i));
//				points.remove(points.get(i));	
//				i--;
//			}
//			else {
//				if (d11.size()>0) {
//					double a1=0;
//					double a2=0;
//					for(int k=0;k<d11.size();k++)
//					{
//						a1+=d11.get(k).lon;
//						a2+=d11.get(k).lat;
//					}				
//					d12.lon=a1/d11.size();
//					d12.lat=a2/d11.size();
//					points.add(i, d12);
//					d12=new GPSData();
//					d11=new ArrayList<GPSData>();
//				}
//				
//			}
//		}
    	
    	
//    	for (int i=0;i<points.size();i++) {
//			if (points.get(i).cluster!=0) {
//				points.remove(points.get(i));	
//				i--;
//			}
//		}
    	
    	for (int j = 0; j < points.size(); j++) {
    		points.get(j).GPSID=j;
			
		}
		
	}

}
