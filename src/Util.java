import java.awt.*;
import java.util.*;
import java.util.List;

public class Util {

    static int getMixedColors(ArrayList<Integer> pixels) {
        float totalH = 0, totalS = 0, totalV = 0;
        float totalX = 0, totalY = 0;

        for (Integer pixel : pixels) {
            float[] hsv = rgbToHsv(pixel);

            // Para Hue, converte para coordenadas cartesianas e acumula
            double hueRadians = Math.toRadians(hsv[0] * 360); // Converte Hue para radianos
            totalX += Math.cos(hueRadians);  // x = cos(Hue)
            totalY += Math.sin(hueRadians);  // y = sin(Hue)

            // Para Saturation e Value, soma diretamente
            totalS += hsv[1];
            totalV += hsv[2];
        }

        // Média de Hue em coordenadas polares
        float avgH = (float) Math.toDegrees(Math.atan2(totalY, totalX)); // Converte de volta para graus
        if (avgH < 0) avgH += 360;  // Ajusta para garantir que esteja no intervalo [0, 360]
        avgH /= 360;  // Normaliza o Hue para o intervalo [0, 1]

        // Média de Saturation e Value
        int count = pixels.size();
        float avgS = totalS / count;
        float avgV = totalV / count;

        // Converte a cor média de HSV de volta para um int RGB
        return hsvToRgb(avgH, avgS, avgV);
    }

    private static float[] rgbToHsv(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        float[] hsv = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);  // Usa função padrão do Java para converter RGB para HSV
        return hsv;
    }

    // Função para converter HSV de volta para um int RGB
    private static int hsvToRgb(float h, float s, float v) {
        // Converte HSV para um Color e extrai o int RGB
        int rgb = Color.HSBtoRGB(h, s, v);
        return rgb;
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

        int l = pixelsColor.size();

        r = r/l;
        g = g/l;
        b = b/l;

        return (r << 16) | (g << 8) | b;
    }

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
                    // Se as cores são suficientemente próximas, considera como uma mesma cor
                    return color1;
                }
            }
        }

        // Se não encontrou cores próximas, retorna a cor com mais ocorrências
        return sortedColors.get(0).getKey();
    }

    static String decodeRGB(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return red + "," + green + "," + blue;
    }
    static int encodeRGB(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
    static int roundColor(int rgb, int round) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        r = Math.round((float) r / round) * round;
        g = Math.round((float) g / round) * round;
        b = Math.round((float) b / round) * round;

        if (r > 255) {
            r = 255;
        }

        if (g > 255) {
            g = 255;
        }

        if (b > 255) {
            b = 255;
        }

        return (r << 16) | (g << 8) | b;
    }

    static Integer getPredominantColor(ArrayList<Integer> pixels) {
        Map<Integer, Integer> count = new HashMap<>();

        for (Integer valor : pixels) {
            count.put(valor, count.getOrDefault(valor, 0) + 1);
        }

        int maxCount = Collections.max(count.values());
//        long tied = count.values().stream().filter(v -> v == maxCount).count();
//
//        if (tied > 1) {
//            // Tied
//            return 0;
//        }

        return count.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxCount)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
