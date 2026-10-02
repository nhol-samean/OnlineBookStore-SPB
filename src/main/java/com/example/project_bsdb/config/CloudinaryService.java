package com.example.project_bsdb.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.project_bsdb.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {

        this.cloudinary = cloudinary;
    }

    public Map uploadImage(MultipartFile file) {
        try {
            return cloudinary.uploader()
                    .upload(
                            file.getBytes(),
                            ObjectUtils.emptyMap()
                    );
        }catch (Exception e){
            throw new BadRequestException("File upload failed"+e.getMessage());
        }

    }

    public Map deleteImage(String publicId){

        try {
            return cloudinary.uploader()
                    .destroy(
                            publicId,
                            ObjectUtils.emptyMap());
        }catch (Exception e){
            throw new BadRequestException("Delete image failed"+e.getMessage());
        }
    }
}
