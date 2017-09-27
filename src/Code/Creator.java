package Code;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Creator extends JPanel{

	private static int CELL_WIDTH = 10 ;
	private static int GRID_WIDTH_SIZE = 9 ; 
	private static int GRID_HEIGHT_SIZE = 9 ; 	
	
	private static Cell[] cells ; 
	
    public static void main(String[] args) {
    
    	//Initialize window
    	JFrame frame = new JFrame();
        frame.setSize(GRID_WIDTH_SIZE * CELL_WIDTH, GRID_HEIGHT_SIZE * CELL_WIDTH);
        
        //Initialize cells
        cells  = new Cell[GRID_WIDTH_SIZE*GRID_HEIGHT_SIZE] ; 
        
        int count = 0 ; 
        for (int i=0 ; i < GRID_HEIGHT_SIZE ; i++ ){
        	for (int j=0 ; j < GRID_WIDTH_SIZE ; j++ ){
        		
        		cells[count] = new Cell(i*CELL_WIDTH, j*CELL_WIDTH) ;
        		cells[count].set_down(true);
        		cells[count].set_left(true);
        		cells[count].set_right(true);
        		cells[count].set_up(true);

        	}
        }
        
        
        
    }
	
    public void draw_cell(Cell myCell){

    }
    
    public void draw_line (Graphics g, int x, int y, int x2, int y2 ) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawLine(x, y, x2, y2);
    }
}
