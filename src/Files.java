import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Files {
    static File writeImage(int[][] output, int size) {

        File outputFile = createFile();
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int rgb = output[y][x];
                image.setRGB(x, y, rgb);
            }
        }

        try {
            ImageIO.write(image, "jpg", outputFile);
            System.out.println("Imagem gerada com sucesso: " + outputFile.getAbsolutePath());
            return outputFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static File createFile() {
        try {
            Properties prop = new Properties();
            FileInputStream settingsFile = new FileInputStream("settings.config");
            prop.load(settingsFile);

            final String OUTPUT_PATH = prop.getProperty("OUTPATH_PATH");

            return new File(OUTPUT_PATH + "/" + System.currentTimeMillis() + ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static ArrayList<int[][]> dataProcessor(int size, String in, int datanum) {
        File path = new File(in);

        ArrayList<int[][]> images = new ArrayList<>();

        if (path.isDirectory()) {
            File[] files = path.listFiles();
            List<File> fileList = Arrays.asList(files);
            Collections.shuffle(fileList);
            List<File> randomFiles = fileList.subList(0, Math.min(datanum, fileList.size()));

            if (files.length != 0) {
                assert files != null;
                for (File file: randomFiles) {
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
}
