import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //PARAMS
        int size = 3;
        String local = "src/data/3x3";

        //DATA
        imageProcessor(size, local);

        int[][] data1 = {
                {1,1,2},
                {2,2,2},
                {4,3,3}
        };

        int[][] data2 = {
                {1,2,2},
                {2,2,2},
                {4,4,4}
        };

        // result
        int[][] res = new int[size][size];

        for (int i = 0; i < res.length; i++) {
            for (int o = 0; o < res[0].length; o++) {
                int[] colors = {data1[i][o], data2[i][o]};
                res[i][o] = calcColor(colors);
            }
        }

        print(res);
    }

    static void print(int[][] mat) {
        // EIXO Y
        for (int i = 0; i < mat.length; i++) {
            //EIXO X
            System.out.print(" | ");
            for (int o = 0; o < mat[0].length; o++) {
                //System.out.println("M" + (i+1) + "," + (o+1) + " = " + mat[i][o]);
                System.out.print(mat[i][o] + " | ");
            }
            System.out.println();
        }
    }

    static int calcColor(int[] colors) {
        // 1 - blue
        // 2 - light blue
        // 3 - green
        // 4 - palha
        // 5 - yellow
        // 6 - orange
        // 7 - red

        int plus = 0;
        for (int color : colors) {
            plus = plus + color;
        }
        return plus / colors.length;
    }

    static int[] imageProcessor(int size, String local) {
        // FOLDER PATH
        File path = new File(System.getProperty("user.dir") + File.separator + local);

        if (path.isDirectory()) {
            File[] files = path.listFiles();

            // FOR EACH FILE
            for (File file: files) {
                // IS A JPG IMAGE?
                if (file.isFile() && file.getName().toLowerCase().endsWith(".jpg")) {

                    // READ IMAGE
                    try {
                        BufferedImage image = ImageIO.read(file);

                        // IMAGE SIZE IS VALID?
                        if (image.getWidth() == size && image.getHeight() == size) {

                        } else {
                            System.err.println("A imagem '" + file.getName() + "' não tem tamanho " + size + "x" + size);
                        }
                    } catch (IOException e) {
                        System.out.println("Houve um problema ao tentar ler a imagem '" + file.getName());
                    }
                } else {
                    System.err.println("O arquivo '" + file.getName() + "' não é uma imagem jpg");
                }
            }
        } else {
            System.err.println("Diretório não encontrado: " + path);
        }

        return new int[]{0};
    }
}