package com.example.demo.service;

import com.example.demo.api.dto.UserRegisterDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Phương thức xử lý logic đăng ký người dùng mới.
     * Bao gồm kiểm tra trùng lặp và lưu người dùng vào database.
     *
     * @param registerDto DTO chứa thông tin đăng ký từ người dùng.
     * @return User Entity đã được lưu vào database.
     * @throws IllegalArgumentException nếu email, username hoặc số điện thoại đã tồn tại.
     */
    @Transactional
    public User registerNewUser(UserRegisterDto registerDto) {
        //email existed
        if (userRepository.findByEmail(registerDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng thử lại.");
        }

        //username existed
        if (userRepository.findByUsername(registerDto.getUserName()) != null) {
            throw new IllegalArgumentException("Tên tài khoản đã tồn tại. Vui lòng thử lại.");
        }

        //phone number used
        if (userRepository.findByPhone(registerDto.getPhone()) != null) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại. Vui lòng thử lại.");
        }

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        User newUser = new User(registerDto.getUserName(),
                                encodedPassword,
                                registerDto.getEmail(),
                                registerDto.getPhone());

        return userRepository.save(newUser);
    }

    public User authenticate(String inputUsername, String inputPassword) {

        User user = userRepository.findByUsername(inputUsername);

        if (!passwordEncoder.matches(inputPassword, user.getPasswordHash())) {
            throw new BadCredentialsException("Tài khoản hoặc mật khẩu không chính xác");
        }

        return user;
    }
}
