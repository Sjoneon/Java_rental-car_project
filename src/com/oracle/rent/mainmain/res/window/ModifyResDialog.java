package com.oracle.rent.mainmain.res.window;

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

import com.oracle.rent.mainmain.res.controller.ResController;
import com.oracle.rent.mainmain.res.vo.ResVO;

public class ModifyResDialog extends JDialog {
    JPanel jPanel;
    JLabel lNumber, lCarNumber, lDate, lBeginDate, lReturnDate, lUserId;
    JTextField tfNumber, tfCarNumber, tfDate, tfBeginDate, tfReturnDate, tfUserId;
    JButton btnModify;

    ResController resController;

    public ModifyResDialog(ResController resController, String str) {
        this.resController = resController;
        setTitle(str);
        init();
    }

    private void init() {
        lNumber = new JLabel("예약번호");
        lCarNumber = new JLabel("차량번호");
        lDate = new JLabel("예약일자");
        lBeginDate = new JLabel("사용시작일");
        lReturnDate = new JLabel("반납일자");
        lUserId = new JLabel("사용자ID");

        tfNumber = new JTextField(20);
        tfCarNumber = new JTextField(20);
        tfDate = new JTextField(20);
        tfBeginDate = new JTextField(20);
        tfReturnDate = new JTextField(20);
        tfUserId = new JTextField(20);

        btnModify = new JButton("예약수정");

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = tfNumber.getText().trim();
                String carNumber = tfCarNumber.getText().trim();
                String date = tfDate.getText().trim();
                String beginDate = tfBeginDate.getText().trim();
                String returnDate = tfReturnDate.getText().trim();
                String userId = tfUserId.getText().trim();

                ResVO resVO = new ResVO(number, carNumber, date, beginDate, returnDate, userId);
                resController.modResInfo(resVO);

                showMessage("예약 정보를 수정했습니다.");
                tfNumber.setText("");
                tfCarNumber.setText("");
                tfDate.setText("");
                tfBeginDate.setText("");
                tfReturnDate.setText("");
                tfUserId.setText("");
                dispose();
            }
        });

        jPanel = new JPanel(new GridLayout(0, 2));
        jPanel.add(lNumber);
        jPanel.add(tfNumber);

        jPanel.add(lCarNumber);
        jPanel.add(tfCarNumber);

        jPanel.add(lDate);
        jPanel.add(tfDate);

        jPanel.add(lBeginDate);
        jPanel.add(tfBeginDate);

        jPanel.add(lReturnDate);
        jPanel.add(tfReturnDate);

        jPanel.add(lUserId);
        jPanel.add(tfUserId);

        add(jPanel, BorderLayout.NORTH);
        add(btnModify, BorderLayout.SOUTH);

        setLocation(400, 200);
        setSize(400, 400);
        setModal(true);
        setVisible(true);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지 박스", JOptionPane.INFORMATION_MESSAGE);
    }
}
