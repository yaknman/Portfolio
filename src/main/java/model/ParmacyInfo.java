package model;

public class ParmacyInfo {

	private String lat; //위도
	private String logt; //경도
	private double distance; //거리
	private String ParName; //약국이름
	private String address; //약국주소
	
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLogt() {
		return logt;
	}
	public void setLogt(String logt) {
		this.logt = logt;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getParName() {
		return ParName;
	}
	public void setParName(String parName) {
		ParName = parName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
		
}
