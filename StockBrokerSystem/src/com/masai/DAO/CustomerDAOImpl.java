package com.masai.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.masai.DTO.Customer;
import com.masai.DTO.CustomerImpl;
import com.masai.EXCEPTION.InvalidCredentialsException;
import com.masai.EXCEPTION.SomethingWentWrongException;

public class CustomerDAOImpl implements CustomerDAO{
	
	@Override
	public  void addCustomer(Customer customer) throws SomethingWentWrongException {
	    String query = "INSERT INTO customer (first_name, last_name, username, password, address, mobile_number, email, wallet_balance, is_active) VALUES (?,?,?,?,?,?,?,?,?)";
	    Connection conn = null;
	    try{
	    	conn = DBUtils.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, customer.getFirstName());
	        pstmt.setString(2, customer.getLastName());
	        pstmt.setString(3, customer.getUsername());
	        pstmt.setString(4, customer.getPassword());
	        pstmt.setString(5, customer.getAddress());
	        pstmt.setString(6, customer.getPhone());
	        pstmt.setString(7, customer.getEmail());
	        pstmt.setDouble(8, customer.getWalletBalance());
	        pstmt.setInt(9, 1);
	        pstmt.executeUpdate();

	    } catch (SQLException | ClassNotFoundException e) {
	        throw new SomethingWentWrongException("Could not able to add customer"+e.getMessage());
	    }finally {
	    	try {
	    		DBUtils.closeConnection(conn);
	    	}catch(SQLException e) {
	    		
	    	}
	    }
	}
	
	@Override
	public Customer authenticateCustomer(String username, String password) throws InvalidCredentialsException {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    try {
	        connection = DBUtils.getConnection();
	        String query = "SELECT * FROM customer WHERE username = ? AND password = ? AND is_active = 1";
	        statement = connection.prepareStatement(query);
	        statement.setString(1, username);
	        statement.setString(2, password);
	        resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	        	Customer customer = new  CustomerImpl(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)
	        			, resultSet.getString(6),
	    				resultSet.getString(7), resultSet.getString(8), resultSet.getDouble(9)
	    				, resultSet.getInt(10));
	        	customer.setId(resultSet.getInt(1));
	            return customer;
	        } else {
	            throw new InvalidCredentialsException("Invalid username or password");
	        }
	    } catch (ClassNotFoundException | SQLException e) {
	        throw new InvalidCredentialsException("Invalid username or password");
	    } finally {
	        try {
	            DBUtils.closeConnection(connection);
	            if (statement != null) statement.close();
	            if (resultSet != null) resultSet.close();
	        } catch (SQLException ex) {
	            
	        }
	    }
	}

}
