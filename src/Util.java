import java.util.*;

public class Util {
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
                    // Se as cores são suficientemente próximas, considera como uma única cor
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
