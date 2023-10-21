import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Gen {
    public static void start(int size, String in, String out) {
        ArrayList<int[][]> data = dataProcessor(size, in);

        gen(size, data);
    }
    private static ArrayList<int[][]> dataProcessor(int size, String in) {
        File path = new File(System.getProperty("user.dir") + File.separator + in);

        ArrayList<int[][]> images = new ArrayList<>();

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            if (files.length != 0) {
                assert files != null;
                for (File file: files) {
                    if (file.isFile() && (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg"))) {
                        try {
                            BufferedImage image = ImageIO.read(file);
                            if (image != null && image.getWidth() == size && image.getHeight() == size) {

                                int[][] processedImage = new int[size][size];

                                for (int y = 0; y < size; y++) {
                                    for (int x = 0; x < size; x++) {
                                        // Armazena a cor no array
                                        processedImage[y][x] = image.getRGB(x, y);
                                    }
                                }
                                images.add(processedImage);

                            } else {
                                System.err.println("A imagem '" + file.getName() + "' está corrompida ou não tem tamanho " + size + "x" + size);
                                System.exit(0);
                            }
                        } catch (IOException e) {
                            System.out.println("Houve um problema ao tentar ler a imagem '" + file.getName());
                            System.exit(0);
                        }
                    } else {
                        System.err.println("O arquivo '" + file.getName() + "' não é uma imagem jpg");
                        System.exit(0);
                    }
                }
            } else {
                System.err.println("A pasta " + path.getName() + " está vazia");
                System.exit(0);
            }
        } else {
            System.err.println("Diretório não encontrado: " + path);
            System.exit(0);
        }

        return images;
    }
    private static void gen(int size, ArrayList<int[][]> data) {
        int[][] output = new int[size][size];

        for (int y = 0; y < output.length; y++) {
            for (int x = 0; x < output[0].length; x++) {
                // DATA
                ArrayList<Integer> pixels = new ArrayList<>();

                for (var image: data) {
                    int val = image[y][x];
                    pixels.add(val);
                }

                output[y][x] = colorMix(pixels);
            }
        }

        createImage(output, size);
    }
    private static int colorMix(ArrayList<Integer> pixelsColor) {
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
    private static void createImage(int[][] output, int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int rgb = output[y][x];
                image.setRGB(x, y, rgb);
            }
        }
        long timestamp = System.currentTimeMillis();
        File outputFile = new File("src/output/" + timestamp + ".jpg");
        try {
            ImageIO.write(image, "jpg", outputFile);
            System.out.println("Imagem gerada com sucesso: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
