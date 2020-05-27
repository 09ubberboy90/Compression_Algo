public class Node implements Comparable<Node>{

    private int data;
    private char c;

    public Node(char c, int frequency)
    {
        this.data = frequency;
        this.c = c;
    }
    
    public int GetCount() {
        return this.data;
    }

    @Override
    public int compareTo(Node node) {
        return this.data - node.GetCount();
    }

}