import java.util.ArrayList;

/**
 * Created by Anatoly Shelikhovsky
 * Date: 15.10.2016
 * Time: 20:30
 * b_tree
 */
class B_tree {
    private final int MINIMUM_PARAM_T = 2;
    private final int KEY_SIZE;
    private Node root;
    private final int t;
    private int index = 0;

    private class MyException extends Exception {
        MyException(String s) {
            super(s);
        }
    }

    public B_tree(int t) throws Exception { //t = minimum number of keys in non-root node
        if (t < MINIMUM_PARAM_T) throw new MyException("Error");
        else {
            this.t = t;
            root = new Node(t);
            KEY_SIZE = 2 * t - 1;
        }
    }

    private ArrayList<Integer> getAll(Node node, ArrayList<Integer> list, int notToAdd) {
        for (int i = 0; i < node.getLength() + 1; i++) {
            if (i != node.getLength() && node.getKey(i) != notToAdd) list.add(node.getKey(i));
            if (!node.isLeaf()) list = getAll(node.getChildren(i), list, notToAdd);
        }
        return list;
    }


    public void insert(int key) {
        Node temp = root;
        if (temp.getLength() == KEY_SIZE) {
            Node newRoot = new Node(t);
            root = newRoot;
            newRoot.setLeaf(false);
            newRoot.getChildren()[0] = temp;
            split(newRoot, 1);
            newRoot.getChildren()[0].parent = root;
            newRoot.getChildren()[1].parent = root;
            insertNotFull(newRoot, key);
        } else insertNotFull(temp, key);
    }

    private void insertNotFull(Node node, int key) {
        int i = node.getLength() - 1;
        Node parent = node;
        if (node.isLeaf()) {
            while (i >= 0 && key < node.getKey(i)) {
                node.getKeys()[i + 1] = node.getKey(i);
                i--;
            }
            node.getKeys()[i + 1] = key;
        } else {
            while (i > -1 && key < node.getKey(i)) {
                i--;
            }
            i++;
            if (node.getChildren()[i].getLength() == KEY_SIZE) {
                split(node, i + 1);
                if (key > node.getKey(i)) {
                    i++;
                }
            }
            for (int j = 0; j <= node.getLength(); j++) node.getChildren()[j].parent = parent;
            insertNotFull(node.getChildren(i), key);
        }
    }

    private void delete(int key) throws Exception {
        B_tree bTree = new B_tree(t);
        ArrayList<Integer> list = new ArrayList<Integer>();
        list = getAll(root, list, key);
        for (int keys : list) bTree.insert(keys);
        this.root = bTree.root;
    }


    /**
     * Split child[index] of node
     *
     * @param node  node which child[index] will be splitted
     * @param index child to be splitted
     */
    private void split(Node node, int index) {
        Node right = new Node(t);
        Node left = node.getChildren(index - 1);
        right.setLeaf(left.isLeaf());
        for (int i = 0; i < t - 1; i++) {
            right.addKey(i, left.getKey(i + t));
            left.getKeys()[i + t] = 0;
        }
        if (!left.isLeaf()) {
            for (int i = 0; i < t; i++) {
                right.getChildren()[i] = left.getChildren(i + t);
                left.getChildren()[i + t] = null;
            }
        }
        for (int i = node.getLength(); i > index - 1; i--) {
            node.getChildren()[i + 1] = node.getChildren(i);
        }
        node.getChildren()[index] = right;

        for (int i = node.getLength() - 1; i > index - 2; i--) {
            node.getKeys()[i + 1] = node.getKey(i);
        }

        node.getKeys()[index - 1] = left.getKey(t - 1);
        left.getKeys()[t - 1] = 0;
    }

    @Override
    public String toString() {
        return ("This is my B-Tree\n" + root.toString(root, 1));
    }

    public static void main(String[] args) {
        /*try {
            B_tree tree1 = new B_tree(3);
            for (int i = 1; i <= 4; i++) tree1.insert(i);
            tree1.insert(27);
            tree1.insert(31);
            tree1.insert(96);
            tree1.insert(97);
            tree1.insert(99);
            tree1.insert(7);
            tree1.insert(9);
            tree1.insert(11);
            tree1.insert(13);
            tree1.insert(16);
            tree1.insert(17);
            tree1.insert(19);
            tree1.insert(26);
            B_tree tree2 = new B_tree(2);
            for (int i = 1; i <= 10; i++) tree2.insert(10 * i);
            for (int i = 1; i <= 10; i++) tree2.insert(11 * i);
            tree2.delete(111);
            tree2.delete(40);
            tree1.delete(11);
            tree1.delete(4);
            tree1.delete(2);
            tree1.delete(31);
            System.out.println(tree2.toString());
        } catch (Exception e) {
            System.out.println("Read more about B-Tree and then try again");
        }*/
    }
}
