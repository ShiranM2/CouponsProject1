package facade;

import beans.ClientType;
import exception.CouponSystemException;
import exception.ErrMsg;

import java.sql.SQLException;

public class LoginManager {
    private static final LoginManager instance = new LoginManager();
    private AdminFacade adminFacade;
    private CompanyFacade companyFacade;
    private CustomerFacade customerFacade;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, InterruptedException, CouponSystemException {

        if (clientType.equals(clientType.Administrator)) {// && adminFacade!=null){

            adminFacade = new AdminFacade(email, password);
            return adminFacade;

        } else if (clientType.equals(clientType.Company)) {// && companyFacade!=null){

            companyFacade = new CompanyFacade(email, password);
            return companyFacade;

        } else if (clientType.equals(clientType.Customer)) {// && customerFacade!=null){
            customerFacade = new CustomerFacade(email, password);
            return customerFacade;
        } else {
            throw new CouponSystemException(ErrMsg.LOGIN_FAILED);
        }
    }
}