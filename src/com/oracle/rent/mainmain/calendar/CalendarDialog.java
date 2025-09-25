package com.oracle.rent.mainmain.calendar;

import javax.swing.*;
import com.toedter.calendar.JCalendar;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarDialog extends JDialog {
    private JCalendar calendar;
    private Connection conn;

    public CalendarDialog(JFrame parent, Connection conn) {
        super(parent, "달력", true);
        this.conn = conn;
        setLayout(new BorderLayout());

        calendar = new JCalendar();
        calendar.getDayChooser().addPropertyChangeListener("day", new DayChangeListener());

        add(calendar, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton btnMemoList = new JButton("메모장 확인");
        btnMemoList.addActionListener(e -> showMemoList());
        buttonPanel.add(btnMemoList);

        JButton btnDeleteMemo = new JButton("메모 삭제");
        btnDeleteMemo.addActionListener(e -> deleteMemo());
        buttonPanel.add(btnDeleteMemo);

        JButton btnClose = new JButton("닫기");
        btnClose.addActionListener(e -> dispose());
        buttonPanel.add(btnClose);

        add(buttonPanel, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private class DayChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() != null) {
                Date selectedDate = calendar.getDate();
                showMemoDialog(selectedDate);
            }
        }
    }

    private void showMemoDialog(Date date) {
        String memoText = getMemo(date);
        JTextArea textArea = new JTextArea(memoText, 10, 30);
        int result = JOptionPane.showConfirmDialog(this, new JScrollPane(textArea), "메모", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            saveMemo(date, textArea.getText());
        }
    }

    private String getMemo(Date date) {
        String memoText = "";
        String query = "SELECT memoText FROM t_memo WHERE memoDate = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                memoText = rs.getString("memoText");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memoText;
    }

    private void saveMemo(Date date, String memoText) {
        String selectQuery = "SELECT COUNT(*) FROM t_memo WHERE memoDate = ?";
        String insertQuery = "INSERT INTO t_memo (memoDate, memoText) VALUES (?, ?)";
        String updateQuery = "UPDATE t_memo SET memoText = ? WHERE memoDate = ?";

        try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Update existing memo
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, memoText);
                    updateStmt.setDate(2, new java.sql.Date(date.getTime()));
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert new memo
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setDate(1, new java.sql.Date(date.getTime()));
                    insertStmt.setString(2, memoText);
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showMemoList() {
        JDialog memoListDialog = new JDialog(this, "메모 목록", true);
        memoListDialog.setLayout(new BorderLayout());

        JTextArea memoListArea = new JTextArea(20, 40);
        memoListArea.setEditable(false);
        memoListArea.setFont(new Font("돋움", Font.PLAIN, 14));

        String query = "SELECT memoDate, memoText FROM t_memo ORDER BY memoDate";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            StringBuilder memoListBuilder = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (rs.next()) {
                Date memoDate = rs.getDate("memoDate");
                String memoText = rs.getString("memoText");
                memoListBuilder.append(sdf.format(memoDate)).append(": ").append(memoText).append("\n");
            }
            memoListArea.setText(memoListBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        memoListDialog.add(new JScrollPane(memoListArea), BorderLayout.CENTER);

        JButton btnClose = new JButton("닫기");
        btnClose.addActionListener(e -> memoListDialog.dispose());
        memoListDialog.add(btnClose, BorderLayout.SOUTH);

        memoListDialog.pack();
        memoListDialog.setLocationRelativeTo(this);
        memoListDialog.setVisible(true);
    }

    private void deleteMemo() {
        Date selectedDate = calendar.getDate();
        if (selectedDate != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "선택한 날짜의 메모를 삭제하시겠습니까?", "메모 삭제", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String deleteQuery = "DELETE FROM t_memo WHERE memoDate = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                    pstmt.setDate(1, new java.sql.Date(selectedDate.getTime()));
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "메모가 삭제되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(this, "해당 날짜에 메모가 없습니다.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
