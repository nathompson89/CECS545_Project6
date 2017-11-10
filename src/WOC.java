import java.util.List;

public class WOC {

	public static Solution WisdomOfCrowds(List<Solution> crowd, int numVars){
		
		boolean[] newVars = new boolean[numVars+1];
		for(int i = 1; i <= numVars; i++){
			int numTrue = 0;
			for(Solution s : crowd){
				if(s.getVars()[i] == true){
					numTrue++;
				}
			}
			if(numTrue >= numVars/2){
				newVars[i] = true;
			}
			else{
				newVars[i] = false;
			}
		}
		
		Solution WOCSolution = new Solution(newVars);
		return WOCSolution;
	}
	
}
