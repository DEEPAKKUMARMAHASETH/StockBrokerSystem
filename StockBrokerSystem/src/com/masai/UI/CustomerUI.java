package com.masai.UI;

import java.util.Scanner;

import com.masai.DAO.CustomerDAO;
import com.masai.DAO.CustomerDAOImpl;
import com.masai.DTO.Customer;
import com.masai.DTO.CustomerImpl;
import com.masai.EXCEPTION.InvalidCredentialsException;
import com.masai.EXCEPTION.NoRecordFoundException;
import com.masai.EXCEPTION.SomethingWentWrongException;

public class CustomerUI {
	public static void customerLogin(Scanner scanner) {
		
		if(isCustomerLogged.isLogged) {
			try {
				showUserMenu(scanner);
			} catch (NoRecordFoundException e) {
				System.out.println(e.getMessage());
			}
		}else {
			int choice;

	        do {
	            System.out.println("Main Menu");
	            System.out.println("1. Sign Up");
	            System.out.println("2. Login");
	            System.out.println("3. Exit");
	            System.out.print("Enter your choice: ");
	            choice = scanner.nextInt();

	            switch (choice) {
	                case 1:
	                    signUp(scanner);
	                    break;
	                case 2:
	                    login(scanner);
	                    break;
	                case 3:
	                    System.out.println("Goodbye!");
	                    break;
	                default:
	                    System.out.println("Invalid choice!");
	            }
	        } while (choice != 3);
		}
		

	}
	
    private static void login(Scanner scanner) {
        System.out.println("Enter username");
        String username = scanner.next();
        System.out.println("Enter Password");
        String password = scanner.next();
        CustomerDAO daoLayer = new CustomerDAOImpl();
       try {
		Customer customer =  daoLayer.authenticateCustomer(username, password);
		System.out.println("You're logged in!!!");
		try {
			showUserMenu(scanner);
		} catch (NoRecordFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		} catch (InvalidCredentialsException e) {
			System.out.println(e.getMessage());
		}
    }
    private static void signUp(Scanner scanner) {
    	  System.out.println("Enter first name: ");
    	  String firstName = scanner.next();
    	  System.out.println("Enter last name: ");
    	  String lastName = scanner.next();
    	  System.out.println("Enter username: ");
    	  String username = scanner.next();
    	  System.out.println("Enter password: ");
    	  String password = scanner.next();
    	  System.out.println("Enter address: ");
    	  String address = scanner.next();
    	  System.out.println("Enter mobile number: ");
    	  String mobileNumber = scanner.next();
    	  System.out.println("Enter email: ");
    	  String email = scanner.next();
    	  System.out.println("Enter wallet balance: ");
    	  double walletBalance = scanner.nextDouble();
    	  System.out.println("Is active (1/0): ");
    	  int isActive = scanner.nextInt();
    	  Customer customer = new CustomerImpl( firstName,  lastName,  username,  mobileNumber,  address,
  				 email,  password,  walletBalance,  isActive);
    	  CustomerDAO daoLayer = new CustomerDAOImpl();
    	  try {
    		  daoLayer.addCustomer(customer);
    		  System.out.println("Customer Added Successfully!!");
    	  }catch(SomethingWentWrongException e) {
    		  System.out.println(e.getMessage());
    	  }
    }
    private static void logout() {
    	isCustomerLogged.isLogged= false;
    	System.out.println("You're Logged Out!!!!!");
    }
	
	 private static void showUserMenu(Scanner scanner) throws NoRecordFoundException {
	        int choice;

	        do {
	            System.out.println("User Menu");
	            System.out.println("1. View Stocks");
	            System.out.println("2. Buy/Sell Stocks");
	            System.out.println("3. View Transaction History");
	            System.out.println("4. Add/Withdraw Funds");
	            System.out.println("5. Logout");
	            System.out.print("Enter your choice: ");
	            choice = scanner.nextInt();

	            switch (choice) {
	                case 1:
	                	BrokerUI viewStock = new BrokerUI();
	                	viewStock.viewStock();
	                    break;
	                case 2:
	                    //buySellStocks();
	                    break;
	                case 3:
	                   // viewTransactionHistory();
	                    break;
	                case 4:
	                    //addWithdrawFunds();
	                    break;
	                case 5:
	                    logout();
	                    break;
	                default:
	                    System.out.println("Invalid choice!");
	            }
	        } while (choice != 5);

	    }
}
