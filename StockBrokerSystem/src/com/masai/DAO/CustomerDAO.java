package com.masai.DAO;

import com.masai.DTO.Customer;
import com.masai.EXCEPTION.InvalidCredentialsException;
import com.masai.EXCEPTION.SomethingWentWrongException;

public interface CustomerDAO {
	public  void addCustomer(Customer customer) throws SomethingWentWrongException;
	
	public Customer authenticateCustomer(String username, String password) throws InvalidCredentialsException;
}
