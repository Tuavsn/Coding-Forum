package com.hoctuan.codingforum.config;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.exception.CustomException;

import java.io.IOException;
import java.io.InputStream;

@Component
public class TikaAnalysis {
    public void checkSupportType(MultipartFile file, String... supportType) throws IOException {
        String fileType = getContentType(file);
        for (String type : supportType) {
            if (fileType != null && fileType.equals(type)) {
                return;
            }
        }
        throw new CustomException(ErrorCode.UNSUPPORTED_FILE_TYPE);
    }

    private String getContentType(MultipartFile file) throws IOException {
        Tika tika  = new Tika();
        try(InputStream stream = file.getInputStream()) {
            return tika.detect(stream);
        }
    }
}
