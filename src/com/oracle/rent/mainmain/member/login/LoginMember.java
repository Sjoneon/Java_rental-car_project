package com.oracle.rent.mainmain.member.login;

import javax.swing.*;

import com.oracle.rent.mainmain.common.base.AbstractBaseDAO;
import com.oracle.rent.mainmain.main.RentMainWindow;
import com.oracle.rent.mainmain.member.registration.RegisterMember;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginMember extends AbstractBaseDAO {
    public void login() {
        JFrame frame = new JFrame("로그인");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel labelId = new JLabel("아이디:");
        JTextField tfId = new JTextField(20);
        JLabel labelPassword = new JLabel("비밀번호:");
        JPasswordField tfPassword = new JPasswordField(20);

        JButton btnLogin = new JButton("로그인");
        btnLogin.addActionListener(e -> {
            String id = tfId.getText();
            String password = new String(tfPassword.getPassword());

            if (authenticateUser(id, password)) {
                JOptionPane.showMessageDialog(frame, "로그인에 성공했습니다.");
                frame.dispose(); // 로그인 창 닫기
                new RentMainWindow(conn); // 메인 윈도우 실행
            } else {
                JOptionPane.showMessageDialog(frame, "로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.");
            }
        });

        JButton btnRegister = new JButton("회원가입");
        btnRegister.addActionListener(e -> RegisterMember.register());

        frame.add(labelId);
        frame.add(tfId);
        frame.add(labelPassword);
        frame.add(tfPassword);
        frame.add(btnLogin);
        frame.add(btnRegister);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean authenticateUser(String id, String password) {
        String query = "SELECT COUNT(*) FROM t_member WHERE memId = ? AND memPassword = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
