
public class Solution {

	private boolean[] vars;
	private float fitness;
	private int numTrue, sequenceStart, sequenceLength;
	public int[] trueClauses;
	public int[] numClauses;
	public float[] crossoverValue;

	
	public Solution(boolean[] v){
		this.vars = v;
		this.trueClauses = new int[v.length];
		this.numClauses = new int[v.length];
		this.crossoverValue = new float[v.length];
		for(int i = 0; i < trueClauses.length; i++){
			this.trueClauses[i] = 0;
			this.numClauses[i] = 0;
			this.crossoverValue[i] = 0;
		}
	}
	
	public void calculateFitness(Clause[] clauses){
		this.numTrue = 0;
		for(Clause c : clauses){
			int v1 = Math.abs(c.getVar1());
			int v2 = Math.abs(c.getVar2());
			int v3 = Math.abs(c.getVar3());
			this.numClauses[v1]++;
			this.numClauses[v2]++;
			this.numClauses[v3]++;
			
			if(((c.getVar1() > 0) && (vars[v1] == true)) || ((c.getVar1() < 0) && (vars[v1] == false))){
				this.numTrue++;
				this.trueClauses[v1]++;
			}
			else if(((c.getVar2() > 0) && (vars[v2] == true)) || ((c.getVar2() < 0) && (vars[v2] == false))){
				this.numTrue++;
			}
			else if(((c.getVar3() > 0) && (vars[v3] == true)) || ((c.getVar3() < 0) && (vars[v3] == false))){
				this.numTrue++;
			}
			
			if(((c.getVar2() > 0) && (vars[v2] == true)) || ((c.getVar2() < 0) && (vars[v2] == false))){
				this.trueClauses[v2]++;
			}
			if(((c.getVar3() > 0) && (vars[v3] == true)) || ((c.getVar3() < 0) && (vars[v3] == false))){
				this.trueClauses[v3]++;
			}
		}
		
		this.fitness = ((float) this.numTrue/clauses.length)*100;
		for(int i = 0; i < numClauses.length; i++){
			if(this.numClauses[i] > 0){
				this.crossoverValue[i] = (float)(this.trueClauses[i]/this.numClauses[i]);
			}
			else{
				this.crossoverValue[i] = 0;
			}
		}
	}
	
	public void setStart(int s){
		this.sequenceStart = s;
	}
	
	public int getStart(){
		return this.sequenceStart;
	}
	
	public void setLength(int l){
		this.sequenceLength = l;
	}
	
	public int getLength(){
		return this.sequenceLength;
	}
	
	public int getNumTrue(){
		return this.numTrue;
	}
	
	public boolean[] getVars(){
		return this.vars;
	}
	
	public void setVars(boolean[] v){
		this.vars = v;
	}
	
	public float getFitness(){
		return this.fitness;
	}

	public int[] getTrueClauses() {
		return trueClauses;
	}

	public void setTrueClauses(int[] trueClauses) {
		this.trueClauses = trueClauses;
	}
	
	public void flipBit(int i){
		this.vars[i] = !this.vars[i];
	}
}
