package MainPackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class testdecode {

	@Test
	public void test1() {
		Decode d = new Decode();
        int[] actual = d.e(60);
        int[] expected = {4,3,0}; 
        assertArrayEquals(expected, actual);
	}

}
