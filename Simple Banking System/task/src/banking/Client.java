package banking;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client extends Adm {
    HashMap<String, Integer> user;
    String numAcc;
    int pinAcc;
    Voice voice;
    Statement statement;
    int id;

    public void setStatement(Statement statement, HashMap<String, Integer> user) {
        this.statement = statement;
        this.user = user;
    }

    public HashMap<String, Integer> getUser() {
        return user;
    }

    public void setNumAcc(String numAcc) {
        this.numAcc = numAcc;
    }

    public void setPinAcc(int pinAcc) {
        this.pinAcc = pinAcc;
    }

    double checkAccExist() {
        StringBuilder str = new StringBuilder();
        try (ResultSet accountsBank = statement.executeQuery("select number, id from card  order by id desc limit 0,1;")) {
//            String cardName = accountsBank.getString("number");
            str.append(accountsBank.getString("number"));
            id = accountsBank.getInt("id");

            str.deleteCharAt(0);
        } catch (SQLException throwables) {
            id = 0;
            return 2111123;
        }
        return Double.parseDouble(str.toString());
    }

    public void operationOne() throws SQLException {
        double check = checkAccExist();
        createrAccPin(this, check);
        id++;
        statement.executeUpdate("INSERT INTO card VALUES " +
                "(" + id + ", " + numAcc + ", " + pinAcc + ", + " + 0 + ")" );
        voice.cardReady(numAcc, pinAcc);
    }

    public void operationTwo(Scanner scanner) throws SQLException {
        boolean flag = false;
        while (!flag) {
            System.out.println("\nEnter your card number:");
            String accTmp = scanner.nextLine();
            System.out.println("Enter your PIN:");
            String pinTmp = scanner.nextLine();

            try (ResultSet accountsBank = statement.executeQuery("SELECT * FROM card")) {
                while (accountsBank.next() && !flag) {
                    // Retrieve column values
                    String cardName = accountsBank.getString("number");
                    String cardPin = accountsBank.getString("pin");
                    if (cardName.equals(accTmp) && cardPin.equals(pinTmp)) {
                        System.out.println("\nYou have successfully logged in!");
                        choiseOperationInAdm(statement, cardName);
                        flag = true;
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (!flag) {
                System.out.println("\nWrong card number or PIN!\n");
                action();
            }
        }
        action();
    }

    public void action() throws SQLException {
        voice = new Voice();
        voice.hello();

        Scanner sc = new Scanner(System.in);
        String action = null;
        action = sc.nextLine();

        if (action.length() > 1 || action.matches("[^0-2]")) {
            System.out.println("Bad Choise, try again");
            action();
        } else {
            switch (action) {
                case "0":
                    System.out.println("Bye!");
                    sc.close();
                    System.exit(0);
                case "1":
                    operationOne();
                    action();
                    break;
                case "2":
                    operationTwo(sc);
                    break;
                default:
                    System.out.println("Some error");
                    break;
            }
        }
    }
}
