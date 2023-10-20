import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //PARAMS
        int size = 3;
        String local = "src/data/3x3";

        //DATA
        ArrayList<int[][]> data = imageProcessor(size, local);

        // result
        int[][] res = new int[size][size];

        for (int y = 0; y < res.length; y++) {
            for (int x = 0; x < res[0].length; x++) {
                // DATA
                ArrayList<Integer> pixels = new ArrayList<>();

                for (int[][] image: data) {
                    int val = image[y][x];
;                    pixels.add(val);
                }

                res[y][x] = colorMix(pixels);
            }
        }

        //print(res);
        createImage(size, res);
    }

    static ArrayList<int[][]> imageProcessor(int size, String local) {
        // FOLDER PATH
        File path = new File(System.getProperty("user.dir") + File.separator + local);

        ArrayList<int[][]> processedImages = new ArrayList<>();

        if (path.isDirectory()) {
            File[] files = path.listFiles();

            // FOR EACH FILE
            assert files != null;
            for (File file: files) {
                // IS A JPG IMAGE?
                if (file.isFile() && file.getName().toLowerCase().endsWith(".jpg")) {

                    // READ IMAGE
                    try {
                        BufferedImage image = ImageIO.read(file);

                        // IMAGE SIZE IS VALID?
                        if (image != null && image.getWidth() == size && image.getHeight() == size) {
                            // O ARQUIVO É VÁLIDO

                            int[][] processedImage = new int[size][size];

                            for (int y = 0; y < size; y++) {
                                for (int x = 0; x < size; x++) {
                                    // Armazena a cor no array
                                    processedImage[y][x] = image.getRGB(x, y);
                                }
                            }

                            processedImages.add(processedImage);

                        } else {
                            System.err.println("A imagem '" + file.getName() + "' está corrompida ou não tem tamanho " + size + "x" + size);
                            System.exit(0);
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

        return processedImages;
    }

    static int colorMix(ArrayList<Integer> pixelsColor) {
        int r = 0;
        int g = 0;
        int b = 0;

        for (int rgb: pixelsColor) {
            r = r + ((rgb >> 16) & 0xFF);
            g = g + ((rgb >> 8) & 0xFF);
            b = b + (rgb & 0xFF);
        }

        int l = pixelsColor.toArray().length;

        r = r/l;
        g = g/l;
        b = b/l;

        return (r << 16) | (g << 8) | b;
    }

    static void createImage(int size, int[][] pixels) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int rgb = pixels[y][x];
                image.setRGB(x, y, rgb);
            }
        }
        long timestamp = System.currentTimeMillis();
        File output = new File("src/output/" + timestamp + ".jpg");
        try {
            ImageIO.write(image, "jpg", output);
            System.out.println("Imagem gerada com sucesso: " + output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void print(int[][] mat) {
        // EIXO Y
        for (int i = 0; i < mat.length; i++) {
            //EIXO X
            System.out.print(" | ");
            for (int o = 0; o < mat[0].length; o++) {
                System.out.print(decodeRGB(mat[i][o]) + " | ");
            }
            System.out.println();
        }
    }

    static String decodeRGB(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return red + "," + green + "," + blue;
    }
}