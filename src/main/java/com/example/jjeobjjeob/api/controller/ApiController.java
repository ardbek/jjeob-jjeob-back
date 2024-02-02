package com.example.jjeobjjeob.api.controller;

import com.example.jjeobjjeob.api.dto.ApiRequest;
import com.example.jjeobjjeob.api.dto.ApiResponse;
import com.example.jjeobjjeob.api.service.ApiService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping(value = "/getApiCall.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void getSampleApi(HttpServletRequest req, HttpServletResponse res, ApiRequest apiReq) throws Exception {
        List<ApiResponse> updatedDataList = apiService.fetchAndUpdateData(apiReq);

        // 웹에 출력 테스트
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        res.getWriter().write(new Gson().toJson(updatedDataList));
    }

}