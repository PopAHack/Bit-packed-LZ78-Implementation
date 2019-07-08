import java.io.*;
import java.util.ArrayList;

public class BitUnpacker {

    private File OutputFile;
    private FileInputStream fis;
    private ArrayList<String> libStrings;
    private int MASK;
    private dictionary dictionary;
    private BufferedWriter bw;

    public BitUnpacker(){}

    public void BitunPack(Byte[] inputStream){
        CreateOutputFile();
        //readinDictionary(----put file path here----)
        //readinFile(---- put file path here----);
        dictionary.populateDictionary();

        try {
            bw = new BufferedWriter(new FileWriter(OutputFile, true));

    
            int currentbit;
            MASK = 0x01; // byte value = 0000 0001

            for (byte b : inputStream){
                for(int i=7; i==0; i--){
                    currentbit = b >>> i;
                    currentbit = currentbit & MASK;  //Makes '0000 0xxx' to '0000 000x' to few single bit

                    moveThroughDictionary(currentbit);
                }
            }
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void moveThroughDictionary(int bit){
        node currentnode = dictionary.getCurrentNodePointer();
        if(bit == 0){
            currentnode = currentnode.zeroNode;
        }else if(bit == 1){
            currentnode = currentnode.oneNode;
        }

        if(currentnode.isEndNode()){
            writeOutput(currentnode.aByte);
            dictionary.resetCurrentNodePointer();
        }else{
            dictionary.setCurrentNodePointer(currentnode);
        }
    }

    public void writeOutput(byte b){
        try{
            bw.write(b);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void CreateOutputFile(){
        try{
            OutputFile = new File("H:\\Coding\\java code\\Compx301 - Assignment 2\\Compression\\Bit_Unpacker_Output.txt");
            OutputFile.delete();
            OutputFile.createNewFile();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public byte[] readinFile(File inputFile) {
        byte[] fc;
        try {
            fis = new FileInputStream(inputFile);
            fc = new byte[(int) inputFile.length()];
            fis.read(fc);

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
            return null;
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e);
            return null;
        }

        try {
            fis.close();
            return fc;
        } catch (IOException e) {
            System.out.println("Error while closing stream: " + e);
            return null;
        }
    }

    public void readinDictionary(File inputFile) {
        try{
            //fis = new FileInputStream(inputFile);
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            while(br.ready()){
                libStrings.add(br.readLine());

                /*
                String[] parts = s.split(" ");
                char c = parts[0].charAt(0);
                libChars.add((byte)c);
                libCodes.add(parts[1]);
                */
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        }catch (IOException e) {
            System.out.println("Error while reading file: " + e);
        }try{
            fis.close();
        }catch (IOException e) {
            System.out.println("Error while closing stream: " + e);
        }
    }

    public class dictionary{
        private node currentNodePointer;
        private node parentNode;

        public dictionary(node parentNode){
            this.parentNode = parentNode;
        }

        public node getCurrentNodePointer(){
            return currentNodePointer;
        }

        public void resetCurrentNodePointer(){currentNodePointer = parentNode; }

        public void setCurrentNodePointer(node n){currentNodePointer = n;}

        public void populateDictionary(){
            currentNodePointer = parentNode;
            for(String s : libStrings){
                String[] parts = s.split(" ");
                byte b = Byte.decode(parts[0]);

                char[] code = parts[1].toCharArray();

                for(int i=0;i<code.length;i++){

                    //If this is the last code number in the given code
                    //then check if it exist's
                    //----if it doesn't -- Add to dictionary
                    //----if it does ----- Don't add
                    //Then reset to the top of the dictionary and read in next string
                    if(i == code.length-1){
                        if(code[i] == 0){
                            if(currentNodePointer.zeroNode == null)
                                currentNodePointer.zeroNode = new node(b,parts[1]);
                        }else{
                            if(currentNodePointer.oneNode == null)
                                currentNodePointer.oneNode = new node(b,parts[1]);
                        }
                        currentNodePointer = parentNode;
                        return;
                    }

                    if(code[i] == 0){
                        if(currentNodePointer.zeroNode == null)
                            currentNodePointer.zeroNode = new node("0");
                        currentNodePointer = currentNodePointer.zeroNode;
                    }else{
                        if(currentNodePointer.oneNode == null)
                            currentNodePointer.oneNode = new node("1");
                        currentNodePointer = currentNodePointer.oneNode;
                    }
                }
                currentNodePointer = parentNode;
            }
        }

    }

    public class node{
        private byte aByte;
        private String _code;
        private node zeroNode;
        private node oneNode;
        private boolean endNode;

        public node(byte b, String c){
            this.aByte = b;
            this._code = c;
            zeroNode = null;
            oneNode = null;
            endNode = true;
        }

        public node(String c){
            this._code = c;
            zeroNode = null;
            oneNode = null;
            endNode = false;
        }

        public node(){
            this._code = null;
            zeroNode = null;
            oneNode = null;
            endNode = false;
        }

        public boolean isEndNode() {
            return endNode;
        }
    }
}


