import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
	
	private static int expertSize = 20;

	public static void main(String[] args) throws FileNotFoundException {
		
		long startTime = System.currentTimeMillis();
		
		SAT sat = new SAT("SAT1.CNF");
		
		//Call GA.GeneticAlgorithm() to get list of solutions to pass to WOC
		ArrayList<Solution> bestSolutions = new ArrayList<Solution>();
		for(int i = 0; i < 50; i++) {
			bestSolutions.add(GA.GeneticAlgorithm(sat));
		}
		 
		ArrayList<Solution> experts = findExperts(bestSolutions);
		
		for(int i = 0; i < expertSize; i++) {
			System.out.println("\nExpert " + (i + 1) + " fitness: " + experts.get(i).getFitness());
			System.out.print("Expert " + (i+1) + ": ");
			experts.get(i).printSolution();
		}
		
		System.out.println();
		
		//generate 2D arrays for experts without changing them
		VarTracker vt1 = new VarTracker(experts, sat);
		
		//apply flip heuristic to experts
		vt1.sortVars();
		//experts = vt1.flipHeuristic(experts);
		
		//create new 2D arrays with updated experts
		//VarTracker vt2 = new VarTracker(experts, sat);
		
		//Call WOC.WisdomOfCrowds() to get hopefully better solution
		Solution wisestSolution = WOC.WisdomOfCrowds(experts, sat.getNumVars(), sat.getClauses(), sat);
		wisestSolution.calculateFitness(sat.getClauses());
		
		
		
		
		/*double sum = 0;
		for(int i = 0; i < expertSize; i++) {
            System.out.println("New Expert " + (i + 1) + " fitness: " + experts.get(i).getFitness());
            sum += experts.get(i).getFitness();
        }

        double avg = sum / expertSize;

        System.out.println("\nAvg Improved Expert Fitness: " + avg);*/
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
