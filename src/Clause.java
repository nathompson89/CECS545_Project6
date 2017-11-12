
public class Clause {

	private int var1, var2, var3;
	
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
	
	public boolean[] isTrue(boolean[] vars){
		boolean b1, b2, b3;
		if((var1 >= 0 && vars[Math.abs(var1)] == true) || (var1 < 0 && vars[Math.abs(var1)] == false)){
			b1 = true;
		}
		else{
			b1 = false;
		}
		
		if((var2 >= 0 && vars[Math.abs(var2)] == true) || (var2 < 0 && vars[Math.abs(var2)] == false)){
			b2 = true;
		}
		else{
			b2 = false;
		}
		
		if((var3 >= 0 && vars[Math.abs(var3)] == true) || (var3 < 0 && vars[Math.abs(var3)] == false)){
			b3 = true;
		}
		else{
			b3 = false;
		}
		
		boolean[] b = new boolean[]{b1, b2, b3};
		return b;
	}
	
}
