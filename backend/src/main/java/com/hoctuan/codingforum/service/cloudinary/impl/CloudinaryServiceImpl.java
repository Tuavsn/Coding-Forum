package com.hoctuan.codingforum.service.cloudinary.impl;

import com.cloudinary.Cloudinary;
import com.hoctuan.codingforum.config.TikaAnalysis;
import com.hoctuan.codingforum.service.cloudinary.CloudinaryService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    private final TikaAnalysis tikaAnalysis;

    public CloudinaryServiceImpl(Cloudinary cloudinary, TikaAnalysis tikaAnalysis) {
        this.cloudinary = cloudinary;
        this.tikaAnalysis = tikaAnalysis;
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        String secureUrl = null;
        tikaAnalysis.checkSupportType(file, "image/png", "image/jpg", "image/jpeg", "image/webp");
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), null);
        secureUrl = (String) result.get("secure_url");
        return secureUrl;
    }

    @Override
    public void delete(String imgUrl) throws IOException {
        String[] parts = imgUrl.split("/");
        String publicId = parts[parts.length - 1].split("\\.")[0];
        cloudinary.uploader().destroy(publicId, null);
    }
}
