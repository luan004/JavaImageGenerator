import java.util.ArrayList;

// v1.0

public class Main {
    public static void main(String[] args) {
        View.view();
        //MinhaInterface.start();
        String in = "src/data/cars";
        String out = "src/output";
        String type = "newgen"; // overlay - gen - gen1

        int size = 1024;
        int round = 15;
        int datanum = 4; // Quantidade de imagens utilizadas para gerar
        int imagecount = 2;

        //start(size, in, out, datanum, type, round, imagecount);
    }

    public static void start(int size, String in, String out, int datanum, String type, int round, int imagecount) {


        for (int i = 0; i < imagecount; i++) {
            ArrayList<int[][]> data = Files.dataProcessor(size, in, datanum);
            switch (type) {
                case "overlay":
                    Generation.overlay(size, data);
                    break;
                case "gen":
                    Generation.gen(size, data);
                    break;
                case "newgen":
                    Generation.newgen(size, data, round);
                    break;
            }
        }
    }
}
