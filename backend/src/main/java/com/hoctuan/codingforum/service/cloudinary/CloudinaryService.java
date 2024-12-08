package com.hoctuan.codingforum.service.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    public String upload(MultipartFile file) throws IOException;

    public void delete(String imgUrl) throws IOException;
}
    