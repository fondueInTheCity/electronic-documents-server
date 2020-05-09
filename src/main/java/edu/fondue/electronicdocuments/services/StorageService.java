package edu.fondue.electronicdocuments.services;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {

    void upload(String path, MultipartFile file);

    @SneakyThrows
    void upload(String path, InputStream is, String name);

    byte[] download(String path);

    void createFolder(String path);

    void renameFile(String pathFrom, String pathTo);

    void change(String path, InputStream is);

    void uploadSignaturePng(byte[] imgSource, Long userId, String imgName);
}
