package components.control;

import java.util.HashSet;
import java.util.PriorityQueue;

import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import level.Map;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class AIControl extends BaseControl {

	private BaseMovement BM;
	private BaseGraphics BG;
	private BaseAttack BA;

	private int counter = 0;
	private Vector2 target;
	Map map;

	public AIControl(BaseGraphics BG, BaseAttack BA, BaseMovement BM) {
		this.BA = BA;
		this.BG = BG;
		this.BM = BM;
		map = ShootEmUp.currentLevel.map;
	}

	@Override
	public void update(Entity e) {
		target = ai();
		float y = target.y() * Map.TILE_HEIGHT;
		float x = target.x() * Map.TILE_WIDTH;

		if (target != null) {
			Vector2 movement = new Vector2(0.0f, 0.0f);
			if (y < BG.getY()) {
				if ((y - BG.getY()) > -BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - BG.getY())));
				} else {
					movement.add(0.0f, -1.0f);
				}
			}
			if (x < BG.getX()) {
				if ((x - BG.getX()) > -BM.getSpeed()) {
					movement.add(((1.0f / BM.getSpeed()) * (x - BG.getX())), 0.0f);
				} else {
					movement.add(-1.0f, 0.0f);
				}
			}
			if (y > BG.getY()) {
				if ((y - BG.getY()) < BM.getSpeed()) {
					movement.add(0.0f, ((1.0f / BM.getSpeed()) * (y - BG.getY())));
				} else {
					movement.add(0.0f, 1.0f);
				}
			}
			if (x > BG.getX()) {
				if ((x - BG.getX()) < BM.getSpeed()) {
					movement.add(((1.0f / BM.getSpeed()) * (x - BG.getX())), 0.0f);
				} else {
					movement.add(1.0f, 0.0f);
				}
			}
			if (movement.length() > 0) {
				if (movement.length() > 1) {
					movement.normalize();
				}
				if (BG instanceof AnimatedGraphics) {
					((AnimatedGraphics) BG).setAnimating(true);
				}
				BM.move(e, movement);
				if (BG instanceof AnimatedGraphics) {
					((AnimatedGraphics) BG).setDirection((int) (Math.round(movement.Angle()) / 45));
				}
			} else if (BG instanceof AnimatedGraphics) {
				((AnimatedGraphics) BG).setAnimating(false);
			}
		}
		counter++;
		if (counter == 30) {
			BA.attack(e, (BG instanceof AnimatedGraphics) ? ((AnimatedGraphics) BG).getDirection() : 0);
			counter = 0;
		}
	}

	public Vector2 ai() {
		PriorityQueue<Node> open = new PriorityQueue<Node>(); // queue for tiles
		HashSet<Node> closed = new HashSet<Node>(); // list of already viewed
		Node start = new Node(new Vector2((float) Math.floor(BG.getX() / Map.TILE_WIDTH),
				(float) Math.floor(BG.getY() / Map.TILE_WIDTH)), null);
		// makes a tile for the enemy position
		Node goal = new Node(
				new Vector2(
						(float) Math.floor(
								((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS))
										.getX() / Map.TILE_WIDTH),
				(float) Math.floor(
						((BaseGraphics) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.GRAPHICS)).getY()
								/ Map.TILE_WIDTH)),
				null); // makes a tile for the player 
		open.add(start);
		closed.add(start);
		while (open.size() > 0) {
			Node current = open.poll(); // Tile current being checked
			if (current.equals(goal)) { // if goal is reached
				Node node = current;
				while (true) {
					if(node.getParent() != null){
						if (node.getParent().equals(start)) {
							return node.getPosition();
						} else {
							node = node.getParent();
						}
					} else {
						return new Vector2(0, 0);
					}
				}
			}

			// add children
			Node N = new Node(new Vector2(current.getPosition().x(), current.getPosition().y() - 1), current);
			Node NW = new Node(new Vector2(current.getPosition().x() - 1, current.getPosition().y() - 1), current);
			Node W = new Node(new Vector2(current.getPosition().x() - 1, current.getPosition().y()), current);
			Node SW = new Node(new Vector2(current.getPosition().x() - 1, current.getPosition().y() + 1), current);
			Node SSW = new Node(new Vector2(current.getPosition().x() - 1, current.getPosition().y() + 2), current);
			Node S = new Node(new Vector2(current.getPosition().x(), current.getPosition().y() + 1), current);
			Node SS = new Node(new Vector2(current.getPosition().x(), current.getPosition().y() + 2), current);
			Node SSE = new Node(new Vector2(current.getPosition().x() + 1, current.getPosition().y() + 2), current);
			Node SSEE = new Node(new Vector2(current.getPosition().x() + 2, current.getPosition().y() + 2), current);
			Node SE = new Node(new Vector2(current.getPosition().x() + 1, current.getPosition().y() + 1), current);
			Node SEE = new Node(new Vector2(current.getPosition().x() + 2, current.getPosition().y() + 1), current);
			Node EE = new Node(new Vector2(current.getPosition().x() + 2, current.getPosition().y()), current);
			Node NEE = new Node(new Vector2(current.getPosition().x() + 2, current.getPosition().y() - 1), current);
			Node E = new Node(new Vector2(current.getPosition().x() + 1, current.getPosition().y()), current);
			Node NE = new Node(new Vector2(current.getPosition().x() + 1, current.getPosition().y() - 1), current);

			if (!closed.contains(N)) {
				if (map.getWall(N) && map.getWall(NE) ) {
						if (map.goalBounder.getTile(current.getPosition().x(), current.getPosition().y()).getNorth().boxContains(goal.getPosition())){
							open.add(N);
							closed.add(N);
						}
					}
				}
			if (!closed.contains(NW)) {
				if (map.getWall(NW)  && map.getWall(N) 
						&& map.getWall(W) 
						&& ((map.getWall(SW) || map.getWall(NE)) )) {
					if (map.goalBounder.getTile(current.getPosition().x(), current.getPosition().y()).getNorthWest().boxContains(goal.getPosition())){
						open.add(NW);
						closed.add(NW);
					}
				}
			}
			if (!closed.contains(W)) {
				if (map.getWall(W) && map.getWall(SW) ) {
					if (map.goalBounder.getTile(current.getPosition().x(), current.getPosition().y()).getWest().boxContains(goal.getPosition())){
						open.add(W);
						closed.add(W);
					}
				}
			}
			if (!closed.contains(SW)) {
				if (map.getWall(SW)  && map.getWall(SSW) 
						&& map.getWall(SS) 
						&& (map.getWall(W)  || map.getWall(SSE) )) {
					if (map.goalBounder.getTile(current.getPosition().x(), current.getPosition().y()).getSouthWest().boxContains(goal.getPosition())){
						open.add(SW);
						closed.add(SW);
					}
				}
			}
			if (!closed.contains(S)) {
				if (map.getWall(SS)  && map.getWall(SSE) ) {
					if (map.goalBounder.getTile(current.getPosition().x(), current.getPosition().y()).getSouth().boxContains(goal.getPosition())){
						open.add(S);
						closed.add(S);
					}
				}
			}
			if (!closed.contains(SE) ) {
				if (map.getWall(SSE)  && map.getWall(SSEE) 
						&& map.getWall(SEE) 
						&& (map.getWall(EE) || map.getWall(SS) )) {
					if (map.goalBounder.getTile(current.getPosition().x(), current.getPosition().y()).getSouthEast().boxContains(goal.getPosition())){
						open.add(SE);
						closed.add(SE);
					}
				}
			}
			if (!closed.contains(E) ) {
				if (map.getWall(EE)  && map.getWall(SEE) ) {
					if (map.goalBounder.getTile(current.getPosition().x(), current.getPosition().y()).getEast().boxContains(goal.getPosition())){
						open.add(E);
						closed.add(E);
					}
				}
			}
			if (!closed.contains(NE)) {
				if (map.getWall(NE)  && map.getWall(NEE) 
						&& map.getWall(EE) 
						&& (map.getWall(SEE) || map.getWall(N) )) {
					if (map.goalBounder.getTile(current.getPosition().x(), current.getPosition().y()).getNorthEast().boxContains(goal.getPosition())){
						open.add(NE);
						closed.add(NE);
					}
				}
			}
		}
		System.out.println("cannot find player");
		return new Vector2(0, 0);
	}

	@Override
	public void receive(Message m, Entity e) {

	}
}
