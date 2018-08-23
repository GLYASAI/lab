package org.abewang.lab.air.controller;

import org.abewang.lab.air.domain.vo.AirBookReqVO;
import org.abewang.lab.air.domain.vo.AirBookRespVO;
import org.abewang.lab.air.domain.RestResponse;
import org.abewang.lab.air.service.AirBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
@RequestMapping("air")
@RestController
public class AirController {

    @Autowired
    private AirBookService airBookService;

    @PostMapping("air-book")
    public RestResponse<AirBookRespVO> airBook(@RequestBody AirBookReqVO airBookReqVO) {
        AirBookRespVO airBookRespVO = airBookService.airBook(airBookReqVO);
        RestResponse<AirBookRespVO> response = new RestResponse<>();
        response.setIsSuccess(1);
        response.setData(airBookRespVO);
        return response;
    }
}
