package facade;

import beans.*;
import dao.*;
import exception.CouponSystemException;
import exception.ErrMsg;
import beans.Company;

import java.sql.SQLException;
import java.util.List;

public class CompanyFacade extends ClientFacade {
    private Integer companyid;

    public CompanyFacade(String email, String password) throws SQLException, InterruptedException {
        if (login(email, password)) {
            companyDAO = new CompanyDAOImpl();
            couponDAO = new CouponDAOImpl();
            customerDAO = new CustomerDAOImpl();
            this.companyid = (companyDAO.getOneCompanyByEmail(email)).getId();
        } else {
            System.out.println("email or password incorrect,access denied");
        }
    }

    @Override
    public boolean login(String email, String password) throws SQLException, InterruptedException {
        return (this.companyDAO.isCompanyExists(email, password));
    }

    //---------********** Coupon **********---------");

    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException, CouponSystemException {
        if (this.couponDAO.isCouponExistByTitle(coupon.getCompanyId(), coupon.getTitle())) {
            throw new CouponSystemException(ErrMsg.COUPON_WITH_THIS_TITLE_ALREADY_EXIST);
        }
        this.couponDAO.addCoupon(coupon);
    }


    public void updateCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException, CouponSystemException {

        Coupon fromDB = this.couponDAO.getOneCoupon(couponId);
        if (coupon.getId() != fromDB.getId()) {
            throw new CouponSystemException(ErrMsg.COUPON_ID_NOT_UPDATABLE);
        }
        if (fromDB.getCompanyId() != (coupon.getCompanyId())) {
            throw new CouponSystemException(ErrMsg.COMPANY_ID_NOT_UPDATABLE);
        }
        this.couponDAO.updateCoupon(couponId, coupon);
    }

    public void deleteCoupon(int couponId) throws CouponSystemException, SQLException, InterruptedException {
        if (!this.couponDAO.isCouponExistById(couponId)) {
            throw new CouponSystemException(ErrMsg.COUPON_ID_NOT_EXIST);
        }
        this.couponDAO.deleteCoupon(couponId);
        this.couponDAO.deleteCouponPurchase(couponId);
    }

    public Company getCompanyDetails() throws SQLException, InterruptedException {
        return this.companyDAO.getOneCompany(companyid);
    }


    public List<Integer> getAllCouponsIdThatExpired() throws SQLException, InterruptedException {
        return this.couponDAO.getAllCouponsIdThatExpired();
    }

    public List<Coupon> getAllCouponsOfOneCompany() throws SQLException, InterruptedException, CouponSystemException {

        List<Coupon> couponsList = this.companyDAO.getAllCouponsOfOneCompany(this.companyid);
        if (couponsList.size() == 0) {
            throw new CouponSystemException(ErrMsg.NO_COUPONS_EXIST);

        }
        return couponsList;
    }

    public List<Coupon> getAllCouponsOfSpecificCategory(Category category) throws SQLException, InterruptedException, CouponSystemException {


        List<Coupon> couponsofCurrCompanyAndCategory = this.couponDAO.getAllCouponsOfCompanyWithSpecificCategory(this.companyid, category.ordinal() + 1);
        if (couponsofCurrCompanyAndCategory.size() == 0) {
            throw new CouponSystemException(ErrMsg.NO_COUPONS_EXIST);

        }
        return couponsofCurrCompanyAndCategory;


    }

    public List<Coupon> getAllCouponsOfCompanyBelowMaxPrice(double maxPrice) throws SQLException, InterruptedException, CouponSystemException {
        List<Coupon> coupons = this.couponDAO.getAllCouponsOfCompanyBelowMaxPrice(this.companyid, maxPrice);
        if (coupons.size() == 0) {
            throw new CouponSystemException(ErrMsg.NO_COUPONS_EXIST);

        }
        return coupons;
    }
}