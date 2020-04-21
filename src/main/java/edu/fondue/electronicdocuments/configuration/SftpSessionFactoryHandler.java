package edu.fondue.electronicdocuments.configuration;

import edu.fondue.electronicdocuments.utils.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SftpSessionFactoryHandler {

    private final Properties properties;

    public DefaultSftpSessionFactory gimmeFactory() {
        final DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();

        factory.setHost(properties.getHost());
        factory.setPort(properties.getPort());
        factory.setAllowUnknownKeys(true);
        factory.setUser(properties.getUser());
        factory.setPassword(properties.getPassword());

        return factory;
    }
}
