package services;

public class IdGenerator {
    private static int current = 1;

    public static int nextId() {
        current++;
        return current;
    }
}
