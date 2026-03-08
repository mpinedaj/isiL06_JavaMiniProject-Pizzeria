package services;

public class IdGenerator {
    private static int current = 0;

    public static int nextId() {
        current++;
        return current;
    }
}
