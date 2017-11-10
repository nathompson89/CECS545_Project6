import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
	
	private static int expertSize = 5;

	public static void main(String[] args) throws FileNotFoundException {
		
		long startTime = System.currentTimeMillis();
		
		SAT sat = new SAT("SAT1.CNF");
		
		//Call GA.GeneticAlgorithm() to get list of solutions to pass to WOC
		ArrayList<Solution> bestSolutions = new ArrayList<Solution>();
		for(int i = 0; i < 20; i++) {
			bestSolutions.add(GA.GeneticAlgorithm(sat));
		}
		 
		ArrayList<Solution> experts = findExperts(bestSolutions);
		
		for(int i = 0; i < 5; i++) {
			System.out.println("Expert " + (i + 1) + " fitness: " + experts.get(i).getFitness());
		}
		
		System.out.println();
		
		VarTracker vt = new VarTracker(experts, sat);	
		
		//Call WOC.WisdomOfCrowds() to get hopefully better solution
		experts = vt.flipHeuristic(experts);
		Solution wisestSolution = WOC.WisdomOfCrowds(experts, sat.getNumVars());
		wisestSolution.calculateFitness(sat.getClauses());
		
		for(int i = 0; i < 5; i++) {
			System.out.println("New Expert " + (i + 1) + " fitness: " + experts.get(i).getFitness());
		}
		System.out.println("\nWisdom of Crowds Fitness: " + wisestSolution.getFitness());
		
		//calculate the end time
		long endTime = System.currentTimeMillis();

		//calculate the total time and print it
		float totalTime = (endTime - startTime);
		System.out.println("\nExecution time: " + (totalTime/1000) + " seconds");
	}
	
	public static ArrayList<Solution> findExperts(ArrayList<Solution> crowd){
		ArrayList<Solution> experts = new ArrayList<Solution>();
		
		for(int i=0; i < expertSize; i++){
			Solution expert = crowd.get(0);		
			int toRemove = 0;
			for(int j=0; j<crowd.size(); j++){
				if(expert.getFitness() < crowd.get(j).getFitness()){
					expert = crowd.get(j);
					toRemove = j;
				}
			}
			
			crowd.remove(toRemove);
			experts.add(expert);
		}
		
		return experts;
	}

}
