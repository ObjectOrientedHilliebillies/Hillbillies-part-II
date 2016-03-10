package homeMadeTests;

import java.util.Arrays;

import org.junit.Test;
import ogp.framework.util.Util;

public class SyntaxTest {
	@Test
	public final void test(){
		int[] a = new int[3];
		for (int i=0; i != 3; i++){
			a[i]=i;
		}
		double b; 
		double c;
		double d;
		
		b = a[0];
		c = a[1];
		d = a[2];
		
		System.out.println(d);
}
}
