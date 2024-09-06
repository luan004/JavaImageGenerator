import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        View view = new View();
//        new ViewController(view);


        String in = "/home/luan/dataset/faces";
        String out = "../src/output";
        String gentype = "gen";
        int imgsize = 1024;
        int round = (int) 24;
        int inputCount = 4;
        int outputCount = 2;

        for (int i = 0; i < outputCount; i++) {
            ArrayList<int[][]> data = Files.dataProcessor(imgsize, in, inputCount);
            File file;
            switch (gentype) {
                case "overlay":
                    file = Generation.overlay(imgsize, data);
                    break;
                case "gen":
                    file = Generation.gen(imgsize, data);
                    break;
                case "newgen":
                    file = Generation.newgen(imgsize, data, round);
                    break;
            }
        }
    }
}
