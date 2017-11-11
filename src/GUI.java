
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;



@SuppressWarnings("serial")
public class GUI  extends JPanel {
	
	private static double gain = 0;
	private ArrayList<Solution> experts;
	private Solution woc;
	private SAT sat;

	public GUI(ArrayList<Solution> experts, Solution woc, SAT sat){
		this.experts = experts;
		this.woc = woc;
		this.sat = sat;
	}
	
	public void paintExperts(Graphics g2d, ArrayList<Solution> experts, int startX, int startY){
		int x = startX;
		int y = startY;
		int n = 1;
		for(Solution s : experts){
			x = startX;
			String e = "Expert " + n;
			n++;
			g2d.setColor(Color.BLACK);
			g2d.drawString(e, x, y+12);
			x += 55;
			for(int i = 1; i < s.getVars().length; i++){
				
				if(s.getVars()[i] == true){
					g2d.setColor(Color.GREEN);
				}
				else{
					g2d.setColor(Color.RED);
				}
				//g2d.drawRect(x, y, 10, 10);
				((Graphics2D) g2d).setStroke(new BasicStroke(2));
				g2d.drawRect(x, y, 15, 15);
				x += 18;
			}
			y += 25;
		}
	}
	
	public void paintWOC(Graphics g2d, Solution woc, int startX, int startY, boolean[] wocVar, boolean[] flipped){
		int x = startX;
		int y = startY + (experts.size() * 25);
		
		String w = "WOC";
		g2d.setColor(Color.BLACK);
		g2d.drawString(w, x, y+12);
		x += 55;
		for(int i = 1; i < woc.getVars().length; i++){
			if(woc.getVars()[i] == true){
				g2d.setColor(Color.GREEN);
			}
			else{
				g2d.setColor(Color.RED);
			}
			((Graphics2D) g2d).setStroke(new BasicStroke(2));
			//g2d.drawRect(x, y, 10, 10);
			g2d.drawRect(x, y, 15, 15);
			if(wocVar[i] == true){
				
				g2d.setColor(Color.DARK_GRAY);
				//g2d.drawRect(x-1, startY-3, 16, experts.size()*26);
				g2d.fillOval(x, y+15, 15, 15);
			}
			if(flipped[i] == true){
				//((Graphics2D) g2d).setStroke(new BasicStroke(2));
				g2d.setColor(Color.MAGENTA);
				//g2d.drawRect(x-2, y-2, 18, 18);
				g2d.fillOval(x, y+15, 15, 15);
			}
			x += 18;
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		boolean[] wocVar = new boolean[experts.get(0).getVars().length];
		boolean[] newVars = new boolean[experts.get(0).getVars().length];
		for(int i = 1; i < experts.get(0).getVars().length; i++){
			int numTrue = 0;
			for(Solution s : experts){
				if(s.getVars()[i] == true){
					numTrue++;
				}
			}	
			if(numTrue >= experts.size()*0.60f){
				newVars[i] = true;
				wocVar[i] = true;
			}
			else if(numTrue < experts.size()*0.40f){
				newVars[i] = false;
				wocVar[i] = true;
			}
		}	
		Solution WOCSolution = new Solution(newVars);
		boolean[] flippedVar = new boolean[wocVar.length];
		for(int i = 1; i < WOCSolution.getVars().length; i++){
			if(wocVar[i] == false){
				boolean before = WOCSolution.getVars()[i];
				flip(WOCSolution, i, sat);
				if(WOCSolution.getVars()[i] != before){
					flippedVar[i] = true;
				}
			}
		}
		//g2d.scale(9.0, 9.0);
		g2d.setStroke(new BasicStroke(0.4f));
		paintExperts(g2d, experts, 20, 20);
		paintWOC(g2d, woc, 20, 20, wocVar, flippedVar);
		//g2d.setColor(Color.CYAN);
		   
	}
	
	private static Solution flip(Solution sol, int j, SAT sat){
		double origFit = sol.getFitness();
		
		//try flipping the bit and get the new fitness 
		sol.flipBit(j);
		sol.calculateFitness(sat.getClauses());
		double dif = sol.getFitness() - origFit;
		
		//if the fitness after flipping the bit is less than the gain, 
		//then it sucked so flip it back
		if(dif < gain){
			sol.flipBit(j);
			sol.calculateFitness(sat.getClauses());
		}
		
		return sol;
	}
}
