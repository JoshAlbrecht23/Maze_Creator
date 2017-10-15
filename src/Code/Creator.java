package Code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Creator extends JFrame{

    private myJpanel thePanel;
	
	private static int CELL_WIDTH = 50 ;
	private static int GRID_WIDTH_SIZE = 9 ; 
	private static int GRID_HEIGHT_SIZE = 9 ; 	
	
	private static int FRAME_BUFFER = 20 ; 
	
	private static Cell[][] cells ; 
	
	private Cell DECISION_CELL ;
	//Coordinates for decision cell.
	private int CURRENT_X ;
	private int CURRENT_Y ;

	private Deque<Cell> stack ;
	
	public Creator() {
	    init();
	}
	
	public void init(){
		
		Random rand = new Random();
		
		//Initialize window
		thePanel = new myJpanel() ; 
		this.setContentPane(thePanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        //Initialize stack
        stack = new ArrayDeque<Cell>() ;      
        
        //Initialize grid of cells
        cells  = new Cell[GRID_WIDTH_SIZE][GRID_HEIGHT_SIZE] ; 
                
        for (int i=0 ; i < GRID_HEIGHT_SIZE ; i++ ){
        	for (int j=0 ; j < GRID_WIDTH_SIZE ; j++ ){
        		cells[i][j] = new Cell((i*CELL_WIDTH)+FRAME_BUFFER, (j*CELL_WIDTH)+FRAME_BUFFER*2) ;        		
        		cells[i][j].set_down(true) ;
        		cells[i][j].set_left(true) ;
        		cells[i][j].set_right(true) ;
        		cells[i][j].set_up(true) ;
        		cells[i][j].set_x(i);
        		cells[i][j].set_y(j);
        	}
        } 
       
        //Pick the starting cell in the first row.
        int  start_pos = rand.nextInt(GRID_WIDTH_SIZE);        
        DECISION_CELL = cells[start_pos][0] ;
        CURRENT_X = start_pos ;
        CURRENT_Y = 0 ;
        
        pack();
        repaint();
     
	}
		
   @Override
    public void paint(Graphics g) {
	   
    	g.setColor(Color.BLACK);
        
    	//Draw the Cell border lines.
    	for (int i=0; i<cells.length; i++){
    		for (int j=0; j<cells.length; j++){
    			Cell myCell = cells[i][j] ; 
    		
	    		if (myCell.get_down() == true){
		    		g.drawLine(myCell.get_x_coordinate(), myCell.get_y_coordinate() + CELL_WIDTH, myCell.get_x_coordinate() + CELL_WIDTH, myCell.get_y_coordinate() + CELL_WIDTH);
		    	}
		    	if (myCell.get_left() == true){
		    		g.drawLine(myCell.get_x_coordinate(), myCell.get_y_coordinate(), myCell.get_x_coordinate(), myCell.get_y_coordinate() + CELL_WIDTH);
		    	}
		    	if (myCell.get_right() == true){
		    		g.drawLine(myCell.get_x_coordinate()+CELL_WIDTH, myCell.get_y_coordinate(), myCell.get_x_coordinate()+CELL_WIDTH, myCell.get_y_coordinate() + CELL_WIDTH);
		    	}
		    	if (myCell.get_up() == true){
		    		g.drawLine(myCell.get_x_coordinate(), myCell.get_y_coordinate(), myCell.get_x_coordinate()+CELL_WIDTH, myCell.get_y_coordinate());
		    	}
    		}	
    	}
    	
    	//Draw current cell
    	g.setColor(Color.RED);
    	g.fillRect(DECISION_CELL.get_x_coordinate(), DECISION_CELL.get_y_coordinate(), CELL_WIDTH, CELL_WIDTH);
    }
	
   	private void pick_frontier_cell(){
   		
   		ArrayList<Cell> front_cells = new ArrayList<Cell>(); 
   		
   		for (int i=0; i<GRID_WIDTH_SIZE; i++){
   			for (int j=0; j<GRID_HEIGHT_SIZE; j++){
   				if ( cells[i][j].is_frontier() )
   					front_cells.add(cells[i][j]);
   			}
   		}
		
   		Random rand = new Random();
   		int  rand_pick = rand.nextInt(front_cells.size());
   		
   		
   	}
   
	private void move_cell(int origin_x, int origin_y,int destination_x, int destination_y , Direction OriginWall, Direction DestinationWall ){
		//Move the decision cell
		DECISION_CELL = cells[destination_x][destination_y] ; 
		
		//Mark the destination as part of the maze
		DECISION_CELL.set_marked(true);
		
		//Mark the neighbor cells.
		//Right 
		if ( destination_x != GRID_WIDTH_SIZE ){
			cells[destination_x + 1][destination_y].set_frontier(true);;
		}
		//Left
		if ( destination_x != 0 ){
			cells[destination_x - 1][destination_y].set_frontier(true);;
		}
		//Down
		if ( destination_y != GRID_HEIGHT_SIZE){
			cells[destination_x][destination_y + 1].set_frontier(true);;
		}
		//Up
		if ( destination_y !=  0){
			cells[destination_x][destination_y - 1].set_frontier(true);;
		}
		
		//Toggle origin cell wall.
		if ( OriginWall == Direction.Left ){
			cells[origin_x][origin_y].set_left(false);
		}else if ( OriginWall == Direction.Right ){
			cells[origin_x][origin_y].set_right(false);
		}else if ( OriginWall == Direction.Up ){
			cells[origin_x][origin_y].set_up(false);
		}else if ( OriginWall == Direction.Down ){
			cells[origin_x][origin_y].set_down(false);
		}
		//Toggle destination cell wall.
		if ( DestinationWall == Direction.Left ){
			cells[destination_x][destination_y].set_left(false);
		}else if ( DestinationWall == Direction.Right ){
			cells[destination_x][destination_y].set_right(false);
		}else if ( DestinationWall == Direction.Up ){
			cells[destination_x][destination_y].set_up(false);
		}else if ( DestinationWall == Direction.Down ){
			cells[destination_x][destination_y].set_down(false);
		}
		
		repaint();
		
	}
	
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Creator().setVisible(true);
            }
            
        });
    } 

    class myJpanel extends JPanel {

    	public myJpanel(){
    		setPreferredSize(new Dimension((GRID_WIDTH_SIZE * CELL_WIDTH) + FRAME_BUFFER*2, (GRID_HEIGHT_SIZE * CELL_WIDTH) + FRAME_BUFFER*2));
    	}
    	
    }
    
    public enum Direction{
    	Left, Right, Down, Up
    }
    
}



