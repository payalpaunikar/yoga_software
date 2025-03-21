package com.yoga.config;


import com.yoga.component.Role;
import com.yoga.datamodel.User;
import com.yoga.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminConfig implements ApplicationRunner {

    @Value("${admin.userName}")
    private String adminUserName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.mobileNumber}")
    private String adminMobileNumber;

    @Value("${admin.address}")
    private String adminAddress;


    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminConfig(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Logger logger = LoggerFactory.getLogger(AdminConfig.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userRepository.count() <=0){
            User adminUser = new User();
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRole(Role.ADMIN);

           User newAdminUser = userRepository.save(adminUser);

            logger.info("admin email : "+newAdminUser.getEmail()+" save succefully");

        }
    }
}
