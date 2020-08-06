package banking;

public class Voice implements  WhatToDo{
	@Override
	public void hello() {
		System.out.println("1. Create an account\n" +
				"2. Log into account\n" +
				"0. Exit");
	}

	@Override
	public void accMess() {
		System.out.println("\n1. Balance\n" +
				"2. Add income\n" +
				"3. Do transfer\n" +
				"4. Close account\n" +
				"5. Log out\n" +
				"0. Exit");
	}

	@Override
	public void cardReady(String numAcc, int pinAcc) {
		System.out.println("\nYour card has been created\n" +
				"Your card number:\n" +
				numAcc + "\n" +
				"Your card PIN:\n" +
				pinAcc + "\n");
	}
}
