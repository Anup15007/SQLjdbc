package com.cg.payroll.services;

import java.util.List;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.daoservices.AssociateDAO;
import com.cg.payroll.daoservices.AssociateDAOImpl;
import com.cg.payroll.exceptions.AssociateDetailsNotFoundException;

public class PayrollServicesImpl implements PayrollServices{

	private AssociateDAO associateDao;
	
	public PayrollServicesImpl() {
		associateDao=new AssociateDAOImpl();
	}
	
	public PayrollServicesImpl(AssociateDAO associateDao) {
		this.associateDao=associateDao;
	}
	
	@Override
	public int acceptAssociateDetails(String firstName, String lastName, String emailId, String department,
			String designation, String pancard, int yearlyInvestmentUnder80C, int basicSalary, int epf, int companyPf,
			int accountNumber, String bankName, String ifscCode) {
/*		BankDetails bankDetails = new BankDetails(accountNumber, bankName, ifscCode);
		Salary salary = new Salary(basicSalary, epf, companyPf);
		Associate associate = new Associate(yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId,salary,bankDetails);*/
		
		Associate associate = new Associate(yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId,new Salary(basicSalary, epf, companyPf),new BankDetails(accountNumber, bankName, ifscCode));
		associate = associateDao.save(associate);
		return associate.getAssociateId();
		
		//return associateDao.save(new Associate(yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId,new Salary(basicSalary, epf, companyPf),new BankDetails(accountNumber, bankName, ifscCode)));
	}

	@Override
	public int calculateNetSalary(int associateId) throws AssociateDetailsNotFoundException {
		Associate associate = getAssociateDetails(associateId);
		int netSalary=0,grossSalary=0,monthlyTax=0,YearlyTax=0;
		associate.getSalary().setHra((int)((associate.getSalary().getBasicSalary())*0.3));
		associate.getSalary().setCompanyPf((int)((associate.getSalary().getBasicSalary())*0.3));
		associate.getSalary().setPersonalAllowance((int)((associate.getSalary().getBasicSalary())*0.25));
		associate.getSalary().setOtherAllowance((int)((associate.getSalary().getBasicSalary())*0.2));
		grossSalary = associate.getSalary().getBasicSalary()+associate.getSalary().getHra()+associate.getSalary().getCompanyPf()+associate.getSalary().getPersonalAllowance()+associate.getSalary().getOtherAllowance();
		int annualGrossSalary = grossSalary*12;
		int investment = associate.getYearlyInvestmentUnder80C()+associate.getSalary().getCompanyPf()+associate.getSalary().getEpf();
		if(investment>150000)
			investment=150000;
		int taxableAmount = grossSalary;
		if(annualGrossSalary<=250000) {
			associate.getSalary().setMonthlyTax(monthlyTax);
		}
		else if(annualGrossSalary>250000&&annualGrossSalary<=500000) {
			taxableAmount = annualGrossSalary-250000;
			if(taxableAmount>investment) {
				taxableAmount -=investment;
				monthlyTax=(int)(taxableAmount *0.01);
				associate.getSalary().setMonthlyTax(monthlyTax);
			}else
				monthlyTax= 0;
		}
		else if(annualGrossSalary>500000&&annualGrossSalary<=1000000) {
			taxableAmount = annualGrossSalary-250000;
			if(taxableAmount>investment) {
				taxableAmount -=investment;
				monthlyTax=(int)(taxableAmount *0.02);
				associate.getSalary().setMonthlyTax(monthlyTax);
			}else
				monthlyTax= 0;
		}
		else if(annualGrossSalary>1000000) {
			taxableAmount = annualGrossSalary-250000;
			if(taxableAmount>investment) {
				taxableAmount -=investment;
				monthlyTax=(int)(taxableAmount *0.03);
				associate.getSalary().setMonthlyTax(monthlyTax);
			}else
				monthlyTax= 0;
		}
			
		netSalary = grossSalary-associate.getSalary().getCompanyPf()-associate.getSalary().getEpf();
		
		return netSalary;
	}

	@Override
	public Associate getAssociateDetails(int associateId) throws AssociateDetailsNotFoundException {
		Associate associate = associateDao.findOne(associateId);
		if(associate==null)
			throw new AssociateDetailsNotFoundException("Associate details not found for id = "+associateId);
		
		return associate;
	}

	@Override
	public List<Associate> getAllAssociatesDetails() {
		return associateDao.findAll();
	}
}
