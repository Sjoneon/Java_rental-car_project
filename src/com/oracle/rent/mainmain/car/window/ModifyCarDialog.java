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

public class ModifyCarDialog extends JDialog {
    JPanel jPanel;
    JLabel lNumber, lName, lColor, lSize, lMaker;
    JTextField tfNumber, tfName, tfColor, tfSize, tfMaker;
    JButton btnModify;

    CarController carController;

    public ModifyCarDialog(CarController carController, String str) {
        this.carController = carController;
        setTitle(str);
        init();
    }

    private void init() {
        lNumber = new JLabel("차량번호");
        lName = new JLabel("차량명");
        lSize = new JLabel("배기량");
        lColor = new JLabel("차 색상");
        lMaker = new JLabel("차 제조사");

        tfNumber = new JTextField(20);
        tfName = new JTextField(20);
        tfColor = new JTextField(20);
        tfSize = new JTextField(20);
        tfMaker = new JTextField(20);

        btnModify = new JButton("차량수정");

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String number = tfNumber.getText().trim();
                String name = tfName.getText().trim();
                String color = tfColor.getText().trim();
                int size = Integer.parseInt(tfSize.getText().trim());
                String maker = tfMaker.getText().trim();

                CarVO carVO = new CarVO(number, name, color, size, maker);
                carController.modCarInfo(carVO);

                showMessage("차량 정보를 수정했습니다.");
                tfNumber.setText("");
                tfName.setText("");
                tfColor.setText("");
                tfSize.setText("");
                tfMaker.setText("");
                dispose();
            }
        });

        jPanel = new JPanel(new GridLayout(0, 2));
        jPanel.add(lNumber);
        jPanel.add(tfNumber);

        jPanel.add(lName);
        jPanel.add(tfName);

        jPanel.add(lColor);
        jPanel.add(tfColor);

        jPanel.add(lSize);
        jPanel.add(tfSize);

        jPanel.add(lMaker);
        jPanel.add(tfMaker);

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
