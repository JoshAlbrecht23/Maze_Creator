package Code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Creator extends JPanel{

	private static int CELL_WIDTH = 100 ;
	private static int GRID_WIDTH_SIZE = 9 ; 
	private static int GRID_HEIGHT_SIZE = 9 ; 	
	
	private int x ; 
	private int y ; 
	private int x2 ;
	private int y2 ;
	
	private static Cell[] cells ; 
	
	public void draw_cell(Cell myCell){
    	    	
    	if (myCell.get_down()){
    		x = myCell.get_x() ; 
    		y = myCell.get_y() + CELL_WIDTH ; 
    		x2 = myCell.get_x() + CELL_WIDTH; 
    		y2 = myCell.get_y() + CELL_WIDTH ; 	
    		repaint() ; 
    	}
    	if (myCell.get_left()){
    		x = myCell.get_x() ; 
    		y = myCell.get_y() ; 
    		x2 = myCell.get_x() ; 
    		y2 = myCell.get_y() + CELL_WIDTH ; 	
    		repaint() ;
    	}
    	if (myCell.get_right()){
    		x = myCell.get_x() + CELL_WIDTH ; 
    		y = myCell.get_y() ; 
    		x2 = myCell.get_x() + CELL_WIDTH ; 
    		y2 = myCell.get_y() + CELL_WIDTH ; 	
    		repaint() ;
    	}
    	if (myCell.get_up()){
    		x = myCell.get_x() ; 
    		y = myCell.get_y() ; 
    		x2 = myCell.get_x() + CELL_WIDTH ; 
    		y2 = myCell.get_y()  ; 	
    		repaint() ;
    	}
    }
    
    public void draw_line ( Graphics g ) {
        
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setColor(Color.BLACK);
        g2.drawLine(x, y, x2, y2);
                
    }
	
    public static void main(String[] args) {
    
    	//Initialize window
    	JFrame frame = new JFrame();
        frame.setSize(GRID_WIDTH_SIZE * CELL_WIDTH, GRID_HEIGHT_SIZE * CELL_WIDTH);
        frame.setVisible(true);
        
        //Initialize cells
        cells  = new Cell[GRID_WIDTH_SIZE*GRID_HEIGHT_SIZE] ; 
                
        int count = 0 ; 
        for (int i=0 ; i < GRID_HEIGHT_SIZE ; i++ ){
        	for (int j=0 ; j < GRID_WIDTH_SIZE ; j++ ){
        		System.out.println("Drawn");
        		cells[count] = new Cell(i*CELL_WIDTH, j*CELL_WIDTH) ;        		
        		cells[count].set_down(true) ;
        		cells[count].set_left(true) ;
        		cells[count].set_right(true) ;
        		cells[count].set_up(true) ;
        		
        		count++ ;
        	}
        }
    }
	
    
}
