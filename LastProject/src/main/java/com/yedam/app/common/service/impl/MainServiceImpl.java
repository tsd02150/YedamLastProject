package com.yedam.app.common.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.mapper.MainMapper;
import com.yedam.app.common.service.MainService;
import com.yedam.app.community.service.BoardVO;
import com.yedam.app.community.service.NoticeVO;
import com.yedam.app.community.service.QuestionVO;
import com.yedam.app.stock.service.StockVO;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	MainMapper mainMapper;

	// 농수산물 관련 뉴스 정보
	@Override
	public String getNews() {
		String clientId = "Tvcb97zOj2AcVXaZgr7J"; //애플리케이션 클라이언트 아이디
        String clientSecret = "rsLu6clOBi"; //애플리케이션 클라이언트 시크릿

        String text = null;
        try {
            text = URLEncoder.encode("농수산물", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text;    // JSON 결과
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

		return responseBody;
	}
	
	private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }
    
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
    
	// 자유게시판 6개 출력
	@Override
	public List<BoardVO> getFreeBoardTop6() {
		return mainMapper.getFreeBoardTop6();
	}

	// 주식게시판 6개 출력
	@Override
	public List<BoardVO> getStockBoardTop6() {
		return mainMapper.getStockBoardTop6();
	}

	// 공지사항 6개
	@Override
	public List<NoticeVO> getNoticeTop6() {
		return mainMapper.getNoticeTop6();
	}
	
	// Qna 6개
	@Override
	public List<QuestionVO> getQnaTop6() {
		return mainMapper.getQnaTop6();
	}

	@Override
	public List<StockVO> getFarmRank() {
		return mainMapper.getFarmRank();
	}

	@Override
	public List<StockVO> getSeaRank() {
		return mainMapper.getSeaRank();
	}

	@Override
	public List<StockVO> getIncreaseStock() {
		return mainMapper.getIncreaseStock();
	}

	@Override
	public List<StockVO> getDecreaseStock() {
		return mainMapper.getDecreaseStock();
	}

	@Override
	public List<StockVO> mainChartList() {
		return mainMapper.mainChartList();
	}


}
