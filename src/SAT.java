import java.util.ArrayList;
import java.util.List;

public class SAT {

	private boolean[] vars; //Assignments for each variable
	private int[][] clauses; //Represents boolean expression in CNF
	private float fitness; //Score relative to how many clauses evaluate to true
	
	public SAT(int numVars, int numClauses, int clauseSize){
		this.vars = new boolean[numVars + 1];
		this.clauses = new int[numClauses][clauseSize];
	}
	
	public SAT readSATFile(){
		
		//read file first time to get variable sizes
		
		int numVars = 0, numClauses = 0, clauseSize = 3;
		boolean[] v = new boolean[numVars];
		int[][] c = new int[numClauses][clauseSize];
		
		//read file second time to store data

		
		
		
		
		
		
		SAT sat = new SAT(numVars, numClauses, clauseSize);
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
	
	public void setClauses(int[][] c){
		this.clauses = c;
	}
	
	public int[][] getClauses(){
		return this.clauses;
	}
	
	public int[] getClauses(int i){
		return this.clauses[i];
	}
	
	public int getClauses(int i, int j){
		return this.clauses[i][j];
	}
}
