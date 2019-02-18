package com.cg.banking.test;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServicesDownException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.services.BankingServicesImpl;
import com.cg.banking.util.BankingDBUtil;

public class BankingSystemTest {
	
	public static BankingServicesImpl services;
	
	@BeforeClass
	public static void setUpTestEnv() {
		services = new BankingServicesImpl();
	}
	
	@Before
	public void setUpTestData() {
		HashMap<Integer,Transaction> transactions1 = new HashMap<Integer, Transaction>();
		Account account1 = new Account(101, 5847, "Savings", "ACTIVE", 12000, transactions1);
		HashMap<Integer,Transaction> transactions2 = new HashMap<Integer, Transaction>();
		Account account2= new Account(102, 7485, "Savings", "ACTIVE", 10000, transactions2);
		
		BankingDBUtil.accounts.put(account1.getAccountNo(), account1);
		BankingDBUtil.accounts.put(account2.getAccountNo(), account2);
		
		BankingDBUtil.ACCOUNT_NUMBER=102;
	}
	
	@Test(expected=InvalidAmountException.class)
	public void testOpenAccountForInvalidData() throws InvalidAccountTypeException,InvalidAmountException{
		services.openAccount("Savings", 4000);
	}
	
	@Test
	public void testOpenAccountForValidData() throws InvalidAccountTypeException,InvalidAmountException{
		Account account3 = services.openAccount("Savings", 10000);
		long expectedAccountNumber = 103;
		long actualAccountNumber = account3.getAccountNo();
		Assert.assertEquals(expectedAccountNumber, actualAccountNumber);
	}
	
	@Test(expected=AccountNotFoundException.class)
	public void testDepositAmountForInvalidAccountNumber() throws AccountNotFoundException{
		services.depositAmount(1233, 5430.98f);
	}
	
	@Test
	public void testDepositAmountForValidAccountNumber() throws AccountNotFoundException{
		long expectedAmount = 15000;
		long actualAmount = (long)services.depositAmount(103, 5000);
		Assert.assertEquals(expectedAmount, actualAmount);
	}
	
	@Test(expected=AccountNotFoundException.class)
	public void testWithdrawAmountForInvalidAccountNumber() throws AccountNotFoundException{
		services.withdrawAmount(1233, 5430,1199);
	}
	
	@Test
	public void testWithdrawAmountForValidAccountNumber() throws AccountNotFoundException{
		long expectedAmount = 5000;
		long actualAmount = (long)services.withdrawAmount(101, 5000, 5847);
		Assert.assertEquals(expectedAmount, actualAmount);
	}
	
	@Test(expected=AccountNotFoundException.class)
	public void testFundTransferForInvalidAccountNumber() throws AccountNotFoundException{
		services.fundTransfer(101, 123, 1200, 1122);
	}
	
	@Test
	public void testFundTransferForValidAccountNumber() throws AccountNotFoundException{
		boolean expected = true;
		boolean actual = services.fundTransfer(103, 101, 2000, 5847);
		Assert.assertEquals(expected, actual);
	}
	
	@After
	public void tearUpTestData() {
		BankingDBUtil.accounts.clear();
		BankingDBUtil.ACCOUNT_NUMBER=100;
	}
	@AfterClass
	public static void tearDownTestEnv() {
		services=null;
	}
}
