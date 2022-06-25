package facade;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import dao.CompanyDAOImpl;
import dao.CouponDAOImpl;
import dao.CustomerDAOImpl;
import exception.CouponSystemException;
import exception.ErrMsg;
import java.sql.SQLException;
import java.util.List;

public class AdminFacade extends ClientFacade {

    public AdminFacade(String email, String password) {
        if (login(email, password) == true) {
            companyDAO = new CompanyDAOImpl();
            couponDAO = new CouponDAOImpl();
            customerDAO = new CustomerDAOImpl();
            System.out.println("User from AdminFacade connect succefully to system!  ");
        } else {
            System.out.println("Email or password incorrect,access denied");
        }
    }

    @Override
    public boolean login(String email, String password) {
        return (email.equals("admin@admin.com") && password.equals("admin"));
    }

    //---------********** Company **********---------");
    public void addCompany(Company company) throws SQLException, InterruptedException, CouponSystemException {
        if (this.companyDAO.isCompanyExistByName(company.getName())) {
            throw new CouponSystemException(ErrMsg.COMPANY_NAME_ALREADY_EXIST);
        } else if (this.companyDAO.isCompanyExistByEmail(company.getEmail())) {
            throw new CouponSystemException(ErrMsg.COMPANY_EMAIL_ALREADY_EXIST);
        }
        this.companyDAO.addCompany(company);
    }

    public void updateCompany(int companyId, Company company) throws SQLException, InterruptedException, CouponSystemException {
        Company fromDB = this.companyDAO.getOneCompany(companyId);
        if (companyId != fromDB.getId()) {
            throw new CouponSystemException(ErrMsg.COMPANY_ID_NOT_UPDATABLE);
        }
        if (!fromDB.getName().equals(company.getName())) {
            throw new CouponSystemException(ErrMsg.COMPANY_NAME_NOT_UPDATABLE);
        }
        this.companyDAO.updateCompany(companyId, company);
    }

    public void deleteCompany(int companyId) throws CouponSystemException, SQLException, InterruptedException {
        if (!this.companyDAO.isCompanyExistById(companyId)) {
            throw new CouponSystemException(ErrMsg.COMPANY_ID_NOT_EXIST);
        }
        List<Coupon> couponsOfCurrComapny;
        if (this.couponDAO.getAllCouponsOfSpecificCompany(companyId).size() != 0) {
            couponsOfCurrComapny = couponDAO.getAllCouponsOfSpecificCompany(companyId);
            for (Coupon coupon : couponsOfCurrComapny) {
                this.couponDAO.deleteCoupon(coupon.getId());
                this.couponDAO.deleteAllCouponPurchaseByCouponId(coupon.getId());
            }
        }
        this.companyDAO.deleteCompany(companyId);
    }

    public List<Company> getAllCompanies() throws SQLException, InterruptedException, CouponSystemException {

        if (this.companyDAO.getAllCompanies().size() == 0) {
            throw new CouponSystemException(ErrMsg.NO_COMPANIES_EXIST);
        }
        return this.companyDAO.getAllCompanies();
    }

    public Company getOneCompany(int companyId) throws SQLException, InterruptedException, CouponSystemException {
        if (!this.companyDAO.isCompanyExistById(companyId)) {
            throw new CouponSystemException(ErrMsg.COMPANY_ID_NOT_EXIST);
        }
        return this.companyDAO.getOneCompany(companyId);
    }

    //---------********** Customer **********---------");
    public void addCustomer(Customer customer) throws SQLException, InterruptedException, CouponSystemException {
        if (this.customerDAO.isCustomerExistByEmail(customer.getEmail())) {
            throw new CouponSystemException(ErrMsg.COMPANY_EMAIL_ALREADY_EXIST);
        }
        this.customerDAO.addCustomer(customer);
    }

    public void updateCustomer(int customerId, Customer customer) throws SQLException, InterruptedException, CouponSystemException {
        Customer fromDB = this.customerDAO.getOneCustomer(customerId);
        if (customerId != fromDB.getId()) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_ID_NOT_UPDATABLE);
        }
        this.customerDAO.updateCustomer(customerId, customer);
    }

    public void deleteCustomer(int customerId) throws CouponSystemException, SQLException, InterruptedException {
        if (!this.customerDAO.isCustomerExistById(customerId)) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_ID_NOT_EXIST);
        }

        List<Coupon> couponsOfCurrCustomer;
        if (this.couponDAO.getAllCouponsIdOfOneCustomer(customerId).size() != 0) {
            couponsOfCurrCustomer = couponDAO.getAllCouponsIdOfOneCustomer(customerId);
            for (Coupon coupon : couponsOfCurrCustomer) {
                this.couponDAO.deleteAllCouponPurchaseByCouponId(coupon.getId());
            }
            this.customerDAO.deleteCustomer(customerId);
        }
    }

    public List<Customer> getAllCustomers() throws SQLException, InterruptedException, CouponSystemException {

        if (this.customerDAO.getAllCustomeres().size() == 0) {
            throw new CouponSystemException(ErrMsg.NO_CUSTOMERS_EXIST);

        }
        return this.customerDAO.getAllCustomeres();
    }

    public Customer getOneCustomer(int customerId) throws SQLException, InterruptedException, CouponSystemException {
        if (!this.customerDAO.isCustomerExistById(customerId)) {
            throw new CouponSystemException(ErrMsg.CUSYOMER_ID_NOT_EXIST);
        }
        return this.customerDAO.getOneCustomer(customerId);
    }
}