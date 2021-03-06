package ai;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;

import logging.Logger;
import logging.Logger.Category;

public class GoalBounder implements Serializable {

	private static final long serialVersionUID = 5745028269890683780L;

	private final Map<Integer, GoalboundingTile[][]> goalboundingMaps;

	public GoalBounder(Map<Integer, GoalboundingTile[][]> goalboundingMaps) {
		this.goalboundingMaps = goalboundingMaps;
	}

	public GoalboundingTile getTile(int x, int y, int size) {
		Logger.debug("X:" + x + " Y:" + y + " Size:" + size, Category.AI_GOALBOUNDING);
		return goalboundingMaps.get(size)[x][y];
	}

	public static GoalBounder readGoalbounder(String location) {
		GoalBounder goalBounder = null;
		try (ObjectInputStream in = new ObjectInputStream(GoalBounder.class.getResourceAsStream(location))) {
			goalBounder = (GoalBounder) in.readObject();
			in.close();
		} catch (IOException i) {
			Logger.error(i);
		} catch (ClassNotFoundException c) {
			Logger.error("Employee class not found");
			Logger.error(c);
		}

		return goalBounder;
	}
}
