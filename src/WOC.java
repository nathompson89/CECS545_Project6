import java.util.List;

public class WOC {

	public static Solution WisdomOfCrowds(List<Solution> crowd, int numVars, Clause[] clauses){
		
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
			if(numTrue >= crowd.size()/2){
				newVars[i] = true;
			}
			else{
				newVars[i] = false;
			}
			
			/*if(numTrue >= numVars*0.90f){
				newVars[i] = true;
				wocVar[i] = true;
				woc++;
			}
			else if(numTrue <= numVars*0.1f){
				newVars[i] = false;
				wocVar[i] = false;
				woc++;
			}*/
		}
		
		/*System.out.println("WOC took " + woc + " variables");
		boolean[] tempVars = new boolean[numVars+1];
		for(int i = 0; i < newVars.length; i++){
			tempVars[i] = newVars[i];
		}
		
		Solution best = new Solution(newVars);
		best.calculateFitness(clauses);
		for(int i = 0; i < 100; i++){
			for(int k = 0; k < newVars.length; k++){
				tempVars[k] = newVars[k];
			}
			for(int j = 0; j < tempVars.length; j++){
				if(wocVar[j] == false){
					if(Math.random() < 0.5){
						tempVars[j] = true;
					}
					else{
						tempVars[j] = false;
					}
				}
			}
			Solution temp = new Solution(tempVars);
			temp.calculateFitness(clauses);
			if(temp.getFitness() > best.getFitness()){
				System.out.println("New best solution " + temp.getFitness());
				best = new Solution(tempVars);
			}
		}*/
		
		Solution WOCSolution = new Solution(newVars);
		return WOCSolution;
	}
	
}
