package com.masai.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.masai.DTO.Customer;
import com.masai.DTO.CustomerImpl;
import com.masai.DTO.Stock;
import com.masai.DTO.StockImpl;
import com.masai.EXCEPTION.NoRecordFoundException;
import com.masai.EXCEPTION.SomethingWentWrongException;

public class BrokerDAOImpl implements BrokerDAO {
	@Override
		public List<Customer> viewAllCustomers() throws NoRecordFoundException {
		    List<Customer> customerList = new ArrayList<>();
		    Connection connection = null;
		    try{
		    	connection = DBUtils.getConnection();
		    	String query = "SELECT * FROM customer WHERE is_active = 1";
		        PreparedStatement statement = connection.prepareStatement(query);
		        ResultSet resultSet = statement.executeQuery();
		        
		        if(DBUtils.isResulSetEmpty(resultSet)) {
		        	System.out.println("Empty list");
		        }else {
		        	while (resultSet.next()) {
			        	Customer customer = new  CustomerImpl(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)
			        			, resultSet.getString(6),
			    				resultSet.getString(7), resultSet.getString(8), resultSet.getDouble(9)
			    				, resultSet.getInt(10));
			        	customer.setId(resultSet.getInt(1));
			            customerList.add(customer);

		        }
		        }
		        
		    } catch (ClassNotFoundException | SQLException e) {
				throw new NoRecordFoundException("No Data Found");
			}finally {
				try {
					DBUtils.closeConnection(connection);
				}catch(SQLException ex) {
					
				}
			}
		    return customerList;
		}
	@Override
	public void addnewStock(String name, int quantity, double price) throws SomethingWentWrongException {
		Connection con = null;
		  try {
			 con = DBUtils.getConnection();
		    String sqlQuery = "INSERT INTO stock (name, quantity, price, is_deleted) VALUES (?, ?, ?, 0)";
		    PreparedStatement statement = con.prepareStatement(sqlQuery);
		    statement.setString(1, name);
		    statement.setInt(2, quantity);
		    statement.setDouble(3, price);
		    statement.executeUpdate();
		  } catch (SQLException e) {
		    e.printStackTrace();
		  } catch (ClassNotFoundException e) {
			throw new SomethingWentWrongException("Something Went Wrong");
		}finally {
			try {
				DBUtils.closeConnection(con);
			}catch(SQLException ex) {
				
			}
		}
		}
	
	@Override
	public List<Stock> viewAllStocks() throws NoRecordFoundException{
		Connection con = null;
		List<Stock> list = new ArrayList<>();
		try {
			con = DBUtils.getConnection();
		    String sqlQuery = "SELECT * FROM stock";
		    PreparedStatement ps = con.prepareStatement(sqlQuery);
		    ResultSet resultSet = ps.executeQuery(sqlQuery);
		    while (resultSet.next()) {
		      int stockId = resultSet.getInt(1);
		      String name = resultSet.getString(2);
		      int quantity = resultSet.getInt(3);
		      double price = resultSet.getDouble(4);
		      int isDeleted = resultSet.getInt(5);
		      Stock stock = new StockImpl(name,quantity,price);
		      stock.setId(stockId);
		      stock.setIsDeleted(isDeleted);
		      list.add(stock);
		      
		    }
		  } catch (SQLException | ClassNotFoundException e) {
		    throw new NoRecordFoundException("No data found");
		  } finally {
			  try {
				  DBUtils.closeConnection(con);
			  }catch (SQLException e) {
				e.printStackTrace();
			}
		  }
		return list;
	}
	
	public boolean deleteStock(int id) throws SomethingWentWrongException{
		Connection con = null;
		try {
			con = DBUtils.getConnection();
		 PreparedStatement pr = con.prepareStatement("DELETE FROM STOCK WHERE stock_id = ?");
		 pr.setInt(1, id);
		 pr.executeUpdate();
		 return true;
		}catch(SQLException | ClassNotFoundException e) {
			throw new SomethingWentWrongException("Something went wrong ");
		}finally {
			try {
				DBUtils.closeConnection(con);
				return false;
			}catch(SQLException e) {
				
			}
		}
	}
	
	 @Override
	    public boolean deleteCustomer(int  id) throws SomethingWentWrongException {
		 Connection con = null;
	        try {
	        	String query = "DELETE FROM customer WHERE customer_id = ?";
	 	        con = DBUtils.getConnection();
	 	        PreparedStatement stmt = con.prepareStatement(query);
	            stmt.setInt(1, id);
	           if(stmt.executeUpdate() > 0) {
	        	   return true;
	           }else {
	        	   return false;
	           }
	            
	        }catch(SQLException | ClassNotFoundException ex) {
	        	throw new SomethingWentWrongException("could not be deleted");
	        }finally {
				try {
					DBUtils.closeConnection(con);
				}catch(SQLException e) {
					
				}
			}
	    }
}
