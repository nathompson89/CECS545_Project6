import java.util.ArrayList;

public class GA {
	
	public static Solution GeneticAlgorithm(SAT sat){
		ArrayList<Solution> parents = new ArrayList<Solution>();
		ArrayList<Solution> children = new ArrayList<Solution>();
		int numGens = 500;
		int popSize = 100; 
		double mutationRate = 0.01;
		
		//Initialize parents as initial population
		parents = genInitial(sat, popSize);
		
		for(int i = 0; i < numGens; i++){
				
			for(int j = 0; j < popSize; j++){
				//Choose 2 parents via tournament selection
				Solution parent1 = tournamentSelection(parents, 5);
				Solution parent2 = tournamentSelection(parents, 5);
				
				//Crossover p1 and p2
				Solution child1 = Crossover(parent1, parent2);
				
				//Test for mutation of child1
				if((Math.random() < mutationRate)){
					child1 = mutate(child1);
				} 
				
				//Crossover p2 and p1
				Solution child2 = Crossover(parent2, parent1);
				
				//Test for mutation of child2
				if((Math.random() < mutationRate)){
					child2 = mutate(child2);
				} 
				
				child1.calculateFitness(sat.getClauses());
				child2.calculateFitness(sat.getClauses());
				
				//get best child
				if(child1.getFitness() > child2.getFitness()){
					children.add(child1);
				} else {
					children.add(child2);
				}
			}
			
			//Selected children become new parent population
			parents = new ArrayList<Solution>(children);
			
			//Reset children list
			children.clear();
		}
		
		Solution bestSolution = getBest(parents);
		
		return bestSolution;
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
	
	private static ArrayList<Solution> genInitial(SAT sat, int popSize){
		
		ArrayList<Solution> initial = new ArrayList<Solution>();
		
		for(int i = 0; i < popSize; i++) {
			boolean[] sol = new boolean[sat.getNumVars() + 1];
			for(int j = 0; j < sol.length; j++) {
				if(Math.random() < 0.5){
					sol[j] = true;
				} else {
					sol[j] = false;
				}
			}
			
			Solution s = new Solution(sol);
			s.calculateFitness(sat.getClauses());
			initial.add(s);
		}

		return initial;
	}
	
	public static Solution mutate(Solution s){
		//make list from vars in solution 
		boolean[] varList = s.getVars();
		ArrayList<Integer> elements = new ArrayList<Integer>();
		int i = 0;
		
		//generate the five random elements without repeating any
		while(i < 5){
			int rand = (int) (Math.random() * s.getVars().length);
			
			if(!elements.contains(rand)){
				elements.add(rand);
				i++;
			}
		}
		
		//flip the bit of the five random elements
		for(int e: elements){
			varList[e] = !varList[e];
		}
		
		//set the vars of s to the mutated varList
		s.setVars(varList);
		
		return s;
	}
	
	public static Solution tournamentSelection(ArrayList<Solution> parents, int n){
		//Method for selecting a parent from 5 random individuals
		//Returns individual with best fitness
		Solution best = null;
		for(int i = 1; i <= n; i++){
			int r = (int) (Math.random() * parents.size());
			Solution t = parents.get(r);
			if(best == null || best.getFitness() > t.getFitness()){
				best = t;
			}
		}
		return best;
	}
	
	private static Solution getBest(ArrayList<Solution> parents) {
		//start with the first parent in the list
		Solution bestSolution = parents.get(0);
		
		//compare parents
		for(Solution s: parents){
			if(bestSolution.getFitness() < s.getFitness())
				bestSolution = s;
		}
		
		//return best parent
		return bestSolution;
	}
	
}
