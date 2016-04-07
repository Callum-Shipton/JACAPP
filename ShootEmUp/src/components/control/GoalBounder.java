package components.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import math.Vector2;
import math.Vector4;
import object.Entity;

public class GoalBounder {
	private Tile[][] aiTiles;
	
	public GoalBounder(int width, int height, HashMap<Vector2, Entity> walls){
		aiTiles = new Tile[width][height];
		SetAiTiles(walls);
	}
	
	private void SetAiTiles(HashMap<Vector2, Entity> walls){
		for(int x = 2; x < aiTiles.length - 2; x++){
			System.out.println(x);
			for(int y = 2; y < aiTiles[0].length - 2; y++){
				
				Queue<TypeNode> open = new LinkedList<TypeNode>(); // queue for tiles
				// to be looked
				// at
				HashSet<TypeNode> closed = new HashSet<TypeNode>(); // list of already viewed
				// tiles
				TypeNode start = new TypeNode(new Vector2(x, y), 0);
				
				float startX = start.getPosition().x();
				float startY = start.getPosition().y();
				
				TypeNode N = new TypeNode(new Vector2(startX, startY - 1), 0);
				TypeNode NW = new TypeNode(new Vector2(startX - 1, startY - 1), 1);
				TypeNode W = new TypeNode(new Vector2(startX - 1, startY), 2);
				TypeNode SW = new TypeNode(new Vector2(startX - 1, startY + 1), 3);
				TypeNode S = new TypeNode(new Vector2(startX, startY + 1), 4);
				TypeNode SE = new TypeNode(new Vector2(startX + 1, startY + 1), 5);
				TypeNode E = new TypeNode(new Vector2(startX + 1, startY), 6);
				TypeNode NE = new TypeNode(new Vector2(startX + 1, startY - 1), 7);
				
				BoundingBox[] Boxes = new BoundingBox[8];
				Boxes[0] = new BoundingBox(new Vector4(N.getPosition().x(),N.getPosition().y(),0,0));
				Boxes[1] = new BoundingBox(new Vector4(NW.getPosition().x(),NW.getPosition().y(),0,0));
				Boxes[2] = new BoundingBox(new Vector4(W.getPosition().x(),W.getPosition().y(),0,0));
				Boxes[3] = new BoundingBox(new Vector4(SW.getPosition().x(),SW.getPosition().y(),0,0));
				Boxes[4] = new BoundingBox(new Vector4(S.getPosition().x(),S.getPosition().y(),0,0));
				Boxes[5] = new BoundingBox(new Vector4(SE.getPosition().x(),SE.getPosition().y(),0,0));
				Boxes[6] = new BoundingBox(new Vector4(E.getPosition().x(),E.getPosition().y(),0,0));
				Boxes[7] = new BoundingBox(new Vector4(NE.getPosition().x(),NE.getPosition().y(),0,0));
				
				open.add(N);
				open.add(NW);
				open.add(W);
				open.add(SW);
				open.add(S);
				open.add(SE);
				open.add(E);
				open.add(NE);

				closed.add(N);
				closed.add(NW);
				closed.add(W);
				closed.add(SW);
				closed.add(S);
				closed.add(SE);
				closed.add(E);
				closed.add(NE);
				
				while (open.size() > 0) {
					TypeNode current = open.poll(); // Tile current being checked
					
					int currentType = current.getType();
					float currentX = current.getPosition().x();
					float currentY = current.getPosition().y();
					
					Boxes[currentType].addPoint(current.getPosition());
					
					TypeNode[] nodes = new TypeNode[8];
					nodes[0] = new TypeNode(new Vector2(currentX, currentY - 1), currentType);
					nodes[1] = new TypeNode(new Vector2(currentX - 1, currentY - 1), currentType);
					nodes[2] = new TypeNode(new Vector2(currentX - 1, currentY), currentType);
					nodes[3] = new TypeNode(new Vector2(currentX - 1, currentY + 1), currentType);
					nodes[4] = new TypeNode(new Vector2(currentX, currentY + 1), currentType);
					nodes[5] = new TypeNode(new Vector2(currentX + 1, currentY + 1), currentType);
					nodes[6] = new TypeNode(new Vector2(currentX + 1, currentY), currentType);
					nodes[7] = new TypeNode(new Vector2(currentX + 1, currentY - 1), currentType);
					
					for(int i = 0; i < nodes.length; i++){
						if (!closed.contains(nodes[i])) {
							if (!walls.containsKey(nodes[i].position)){
									open.add(nodes[i]);
									closed.add(nodes[i]);
							}
						}
					}
				}			
				aiTiles[x][y] = new Tile();
				aiTiles[x][y].setBoxes(Boxes);
			}
		}
	}

	public Tile getTile(float x, float y) {
		return aiTiles[(int)x][(int)y];
	}	
}
