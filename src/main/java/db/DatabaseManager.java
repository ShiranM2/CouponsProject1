package db;

import beans.Category;
import dao.CategoryDAO;
import dao.CategoryDAOImpl;

import java.sql.SQLException;

public class DatabaseManager {

    private static final DatabaseManager instance = new DatabaseManager();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private static final String QUERY_CREATE_SCHEMA = "CREATE SCHEMA `coupon-project`;";
    private static final String QUERY_DROP_SCHEMA = "DROP DATABASE `coupon-project`;";
    private static final String QUERY_CREATE_TABLE_COMPANIES = "CREATE TABLE `coupon-project`.`companies` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";
    private static final String QUERY_CREATE_TABLE_CUSTOMERS = "CREATE TABLE `coupon-project`.`customers` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `firstName` VARCHAR(45) NOT NULL,\n" +
            "  `lastName` VARCHAR(45) NOT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";
    private static final String QUERY_CREATE_TABLE_CATEGORIES = "CREATE TABLE `coupon-project`.`categories` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`));\n";
    private static final String QUERY_CREATE_TABLE_COUPONS = "CREATE TABLE `coupon-project`.`coupons` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `companyId` INT NOT NULL,\n" +
            "  `categoryId` INT NOT NULL,\n" +
            "  `title` VARCHAR(45) NOT NULL,\n" +
            "  `description` VARCHAR(45) NOT NULL,\n" +
            "  `startDate` DATE NOT NULL,\n" +
            "  `endDate` DATE NOT NULL,\n" +
            "  `amount` INT NOT NULL,\n" +
            "  `price` DOUBLE NOT NULL,\n" +
            "  `image` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `categoryiD_idx` (`categoryId` ASC) VISIBLE,\n" +
            "  INDEX `companyId_idx` (`companyId` ASC) VISIBLE,\n" +
            "  CONSTRAINT `companyId`\n" +
            "    FOREIGN KEY (`companyId`)\n" +
            "    REFERENCES `coupon-project`.`companies` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `categoryId`\n" +
            "    FOREIGN KEY (`categoryId`)\n" +
            "    REFERENCES `coupon-project`.`categories` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";


    private static final String QUERY_CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE `coupon-project`.`customers_vs_coupons` (\n" +
            "  `customeId` INT NOT NULL,\n" +
            "  `couponId` INT NOT NULL,\n" +
            "  PRIMARY KEY (`customeId`, `couponId`),\n" +
            "  INDEX `couponId_idx` (`couponId` ASC) VISIBLE,\n" +
            "  CONSTRAINT `customeId`\n" +
            "    FOREIGN KEY (`customeId`)\n" +
            "    REFERENCES `coupon-project`.`customers` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `couponId`\n" +
            "    FOREIGN KEY (`couponId`)\n" +
            "    REFERENCES `coupon-project`.`coupons` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);\n";


    public void dropCreateStrategy() {

        try {
            JDBCUtils.executeQuery(QUERY_DROP_SCHEMA);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            JDBCUtils.executeQuery(QUERY_CREATE_SCHEMA);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_COMPANIES);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CUSTOMERS);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CATEGORIES);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_COUPONS);
            JDBCUtils.executeQuery(QUERY_CREATE_TABLE_CUSTOMERS_VS_COUPONS);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertCategories() throws SQLException, InterruptedException {
        for (Category c : Category.values()) {
            String name = c.name();
            categoryDAO.insert(name);
        }
    }

}
