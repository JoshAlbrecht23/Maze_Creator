package Code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Creator extends JFrame{

    private myJpanel thePanel;
	
	private static int CELL_WIDTH = 100 ;
	private static int GRID_WIDTH_SIZE = 9 ; 
	private static int GRID_HEIGHT_SIZE = 9 ; 	
	
	private int x ; 
	private int y ; 
	private int x2 ;
	private int y2 ;
	
	private static Cell[] cells ; 
	
	public Creator() {
	    init();
	}
	
	public void init(){
		
		//Initialize window
		thePanel = new myJpanel() ; 
		this.setContentPane(thePanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//JFrame frame = new JFrame();
        //this.setSize(GRID_WIDTH_SIZE * CELL_WIDTH, GRID_HEIGHT_SIZE * CELL_WIDTH);
        //this.setVisible(true);
                
        //Initialize cells
        cells  = new Cell[GRID_WIDTH_SIZE*GRID_HEIGHT_SIZE] ; 
                
        int count = 0 ; 
        for (int i=0 ; i < GRID_HEIGHT_SIZE ; i++ ){
        	for (int j=0 ; j < GRID_WIDTH_SIZE ; j++ ){
        		cells[count] = new Cell(i*CELL_WIDTH, j*CELL_WIDTH) ;        		
        		cells[count].set_down(true) ;
        		cells[count].set_left(true) ;
        		cells[count].set_right(true) ;
        		cells[count].set_up(true) ;
        		
        		draw_cell(cells[count]) ;
        		
        		count++ ;
        	}
        } 
        
        pack();
	}
	
	public void draw_cell(Cell myCell){
    	    	
    	if (myCell.get_down()){
    		x = myCell.get_x() ; 
    		y = myCell.get_y() + CELL_WIDTH ; 
    		x2 = myCell.get_x() + CELL_WIDTH; 
    		y2 = myCell.get_y() + CELL_WIDTH ; 	
    		thePanel.repaint();
    	}
    	if (myCell.get_left()){
    		x = myCell.get_x() ; 
    		y = myCell.get_y() ; 
    		x2 = myCell.get_x() ; 
    		y2 = myCell.get_y() + CELL_WIDTH ; 	
    		thePanel.repaint() ;
    	}
    	if (myCell.get_right()){
    		x = myCell.get_x() + CELL_WIDTH ; 
    		y = myCell.get_y() ; 
    		x2 = myCell.get_x() + CELL_WIDTH ; 
    		y2 = myCell.get_y() + CELL_WIDTH ; 	
    		thePanel.repaint();
    	}
    	if (myCell.get_up()){
    		x = myCell.get_x() ; 
    		y = myCell.get_y() ; 
    		x2 = myCell.get_x() + CELL_WIDTH ; 
    		y2 = myCell.get_y()  ; 	
    		thePanel.repaint() ;
    	}
    }
    
	/*
    public void draw_line ( Graphics g ) {
        
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setColor(Color.BLACK);
        g2.drawLine(x, y, x2, y2);
                
    }
	*/
	
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Creator().setVisible(true);
            }
            
        });
    } 

    class myJpanel extends JPanel {

    	public myJpanel(){
    		setPreferredSize(new Dimension(GRID_WIDTH_SIZE * CELL_WIDTH,GRID_HEIGHT_SIZE * CELL_WIDTH));
    	}
    	
	    @Override
	    public void paintComponent(Graphics g) {
	    	super.paintComponent(g);
	
	    	Graphics2D g2 = (Graphics2D) g;
	    	g2.setColor(Color.BLACK);
	        g2.drawLine(x, y, x2, y2);
	    	
	    }
    }
}



