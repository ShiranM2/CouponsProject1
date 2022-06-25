package dao;

import beans.Coupon;
import db.JDBCUtils;
import db.ResultsUtils;
import exception.CouponSystemException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDAOImpl implements CouponDAO {
    private static final String QUERY_INSERT = "INSERT INTO `coupon-project`.`coupons` (`companyId`, `categoryId`, `title`,`description`, `startDate`, `endDate`,`amount`, `price`, `image`) VALUES (?, ?, ?,?, ?, ?,?, ?, ?);";
    private static final String QUERY_UPDATE = "UPDATE `coupon-project`.`coupons` SET `companyId` =?,`categoryId`=?,`title`=?,`description`=?,`startDate`=?,`endDate`=?,`amount`=?,`price`=?,`image`=? WHERE (`id`=?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-project`.`coupons` WHERE (`id`=?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-project`.`coupons`";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-project`.`coupons` WHERE (`id`=?);";
    private static final String QUERY_ADD_COUPON_PURCHASE = " INSERT INTO `coupon-project`.`customers_vs_coupons` (`customeId`, `couponid`) VALUES (?, ?);";
    private static final String QUERY_DELETE_COUPON_PURCHASE = "DELETE FROM `coupon-project`.`customers_vs_coupons` WHERE ( `couponid`=?);";
    private static final String QUERY_IF_ID_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-project`.coupons WHERE `id`=?  ) AS res;";
    private static final String QUERY_GET_ALL_COUPONS_OF_COMPANY_WITH_SPECIFIC_CATEGORY = "SELECT * FROM `coupon-project`.`coupons` WHERE (`companyId`=? AND `categoryId`=?);";
    private static final String QUERY_GET_ALL_COUPONS_OF_SPECIFIC_COMPANY = "SELECT * FROM `coupon-project`.`coupons` WHERE (`companyId`=?);";
    private static final String QUERY_DELETE_ALL_COUPONs_PURCHASE_BY_COUPONID = "DELETE FROM `coupon-project`.`customers_vs_coupons` WHERE (`couponid`=?);";
    private static final String QUERY_IF_EXIST_BY_COUPONID_AND_CUSTOMERID = "SELECT EXISTS(SELECT * FROM `coupon-project`.customers_vs_coupons WHERE (`couponid`=? AND  `customeId`=?) ) AS res;";
    private static final String QUERY_IS_COUPON_AMOUNT_NOT_ZERO = "SELECT EXISTS(SELECT * FROM `coupon-project`.coupons WHERE (`id`=? AND `amount`>0 ) ) AS res;";
    private static final String QUERY_IS_COUPON_EXPIRED = " SELECT EXISTS(SELECT * FROM `coupon-project`.coupons WHERE (`id`=? AND `endDate`<current_date()) ) AS res;";
    private static final String QUERY_GET_ALL_COUPONS_THAT_EXPIRED = " SELECT * FROM `coupon-project`.coupons WHERE ( `endDate`<current_date()) ;";
    private static final String QUERY_REDUCE_AMOUNT_OF_COUPON = "UPDATE `coupon-project`.`coupons` SET `amount` = ? WHERE (`id` = ?);";
    private static final String QUERY_IS_COUPON_EXIST_BY_TITLE = "SELECT EXISTS(SELECT * FROM `coupon-project`.coupons WHERE (`companyId`=? AND `title`=? ) ) AS res;";
    private static final String QUERY_GET_ALL_COUPONS_OF_COMAPNY_BELOW_MAX_PRICE = "SELECT * FROM `coupon-project`.coupons WHERE (`companyId`=? AND `price`<? ) ;";
    private static final String QUERY_GET_ALL_COUPONS_OF_CUSTOMER_BELOW_MAX_PRICE = "SELECT * FROM `coupon-project`.`coupons` a INNER JOIN  `coupon-project`.`customers_vs_coupons` c ON a.`id`=c.`couponId` AND c.`customeId`=? AND a.`price`<=?  ;";
    private static final String QUERY_GET_ALL_COUPONS_OF_CUSTOMER_WITH_SPECIFIC_CATEGORY = "SELECT * FROM `coupon-project`.`coupons` a RIGHT JOIN  `coupon-project`.`customers_vs_coupons` c ON a.`id`=c.`couponId` AND c.`customeId`=? AND a.`categoryId`=? ;";
    private static final String QUERY_GET_ALL_COUPONS_OF_ONE_CUSTOMER = "SELECT *   FROM `coupon-project`.`coupons` a RIGHT JOIN  `coupon-project`.`customers_vs_coupons` c ON a.`id`=c.`couponId` AND c.`customeId`=?;";
    private static final String QUERY_DELETEA_ALL_COUPONS_EXPIRED = "DELETE FROM `coupon-project`.`coupons` WHERE (`endDate`<current_date());";

    @Override
    public void addCoupon(Coupon coupon) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory());
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        JDBCUtils.executeQuery(QUERY_INSERT, params);
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory());
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        params.put(10, couponId);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }

    @Override
    public void deleteCoupon(int couponId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        JDBCUtils.executeQuery(QUERY_DELETE, params);
    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for (Object obj : rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public Coupon getOneCoupon(int couponId) throws SQLException, InterruptedException {
        Coupon coupon = null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);
        try {
            coupon = ResultsUtils.couponFromRow((HashMap<String, Object>) rows.get(0));

        } catch (Exception e) {
            System.out.println(e);
        }
        return coupon;

    }

    @Override
    public List<Integer> getAllCouponsIdThatExpired() throws SQLException, InterruptedException {
        List<Integer> couponsList = new ArrayList<>();
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS_THAT_EXPIRED);

        for (Object obj : rows) {
            couponsList.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj).getId());
        }
        return couponsList;
    }

    @Override
    public void deleteAllCouponsExpired() throws SQLException, InterruptedException {
        JDBCUtils.executeQuery(QUERY_DELETEA_ALL_COUPONS_EXPIRED);
    }

    @Override
    public void addCouponPurchase(int customerId, int couponId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, couponId);
        JDBCUtils.executeQuery(QUERY_ADD_COUPON_PURCHASE, params);
    }

    @Override
    public void deleteCouponPurchase(int couponId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();

        params.put(1, couponId);
        JDBCUtils.executeQuery(QUERY_DELETE_COUPON_PURCHASE, params);
    }

    @Override
    public boolean isCouponExistById(int couponId) throws SQLException, InterruptedException {

        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IF_ID_EXIST, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;

    }

    @Override
    public List<Coupon> getAllCouponsOfCompanyWithSpecificCategory(int companyId, int categoryId) throws SQLException, InterruptedException {

        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, categoryId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS_OF_COMPANY_WITH_SPECIFIC_CATEGORY, params);
        for (Object obj : rows) {
            coupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return coupons;
    }

    @Override
    public void getAllCouponsOfCusomerWithSpecificCategory(int customerId, int categoryId) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, categoryId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS_OF_CUSTOMER_WITH_SPECIFIC_CATEGORY, params);
        rows.forEach(System.out::println);
    }

    @Override
    public List<Coupon> getAllCouponsOfSpecificCompany(int companyId) throws CouponSystemException, SQLException, InterruptedException {
        List<Coupon> listOfCoupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS_OF_SPECIFIC_COMPANY, params);
        if (rows.size() > 0) {
            listOfCoupons = (List<Coupon>) ResultsUtils.couponFromRow((HashMap<String, Object>) rows.get(0));
        }
        return listOfCoupons;
    }

    @Override
    public void deleteAllCouponPurchaseByCouponId(int couponId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        JDBCUtils.executeQuery(QUERY_DELETE_ALL_COUPONs_PURCHASE_BY_COUPONID, params);
    }

    @Override
    public boolean isCouponExistByIdAndCustomerId(int couponId, int customerId) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        params.put(2, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IF_EXIST_BY_COUPONID_AND_CUSTOMERID, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public boolean isCouponAmountNotZero(int couponId) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_COUPON_AMOUNT_NOT_ZERO, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public boolean isCouponExpired(int couponId) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_COUPON_EXPIRED, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public void reduceAmountOfCoupon(int couponId, int newAmount) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, newAmount);
        params.put(2, couponId);
        JDBCUtils.executeQuery(QUERY_REDUCE_AMOUNT_OF_COUPON, params);
    }

    @Override
    public boolean isCouponExistByTitle(int companyId, String title) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, title);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_IS_COUPON_EXIST_BY_TITLE, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public List<Coupon> getAllCouponsOfCompanyBelowMaxPrice(int companyId, double maxPrice) throws SQLException, InterruptedException {
        List<Coupon> listOfCoupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        params.put(2, maxPrice);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS_OF_COMAPNY_BELOW_MAX_PRICE, params);
        for (Object obj : rows) {
            listOfCoupons.add(ResultsUtils.couponFromRow((HashMap<String, Object>) obj));
        }
        return listOfCoupons;
    }

    @Override
    public List<Coupon> getAllCouponsIdOfOneCustomer(int customerId) throws SQLException, InterruptedException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS_OF_ONE_CUSTOMER, params);
        return (List<Coupon>) rows;
    }


    @Override
    public void getAllCouponsOfCustomerBelowMaxPrice(int customerId, Double maxPrice) throws SQLException, InterruptedException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        params.put(2, maxPrice);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS_OF_CUSTOMER_BELOW_MAX_PRICE, params);
        rows.forEach(System.out::println);
    }
}