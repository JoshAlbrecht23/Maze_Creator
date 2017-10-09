package Code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayDeque;
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
	
	private static Cell[] cells ; 
	
	private Cell DECISION_CELL ;
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
        cells  = new Cell[GRID_WIDTH_SIZE*GRID_HEIGHT_SIZE] ; 
                
        int count = 0 ; 
        for (int i=0 ; i < GRID_HEIGHT_SIZE ; i++ ){
        	for (int j=0 ; j < GRID_WIDTH_SIZE ; j++ ){
        		cells[count] = new Cell((j*CELL_WIDTH)+FRAME_BUFFER, (i*CELL_WIDTH)+FRAME_BUFFER*2) ;        		
        		cells[count].set_down(true) ;
        		cells[count].set_left(true) ;
        		cells[count].set_right(true) ;
        		cells[count].set_up(true) ;
        		        		        		
        		count++ ;
        	}
        } 
       
        //Pick the starting cell in the first row.
        int  start_pos = rand.nextInt(GRID_WIDTH_SIZE);        
        DECISION_CELL = new Cell((start_pos*CELL_WIDTH)+FRAME_BUFFER, FRAME_BUFFER*2 ) ;
        
        pack();
        repaint();
     
	}
		
   @Override
    public void paint(Graphics g) {
	   
    	g.setColor(Color.BLACK);
        
    	//Draw the Cell border lines.
    	for (int i=0; i<cells.length; i++){
    		Cell myCell = cells[i] ; 
    		
    		if (myCell.get_down() == true){
	    		g.drawLine(myCell.get_x(), myCell.get_y() + CELL_WIDTH, myCell.get_x() + CELL_WIDTH, myCell.get_y() + CELL_WIDTH);
	    	}
	    	if (myCell.get_left() == true){
	    		g.drawLine(myCell.get_x(), myCell.get_y(), myCell.get_x(), myCell.get_y() + CELL_WIDTH);
	    	}
	    	if (myCell.get_right() == true){
	    		g.drawLine(myCell.get_x()+CELL_WIDTH, myCell.get_y(), myCell.get_x()+CELL_WIDTH, myCell.get_y() + CELL_WIDTH);
	    	}
	    	if (myCell.get_up() == true){
	    		g.drawLine(myCell.get_x(), myCell.get_y(), myCell.get_x()+CELL_WIDTH, myCell.get_y());
	    	}
    	}
    	
    	//Draw current cell
    	g.setColor(Color.RED);
    	g.fillRect(DECISION_CELL.get_x(), DECISION_CELL.get_y(), CELL_WIDTH, CELL_WIDTH);
    }
	
	private void mark(Cell myCell){
		
	}
	
	private void neighbors(Cell myCell){
		
	}
   
	private void move_cell(Cell origin, Cell destination){
		//Move the decision cell
		DECISION_CELL = destination ; 
		
		//Mark the destination as part of the maze
		destination.set_marked(true);
		
		//Mark the neighboring cells as frontier cells
		for (int i=0 ; i< cells.length; i++){
			//Right
			if (cells[i].get_x() == destination.get_x() + CELL_WIDTH && cells[i].get_y() == destination.get_y()  && !cells[i].is_frontier()){
				cells[i].set_frontier(true);
			}
			//Left
			if (cells[i].get_x() == destination.get_x() - CELL_WIDTH && cells[i].get_y() == destination.get_y() && !cells[i].is_frontier()){
				cells[i].set_frontier(true);
			}
			//Down
			if (cells[i].get_x() == destination.get_x() && cells[i].get_y() == destination.get_y() - CELL_WIDTH && !cells[i].is_frontier()){
				cells[i].set_frontier(true);
			}
			//Up
			if (cells[i].get_x() == destination.get_x()  && cells[i].get_y()  == destination.get_y() + CELL_WIDTH && !cells[i].is_frontier()){
				cells[i].set_frontier(true);
			}
		}
		
		//TODO: Remove the walls between origin and destination.
		
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
}



