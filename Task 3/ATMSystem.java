import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(12000.0);
        ATM atm = new ATM(userAccount);
        atm.start();
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ATM {
    private final BankAccount account;
    private final Scanner scanner;

    public ATM(BankAccount account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println(" Welcome to SecureATM ");
        
        while (true) {
            showMenu();
            int choice = getIntegerInput("Enter option (1-4): ");
            
            switch (choice) {
                case 1 -> handleWithdrawal();
                case 2 -> handleDeposit();
                case 3 -> showBalance();
                case 4 -> {
                    System.out.println("Thank you for banking with us!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Try again.");
            }
        }
    }

    private void handleWithdrawal() {
        double amount = getDoubleInput("Enter withdrawal amount: ");
        
        if (amount <= 0) {
            System.out.println("Amount must be positive!");
            return;
        }
        
        if (account.withdraw(amount)) {
            System.out.println("Collect your cash.");
            System.out.printf("New Balance: ₹%.2f%n", account.getBalance());
        } else {
            System.out.println("Failed! Insufficient balance or invalid amount.");
        }
    }

    private void handleDeposit() {
        double amount = getDoubleInput("Enter deposit amount: ");
        
        if (amount <= 0) {
            System.out.println("Amount must be positive!");
            return;
        }
        
        if (account.deposit(amount)) {
            System.out.println("Deposit successful!");
            System.out.printf("New Balance: ₹%.2f%n", account.getBalance());
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    private void showBalance() {
        System.out.printf("Current Balance: ₹%.2f%n", account.getBalance());
    }

    private void showMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Withdraw Cash");
        System.out.println("2. Deposit Money");
        System.out.println("3. Check Balance");
        System.out.println("4. Exit");
    }

    private int getIntegerInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            double value = Double.parseDouble(scanner.nextLine());
            return Math.round(value * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}
