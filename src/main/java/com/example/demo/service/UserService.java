package com.example.demo.service;

import com.example.demo.api.dto.UserRegisterDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserService {
    private Map<String, String> otpStorage = new HashMap<>(); // email -> OTP
    private Map<String, LocalDateTime> otpExpiry = new HashMap<>(); // email -> expiryTime

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

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
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng xem lại.");
        }

        //username existed
        if (userRepository.findByUsername(registerDto.getUserName()) != null) {
            throw new IllegalArgumentException("Tên tài khoản đã tồn tại. Vui lòng xem lại.");
        }

        //phone number used
        if (userRepository.findByPhone(registerDto.getPhone()) != null) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại. Vui lòng xem lại.");
        }

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User newUser = new User(registerDto.getUserName(),
                                encodedPassword,
                                registerDto.getEmail(),
                                registerDto.getPhone());
        return userRepository.save(newUser);
    }

    public void OTPEmailSender(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new IllegalArgumentException("Email không tồn tại trong hệ thống. Vui lòng xem lại.");
        }

        String outputOtp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(2);

        otpStorage.put(email, outputOtp);
        otpExpiry.put(email, expiryTime);

        mailService.sendOtpEmail(email, outputOtp);
        System.out.println("Sent OTP to user with email: " + email);
    }

    public boolean verifyOTP(String inputOtp, String email) {
        String storedOtp = otpStorage.get(email);
        LocalDateTime storedExpiryTime = otpExpiry.get(email);

        if (storedOtp == null || !storedOtp.equals(inputOtp)) {
            throw new IllegalArgumentException("Mã OTP không hợp lệ");
        }
        if (storedExpiryTime == null || storedExpiryTime.isBefore(LocalDateTime.now())){
            otpStorage.remove(email);
            otpExpiry.remove(email);
            throw new IllegalArgumentException("Mã OTP đã hết hạn");
        }

        otpStorage.remove(email);
        otpExpiry.remove(email);
        return true;
    }
}
