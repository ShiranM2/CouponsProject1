package jobs;


import dao.CouponDAO;
import dao.CouponDAOImpl;

import exception.CouponSystemException;
import exception.ErrMsg;

import java.util.List;

public class CouponExpirationDailyJob extends Thread{//implements Runnable {

    private CouponDAO couponDAO = new CouponDAOImpl();
    private static boolean quit;


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

    public void stopJob() {
         quit=true;
    }
}