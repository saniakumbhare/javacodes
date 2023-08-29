class BinarySearchTree extends BinaryTree {
    public BinarySearchTree() {
        super();
    }

    public BinarySearchTree(int... values) {
        for (int value : values) {
            insert(value);
        }
    }

    @Override
    public void insert(int value) {
        setRoot(insert(getRoot(), value));
    }

    private TreeNode insert(TreeNode node, int value) {
        if (node == null) {
            return new TreeNode(value);
        }

        if (value < node.getValue()) {
            node.setLeft(insert(node.getLeft(), value));
        } else if (value > node.getValue()) {
            node.setRight(insert(node.getRight(), value));
        }

        return node;
    }
}