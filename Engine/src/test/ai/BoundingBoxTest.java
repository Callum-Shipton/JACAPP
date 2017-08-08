package test.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joml.Vector2i;
import org.joml.Vector4i;
import org.junit.Test;

import ai.goalbounding.BoundingBox;

public class BoundingBoxTest {

	@Test
	public void addOutsidePointsTest() {
		BoundingBox boundingBox = new BoundingBox(new Vector4i(1, 1, 10, 10));

		boundingBox.addPoint(new Vector2i(1, 11));
		assertEquals(new BoundingBox(new Vector4i(1, 1, 10, 11)), boundingBox);

		boundingBox = new BoundingBox(new Vector4i(1, 1, 10, 10));
		boundingBox.addPoint(new Vector2i(11, 1));
		assertEquals(new BoundingBox(new Vector4i(1, 1, 11, 10)), boundingBox);

		boundingBox = new BoundingBox(new Vector4i(1, 1, 10, 10));
		boundingBox.addPoint(new Vector2i(1, 0));
		assertEquals(new BoundingBox(new Vector4i(1, 0, 10, 10)), boundingBox);

		boundingBox = new BoundingBox(new Vector4i(1, 1, 10, 10));
		boundingBox.addPoint(new Vector2i(0, 1));
		assertEquals(new BoundingBox(new Vector4i(0, 1, 10, 10)), boundingBox);
	}

	@Test
	public void addInsidePointsTest() {
		Vector4i box = new Vector4i(0, 0, 10, 10);
		BoundingBox boundingBox = new BoundingBox(box);

		boundingBox.addPoint(new Vector2i(5, 5));
		assertEquals(new BoundingBox(box), boundingBox);

		boundingBox.addPoint(new Vector2i(10, 10));
		assertEquals(new BoundingBox(box), boundingBox);
	}

	@Test
	public void boxContains() {
		BoundingBox boundingBox = new BoundingBox(new Vector4i(0, 0, 10, 10));

		assertTrue(boundingBox.boxContains(new Vector2i(5, 5), 1));
		assertTrue(boundingBox.boxContains(new Vector2i(10, 10), 1));
		assertFalse(boundingBox.boxContains(new Vector2i(11, 11), 1));
	}

}
