package model;

public final class Lokalizacja {
	
	private final static Room[] lokalizacja={
			new Room("1",750,100),
			new Room("2",750,60),
			new Room("3",720,60),
			new Room("4",690,60),
			new Room("5",660,60),
			new Room("6",655,75),
			new Room("7",655,87),
			new Room("8",655,100),
			new Room("9",655,112),
			new Room("10",655,123),
			new Room("11",655,135),
			new Room("12",655,148),
			new Room("13",655,167),
			new Room("14",655,190),
			new Room("15",655,232),
			new Room("16",655,257),
			new Room("17",655,271),
			new Room("18",655,284),
			new Room("19",655,297),
			new Room("20",655,308),
			new Room("21",655,320),
			new Room("22",655,333),
			new Room("23",655,345),
			new Room("24",655,360)			

	};
	
	public static Room gdzie(int i){
		if (i<=lokalizacja.length){
			return lokalizacja[i-1];
		}
		else
			return null;
	}
	
}
