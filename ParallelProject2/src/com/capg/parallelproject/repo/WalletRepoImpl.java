package com.capg.parallelproject.repo;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import com.capg.parallelproject.beans.Customer;
import com.capg.parallelproject.beans.Wallet;
import com.capg.parallelproject.util.Util;

public class WalletRepoImpl implements WalletRepo {

	EntityManager em;
	
	
	public WalletRepoImpl() {

		super();
		em = Util.getEntityManager();
	
	}
	
	/* (non-Javadoc)
	 * @see com.capg.parallelproject.repo.WalletRepo#save(com.capg.parallelproject.beans.Customer)
	 */
	public boolean save(Customer customer){
		
		Customer cust =  new Customer();
		em.getTransaction().begin();
		cust = em.find(Customer.class, customer.getMobileNo());
		if(cust == null) {
			em.persist(customer);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.capg.parallelproject.repo.WalletRepo#findOne(java.lang.String)
	 */
	@Override
	public Customer findOne(String mobileNo){
		
		Customer customer = em.find(Customer.class, mobileNo);
		if(customer != null)
			return customer;
		return null;
		
	}

	@Override
	public boolean updateAmount(String mobileNo, BigDecimal amount) {
		em.getTransaction().begin();		
		Customer customer = em.find(Customer.class, mobileNo);
		Wallet wallet = new Wallet();
		wallet.setBalance(amount);
		customer.setWallet(wallet);
		em.getTransaction().commit();
		return true;
	}

	
}