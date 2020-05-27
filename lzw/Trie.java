import java.util.ArrayList;

public class Trie {
	
	// create root of the trie
	private Node root; 
	
	public Node getRoot(){
		return root;
	}

	public Trie() {
		// null character in the root
		root = new Node(Character.MIN_VALUE); 
	}        
	
	// possible outcomes of a search
	private enum Outcomes {PRESENT, ABSENT, UNKNOWN}
	
	/** search trie for word w Legacy code*/
	public boolean search(String w) {
		Node node = searchNode(w, root.getChild(),0);
		if (node == null) {
			return false;
		}
		return node.getIsWord();
	}

	/** search trie for word w starting from current node*/
	public Node searchNode(String w, Node node, int currentIndex){
		// initially outcome unknown
		Outcomes outcome = Outcomes.UNKNOWN;

		// position in word so far searched (start at beginning)
		int i = currentIndex;

		// start with first child of the root
		Node current = node.getChild();

		// start search
		while (outcome == Outcomes.UNKNOWN) {

			if (current == null)
				outcome = Outcomes.ABSENT; // dead-end
			else if (current.getLetter() == w.charAt(i)) { // positions match
				if (i == w.length() - 1) {
					outcome = Outcomes.PRESENT; // matched word
				} else { // descend one level...
					current = current.getChild(); // in trie
					i++; // in word being searched
				}
			} else { // positions not matched so try next sibling
				current = current.getSibling();
			}
		}
		// return answer
		if (outcome != Outcomes.PRESENT)
			return null;
		else
			return current;

	}

	public void insertAt(String w, Node node, int currentIndex){
		Node current = node;
		Node next = current.getChild(); // first child of current node we are testing
		
		while (currentIndex < w.length()) { // not reached end of word
			if (next != null && next.getLetter() == w.charAt(currentIndex)) { // chars match: decend a level
				current = next; // update node to the child node
				next = current.getChild(); // update child node
				currentIndex++; // next position in the word
			} else if (next != null) { // try next sibling
				next = next.getSibling();
			} else { // no more siblings: need new node
				Node x = new Node(w.charAt(currentIndex)); // label with ith element of the word
				x.setSibling(current.getChild()); // sibling: first child of current node
				current.setChild(x); // make first child of current node
				current = x; // move to the new node
				next = current.getChild(); // update child node
				currentIndex++; // next position in word
			}
		}
		current.setIsWord(true); // current represents word w

	}
	/** insert word w into trie */
	public void insert(String w){
		insertAt(w, root, 0);
	}	
}
