package banking;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank {

    double num;

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public int getNum(int[] arr1) {
        int[] arr = new int[16];
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                arr[i] = arr1[i] * 2;
            } else {
                arr[i] = arr1[i];
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
        return res;
    }

    public int fillArray(int[] array, double num) {
        array[0] = 4;
        String[] arr = String.valueOf(num).split("\\.");

        for (int i = 1; i < 16; i++) {
            if (i < 6) {
                array[i] = 0;
            } else if (i > 5 && i < 16 - arr[0].length()) {
                array[i] = 0;
            } else {
                array[i] = Integer.parseInt(arr[0].charAt(i + arr[0].length() - 16) + "");               //[i + arr]
            }
        }
        return getNum(array);
    }

    public boolean algoritmLuhn(StringBuilder stringBuilder, double num) {
        int[] arrayNums = new int[16];
        int res = fillArray(arrayNums, num);

        if (res % 10 == 0) {
            for (int i = 0; i < 16; i++) {
                stringBuilder.append(arrayNums[i]);
            }
        }
        return res % 10 == 0;
    }

    public void createrAccPin(Client client, double n) {
        if (num == 0) {
            setNum(n + 2);
        }
        StringBuilder stringTmpAcc = new StringBuilder();
        while (true) {
            if (algoritmLuhn(stringTmpAcc, num)) {
                break;
            }
            num += 2;
        }
        num += 100;
        setNum(num);
        Random r = new Random(client.getUser().size() + 100);
        client.numAcc = stringTmpAcc.toString();
        client.pinAcc = r.nextInt(9999);
        client.getUser().put(client.numAcc, client.pinAcc);
    }
}
