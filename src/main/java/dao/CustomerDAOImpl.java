package dao;

import beans.Customer;
import db.JDBCUtils;
import db.ResultsUtils;

import java.sql.SQLException;
import java.util.*;

public class CustomerDAOImpl implements CustomerDAO {

    private static final String QUERY_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-project`.customers WHERE `email`=? AND `password`=?) AS res;";
    private static final String QUERY_INSERT = "INSERT INTO `coupon-project`.`customers` (`firstName`, `lastName`, `email`,`password`) VALUES (?, ?, ?,?);";
    private static final String QUERY_UPDATE = "UPDATE `coupon-project`.`customers` SET `firstName` =?,`lastName`=?,`email`=?,`password`=? WHERE (`id`=?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-project`.`customers` WHERE (`id`=?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-project`.`customers`";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-project`.`customers` WHERE (`id`=?);";
    private static final String QUERY_IF_EMAIL_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-project`.customers WHERE `email`=?  ) AS res;";
    private static final String QUERY_IF_ID_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-project`.customers WHERE `id`=?  ) AS res;";
    private static final String QUERY_GET_ONE_BY_EMAIL = "SELECT * FROM `coupon-project`.`customers` WHERE (`email`=?);";

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_EXIST, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        JDBCUtils.executeQuery(QUERY_INSERT, params);
    }

    @Override
    public void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customerId);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        JDBCUtils.executeQuery(QUERY_DELETE, params);
    }

    @Override
    public List<Customer> getAllCustomeres() throws SQLException, InterruptedException {
        List<Customer> customers = new ArrayList<>();
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for (Object obj : rows) {
            customers.add(ResultsUtils.customerFromRow((HashMap<String, Object>) obj));
        }
        return customers;
    }

    @Override
    public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException {
        Customer customer = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        try {
            customer = ResultsUtils.customerFromRow((HashMap<String, Object>) rows.get(0));

        } catch (Exception e) {
            System.out.println(e);
        }
        return customer;
    }

    @Override
    public boolean isCustomerExistByEmail(String emailOfCustomer) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, emailOfCustomer);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IF_EMAIL_EXIST, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public boolean isCustomerExistById(int customerId) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IF_ID_EXIST, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public Customer getCustomerByEmail(String emailOfCustomer) throws SQLException, InterruptedException {
        Customer customer = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, emailOfCustomer);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE_BY_EMAIL, params);
        try {
            customer = ResultsUtils.customerFromRow((HashMap<String, Object>) rows.get(0));

        } catch (Exception e) {
            System.out.println(e);
        }
        return customer;
    }
}
