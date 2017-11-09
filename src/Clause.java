
public class Clause {

	private int var1, var2, var3;
	
	private boolean isTrue;
	
	public Clause(int v1, int v2, int v3){
		this.var1 = v1;
		this.var2 = v2;
		this.var3 = v3;
	}
	
	public int getVar1(){
		return this.var1;
	}
	
	public int getVar2(){
		return this.var2;
	}

	public int getVar3(){
		return this.var3;
	}
}
