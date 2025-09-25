package com.oracle.rent.mainmain.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oracle.rent.mainmain.common.base.AbstractBaseDAO;
import com.oracle.rent.mainmain.member.vo.MemberVO;

public class MemberDAOImpl extends AbstractBaseDAO implements MemberDAO {

    @Override
    public List<MemberVO> selectMember(MemberVO memVO) throws SQLException, ClassNotFoundException {
        List<MemberVO> memList = new ArrayList<MemberVO>();
        String _memName = memVO.getMemName();
        if (_memName != null && _memName.length() != 0) {
            pstmt = conn.prepareStatement("SELECT * FROM t_Member WHERE memName = ? ORDER BY memId");
            pstmt.setString(1, _memName);
        } else {
            pstmt = conn.prepareStatement("SELECT * FROM t_Member ORDER BY memId");
        }

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String memId = rs.getString("memId");
            String memPassword = rs.getString("memPassword");
            String memName = rs.getString("memName");
            String memAddress = rs.getString("memAddress");
            String memPhoneNum = rs.getString("memPhoneNum");
            MemberVO _memVO = new MemberVO(memId, memPassword, memName, memAddress, memPhoneNum);
            memList.add(_memVO);
        }
        rs.close();
        return memList;
    }

    @Override
    public void insertMember(MemberVO memVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement(
            "INSERT INTO t_member (memId, memPassword, memName, memAddress, memPhoneNum) VALUES (?, ?, ?, ?, ?)"
        );
        pstmt.setString(1, memVO.getMemId());
        pstmt.setString(2, memVO.getMemPassword());
        pstmt.setString(3, memVO.getMemName());
        pstmt.setString(4, memVO.getMemAddress());
        pstmt.setString(5, memVO.getMemPhoneNum());

        pstmt.executeUpdate();
    }

    @Override
    public void updateMember(MemberVO memVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement(
            "UPDATE t_member SET memPassword = ?, memName = ?, memAddress = ?, memPhoneNum = ? WHERE memId = ?"
        );
        pstmt.setString(1, memVO.getMemPassword());
        pstmt.setString(2, memVO.getMemName());
        pstmt.setString(3, memVO.getMemAddress());
        pstmt.setString(4, memVO.getMemPhoneNum());
        pstmt.setString(5, memVO.getMemId());
        pstmt.executeUpdate();
    }

    @Override
    public void deleteMember(MemberVO memVO) throws SQLException, ClassNotFoundException {
        pstmt = conn.prepareStatement("DELETE FROM t_member WHERE memId = ?");
        pstmt.setString(1, memVO.getMemId());
        pstmt.executeUpdate();
    }
}
