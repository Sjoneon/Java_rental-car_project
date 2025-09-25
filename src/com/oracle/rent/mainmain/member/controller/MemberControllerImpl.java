package com.oracle.rent.mainmain.member.controller;

import com.oracle.rent.mainmain.common.base.AbstractBaseDAO;
import com.oracle.rent.mainmain.member.vo.MemberVO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberControllerImpl extends AbstractBaseDAO implements MemberController {

    @Override
    public List<MemberVO> listMember(MemberVO memVO) {
        List<MemberVO> memList = new ArrayList<>();
        String query = "SELECT memId, memPassword, memName, memAddress, memPhoneNum FROM t_member";
        if (memVO.getMemName() != null && !memVO.getMemName().isEmpty()) {
            query += " WHERE memName LIKE ?";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            if (memVO.getMemName() != null && !memVO.getMemName().isEmpty()) {
                pstmt.setString(1, "%" + memVO.getMemName() + "%");
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String memId = rs.getString("memId");
                String memPassword = rs.getString("memPassword");
                String memName = rs.getString("memName");
                String memAddress = rs.getString("memAddress");
                String memPhoneNum = rs.getString("memPhoneNum");
                MemberVO member = new MemberVO(memId, memPassword, memName, memAddress, memPhoneNum);
                memList.add(member);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memList;
    }

    @Override
    public void regMember(MemberVO memVO) {
        String query = "INSERT INTO t_member (memId, memPassword, memName, memAddress, memPhoneNum) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, memVO.getMemId());
            pstmt.setString(2, memVO.getMemPassword());
            pstmt.setString(3, memVO.getMemName());
            pstmt.setString(4, memVO.getMemAddress());
            pstmt.setString(5, memVO.getMemPhoneNum());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modMember(MemberVO memVO) {
        String query = "UPDATE t_member SET memPassword = ?, memName = ?, memAddress = ?, memPhoneNum = ? WHERE memId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, memVO.getMemPassword());
            pstmt.setString(2, memVO.getMemName());
            pstmt.setString(3, memVO.getMemAddress());
            pstmt.setString(4, memVO.getMemPhoneNum());
            pstmt.setString(5, memVO.getMemId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeMember(MemberVO memVO) {
        String query = "DELETE FROM t_member WHERE memId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, memVO.getMemId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
