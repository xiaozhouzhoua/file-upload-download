package com.me.fileuploaddownload.api;

import com.me.fileuploaddownload.entity.File;
import com.me.fileuploaddownload.entity.ResponseData;
import com.me.fileuploaddownload.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileApi {
    private final FileService fileService;

    public FileApi(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        File file = fileService.saveFile(multipartFile);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(file.getId())
                .toUriString();

        return new ResponseData(file.getFileName(), downloadURL,
                multipartFile.getContentType(), multipartFile.getSize() );
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) throws Exception {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName()
                        + "\"")
                .body(new ByteArrayResource(file.getData()));
    }
}
