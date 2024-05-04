package com.fmap.utils;

import com.fmap.dto.RestaurantReq;
import com.fmap.dto.RestaurantRes;
import com.fmap.restaurant.repository.RestaurantRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalDataApiUtils {

    private final String SERVICE_CODE = "07_24_04_P"; // 음식점 코드
    @Value("${LOCALDATA_API_KEY}")
    private String LOCALDATA_API_KEY;
    private final int PAGE_SIZE = 500;
    private final String API_URL = "http://www.localdata.go.kr/platform/rest/TO0/openDataApi";

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
    public List<RestaurantRes> parseStringToObject(String data) {
        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();

        JsonArray rows = jsonObject.getAsJsonObject("result")
                .getAsJsonObject("body")
                .getAsJsonArray("rows")
                .get(0).getAsJsonObject()
                .getAsJsonArray("row");

        Type listType = new TypeToken<List<RestaurantRes>>() {
        }.getType();
        List<RestaurantRes> apiRespons = gson.fromJson(rows, listType);

        return apiRespons;
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
    public List<RestaurantRes> fetchAndUpdateData(RestaurantReq apiReq) throws Exception {
        String apiUrl = getApiURL(apiReq);
        String data = getData(apiUrl);
        int totalPage = getTotalPage(data);

        log.info("fetchAndUpdateData :: totalPage = {}", totalPage );

        List<RestaurantRes> updatedDataList = new ArrayList<>();
        for (int i = 1; i <= totalPage; i++) {
            String dataApiUrl = apiUrl + "&pageIndex=" + i;
            String responseData = getData(dataApiUrl);
            List<RestaurantRes> apiRespons = parseStringToObject(responseData);
            if (apiRespons != null) {
                updatedDataList.addAll(apiRespons);
            }
        }
        return updatedDataList;
    }

    /**
     * API 호출 및 데이터 업데이트
     *
     * @param
     * @return
     * @throws Exception
     */
    public List<RestaurantRes> fetchAndUpdateData(){

        try {
            String apiUrl = getApiURL();
            String data = getData(apiUrl);
            int totalPage = getTotalPage(data);

            List<RestaurantRes> updatedDataList = new ArrayList<>();
            for (int i = 1; i <= totalPage; i++) {
                String dataApiUrl = apiUrl + "&pageIndex=" + i;
                String responseData = getData(dataApiUrl);
                List<RestaurantRes> apiRespons = parseStringToObject(responseData);
                if (apiRespons != null) {
                    updatedDataList.addAll(apiRespons);
                }
            }
            return updatedDataList;
        } catch (Exception e) {
            log.error("LocalDataApiUtils :: fetchAndUpdateData() ====== ",e);
            return null;
        }
    }


    /**
     * 요청 URL 생성
     *
     * @param apiReq
     * @return
     */
    private String getApiURL(RestaurantReq apiReq) {
        String url = API_URL + "?authKey=" + LOCALDATA_API_KEY;

        url += "&resultType=json"; // 반환 타입 default : xml
        url += "&opnSvcId=07_24_04_P";// 음식점 코드
        url += "&pageSize=" + PAGE_SIZE;// 개발 : 최대 500건 / 운영 최대 10,000건

        if (apiReq.getLocalCode() != null) {
            url += "&localCode=" + apiReq.getLocalCode();
        }
        // bgnYmd, endYmd = YYYYMMDD
        if (apiReq.getBgnYmd() != null && apiReq.getEndYmd() != null) {
            url += "&bgnYmd=" + apiReq.getBgnYmd() + "&endYmd=" + apiReq.getEndYmd();
        }
        url += "&bgnYmd=" + "20240323" + "&endYmd=" + "20240325";
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
        String url = API_URL + "?authKey=" + LOCALDATA_API_KEY;

        url += "&resultType=json"; // 반환 타입 default : xml
        url += "&opnSvcId=" + SERVICE_CODE;// 음식점 코드
        url += "&pageSize=" + PAGE_SIZE;// 개발 : 최대 500건 / 운영 최대 10,000건

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String today = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        LocalDateTime twoDaysAgo = now.minusDays(2);
        String twoDaysAgoDate = twoDaysAgo.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        log.info("lastModTsBgn(변동분 시작일) : {}", twoDaysAgoDate);
        log.info("lastModTsBgn(변동분 종료일) : {}", today);

        url += "&lastModTsBgn=" + twoDaysAgoDate;
        url += "&lastModTsEnd=" + today;

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
