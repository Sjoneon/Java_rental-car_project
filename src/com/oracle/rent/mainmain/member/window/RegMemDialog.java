package com.oracle.rent.mainmain.member.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.oracle.rent.mainmain.member.controller.MemberController;
import com.oracle.rent.mainmain.member.vo.MemberVO;

public class RegMemDialog extends JDialog {
    JPanel jPanel;
    JLabel lId, lName, lPassword, lAddress, lPhoneNum;
    JTextField tfId, tfName, tfPassword, tfAddress, tfPhoneNum;
    JButton btnReg;
    
    MemberController memberController;

    public RegMemDialog(MemberController memberController, String str) {
        this.memberController = memberController;
        setTitle(str);
        init();
    }

    private void init() {
        lId = new JLabel("아이디");
        lPassword = new JLabel("비밀번호");
        lName = new JLabel("이름");
        lAddress = new JLabel("주소");
        lPhoneNum = new JLabel("전화번호");

        tfId = new JTextField(20);
        tfPassword = new JTextField(20);
        tfName = new JTextField(20);
        tfAddress = new JTextField(20);
        tfPhoneNum = new JTextField(20);

        btnReg = new JButton("회원등록하기");

        btnReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = tfId.getText().trim();
                String password = tfPassword.getText().trim();
                String name = tfName.getText().trim();
                String address = tfAddress.getText().trim();
                String phoneNum = tfPhoneNum.getText().trim();
                
                MemberVO memVO = new MemberVO(id, password, name, address, phoneNum);

                memberController.regMember(memVO);

                showMessage("새 회원을 등록했습니다.");
                tfId.setText("");
                tfPassword.setText("");
                tfName.setText("");
                tfAddress.setText("");
                tfPhoneNum.setText("");
                dispose();
            }
        });

        jPanel = new JPanel(new GridLayout(0, 2));
        jPanel.add(lId);
        jPanel.add(tfId);

        jPanel.add(lPassword);
        jPanel.add(tfPassword);

        jPanel.add(lName);
        jPanel.add(tfName);

        jPanel.add(lAddress);
        jPanel.add(tfAddress);

        jPanel.add(lPhoneNum);
        jPanel.add(tfPhoneNum);

        add(jPanel, BorderLayout.NORTH);
        add(btnReg, BorderLayout.SOUTH);

        setLocation(400, 200);
        setSize(400, 400);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지 박스", JOptionPane.INFORMATION_MESSAGE);
    }
}
