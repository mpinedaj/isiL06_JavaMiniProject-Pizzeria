package ui;

public class PizzaAnimation {

    static final String RESET  = "\033[0m";
    static final String BOLD   = "\033[1m";
    static final String DIM    = "\033[2m";
    static final String ORANGE = "\033[38;5;214m";
    static final String GOLD   = "\033[38;5;220m";
    static final String BROWN  = "\033[38;5;130m";
    static final String CREAM  = "\033[38;5;229m";
    static final String GRAY   = "\033[38;5;240m";
    static final String CLEAR  = "\033[H\033[2J";
    static final String HIDE   = "\033[?25l";
    static final String SHOW   = "\033[?25h";

    public static void mostrar() throws InterruptedException {
        System.out.print(HIDE);
        try {
            intro();
        } finally {
            System.out.print(SHOW);
        }
    }
    static void intro() throws InterruptedException {

        // ── Pizza ASCII ───────────────────────────────────────────
        String[] pizza = {
                "         .,:::,.,         ",
                "      .:::::::::::::.      ",
                "    .::  o   :::  o  ::.   ",
                "   :::   :::::::::::   ::  ",
                "   ::  o  ::: ~ :::  o ::  ",
                "   :::    ::: ~ :::    ::  ",
                "    '::  o  :::::  o  ::'  ",
                "      ':::::::::::::'      ",
                "         ':::::::'         ",
        };

        String[] colores = {
                BROWN, BROWN,
                ORANGE, GOLD,
                ORANGE, ORANGE,
                GOLD, BROWN, BROWN,
        };

        // ── Aparece la pizza línea por línea ──────────────────────
        limpiar();
        espacio(5);
        for (int i = 0; i < pizza.length; i++) {
            System.out.println("  " + colores[i] + BOLD + pizza[i] + RESET);
            Thread.sleep(80);
        }

        // ── Título aparece debajo ─────────────────────────────────
        Thread.sleep(200);
        espacio(1);
        escribirLento("        P A P A ' S", CREAM + BOLD, 55);

        Thread.sleep(150);
        System.out.println(GRAY + DIM + "        pizzeria artesanal" + RESET);

        // ── Separador ─────────────────────────────────────────────
        Thread.sleep(400);
        espacio(1);
        System.out.println(GRAY + "        ─────────────────" + RESET);
        Thread.sleep(200);
        // ── Prompt ────────────────────────────────────────────────
        espacio(1);

        }
    static void limpiar() {
        System.out.print(CLEAR);
        System.out.flush();
    }

    static void espacio(int n) {
        for (int i = 0; i < n; i++) System.out.println();
    }

    static void escribirLento(String txt, String color, int ms) throws InterruptedException {
        for (char c : txt.toCharArray()) {
            System.out.print(color + c + RESET);
            System.out.flush();
            Thread.sleep(ms);
        }
        System.out.println();
    }

    static void parpadear() throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            System.out.print(ORANGE + "\u2588" + RESET);
            System.out.flush();
            Thread.sleep(450);
            System.out.print("\b \b");
            System.out.flush();
            Thread.sleep(450);
        }
        System.out.print(ORANGE + "\u2588" + RESET);
        System.out.flush();
        System.out.println();
    }
}
