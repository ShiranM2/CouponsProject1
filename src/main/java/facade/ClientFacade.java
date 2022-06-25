package facade;

import dao.*;
import java.sql.SQLException;

public abstract class ClientFacade {

    protected CompanyDAO companyDAO=new CompanyDAOImpl();
    protected CustomerDAO customerDAO=new CustomerDAOImpl();
    protected CouponDAO couponDAO=new CouponDAOImpl();

    public abstract boolean login(String email,String password) throws SQLException, InterruptedException;
}