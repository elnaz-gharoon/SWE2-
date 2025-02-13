import Application.Services.Implementations.AccountService;
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Passwort-Management-System");
            System.out.println("1. Account erstellen");
            System.out.println("2. Account anzeigen");
            System.out.println("3. Alle Accounts anzeigen");
            System.out.println("4. Account aktualisieren");
            System.out.println("5. Account löschen");
            System.out.println("0. Beenden");
            System.out.print("Wählen Sie eine Option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

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
}