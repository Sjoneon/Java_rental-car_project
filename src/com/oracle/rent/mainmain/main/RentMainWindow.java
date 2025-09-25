package com.oracle.rent.mainmain.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import com.oracle.rent.mainmain.calendar.CalendarDialog;
import com.oracle.rent.mainmain.car.controller.CarController;
import com.oracle.rent.mainmain.car.controller.CarControllerImpl;
import com.oracle.rent.mainmain.car.window.DeleteCarDialog;
import com.oracle.rent.mainmain.car.window.ModifyCarDialog;
import com.oracle.rent.mainmain.car.window.RegCarDialog;
import com.oracle.rent.mainmain.car.window.SearchCarDialog;
import com.oracle.rent.mainmain.common.base.AbstractBaseWindow;
import com.oracle.rent.mainmain.member.controller.MemberController;
import com.oracle.rent.mainmain.member.controller.MemberControllerImpl;
import com.oracle.rent.mainmain.member.login.LoginMember;
import com.oracle.rent.mainmain.member.window.RegMemDialog;
import com.oracle.rent.mainmain.member.window.SearchMemDialog;
import com.oracle.rent.mainmain.res.controller.ResController;
import com.oracle.rent.mainmain.res.controller.ResControllerImpl;
import com.oracle.rent.mainmain.res.window.DeleteResDialog;
import com.oracle.rent.mainmain.res.window.ModifyResDialog;
import com.oracle.rent.mainmain.res.window.RegResDialog;
import com.oracle.rent.mainmain.res.window.SearchResDialog;

public class RentMainWindow extends AbstractBaseWindow {
    JFrame frame;
    JMenuBar menuBar;
    JMenu carMenu, memberMenu, resMenu, helpMenu, calendarMenu, settingsMenu;
    JMenuItem carMenu11, carMenu12, carMenu13, carMenu14;
    JMenuItem memMenu21, memMenu22, memMenu23, memMenu24;
    JMenuItem resMenu31, resMenu32, resMenu33, resMenu34;
    JMenuItem helpMenu41, calendarMenuItem, memoListMenuItem, deleteMemoMenuItem, settingsMenuItem;
    JLabel dateTimeLabel; // 날짜와 시간을 표시할 레이블
    JLabel imageLabel; // 이미지를 표시할 레이블

    MemberController memberController;
    CarController carController;
    ResController resController;
    Connection conn;

    private static final String SETTINGS_FILE = "settings.properties";
    private static final String IMAGE_PATH_KEY = "imagePath";

    public RentMainWindow(Connection conn) {
        this.conn = conn;
        frame = new JFrame("렌트카 조회/예약 시스템");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        menuBar = new JMenuBar();
        carMenu = new JMenu("차량 관리");
        memberMenu = new JMenu("회원관리");
        resMenu = new JMenu("예약관리");
        calendarMenu = new JMenu("달력");
        helpMenu = new JMenu("도움말");
        settingsMenu = new JMenu("환경설정");

        dateTimeLabel = new JLabel();
        updateDateTime(); // 초기 시간 설정
        startTimer(); // 타이머 시작

        setMenuFont(carMenu);
        setMenuFont(memberMenu);
        setMenuFont(resMenu);
        setMenuFont(calendarMenu);
        setMenuFont(helpMenu);
        setMenuFont(settingsMenu);

        initMenu();
        frame.setJMenuBar(menuBar);

        initLayout();

        loadImageFromSettings();

        frame.setVisible(true);
    }

    private void setMenuFont(JMenu menu) {
        Font menuFont = new Font("돋움", Font.PLAIN, 18);
        menu.setFont(menuFont);
    }

