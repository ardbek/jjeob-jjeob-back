package com.example.jjeobjjeob.api.controller;

import com.example.jjeobjjeob.api.dto.ApiRequest;
import com.example.jjeobjjeob.api.dto.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ApiController {

    @Value("${LOCALDATA_API_KEY}")
    private String LOCALDATA_API_KEY;
    private final int PAGE_SIZE = 500;

    @RequestMapping(value = "/getApiCall.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void getSampleApi(HttpServletRequest req, HttpServletResponse res, ApiRequest apiReq) throws Exception {

        String apiUrl = getApiURL(apiReq);
        String data = getData(apiUrl);

        // 웹에 출력 테스트  ---------------
        res.setCharacterEncoding("UTF-8");
        res.setContentType("application/json");
        res.getWriter().write(data);
        // -------------------------------------

        parseStringToObject(data);
        int totalPage = getTotalPage(data);

        List<ApiResponse> updatedDataList = new ArrayList<>();

        for (int i = 1; i <= totalPage; i++) {
            String dataApiUrl = apiUrl + "&pageIndex=" + i;
            String responseData = getData(dataApiUrl); // 수정된 URL 사용
            if (parseStringToObject(responseData) != null) {
                updatedDataList.addAll(parseStringToObject(responseData));
            }
        }
    }

    /**
     * 총 페이지 수
     *
     * @param data
     * @return
     */
    private int getTotalPage(String data) {

        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();

        // totalCount 값 추출
        JsonObject result = jsonObject.getAsJsonObject("result");
        JsonObject header = result.getAsJsonObject("header");
        JsonObject paging = header.getAsJsonObject("paging");

        int totalCount = paging.get("totalCount").getAsInt();
        int totalPage = 0;

        if (totalCount % PAGE_SIZE != 0) {
            totalPage = (totalCount / PAGE_SIZE) + 1;
        } else {
            totalPage = totalCount / PAGE_SIZE;
        }

        log.info("totalPage : {}", totalPage);
        return totalPage;
    }

    /**
     * 요청 url 생성
     *
     * @param apiReq
     * @return
     */
    private String getApiURL(ApiRequest apiReq) {

        String url = "http://www.localdata.go.kr/platform/rest/TO0/openDataApi?authKey=" + LOCALDATA_API_KEY;

        url += "&resultType=json"; // 반환 타입 default : xml
        url += "&opnSvcId=07_24_04_P";// 음식점 코드
        url += "&pageSize=" + PAGE_SIZE;// 개발 : 최대 500건 / 운영 최대 10,000건

        if (apiReq.getLocalCode() != null) {
            url += "&localCode=" + apiReq.getLocalCode();
        }
        if (apiReq.getBgnYmd() != null && apiReq.getEndYmd() != null) {
            url += "&bgnYmd=" + apiReq.getBgnYmd() + "&endYmd=" + apiReq.getEndYmd();
        }
        if (apiReq.getState() != null) {
            url += "&state=" + apiReq.getState();
        }
        if (apiReq.getState() != null) {
            // 데이터 갱신일자 시작일
            url += "&lastModTsBgn=" + apiReq.getLastModTsBgn();
        }
        if (apiReq.getState() != null) {
            // 데이터 갱신일자 종료일
            url += "&lastModTsEnd=" + apiReq.getLastModTsBgn();
        }

        return url;
    }

    /**
     * 데이터 요청
     *
     * @param apiUrl
     * @return
     * @throws Exception
     */
    private String getData(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        String tempStr;

        while ((tempStr = br.readLine()) != null) {
            sb.append(tempStr);
        }
        br.close();

        return sb.toString();
    }

    /**
     * 데이터 파싱
     *
     * @param data
     * @return
     */
    private List<ApiResponse> parseStringToObject(String data) {
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();

        JsonArray rows = jsonObject.getAsJsonObject("result")
                .getAsJsonObject("body")
                .getAsJsonArray("rows")
                .get(0).getAsJsonObject()
                .getAsJsonArray("row");

        Type listType = new TypeToken<List<ApiResponse>>() {}.getType();
        List<ApiResponse> apiResponses = gson.fromJson(rows, listType);

        return apiResponses;

    }

}