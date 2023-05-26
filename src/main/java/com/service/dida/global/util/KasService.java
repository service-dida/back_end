package com.service.dida.global.util;

import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.ErrorCode;
import com.service.dida.global.config.exception.errorCode.WalletErrorCode;
import com.service.dida.global.config.properties.KasProperties;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KasService {

    private final KasProperties kasProperties;

    public String checkResponse(HttpResponse<String> response, String parameter,
        ErrorCode errorCode) throws BaseException, ParseException {
        if (response.statusCode() != 200) {
            throw new BaseException(errorCode);
        }
        JSONObject jsonObject = parseBody(response);
        if (jsonObject.get(parameter) == null) {
            throw new BaseException(errorCode);
        }
        return (String) jsonObject.get(parameter);
    }

    public String useKasApi(String query, String method, HttpRequest.BodyPublisher body,
        String parameter, ErrorCode errorCode)
        throws IOException, InterruptedException, BaseException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(java.net.URI.create(query))
            .header("Content-Type", "application/json")
            .header("x-chain-id", kasProperties.getVersion())
            .header("Authorization", kasProperties.getAuthorization())
            .method(method, body)
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());
        return checkResponse(response, parameter, errorCode);
    }

    // 지갑 생성
    public String createAccount()
        throws BaseException, IOException, ParseException, InterruptedException {
        String url = "https://wallet-api.klaytnapi.com/v2/account";
        return useKasApi(
            url,
            "POST",
            HttpRequest.BodyPublishers.noBody(),
            "address",
            WalletErrorCode.FAILED_CREATE_WALLET
        );
    }


    public JSONObject parseBody(HttpResponse<String> response) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(response.body());
        return (JSONObject) obj;
    }
}
