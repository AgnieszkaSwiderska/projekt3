package model;

public class Person {
	private String firstName;
	private String lastName;
	private String pokoj;
	private String hourStart;
	private String hourStop;
	
	public Person(String firstName, String lastName, String pokoj) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.pokoj=pokoj;
	}
	
	public Person(String firstName, String lastName, String pokoj, String hourStart, String hourStop) {
		this.firstName=firstName;
		this.lastName=lastName;
		this.pokoj=pokoj;
		this.hourStart=hourStart;
		this.hourStop=hourStop;
	}
	public Person(String firstName){
		this.firstName=firstName;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getPokoj(){
		return pokoj;
	}
	
	public String getHourStart(){
		return hourStart;
	}
	
	public String getHourStop(){
		return hourStop;
	}
	
	public void setFirstName(String firstName){
		this.firstName=firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName=lastName;
	}
	
	public void setPokoj(String pokoj){
		this.pokoj=pokoj;
	}
	
	public void setHourStart(String hourStart){
		this.hourStart=hourStart;
	}
	
	public void setHourStop(String hourStop){
		this.hourStop=hourStop;
	}
	
	
	public Double czasToDouble(String czas)
	{
		Double czasDouble;
		int koma = czas.indexOf(":");
		
		czasDouble = Double.parseDouble(czas.substring(0, koma));
		czasDouble += (Double.parseDouble(czas.substring(koma+1, czas.length())))/60;
		
		return czasDouble;
	}
	
	public Double czasPracy()
	{
		return czasToDouble(hourStop) - czasToDouble(hourStart);
	}
}
