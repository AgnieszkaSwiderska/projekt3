package model;

public class Room {
	private String number;
	private int coordinateX;
	private int coordinateY;
	
	public Room(String number, int coordinateX, int coordinateY) {
		this.number=number;
		this.coordinateX=coordinateX;
		this.coordinateY=coordinateY;
	}
	
	public String getNumber(){
		return number;
	}
	
	public int getCoordinateX(){
		return coordinateX;
	}
	
	public int getCoordinateY(){
		return coordinateY;
	}
	
	/*
	public void setNumber(String number){
		this.number = number;
	}
	
	public void setCoordinateX(int coordinateX){
		this.coordinateX = coordinateX;
	}
	
	public void setCoordinateY(int coordinateY){
		this.coordinateY = coordinateY;
	}
	*/   
	//metody typu "set" nie beda potrzebne w programie
}
