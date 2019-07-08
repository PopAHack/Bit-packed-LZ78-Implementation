import java.util.ArrayList;

public class DictionaryNode {
    public int Index;
    public int PhraseNumber;
    public byte value;
    //public TreeMap<Byte, DictionaryNode> children;
    public ArrayList<DictionaryNode> children;

    public DictionaryNode(int index, int phraseNumber, byte value){
        this.Index = index;
        this.PhraseNumber = phraseNumber;
        this.value = value;
        children = new ArrayList<DictionaryNode>();
    }

    public DictionaryNode(byte value){
        this.value = value;
    }

    public void addNode(int index, int phraseNumber, byte value){
        children.add(new DictionaryNode(index, phraseNumber, value));
    }

    public DictionaryNode getNode(DictionaryNode node){
        for(DictionaryNode d : children){
            if(d.value == node.value){
                return d;
            }
        }
        return null;
    }

    /*public DictionaryNode getNode(int index){
        if(children.contains(children.get(index))){
            return children.get(index);
        }else{
            return null;
        }
    }*/

    public int getIndex() {
        return Index;
    }

    public int getPhraseNumber() {
        return PhraseNumber;
    }

    public byte getValue() {
        return value;
    }
}
