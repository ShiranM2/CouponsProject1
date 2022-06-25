package db;


import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;

import java.sql.Date;
import java.util.HashMap;


public class ResultsUtils {

    public static Company companyFromRow(HashMap<String, Object> map) {
        int id = (int) map.get("id");
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String password = (String) map.get("password");

        return new Company(id, name, email, password);
    }

    public static Customer customerFromRow(HashMap<String, Object> map) {

        int id = (int) map.get("id");
        String firstName = (String) map.get("firstName");
        String lastName = (String) map.get("lastName");
        String email = (String) map.get("email");
        String password = (String) map.get("password");

        return new Customer(id, firstName, lastName, email, password);
    }

    public static Coupon couponFromRow(HashMap<String, Object> map) {

        int id = (int) map.get("id");
        int companyId = (int) map.get("companyId");
        int categoryId = (int) map.get("categoryId");
        String title = (String) map.get("title");
        String description = (String) map.get("description");
        Date startDate = (Date) map.get("startDate");
        Date endDate = (Date) map.get("endDate");
        int amount = (int) map.get("amount");
        double price = (double) map.get("price");
        String image = (String) map.get("image");
        return new Coupon(id, companyId, categoryId, title, description, startDate, endDate, amount, price, image);


    }

    public static boolean booleanFromRow(HashMap<String, Object> map) {
        long val = (long) map.get("res");
        return val == 1;
    }


}
