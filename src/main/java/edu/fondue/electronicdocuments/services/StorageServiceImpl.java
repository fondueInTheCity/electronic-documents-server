package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.SftpSessionFactoryHandler;
import edu.fondue.electronicdocuments.utils.Properties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final Properties properties;

    private final SftpSessionFactoryHandler sftpSessionFactoryHandler;

    @SneakyThrows
    @Override
    public void upload(final String path, final MultipartFile file) {
        final SftpSession session = sftpSessionFactoryHandler.gimmeFactory().getSession();
        final InputStream fileStream = file.getInputStream();

        session.write(fileStream, format("%s%s/%s", properties.getDirectory(), path, file.getOriginalFilename()));
        session.close();
    }

    @SneakyThrows
    @Override
    public void upload(final String path, final InputStream is, final String name) {
        final SftpSession session = sftpSessionFactoryHandler.gimmeFactory().getSession();

        session.write(is, format("%s%s/%s", properties.getDirectory(), path, name));
        session.close();
    }

    @SneakyThrows
    @Override
    public byte[] download(final String path) {
        final SftpSession session = sftpSessionFactoryHandler.gimmeFactory().getSession();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        session.read(path, outputStream);
        return outputStream.toByteArray();
    }

    @SneakyThrows
    @Override
    public void createFolder(final String path) {
        final SftpSession session = sftpSessionFactoryHandler.gimmeFactory().getSession();

        session.mkdir(format("%s/%s", properties.getDirectory(), path));
    }

    @SneakyThrows
    @Override
    public void renameFile(final String pathFrom, final String pathTo) {
        final SftpSession session = sftpSessionFactoryHandler.gimmeFactory().getSession();

        session.rename(pathFrom, pathTo);
        session.close();
    }

    @Override
    @SneakyThrows
    public void change(String path, InputStream is) {
        final SftpSession session = sftpSessionFactoryHandler.gimmeFactory().getSession();

        session.remove(path);
        session.write(is, path);
        session.close();
    }

    @Override
    @SneakyThrows
    public void uploadSignaturePng(final byte[] imgSource, final Long userId, final String imgName) {
        final SftpSession session = sftpSessionFactoryHandler.gimmeFactory().getSession();
        final String path = format("electronic-documents/users/%d/%s", userId, imgName);

        if (session.exists(path)) {
            session.remove(path);
        }
        session.write(new ByteArrayInputStream(imgSource), path);
    }
}
