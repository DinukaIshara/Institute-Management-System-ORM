package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.QueryDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {
    public Object[] getProgramPaymentDetails(String studentId, String programId) {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            // Native SQL Query
            String sql = "SELECT " +
                    "p.fee, " +
                    "r.upfrontpayment, " +
                    "SUM(pay.upfrontpayment) " +
                    "FROM Registration r " +
                    "JOIN Program p ON r.program_pId = p.pId " +
                    "JOIN Student s ON r.student_s_id = s.s_id " +
                    "JOIN Payment pay ON s.s_id = pay.student_s_id " +
                    "WHERE s.s_id = ? AND p.pName = ? " +
                    "GROUP BY p.pName, p.fee, r.upfrontpayment";
            /*String sql = "SELECT " +
                    "p.pName AS CourseName, " +
                    "p.fee AS ProgramFee, " +
                    "r.upfrontpayment AS RegistrationUpfrontPayment, " +
                    "SUM(pay.upfrontpayment) AS TotalUpfrontPayments " +
                    "FROM Registration r " +
                    "JOIN Program p ON r.program_pId = p.pId " +
                    "JOIN Student s ON r.student_s_id = s.s_id " +
                    "JOIN Payment pay ON s.s_id = pay.student_s_id " +
                    "WHERE s.s_id = ? " +
                    "GROUP BY p.pName, p.fee, r.upfrontpayment";*/


            Query<Object[]> query = session.createNativeQuery(sql, Object[].class);
            query.setParameter(1, studentId);
            query.setParameter(2, programId);

            Object[] result = query.uniqueResult();

            return result;
        } finally {
            session.close();
        }
    }

    @Override
    public Object[] getBarChart() throws SQLException,ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            String sql = "SELECT\n" +
                    "    DATE_FORMAT(p.date, '%Y-%m-%d') AS PaymentDate,\n" +
                    "    SUM(p.upfrontpayment) AS TotalAmount\n" +
                    "FROM\n" +
                    "    Payment p\n" +
                    "JOIN\n" +
                    "    Registration r ON p.rid = r.rid\n" +
                    "WHERE\n" +
                    "    p.date BETWEEN (SELECT MIN(date) FROM Payment) AND (SELECT MAX(date) FROM Payment)\n" +
                    "GROUP BY\n" +
                    "    DATE_FORMAT(p.date, '%Y-%m-%d')\n" +
                    "ORDER BY\n" +
                    "    PaymentDate;";

            Query<Object[]> query = session.createNativeQuery(sql, Object[].class);

            Object[] result = query.uniqueResult();

            return result;
        } finally {
            session.close();
        }
    }

}
