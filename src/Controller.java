import java.io.FileNotFoundException;
import java.util.Collections;

public class Controller {

	public static void main(String[] args) throws FileNotFoundException {
		
		SAT sat = new SAT("SAT1.CNF");
		for(Clause c : sat.getClauses()){
			System.out.println(c.getVar1() + " " + c.getVar2() + " " + c.getVar3());
		}
		
		boolean[] test = new boolean[101];
		for(boolean b : test){
			b = true;
		}
		
		Solution s = new Solution(test);
		s.calculateFitness(sat.getClauses());
		System.out.println("Fitness percentage: " + s.getFitness());
		System.out.println("Number of true clauses: " + s.getNumTrue());
		
		for(int i = 1; i < s.trueClauses.length-1; i++){
			System.out.println("Variable " + i + " makes " + s.trueClauses[i] + " clauses true.");
		}
		
		//Call GA.GeneticAlgorithm() to get list of solutions to pass to WOC
		//Call WOC.WisdomOfCrowds() to get hopefully better solution
	}

}
