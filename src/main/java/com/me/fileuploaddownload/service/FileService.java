package com.me.fileuploaddownload.service;

import com.me.fileuploaddownload.entity.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    File saveFile(MultipartFile file) throws Exception;

    File getFile(String fileId) throws Exception;
}
