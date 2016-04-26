package hillbillies.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pathfinding {
	
	 /** Returns the most efficient path between start and goal 
	 * 	according the A* algorithm.
	 * 
	 * @param start
	 * 		The cube the path starts from.
	 * 
	 * @param goal
	 * 		The cube to be reached
	 * 
	 * @return
	 * 		A list of cubes to follow if goal can be reached.
	 * 		Null if the goal can't be reached.
	 */
	public static List<Cube> getPath(Cube start, Cube goal, World world){
		// The set of nodes already evaluated.
		Set<Cube> closedSet = new HashSet<>();
		
		// The set of currently discovered nodes still to be evaluated.
		// Initially, only the start node is known.
		Set<Cube> openSet = new HashSet<>();
		openSet.add(start);
		
		// For each node, which node it can most efficiently be reached from.
		// If a node can be reached from many nodes, cameFrom will eventually contain the
		// most efficient previous step.
		HashMap<Cube, Cube> cameFrom=new HashMap<>();
		
		// For each node, the cost of getting from the start node to that node.
		HashMap<Cube, Double> gScore=new HashMap<>();
				
		// For each node, the total cost of getting from the start node to the goal
		// by passing by that node. That value is partly known, partly heuristic.
		HashMap<Cube, Double> fScore=new HashMap<Cube, Double>();
		
		// The cost of going from start to start is zero.
		gScore.put(start,  new Double(0));
	   		
		// For the first node, that value is completely heuristic.
		fScore.put(start, Pathfinding.heuristic_cost_estimate(start, goal));
		
		//while openSet is not empty
		while (openSet.size() != 0){
			//current := the node in openSet having the lowest fScore[] value
			Cube currentNode = null;
			Double lowestF = null;
			for (Cube node : openSet){
				if (lowestF == null){
					currentNode = node;
					lowestF = fScore.get(node);
				} else if (lowestF > fScore.get(node)){
					currentNode = node;
					lowestF = fScore.get(node);
				}
			}
			if (currentNode.equals(goal)){
				return Pathfinding.reconstruct_path(cameFrom, goal);
			}
			openSet.remove(currentNode);
			closedSet.add(currentNode);
			Set<Cube> accessibleNeigbours =	new HashSet<>(world.getAccessibleNeigbours(currentNode));
			for (Cube neighbour : accessibleNeigbours){
				if (closedSet.contains(neighbour)){
	                continue;		// Ignore the neighbor which is already evaluated.
				}
	            // The distance from start to a neighbor
				double tentative_gScore = gScore.get(currentNode) + Vector.distanceBetween(currentNode, neighbour);
				if (!openSet.contains(neighbour)){
					openSet.add(neighbour);	// Discoverd a new node.
				}else if (tentative_gScore >= gScore.get(neighbour)){  
	                continue;		// This is not a better path.
				}
	            // This path is the best until now. Record it!
				cameFrom.put(neighbour, currentNode);
				gScore.put(neighbour, tentative_gScore);
				fScore.put(neighbour, tentative_gScore+Pathfinding.heuristic_cost_estimate(neighbour, goal));
			}
		}
		System.out.println("Impossible to get there!");
		return null;
	}	
	
	/**
	 * Returns the estimated 'heuristic cost' to get form start to goal.
	 * @param start
	 * 		The cube the path starts on
	 * @param goal
	 * 		The cube to get to
	 * @return
	 * 		The distance between start and goal.
	 */
	private static double heuristic_cost_estimate(Cube start, Cube goal){
		return Vector.distanceBetween(start.getCenterOfCube(), goal.getCenterOfCube());
	}
	
	/**
	 * Returns the reconstructed path
	 * 
	 * @param cameFrom
	 * 		//TODO
	 * @param current
	 * @return
	 */
	private static List<Cube> reconstruct_path(HashMap<Cube, Cube> cameFrom, Cube current){
		List<Cube> total_path = new ArrayList<>();
	    total_path.add(current);
	    while (cameFrom.containsKey(current)){
	    	current = cameFrom.get(current);
	    	total_path.add(current);
	    }
	    return total_path;
	}

}
