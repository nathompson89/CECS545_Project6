
public class Solution {

	private boolean[] vars;
	private float fitness;
	private int numTrue, sequenceStart, sequenceLength;
	
	public int[] trueClauses;
	
	public Solution(boolean[] v){
		this.vars = v;
		this.trueClauses = new int[v.length+1];
		for(int i : this.trueClauses){
			i = 0;
		}
	}
	
	public boolean[] getVars(){
		return this.vars;
	}
	
	public float getFitness(){
		return this.fitness;
	}
	
	public void calculateFitness(Clause[] clauses){
		this.numTrue = 0;
		for(Clause c : clauses){
			int v1 = Math.abs(c.getVar1());
			int v2 = Math.abs(c.getVar2());
			int v3 = Math.abs(c.getVar3());
			
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
}
