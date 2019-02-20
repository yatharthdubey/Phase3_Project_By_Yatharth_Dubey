package com.capg.parallelproject.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;

import com.capg.parallelproject.beans.Customer;
import com.capg.parallelproject.beans.Wallet;
import com.capg.parallelproject.exceptiions.DuplicateMobileNumberException;
import com.capg.parallelproject.exceptiions.InsufficientWalletBalanceException;
import com.capg.parallelproject.exceptiions.MobileNumberNotFoundException;
import com.capg.parallelproject.repo.WalletRepo;

public class WalletServiceImpl implements WalletService {

	
	WalletRepo walletRepo;
	
	public WalletServiceImpl(WalletRepo walletRepo) {
		super();
		this.walletRepo = walletRepo;
	}

	/* (non-Javadoc)
	 * @see com.capg.parallelproject.service.WalletService#createAccount(java.lang.String, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public Customer createAccount(String name, String mobileNo, BigDecimal balance) throws DuplicateMobileNumberException {
		
		Wallet wallet =new Wallet();
		wallet.setBalance(balance);
		Customer customer = new Customer();
		customer.setName(name);
		customer.setMobileNo(mobileNo);
		customer.setWallet(wallet);
		if(walletRepo.save(customer)) {
			return customer;
		}
		throw new DuplicateMobileNumberException();
			
	}
	
	/* (non-Javadoc)
	 * @see com.capg.parallelproject.service.WalletService#showBalance(java.lang.String)
	 */
	@Override
	public Customer showBalance(String mobileNo) throws MobileNumberNotFoundException {

		Customer customer = new Customer();
		customer = walletRepo.findOne(mobileNo);
		if(customer != null)
			return customer;
		throw new MobileNumberNotFoundException();
	}
	
	/* (non-Javadoc)
	 * @see com.capg.parallelproject.service.WalletService#fundTransfer(java.lang.String, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public Customer[] fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InsufficientWalletBalanceException, MobileNumberNotFoundException {
	
		Customer[] customerArray = new Customer[2];
		Customer customerSource = new Customer();
		customerSource = walletRepo.findOne(sourceMobileNo);
		if(customerSource==null)
			throw new MobileNumberNotFoundException();
		Customer customerTarget = new Customer();
		customerTarget = walletRepo.findOne(targetMobileNo);
		if(customerTarget==null)
			throw new MobileNumberNotFoundException();
		if(amount.compareTo(customerSource.getWallet().getBalance()) == 1) {
			throw new InsufficientWalletBalanceException();
		}
		else {
			BigDecimal sourceAmount = customerSource.getWallet().getBalance().subtract(amount);
			customerSource.getWallet().setBalance(sourceAmount);
			walletRepo.updateAmount(sourceMobileNo, sourceAmount);
			BigDecimal targetAmount = customerTarget.getWallet().getBalance().add(amount);
			customerTarget.getWallet().setBalance(targetAmount);
			walletRepo.updateAmount(targetMobileNo, targetAmount);
			customerArray[0] = customerSource;
			customerArray[1] = customerTarget;
		}
		
		return customerArray;
}
	/* (non-Javadoc)
	 * @see com.capg.parallelproject.service.WalletService#depositAmount(java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws MobileNumberNotFoundException {
		
		Customer customer = new Customer();
		customer = walletRepo.findOne(mobileNo);
			
		if(customer!=null) {
			BigDecimal am = customer.getWallet().getBalance().add(amount);
			customer.getWallet().setBalance(am);
			walletRepo.updateAmount(mobileNo, am);
			return customer;
		}
		throw new MobileNumberNotFoundException();

		
		
	}
	
	/* (non-Javadoc)
	 * @see com.capg.parallelproject.service.WalletService#withdrawAmount(java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws MobileNumberNotFoundException, InsufficientWalletBalanceException {
		
		Customer customer = new Customer();
		customer = walletRepo.findOne(mobileNo);
		if(customer== null) {
			throw new MobileNumberNotFoundException();
		}
		if(amount.compareTo(customer.getWallet().getBalance()) == 1) {
			throw new InsufficientWalletBalanceException();
		}
		BigDecimal am = customer.getWallet().getBalance().subtract(amount);
		customer.getWallet().setBalance(am);
		walletRepo.updateAmount(mobileNo, am);
		return customer;
	}
}