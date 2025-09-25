package com.oracle.rent.mainmain.car.controller;

import java.io.IOException;
import java.util.List;

import com.oracle.rent.mainmain.car.exception.CarException;
import com.oracle.rent.mainmain.car.vo.CarVO;

public interface CarController {
	public List<CarVO> listCarInfo(CarVO carVO) ;  //렌터카 리스트 출력 

	public void regCarInfo(CarVO carVO) ; 
	
	public void modCarInfo(CarVO carVO) ;
	
	public void removeCarInfo(CarVO carVO) ;

}
