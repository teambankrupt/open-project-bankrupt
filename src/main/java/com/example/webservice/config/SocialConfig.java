package com.example.webservice.config;

import com.example.webservice.domains.users.services.UserService;
import com.example.webservice.domains.users.services.beans.ConnectionSignUpImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author mir00r on 3/4/20
 * @project IntelliJ IDEA
 */
@Configuration
@EnableSocial
@PropertySource("classpath:social-login.properties")
public class SocialConfig implements SocialConfigurer {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    private boolean autoSignUp = false;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment environment) {
        try {
            this.autoSignUp = Boolean.parseBoolean(environment.getProperty("social.auto-signup"));
        } catch (Exception e) {
            this.autoSignUp = false;
        }

        FacebookConnectionFactory ffactory = new FacebookConnectionFactory(Objects.requireNonNull(environment.getProperty("facebook.app.id")), Objects.requireNonNull(environment.getProperty("facebook.app.secret")));
        ffactory.setScope(environment.getProperty("facebook.scope"));
        cfConfig.addConnectionFactory(ffactory);

        GoogleConnectionFactory gfactory = new GoogleConnectionFactory(Objects.requireNonNull(environment.getProperty("google.client.id")), Objects.requireNonNull(environment.getProperty("google.client.secret")));
        gfactory.setScope(environment.getProperty("google.scope"));
        cfConfig.addConnectionFactory(gfactory);

        LinkedInConnectionFactory lfactory = new LinkedInConnectionFactory(Objects.requireNonNull(environment.getProperty("linkedin.consumer.key")), Objects.requireNonNull(environment.getProperty("linkedin.consumer.secret")));
        lfactory.setScope(environment.getProperty("linkedin.scope"));
        cfConfig.addConnectionFactory(lfactory);

        TwitterConnectionFactory tfactory = new TwitterConnectionFactory(Objects.requireNonNull(environment.getProperty("twitter.consumer.key")), Objects.requireNonNull(environment.getProperty("twitter.consumer.secret")));
        cfConfig.addConnectionFactory(tfactory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository usersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());

        if (autoSignUp) {
            ConnectionSignUp connectionSignUp = new ConnectionSignUpImpl(userService);
            usersConnectionRepository.setConnectionSignUp(connectionSignUp);
        } else {
            usersConnectionRepository.setConnectionSignUp(null);
        }
        return usersConnectionRepository;
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }
}
