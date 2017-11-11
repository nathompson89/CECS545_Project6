import java.util.ArrayList;
import java.util.List;

public class GA {
	
	
	public static Solution GeneticAlgorithm(SAT sat){
		ArrayList<Solution> parents = new ArrayList<Solution>();
		ArrayList<Solution> children = new ArrayList<Solution>();
		ArrayList<Solution> newGen = new ArrayList<Solution>();
		int numGens = 80;
		int popSize = 80; 
		double mutationRate = 0.025;
		
		parents.addAll(genInitial(sat, (popSize-parents.size())));
		
		float firstGenBest = getBest(parents).getFitness();
		for(int i = 0; i < numGens; i++){
				
			for(int j = 0; j < popSize; j++){
				//System.out.println(parents.size());

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
				
				children.add(child1);
				children.add(child2);
				
				Sort.SortList(newGen, child1, popSize);
				Sort.SortList(newGen, child2, popSize);
			}
			
			for(Solution s : parents){
				Sort.SortList(newGen, s, popSize);
			}
			parents = new ArrayList<Solution>(newGen);
			
			newGen.clear();
			
			parents.addAll(genInitial(sat, (popSize-parents.size())));

			//Reset children list
			children.clear();
			
		}
		
		
		Solution bestSolution = getBest(parents);
		bestSolution.setImprovement(bestSolution.getFitness() - firstGenBest);
		return bestSolution;
	}
	
	public static Solution Crossover(Solution p1, Solution p2){
		boolean[] vars = new boolean[p1.getVars().length];
		
		//Fill in child values using good p1 values
		boolean[] taken = new boolean[p1.getVars().length];
		for(int i = 1; i < p1.getVars().length; i++){
			if(p1.crossoverValue[i] > 0.5f){
			//if(p1.percentTrueClauses[i] > 0.95){
				vars[i] = p1.getVars()[i];
				taken[i] = true;
			}
		}
		
		//Fill in remainder of child with values from p2
		for(int i = 1; i < p2.getVars().length; i++){
			if(taken[i] == false){
				vars[i] = p2.getVars()[i];
			}
		}	
		
		Solution child = new Solution(vars);
		
		return child;
	}
	
	/*public static Solution Crossover1(Solution p1, Solution p2){
		boolean[] vars = new boolean[p1.getVars().length];
		
		int n1 = (int) Math.random() * p1.getVars().length;
		int n2 = (int) Math.random() * p1.getVars().length;
		
		int start = Math.min(n1, n2);
		int end = Math.max(n1, n2);
		
		for(int i = start; i <= end; i++){
			vars[i] = p1.getVars()[i];
		}
		
		for(int i = 0; i < p2.getVars().length; i++){
			if(i < start || i > end){
				vars[i] = p2.getVars()[i];
			}
		}
		
		Solution child = new Solution(vars);
		return child;
	}*/
	
	//method to generate the starting parents 
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
	
	/*public static Solution mutate1(Solution s, Clause[] clauses){
		boolean[] vars = s.getVars();
		s.percentTrueClauses(clauses);
		for(int i = 1; i < vars.length; i++){
			
			//System.out.println(s.percentTrueClauses[i]);
			if(s.percentTrueClauses[i] < .80){
				vars[i] = !vars[i];
				s.percentTrueClauses(clauses);
			}
		}
		//Solution newS = new Solution(vars);
		s.setVars(vars);
		return s;
	}*/
	
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
			if(s.getFitness() > bestSolution.getFitness())
				bestSolution = s;
		}
		
		//return best parent
		return bestSolution;
	}
	
	private static ArrayList<Solution> getGreedySolutions(Clause[] clauses){
		ArrayList<Solution> greedy = new ArrayList<Solution>();
		
		for(int i = 0; i < 6; i++){
			boolean[] vars = new boolean[101];
			boolean[] tracker = new boolean[101];
			for(int j = 0; j < clauses.length; j++){
				int v1 = Math.abs(clauses[j].getVar1());
				int v2 = Math.abs(clauses[j].getVar2());
				int v3 = Math.abs(clauses[j].getVar3());
				if(i == 0){ //First var
					if(clauses[j].getVar1() < 0 && tracker[v1] == false){
						vars[v1] = false;
						tracker[v1] = true;
					}
					else if(clauses[j].getVar1() >= 0 && tracker[v1] == false){
						vars[v1] = true;
						tracker[v1] = true;
					}
				}
				if(i == 1){ //Second var
					if(clauses[j].getVar2() < 0 && tracker[v2] == false){
						vars[v2] = false;
						tracker[v2] = true;
					}
					else if(clauses[j].getVar2() >= 0 && tracker[v2] == false){
						vars[v2] = true;
						tracker[v2] = true;
					}
				}
				if(i == 2){ //Third var
					if(clauses[j].getVar3() < 0 && tracker[v3] == false){
						vars[v3] = false;
						tracker[v3] = true;
					}
					else if(clauses[j].getVar3() >= 0 && tracker[v3] == false){
						vars[v3] = true;
						tracker[v3] = true;
					}
				}
				if(i == 3){ //First var
					if(clauses[clauses.length-j-1].getVar1() < 0 && tracker[v1] == false){
						vars[v1] = false;
						tracker[v1] = true;
					}
					else if(clauses[clauses.length-j-1].getVar1() >= 0 && tracker[v1] == false){
						vars[v1] = true;
						tracker[v1] = true;
					}
				}
				if(i == 4){ //Second var
					if(clauses[clauses.length-j-1].getVar2() < 0 && tracker[v2] == false){
						vars[v2] = false;
						tracker[v2] = true;
					}
					else if(clauses[clauses.length-j-1].getVar2() >= 0 && tracker[v2] == false){
						vars[v2] = true;
						tracker[v2] = true;
					}
				}
				if(i == 5){ //Third var
					if(clauses[clauses.length-j-1].getVar3() < 0 && tracker[v3] == false){
						vars[v3] = false;
						tracker[v3] = true;
					}
					else if(clauses[clauses.length-j-1].getVar3() >= 0 && tracker[v3] == false){
						vars[v3] = true;
						tracker[v3] = true;
					}
				}
			}
			int notSet = 0;
			for(int k = 0; k < tracker.length; k++){
				if(tracker[k] == false){
					notSet++;
					if(Math.random() < 0.5){
						vars[k] = true;
					}
					else{
						vars[k] = false;
					}
				}
			}
			Solution greedySol = new Solution(vars);
			greedySol.calculateFitness(clauses);
			//System.out.println(notSet + " variables not set");
			//System.out.print("\nGreedy solution " + i + ": ");
			//greedySol.printSolution();
			//System.out.println("\n" + greedySol.getFitness());
			greedy.add(greedySol);
		}
		
		return greedy;
	}
	
	private static float getAverageFitness(ArrayList<Solution> solutions){
		float total = 0;
		for(Solution s : solutions){
			total += s.getFitness();
		}
		
		return total/solutions.size();
	}
}
