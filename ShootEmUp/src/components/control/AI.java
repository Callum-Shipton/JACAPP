package components.control;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import main.ShootEmUp;
import math.Vector2;
import math.Vector4;

public class AI {
	public static Tile[][] aiTiles;
	
	public static void SetAiTiles(){
		aiTiles = new Tile[ShootEmUp.currentLevel.map.getWidth()][ShootEmUp.currentLevel.map.getHeight()];
		
		for(int x = 0; x < aiTiles.length; x++){
			System.out.println(x);
			for(int y = 0; y < aiTiles[0].length; y++){
				System.out.print(y + " ");
				
				Queue<TypeNode> open = new LinkedList<TypeNode>(); // queue for tiles
				// to be looked
				// at
				HashSet<TypeNode> closed = new HashSet<TypeNode>(); // list of already viewed
				// tiles
				TypeNode start = new TypeNode(new Vector2(x, y), 0);
				
				TypeNode N = new TypeNode(new Vector2(start.getPosition().x(), start.getPosition().y() - 1), 0);
				TypeNode NW = new TypeNode(new Vector2(start.getPosition().x() - 1, start.getPosition().y() - 1), 1);
				TypeNode W = new TypeNode(new Vector2(start.getPosition().x() - 1, start.getPosition().y()), 2);
				TypeNode SW = new TypeNode(new Vector2(start.getPosition().x() - 1, start.getPosition().y() + 1), 3);
				TypeNode S = new TypeNode(new Vector2(start.getPosition().x(), start.getPosition().y() + 1), 4);
				TypeNode SE = new TypeNode(new Vector2(start.getPosition().x() + 1, start.getPosition().y() + 1), 5);
				TypeNode E = new TypeNode(new Vector2(start.getPosition().x() + 1, start.getPosition().y()), 6);
				TypeNode NE = new TypeNode(new Vector2(start.getPosition().x() + 1, start.getPosition().y() - 1), 7);
				
				Vector4[] Boxes = new Vector4[8];
				Boxes[0] = new Vector4(N.getPosition().x(),N.getPosition().y(),0,0);
				Boxes[1] = new Vector4(NW.getPosition().x(),NW.getPosition().y(),0,0);
				Boxes[2] = new Vector4(W.getPosition().x(),W.getPosition().y(),0,0);
				Boxes[3] = new Vector4(SW.getPosition().x(),SW.getPosition().y(),0,0);
				Boxes[4] = new Vector4(S.getPosition().x(),S.getPosition().y(),0,0);
				Boxes[5] = new Vector4(SE.getPosition().x(),SE.getPosition().y(),0,0);
				Boxes[6] = new Vector4(E.getPosition().x(),E.getPosition().y(),0,0);
				Boxes[7] = new Vector4(NE.getPosition().x(),NE.getPosition().y(),0,0);
				
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
					System.out.println(open.size());
					TypeNode current = open.poll(); // Tile current being checked
					
					if(current.getPosition().x() < Boxes[current.getType()].x()){
						Boxes[current.getType()].x(current.getPosition().x());
					}
					if(current.getPosition().y() < Boxes[current.getType()].y()){
						Boxes[current.getType()].y(current.getPosition().y());
					}
					if(current.getPosition().x() > Boxes[current.getType()].x() + Boxes[current.getType()].z()){
						Boxes[current.getType()].z(current.getPosition().x()-Boxes[current.getType()].x());
					}
					if(current.getPosition().y() > Boxes[current.getType()].y() + Boxes[current.getType()].w()){
						Boxes[current.getType()].w(current.getPosition().y()-Boxes[current.getType()].y());
					}
						
					TypeNode[] nodes = new TypeNode[8];
					nodes[0] = new TypeNode(new Vector2(current.getPosition().x(), current.getPosition().y() - 1), current.getType());
					nodes[1] = new TypeNode(new Vector2(current.getPosition().x() - 1, current.getPosition().y() - 1), current.getType());
					nodes[2] = new TypeNode(new Vector2(current.getPosition().x() - 1, current.getPosition().y()), current.getType());
					nodes[3] = new TypeNode(new Vector2(current.getPosition().x() - 1, current.getPosition().y() + 1), current.getType());
					nodes[4] = new TypeNode(new Vector2(current.getPosition().x(), current.getPosition().y() + 1), current.getType());
					nodes[5] = new TypeNode(new Vector2(current.getPosition().x() + 1, current.getPosition().y() + 1), current.getType());
					nodes[6] = new TypeNode(new Vector2(current.getPosition().x() + 1, current.getPosition().y()), current.getType());
					nodes[7] = new TypeNode(new Vector2(current.getPosition().x() + 1, current.getPosition().y() - 1), current.getType());
					
					for(int i = 0; i < nodes.length; i++){
						if (!closed.contains(nodes[i])) {
							if (ShootEmUp.currentLevel.map.getWall(nodes[i]) ) {
									open.add(nodes[i]);
									closed.add(nodes[i]);
							}
						}
					}
				}			
				
				aiTiles[x][y] = new Tile();
				aiTiles[x][y].setNorth(Boxes[0]);
				aiTiles[x][y].setNorth(Boxes[1]);
				aiTiles[x][y].setNorth(Boxes[2]);
				aiTiles[x][y].setNorth(Boxes[3]);
				aiTiles[x][y].setNorth(Boxes[4]);
				aiTiles[x][y].setNorth(Boxes[5]);
				aiTiles[x][y].setNorth(Boxes[6]);
				aiTiles[x][y].setNorth(Boxes[7]);
			}
		}
	}

	public static Tile getTile(float x, float y) {
		return aiTiles[(int)x][(int)y];
	}	
	
	public static boolean boxContains(Vector4 box, Vector2 goal){
		if(((goal.x() > box.x()) && (goal.x() < box.x()+box.z())) && ((goal.y() > box.y()) && (goal.y() < box.y()+box.w()))){
			return true;
		}
		return false;
	}
}
