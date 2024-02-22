package com.fmap.api.service;

import com.fmap.api.dto.ApiRequest;
import com.fmap.api.dto.ApiResponse;
import com.fmap.restaurant.repository.RestaurantRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ApiService {

    @Value("${LOCALDATA_API_KEY}")
    private String LOCALDATA_API_KEY;
    private final int PAGE_SIZE = 500;

    private final RestaurantRepository restaurantRepository;

    public ApiService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Api 데이터 호출
     *
     * @param apiUrl
     * @return
     * @throws Exception
     */
    public String getData(String apiUrl) throws Exception {
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
    public List<ApiResponse> parseStringToObject(String data) {
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();

        JsonArray rows = jsonObject.getAsJsonObject("result")
                .getAsJsonObject("body")
                .getAsJsonArray("rows")
                .get(0).getAsJsonObject()
                .getAsJsonArray("row");

        Type listType = new TypeToken<List<ApiResponse>>() {
        }.getType();
        List<ApiResponse> apiResponses = gson.fromJson(rows, listType);

        return apiResponses;
    }

    /**
     * 총 페이지 수 계산
     *
     * @param data
     * @return
     */
    public int getTotalPage(String data) {
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

        log.info("ApiService :: totalPage = {}", totalPage);
        return totalPage;
    }

    /**
     * API 호출 및 데이터 업데이트
     *
     * @param apiReq
     * @return
     * @throws Exception
     */
    public List<ApiResponse> fetchAndUpdateData(ApiRequest apiReq) throws Exception {
        String apiUrl = getApiURL(apiReq);
        String data = getData(apiUrl);
        int totalPage = getTotalPage(data);

        List<ApiResponse> updatedDataList = new ArrayList<>();
        for (int i = 1; i <= totalPage; i++) {
            String dataApiUrl = apiUrl + "&pageIndex=" + i;
            String responseData = getData(dataApiUrl);
            List<ApiResponse> apiResponses = parseStringToObject(responseData);
            if (apiResponses != null) {
                updatedDataList.addAll(apiResponses);
            }
        }
        return updatedDataList;
    }

    public List<ApiResponse> fetchAndUpdateData() throws Exception {
        String apiUrl = getApiURL();
        String data = getData(apiUrl);
        int totalPage = getTotalPage(data);

        List<ApiResponse> updatedDataList = new ArrayList<>();
        for (int i = 1; i <= totalPage; i++) {
            String dataApiUrl = apiUrl + "&pageIndex=" + i;
            String responseData = getData(dataApiUrl);
            List<ApiResponse> apiResponses = parseStringToObject(responseData);
            if (apiResponses != null) {
                updatedDataList.addAll(apiResponses);
            }
        }
        return updatedDataList;
    }


    /**
     * 요청 URL 생성
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

    private String getApiURL() {
        String url = "http://www.localdata.go.kr/platform/rest/TO0/openDataApi?authKey=" + LOCALDATA_API_KEY;

        url += "&resultType=json"; // 반환 타입 default : xml
        url += "&opnSvcId=07_24_04_P";// 음식점 코드
        url += "&pageSize=" + PAGE_SIZE;// 개발 : 최대 500건 / 운영 최대 10,000건

        // TODO 나중에 현재 날짜로
//        if (apiReq.getBgnYmd() != null && apiReq.getEndYmd() != null) {
//            url += "&bgnYmd=" + apiReq.getBgnYmd() + "&endYmd=" + apiReq.getEndYmd();
//        }
//        if (apiReq.getState() != null) {
//            url += "&state=" + apiReq.getState();
//        }
//        if (apiReq.getLastModTsBgn() != null) {
//            // 데이터 갱신일자 시작일
//            url += "&lastModTsBgn=" + apiReq.getLastModTsBgn();
//        }
//        if (apiReq.getLastModTsEnd() != null) {
//            // 데이터 갱신일자 종료일
//            url += "&lastModTsEnd=" + apiReq.getLastModTsBgn();
//        }

        return url;
    }



}
