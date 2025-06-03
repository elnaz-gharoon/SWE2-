package com.elnaz.Application;

import com.elnaz.Application.Data.Enitites.Account;
import com.elnaz.Application.Data.Enitites.Category;
import com.elnaz.Application.Services.Implementations.*;
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
    private static UserService userService;
    private static CategoryService categoryService;

    public static void main(String[] args) {
        var context = SpringApplication.run(MainApplication.class, args);

        accountService = context.getBean(AccountService.class);
        secureStringService = context.getBean(SecureStringService.class);
        passwordGeneratorService = context.getBean(PasswordGeneratorService.class);
        userService = context.getBean(UserService.class);
        categoryService = context.getBean(CategoryService.class);

        // Clean up hooks
        Runtime.getRuntime().addShutdownHook(new Thread(context::close));

        /*
         * Main control loop of the password manager application.
         * This method displays a menu-driven interface that allows the user to:
         * - Create and manage accounts
         * - Generate and retrieve secure passwords
         * - Update or delete stored credentials
         * The loop handles user input safely and runs until the user chooses to exit.
         */

        Scanner scanner = new Scanner(System.in);
        int option;


        do {
            option = mainMenu(scanner);
            scanner.nextLine();
            try {
                switch (option) {
                    case 1:
                        System.out.println(">>>>> Create A New Account <<<<<");
                        System.out.println("Please Enter your name, username and password");
                        createAccount(scanner);
                        System.out.println("New account has been created");
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
                        System.out.println(">>>>> Enter Category <<<<<");
                        manageCategories(scanner);
                        break;
                    case 11:
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
        } while (option != 11);

        scanner.close();
    }



    private static int mainMenu(Scanner scanner) {
        System.out.println("--------- Welcome to the Password Manager! --------");
        System.out.println("1. Create Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Find Account By ID");
        System.out.println("4. Update Account");
        System.out.println("5. Delete Account");
        System.out.println("6. Generate Password");
        System.out.println("7. Retrieve Secure Password");
        System.out.println("8. Update Password");
        System.out.println("9. Show Password");
        System.out.println("10. Add Category");
        System.out.println("11. Exit");
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }
    /**
     * Prompts the user to enter an account UUID and attempts to parse it.
     * If the input is not in a valid UUID format, an error message is shown and null is returned.
     * This method ensures that only properly formatted UUIDs are accepted for further processing.
     *
     * @param scanner Scanner object used to read user input
     * @return A valid UUID object if the input is correctly formatted, otherwise null
     */


    private static UUID getAndFormatAccountID(Scanner scanner) {
        System.out.print("Enter account UUID: ");
        UUID accountId;
        try {
            accountId = UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Expected format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
            return null;
        }
        return accountId;
    }

    /**
     * Collects user input to create a new account.
     * The password is securely encrypted before being stored.
     * A new Account object is created with a randomly generated UUID and passed to the account service for persistence.
     * @param scanner Scanner object used to read input from the user
     */
    private static void createAccount(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();


        String login;
        while (true) {
            System.out.print("Please Enter username: ");
            login = scanner.nextLine();

            Optional<Account> user = accountService.getAccountByLogin(login);
            if (user.isPresent()) {
                System.out.printf("The given username (%s) already taken! Please try again.\n", login);
            } else {
                break;
            }
        }
        System.out.print("Enter password: ");
        String encryptedPassword = secureStringService.createSecureString(scanner.nextLine());

        accountService.createAccount(new Account(UUID.randomUUID(), name, login, encryptedPassword));
    }
    /**
     * Searches for and displays an account based on a user-provided UUID.
     * The method prompts the user to enter an account ID, validates its format,
     * and attempts to retrieve the corresponding account from the account service.
     * If found, the account details are printed; otherwise, a not-found message is shown.
     * Waits for user input before continuing to ensure the output is readable.
     *
     * @param scanner Scanner object used to read user input
     */
    private static void findAccountById(Scanner scanner) {
        UUID accountId = getAndFormatAccountID(scanner);

        if (accountId != null) {
            Optional<Account> accountOpt = accountService.getAccountById(accountId);
            if (accountOpt.isPresent()) {
                Account account = accountOpt.get();
                System.out.printf(
                        "Account ID: %s\nName: %s\nUsername: %s\nPassword: %s\n",
                        account.getId(),
                        account.getName(),
                        account.getLogin(),
                        account.getPassword()
                );
            } else {
                System.out.println("Account not found for ID: " + accountId);
            }
        }
        System.out.println("--------------------------------------");
        System.out.println("Press any key to continue.");
        System.out.println("--------------------------------------");
        scanner.nextLine();
    }

    /**
     * Retrieves and displays a list of all stored accounts in a formatted table.
     * Each account is printed with a row number, ID, name, login, and password.
     * The method waits for user input before proceeding to ensure the user can view the output.
     * May throw ExecutionException or InterruptedException depending on the underlying data retrieval process.
     * @param scanner Scanner object used to pause for user input after displaying the accounts
     * @throws ExecutionException if retrieving accounts fails due to concurrent processing
     * @throws InterruptedException if the operation is interrupted during execution
     */

    private static void viewAllAccounts(Scanner scanner) throws ExecutionException, InterruptedException {
        List<Account> accounts = accountService.getAllAccounts();
        System.out.printf("| %-4s | %-36s | %-15s | %-20s | %-24s |\n", "No.", "ID", "Name", "Username", "Password");
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
        System.out.println("--------------------------------------");
        scanner.nextLine();
    }

    /**
     * Updates an existing account with new information provided by the user.
     * The method prompts the user to enter an account UUID and verifies its existence.
     * If the account is found, the user is asked to input a new name, username, and password.
     * The new password is securely encrypted before the account is updated in the system.
     * Displays a confirmation message after a successful update or an error if the account is not found.
     * Waits for user input before returning to ensure the output is readable.
     *
     * @param scanner Scanner object used to read input from the user
     */

    private static void updateAccount(Scanner scanner) {
        UUID accountId = getAndFormatAccountID(scanner);

        if (accountId != null) {
            Optional<Account> currentAccount = accountService.getAccountById(accountId);
            if (currentAccount.isPresent()) {
                Account existingAccount = currentAccount.get();
                System.out.printf("Enter new name (%s): ", existingAccount.getName());
                String newName = scanner.nextLine();
                System.out.printf("Enter new username (%s): ", existingAccount.getLogin());
                String newLogin = scanner.nextLine();
                while (true) {
                    System.out.print("Please Enter username: ");
                    newLogin = scanner.nextLine();

                    Optional<Account> user = accountService.getAccountByLogin(newLogin);
                    if (user.isPresent()) {
                        System.out.printf("The given username (%s) already taken! Please try again.\n", newLogin);
                    } else {
                        break;
                    }
                }
                System.out.print("Enter new password: ");
                String encryptedPassword = secureStringService.createSecureString(scanner.nextLine());

                accountService.updateAccount(new Account(accountId, newName, newLogin, encryptedPassword));
                System.out.printf("Account %s has been updated successfully...\n", accountId);
            } else {
                System.out.println("Account not found for ID: " + accountId);
            }
        }
        System.out.println("--------------------------------------");
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }
    /**
     * Deletes an existing account identified by a UUID provided by the user.
     * The method prompts the user for an account ID, verifies if the account exists,
     * and deletes it using the account service if found. Otherwise, it displays an error message.
     * Displays a confirmation upon successful deletion.
     * May throw ExecutionException or InterruptedException depending on service behavior.
     * @param scanner Scanner object used to read user input
     * @throws ExecutionException if the delete operation fails during asynchronous execution
     * @throws InterruptedException if the operation is interrupted
     */

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
        System.out.println("--------------------------------------");
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    /**
     * Generates a secure random password based on a user-defined length.
     * The method prompts the user to specify the desired password length,
     * calls the password generator service to create a secure password,
     * Waits for user input before continuing to ensure the output is visible.
     *
     * @param scanner Scanner object used to read user input
     */


    private static void generatePassword(Scanner scanner) {
        System.out.print("Enter password length: ");
        int length = scanner.nextInt();
        scanner.nextLine();

        // Assuming you have a PasswordGeneratorService
        String generatedPassword = passwordGeneratorService.generatePassword(length);
        System.out.println("Generated password: " + generatedPassword);
        System.out.println("--------------------------------------");
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }

    /**
     * Decrypts and displays a previously encrypted (secure) password.
     * The user is prompted to enter the encrypted password string,
     * which is then passed to the secure string service for decryption.
     * Waits for user input before continuing to ensure the result is visible.
     *
     * @param scanner Scanner object used to read user input
     */

    private static void retrieveSecurePassword(Scanner scanner) {
        System.out.print("Sicheres Passwort: ");
        String securePassword = scanner.nextLine();
        String password = secureStringService.retrieveString(securePassword);
        System.out.println("Entschlüsseltes Passwort: " + password);
        System.out.println("--------------------------------------");
        System.out.println("Press any key to continue.");
        scanner.nextLine();
    }





    private static void manageCategories(Scanner scanner) {
        System.out.println("Categories currently in DB:");
        List<Category> categories = categoryService.listAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            categories.forEach(cat -> System.out.println("- " + cat.getName()));
        }

        System.out.print("Do you want to add a new category? (y/n): ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y")) {
            System.out.print("Enter new category name: ");
            String newCategoryName = scanner.nextLine();

            categoryService.createCategory(newCategoryName);
            System.out.println("Category '" + newCategoryName + "' added successfully.");
        }
        System.out.println("--------------------------------------");
        System.out.println("Press any key to continue.");
        scanner.nextLine();

    }
    /**
     * Updates the password of an existing account specified by a user-provided UUID.
     * After verifying the account exists, the method prompts the user for a new password,
     * securely encrypts it using the secure string service, and updates the account
     * while preserving the existing name and login credentials.
     * Waits for user input before continuing to ensure the user can read the output.
     *
     * @param scanner Scanner object used to read user input
     * @throws ExecutionException if the update operation fails during asynchronous processing
     * @throws InterruptedException if the thread is interrupted during execution
     */

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

    /**
     * Displays both the encrypted and decrypted password for a specific account identified by UUID.
     * The method prompts the user to enter the account ID, verifies its existence,
     * and then prints the account ID, the stored (encrypted) password, and the decrypted password.
     * Useful for administrators or users who need to verify stored credentials.
     * Waits for user input before returning to ensure the information is visible.
     *
     * @param scanner Scanner object used to read user input
     * @throws ExecutionException if retrieving the account fails during asynchronous execution
     * @throws InterruptedException if the operation is interrupted during processing
     */


    private static void showPassword(Scanner scanner) throws ExecutionException, InterruptedException {
        UUID accountId = getAndFormatAccountID(scanner);
        if (accountId != null) {
            Optional<Account> currentAccount = accountService.getAccountById(accountId);
            if (currentAccount.isPresent()) {
                Account existingAccount = currentAccount.get();
                System.out.println("Account ID: " + existingAccount.getId());
                System.out.println("Encrypted Password: " + existingAccount.getPassword());
                System.out.println("Decrypted Password: " + secureStringService.retrieveString(existingAccount.getPassword()));
                System.out.println("--------------------------------------");
                System.out.println("Press any key to continue.");
                scanner.nextLine();
            }

        }

    }
}
