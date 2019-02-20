package com.capg.parallelproject.repo;

import java.math.BigDecimal;

import com.capg.parallelproject.beans.Customer;

public interface WalletRepo {

	boolean save(Customer customer);
	
	Customer findOne(String mobileNo);
	
	public boolean updateAmount(String mobileNo, BigDecimal amount);
	 
}