package com.example.jjeobjjeob.api.controller;

import com.example.jjeobjjeob.api.dto.ApiRequest;
import com.example.jjeobjjeob.api.dto.ApiResponse;
import com.example.jjeobjjeob.api.dto.ApiResponseContainer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
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

        // 음식점 코드
        apiUrl += "&opnSvcId=07_24_04_P";
        // 개발 : 최대 500건 / 운영 최대 10,000건
        int pageSize = 500;
        apiUrl += "&pageSize="+pageSize;


        URL url = new URL(apiUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        String tempStr;

        while ((tempStr = br.readLine()) != null) {
            sb.append(tempStr);
        }
        br.close();

        // 웹에 출력 테스트 후 삭제 ---------------
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        res.getWriter().write(sb.toString());
        // -------------------------------------

        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();

        // totalCount 값 추출
        JsonObject result = jsonObject.getAsJsonObject("result");
        JsonObject header = result.getAsJsonObject("header");
        JsonObject paging = header.getAsJsonObject("paging");
        int totalCount = paging.get("totalCount").getAsInt();

        int totalPage = getTotalPage(totalCount, pageSize);

        log.info("totalPage : {}",totalPage);

        List<ApiResponse> responseList = new ArrayList<>();

        for (int i = 0; i <= totalPage ; i++) {
            String dataApiUrl = apiUrl + "&pageIndex=" + i;
            url = new URL(dataApiUrl); // 수정된 URL 사용
            br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            StringBuilder pageSb = new StringBuilder();
            String pageTempStr;

            while ((pageTempStr = br.readLine()) != null) {
                pageSb.append(pageTempStr);
            }

            br.close();

            ApiResponseContainer container = gson.fromJson(pageSb.toString(), ApiResponseContainer.class);
            if (container != null && container.getRow() != null) {
                responseList.addAll(container.getRow());
            } else {
                log.warn("No data found for page: {}", i);
            }
        }
        log.info("Api Result :: {}",responseList);



    }

    /**
     * 총 페이지 수
     * @param totalCount
     * @param pageSize
     * @return
     */
    public int getTotalPage(int totalCount, int pageSize) {
        if (totalCount % pageSize != 0) {
            return (totalCount / pageSize) + 1;
        } else {
            return totalCount / pageSize;
        }
    }

}