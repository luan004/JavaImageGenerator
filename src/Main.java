public class Main {
    public static void main(String[] args) {
        //DATA
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
        int[][] res = new int[3][3];

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
}