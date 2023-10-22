public class Main {
    public static void main(String[] args) {
        Gen gen = new Gen();

        int size = 1024;
        String in = "src/data/tdne";
        String out = "src/output";
        int weight = 0;

        gen.start(size, in, out);
    }
}
