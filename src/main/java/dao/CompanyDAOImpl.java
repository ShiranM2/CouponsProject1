package dao;

import beans.Company;
import beans.Coupon;
import db.JDBCUtils;
import db.ResultsUtils;
import java.sql.SQLException;
import java.util.*;

public class CompanyDAOImpl implements CompanyDAO {

    private static final String QUERY_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-project`.companies WHERE `email`=? AND `password`=?) AS res;";
    private static final String QUERY_INSERT = "INSERT INTO `coupon-project`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);";
    private static final String QUERY_UPDATE = "UPDATE `coupon-project`.`companies` SET `name` =?,`email`=?,`password`=? WHERE (`id`=?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-project`.`companies` WHERE (`id`=?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-project`.`companies`";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-project`.`companies` WHERE (`id`=?);";
    private static final String QUERY_IF_NAME_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-project`.companies WHERE `name`=?  ) AS res;";
    private static final String QUERY_IF_EMAIL_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-project`.companies WHERE `email`=?  ) AS res;";
    private static final String QUERY_IF_ID_EXIST = "SELECT EXISTS(SELECT * FROM `coupon-project`.companies WHERE `id`=?  ) AS res;";
    private static final String QUERY_GET_ONE_BY_EMAIL = "SELECT * FROM `coupon-project`.`companies` WHERE (`email`=?);";
    private static final String QUERY_GET_ALL_COUPONS = "SELECT * FROM `coupon-project`.`coupons` WHERE (`companyId`=?);";

    @Override
    public boolean isCompanyExists(String email, String password) throws SQLException, InterruptedException {
        boolean res = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        params.put(2, password);
        List<?> rows = JDBCUtils.executeQueryWithResults(QUERY_EXIST, params);
        res = ResultsUtils.booleanFromRow((HashMap<String, Object>) rows.get(0));
        return res;
    }

    @Override
    public void addCompany(Company company) throws SQLException, InterruptedException {

        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        JDBCUtils.executeQuery(QUERY_INSERT, params);
    }

    @Override
    public void updateCompany(int companyId, Company company) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, company.getName());
        params.put(2, company.getEmail());
        params.put(3, company.getPassword());
        params.put(4, companyId);
        JDBCUtils.executeQuery(QUERY_UPDATE, params);
    }


    @Override
    public void deleteCompany(int companyId) throws SQLException, InterruptedException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        JDBCUtils.executeQuery(QUERY_DELETE,params);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException, InterruptedException {
        List<Company> companies =new ArrayList<>();
        List<?> rows =JDBCUtils.executeQueryWithResults(QUERY_GET_ALL);
        for(Object obj:rows){
            companies.add(ResultsUtils.companyFromRow((HashMap<String,Object>) obj));
        }
        return  companies;
    }

    @Override
    public Company getOneCompany(int companyId) throws SQLException, InterruptedException {
        Company company=null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> rows=JDBCUtils.executeQueryWithResults(QUERY_GET_ONE, params);

        try{
            company=ResultsUtils.companyFromRow((HashMap<String, Object>) rows.get(0));

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        List<Coupon> coupons=getAllCouponsOfOneCompany(companyId);
        company.setCoupons(coupons);
         return  company;

    }
    @Override
    public Company getOneCompanyByEmail(String email) throws SQLException, InterruptedException{
        Company company=null;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        List<?> rows=JDBCUtils.executeQueryWithResults(QUERY_GET_ONE_BY_EMAIL, params);

        try{
            company=ResultsUtils.companyFromRow((HashMap<String, Object>) rows.get(0));

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return  company;

    }

    @Override
    public List<Coupon> getAllCouponsOfOneCompany(int companyId) throws SQLException, InterruptedException {

        List<Coupon> coupons =new ArrayList<>();
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        List<?> rows=JDBCUtils.executeQueryWithResults(QUERY_GET_ALL_COUPONS, params);
        for(Object obj:rows){
            coupons.add(ResultsUtils.couponFromRow((HashMap<String,Object>) obj));
        }
        return  coupons;


    }

    @Override
    public boolean isCompanyExistByName(String nameOfCompany) throws SQLException, InterruptedException {
        boolean res=false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1,nameOfCompany);
        List<?> rows=JDBCUtils.executeQueryWithResults(QUERY_IF_NAME_EXIST, params);
        res=ResultsUtils.booleanFromRow((HashMap<String,Object> ) rows.get(0));
return res;

    }

    @Override
    public boolean isCompanyExistByEmail(String emailOfCompany) throws SQLException, InterruptedException {
        boolean res=false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1,emailOfCompany);
        List<?> rows=JDBCUtils.executeQueryWithResults(QUERY_IF_EMAIL_EXIST, params);
        res=ResultsUtils.booleanFromRow((HashMap<String,Object> ) rows.get(0));
        return res;
    }

    @Override
    public boolean isCompanyExistById(int companyId) throws SQLException, InterruptedException {
        boolean res=false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1,companyId);
        List<?> rows=JDBCUtils.executeQueryWithResults(QUERY_IF_ID_EXIST, params);
        res=ResultsUtils.booleanFromRow((HashMap<String,Object> ) rows.get(0));
        return res;
    }
}