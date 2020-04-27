package edu.fondue.electronicdocuments.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void upload(String path, MultipartFile file);

    byte[] download(String path);

    void createFolder(String path);
}
