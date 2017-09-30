package Code;

public class Cell {

	private int X_COORDINATE ; 
	private int Y_COORDINATE ;
	
	private boolean[] Walls ; 
	
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
	
	public void set_left(boolean myBool){
		Walls[0] = myBool ; 
	}
	
	public void set_up(boolean myBool){
		Walls[1] = myBool ;
	}
	
	public void set_right(boolean myBool){
		Walls[2] = myBool ;
	}
	
	public void set_down(boolean myBool){
		Walls[3] = myBool ;
	}
	
	public boolean get_left(){
		return Walls[0] ; 
	}
	
	public boolean get_up(){
		return Walls[1] ;
	}
	
	public boolean get_right(){
		return Walls[2] ;
	}
	
	public boolean get_down(){
		return Walls[3] ;
	}
	
	public int get_x(){
		return X_COORDINATE ; 
	}
	
	public int get_y(){
		return Y_COORDINATE ; 
	}
	
}
