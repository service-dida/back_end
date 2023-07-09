package com.service.dida.global.util.usecase;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface S3UseCase {

    String uploadImg(Long memberId, MultipartFile file, String type) throws IOException;
}
