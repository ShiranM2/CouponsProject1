package facade;

import beans.Coupon;
import beans.Customer;
import dao.CompanyDAOImpl;
import dao.CouponDAOImpl;
import dao.CustomerDAOImpl;
import exception.CouponSystemException;
import exception.ErrMsg;
import java.sql.SQLException;
import java.util.List;

public class CustomerFacade extends ClientFacade {
    private Integer customerId;

    public CustomerFacade(String email, String password) throws SQLException, InterruptedException {
        if (login(email, password)) {
            companyDAO = new CompanyDAOImpl();
            couponDAO = new CouponDAOImpl();
            customerDAO = new CustomerDAOImpl();
            this.customerId = (this.customerDAO.getCustomerByEmail(email)).getId();
        } else {
            System.out.println("email or password incorrect,access denied");
        }
    }

    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException {
        return (this.customerDAO.isCustomerExists(email, password));
    }

    public List<Coupon> getAllCouponsIdOfOneCustomer() throws SQLException, InterruptedException, CouponSystemException {

        if (this.couponDAO.getAllCouponsIdOfOneCustomer(this.customerId).size() == 0) {
            throw new CouponSystemException(ErrMsg.NO_COUPONS_EXIST);

        }
         return this.couponDAO.getAllCouponsIdOfOneCustomer(this.customerId);
    }
    public void getAllCouponsOfCusomerWithSpecificCategory( Integer categoryId) throws SQLException, InterruptedException, CouponSystemException {
          this.couponDAO.getAllCouponsOfCusomerWithSpecificCategory(this.customerId,categoryId);
    }
    public void getAllCouponsOfCustomerBelowMaxPrice( Double  maxPrice) throws SQLException, InterruptedException, CouponSystemException {

        this.couponDAO.getAllCouponsOfCustomerBelowMaxPrice(this.customerId,maxPrice);
    }

    public Customer getCustomerDetails() throws SQLException, InterruptedException {

        Customer customer= this.customerDAO.getOneCustomer(customerId);
        customer.setCoupons(couponDAO.getAllCouponsIdOfOneCustomer(this.customerId));
        return  customer;
    }

    public void purchaseCoupon(int couponid) throws SQLException, InterruptedException, CouponSystemException {

        Coupon couponObj=couponDAO.getOneCoupon(couponid);

        if (this.couponDAO.isCouponExistByIdAndCustomerId(couponid, customerId)) //==true
        {
            throw new CouponSystemException(ErrMsg.THIS_COUPON_ALREADY_BEEN_PURCHASED_BY_CUSTOMER);
        }
        if (!this.couponDAO.isCouponAmountNotZero(couponid)) //==false
        {
            throw new CouponSystemException(ErrMsg.THIS_COUPON_IS_OUT_OF_STOCK_RIGHT_NOW);
        }
        if (this.couponDAO.isCouponExpired(couponid)) //==true
        {
            throw new CouponSystemException(ErrMsg.THIS_COUPON_EXPIRED);
        }

        this.couponDAO.addCouponPurchase(customerId, couponid);
        this.couponDAO.reduceAmountOfCoupon(couponid, couponObj.getAmount() - 1);
    }
}