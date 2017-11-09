import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA {
	
	public static List<Solution> GeneticAlgorithm(SAT sat, int popSize, int numGens, int mutRate){
		List<Solution> parents = new ArrayList<Solution>();
		List<Solution> children = new ArrayList<Solution>();
		int numChildren = 100; //Test this value
		
		//Initialize parents as initial population
		
		
		for(int i = 0; i < numGens; i++){
			
			
			for(int j = 0; j < numChildren; j++){
				//Choose 2 parents via tournament selection
				
				
				//Crossover p1 and p2
				//Test for mutation of child1
				
				
				//Crossover p2 and p1
				//Test for mutation of child2
				
				
				//Add children to pool of children
			}
			
			//Choose children (sort by best fit, or tournament selection?)
			//to be passed to next population (should this include current parents?)
			
			
			//Selected children become new parent population
			//Reset children list
		}
		
		
		return children;
	}
	
	public static Solution Crossover(Solution p1, Solution p2){
		boolean[] vars = new boolean[p1.getVars().length];
		
		//Find maximum value in p1.trueClauses
		int max = 0;
		for(int i = 1; i < p1.trueClauses.length; i++){
			if(p1.trueClauses[i] > max){
				max = p1.trueClauses[i];
			}
		}
		
		//Fill in child values using good p1 values
		max = (int) max/2;
		for(int i = 1; i < p1.getVars().length; i++){
			if(p1.trueClauses[i] >= max){
				vars[i] = p1.getVars()[i];
			}
		}
		
		//Fill in remainder of child with values from p2
		for(int i = 1; i < p2.getVars().length; i++){
			if(vars[i] != p1.getVars()[i]){
				vars[i] = p2.getVars()[i];
			}
		}
		
		
		
		Solution child = new Solution(vars);
				
		return child;
	}
	
	public void Mutate(Solution s){
		//Randomly flipping bits will probably be best
	}
	
	public Solution tournamentSelection(List<Solution> parents, int n){
		//Method for selecting a parent from 5 random individuals
		//Returns individual with best fitness
		Solution best = null;
		Random rng = new Random();
		for(int i = 1; i <= n; i++){
			int r = rng.nextInt(parents.size());
			Solution t = parents.get(r);
			if(best == null || best.getFitness() > t.getFitness()){
				best = t;
			}
		}
		return best;
	}
	
}
