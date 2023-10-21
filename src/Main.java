public class Main {
    public static void main(String[] args) {
        Gen gen = new Gen();

        int size = 512;
        String in = "src/data/face";
        String out = "src/output";

        gen.start(size, in, out);
    }
}
