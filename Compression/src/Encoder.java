import java.io.*;
import java.util.ArrayList;

public class Encoder {

    MutliwayTrieDictionary dictionary;
    FileInputStream fis;
    File OutputFile;
    ArrayList<DictionaryNode> prevNodes;
    double percentageRead;

    BufferedWriter bw;

    int linesRead = 0;

    public Encoder(){}

    public void encodeFile(File inputFile){
        fis = null;

        CreateOutputFile();

        byte[] fileContents = readinFile(inputFile);

        if(fileContents != null){

            setMaxSize(fileContents);
            encode(fileContents);

        }else
            System.out.println("Error with file");
    }

    public void setMaxSize(byte[] b){
        dictionary = new MutliwayTrieDictionary(b.length);
    }

    public void encode(byte[] file){

        try {
            bw = new BufferedWriter(new FileWriter(OutputFile, true));
        }catch (Exception e){
            e.printStackTrace();
        }
        int phraseNumber = 0;
        DictionaryNode node = null;

        int index = 1;
        int position = 0;
        DictionaryNode lastnode = null;
        //prevNodes = new ArrayList<DictionaryNode>();
        for(byte b : file){
            //System.out.println(linesRead + "% bytes read     out of: " + file.length);
            linesRead++;
            percentageRead = linesRead/file.length;

            node = new DictionaryNode(b);

            //node = dictionary.getNode(b);

            //if position is 0, only check top nodes
            if(position == 0) {
                node = dictionary.getParentNode(b);
                if(node == null) {
                    dictionary.add(index, position, b);
                    WritetoOutput(b, position);
                    //System.out.println("Index: " + index + "   Phrase Number: " + position + "   Value: " + b);
                    lastnode = null;
                    index++;
                    position = 0;
                }else{
                    position = node.getIndex();
                    lastnode = node;
                    continue;
                }
            }else{
                /*System.out.println();System.out.println();System.out.println();
                for(DictionaryNode d : lastnode.children){
                    System.out.println("Index: " + d.getIndex() + "    phraseNumber: " + d.getPhraseNumber() + "   value: " + d.value);
                }*/
                DictionaryNode _node = lastnode.getNode(node);
                if(_node != null) {
                    node = _node;
                    position = node.getIndex();
                    lastnode = node;
                    continue;
                }else {
                    dictionary.add(index, position, b, lastnode);
                    WritetoOutput(b, position);
                    //System.out.println("Index: " + index + "   Phrase Number: " + position + "   Value: " + b);
                    lastnode = null;
                    index++;
                    position = 0;
                }
            }

        }
        try{
        bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void WritetoOutput(byte b, int position){
        //char ch = (char) b;
        String output = position + " " + b + "\n";
        try{
            bw.write(output);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void CreateOutputFile(){
        try{

            OutputFile = new File("C:\\Users\\Jesse\\Desktop\\TestOutPut.txt");
            OutputFile.delete();
            OutputFile.createNewFile();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public byte[] readinFile(File inputFile){
        byte[] fc;
        try{
            fis = new FileInputStream(inputFile);

            fc = new byte[(int)inputFile.length()];

            fis.read(fc);

            String s = new String(fc);
            //System.out.println(s);
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found: " + e);
            return null;
        }catch (IOException e){
            System.out.println("Error while reading file: " + e);
            return null;
        }

        try {
            fis.close();
            return fc;
        }catch(IOException e){
            System.out.println("Error while closing stream: " + e);
            return null;
        }
    }
}
