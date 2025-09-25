package com.oracle.rent.mainmain.car.window;

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

import com.oracle.rent.mainmain.car.controller.CarController;
import com.oracle.rent.mainmain.car.vo.CarVO;

public class DeleteCarDialog extends JDialog {
    JPanel jPanel;
    JLabel lNumber;
    JTextField tfNumber;
    JButton btnDelete;

    CarController carController;

    public DeleteCarDialog(CarController carController, String str) {
        this.carController = carController;
        setTitle(str);
        init();
    }

    private void init() {
        lNumber = new JLabel("차량번호");

        tfNumber = new JTextField(20);

        btnDelete = new JButton("차량삭제");

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = tfNumber.getText().trim();
                CarVO carVO = new CarVO();
                carVO.setCarNumber(number);

                carController.removeCarInfo(carVO);

                showMessage("차량 정보를 삭제했습니다.");
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
