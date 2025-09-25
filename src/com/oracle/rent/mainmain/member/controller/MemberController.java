package com.oracle.rent.mainmain.member.controller;

import java.util.List;

import com.oracle.rent.mainmain.member.vo.MemberVO;



public interface MemberController {
	public List<MemberVO> listMember(MemberVO memVO) ;
	
	public void regMember(MemberVO memberVO) ;
	
	public void modMember(MemberVO memberVO) ;
	
	public void removeMember(MemberVO memberVO) ;
	
}


