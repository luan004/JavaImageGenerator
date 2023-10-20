import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //PARAMS
        int size = 512; // Tamanho da imagem
        String local = "src/data/"+size+"x"+size; // Pasta das imagens de entrada
        int tol = 80; // Valor da tolerância de  similaridade entre cores

        //DATA
        ArrayList<int[][]> data = imageProcessor(size, local);

        // result
        int[][] res = new int[size][size];

        for (int y = 0; y < res.length; y++) {
//            for (int x = 0; x < res[0].length; x++) {
//                // DATA
//                ArrayList<Integer> pixels = new ArrayList<>();
//
//                for (int[][] image: data) {
//                    int val = image[y][x];
//;                    pixels.add(val);
//                }
//
//                res[y][x] = colorMix(pixels);
//            }


            for (int x = 0; x < res[0].length; x++) {

                // O seleciona qual o tom de cor mais comum em determinado pixel
                // de cada imagem, após isso, monta um array que guarda apenas as
                // cores semelhantes e cria uma nova cor que equivale a cor média
                // das cores no array, essa cor média será usado para colorir o
                // pixel da imagem gerada.

                ArrayList<Integer> pixels = new ArrayList<>();

                // Contador para contar as ocorrências de cada cor
                HashMap<Integer, Integer> colorCounts = new HashMap<>();

                for (int[][] image : data) {
                    int val = image[y][x];

                    // Incrementa o contador para esta cor
                    colorCounts.put(val, colorCounts.getOrDefault(val, 0) + 1);
                }

                // Encontra a cor predominante considerando uma tolerância
                int predominante = findPredominantColor(colorCounts, tol);

                // Adiciona apenas a cor predominante ao array
                pixels.add(predominante);

                res[y][x] = colorMix(pixels);
            }
        }

        //print(res);
        createImage(size, res);
    }

    private static int findPredominantColor(HashMap<Integer, Integer> colorCounts, int tol) {
        // Ordena as cores pelo número de ocorrências
        List<Map.Entry<Integer, Integer>> sortedColors = new ArrayList<>(colorCounts.entrySet());
        sortedColors.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Verifica se há cores suficientemente próximas para serem consideradas iguais
        for (int i = 0; i < sortedColors.size() - 1; i++) {
            int color1 = sortedColors.get(i).getKey();

            for (int j = i + 1; j < sortedColors.size(); j++) {
                int color2 = sortedColors.get(j).getKey();

                if (colorDistance(color1, color2) <= tol) {
                    // Se as cores são suficientemente próximas, considera como uma única cor
                    return color1;
                }
            }
        }

        // Se não encontrou cores próximas, retorna a cor com mais ocorrências
        return sortedColors.get(0).getKey();
    }

    // Função para calcular a diferença entre duas cores
    private static int colorDistance(int color1, int color2) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        // Distância Euclidiana entre as cores nos canais RGB
        return (int) Math.sqrt(Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2));
    }

    static ArrayList<int[][]> imageProcessor(int size, String local) {
        // FOLDER PATH
        File path = new File(System.getProperty("user.dir") + File.separator + local);

        ArrayList<int[][]> processedImages = new ArrayList<>();

        if (path.isDirectory()) {
            File[] files = path.listFiles();

            if (files.length == 0) {
                System.err.println("A pasta " + path.getName() + " está vazia");
                System.exit(0);
            }

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
                        System.exit(0);
                    }
                } else {
                    System.err.println("O arquivo '" + file.getName() + "' não é uma imagem jpg");
                    System.exit(0);
                }
            }
        } else {
            System.err.println("Diretório não encontrado: " + path);
            System.exit(0);
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