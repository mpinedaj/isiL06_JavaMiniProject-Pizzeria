package services;

public class IdGenerator {
    private static int current = 100;

    public static int nextId() {
        current++;
        return current;
    }
}
