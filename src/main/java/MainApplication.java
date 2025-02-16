import Application.Services.Implementations.AccountService;
import Application.Services.Implementations.PasswordGeneratorService;
import Application.Services.Implementations.SecureStringService;
import Data.Enitites.Account;
import Data.Repositories.AccountRepository;
import Exceptions.AccountNotFoundException;
import Exceptions.InvalidInputException;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import java.util.Scanner;

public class MainApplication {
    private static final AccountService accountService = new AccountService(new AccountRepository());
    private static final PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();
    private static final SecureStringService secureStringService = new SecureStringService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Passwort-Management-System");
            System.out.println("1. Account erstellen");
            System.out.println("2. Account anzeigen");
            System.out.println("3. Alle Accounts anzeigen");
            System.out.println("4. Account aktualisieren");
            System.out.println("5. Account löschen");
            System.out.println("6. Passwort generieren");
            System.out.println("7. Passwort sicher speichern");
            System.out.println("8. Sicheren Passwort anzeigen");
            System.out.println("9. Passwort ändern");
            System.out.println("10. Passwort anzeigen");
            System.out.println("0. Beenden");
            System.out.print("Wählen Sie eine Option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (option) {
                    case 1:
                        createAccount(scanner);
                        break;
                    case 2:
                        showAccount(scanner);
                        break;
                    case 3:
                        showAllAccounts();
                        break;
                    case 4:
                        updateAccount(scanner);
                        break;
                    case 5:
                        deleteAccount(scanner);
                        break;
                    case 6:
                        generatePassword(scanner);
                        break;
                    case 7:
                        storeSecurePassword(scanner);
                        break;
                    case 8:
                        retrieveSecurePassword(scanner);
                        break;
                    case 9:
                        updatePassword(scanner);
                        break;
                    case 10:
                        showPassword(scanner);
                        break;
                    case 0:
                        System.exit(0);
                    default:
                        System.out.println("Ungültige Option. Bitte versuchen Sie es erneut.");
                }
            } catch (InvalidInputException | AccountNotFoundException e) {
                System.out.println("Fehler: " + e.getMessage());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Passwort: ");
        String password = scanner.nextLine();
        accountService.createAccountAsync(name, login, password).get();
        System.out.println("Account erstellt.");
    }

    private static void showAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.print("Account-ID: ");
        UUID id = UUID.fromString(scanner.nextLine());
        System.out.println(accountService.getAccountAsync(id).get());
    }

    private static void showAllAccounts() throws ExecutionException, InterruptedException {
        accountService.getAccountsAsync().get().forEach(System.out::println);
    }

    private static void updateAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.print("Account-ID: ");
        UUID id = UUID.fromString(scanner.nextLine());
        System.out.print("Neuer Name: ");
        String name = scanner.nextLine();
        System.out.print("Neuer Login: ");
        String login = scanner.nextLine();
        System.out.print("Neues Passwort: ");
        String password = scanner.nextLine();
        accountService.updateAccountAsync(new Account(id, name, login, password)).get();
        System.out.println("Account aktualisiert.");
    }

    private static void deleteAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.print("Account-ID: ");
        UUID id = UUID.fromString(scanner.nextLine());
        accountService.deleteAccountAsync(id).get();
        System.out.println("Account gelöscht.");
    }

    private static void generatePassword(Scanner scanner) {
        System.out.print("Passwortlänge: ");
        int length = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        String password = passwordGeneratorService.generatePassword(length);
        System.out.println("Generiertes Passwort: " + password);
    }

    private static void storeSecurePassword(Scanner scanner) {
        System.out.print("Passwort: ");
        String password = scanner.nextLine();
        String securePassword = secureStringService.createSecureString(password);
        System.out.println("Sicheres Passwort: " + securePassword);
    }

    private static void retrieveSecurePassword(Scanner scanner) {
        System.out.print("Sicheres Passwort: ");
        String securePassword = scanner.nextLine();
        String password = secureStringService.retrieveString(securePassword);
        System.out.println("Entschlüsseltes Passwort: " + password);
    }

    private static void updatePassword(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.print("Account-ID: ");
        UUID id = UUID.fromString(scanner.nextLine());
        System.out.print("Neues Passwort: ");
        String newPassword = scanner.nextLine();
        accountService.updatePasswordAsync(id, newPassword).get();
        System.out.println("Passwort aktualisiert.");
    }

    private static void showPassword(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.print("Account-ID: ");
        UUID id = UUID.fromString(scanner.nextLine());
        String password = accountService.getPasswordAsync(id).get();
        System.out.println("Passwort: " + password);
    }
}