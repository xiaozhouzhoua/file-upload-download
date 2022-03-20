package com.me.fileuploaddownload.service.impl;

import com.me.fileuploaddownload.db.FileRepository;
import com.me.fileuploaddownload.entity.File;
import com.me.fileuploaddownload.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File saveFile(MultipartFile multipartFile) throws Exception {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }
            File file = new File(fileName, multipartFile.getContentType(), multipartFile.getBytes());
            return fileRepository.save(file);
        } catch (Exception e) {
            throw new Exception("could not save file: " + fileName);
        }
    }

    @Override
    public File getFile(String fileId) throws Exception {
        return fileRepository.findById(fileId).orElseThrow(() -> new Exception("File not found with id: " + fileId));
    }
}
