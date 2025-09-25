package com.oracle.rent.mainmain.member.dao;

import java.sql.SQLException;
import java.util.List;

import com.oracle.rent.mainmain.member.vo.MemberVO;



public interface MemberDAO {
	public List<MemberVO> selectMember(MemberVO memVO) throws SQLException, ClassNotFoundException;
	
	public void insertMember(MemberVO memVO) throws SQLException, ClassNotFoundException;
	
	public void updateMember(MemberVO memVO) throws SQLException, ClassNotFoundException;
	
	public void deleteMember(MemberVO memVO) throws SQLException, ClassNotFoundException;
}


