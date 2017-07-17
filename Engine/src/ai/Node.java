package ai;

import java.util.Set;

import org.joml.Vector2i;

public class Node {
	protected Vector2i position;
	protected int size;

	public Node(Vector2i position, int size) {
		this.position = position;
		this.size = size;
	}

	public Vector2i getPosition() {
		return position;
	}

	public int getSize() {
		return size;
	}

	public static boolean movesIntoWall(Node node, Set<Vector2i> walls, String key) {

		Vector2i position = node.getPosition();
		int size = node.getSize();

		switch (key) {
		case "NW":
			return checkEdgesForWalls(position, size + 1, walls, "N")
					|| checkEdgesForWalls(position, size + 1, walls, "W");
		case "NE":
			return checkEdgesForWalls(new Vector2i(position.x() - 1, position.y()), size + 1, walls, "N")
					|| checkEdgesForWalls(new Vector2i(position.x() - 1, position.y()), size + 1, walls, "E");
		case "SW":
			return checkEdgesForWalls(new Vector2i(position.x(), position.y() - 1), size + 1, walls, "S")
					|| checkEdgesForWalls(new Vector2i(position.x(), position.y() - 1), size + 1, walls, "W");
		case "SE":
			return checkEdgesForWalls(new Vector2i(position.x() - 1, position.y() - 1), size + 1, walls, "S")
					|| checkEdgesForWalls(new Vector2i(position.x() - 1, position.y() - 1), size + 1, walls, "E");
		default:
			return checkEdgesForWalls(position, size, walls, key);
		}

	}

	private static boolean checkEdgesForWalls(Vector2i position, int size, Set<Vector2i> walls, String key) {
		switch (key) {
		case "N":
			for (int i = position.x(); i < (position.x() + size); i++) {
				if (walls.contains(new Vector2i(i, position.y()))) {
					return true;
				}
			}
			break;
		case "S":
			for (int i = position.x(); i < (position.x() + size); i++) {
				if (walls.contains(new Vector2i(i, (position.y() + size) - 1))) {
					return true;
				}
			}
			break;
		case "W":
			for (int j = position.y(); j < (position.y() + size); j++) {
				if (walls.contains(new Vector2i(position.x(), j))) {
					return true;
				}
			}
			break;
		case "E":
			for (int j = position.y(); j < (position.y() + size); j++) {
				if (walls.contains(new Vector2i((position.x() + size) - 1, j))) {
					return true;
				}
			}
			break;
		}
		return false;
	}
}
