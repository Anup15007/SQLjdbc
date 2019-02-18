package com.cg.banking.client;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import com.cg.banking.beans.Account;
import com.cg.banking.services.BankingServices;
import com.cg.banking.services.BankingServicesImpl;

public class MainClass {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		BankingServices bankingServices = new BankingServicesImpl();
		System.out.println("******************BANKING SYSTEM********************");
		char ch='y';
		while(ch=='y'||ch=='Y') {
			System.out.println("Enter your choice:\n1. Open Account\n2. Deposit"
					+ "\n3. Withdraw"+"\n4. Get Account Details"+"\n5. Get All Account Details"
					+"\n6. Fund Transfer"+"\n7. Get Transactions");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter your Account type:\n1. Savings \n2. Current");
				int in = sc.nextInt();
				System.out.println("Enter initial balance:");
				int initBalance = sc.nextInt();
				System.out.println("Account created : KINDLY NOTE YOUR CREDENTIALS");
				if(in==1)
					System.out.println(bankingServices.openAccount("Savings" , initBalance));
				else
					System.out.println(bankingServices.openAccount("Current" , initBalance));
				 break;
			case 2:
				System.out.println("****DEPOSIT MONEY IN ACCOUNT****");
				System.out.println("Enter your account number");
				long accNo = sc.nextLong();
				System.out.println("Enter the amount you want to deposit");
				float depAmount = sc.nextFloat();
				float newAmount = bankingServices.depositAmount(accNo, depAmount);
				System.out.println("Transaction successful \nUpdated Balance  :" + newAmount);
				break;
			case 3:
				 System.out.println("****WITHDRAW MONEY FROM ACCOUNT****");
				 System.out.println("Enter your account number");
				 accNo = sc.nextLong();
				 System.out.println("Enter the amount you want to Withdraw");
				 float withAmount = sc.nextFloat();
				 System.out.println("Enter your pin number");
				 int pinNo = sc.nextInt();
				 newAmount = bankingServices.withdrawAmount(accNo, withAmount, pinNo);
				 System.out.println("Transaction successful \nUpdated Balance" + newAmount);
				 break;
			case 4: 
				System.out.println("Enter Account number to find details: ");
				int accountNo = sc.nextInt();
				Account accountDetails = bankingServices.getAccountDetails(accountNo);
				System.out.println(accountDetails);
				break;
			case 5:
				List<Account> allAccountDetails = bankingServices.getAllAccountsDetails();		
				System.out.println(allAccountDetails);
				break;
			case 6:
				 System.out.println("*****FUND TRANSFER*****");
				 System.out.println("Enter your account number");
				 long accountNoFrom = sc.nextLong();
				 System.out.println("Enter amount you want to transfer");
				 float transferAmount = sc.nextFloat();
				 System.out.println("Enter account number to transfer");
				 long accountNoTo = sc.nextLong();
				 System.out.println("Enter your pin number");
				 int pinNumber = sc.nextInt();
				 boolean success = bankingServices.fundTransfer(accountNoTo, accountNoFrom, transferAmount, pinNumber);
				 if(success)
					 System.out.println("Your transfer was successful ");
				 else
					 System.out.println("NOT SUCCESSFUL");
				 break;
			case 7:
				 System.out.println("******TRANSACTIONS******");
				 System.out.println("Enter your account number");
				 accNo = sc.nextLong();
				 System.out.println("Enter your pin");
				 pinNo = sc.nextInt();
				 if(bankingServices.getAccountDetails(accNo).getPinNumber()==pinNo)
					 System.out.println(Arrays.asList((bankingServices.getAccountDetails(accNo).getTransactions())));
				 else
					 System.out.println("WRONG PIN NUMBER");
				 break;
			default:
				System.out.println("Please enter a valid number");
				break;
		}
			System.out.println("Do you want to continue(y/n): ");
			ch = sc.next().charAt(0);
		}
	}
}
