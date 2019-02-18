package com.cg.payroll.client;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.services.PayrollServices;
import com.cg.payroll.services.PayrollServicesImpl;

public class MainClass {
	public static void main(String[] args) {
			PayrollServices services = new PayrollServicesImpl();
			Scanner sc = new Scanner(System.in);			
			System.out.println("******************Payroll********************");
			char ch='y';
			while(ch=='y'||ch=='Y') {
				System.out.println("Enter your choice:\n1. Save Associate\n2. Get Associate Details\n3. Get All Associate Details"
						+ "\n4.Calculate Net Salary");
				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					System.out.println("Enter First name of associate: ");
					String firstName = sc.next();
					System.out.println("Enter Last name of associate: ");
					String lastName = sc.next();
					System.out.println("Enter Email Id of associate: ");
					String emailId = sc.next();
					System.out.println("Enter Department of associate: ");
					String department = sc.next();
					System.out.println("Enter Designation of associate: ");
					String designation = sc.next();
					System.out.println("Enter PAN number of associate:  ");
					String pancard = sc.next();
					System.out.println("Enter Yearly Investment: ");
					int yearlyInvestmentUnder80C = sc.nextInt();
					System.out.println("Enter Basic salary: ");
					int basicSalary = sc.nextInt();
					System.out.println("Enter EPF: ");
					int epf = sc.nextInt();
					System.out.println("Enter Company PF: ");
					int companyPf = sc.nextInt();
					System.out.println("Enter Account number: ");
					int accountNumber = sc.nextInt();
					System.out.println("Enter Bank name: ");
					String bankName = sc.next();
					System.out.println("Enter IFSC Code: ");
					String ifscCode = sc.next();
					int associateId=services.acceptAssociateDetails(firstName, lastName, emailId, department, designation, pancard, yearlyInvestmentUnder80C, basicSalary, epf, companyPf,accountNumber, bankName, ifscCode);
					System.out.println("Associate Id: "+associateId);
					break;
				case 2: 
					System.out.println("Enter associate id to find details: ");
					int id = sc.nextInt();
					Associate associateDetails = services.getAssociateDetails(id);
					System.out.println(associateDetails);
					break;
				case 3:
					List<Associate> allAssociateDetails = services.getAllAssociatesDetails();		
					System.out.println(allAssociateDetails);
					break;
				case 4:
					System.out.println("Enter associate id to calculate net salary: ");
					id = sc.nextInt();
					int netSalary = services.calculateNetSalary(id);
					System.out.println("Net Salary = "+netSalary);
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
