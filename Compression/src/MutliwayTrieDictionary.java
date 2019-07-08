import java.util.ArrayList;

public class MutliwayTrieDictionary {
    public ArrayList<DictionaryNode> parents;
    public DictionaryNode childNode;

    public MutliwayTrieDictionary(int maxSize) {
        parents = new ArrayList(maxSize);
    }

    public void add(int index, int phraseNumber, byte node) {
        if(phraseNumber == 0){
            parents.add(phraseNumber, new DictionaryNode(index, phraseNumber, node));
        }/*else{
            for(DictionaryNode n : parents){
                DictionaryNode _node = getNode(n, phraseNumber);
                _node.children.add(new DictionaryNode(index, phraseNumber, node));
            }
        }*/
    }

    public void add(int index, int phraseNumber, byte value, DictionaryNode lastnode){
        lastnode.children.add(new DictionaryNode(index, phraseNumber, value));
    }

    public DictionaryNode getParentNode(byte b){
        for(DictionaryNode d : parents){
            if(d.value == b){
                return d;
            }
        }
        return null;
    }

    /*public DictionaryNode getNode(DictionaryNode n, int phraseNumber){
        if(n.children.contains(n.children.get(phraseNumber))){
            return n.children.get(phraseNumber);
        }else{
            return n.getNode(phraseNumber);
        }
    }*/

}
