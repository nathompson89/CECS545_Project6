import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Sort {

	public static void SortList(ArrayList<Solution> solutions, Solution s, int numSolutions){
		if(solutions.size() == 0){
			solutions.add(s);
		}
		else{
			
			if(solutions.size() == numSolutions){
				
				if(solutions.get(numSolutions-1).getFitness() >= s.getFitness()){
					return;
				}
				else{
					for(int i = 0; i < solutions.size(); i++){
						if(/*s.getFitness() == solutions.get(i).getFitness() && */Arrays.equals(s.getVars(), solutions.get(i).getVars())){
							return;
						}
						else if(s.getFitness() >= solutions.get(i).getFitness()){
						//if(s.getFitness() >= solutions.get(i).getFitness()){
							//System.out.println(numSolutions + " " + solutions.size());
							//scan.nextLine();
							solutions.add(i, s);
							solutions.remove(numSolutions);
							return;
						}
					}
				}
			}
			else{
				for(int i = 0; i < solutions.size(); i++){
					if (s.getFitness() == solutions.get(i).getFitness())
                    {
                        return;
                    }
                    
					else if (s.getFitness() >= solutions.get(i).getFitness())
                    {
                    	solutions.add(i, s);
                        return;
                    }
                    //If the current child needs to be the
                    //last item in the list for the first time
                    else if(i == solutions.size() - 1)
                    {
                        solutions.add(s);
                        return;
                    }
				}
			}

		}
	}
}