    private void initMenu() {
        menuBar.add(memberMenu);
        memberMenu.add(memMenu21 = createMenuItem("회원등록"));
        memberMenu.add(memMenu22 = createMenuItem("회원조회"));
        memberMenu.addSeparator();
        memberMenu.add(memMenu23 = createMenuItem("회원수정"));
        memberMenu.add(memMenu24 = createMenuItem("회원삭제"));

        menuBar.add(carMenu);
        carMenu.add(carMenu11 = createMenuItem("차량등록"));
        carMenu.add(carMenu12 = createMenuItem("차량조회"));
        carMenu.addSeparator();
        carMenu.add(carMenu13 = createMenuItem("차량수정"));
        carMenu.add(carMenu14 = createMenuItem("차량삭제"));

        menuBar.add(resMenu);
        resMenu.add(resMenu31 = createMenuItem("예약등록"));
        resMenu.add(resMenu32 = createMenuItem("예약조회"));
        resMenu.addSeparator();
        resMenu.add(resMenu33 = createMenuItem("예약수정"));
        resMenu.add(resMenu34 = createMenuItem("예약취소"));

        menuBar.add(calendarMenu);
        calendarMenuItem = createMenuItem("달력 보기");
        calendarMenu.add(calendarMenuItem);
        calendarMenuItem.addActionListener(e -> openDialog(new CalendarDialog(frame, conn)));

        memoListMenuItem = createMenuItem("메모장 확인");
        calendarMenu.add(memoListMenuItem);
        memoListMenuItem.addActionListener(e -> showMemoList());

        deleteMemoMenuItem = createMenuItem("메모 삭제");
        calendarMenu.add(deleteMemoMenuItem);
        deleteMemoMenuItem.addActionListener(e -> deleteMemo());

        menuBar.add(helpMenu);
        helpMenu.add(helpMenu41 = createMenuItem("버전"));

        menuBar.add(settingsMenu);
        settingsMenuItem = createMenuItem("사진 추가");
        settingsMenu.add(settingsMenuItem);
        settingsMenuItem.addActionListener(e -> addImage());

        // 날짜와 시간 레이블을 메뉴바에 추가
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(dateTimeLabel);

        memMenu21.addActionListener(new MemberHandler());
        memMenu22.addActionListener(new MemberHandler());
        memMenu23.addActionListener(new MemberHandler());
        memMenu24.addActionListener(new MemberHandler());

        carMenu11.addActionListener(new CarHandler());
        carMenu12.addActionListener(new CarHandler());
        carMenu13.addActionListener(new CarHandler());
        carMenu14.addActionListener(new CarHandler());

        resMenu31.addActionListener(new ResHandler());
        resMenu32.addActionListener(new ResHandler());
        resMenu33.addActionListener(new ResHandler());
        resMenu34.addActionListener(new ResHandler());

        helpMenu41.addActionListener(new HelpHandler());

        memberController = new MemberControllerImpl();
        carController = new CarControllerImpl();
        resController = new ResControllerImpl();
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(new Font("돋움", Font.PLAIN, 18));
        return menuItem;
    }

    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTimeLabel.setText(sdf.format(new Date()));
    }

    private void startTimer() {
        Timer timer = new Timer(1000, e -> updateDateTime());
        timer.start();
    }

    private void initLayout() {
        // 이미지를 표시할 패널 생성 및 중앙 정렬
        JPanel imagePanel = new JPanel(new BorderLayout());
        imageLabel = new JLabel("", JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // 이미지 패널을 프레임에 추가
        frame.add(imagePanel, BorderLayout.CENTER);

        // 날짜와 시간을 표시할 패널을 하단에 추가
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(dateTimeLabel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            saveImagePathToSettings(selectedFile.getAbsolutePath());
            displayImage(selectedFile.getAbsolutePath());
        }
    }

    private void saveImagePathToSettings(String imagePath) {
        Properties properties = new Properties();
        try {
            properties.setProperty(IMAGE_PATH_KEY, imagePath);
            properties.store(new FileOutputStream(SETTINGS_FILE), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImageFromSettings() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(SETTINGS_FILE));
            String imagePath = properties.getProperty(IMAGE_PATH_KEY);
            if (imagePath != null) {
                displayImage(imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayImage(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        imageLabel.setIcon(imageIcon);
    }

    private void openDialog(JDialog dialog) {
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void showMemoList() {
        JDialog memoListDialog = new JDialog(frame, "메모 목록", true);
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
        memoListDialog.setLocationRelativeTo(frame);
        memoListDialog.setVisible(true);
    }

    private void deleteMemo() {
        Date selectedDate = new Date(); // 임시로 현재 날짜를 사용합니다. 실제로는 선택된 날짜를 가져와야 합니다.
        int confirm = JOptionPane.showConfirmDialog(frame, "선택한 날짜의 메모를 삭제하시겠습니까?", "메모 삭제", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String deleteQuery = "DELETE FROM t_memo WHERE memoDate = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setDate(1, new java.sql.Date(selectedDate.getTime()));
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "메모가 삭제되었습니다.");
                } else {
                    JOptionPane.showMessageDialog(frame, "해당 날짜에 메모가 없습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    class MemberHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == memMenu21) {
                    openDialog(new RegMemDialog(memberController, "회원 등록창"));
                } else if (e.getSource() == memMenu22) {
                    openDialog(new SearchMemDialog(memberController,"회원 조회창"));
                } else if (e.getSource() == memMenu23) {
                    openDialog(new SearchMemDialog(memberController, "회원 수정창"));
                } else if (e.getSource() == memMenu24) {
                    openDialog(new SearchMemDialog(memberController, "회원 삭제창"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class CarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == carMenu11) {
                    openDialog(new RegCarDialog(carController, "차량등록창"));
                } else if (e.getSource() == carMenu12) {
                    openDialog(new SearchCarDialog(carController, "차량조회창"));
                } else if (e.getSource() == carMenu13) {
                    openDialog(new ModifyCarDialog(carController, "차량수정창"));
                } else if (e.getSource() == carMenu14) {
                    openDialog(new DeleteCarDialog(carController, "차량삭제창"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class ResHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == resMenu31) {
                    openDialog(new RegResDialog(resController, "예약 등록창"));
                } else if (e.getSource() == resMenu32) {
                    openDialog(new SearchResDialog(resController, "예약 조회창"));
                } else if (e.getSource() == resMenu33) {
                    openDialog(new ModifyResDialog(resController, "예약수정창"));
                } else if (e.getSource() == resMenu34) {
                    openDialog(new DeleteResDialog(resController, "예약취소창"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class HelpHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            version();
        }
    }

    public void version() {
        final JDialog d = new JDialog(frame, "버전관리");
        JLabel jbver = new JLabel("버전1.5.2");
        JLabel jbdate = new JLabel("2024");
        JLabel jbauthor = new JLabel("송재원");

        jbver.setFont(new Font("돋움", Font.PLAIN, 18));
        jbdate.setFont(new Font("돋움", Font.PLAIN, 18));
        jbauthor.setFont(new Font("돋움", Font.PLAIN, 18));

        d.setLayout(new FlowLayout());
        d.add(jbver);
        d.add(jbdate);
        d.add(jbauthor);

        d.setSize(300, 150);
        d.setVisible(true);

        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });
    }

    public static void main(String[] args) {
        LoginMember loginMember = new LoginMember();
        loginMember.login();
    }
}
