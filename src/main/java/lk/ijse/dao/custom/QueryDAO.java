package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public Object[] getProgramPaymentDetails(String studentId, String programId);

    public Object[] getBarChart() throws SQLException,ClassNotFoundException ;

}
