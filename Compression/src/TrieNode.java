import java.util.TreeMap;

public class TrieNode {

    public TreeMap<Byte, TrieNode> children;
    public byte node;
    public int position;

    public TrieNode(byte node, int position){
        children = new TreeMap<Byte, TrieNode>();
        node = node;
        position = position;
    }

    public void addNode(Byte node, int position){

        children.put(node, new TrieNode(node, position));
    }

    public TrieNode getNode(Byte node){
        if(children.containsKey(node)){
            return children.get(node);
        }else{
            return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(node);
    }
}
