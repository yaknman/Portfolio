package model;

public class HospInfo {
	private String hospName; //병원이름
	private String subject; //진료과목
	private String address; //주소
	private String lat; //위도
	private String logt; //경도
	private double distance; //거리
	
	
	public String getHospName() {
		return hospName;
	}
	public void setHospName(String hospName) {
		this.hospName = hospName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
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
	
}
