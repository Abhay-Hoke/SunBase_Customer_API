package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Customer;
import com.example.utils.DbUtils;



public class CustomerDao {
	
	public List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM customers order by uuid desc";
        List<Customer> customers = new ArrayList<>();
        try (Connection con = DbUtils.connectDB();
             PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setUuid(rs.getString("uuid"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setStreet(rs.getString("street"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
        }
        return customers;
    }

    public boolean addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO customers (first_name,last_name,street,address,city,state, email, phone) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection con = DbUtils.connectDB();
                PreparedStatement ps = con.prepareStatement(query)) {
        	ps.setString(1, customer.getFirst_name());
        	ps.setString(2, customer.getLast_name());
        	ps.setString(3, customer.getStreet());
        	ps.setString(4, customer.getAddress());
        	ps.setString(5, customer.getCity());
        	ps.setString(6, customer.getState());
        	ps.setString(7, customer.getEmail());
        	ps.setString(8, customer.getPhone());
            return ps.executeUpdate() > 0;
        }
    }
    
    public List<Customer> searchCustomers(String attribute, String value) throws ClassNotFoundException, SQLException{
    	
    	String query = "SELECT * FROM customers WHERE " + attribute + " LIKE ?";
    	try (Connection con = DbUtils.connectDB();
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, "%" + value + "%");
            ResultSet rs = ps.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
            	Customer customer = new Customer();
                customer.setUuid(rs.getString("uuid"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setStreet(rs.getString("street"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
            return customers;
        }
    	
    }
    
    
    
    public boolean deleteCustomer(String uuid) throws ClassNotFoundException, SQLException {
        String query = "DELETE FROM customers WHERE uuid = ?";
        try(Connection con = DbUtils.connectDB();
                PreparedStatement ps = con.prepareStatement(query)){
        	ps.setString(1,uuid);
        	return ps.executeUpdate()>0;
        	
        }
    	
    }
    
    public  boolean updateCustomer(Customer customer) throws ClassNotFoundException, SQLException {
    	 String query = "UPDATE customers SET first_name = ?,last_name=?,street=?,address=?,city=?,state=?, email = ?, phone = ? WHERE uuid = ?";
    	    try (Connection conn = DbUtils.connectDB();
    	         PreparedStatement ps = conn.prepareStatement(query)) {

    	    	ps.setString(1, customer.getFirst_name());
            	ps.setString(2, customer.getLast_name());
            	ps.setString(3, customer.getStreet());
            	ps.setString(4, customer.getAddress());
            	ps.setString(5, customer.getCity());
            	ps.setString(6, customer.getState());
            	ps.setString(7, customer.getEmail());
            	ps.setString(8, customer.getPhone());
            	ps.setString(9, customer.getUuid());
                return ps.executeUpdate() > 0;
    	    }
    	
    }
    
    public Customer getCustomerByUuid(String uuid) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM customers WHERE uuid = ?";
        try (Connection conn = DbUtils.connectDB();
   	         PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setUuid(rs.getString("uuid"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setStreet(rs.getString("street"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getCustomersWithPagination(int offset, int limit) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers LIMIT ?, ?";
        try (Connection conn = DbUtils.connectDB();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, offset);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setUuid(rs.getString("uuid"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setStreet(rs.getString("street"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    public boolean existsByEmail(String email) throws SQLException, ClassNotFoundException {
        String query = "SELECT 1 FROM customers WHERE email = ?";
        try (Connection conn = DbUtils.connectDB();
        		PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
    
    public int getTotalCustomers() {
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM customers";
        try (Connection conn = DbUtils.connectDB();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


}


