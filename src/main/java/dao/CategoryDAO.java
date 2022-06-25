package dao;

import java.sql.SQLException;

public interface CategoryDAO {
    void insert(String name) throws SQLException, InterruptedException;

}
