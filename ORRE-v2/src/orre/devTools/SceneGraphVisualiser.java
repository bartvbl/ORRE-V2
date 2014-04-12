package orre.devTools;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import orre.sceneGraph.SceneNode;

public class SceneGraphVisualiser {

	public static void showSceneGraph(SceneNode rootNode) {
		JFrame window = new JFrame("Scene");
		JTree sceneTree = new JTree();
		window.add(sceneTree);
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(rootNode);
		DefaultTreeModel model = new DefaultTreeModel(node);
		sceneTree.setModel(model);
		populateTree(node, rootNode);
		window.pack();
		window.setVisible(true);
	}

	private static void populateTree(DefaultMutableTreeNode node, SceneNode sceneNode) {
		for(SceneNode child : sceneNode.getChildren()) {
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
			node.add(childNode);
			populateTree(childNode, child);
		}
	}

}
