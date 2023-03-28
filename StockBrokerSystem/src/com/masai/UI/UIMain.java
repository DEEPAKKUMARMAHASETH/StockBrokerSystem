package com.masai.UI;

import java.util.Scanner;

public class UIMain {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		 boolean quit = false;
	        while (!quit) {
	            System.out.println("Enter your choice:");
	            System.out.println("1. Broker Login");
	            System.out.println("2. Customer Login");
	            System.out.println("3. Exit");

	            int choice = scanner.nextInt();
	            scanner.nextLine(); 

	            switch (choice) {
	                case 1:
	                    BrokerUI.brokerLogin(scanner);
	                    break;
	                case 2:
	                    CustomerUI.customerLogin(scanner);
	                    break;
	                case 3:
	                	System.out.println("Thanks for using Stock Broker System");
	                    quit = true;
	                    break;
	                default:
	                    System.out.println("Invalid choice");
	                    break;
	            }
	        }
	    }
	}
		
