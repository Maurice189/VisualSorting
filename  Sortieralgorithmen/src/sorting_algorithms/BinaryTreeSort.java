package sorting_algorithms;

import main.SortVisualtionPanel;

/*
 * BST class have only 1 constructor, which takes as parameter an Object type.
 * Root element will be the first element in the list of elements to sort.
 */

// TODO implementiere Grafikausgabe etc.
public class BinaryTreeSort extends Sort {

	private Node root;

	public BinaryTreeSort() { // Only Constructor//

		super();
		root = new Node(new Element(elements[0],0));

	}

	public BinaryTreeSort(SortVisualtionPanel svp) {
		super(svp);
		root = new Node(new Element(elements[0],0));

	}

	/*
	 * ( Recursive Approach ) Function insert() will add the new element in tree
	 * corresponding to the root, at each level it will check whether the
	 * element to be added is smaller (move to left-subtree) or greater (move to
	 * right-subtree); and accordingly position decided.
	 */
	public Node insert(Node node, Element e) {
		if (node == null) {
			return node = new Node(e);
		}
		if (e.value < node.element.value) {
			
		
			node.left = insert(node.left, e);
			//svp.drawElements(node.element.index,e.index, true);
			
			//System.out.println("TAUSCHE: " + x + " mit " + node.element);
			try {
				Thread.sleep(Sort.delayMs,Sort.delayNs);
			} catch (InterruptedException ie) {
				// TODO Auto-generated catch block
				ie.printStackTrace();

			}
		} else {
			
			
			node.right = insert(node.right, e);
			//svp.drawElements(node.element.index,e.index, true);
			
			try {
				Thread.sleep(Sort.delayMs,Sort.delayNs);
			} catch (InterruptedException ie) {
				// TODO Auto-generated catch block
				ie.printStackTrace();

			}

		}

		return node;
	}

	public void run() {

		for (int i = 0; i < elements.length; i++)
			insert(root,new Element(elements[i],i));

	}

	/*
	 * Basic node stored in a Tree.
	 */
	
	class Element{
		
		public int value,index;
		
		public Element(int value, int index){
			this.value = value;
			this.index = index;
		}
		
	}
	
	class Node {
		
		public Element element;
		public Node left;
		public Node right;
		public int index;

		// Constructors
		public Node(Element e) {
			this(e, null, null);
		}
		
		public Node(Element e,int index) {
			this(e, null, null);
			this.index = index;
		}

		public Node(Element e, Node lLink, Node rLink) {
			this.element = e;
			this.left = lLink;
			this.right = rLink;
		}
	}

}