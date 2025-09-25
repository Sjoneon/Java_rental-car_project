package com.oracle.rent.mainmain.member.registration;

import javax.swing.*;

import com.oracle.rent.mainmain.common.base.AbstractBaseDAO;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterMember extends AbstractBaseDAO {
    public static void register() {
        JFrame frame = new JFrame("회원가입");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel labelName = new JLabel("이름:");
        JTextField tfName = new JTextField(20);
        JLabel labelId = new JLabel("아이디:");
        JTextField tfId = new JTextField(20);
        JLabel labelPassword = new JLabel("비밀번호:");
        JPasswordField tfPassword = new JPasswordField(20);

        JButton btnRegister = new JButton("가입하기");
        btnRegister.addActionListener(e -> {
            String name = tfName.getText();
            String id = tfId.getText();
            String password = new String(tfPassword.getPassword());

            if (new RegisterMember().registerUser(name, id, password)) {
                JOptionPane.showMessageDialog(frame, "회원가입이 완료되었습니다.");
                frame.dispose(); // 회원가입 창 닫기
            } else {
                JOptionPane.showMessageDialog(frame, "회원가입에 실패했습니다. 다시 시도해주세요.");
            }
        });

        frame.add(labelName);
        frame.add(tfName);
        frame.add(labelId);
        frame.add(tfId);
        frame.add(labelPassword);
        frame.add(tfPassword);
        frame.add(btnRegister);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean registerUser(String name, String id, String password) {
        String query = "INSERT INTO t_member (memId, memPassword, memName) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
