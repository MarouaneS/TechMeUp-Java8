package generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class ComptesGenerator {

    public static void main(String[] args) throws Exception {
        generateComptes();
        generateEmprunts();
    }

    private static void generateComptes() throws IOException {
        Path comptes = Paths.get("files/comptes.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(comptes))
        {
            for(int i = 0; i < 1000; i++) {
                writer.write(generateNewCompte(i));
                writer.write("\n");
            }
        }
    }

    private static void generateEmprunts() throws IOException {
        Path comptes = Paths.get("files/emprunts.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(comptes))
        {
            for(int i = 0; i < 10000; i++) {
                writer.write(generateNewEmprunt(i));
                writer.write("\n");
            }
        }
    }

    private static String generateNewCompte(int id) {
        return String.join(";", String.valueOf(id),
                                        "Compte " + id,
                                        String.valueOf(getRandomYear()));
    }

    private static String generateNewEmprunt(int id) {
        return String.join(";", String.valueOf(getRandomIdEmprunt()),
                                         String.valueOf(id),
                                         String.valueOf(getRandomMontant()),
                                         String.valueOf(getRandomYear()));
    }

    private static int getRandomYear() {
        return 1968 + new Random().nextInt(50);
    }

    private static int getRandomMontant() {
        return new Random().nextInt(1_000_000_000);
    }

    private static int getRandomIdEmprunt() {
        return new Random().nextInt(1_000);
    }

}