package com.capg.parallelproject.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.capg.parallelproject.beans.Customer;
import com.capg.parallelproject.beans.Wallet;
import com.capg.parallelproject.exceptiions.DuplicateMobileNumberException;
import com.capg.parallelproject.exceptiions.InsufficientWalletBalanceException;
import com.capg.parallelproject.exceptiions.MobileNumberNotFoundException;
import com.capg.parallelproject.repo.WalletRepoImpl;
import com.capg.parallelproject.service.WalletService;
import com.capg.parallelproject.service.WalletServiceImpl;

public class TestCase {
	
	WalletService ws;
	@Before
	public void setup() {
		
		ws = new WalletServiceImpl(new WalletRepoImpl());
		
	}

	@Test
	public void whenCustomerAccountIsCreatedSuccessfully() throws DuplicateMobileNumberException {
		
		Wallet wallet = new Wallet();
		wallet.setBalance(new BigDecimal("200.0"));
		Customer customer = new Customer();
		customer.setMobileNo("9808180238");
		customer.setName("Yatharth");
		customer.setWallet(wallet);
		assertEquals(customer, ws.createAccount("Yatharth", "9808180238", new BigDecimal("200.0")));
		
	}
	
	@Test(expected = DuplicateMobileNumberException.class)
	public void whenYouEnteredADuplicateNumberThenDuplicateNumberExceptionOccurs() throws DuplicateMobileNumberException {
		
		ws.createAccount("Yatharth", "9412164694", new BigDecimal("200.0"));
		ws.createAccount("Dubey", "9412164694", new BigDecimal("300.0"));
		
	}
	
	@Test(expected = MobileNumberNotFoundException.class)
	public void whenMobileNumberIsNotPresentWhileCallingShowBalaceMethodThenMobileNumberNotFoundExceptionOccurs() throws DuplicateMobileNumberException, MobileNumberNotFoundException {
		
		ws.createAccount("Yatharth", "9412164695", new BigDecimal("200.0"));
		ws.showBalance("9412164696");
		
	}
	@Test
	public void whenShowBalanceIsSuccessfullyCompleted() throws DuplicateMobileNumberException, MobileNumberNotFoundException {
		
		assertEquals(ws.createAccount("Yatharth", "9412164697", new BigDecimal("200.0")),ws.showBalance("9412164697"));
		
	}
	@Test
	public void whenWithdrawAmountIsSuccessfullyCompleted() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		ws.createAccount("Yatharth", "9412164698", new BigDecimal("200.0"));
		ws.withdrawAmount("9412164698", new BigDecimal("100.0"));

	}
	
	@Test
	public void whenDepositeAmountIsSuccessfullyCompleted() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		ws.createAccount("Yatharth", "9412164699", new BigDecimal("200.0"));
		ws.depositAmount("9412164699", new BigDecimal("100.0"));

	}
	@Test
	public void whenFundTransferIsSuccessfullyCompleted() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		ws.createAccount("Yatharth", "9412164800", new BigDecimal("200.0"));
		ws.createAccount("Yatharth", "9412164801", new BigDecimal("100.0"));
		ws.fundTransfer("9412164800", "9412164801", new BigDecimal("50.0"));
		

	}
	@Test(expected = MobileNumberNotFoundException.class)
	public void whenMobileNumberIsNotPresentWhileCallingWithdrawAmountMethodThenMobileNumberNotFoundExceptionOccurs() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		
		ws.createAccount("Yatharth", "9412164702", new BigDecimal("200.0"));
		ws.withdrawAmount("9412164703", new BigDecimal("100.0"));
		
	}
	
	@Test(expected = InsufficientWalletBalanceException.class)
	public void whenBalanceIsLessThanAmountThatYouWantToWithdrawThenInsufficientWalletBalanceExceptionOccurs() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		
		ws.createAccount("Yatharth", "9412164704", new BigDecimal("200.0"));
		ws.withdrawAmount("9412164704", new BigDecimal("300.0"));
		
	}
	@Test(expected = MobileNumberNotFoundException.class)
	public void whenMobileNumberIsNotPresentWhileCallingDepositeAmountMethodThenMobileNumberNotFoundExceptionOccurs() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		
		ws.createAccount("Yatharth", "9412164905", new BigDecimal("200.0"));
		ws.depositAmount("9412164906", new BigDecimal("100.0"));
		
	}
	@Test(expected = MobileNumberNotFoundException.class)
	public void whenSourceMobileNumberIsNotPresentWhileCallingFundTranferMethodThenMobileNumberNotFoundExceptionOccurs() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		
		ws.createAccount("Yatharth", "9412164706", new BigDecimal("200.0"));
		ws.createAccount("Yatharth", "94121646708", new BigDecimal("100.0"));
		ws.fundTransfer("9412164707", "9412164708", new BigDecimal("50.0"));
		
	}
	@Test(expected = MobileNumberNotFoundException.class)
	public void whenTargetMobileNumberIsNotPresentWhileCallingDepositeAmountMethodThenMobileNumberNotFoundExceptionOccurs() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		
		ws.createAccount("Yatharth", "9412164709", new BigDecimal("200.0"));
		ws.createAccount("Yatharth", "9808180710", new BigDecimal("100.0"));
		ws.fundTransfer("9412164709", "9808180711", new BigDecimal("50.0"));
		
	}
	
	@Test(expected = InsufficientWalletBalanceException.class)
	public void whenBalanceInSourceAccountIsLessThanAmountThatYouWantToTransferThenInsufficientWalletBalanceExceptionOccurs() throws DuplicateMobileNumberException, MobileNumberNotFoundException, InsufficientWalletBalanceException {
		
		ws.createAccount("Yatharth", "9412164711", new BigDecimal("100.0"));
		ws.createAccount("Yatharth", "9808180712", new BigDecimal("200.0"));
		ws.fundTransfer("9412164711", "9808180712", new BigDecimal("150.0"));
		
	}
	
}
