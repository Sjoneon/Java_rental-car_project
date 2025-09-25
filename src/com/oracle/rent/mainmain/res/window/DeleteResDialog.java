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

public class DeleteResDialog extends JDialog {
    JPanel jPanel;
    JLabel lNumber;
    JTextField tfNumber;
    JButton btnDelete;

    ResController resController;

    public DeleteResDialog(ResController resController, String str) {
        this.resController = resController;
        setTitle(str);
        init();
    }

    private void init() {
        lNumber = new JLabel("예약번호");

        tfNumber = new JTextField(20);

        btnDelete = new JButton("예약삭제");

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = tfNumber.getText().trim();
                ResVO resVO = new ResVO();
                resVO.setResNumber(number);

                resController.removeRes(resVO);

                showMessage("예약 정보를 삭제했습니다.");
                tfNumber.setText("");
                dispose();
            }
        });

        jPanel = new JPanel(new GridLayout(0, 2));
        jPanel.add(lNumber);
        jPanel.add(tfNumber);

        add(jPanel, BorderLayout.NORTH);
        add(btnDelete, BorderLayout.SOUTH);

        setLocation(400, 200);
        setSize(400, 400);
        setModal(true);
        setVisible(true);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "메시지 박스", JOptionPane.INFORMATION_MESSAGE);
    }
}
