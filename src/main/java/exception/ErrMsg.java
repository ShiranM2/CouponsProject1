package exception;

import beans.Coupon;

public enum ErrMsg {
    //---------********** Login **********---------");
    LOGIN_FAILED("Login failed..."),
    //---------********** Company **********---------");
    COMPANY_EMAIL_ALREADY_EXIST("company email already exist..."),

    COMPANY_NAME_ALREADY_EXIST("company name already exist..."),

    COMPANY_ID_NOT_UPDATABLE("company id not updatable..."),

    COMPANY_ID_NOT_EXIST("company id not exist..."),

    COMPANY_NAME_NOT_UPDATABLE("company name not updatable..."),

    NO_COMPANIES_EXIST("there is no companies exist..."),


    //---------********** Customer **********---------");
    CUSTOMER_EMAIL_ALREADY_EXIST("customer email already exist..."),
    CUSTOMER_ID_NOT_UPDATABLE("customer id not updatable..."),
    CUSTOMER_ID_NOT_EXIST("customer id not exist..."),
    NO_CUSTOMERS_EXIST("there is no custmers exist..."),
    CUSYOMER_ID_NOT_EXIST("customer id not exist..."),

    //---------********** Coupon **********---------");


    COUPON_ID_NOT_UPDATABLE("coupon id not updatable..."),
    COUPON_ID_NOT_EXIST("coupon id not exist..."),
    NO_COUPONS_EXIST("no coupons exist..."),
    THIS_COUPON_ALREADY_BEEN_PURCHASED_BY_CUSTOMER("this coupon already been purchased by this customer...Can't purchase coupon more than one time!"),

    THIS_COUPON_IS_OUT_OF_STOCK_RIGHT_NOW("this coupon is out of stock right now..."),
    THIS_COUPON_EXPIRED("this coupon expired"),
    COUPON_WITH_THIS_TITLE_ALREADY_EXIST("coupon with this title already exist"),

    JOB_EXCEPTION("Job exception..."),
    INTERRUPTED_EXCEPTION("Interrupted Exception...");
    private String msg;

    ErrMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
