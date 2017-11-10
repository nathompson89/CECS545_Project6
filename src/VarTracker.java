import java.util.ArrayList;

public class VarTracker {
	private int[][] falseVarList;
	private int[][] trueVarList;
	private int[] trueSumValues;
	private int[] falseSumValues;
	private int trueCutOff;
	private int falseCutOff;
	private ArrayList<Integer> confirmed = new ArrayList<Integer>();
	private static double gain = 0;
	private SAT sat;
	
	public VarTracker(ArrayList<Solution> experts, SAT sat){
		this.sat = sat;
		trueVarList = new int[experts.size()][sat.getNumVars()+1];
		falseVarList = new int[experts.size()][sat.getNumVars()+1];
		trueSumValues = new int[sat.getNumVars() + 1];
		falseSumValues = new int[sat.getNumVars() + 1];
		
		//iterate through each solution in experts
		for(int i = 0; i < experts.size(); i++) {
			//iterate through each clause in current solution
			for(int j = 0; j < experts.get(i).getVars().length; j++) {
				//if variable is true, then get number of clauses true and fill in trueVarList
				//if variable is false, get number of clauses true and fill in falseVarList
				if(experts.get(i).getVars()[j]){
					trueVarList[i][j] = experts.get(i).getTrueClauses()[j];
					trueSumValues[j] += experts.get(i).getTrueClauses()[j];
				} else {
					falseVarList[i][j] = experts.get(i).getTrueClauses()[j];
					falseSumValues[j] += experts.get(i).getTrueClauses()[j];
				}
			}
		}
		
		sortVars();
		
	}
	
	private void sortVars(){
		int trueMax = 0; 
		int falseMax = 0;
		
		for(int i: trueSumValues){
			if(i > trueMax){
				trueMax = i;
			}
		}
		
		for(int i: falseSumValues){
			if(i > falseMax){
				falseMax = i;
			}
		}
		
		//cutoff value can be tweaked
		trueCutOff = trueMax/3;
		falseCutOff = falseMax/3;
		
		
		//decide if experts agree on variable being true or false
		//if they can't agree, then variable is not added to confirmed list
		//all unconfirmed variables are then considered for flip heuristic
		for(int i = 1; i < trueSumValues.length; i++){
			if(trueSumValues[i] >= trueCutOff){
				//confirmed as true, add to list
				confirmed.add(i);
			}
		}
		
		for(int i = 1; i < falseSumValues.length; i++){
			if(falseSumValues[i] >= falseCutOff){
				//if it is in both lists, it is unconfirmed
				if(confirmed.contains(i)){
					confirmed.remove(confirmed.indexOf(i));
					continue;
				} 
				
				//confirmed as false, add to list
				confirmed.add(i);
			} 
		}
	}
	
	public ArrayList<Solution> flipHeuristic(ArrayList<Solution> experts){
		ArrayList<Solution> editedExperts = new ArrayList<Solution>();
		for(int i = 0; i < experts.size(); i++){
			Solution ws = experts.get(i);
			
			for(int j = 1; j < experts.get(i).getVars().length; j++){
				if(confirmed.contains(j)){
					continue;
				}
				
				ws = flip(ws, j);
			}
			
			editedExperts.add(ws);
		}
		
		return editedExperts;
		
	}

	private Solution flip(Solution sol, int j){
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
