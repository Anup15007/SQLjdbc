package com.cg.payroll.test;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.daoservices.AssociateDAO;
import com.cg.payroll.exceptions.AssociateDetailsNotFoundException;
import com.cg.payroll.services.PayrollServices;
import com.cg.payroll.services.PayrollServicesImpl;
import com.cg.payroll.util.PayrollDBUtil;

public class PayrollServicesTestEasyMock {
	private static PayrollServices payrollServices;
	private static AssociateDAO mockAssociateDao;
	
	@BeforeClass
	public static void setUpTestEnv() {
		mockAssociateDao = EasyMock.mock(AssociateDAO.class);
		payrollServices = new  PayrollServicesImpl(mockAssociateDao);
	}

	@Before
	public void setUpTestMockData() {
		Associate associate1 = new Associate(101, 78000, "Kuldeep", "Dangwal", "SE", "Analyst", "KDTY654CG", "dangwalk123@capgemini.com",
				new Salary(35000,1800,1800), new BankDetails(54321,"Citi","citi0001"));
		Associate associate2 = new Associate(102, 87654, "Patit", "Pawan", "SE", "Analyst", "PPTY6754CG", "patitpawan123@capgemini.com",
				new Salary(45000,2800,2800), new BankDetails(54654,"HDFC","hdfc0005"));
		Associate associate3 = new Associate(65400, "Mayuri","Sharma", "ADC", "Trainee", "MSH9876CG", "mayurish123@gmail.com", 
				new Salary(30000,2000,2000), new BankDetails(54768,"ICICI","icici0001"));
		
		ArrayList<Associate> associatesList = new ArrayList<Associate>();
		associatesList.add(associate1);
		associatesList.add(associate2);
		
		EasyMock.expect(mockAssociateDao.save(associate3)).andReturn(associate3);
		
		EasyMock.expect(mockAssociateDao.findOne(101)).andReturn(associate1);
		EasyMock.expect(mockAssociateDao.findOne(102)).andReturn(associate2);
		EasyMock.expect(mockAssociateDao.findOne(1234)).andReturn(null);
		EasyMock.expect(mockAssociateDao.findAll()).andReturn(associatesList);
		EasyMock.replay(mockAssociateDao);
	}
	
	@Test(expected=AssociateDetailsNotFoundException.class)
	public void testGetAssociateDetailsForInvalidAssociateId() throws AssociateDetailsNotFoundException{
		payrollServices.getAssociateDetails(123);
		EasyMock.verify(mockAssociateDao.findOne(123));
	}
	
	@Test
	public void testGetAssociateDetailsForValidAssociateId() throws AssociateDetailsNotFoundException{
		Associate expectedAssociate = new Associate(101, 87654, "Patit", "Pawan", "SE", "Analyst", "PPTY6754CG", "patitpawan123@capgemini.com",
				new Salary(45000,2800,2800), new BankDetails(54654,"HDFC","hdfc0005"));
		Associate actualAssociate = payrollServices.getAssociateDetails(101);
		Assert.assertEquals(expectedAssociate, actualAssociate);
		EasyMock.verify(mockAssociateDao.findOne(101));
	}
	
	@After
	public void tearDownTestMockData() {
		EasyMock.resetToDefault(mockAssociateDao);
	}
	
	@AfterClass
	public static void tearDownTestEnv() {
		mockAssociateDao=null;
		payrollServices=null;
	}
}
