import Application.Services.Implementations.AccountService;
import Application.Services.Implementations.PasswordGeneratorService;
import Application.Services.Implementations.SecureStringService;
import Config.AppConfig;
import Data.Enitites.Account;
import Data.Repositories.AccountRepository;
import Exceptions.AccountNotFoundException;
import Exceptions.InvalidInputException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import java.util.Scanner;

public class MainApplication {
    private static AccountService accountService;
    private static SecureStringService secureStringService;

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Clean up hooks
        Runtime.getRuntime().addShutdownHook(new Thread(context::close));

        // Resolve beans from the DI container
        accountService = context.getBean(AccountService.class);
        secureStringService = context.getBean(SecureStringService.class);

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Example interaction
        System.out.println("Welcome to the Password Manager!");
        System.out.println("1. Create Account");
        System.out.println("2. View Account");
        System.out.println("3. Update Account");
        System.out.println("4. Delete Account");
        System.out.println("5. Generate Password");
        System.out.println("6. Retrieve Secure Password");
        System.out.println("7. Update Password");
        System.out.println("8. Show Password");
        System.out.println("Select an option:");

        int option = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        try {
            switch (option) {
                case 1:
                    createAccount(scanner);
                    break;
                case 2:
                    viewAccount(scanner);
                    break;
                case 3:
                    updateAccount(scanner);
                    break;
                case 4:
                    deleteAccount(scanner);
                    break;
                case 5:
                    generatePassword(scanner);
                    break;
                case 6:
                    retrieveSecurePassword(scanner);
                    break;
                case 7:
                    updatePassword(scanner);
                    break;
                case 8:
                    showPassword(scanner);
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        scanner.close();
    }

    private static void createAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.println("Enter account name:");
        String name = scanner.nextLine();
        System.out.println("Enter login:");
        String login = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        accountService.createAccountAsync(name, login, password)
                .thenAccept(account -> System.out.println("Account created: " + account))
                .get();
    }

    private static void viewAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.println("Enter account ID:");
        UUID accountId = UUID.fromString(scanner.nextLine());

        accountService.getAccountAsync(accountId)
                .thenAccept(account -> System.out.println("Account: " + account))
                .exceptionally(ex -> {
                    System.out.println("Account not found: " + ex.getMessage());
                    return null;
                })
                .get();
    }

    private static void updateAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.println("Enter account ID:");
        UUID updateAccountId = UUID.fromString(scanner.nextLine());
        System.out.println("Enter new name:");
        String newName = scanner.nextLine();
        System.out.println("Enter new login:");
        String newLogin = scanner.nextLine();
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();

        accountService.getAccountAsync(updateAccountId)
                .thenCompose(account -> {
                    account.setName(newName);
                    account.setLogin(newLogin);
                    account.setPassword(newPassword);
                    return accountService.updateAccountAsync(account);
                })
                .thenAccept(account -> System.out.println("Account updated: " + account))
                .exceptionally(ex -> {
                    System.out.println("Error updating account: " + ex.getMessage());
                    return null;
                })
                .get();
    }

    private static void deleteAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.println("Enter account ID:");
        UUID deleteAccountId = UUID.fromString(scanner.nextLine());

        accountService.deleteAccountAsync(deleteAccountId)
                .thenRun(() -> System.out.println("Account deleted"))
                .exceptionally(ex -> {
                    System.out.println("Error deleting account: " + ex.getMessage());
                    return null;
                })
                .get();
    }

    private static void generatePassword(Scanner scanner) {
        System.out.println("Enter password length:");
        int length = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Assuming you have a PasswordGeneratorService
        // String generatedPassword = passwordGeneratorService.generatePassword(length);
        // System.out.println("Generated password: " + generatedPassword);
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