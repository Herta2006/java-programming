import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Node class that defines general Node method implementations.
 * Provides fundamental methods for tree nodes. The notable exception 
 * of methods for addition and deletion of nodes, which are not publicly visible.
 * @author Max Fisher
 *
 * @param <E> the type of object to store in this Node
 */
public abstract class AbstractNode<E> implements TreeNode<E> {
	/**
	 * The element stored by this Node
	 */
	private E element;

	/**
	 * Default constructor that does not set element.
	 */
	public AbstractNode() {
		this(null);
	}

	/**
	 * Constructor that initializes element
	 * @param element the Object to store in this Node
	 */
	public AbstractNode(E element) {
		setElement(element);
	}
	
	/**
	 * Checks whether the specified tree contains this Node.
	 * A null containing Tree signifies no containing Tree
	 * @param t the tree to check for presence of this Node
	 * @return True if the given tree contains this Node, otherwise false
	 */
	@Override
	public abstract boolean containedBy(Tree<E> t);
	
	/**
	 * Returns a list of this Node's children in the order in which they were added.
	 * The collection is guaranteed to be random-access.
	 * @return a list of this Node's children in the order in which they were added.
	 */
	public abstract List<TreeNode<E>> getChildren();
	
	@Override
	public List<TreeNode<E>> getDescendants() {
		return preOrderTraversal();
	}
	
	/**
	 * Returns the data stored by this Node
	 * @return the data stored by this Node
	 */
	@Override
	public E getElement() {
		return element;
	}

	@Override
	public abstract TreeNode<E> getParent();

	@Override
	public boolean isAncestorOf(TreeNode<E> v) {
		while (v != null) { // could be !v.isRoot()
			if (this.equals(v))
				return true;
			v = v.getParent();
		}
		return false;
	}
	
	@Override
	public boolean isExternal() {
		return getChildren().isEmpty();
	}

	@Override
	public boolean isInternal() {
		return !getChildren().isEmpty();
	}
	
	/**
	 * Performs a post-order traversal of this node and its descendants.
	 * @return a List containing this node and its descendants,
	 * in post-order traversal order.
	 */
	public List<TreeNode<E>> postOrderTraversal() {
		List<TreeNode<E>> postNodes = new ArrayList<>();
		for (TreeNode<E> child : getChildren())
			postNodes.addAll(child.postOrderTraversal());
		postNodes.add(this);
		return postNodes;
	}
	
	/**
	 * Performs a pre-order traversal of this node and its descendants.
	 * @return a List containing this node and its descendants,
	 * in pre-order traversal order.
	 */
	public List<TreeNode<E>> preOrderTraversal() {
		List<TreeNode<E>> preNodes = new ArrayList<>();
		preNodes.add(this);
		for (TreeNode<E> child : this.getChildren())
			preNodes.addAll(child.preOrderTraversal());
		return preNodes;
	}
	
	/**
	 * Sets the data stored by this Node to the given element, 
	 * and returns the Object previously stored by this Node.
	 * @param element the new Object to store in this Node.
	 * @return the Object previously stored by this Node.
	 */
	@Override
	public E setElement(E element) {
		E oldElement = this.element;
		this.element = element;
		return oldElement;
	}
	
	@Override
	public String toString() {
		return element.toString();
	}
	
	/**
	 * Adds the given node as a child of this Node.
	 * Subclasses may choose not to allow null or duplicate nodes, and should
	 * take appropriate action to prevent this if so.
	 * @param v the new child Node of this Node.
	 */
	protected abstract void addChild(TreeNode<E> child);
	
	/**
	 * Removes the given node as a child of this Node, if it was previously a
	 * child of this Node.
	 * 
	 * @param child the child Node to separate from this Node.
	 * @return True if the specified Node was found and removed, otherwise
	 *         false.
	 */
	protected abstract boolean removeChild(TreeNode<E> child);

	/**
	 * Unlinks this Node, from its parent, if it has one. If detached nodes are
	 * removed from the Tree they were originally contained in, then this method
	 * should also detach all the descendant nodes of this node from that Tree.
	 */
	protected abstract void removeFromParent();

	/**
	 * Sets this Node's parent to the given Node. This method only should be
	 * called internally by addChild methods, as they are in the public API.
	 * Therefore, no checking of types should necessary even for Node classes
	 * which do not allow null or duplicate nodes.
	 * @param parent the new parent Node for this Node.
	 */
	protected abstract void setParent(TreeNode<E> parent);
}
