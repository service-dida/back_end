package com.service.dida.domain.alarm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.service.dida.domain.alarm.Alarm;
import com.service.dida.domain.alarm.AlarmMessage;
import com.service.dida.domain.alarm.AlarmType;
import com.service.dida.domain.alarm.repository.AlarmRepository;
import com.service.dida.domain.alarm.usecase.RegisterAlarmUseCase;
import com.service.dida.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterAlarmService implements RegisterAlarmUseCase {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/dida-7ebdf/messages:send";
    private final ObjectMapper objectMapper;
    private final AlarmRepository alarmRepository;

    private void save(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    private Long register(AlarmType type, Member member, Long id) {
        Alarm alarm = Alarm.builder()
            .member(member)
            .type(type)
            .id(id)
            .watched(false)
            .build();
        save(alarm);
        return alarm.getAlarmId();
    }

    @Override
    public void registerSaleAlarm(Member member, Long nftId) throws IOException {
        sendMessageTo(
            member.getDeviceToken(),
            register(AlarmType.SALE, member, nftId),
            "판매",
            "NFT가 판매되었습니다."
        );
    }

    @Override
    public void registerLikeAlarm(Member member, Long nftId) throws IOException {
        sendMessageTo(
            member.getDeviceToken(),
            register(AlarmType.LIKE, member, nftId),
            "좋아요",
            "누군가 내 NFT에 좋아요를 눌렀습니다."
        );
    }

    @Override
    public void registerFollowAlarm(Member member, Long followerId) throws IOException {
        sendMessageTo(
            member.getDeviceToken(),
            register(AlarmType.FOLLOW, member, followerId),
            "팔로우",
            "누군가 나를 팔로우 합니다."
        );
    }

    @Override
    public void registerCommentAlarm(Member member, Long commentId) throws IOException {
        sendMessageTo(
            member.getDeviceToken(),
            register(AlarmType.COMMENT, member, commentId),
            "댓글",
            "누군가 내 게시글에 댓글을 달았습니다."
        );
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase-service-key.json";
        GoogleCredentials googleCredentials = GoogleCredentials
            .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
            .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private void sendMessageTo(String targetToken, Long alarmId, String title, String body)
        throws IOException {
        String message = makeMessage(targetToken, alarmId, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
            MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .addHeader(com.google.common.net.HttpHeaders.AUTHORIZATION,
                "Bearer " + getAccessToken())
            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
            .build();

        Response response = client.newCall(request).execute();
        System.out.println(response);
    }

    private String makeMessage(String targetToken, Long alarmId, String title, String body)
        throws JsonProcessingException {
        HashMap map = new HashMap();
        map.put("alarmId", Long.toString(alarmId));
        AlarmMessage fcmMessage = AlarmMessage.builder()
            .message(AlarmMessage.Message.builder()
                .token(targetToken)
                .notification(AlarmMessage.Notification.builder()
                    .title(title)
                    .body(body)
                    .build()
                )
                .data(map)
                .build()
            )
            .validate_only(false)
            .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }
}
