package test.main;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.ShootEmUp;

public class ShootEmUpTest {

	private int numRuns = 50;
	private int[] runs = new int[numRuns];
	
	@Test
	public void mainTest() {
		for (int i : runs) {
		TestLoop loop = new TestLoop(new ShootEmUp(), 60.0f);
		loop.run();
		assertTrue(loop.isFinished());
		}
	}
}
