
public class Clause {

	private boolean bool1;
	private boolean bool2;
	private boolean bool3;
	
	private boolean isTrue;
	
	public boolean getBool(int b){
		if(b == 1){
			return bool1;
		}
		else if(b == 2){
			return bool2;
		}
		else{
			return bool3;
		}
	}
}
