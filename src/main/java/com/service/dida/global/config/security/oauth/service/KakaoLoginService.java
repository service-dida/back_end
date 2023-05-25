package com.service.dida.global.config.security.oauth.service;

import com.google.gson.JsonParser;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.AuthErrorCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class KakaoLoginService {
    public String getEmail(String accessToken) throws IOException {
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(HttpMethod.GET.name());
        conn.setRequestProperty("Authorization", " Bearer " + accessToken);

        if (conn.getResponseCode() >= 400) {
            throw new BaseException(AuthErrorCode.INVALID_ID_TOKEN);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        return new JsonParser().parse(result.toString()).getAsJsonObject().get("kakao_account")
            .getAsJsonObject().getAsJsonObject().get("email").getAsString();
    }
}
