package org.abewang.lab.air.service;

import org.abewang.lab.air.domain.vo.AirBookReqVO;
import org.abewang.lab.air.domain.vo.AirBookRespVO;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
public interface AirBookService {
    AirBookRespVO airBook(AirBookReqVO airBookReqVO);
}
