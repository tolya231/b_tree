/**
 * Created by Anatoly Shelikhovsky
 * Date: 15.10.2016
 * Time: 20:30
 * b_tree
 */
public class Node {
    private final int[] keys; //keys in increasing order
    private final Node[] children;
    private boolean leaf;
    private final String indent = "    ";
    public Node parent;

    public Node(int t) {
        keys = new int[2 * t - 1];
        children = new Node[2 * t];
        leaf = true;
        parent = null;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getLength() {
        int sum = 0;
        for (int key : keys) if (key != 0) sum++;
        return sum;
    }

    public Node[] getChildren() {
        return children;
    }

    public int[] getKeys() {
        return keys;
    }

    public int getKey(int i) {
        return keys[i];
    }

    public void addKey(int i, int key) {
        keys[i] = key;
    }

    public Node getChildren(int i) {
        return isLeaf() ? null : children[i];
    }

    private String count(int i) {
        String res = "";
        for(int j = 1; j < i; j++) res = res + indent;
        return res;
    }
    public String toString(Node node, int itr) {
        StringBuilder res = new StringBuilder();
        if (node.isLeaf()) {
            for (int i = 0; i < node.getLength(); i++) {
                res.append("[").append(node.getKey(i)).append("] ");
            }
            //res.append("<" + itr + ">");
            res.append("\n");
        } else {
            for (int i = 0; i < node.getLength() + 1; i++) {
                if(i == 0) res.append(indent);
                res.append(count(itr - 1)).append(toString(node.getChildren(i), ++itr));
                itr--;
                if (i < node.getLength()) {
                    for(int j = 1; j < itr; j++) res.append(indent);
                    res.append(node.getKey(i)).append("\n");
                    for(int j = 0; j < itr; j++) res.append(indent);
                    //res.append(indent);
                    //res.append("<" + itr + ">");
                }
            }
        }
        return res.toString();
    }

}
