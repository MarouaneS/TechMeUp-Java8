import entity.Compte;
import entity.Emprunt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Compte> comptes = getAccountWithEmprunts();

        System.out.println(getFirstYearOfAccountCreation(comptes));
        System.out.println(getRangeBetweenLowestAndHighestYearOfAccountCreation(comptes));
        System.out.println(anyDoublon(comptes));
        System.out.println(getNumberOfAccountCreatedAt(comptes, 1987));
        System.out.println(accountNameOfAccountWithTheMostLoans(comptes));

        Map<Boolean, List<Emprunt>> empruntPartionnesParMontant = partitionEmpruntByMontant(comptes, 5_000_000);
        empruntPartionnesParMontant.forEach((k, v) -> {
            System.out.println(k ? "Nombre d'emprunts superieurs à 5M : " + v.size()
                                 : "Nombre d'emprunts inférieurs à 5M : " + v.size());
        });

        Map<Integer, Long> numberOfLoansByYears = getNumberOfAccountByYears(comptes);
        numberOfLoansByYears.forEach((k, v) -> System.out.println(k + " -> " + v));

        System.out.println(getYearWithMostAccountCreation(numberOfLoansByYears));
        System.out.println(getNumberOfDistinctAmountOfLoans(comptes));
        System.out.println(getMostPresentAmount(comptes));


        // Exos sur MAP :
        // Créer une map qui contient en clé la première lettre du nom du compte et en
        // valeur la liste des comptes qui commencent par cette lettre

        Map<String, List<Compte>> lettreComptes = new HashMap<>();
        comptes.forEach(compte -> lettreComptes.computeIfAbsent(compte.getNom().substring(0, 1),
                                                                key -> new ArrayList<>()).add(compte));
    }

    /**
     * Il faut retourner l'année dans laquelle le premier compte a été créé en utilisant le getter
     * getAnneeOuverture
     *
     * @param comptes
     * @return
     */
    private static int getFirstYearOfAccountCreation(List<Compte> comptes) {
        return
        comptes.stream()
               .mapToInt(Compte::getAnneeOuverture)
               .min()
               .getAsInt();
    }

    /**
     * Il faut retourner l'intervalle de temps entre l'année de première création de compte et
     * le dernier compte créé.
     *
     * @param comptes
     * @return
     */
    private static int getRangeBetweenLowestAndHighestYearOfAccountCreation(List<Compte> comptes) {
        IntSummaryStatistics statis =
        comptes.stream()
               .mapToInt(Compte::getAnneeOuverture)
               .summaryStatistics();

        return statis.getMax() - statis.getMin();
    }

    /**
     * Vérifier si la liste en entrée contient des doublons
     *
     * @param comptes
     * @return
     */
    private static boolean anyDoublon(List<Compte> comptes) {
        return
        !comptes.stream()
                .allMatch(new HashSet()::add);
    }

    /**
     * Retourner le nombre de comptes créés durant l'année passée en paramètre
     *
     * @param comptes
     * @param annee
     * @return
     */
    private static Long getNumberOfAccountCreatedAt(List<Compte> comptes, int annee) {
        return
        comptes.stream()
               .filter(compte -> compte.getAnneeOuverture() == annee)
               .count();
    }

    /**
     * Retourner le nom du compte pour lequel le nombre d'emprunts est le plus grand
     *
     * @param comptes
     * @return
     */
    private static String accountNameOfAccountWithTheMostLoans(List<Compte> comptes) {
        return
        comptes.stream()
               .max(Comparator.comparing(compte -> compte.getEmprunts().size()))
               .get()
               .getNom();
    }

    /**
     * Retourner un mapping qui partitionne les emprunt selon si le montant est supérieur ou pas
     * au montant passé en paramètre.
     *
     * @param comptes
     * @param montant
     * @return
     */
    private static Map<Boolean, List<Emprunt>> partitionEmpruntByMontant(List<Compte> comptes, int montant) {
        return
        comptes.stream()
               .flatMap(compte -> compte.getEmprunts().stream())
               .collect(Collectors.partitioningBy(emprunt -> emprunt.getMontant() > montant));

    }

    private static Map<Boolean, List<Emprunt>> partitionEmpruntByMontantOld(List<Compte> comptes, int montant) {
        Map<Boolean, List<Emprunt>> result = new HashMap<>();

        for(Compte compte : comptes) {
            for(Emprunt emprunt : compte.getEmprunts()) {
                int montantEmp = emprunt.getMontant();
                    if(!result.containsKey(montantEmp > montant)) {
                        List<Emprunt> list = new ArrayList<>();
                        list.add(emprunt);
                        result.put(montantEmp > montant, list);
                    } else {
                        List<Emprunt> list = result.get(montantEmp > montant);
                        list.add(emprunt);
                    }
            }
        }

        return result;
    }

    /**
     * Retourner un mapping du nombre de création de compte par année
     *
     * Key = Année
     * Value = nombre de compte
     *
     * @param comptes
     * @return
     */
    private static Map<Integer, Long> getNumberOfAccountByYears(List<Compte> comptes) {
        return
        comptes.stream()
               .collect(Collectors.groupingBy(Compte::getAnneeOuverture, Collectors.counting()));
    }

    /**
     * Utiliser le mapping ci-dessus pour retrouver l'année durant laquelle il-y-a eu le plus de création
     * de comptes
     *
     * @param numberOfLoansByYears
     * @return
     */
    private static int getYearWithMostAccountCreation(Map<Integer, Long> numberOfLoansByYears) {
        return
        numberOfLoansByYears.entrySet()
                            .stream()
                            .max(Map.Entry.comparingByValue())
                            .get()
                            .getKey();
    }

    /**
     * Retourner le nombre de montants distincts d'emprunts sur les comptes
     *
     * @param comptes
     * @return
     */
    private static Long getNumberOfDistinctAmountOfLoans(List<Compte> comptes) {
        return
        comptes.stream()
               .flatMap(compte -> compte.getEmprunts().stream())
               .map(Emprunt::getMontant)
               .distinct()
               .count();
    }

    /**
     * Retourner le montant le plus présent sur tous les emprunts de tous les comptes.
     *
     * @param comptes
     * @return
     */
    private static int getMostPresentAmount(List<Compte> comptes) {
        return
        comptes.stream()
               .flatMap(compte -> compte.getEmprunts().stream())
               .collect(Collectors.groupingBy(Emprunt::getMontant, Collectors.counting()))
               .entrySet()
               .stream()
               .max(Map.Entry.comparingByValue())
               .get()
               .getKey();
    }

    ////////////// Parsing des comptes ///////////////////////////////

    private static List<Compte> getAccountWithEmprunts() throws IOException {
        List<Compte> comptes = parseAccounts();
        List<Emprunt> emprunts = parseEmprunts();

        comptes.forEach(compte ->
                                compte.setEmprunts(emprunts.stream()
                                                           .filter(emp -> emp.getIdCompte() == compte.getId())
                                                           .collect(Collectors.toSet()))
        );

        return comptes;
    }

    private static List<Compte> parseAccounts() throws IOException {
        return Files.lines(Paths.get("files/comptes.txt"))
                    .map(Main::getAccountFromLine)
                    .collect(Collectors.toList());
    }

    private static List<Emprunt> parseEmprunts() throws IOException {
        return Files.lines(Paths.get("files/emprunts.txt"))
                    .map(Main::getEmpruntFromLine)
                    .collect(Collectors.toList());
    }

    private static Compte getAccountFromLine(String line) {
        String[] compte = line.split(";");

        return new Compte(Integer.valueOf(compte[0]),
                          compte[1],
                          Integer.valueOf(compte[2]));
    }

    private static Emprunt getEmpruntFromLine(String line) {
        String[] emprunt = line.split(";");

        return new Emprunt(Integer.valueOf(emprunt[0]),
                           Integer.valueOf(emprunt[1]),
                           Integer.valueOf(emprunt[2]),
                           Integer.valueOf(emprunt[3]));
    }

}