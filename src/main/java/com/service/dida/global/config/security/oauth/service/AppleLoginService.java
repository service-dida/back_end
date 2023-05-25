package com.service.dida.global.config.security.oauth.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.service.dida.global.config.exception.BaseException;
import com.service.dida.global.config.exception.errorCode.AuthErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Objects;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AppleLoginService {
    public String getEmail(String idToken) throws BaseException {
        /**
         * 1. apple로 부터 공개키 3개 가져옴
         * 2. 내가 클라에서 가져온 token String과 비교해서 써야할 공개키 확인 (kid,alg 값 같은 것)
         * 3. 그 공개키 재료들로 공개키 만들고, 이 공개키로 JWT토큰 부분의 바디 부분의 decode하면 유저 정보
         */
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("https://appleid.apple.com/auth/keys");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(HttpMethod.GET.name());
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            throw new BaseException(AuthErrorCode.INVALID_ID_TOKEN);
        }
        JsonParser parser = new JsonParser();
        JsonObject keys = (JsonObject) parser.parse(result.toString());
        JsonArray keyArray = (JsonArray) keys.get("keys");

        //클라이언트로부터 가져온 identity token String decode
        String[] decodeArray = idToken.split("\\.");
        String header = new String(Base64.getDecoder().decode(decodeArray[0]));

        //apple에서 제공해주는 kid값과 일치하는지 알기 위해
        JsonElement kid = ((JsonObject) parser.parse(header)).get("kid");
        JsonElement alg = ((JsonObject) parser.parse(header)).get("alg");

        //써야하는 Element (kid, alg 일치하는 element)
        JsonObject avaliableObject = null;
        for (int i = 0; i < keyArray.size(); i++) {
            JsonObject appleObject = (JsonObject) keyArray.get(i);
            JsonElement appleKid = appleObject.get("kid");
            JsonElement appleAlg = appleObject.get("alg");

            if (Objects.equals(appleKid, kid) && Objects.equals(appleAlg, alg)) {
                avaliableObject = appleObject;
                break;
            }
        }
        //일치하는 공개키 없음
        if (ObjectUtils.isEmpty(avaliableObject))
            throw new BaseException(AuthErrorCode.FAILED_APPLE_LOGIN);

        PublicKey publicKey = this.getPublicKey(avaliableObject);

        Claims userInfo;
        try {
            userInfo = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(idToken).getBody();
        } catch (ExpiredJwtException e) {   // 토큰 만료
            throw new BaseException(AuthErrorCode.INVALID_ID_TOKEN);
        } catch (Exception e) {     // 그 외 에러
            throw new BaseException(AuthErrorCode.FAILED_APPLE_LOGIN);
        }

        JsonObject userInfoObject = (JsonObject) parser.parse(new Gson().toJson(userInfo));
        JsonElement appleAlg = userInfoObject.get("email");

        return appleAlg.getAsString();
    }

    public PublicKey getPublicKey(JsonObject object) throws BaseException {
        String nStr = object.get("n").toString();
        String eStr = object.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() - 1));

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception exception) {
            throw new BaseException(AuthErrorCode.FAILED_APPLE_LOGIN);
        }
    }
}
