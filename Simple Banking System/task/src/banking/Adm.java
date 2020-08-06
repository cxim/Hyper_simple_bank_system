package banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Adm extends Bank {
    int balance;
    Voice voice;
    Statement statement;
    String cardName;
    String balanceStr;

    void  addInCome(String newBalance) throws SQLException {
        statement.executeUpdate("Update card SET balance = balance + " + newBalance + " WHERE number = " + cardName + ";");
        ResultSet accountsBank = statement.executeQuery("SELECT balance FROM card WHERE number = " + cardName);
        balanceStr = accountsBank.getString("balance");
    }

    void getBalance() throws SQLException {
        ResultSet accountsBank = statement.executeQuery("SELECT balance FROM card WHERE number = " + cardName);
        balanceStr = accountsBank.getString("balance");
        System.out.println("\nBalance: " + balanceStr);
    }

    void closeAcc() throws SQLException {
        statement.executeUpdate("delete from card where number =" + cardName + ";");
    }

    boolean algLuhn(String target) {
        int[] arr = new int[16];
        for (int i = 0; i < 16; i++) {
            try {
                arr[i] = Integer.parseInt(target.charAt(i) + "");
            } catch (Exception e) {
                return false;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                arr[i] = arr[i] * 2;
            } else {
                arr[i] = arr[i];
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 9) {
                arr[i] = arr[i] - 9;
            }
        }
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res = res + arr[i];
        }
        return res % 10 == 0;
    }

    boolean checkAccount(String targetAcc) {
        if (targetAcc.length() != 16) {
            System.out.println("Probably you made mistake in the card number. Please try again!\n");
            return false;
        } else if (!algLuhn(targetAcc)) {
            System.out.println("Probably you made mistake in the card number. Please try again!\n");
            return false;
        }
        return true;
    }

    void doTransfer() {
        System.out.println("\nTransfer");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card number:");
        String targetAcc = scanner.nextLine();
        if(checkAccount(targetAcc)) {
            try {
                ResultSet accountsBank = statement.executeQuery("select id from card where number = "
                                        + targetAcc + ";");
                System.out.println("Enter how much money you want to transfer:");
                try {
                    int money = scanner.nextInt();
                    ResultSet thisAccMoney = statement.executeQuery("select balance from card where number = "
                            + cardName + ";");
                    int thisMoney = thisAccMoney.getInt("balance");
                    if (money > thisMoney) {
                        System.out.println("Not enough money!\n");
                    } else {
                        statement.executeUpdate("Update card SET balance = balance + " + money
                                + " WHERE number = " + targetAcc + ";");
                        statement.executeUpdate("Update card SET balance = balance - " + money
                                + " WHERE number = " + cardName + ";");
                        System.out.println("tyt bydet bablo\n");
                    }
                } catch (Exception e) {
                    System.out.println("Its not money!\n");
                }
            } catch (SQLException e) {
                System.out.println("Such a card does not exist.\n");
            }
        }
    }

    void choiseOperationInAdm(Statement statement, String cardName) throws SQLException {
        boolean flag = false;
        setStatementCardName(statement, cardName);
        while (!flag) {
        voice = new Voice();
        voice.accMess();
        Scanner scanner = new Scanner(System.in);
        String operation = scanner.nextLine();
        if (operation.length() > 1 || operation.matches("[^0-5]")) {
            System.out.println("\nBad Choise, try again\n");
        }
        switch (operation) {
             case "0":
                 System.out.println("\nBye!");
                 System.exit(0);
             case "1":
                 getBalance();
                 break;
             case "2":
                 System.out.println("\nEnter income:");
                 addInCome(scanner.nextLine());
                 System.out.println("Income was added!\n");
                 break;
             case "3":
                 doTransfer();
                 break;
             case "4":
                 closeAcc();
                 System.out.println("\nThe account has been closed!\n");
                 flag = true;
                 break;
             case "5":
                 System.out.println("\nYou have successfully logged out!\n");
                 flag = true;
                 break;
            }
        }
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatementCardName(Statement statement, String cardName) {
        this.statement = statement;
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

}
