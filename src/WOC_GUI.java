import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class WOC_GUI extends JPanel {

	private Solution woc;
	private SAT sat;
	private double avgFitness;

	public WOC_GUI(SAT sat, Solution woc, double avg){
		this.woc = woc;
		this.sat = sat;
		this.avgFitness = avg;
	}

	
	public void paintWOC(Graphics g2d, Solution woc, int startX, int startY){
		int x = startX;
		int y = startY;
		
		for(int i = 0; i < sat.getClauses().length; i++){
			boolean[] vars = sat.getClauses()[i].isTrue(woc.getVars());
			boolean isTrue = false;
			if(vars[0] || vars[1] || vars[2]){
				g2d.setColor(Color.GREEN);	
				isTrue = true;
			}
			else{
				g2d.setColor(Color.RED);
				isTrue = false;
			}
			
			g2d.fillRect(x, y, 30, 14);
			
			if(vars[0] == true){
				if(isTrue){
					g2d.setColor(Color.BLACK);	
					g2d.drawOval(x+2, y+3, 5, 5);
				}
				else{
					g2d.setColor(Color.GREEN);	
					g2d.fillOval(x+2, y+3, 5, 5);
				}
			}
			else{
				if(!isTrue){
					g2d.setColor(Color.BLACK);	
					g2d.drawOval(x+2, y+3, 5, 5);
				}
				else{
					g2d.setColor(Color.RED);
					g2d.fillOval(x+2, y+3, 5, 5);
				}
			}
			
			
			
			if(vars[1] == true){
				if(isTrue){
					g2d.setColor(Color.BLACK);	
					g2d.drawOval(x+12, y+3, 5, 5);
				}
				else{
					g2d.setColor(Color.GREEN);	
					g2d.fillOval(x+12, y+3, 5, 5);
				}
			}
			else{
				if(!isTrue){
					g2d.setColor(Color.BLACK);	
					g2d.drawOval(x+12, y+3, 5, 5);
				}
				else{
					g2d.setColor(Color.RED);
					g2d.fillOval(x+12, y+3, 5, 5);
				}
			}
			
			if(vars[2] == true){
				if(isTrue){
					g2d.setColor(Color.BLACK);	
					g2d.drawOval(x+22, y+3, 5, 5);
				}
				else{
					g2d.setColor(Color.GREEN);	
					g2d.fillOval(x+22, y+3, 5, 5);
				}
			}
			else{
				if(!isTrue){
					g2d.setColor(Color.BLACK);	
					g2d.drawOval(x+22, y+3, 5, 5);
				}
				else{
					g2d.setColor(Color.RED);
					g2d.fillOval(x+22, y+3, 5, 5);
				}
			}
			
			if(x + 70 > this.getWidth()){
				y += 20;
				x = startX;
			}
			else{
				x += 40;
			}
			
		}
	}
	
	public void paintFitness(float woc, double avg, int startX, int startY){
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		

		g2d.setStroke(new BasicStroke(0.8f));

		paintWOC(g2d, woc, 20, 20);
		   
	}
}
