package test.main;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import display.Art;
import main.ShootEmUp;

public class ShootEmUpTest {

	@Test
	public void mainTest() {
		TestLoop loop = new TestLoop(new ShootEmUp(), 60.0f, new Art());
		loop.run();
		assertTrue(loop.isFinished());
	}
}
