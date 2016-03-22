package homeMadeTests;

import java.util.Arrays;

import org.junit.Test;
import ogp.framework.util.Util;

public class SyntaxTest {
	@Test
	public final void test(){
		int teller = 0; 
		int[][][] a = new int[3][4][5];
		for (int i=0; i != 3; i++){
			for (int j=0; j != 4; j++){
				for (int k=0; k != 4; k++){
					a[i][j][k] = teller;
					teller = teller+1;
				}
			}
		}
		
		System.out.println(a.length);
		System.out.println(a[0].length);
		System.out.println(a[0][0].length);
		System.out.println(Arrays.deepToString(a));
}
}
