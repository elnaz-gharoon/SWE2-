package com.elnaz.Application;

import com.elnaz.Application.Data.Enitites.Account;
import com.elnaz.Application.Services.Implementations.AccountService;
import com.elnaz.Application.Services.Implementations.PasswordGeneratorService;
import com.elnaz.Application.Services.Implementations.SecureStringService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class MainApplication {
    private static AccountService accountService;
    private static SecureStringService secureStringService;
    private static PasswordGeneratorService passwordGeneratorService;

    public static void main(String[] args) {
        var context = SpringApplication.run(MainApplication.class, args);

        accountService = context.getBean(AccountService.class);
        secureStringService = context.getBean(SecureStringService.class);
        passwordGeneratorService = context.getBean(PasswordGeneratorService.class);

        // Clean up hooks
        Runtime.getRuntime().addShutdownHook(new Thread(context::close));

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            option = mainMenu(scanner);
            scanner.nextLine();
            try {
                switch (option) {
                    case 1:
                        System.out.println(">>>>> Create A New Account <<<<<");
                        createAccount(scanner);
                        System.out.println("New account has been created...");
                        break;
                    case 2:
                        System.out.println(">>>>> All Accounts List! <<<<<");
                        viewAllAccounts(scanner);
                        break;
                    case 3:
                        System.out.println(">>>>> Find Account By ID! <<<<<");
                        findAccountById(scanner);
                        break;
                    case 4:
                        System.out.println(">>>>> Update Account! <<<<<");
                        updateAccount(scanner);
                        break;
                    case 5:
                        System.out.println(">>>>> Delete Account! <<<<<");
                        deleteAccount(scanner);
                        break;
                    case 6:
                        System.out.println(">>>>> Generate A Secure Password! <<<<<");
                        generatePassword(scanner);
                        break;
                    case 7:
                        System.out.println(">>>>> Retrieve Given Encrypted Password! <<<<<");
                        retrieveSecurePassword(scanner);
                        break;
                    case 8:
                        System.out.println(">>>>> Update Password! <<<<<");
                        updatePassword(scanner);
                        break;
                    case 9:
                        System.out.println(">>>>> Show Password! <<<<<");
                        showPassword(scanner);
                        break;
                    case 10:
                        System.out.println("Thanks for the using.");
                        System.out.println("By Elnaz");
                        System.out.println(">>>>> Good bye! <<<<<");
                        break;
                    default:
                        System.out.println(">>>>> Invalid option!!! <<<<<");
                        break;
                }
            } catch (ExecutionException | InterruptedException e) {
                System.err.println("An error occurred: " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while (option != 10);

        scanner.close();
    }

    private static int mainMenu(Scanner scanner) {
        System.out.println("---------- Welcome to the Password Manager! ----------");
        System.out.println("1. Create Account");
        System.out.println("2. View All Account");
        System.out.println("3. Find Account By ID");
        System.out.println("4. Update Account");
        System.out.println("5. Delete Account");
        System.out.println("6. Generate Password");
        System.out.println("7. Retrieve Secure Password");
        System.out.println("8. Update Password");
        System.out.println("9. Show Password");
        System.out.println("10. Exit");
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }

    private static UUID getAndFormatAccountID(Scanner scanner) {
        System.out.print("Enter account ID: ");
        UUID accountId;
        try {
            accountId = UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Expected format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
            return null;
        }
        return accountId;
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter account name: ");
        String name = scanner.nextLine();
        System.out.print("Enter login: ");
        String login = scanner.nextLine();
        System.out.print("Enter password: ");
        String encryptedPassword = secureStringService.createSecureString(scanner.nextLine());

        accountService.createAccount(new Account(UUID.randomUUID(), name, login, encryptedPassword));
    }

    private static void findAccountById(Scanner scanner) {
        UUID accountId = getAndFormatAccountID(scanner);

        if (accountId != null) {
            Optional<Account> accountOpt = accountService.getAccountById(accountId);
            if (accountOpt.isPresent()) {
                Account account = accountOpt.get();
                System.out.printf(
                        "Account ID: %s\nName: %s\nLogin: %s\nPassword: %s\n",
                        account.getId(),
                        account.getName(),
                        account.getLogin(),
                        account.getPassword()
                );
            } else {
                System.out.println("Account not found for ID: " + accountId);
            }
        }
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    private static void viewAllAccounts(Scanner scanner) throws ExecutionException, InterruptedException {
        List<Account> accounts = accountService.getAllAccounts();
        System.out.printf("| %-4s | %-36s | %-15s | %-20s | %-24s |\n", "No.", "ID", "Name", "Login", "Password");
        AtomicInteger rowNo = new AtomicInteger(1);
        accounts.forEach(account ->
            System.out.printf(
                    "| %-4d | %-36s | %-15s | %-20s | %-24s |\n",
                    rowNo.getAndIncrement(),
                    account.getId(),
                    account.getName(),
                    account.getLogin(),
                    account.getPassword()
            )
        );
        System.out.println("--------------------------------------");
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    private static void updateAccount(Scanner scanner) {
        UUID accountId = getAndFormatAccountID(scanner);

        if (accountId != null) {
            Optional<Account> currentAccount = accountService.getAccountById(accountId);
            if (currentAccount.isPresent()) {
                Account existingAccount = currentAccount.get();
                System.out.printf("Enter new name (%s): ", existingAccount.getName());
                String newName = scanner.nextLine();
                System.out.printf("Enter new login (%s): ", existingAccount.getLogin());
                String newLogin = scanner.nextLine();
                System.out.print("Enter new password: ");
                String encryptedPassword = secureStringService.createSecureString(scanner.nextLine());

                accountService.updateAccount(new Account(accountId, newName, newLogin, encryptedPassword));
                System.out.printf("Account %s has been updated successfully...\n", accountId);
            } else {
                System.out.println("Account not found for ID: " + accountId);
            }
        }
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    private static void deleteAccount(Scanner scanner) throws ExecutionException, InterruptedException {
        UUID accountId = getAndFormatAccountID(scanner);
        if (accountId != null) {
            Optional<Account> currentAccount = accountService.getAccountById(accountId);
            if (currentAccount.isPresent()) {
                accountService.deleteAccount(accountId);
                System.out.printf("Account %s has been deleted successfully...\n", accountId);
            } else {
                System.out.println("Account not found for ID: " + accountId);
            }
        }
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    private static void generatePassword(Scanner scanner) {
        System.out.print("Enter password length: ");
        int length = scanner.nextInt();
        scanner.nextLine();

        // Assuming you have a PasswordGeneratorService
        String generatedPassword = passwordGeneratorService.generatePassword(length);
        System.out.println("Generated password: " + generatedPassword);
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    private static void retrieveSecurePassword(Scanner scanner) {
        System.out.print("Sicheres Passwort: ");
        String securePassword = scanner.nextLine();
        String password = secureStringService.retrieveString(securePassword);
        System.out.println("Entschlüsseltes Passwort: " + password);
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    private static void updatePassword(Scanner scanner) throws ExecutionException, InterruptedException {
        UUID accountId = getAndFormatAccountID(scanner);
        if (accountId != null) {
            Optional<Account> currentAccount = accountService.getAccountById(accountId);
            if (currentAccount.isPresent()) {
                System.out.print("Neues Passwort: ");
                String newPassword = scanner.nextLine();

                Account existingAccount = currentAccount.get();

                accountService.updateAccount(
                        new Account(
                                accountId,
                                existingAccount.getName(),
                                existingAccount.getLogin(),
                                secureStringService.createSecureString(newPassword)
                        )
                );
                System.out.printf("Password for the account %s has been updated successfully...\n", accountId);
            } else {
                System.out.println("Account not found for ID: " + accountId);
            }
        }
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    private static void showPassword(Scanner scanner) throws ExecutionException, InterruptedException {
        UUID accountId = getAndFormatAccountID(scanner);
        if (accountId != null) {
            Optional<Account> currentAccount = accountService.getAccountById(accountId);
            if (currentAccount.isPresent()) {
                Account existingAccount = currentAccount.get();
                System.out.println("Account ID: " + existingAccount.getId());
                System.out.println("Encrypted Password: " + existingAccount.getPassword());
                System.out.println("Decrypted Password: " + secureStringService.retrieveString(existingAccount.getPassword()));
                System.out.println("Press any key to continue.");
                scanner.nextLine();
            }
        }
    }
}
