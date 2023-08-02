package com.service.dida.global.util.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.dida.domain.member.entity.Member;
import com.service.dida.domain.nft.dto.NftRequestDto.DrawPictureRequestDto;
import com.service.dida.domain.wallet.usecase.WalletUseCase;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.NftErrorCode;
import com.service.dida.global.util.dto.AiPictureDto;
import com.service.dida.global.util.usecase.AiUseCase;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService implements AiUseCase {

    private final WalletUseCase walletUseCase;

    @Value("${ai.auth}")
    private String auth;

    @Override
    public AiPictureDto drawPicture(Member member, DrawPictureRequestDto drawPictureRequestDto)
        throws IOException, ParseException, InterruptedException {
        // 수수료 및 지갑 사용 확인 부분 필요, 하나의 함수를 만들어서 해당 함수 내부에 수수료 및 지갑 사용 넣을 예정
        return drawPictureWithAi(drawPictureRequestDto.getSentence());
    }

    public AiPictureDto drawPictureWithAi(String sentence)
        throws IOException, InterruptedException, ParseException {
        String size = "1024x1024";
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(
            "{\n " +
                "\"prompt\": \"" + sentence + "\",\n    " +
                "\"n\": 4 ,\n    " +
                "\"size\": \"" + size + "\" \n" +
                "}"
        );
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/images/generations"))
            .header("Content-Type", "application/json")
            .header("Authorization", auth)
            .method("POST", body)
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            BaseException baseException = new BaseException(NftErrorCode.FAILED_DRAW_PICTURE);
            baseException.setAiErrorMessage(takeErrorCause(response.body()));
            throw baseException;
        }
        return new AiPictureDto(takeOnlyUrls(response));
    }

    private List<String> takeOnlyUrls(HttpResponse<String> response)
        throws ParseException, JsonProcessingException {
        JSONParser parser = new JSONParser();
        Object responseBody = parser.parse(response.body());
        JSONObject urls = (JSONObject) responseBody;

        String jsonString = new ObjectMapper().writeValueAsString(urls.get("data"));
        JSONArray jsonArray = (JSONArray) parser.parse(jsonString);

        List<String> urlList = new ArrayList<>();
        jsonArray.forEach(t -> urlList.add(((JSONObject) t).get("url").toString()));
        return urlList;
    }

    private String takeErrorCause(String body) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(body);
        JSONObject jsonObject = (JSONObject) obj;

        JSONObject error = (JSONObject) jsonObject.get("error");
        return error.get("message").toString();
    }
}
