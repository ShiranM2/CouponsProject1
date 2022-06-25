import beans.*;
import db.ConnectionPool;
import db.DatabaseManager;
import exception.CouponSystemException;
import facade.*;
import jobs.CouponExpirationDailyJob;
//import jobs.CouponExpirationDailyJob;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/*
 * 1.לבדוק התחברות לא נכונה עם יוזר-לא אמור לתת לבצע דברים
 *בג'וב להוסיף מחיקת רכישות של הקופונים

 * להעלות לגיט
 * */
public class Test {
    public static void testAll() throws Exception {
        System.out.println("---------**********------------------**********--------- START testAll ---------**********------------------**********---------\n");
        DatabaseManager.getInstance().dropCreateStrategy();
        DatabaseManager.getInstance().insertCategories();

//        CouponExpirationDailyJob job1=new CouponExpirationDailyJob();
//        Thread t1=new Thread(job1);
//        t1.run();
        System.out.println("---------**********------------------ ClientFacade Testing ------------------**********---------\n");

        System.out.println("---------------------------------AdminFacade Testing---------------------------------\n");
        ClientFacade clientFacade = null;
        AdminFacade adminFacade = null;
        CompanyFacade companyFacade = null;
        CustomerFacade customerFacade = null;
        System.out.println("-------1. login into system-------");
        try {
            clientFacade = LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Administrator);
            adminFacade = (AdminFacade) clientFacade;
        } catch (CouponSystemException e) {
            System.out.println(e);
//                return;
        }

        System.out.println("-------2. add new companies-------");
        try {

            adminFacade.addCompany(new Company("castro", "castro@gmail.com", "castro1"));
            adminFacade.addCompany(new Company("fox", "fox@gmail.com", "fox1"));
            adminFacade.addCompany(new Company("golf", "golf@gmail.com", "golf"));
            adminFacade.addCompany(new Company("supersal", "supersal@gmail.com", "supersal1"));
            adminFacade.addCompany(new Company("victori", "victori@gmail.com", "victori1"));
            System.out.println(adminFacade.getAllCompanies());
        } catch (CouponSystemException e) {
            System.out.println(e);
        }

        System.out.println("-------3. update exist company->company with companyId=1-------");
        try {
            adminFacade.updateCompany(1, new Company("castro", "castro2@gmail.com", "castro2"));
            System.out.println(adminFacade.getAllCompanies());
            System.out.println(adminFacade.getOneCompany(1));
        } catch (CouponSystemException e) {
            System.out.println(e);
        }
        System.out.println("-------4. delete exist company->company with companyId=1-------");
        try {
            adminFacade.deleteCompany(1);
            System.out.println(adminFacade.getAllCompanies());
        } catch (CouponSystemException e) {
            System.out.println(e);
        }
        System.out.println("-------5. get all companies-------");
        try {
            System.out.println(adminFacade.getAllCompanies());
        } catch (CouponSystemException e) {
            System.out.println(e);
        }

        System.out.println("-------6. get specific company acoording to companyId->company with companyId=3-------");
        try {
            System.out.println(adminFacade.getOneCompany(3));
        } catch (CouponSystemException e) {
            System.out.println(e);
        }
        System.out.println("-------7. add new customers-------");

        try {
            adminFacade.addCustomer(new Customer("shiran", "maimon", "shiran@gmail.com", "123456"));
            adminFacade.addCustomer(new Customer("nir", "naaman", "nir@gmail.com", "12"));
            adminFacade.addCustomer(new Customer("tamar", "maimon", "tamar@gmail.com", "123"));
            System.out.println(adminFacade.getAllCustomers());
        } catch (CouponSystemException e) {
            System.out.println(e);
        }


        System.out.println("-------8. update exist customer->customer with customerId=1-------");
        try {
            adminFacade.updateCustomer(1, new Customer("shiran", "naaman", "shiranN@gmail.com", "1234"));
            System.out.println(adminFacade.getAllCustomers());
        } catch (CouponSystemException e) {
            System.out.println(e);
        }
        System.out.println("-------9. delete exist customer->customer with customerId=3-------");
        try {
            adminFacade.deleteCustomer(3);
            System.out.println(adminFacade.getAllCustomers());
        } catch (CouponSystemException e) {
            System.out.println(e);
        }
        System.out.println("-------10. get all customers-------");
        try {
            System.out.println(adminFacade.getAllCustomers());
        } catch (CouponSystemException e) {
            System.out.println(e);
        }
        System.out.println("-------11. get specific customer acoording to customerId->customer with customerId=1-------");
        try {
            System.out.println(adminFacade.getOneCustomer(1));
        } catch (CouponSystemException e) {
            System.out.println(e);
        }


