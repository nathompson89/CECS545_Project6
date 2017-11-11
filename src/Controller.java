import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
	
	private static int expertSize = 17;

	public static void main(String[] args) throws FileNotFoundException {
		
		long startTime = System.currentTimeMillis();
		
		SAT sat = new SAT("SAT1.CNF");
		
		//Call GA.GeneticAlgorithm() to get list of solutions to pass to WOC
		ArrayList<Solution> bestSolutions = new ArrayList<Solution>();
		for(int i = 0; i < 50; i++) {
			bestSolutions.add(GA.GeneticAlgorithm(sat));
		}
		 
		ArrayList<Solution> experts = findExperts(bestSolutions);
		
		double sum = 0;
		for(int i = 0; i < expertSize; i++) {
            sum += experts.get(i).getFitness();
        }

        double avg = sum / expertSize;

        System.out.printf("%nAvg Expert Fitness: %.2f", avg);
		
		System.out.println();

		
		//Call WOC.WisdomOfCrowds() to get hopefully better solution
		Solution wisestSolution = WOC.WisdomOfCrowds(experts, sat.getNumVars(), sat.getClauses(), sat);
		wisestSolution.calculateFitness(sat.getClauses());

        System.out.printf("Wisdom of Crowds Fitness: %.2f", wisestSolution.getFitness());
        
        double percentChange = (((wisestSolution.getFitness() - avg)/wisestSolution.getFitness()) * 100);
        System.out.printf("\nPercent Change: %.2f%%%n", percentChange);
        
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
