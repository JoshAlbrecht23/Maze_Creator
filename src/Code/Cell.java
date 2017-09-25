package Code;

public class Cell {

	int X_COORDINATE ; 
	int Y_COORDINATE ; 
	
	boolean[] Walls ; 
	
	public Cell (int x, int y){
		
		X_COORDINATE = x ; 
		Y_COORDINATE = y ; 
		Walls = new boolean[4] ;
		//Initialize all walls to be filled (True).
		//Left Wall
		Walls[0] = true ; 
		//Up Wall
		Walls[1] = true ; 
		//Right Wall
		Walls[2] = true ; 
		//Down Wall
		Walls[3] = true ; 

	}
	
	
	
	
}
