package jobs;

import beans.ClientType;
import beans.Coupon;
import dao.CouponDAO;
import dao.CouponDAOImpl;
import db.ResultsUtils;
import exception.CouponSystemException;
import exception.ErrMsg;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.LoginManager;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CouponExpirationDailyJob extends Thread{//implements Runnable {

    private CouponDAO couponDAO = new CouponDAOImpl();
    private static boolean quit;
//    private AdminFacade adminFacade;

//    public CouponExpirationDailyJob() {
//    }

    @Override
    public void run() {
        System.out.println("start CouponExpirationDailyJob->run()");
        while (!quit) {
            try {
                couponDAO.deleteAllCouponsExpired();
                List<Integer> couponsIdThatExpired = couponDAO.getAllCouponsIdThatExpired();
                for (Integer obj : couponsIdThatExpired) {
                    couponDAO.deleteCouponPurchase(obj);
                }

            } catch (Exception e) {
                try {
                    throw new CouponSystemException(ErrMsg.JOB_EXCEPTION);
                } catch (CouponSystemException e2) {
                    e2.getMessage();
                }
            }
            try {
                Thread.sleep(1000 * 60 * 60 * 24);
                System.out.println("END CouponExpirationDailyJob->run()");
            } catch (InterruptedException e) {
                try {
                    throw new CouponSystemException(ErrMsg.INTERRUPTED_EXCEPTION);
                } catch (CouponSystemException e2) {
                    e2.getMessage();
                }
            }


        }


    }

//    public CouponExpirationDailyJob(CouponDAO couponDAO, boolean quit) throws SQLException, InterruptedException, CouponSystemException {
//        ClientFacade clientFacade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Administrator);
//        AdminFacade adminFacade = (AdminFacade) clientFacade;
//
//        this.quit = quit;
//    }


    public void stopJob() {
         quit=true;
    }
}