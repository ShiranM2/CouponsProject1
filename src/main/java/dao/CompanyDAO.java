package dao;

import beans.Company;
import beans.Coupon;
import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO {
    boolean isCompanyExists(String email,String password) throws SQLException, InterruptedException;
    void addCompany(Company company) throws SQLException, InterruptedException;
    void updateCompany(int companyId,Company company) throws SQLException, InterruptedException;
    void deleteCompany(int companyId) throws SQLException, InterruptedException;
    List<Company> getAllCompanies() throws SQLException, InterruptedException;
    Company getOneCompany(int companyId) throws SQLException, InterruptedException;
    boolean isCompanyExistByName(String nameOfCompany) throws SQLException, InterruptedException;
    boolean isCompanyExistByEmail(String emailOfCompany) throws SQLException, InterruptedException;
    boolean isCompanyExistById(int companyId) throws SQLException, InterruptedException;
    Company getOneCompanyByEmail(String email) throws SQLException, InterruptedException;
    List<Coupon>  getAllCouponsOfOneCompany(int companyId)throws SQLException, InterruptedException;
}