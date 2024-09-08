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

    static ArrayList<int[][]> dataProcessor(
            final int OUTPUT_IMG_SIZE,
            final String INPUT_PATH,
            final int INPUT_IMG_COUNT
    ) {
        File path = new File(INPUT_PATH);

        ArrayList<int[][]> images = new ArrayList<>();

        try {
            // emaralhar dataset e selecionar as imagens que serão utilizadas
            File[] files = path.listFiles();
            List<File> fileList = Arrays.asList(files);
            Collections.shuffle(fileList);
            List<File> randomFiles = fileList.subList(0, Math.min(INPUT_IMG_COUNT, fileList.size()));

            // pasta vazia
            if (files.length != 0) {
                assert files != null;
                for (File file: randomFiles) {
                    if (
                        file.isFile() &&
                            (
                                file.getName().toLowerCase().endsWith(".jpg") ||
                                file.getName().toLowerCase().endsWith(".jpeg") ||
                                file.getName().toLowerCase().endsWith(".png"))
                            )
                    {
                        try {
                            BufferedImage image = ImageIO.read(file);

                            int[][] processedImage = new int[OUTPUT_IMG_SIZE][OUTPUT_IMG_SIZE];

                            for (int y = 0; y < OUTPUT_IMG_SIZE; y++) {
                                for (int x = 0; x < OUTPUT_IMG_SIZE; x++) {

                                    int yCentered = (OUTPUT_IMG_SIZE - image.getHeight()) / 2;
                                    int xCentered = (OUTPUT_IMG_SIZE - image.getWidth()) / 2;

                                    int finalX = x;
                                    int finalY = y;

                                    if (yCentered < 0) {
                                        finalY = (yCentered * -1) + y;
                                        yCentered = 0;
                                    }

                                    if (xCentered < 0) {
                                        finalX = (xCentered * -1) + x;
                                        xCentered = 0;
                                    }

                                    processedImage[yCentered + y][xCentered + x] = image.getRGB(finalX, finalY );
                                    if (x == image.getWidth()-1) {
                                        x = OUTPUT_IMG_SIZE;
                                    }
                                }
                                if (y == image.getHeight()-1) {
                                    y = OUTPUT_IMG_SIZE;
                                }
                            }
                            images.add(processedImage);
                        } catch (IOException e) {
                            System.out.println("ERROR: failed when trying to process image '" + file.getName() + "'");
                        }
                    } else {
                        System.err.println("ERROR: the file '" + file.getName() + "' is not a jpg image");
                    }
                }
            } else {
                System.err.println("ERROR: empty directory (" + path.getAbsolutePath() + ")");
            }
        } catch (NullPointerException e) {
            System.err.println("ERROR: dataset directory not found");
        }

        return images;
    }
}
