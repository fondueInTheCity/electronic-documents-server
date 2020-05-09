package edu.fondue.electronicdocuments.services;

public interface CryptoService {

    void addSignatureToPdf(String path, Long userId, String fileName, String reason);
}
