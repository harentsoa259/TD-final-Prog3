package org.example.agroptima.Repository;

import org.example.agroptima.Modele.Contribution.CreateMemberPayment;
import org.example.agroptima.Modele.Contribution.CreateMembershipFee;
import org.example.agroptima.Modele.Contribution.Frequency;
import org.example.agroptima.Modele.Contribution.MembershipFee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContributionRepository {
    private final Connection connection;

    public ContributionRepository(Connection connection) {
        this.connection = connection;
    }

    public void saveMembershipFee(int collectivityId, CreateMembershipFee fee) throws SQLException {
        String sql = "INSERT INTO membership_fee (collectivity_id, eligible_from, frequency, amount, label) " +
                "VALUES (?, ?, ?::frequency, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, collectivityId);
            pstmt.setDate(2, Date.valueOf(fee.getEligibleFrom()));
            pstmt.setString(3, fee.getFrequency());
            pstmt.setDouble(4, fee.getAmount());
            pstmt.setString(5, fee.getLabel());
            pstmt.executeUpdate();
        }
    }

    public void processMemberPayment(int memberId, CreateMemberPayment payment) throws SQLException {
        String sqlPayment = "INSERT INTO member_payment (member_id, fee_id, account_id, amount, payment_mode) " +
                "VALUES (?, ?, ?, ?, ?::payment_mode)";

        String sqlUpdateAccount = "UPDATE financial_account SET amount = amount + ? WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement pstmt = connection.prepareStatement(sqlPayment)) {
                pstmt.setInt(1, memberId);
                pstmt.setInt(2, Integer.parseInt(payment.getMembershipFeeIdentifier()));
                pstmt.setInt(3, Integer.parseInt(payment.getAccountCreditedIdentifier()));
                pstmt.setInt(4, payment.getAmount());
                pstmt.setString(5, payment.getPaymentMode());
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = connection.prepareStatement(sqlUpdateAccount)) {
                pstmt.setDouble(1, payment.getAmount());
                pstmt.setInt(2, Integer.parseInt(payment.getAccountCreditedIdentifier()));
                pstmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<CreateMemberPayment> getTransactionsByPeriod(int collectivityId, LocalDate from, LocalDate to) throws SQLException {
        List<CreateMemberPayment> list = new ArrayList<>();
        String sql = "SELECT p.* FROM member_payment p " +
                "INNER JOIN member m ON p.member_id = m.id " +
                "WHERE m.collectivity_id = ? " +
                "AND p.creation_date >= ? AND p.creation_date <= ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, collectivityId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CreateMemberPayment p = new CreateMemberPayment();
                    p.setAmount(rs.getInt("amount"));
                    p.setPaymentMode(rs.getString("payment_mode"));
                    p.setMembershipFeeIdentifier(String.valueOf(rs.getInt("fee_id")));
                    p.setAccountCreditedIdentifier(String.valueOf(rs.getInt("account_id")));
                    list.add(p);
                }
            }
        }
        return list;
    }


    public List<MembershipFee> getMembershipFeesByCollectivity(int collectivityId) throws SQLException {
        List<MembershipFee> fees = new ArrayList<>();
        String sql = "SELECT * FROM membership_fee WHERE collectivity_id = ? AND status = 'ACTIVE'";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, collectivityId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MembershipFee fee = new MembershipFee();

                fee.setId(String.valueOf(rs.getInt("id")));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setAmount(rs.getDouble("amount"));
                fee.setLabel(rs.getString("label"));

                fee.setFrequency(String.valueOf(Frequency.valueOf(rs.getString("frequency"))));

                fee.setStatus(rs.getString("status"));

                fees.add(fee);
            }
        }
        return fees;
    }
}