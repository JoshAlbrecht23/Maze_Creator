package Code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class Creator extends JFrame implements ActionListener{

    private myJpanel thePanel ;
	
	private static int CELL_WIDTH = 50 ;
	private static int GRID_WIDTH_SIZE = 10 ; 
	private static int GRID_HEIGHT_SIZE = 10 ; 	
	
	private static int COUNTER = 0 ;
	private static int LONGEST_COUNTER = 0 ;
	private int[][] distFromStart ;
	
	private static int FRAME_BUFFER = 20 ; 

	private static Cell[][] cells ; 

	private Cell DECISION_CELL ;
	private Cell START_CELL ; 
	private Cell END_CELL ;
	private Cell FINISH_LINE_CELL ;
		
	private Deque<Cell> stack ;

	private Timer myTimer ;
	
	public Creator() {
	    init();
	}
		
	public void init(){
		
		Random rand = new Random();
			
		//Initialize window
		thePanel = new myJpanel() ; 
		
		this.add(thePanel) ; 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Maze Creator");
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
       
        //Initialize stack
        stack = new ArrayDeque<Cell>() ;      
        
        //Initialize grid of cells
        cells  = new Cell[GRID_WIDTH_SIZE][GRID_HEIGHT_SIZE] ;
        distFromStart = new int [GRID_WIDTH_SIZE][GRID_HEIGHT_SIZE] ;
        
        //Create cells    
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
        cells[start_pos][0].set_marked(true);
        
        //Init Start Cell
        START_CELL = new Cell(DECISION_CELL.get_x_coordinate(),DECISION_CELL.get_y_coordinate());  
        START_CELL.set_x(DECISION_CELL.get_x());
        START_CELL.set_y(DECISION_CELL.get_y());

        pack();
        repaint();
        
    	myTimer = new Timer (500, this) ;
        myTimer.start();
             
	}
		
   @Override
    public void paint(Graphics g) {
	   	   
        
    	//Draw the Cell border lines.
    	for (int i=0; i<cells.length; i++){
    		for (int j=0; j<cells.length; j++){
    			Cell myCell = cells[i][j] ; 
    		
    			//Fill rectangle with white/
    			g.setColor(Color.WHITE);
    			g.fillRect(myCell.get_x_coordinate(), myCell.get_y_coordinate(), CELL_WIDTH, CELL_WIDTH);
    			
    			//Draw cell borders
    	    	g.setColor(Color.BLACK);
    			
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
    	if (DECISION_CELL != null){
    		g.setColor(Color.RED);
        	g.fillRect(DECISION_CELL.get_x_coordinate(), DECISION_CELL.get_y_coordinate(), CELL_WIDTH, CELL_WIDTH);
    	}
    		
    	//Draw Start
    	g.setColor(Color.BLUE);
    	
    	int reduced_width = (int) ((int) CELL_WIDTH - (CELL_WIDTH*.10)) ;
    	if( START_CELL != null ){
        	g.fillRect(START_CELL.get_x_coordinate() + 2, START_CELL.get_y_coordinate() + 2, reduced_width , reduced_width) ;
    	}
    	
    	//Draw End
    	if( END_CELL != null ){
        	
    		g.setColor(Color.white);
    		g.fillRect(END_CELL.get_x_coordinate() + 2, END_CELL.get_y_coordinate() + 2, reduced_width, reduced_width);
        	clearVisited() ;
        	fillDistanceGrid(START_CELL);
        	FINISH_LINE_CELL = getEndCell() ;
        	
    	}
    	
    	//Draw Finish Line
    	if( FINISH_LINE_CELL != null ){
    		g.setColor(Color.RED);
    		g.fillRect(FINISH_LINE_CELL.get_x_coordinate() + 2, FINISH_LINE_CELL.get_y_coordinate() + 2, reduced_width, reduced_width);
    	}
    	
    }
	
 	public void actionPerformed(ActionEvent ev){
 		if(ev.getSource()==myTimer){
 			if (is_unvisited_cells()){
 				pick_frontier_cell();
 				repaint();
 			}else{
 				myTimer.stop();
 			    END_CELL = new Cell (DECISION_CELL.get_x_coordinate(), DECISION_CELL.get_y_coordinate()) ;
 			    DECISION_CELL = null ;
 			    repaint();
 			}
 		}
 	} 
   
   	private void pick_frontier_cell(){
   		   		
   		ArrayList<Cell> front_cells = new ArrayList<Cell>(); 
   			
   		//Add current cell's neighbors
   		//Right
   		if (DECISION_CELL.get_x() != GRID_WIDTH_SIZE - 1 ){
   			if (cells[DECISION_CELL.get_x() + 1][DECISION_CELL.get_y()].is_marked() == false){
   	   			front_cells.add(cells[DECISION_CELL.get_x() + 1][DECISION_CELL.get_y()]);
   			}
   		}
   		//Left
   		if (DECISION_CELL.get_x() != 0 ){
   			if (cells[DECISION_CELL.get_x() - 1][DECISION_CELL.get_y()].is_marked() == false){
   	   			front_cells.add(cells[DECISION_CELL.get_x() - 1][DECISION_CELL.get_y()]);
   			}
   		}
   		//Down
   		if (DECISION_CELL.get_y() != GRID_HEIGHT_SIZE - 1 ){
   			if (cells[DECISION_CELL.get_x()][DECISION_CELL.get_y() + 1].is_marked() == false){
   	   			front_cells.add(cells[DECISION_CELL.get_x()][DECISION_CELL.get_y() + 1]);
   			}
   		}
   		//Up
   		if (DECISION_CELL.get_y() != 0 ){
   			if (cells[DECISION_CELL.get_x()][DECISION_CELL.get_y() - 1].is_marked() == false){
   	   			front_cells.add(cells[DECISION_CELL.get_x()][DECISION_CELL.get_y() - 1]);
   			}
   		}
   		  		
   		if (front_cells.isEmpty() == false){
   	   		//Push current cell to the stack
   	   		stack.push(DECISION_CELL);
   			
   			//Randomly choose a neighbor
   	   		Random rand = new Random();
   	   		int  rand_pick = rand.nextInt(front_cells.size());
   	   		
   	   		Cell rand_front_cell = front_cells.get(rand_pick) ; 
   	   		
   	   		//Remove the wall between the chosen cell and the current cell.
   	   		//Right
   	   		if (DECISION_CELL.get_x() + 1 == rand_front_cell.get_x() && DECISION_CELL.get_y() == rand_front_cell.get_y()){
   	   			cells[DECISION_CELL.get_x()][DECISION_CELL.get_y()].set_right(false);
   	   			cells[rand_front_cell.get_x()][rand_front_cell.get_y()].set_left(false);
   	   		//Left	
   	   		}else if (DECISION_CELL.get_x() - 1 == rand_front_cell.get_x() && DECISION_CELL.get_y() == rand_front_cell.get_y()){
   	   			cells[DECISION_CELL.get_x()][DECISION_CELL.get_y()].set_left(false);
   	   			cells[rand_front_cell.get_x()][rand_front_cell.get_y()].set_right(false);
   	   		//Down	
   	   		}else if (DECISION_CELL.get_x() == rand_front_cell.get_x() && DECISION_CELL.get_y() + 1 == rand_front_cell.get_y()){
   	   			cells[DECISION_CELL.get_x()][DECISION_CELL.get_y()].set_down(false);
   	   			cells[rand_front_cell.get_x()][rand_front_cell.get_y()].set_up(false);		
   	   		//Up
   	   		}else{
   	   			cells[DECISION_CELL.get_x()][DECISION_CELL.get_y()].set_up(false);
   	   			cells[rand_front_cell.get_x()][rand_front_cell.get_y()].set_down(false);	
   	   		}
   	   		
   	   		cells[rand_front_cell.get_x()][rand_front_cell.get_y()].set_marked(true);
   	   		DECISION_CELL = rand_front_cell ;
   	   		
   		}else{
   			if (stack.isEmpty() == false){
   	   			DECISION_CELL = stack.pop() ;
   			}
   		}
   	}
   
   	private boolean is_unvisited_cells(){
   		boolean b = false;
   		
   		for (int i=0; i<GRID_WIDTH_SIZE; i++){
   			for (int j=0; j<GRID_HEIGHT_SIZE; j++){
   				if ( cells[i][j].is_marked() == false)
   					return true ; 
   			}
   		}
   		
   		return b ;
   	}
   	
   	public void fillDistanceGrid( Cell currCell ){
   		
   		Cell myCell = cells[currCell.get_x()][currCell.get_y()] ;
   		   		
   		if( myCell.get_x() ==  START_CELL.get_x() && myCell.get_y() ==  START_CELL.get_y() ){
   			distFromStart[myCell.get_x()][myCell.get_y()] = 0 ;
   		}else if( distFromStart[myCell.get_x()][myCell.get_y()] == -1 ){
   			
   			COUNTER++;
   			distFromStart[myCell.get_x()][myCell.get_y()] = COUNTER ;
   		}else{
   			COUNTER = distFromStart[myCell.get_x()][myCell.get_y()] ;
   		}
   		
   		
   		if ( myCell.get_left() == false ){
   			if (distFromStart[myCell.get_x() - 1][myCell.get_y()] == -1)
   				stack.push(cells[myCell.get_x() - 1][myCell.get_y()]);
   		}
   			
   		if ( myCell.get_right() == false ){
   			if ( distFromStart[myCell.get_x() + 1][myCell.get_y()] == -1 )
   				stack.push(cells[myCell.get_x() + 1][myCell.get_y()]);
   		}
   		
   		if ( myCell.get_down() == false ){
   			if ( distFromStart[myCell.get_x()][myCell.get_y() + 1] == -1 )
   				stack.push(cells[myCell.get_x()][myCell.get_y() + 1]);
   		}
   				
   		if ( myCell.get_up() == false ){
   			if (distFromStart[myCell.get_x()][myCell.get_y() -1] == -1)
   				stack.push(cells[myCell.get_x()][myCell.get_y() - 1]);
   		}
   			
   		if ( stack.isEmpty() == false ){
   			Cell temp = stack.pop() ;
   			fillDistanceGrid( temp ) ;
   		}
   			
   		
   	}
   	
   	
   	public void clearVisited(){
   		//Clear stack.
   		stack.clear() ; 
   		//Clear the marked flags.
   		for (int i=0; i<GRID_WIDTH_SIZE; i++){
   			for (int j=0; j<GRID_HEIGHT_SIZE; j++){
   				cells[i][j].set_marked(false);
   			}
   		}
   		//Fill the distance grid with -1
   		for (int i=0; i<GRID_WIDTH_SIZE; i++){
   			for (int j=0; j<GRID_HEIGHT_SIZE; j++){
   				distFromStart[i][j] = -1 ;
   			}
   		}
   	}
   	
   	public Cell getEndCell(){
   		
   		Cell lastCell = null ;
   		
   		for ( int i=0; i<GRID_WIDTH_SIZE; i++ ){
   			for (int j=0; j<GRID_HEIGHT_SIZE; j++){
   				if ( lastCell == null ){
   					lastCell = cells[i][j];
   				}else{
   					if ( distFromStart[i][j] > distFromStart[lastCell.get_x()][lastCell.get_y()] )
   						lastCell = cells[i][j] ;
   				}
   			}
   		}
   		
   		return lastCell ;
   	}
 	
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Creator().setVisible(true);
            }
            
        });
        

    } 

    class myJpanel extends JPanel  {
    	
    	public myJpanel(){
    		setPreferredSize(new Dimension((GRID_WIDTH_SIZE * CELL_WIDTH) + FRAME_BUFFER*2, (GRID_HEIGHT_SIZE * CELL_WIDTH) + FRAME_BUFFER*2));
    	} 
    	
    }
}



