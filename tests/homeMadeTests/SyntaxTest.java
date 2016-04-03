package homeMadeTests;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import oracle.jrockit.jfr.settings.EventDefaultSet;

public class SyntaxTest {
	
	@Test
	public final void test(){
		Set<Integer> testSet = new HashSet<Integer>();
		for (int i=0 ; i != 15 ; i++){
			testSet.add(i);
		}
		System.out.println(testSet.toString());
		Set<Integer> evenSet = getEven(testSet);
		System.out.println(evenSet.toString());
		System.out.println(testSet.toString());
	}
	
	public Set<Integer> getEven(Set<Integer> givenSet){
		Set<Integer> returnSet = new HashSet<>();
		for (Integer j : givenSet){
			if (j%2 == 0){
				returnSet.add(j);
			}
		}
		return returnSet;
	}
}
