package edu.fondue.electronicdocuments.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Properties {

    @Value("${t.sftp.host}")
    private String host;

    @Value("${t.sftp.user}")
    private String user;

    @Value("${t.sftp.password}")
    private String password;

    @Value("${t.sftp.port}")
    private Integer port;

    @Value("${t.sftp.directory}")
    private String directory;

    @Value("${t.sftp.usersDirectory}")
    private String usersDirectory;

    @Value("${t.sftp.organizationsDirectory}")
    private String organizationsDirectory;

    @Value("${t.sftp.timeout}")
    private String timeout;

    @Value("${t.app.localStorage}")
    private String localStorage;
}
