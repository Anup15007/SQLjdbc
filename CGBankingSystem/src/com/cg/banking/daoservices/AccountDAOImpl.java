package com.cg.banking.daoservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cg.banking.beans.Account;
import com.cg.banking.util.BankingDBUtil;


public class AccountDAOImpl implements AccountDAO{
	private static Connection con=BankingDBUtil.getDBConnection();

	@Override
	public Account save(Account account) {
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt1=con.prepareStatement("Insert into Associate(associateId,yearlyInvestmentUnder80C, firstName,lastName,department,designation,pancard,emailId) values(Associate_ID_SEQ.NEXTVAL,?,?,?,?,?,?,?)");
			pstmt1.setInt(1, associate.getYearlyInvestmentUnder80C());
			pstmt1.setString(2, associate.getFirstName());
			pstmt1.setString(3, associate.getLastName());
			pstmt1.setString(4, associate.getDepartment());
			pstmt1.setString(5, associate.getDesignation());
			pstmt1.setString(6, associate.getPancard());
			pstmt1.setString(7, associate.getEmailId());
			
			pstmt1.executeUpdate();
			
			PreparedStatement pstmt2=con.prepareStatement("select max(associateId) from Associate");
			ResultSet rs=pstmt2.executeQuery();
			rs.next();
			int associateId=rs.getInt(1);
			
			PreparedStatement pstmt3=con.prepareStatement("insert into Salary(associateId,basicSalary,epf,companyPf)values(?,?,?,?)");
			pstmt3.setInt(1, associateId);
			pstmt3.setInt(2, associate.getSalary().getBasicSalary());
			pstmt3.setInt(3, associate.getSalary().getEpf());
			pstmt3.setInt(4, associate.getSalary().getCompanyPf());
			pstmt3.executeUpdate();
			
			
			PreparedStatement pstmt4=con.prepareStatement("insert into BankDetails(associateId,accountNumber, bankName,  ifscCode)values(?,?,?,?)");
			pstmt4.setInt(1, associateId);
			pstmt4.setInt(2, associate.getBankDetails().getAccountNumber());
			pstmt4.setString(3, associate.getBankDetails().getBankName());
			pstmt4.setString(4, associate.getBankDetails().getIfscCode());
			pstmt4.executeUpdate();
			
			associate.setAssociateId(associateId);
			con.commit();
			
		}
		catch(SQLException e1) {
			e1.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean update(Account account) {
	
		return false;
	}

	@Override
	public Account findOne(long accountNo) {
		
		return null;
	}

	@Override
	public List<Account> findAll() {
	
		return null;
	}



}
