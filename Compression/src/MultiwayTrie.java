import java.util.HashMap;

public class MultiwayTrie {

    private HashMap<Byte, TrieNode> parent;
    private TrieNode child;
    private int maxSize;
    private int dictionarySize;
    public boolean Full;
    public boolean Top;

    public MultiwayTrie(int ms){
        parent = new HashMap<Byte, TrieNode>(1000); //instead of default 16
        maxSize = (1 << ms) - 1;
        dictionarySize = 0;
        Full = false;
        Top = true;
    }

    public void add(Byte node, int position){
        if(Top){
            parent.put(node, new TrieNode(node,position));
            dictionarySize++;
        }else if(!Full){
            child.addNode(node,position);
            if(dictionarySize+1 >= maxSize){
                dictionarySize++;
                Full = true;
            }
        }
    }

    public TrieNode getNode (Byte node){
        try {
            if (Top) {
                if (parent.containsKey(node)) {
                    Top = false;
                    child = parent.get(node);
                    return child;
                }
            } else {
                TrieNode trieNode = child.getNode(node);
                if (trieNode != null) {
                    child = trieNode;
                    return child;
                }
            }
            return null;
        }catch (NullPointerException e){
            return null;
        }
    }

    public int getPosition(){
        if(Top)return 0;
        else return child.position;
    }

    public void setTop(){
        Top = true;
    }
}
