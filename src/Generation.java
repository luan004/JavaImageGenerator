import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Generation {
    static void gen(int size, ArrayList<int[][]> data) {
        int[][] output = new int[size][size];

        for (int y = 0; y < output.length; y++) {
            for (int x = 0; x < output[0].length; x++) {
                // DATA
                ArrayList<Integer> pixels = new ArrayList<>();

                // Contador para contar as ocorrências de cada cor
                HashMap<Integer, Integer> colorCounts = new HashMap<>();

                for (int[][] image : data) {
                    int val = image[y][x];

                    // Incrementa o contador para esta cor
                    colorCounts.put(val, colorCounts.getOrDefault(val, 0) + 1);
                }

                // Encontra a cor predominante
                int predominante = Collections.max(colorCounts.entrySet(), Map.Entry.comparingByValue()).getKey();

                // Adiciona apenas a cor predominante ao array
                pixels.add(predominante);

                output[y][x] = Util.colorMix(pixels);
            }
        }

        Files.createImage(output, size);
    }
    static void overlay(int size, ArrayList<int[][]> data) {
        int[][] output = new int[size][size];

        for (int y = 0; y < output.length; y++) {
            for (int x = 0; x < output[0].length; x++) {
                // DATA
                ArrayList<Integer> pixels = new ArrayList<>();

                for (var image: data) {
                    int val = image[y][x];
                    pixels.add(val);
                }

                output[y][x] = Util.colorMix(pixels);
            }
        }

        Files.createImage(output, size);
    }
}
