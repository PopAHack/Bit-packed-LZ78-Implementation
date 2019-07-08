import java.io.*;
import java.util.ArrayList;

public class Decoder {

    BufferedReader br;
    BufferedWriter bw;
    ArrayList<String> inputfileList;
    ArrayList<String> dictionary;
    String currentOutput;
    ArrayList<String> DecodedOutputList;

    File OutputFile;

    public Decoder(){
        CreateOutputFile();
    }

    public void decodeFile(File inputFile){
        inputfileList = new ArrayList<>();
        dictionary = new ArrayList<>();
        dictionary.add(null); //adds a null value to position 0 in list
        DecodedOutputList = null;

        try{
            br = new BufferedReader(new FileReader(inputFile));
            bw = new BufferedWriter(new FileWriter(OutputFile, true));

            while(br.ready()){
                inputfileList.add(br.readLine());
            }
            System.out.println(inputfileList.size());

            //System.out.println(dictionary);

            for (String s : inputfileList){
                String[] parts = s.split(" ");

                int phasenumber = Integer.parseInt(parts[0]);

                if(parts.length == 2){

                    //converts byte value back into character and then into string  ie: 115 -> t
                    int byteValue = Integer.parseInt(parts[1]);
                    char newvalue = (char) byteValue;
                    String value = Character.toString(newvalue);

                    if(phasenumber == 0){
                        if(!dictionary.contains(value))
                            dictionary.add(value);
                        output(value);
                    }else{
                        currentOutput = dictionary.get(phasenumber) + value;
                        dictionary.add(currentOutput);
                        output(currentOutput);
                    }
                }else{
                    char newValue = ' ';
                }
            }
            bw.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found: " + e);
        }catch (IOException e){
            System.out.println("Error while reading file: " + e);
        }
    }

    public void output(String outString){
        try{
            bw.write(outString);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void CreateOutputFile(){
        try{
            OutputFile = new File("C:\\Users\\Jesse\\Desktop\\DecodeOutput.txt");
            OutputFile.delete();
            OutputFile.createNewFile();

        }catch(IOException e){
            e.printStackTrace();    }

    }
}
