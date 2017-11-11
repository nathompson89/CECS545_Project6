import java.util.List;

public class WOC {
	
	private static double gain = 0;

	public static Solution WisdomOfCrowds(List<Solution> crowd, int numVars, Clause[] clauses, SAT sat){
		
		boolean[] newVars = new boolean[numVars+1]; //default false
		boolean[] wocVar = new boolean[numVars+1]; //default false
		int woc = 0;
		for(int i = 1; i <= numVars; i++){
			int numTrue = 0;
			for(Solution s : crowd){
				if(s.getVars()[i] == true){
					numTrue++;
				}
			}	
			if(numTrue >= crowd.size()*0.60f){
				newVars[i] = true;
				wocVar[i] = true;
				woc++;
			}
			else if(numTrue < crowd.size()*0.40f){
				newVars[i] = false;
				wocVar[i] = true;
				woc++;
			}
			System.out.println("Variable " + i + ": True(" + numTrue + ")    False(" + (crowd.size()-numTrue) + ")");
		}
		
		System.out.println("WOC took " + woc + " variables");
		
		
		Solution WOCSolution = new Solution(newVars);
		int numFlipped = 0;
		for(int i = 1; i < WOCSolution.getVars().length; i++){
			if(wocVar[i] == false){
				boolean before = WOCSolution.getVars()[i];
				flip(WOCSolution, i, sat);
				if(WOCSolution.getVars()[i] != before){
					numFlipped++;
				}
			}
		}
		System.out.println("Flipped " + numFlipped + " variables to finish WOC solution");
		
		return WOCSolution;
		
		
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
