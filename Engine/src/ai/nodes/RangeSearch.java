package ai.nodes;

import java.util.HashSet;
import java.util.Set;

import org.joml.Vector2i;

public class RangeSearch {

	private Set<Vector2i> walls;
	private double viewRange;
	private boolean on = true;

	public RangeSearch(Set<Vector2i> walls, double viewRange) {
		this.walls = walls;
		this.viewRange = viewRange;
	}

	public boolean enemyInRange(Vector2i start, Vector2i goal) {
		if (!on) {
			return true;
		}

		if (start.distance(goal) < viewRange) {
			Set<Vector2i> viewTiles = getTilesOnLine(start, goal);
			for (Vector2i tile : viewTiles) {
				if (walls.contains(tile)) {
					return false;
				}
			}
			return true;
		}

		return false;

	}

	private static Set<Vector2i> getTilesOnLine(Vector2i start, Vector2i goal) {
		Set<Vector2i> tiles = new HashSet<>();
		int x0 = start.x();
		int y0 = start.y();
		int x1 = goal.x();
		int y1 = goal.y();

		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;

		int err = dx - dy;
		int e2;

		while (true) {
			tiles.add(new Vector2i(x0, y0));

			if (x0 == x1 && y0 == y1)
				break;

			e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x0 = x0 + sx;
			}

			if (e2 < dx) {
				err = err + dx;
				y0 = y0 + sy;
			}
		}

		return tiles;
	}

	public void setOn(boolean state) {
		on = state;
	}
}
