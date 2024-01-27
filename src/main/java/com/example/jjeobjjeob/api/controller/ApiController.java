package com.example.jjeobjjeob.api.controller;

import com.example.jjeobjjeob.api.dto.ApiRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Controller
public class ApiController {

    @Value("${LOCALDATA_API_KEY}")
    private String LOCALDATA_API_KEY;

    @RequestMapping(value = "/getApiCall.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void getSampleApi(HttpServletRequest req, HttpServletResponse res, ApiRequest apiReq) throws Exception {

        String apiUrl = "http://www.localdata.go.kr/platform/rest/TO0/openDataApi?authKey=" + LOCALDATA_API_KEY + "&resultType=json";

        if (apiReq.getLocalCode() != null) {
            apiUrl += "&localCode=" + apiReq.getLocalCode();
        }
        if (apiReq.getBgnYmd() != null && apiReq.getEndYmd() != null) {
            apiUrl += "&bgnYmd=" + apiReq.getBgnYmd() + "&endYmd=" + apiReq.getEndYmd();
        }
        if (apiReq.getState() != null) {
            apiUrl += "&state=" + apiReq.getState();
        }

        URL url = new URL(apiUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        String tempStr;

        while ((tempStr = br.readLine()) != null) {
            sb.append(tempStr);
        }
        br.close();

        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        res.getWriter().write(sb.toString());

        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();

        JsonObject result = jsonObject.getAsJsonObject("result");
        JsonObject header = result.getAsJsonObject("header");
        JsonObject paging = header.getAsJsonObject("paging");

        // totalCount 값을 추출
        int totalCount = paging.get("totalCount").getAsInt();

        System.out.println("Total Count: " + totalCount);


    }

}
