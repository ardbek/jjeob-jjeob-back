package com.fmap.api.controller;

import com.fmap.dto.RestaurantReq;
import com.fmap.dto.RestaurantRes;
import com.fmap.utils.LocalDataApiUtils;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ApiController {

    @Autowired
    private LocalDataApiUtils localDataApiUtils;

    @RequestMapping(value = "/getApiCall.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void getSampleApi(HttpServletRequest req, HttpServletResponse res, RestaurantReq apiReq) throws Exception {
        List<RestaurantRes> updatedDataList = localDataApiUtils.fetchAndUpdateData();

        // 웹에 출력 테스트
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        res.getWriter().write(new Gson().toJson(updatedDataList));
    }

}