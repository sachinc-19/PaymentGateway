package com.PaymentEngine.service;

import com.PaymentEngine.exceptions.customExceptions.ServiceException;
import com.PaymentEngine.model.Credential;
import com.PaymentEngine.repository.dao.UserRepository;
import com.PaymentEngine.repository.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;

    public void authenticate(Credential credential) {
        try {
            String username = credential.getUsername();
            String plainPassword = credential.getPassword();
            // fetch the password stored in db against this username
            User user = userRepository.findByUsername(username);

            // Compare the provided password with the stored hash
            if(!passwordService.checkPassword(plainPassword, user.getPassword())) {
                System.out.println("User authentication Unsuccessfull");
                throw new ServiceException("Unauthorized Access" , 401);
            }

            System.out.println("User authentication successfull");
        }
        catch (ServiceException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
