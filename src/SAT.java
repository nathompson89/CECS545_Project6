import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SAT {

	//private boolean[] vars; //Assignments for each variable
	private Clause[] clauses; //Represents boolean expression in CNF
	//private float fitness; //Score relative to how many clauses evaluate to true
	private int numVars = 0;
	
	public SAT(String filename) throws FileNotFoundException{
		//this.vars = new boolean[numVars + 1];
		readSATFile(filename);
	}

	public void readSATFile(String filename) throws FileNotFoundException{
		
		Scanner scan1 = new Scanner(new File(filename));
		int numClauses = 0;
		
		
		for(int i = 0; i < 8; i++){
			if(i == 7){
				scan1.next();
				scan1.next();
				numVars = scan1.nextInt();
				numClauses = scan1.nextInt();
			}
			scan1.nextLine();
		}
		//while(scan1.hasNextLine()){
		//	numClauses++;
		//	scan1.nextLine();
		//}
		
		scan1.close();
		
		Clause c[] = new Clause[numClauses];
		Scanner scan2 = new Scanner(new File(filename));
		
		int var1, var2, var3;
		for(int i = 0; i <= 7; i++){
			System.out.println(scan2.nextLine());
		}
		System.out.println(numClauses);
		for(int i = 0; i < numClauses; i++){
			var1 = scan2.nextInt();
			var2 = scan2.nextInt();
			var3 = scan2.nextInt();
			//System.out.println(i + " " + var1 + " " + var2 + " " + var3);
			scan2.nextInt(); //Read extra 0 at the end of each clause
			Clause newClause = new Clause(var1, var2, var3);
			c[i] = newClause;
		}

		this.clauses = c;
	}
	
	public void setClauses(Clause[] c){
		this.clauses = c;
	}
	
	public Clause[] getClauses(){
		return this.clauses;
	}
	
	public Clause getClause(int i){
		return this.clauses[i];
	}
	
	public int getNumVars() {
		return numVars;
	}

	public void setNumVars(int numVars) {
		this.numVars = numVars;
	}
}
