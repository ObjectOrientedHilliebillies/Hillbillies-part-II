package homeMadeTests;

import java.util.Arrays;

import org.junit.Test;
import ogp.framework.util.Util;

public class SyntaxTest {
	@Test
	public final void test(){
		String a;
		String b;
		
		a = "a";
		b = a;
		a = "x";
		
		System.out.println(b);
}
}
