package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joml.Vector4f;
import org.junit.Test;

import ai.BoundingBox;
import math.Vector2;

public class BoundingBoxTest {

	@Test
	public void addOutsidePointsTest() {
		BoundingBox boundingBox = new BoundingBox(new Vector4f(1, 1, 10, 10));

		boundingBox.addPoint(new Vector2(1, 11));
		assertEquals(new BoundingBox(new Vector4f(1, 1, 10, 11)), boundingBox);

		boundingBox = new BoundingBox(new Vector4f(1, 1, 10, 10));
		boundingBox.addPoint(new Vector2(11, 1));
		assertEquals(new BoundingBox(new Vector4f(1, 1, 11, 10)), boundingBox);

		boundingBox = new BoundingBox(new Vector4f(1, 1, 10, 10));
		boundingBox.addPoint(new Vector2(1, 0));
		assertEquals(new BoundingBox(new Vector4f(1, 0, 10, 10)), boundingBox);

		boundingBox = new BoundingBox(new Vector4f(1, 1, 10, 10));
		boundingBox.addPoint(new Vector2(0, 1));
		assertEquals(new BoundingBox(new Vector4f(0, 1, 10, 10)), boundingBox);
	}

	@Test
	public void addInsidePointsTest() {
		Vector4f box = new Vector4f(0, 0, 10, 10);
		BoundingBox boundingBox = new BoundingBox(box);

		boundingBox.addPoint(new Vector2(5, 5));
		assertEquals(new BoundingBox(box), boundingBox);

		boundingBox.addPoint(new Vector2(10, 10));
		assertEquals(new BoundingBox(box), boundingBox);
	}

	@Test
	public void boxContains() {
		BoundingBox boundingBox = new BoundingBox(new Vector4f(0, 0, 10, 10));

		assertTrue(boundingBox.boxContains(new Vector2(5, 5), 1));
		assertTrue(boundingBox.boxContains(new Vector2(10, 10), 1));
		assertFalse(boundingBox.boxContains(new Vector2(11, 11), 1));
	}

}
