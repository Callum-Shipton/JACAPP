package ai;

import java.io.FileInputStream;
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

	public GoalboundingTile getTile(float x, float y, float size) {
		Logger.debug("X:" + x + " Y:" + y + " Size:" + size, Category.AI_GOALBOUNDING);
		return goalboundingMaps.get((int) size)[(int) x][(int) y];
	}

	public static GoalBounder readGoalbounder(String location) {
		GoalBounder goalBounder = null;
		try (FileInputStream fileIn = new FileInputStream(location)) {
			ObjectInputStream in = new ObjectInputStream(fileIn);
			goalBounder = (GoalBounder) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			Logger.error(i);
		} catch (ClassNotFoundException c) {
			Logger.error("Employee class not found");
			Logger.error(c);
		}

		return goalBounder;
	}
}
