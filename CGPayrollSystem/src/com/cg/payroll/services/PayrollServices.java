package com.cg.payroll.services;

import java.util.List;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.exceptions.AssociateDetailsNotFoundException;

public interface PayrollServices {
	int acceptAssociateDetails(String firstName,String lastName,String emailId,String department,String designation,
			String pancard,int yearlyInvestmentUnder80C,int basicSalary,int epf,int companyPf,int accountNumber,
			String bankName,String ifscCode);
	
	int calculateNetSalary(int associateId) throws AssociateDetailsNotFoundException;
	
	Associate getAssociateDetails(int associateId)throws AssociateDetailsNotFoundException;
	
	List<Associate> getAllAssociatesDetails();
}
