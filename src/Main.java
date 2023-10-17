public class Main {
    public static void main(String[] args) {
        int[][] mat = new int[2][3];

        // Matriz M(y,x)
        // Eixo Y vertical, linhas
        // Eixo X horizontal, colunas

        mat[0][2] = 1;
        print(mat);
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
}