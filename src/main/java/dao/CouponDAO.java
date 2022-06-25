package dao;

import beans.Coupon;
import exception.CouponSystemException;
import java.sql.SQLException;
import java.util.List;

public interface CouponDAO {

    void addCoupon(Coupon coupon) throws SQLException, InterruptedException;
    void updateCoupon(int couponId,Coupon coupon) throws SQLException, InterruptedException;
    void deleteCoupon(int couponId) throws SQLException, InterruptedException;
    List<Coupon> getAllCoupons() throws SQLException, InterruptedException;
    Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException;
    void addCouponPurchase(int customerId,int couponId ) throws SQLException, InterruptedException;
    void deleteCouponPurchase(int couponId) throws SQLException, InterruptedException;
    boolean isCouponExistById(int customerId) throws SQLException, InterruptedException;
    List<Coupon> getAllCouponsOfCompanyWithSpecificCategory(int companyId, int categoryId) throws SQLException, InterruptedException;
    void getAllCouponsOfCusomerWithSpecificCategory( int customerId, int categoryId) throws SQLException, InterruptedException;
    List<Coupon> getAllCouponsOfSpecificCompany(int companyId) throws SQLException, InterruptedException, CouponSystemException;
    void deleteAllCouponPurchaseByCouponId(int couponId) throws SQLException, InterruptedException;
    boolean isCouponExistByIdAndCustomerId(int couponId,int customerId) throws SQLException, InterruptedException;
    boolean isCouponAmountNotZero(int couponId) throws SQLException, InterruptedException;
    boolean isCouponExpired(int couponId) throws SQLException, InterruptedException;
    void reduceAmountOfCoupon(int couponId,int newAmount) throws SQLException, InterruptedException;
    boolean isCouponExistByTitle(int companyId,String title) throws SQLException, InterruptedException;
    List<Coupon> getAllCouponsOfCompanyBelowMaxPrice(int companyId, double maxPrice) throws SQLException, InterruptedException;
    List<Coupon> getAllCouponsIdOfOneCustomer(int customerId ) throws SQLException, InterruptedException;
     List<Integer> getAllCouponsIdThatExpired() throws SQLException, InterruptedException ;
    void deleteAllCouponsExpired() throws SQLException, InterruptedException ;
    void getAllCouponsOfCustomerBelowMaxPrice(int customerId,Double maxPrice) throws SQLException, InterruptedException;
}