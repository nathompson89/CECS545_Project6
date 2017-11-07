import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SAT {

	private boolean[] vars; //Assignments for each variable
	private Clause[] clauses; //Represents boolean expression in CNF
	private float fitness; //Score relative to how many clauses evaluate to true
	
	public SAT(int numVars, int numClauses){
		this.vars = new boolean[numVars + 1];
		this.clauses = new Clause[numClauses];
	}
	
	public SAT readSATFile(String filename) throws FileNotFoundException{
		
		Scanner scan = new Scanner(new File(filename));
		//read file first time to get variable sizes
		
		int numVars = 0, numClauses = 0;
		boolean[] v = new boolean[numVars];
		Clause c[] = new Clause[numClauses];
		
		//read file second time to store data

		
		
		
		
		
		
		SAT sat = new SAT(numVars, numClauses);
		sat.setVars(v);
		sat.setClauses(c);
		return sat;
	}
	
	public void evaluateFitness(){
		//set fitness value
		//assign score based on number of clauses that are true
	}
	
	public float getFitness(){
		return this.fitness;
	}
	
	public void setVars(boolean[] v){
		this.vars = v;
	}
	
	public boolean[] getVars(){
		return this.vars;
	}
	
	public boolean getVars(int i){
		return this.vars[i];
	}
	
	public void setClauses(Clause[] c){
		this.clauses = c;
	}
	
	public Clause[] getClauses(){
		return this.clauses;
	}
	
	public Clause getClauses(int i){
		return this.clauses[i];
	}
	
}
