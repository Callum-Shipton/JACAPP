package test.ai;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joml.Vector2i;
import org.junit.Test;

import ai.AStarSearch;

public class AStarSearchTest {

	@Test
	public void containsGoalTest() {
		Vector2i goal = new Vector2i(5, 5);

		Vector2i position1 = new Vector2i(5, 5);
		Vector2i position2 = new Vector2i(4, 4);
		Vector2i position3 = new Vector2i(3, 3);
		Vector2i position4 = new Vector2i(2, 2);

		assertTrue(AStarSearch.containsGoal(position1, 1, goal));
		assertFalse(AStarSearch.containsGoal(position2, 1, goal));

		assertTrue(AStarSearch.containsGoal(position2, 2, goal));
		assertFalse(AStarSearch.containsGoal(position3, 2, goal));

		assertTrue(AStarSearch.containsGoal(position3, 3, goal));
		assertFalse(AStarSearch.containsGoal(position4, 3, goal));
	}

	@Test
	public void movesIntoWallTest() {

	}

}