        System.out.println("---------------------------------2. CompanyFacade Testing---------------------------------\n");
        try {
            try {
                System.out.println("-------1. login into system-------");
                clientFacade = LoginManager.getInstance().login("fox@gmail.com", "fox1", ClientType.Company);
                companyFacade = (CompanyFacade) clientFacade;
                System.out.println("User from CompanyFacade connect succefully to system! name of company: " + companyFacade.getCompanyDetails().getName());
            } catch (CouponSystemException e) {
                System.out.println(e);
            }

            System.out.println("-------2. add new coupons-------");
            try {
                companyFacade.addCoupon(new Coupon(2, 1, "t1", "deal in market", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 8, 30)), 10, 200, "image1"));
                companyFacade.addCoupon(new Coupon(2, 1, "t2", "d2", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 06, 30)), 10, 200, "image2"));
                companyFacade.addCoupon(new Coupon(2, 1, "ELECTRICITYshop", "deal in ELECTRICITYshop", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 06, 25)), 10, 200, "image2"));
                companyFacade.addCoupon(new Coupon(2, 1, "title3", "d3", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 8, 01)), 100, 50, "image3"));
                companyFacade.addCoupon(new Coupon(3, 3, "title4", "d4", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2021, 07, 10)), 5, 150, "image4"));
                companyFacade.addCoupon(new Coupon(3, 1, "title5", "d5", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 12, 14)), 1, 500, "image5"));
                System.out.println(companyFacade.getAllCouponsOfOneCompany());
            } catch (CouponSystemException e) {
                System.out.println(e);
            }
            System.out.println("-------3. update exist coupon->coupon with couponId=1-------");
            try {
                companyFacade.updateCoupon(1, new Coupon(1, 2, 1, "t2", "d2", Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.of(2022, 06, 30)), 10, 200, "image2"));
                ;
            } catch (CouponSystemException e) {
                System.out.println(e);
            }
            System.out.println("-------4. delete exist coupon->coupon with couponId=1-------");
            try {
                companyFacade.deleteCoupon(1);
                System.out.println(companyFacade.getAllCouponsOfOneCompany());
            } catch (CouponSystemException e) {
                System.out.println(e);
            }
            System.out.println("-------5. get all coupons of this company-------");
            try {
                System.out.println(companyFacade.getAllCouponsOfOneCompany());
            } catch (CouponSystemException e) {
                System.out.println(e);
            }
            System.out.println("-------6. get all coupons acoording to categoryId->coupons with categoryId=FOOD=1 -------");
            try {
                System.out.println(companyFacade.getAllCouponsOfSpecificCategory(Category.FOOD));
            } catch (CouponSystemException e) {
                System.out.println(e);
            }
            System.out.println("-------7. get all coupons acoording to max price->coupons with maxPrice=2000 -------");
            try {
                System.out.println(companyFacade.getAllCouponsOfCompanyBelowMaxPrice(2000));
            } catch (CouponSystemException e) {
                System.out.println(e);
            }
            System.out.println("-------8. get company's details-------");
            System.out.println(companyFacade.getCompanyDetails());

            System.out.println("---------------------------------3. CustomerFacade Testing---------------------------------\n");

            try {
                System.out.println("-------1. login into system-------");
                clientFacade = LoginManager.getInstance().login("shiranN@gmail.com", "1234", ClientType.Customer);
                customerFacade = (CustomerFacade) clientFacade;
                System.out.println("User from CustomerFacade connect succefully to system! Details about customer: " + customerFacade.getCustomerDetails());
            } catch (CouponSystemException e) {
                System.out.println(e);
            }

            System.out.println("-------2. Purchase new coupons-------");

            try {
                customerFacade.purchaseCoupon(2);
                customerFacade.purchaseCoupon(3);


                customerFacade.purchaseCoupon(3);
            } catch (CouponSystemException e) {
                System.out.println(e);
            }


//            customerFacade.purchaseCoupon(3);
//            customerFacade.purchaseCoupon(4);

            System.out.println("-------3. get all the coupons that customer Purchased -------");
            try {
                System.out.println(customerFacade.getAllCouponsIdOfOneCustomer());

            } catch (CouponSystemException e) {
                System.out.println(e);
            }
            System.out.println("-------4. get all the coupons from specific category that customer Purchased -------");
            try {
                customerFacade.getAllCouponsOfCusomerWithSpecificCategory(Category.FOOD.ordinal() + 1);
            } catch (CouponSystemException e) {
                System.out.println(e);
            }
            System.out.println("-------5. get all the coupons below to max price that customer Purchased -------");
            try {
                customerFacade.getAllCouponsOfCustomerBelowMaxPrice(1000.0);
            } catch (CouponSystemException e) {
                System.out.println(e);
            }

            System.out.println("-------6. get customer details -------");
            System.out.println(customerFacade.getCustomerDetails());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------------4. Daily Job to delete Expired coupons Testing---------------------------------");
        Thread thread = new CouponExpirationDailyJob();

        thread.start();
        System.out.println("Get all the Expired Coupons");
        System.out.println(companyFacade.getAllCouponsIdThatExpired());
        System.out.println("After removal of expired coupons");
        System.out.println(companyFacade.getAllCouponsIdThatExpired());

        ((CouponExpirationDailyJob) thread).stopJob();
        try {
            ConnectionPool.getInstance().closeAllConnections();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("---------**********------------------**********--------- END testAll ---------**********------------------**********---------");
    }
}