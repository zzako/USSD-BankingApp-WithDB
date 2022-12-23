package com;
public class Main {

	public static void main(String[] args) {
		MainMenu menu = new MainMenu();
		menu.connectToDatabase();
		menu.start();

	}

}
