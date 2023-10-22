import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int size = 1024;
        String in = "src/data/cars";
        String out = "src/output";
        int weight = 0;
        int datanum = 4; // Quantidade de imagens utilizadas para gerar
        String type = "overlay"; // overlay - gen - gen1

        start(size, in, out, datanum, type);
    }

    public static void start(int size, String in, String out, int datanum, String type) {
        ArrayList<int[][]> data = Files.dataProcessor(size, in, datanum);

        switch (type) {
            case "overlay":
                Generation.overlay(size, data);
                break;
            case "gen":
                Generation.gen(size, data);
                break;
        }
    }
}
