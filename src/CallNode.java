/*
	CallTreeBrowser. A simple tool to browse call trees.
	Copyright (C) 2016  Mikael Ågren

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

public class CallNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = -3773943663894403989L;

	public CallNode(String name) {
		super(name);
	}
	
	/**
	 * Create a CallNode for a caller and all its callees.
	 * @param caller                  Name of the function that calls. Should also be a key in callMap.
	 * @param callMap                 Map of caller -> lists of callees.
	 * @param alreadyExpandedCallers  List of functions (in the current branch) that already have been expanded. Used to avoid infinite call loops.
	 * @return                        The newly created callnode with its callees and their callees and their callees and...
	 */
	private static CallNode buildNode(String caller, HashMap<String, ArrayList<String>> callMap, ArrayList<String> alreadyExpandedCallers) {
		CallNode callerNode = new CallNode(caller);
		alreadyExpandedCallers.add(caller);
		ArrayList<String> calleeList = callMap.get(caller);
		if (calleeList != null) {
			for (String callee : calleeList) {
				if (alreadyExpandedCallers.contains(callee)) {
					// Avoid infinite loops.
					callerNode.add(new CallNode(callee + " *"));
				}
				else {
					callerNode.add(buildNode(callee, callMap, alreadyExpandedCallers));
				}
			}
		}
		alreadyExpandedCallers.remove(caller);
		return callerNode;
	}
	
	public static CallNode buildTree(HashMap<String, ArrayList<String>> callMap) {
		CallNode tree = new CallNode("Functions");
		for (String caller : callMap.keySet()) {
			tree.add(buildNode(caller, callMap, new ArrayList<String>()));
		}
		return tree;
	}
}
