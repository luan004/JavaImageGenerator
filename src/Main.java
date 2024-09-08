import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties prop = new Properties();
        try {
            FileInputStream settingsFile = new FileInputStream("settings.config");
            prop.load(settingsFile);

            final String INPUT_PATH = prop.getProperty("INPUT_PATH");
            final String GEN_TYPE = prop.getProperty("GEN_TYPE");
            final int OUTPUT_IMG_SIZE = Integer.parseInt(prop.getProperty("OUTPUT_IMG_SIZE"));
            final int ROUND = Integer.parseInt(prop.getProperty("ROUND"));
            final int INPUT_IMG_COUNT = Integer.parseInt(prop.getProperty("INPUT_IMG_COUNT"));
            final int OUTPUT_IMG_COUNT = Integer.parseInt(prop.getProperty("OUTPUT_IMG_COUNT"));

            run(
                    INPUT_PATH,
                    GEN_TYPE,
                    OUTPUT_IMG_SIZE,
                    ROUND,
                    INPUT_IMG_COUNT,
                    OUTPUT_IMG_COUNT
            );

        } catch (IOException ex) {
            System.err.println("File not found");
        }
    }

    private static void run(
            final String INPUT_PATH,
            final String GEN_TYPE,
            final int OUTPUT_IMG_SIZE,
            final int ROUND,
            final int INPUT_IMG_COUNT,
            final int OUTPUT_IMG_COUNT
    ) {
        for (int i = 0; i < OUTPUT_IMG_COUNT; i++) {
            ArrayList<int[][]> data = Files.dataProcessor(OUTPUT_IMG_SIZE, INPUT_PATH, INPUT_IMG_COUNT);
            File file;
            switch (GEN_TYPE) {
                case "overlay":
                    file = Generation.overlay(OUTPUT_IMG_SIZE, data);
                    break;
                case "gen":
                    file = Generation.gen(OUTPUT_IMG_SIZE, data);
                    break;
                case "newgen":
                    file = Generation.newgen(OUTPUT_IMG_SIZE, data, ROUND);
                    break;
            }
        }
    }
}
