package dao;

import beans.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {

    boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException;

    void addCustomer(Customer customer) throws SQLException, InterruptedException;

    void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException;

    void deleteCustomer(int customerId) throws SQLException, InterruptedException;

    List<Customer> getAllCustomeres() throws SQLException, InterruptedException;

    Customer getOneCustomer(int customerId) throws SQLException, InterruptedException;

    boolean isCustomerExistByEmail(String emailOfCustomer) throws SQLException, InterruptedException;

    boolean isCustomerExistById(int customerId) throws SQLException, InterruptedException;

    Customer getCustomerByEmail(String emailOfCustomer) throws SQLException, InterruptedException;
}