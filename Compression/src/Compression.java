import java.io.File;

public class Compression {

    public static void main(String[] args){

<<<<<<< HEAD
        String filePath = "H:\\Coding\\java code\\301 Assignment 2\\Compression\\src\\MobyDick.txt";
        File encodedInput = new File("H:\\Coding\\java code\\Compx301 - Assignment 2\\Compression\\EncodedOutput.txt");
=======
        //String filePath = "H:\\Coding\\java code\\Compx301 - Assignment 2\\Compression\\src\\MobyDick.txt";
        String filePath = "C:\\Users\\Jesse\\Desktop\\MobyDick.txt";
        File encodedInput = new File("C:\\Users\\Jesse\\Desktop\\TestOutPut");
>>>>>>> origin/master
        File input = new File(filePath);

        Encoder encoder = new Encoder();
        encoder.encodeFile(input);

        Decoder decoder = new Decoder();
        decoder.decodeFile(encodedInput);
    }
}
