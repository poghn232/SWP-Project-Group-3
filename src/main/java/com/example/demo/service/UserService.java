package com.example.demo.service;

import com.example.demo.api.dto.account.ChangePasswordDto;
import com.example.demo.api.dto.account.UserDto;
import com.example.demo.api.dto.authentication.UserLoginDto;
import com.example.demo.api.dto.authentication.UserRegisterDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
    private Map<String, String> otpStorage = new HashMap<>(); // email -> OTP
    private Map<String, LocalDateTime> otpExpiry = new HashMap<>(); // email -> expiryTime

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserInfo(String username) {
        return userRepository.findByUsername(username);
    }

    public void doLogin(UserLoginDto userLoginDto) {
        if (userRepository.findByUsername(userLoginDto.getUsername()) == null) {
            throw new IllegalArgumentException("Username incorrect. Try again!");
        } else {
            User foundUser = userRepository.findByUsername(userLoginDto.getUsername());
            if (!foundUser.getPassword().equals(userLoginDto.getPassword())) {
                throw new IllegalArgumentException("Password incorrect. Try again!");
            }
        }
    }

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

        if (user == null) {
            throw new IllegalArgumentException("Email không tồn tại trong hệ thống. Vui lòng xem lại.");
        }

        String outputOtp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

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
        if (storedExpiryTime == null || storedExpiryTime.isBefore(LocalDateTime.now())) {
            otpStorage.remove(email);
            otpExpiry.remove(email);
            throw new IllegalArgumentException("Mã OTP đã hết hạn");
        }

        otpStorage.remove(email);
        otpExpiry.remove(email);
        return true;
    }

    @Transactional
    public void resetPassword(String email, String password, String confirmPassword) {
        User user = userRepository.findByEmail(email);

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không đúng. Vui lòng thử lại");
        }

        String hashedPassword = passwordEncoder.encode(password);
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);
    }

    @Transactional
    public void changePassword(String username, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findByUsername(username);

        if (!user.getPassword().equals(changePasswordDto.getOldPassword())) {
            throw new IllegalArgumentException("Old password isn't correct. Try again");
        }

        if (!changePasswordDto.confirmingPassword()) {
            throw new IllegalArgumentException(("Confirm password isn't correct. Try again"));
        }

        String hashedPassword = passwordEncoder.encode(changePasswordDto.getPassword());
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(String username) {
        User user = userRepository.findByUsername(username);

        userRepository.delete(user);
    }

    @Transactional
    public User changeUserInformation(String username, UserDto changedUserDto) {
        User user = userRepository.findByUsername(username);

        //email found in db and it is someone else's email
        if (userRepository.findByEmail(changedUserDto.getEmail()) != null) {
            if (!userRepository.findByEmail(changedUserDto.getEmail()).getEmail().equals(user.getEmail())){
                throw new IllegalArgumentException("Email đã tồn tại. Vui lòng xem lại.");
            }
        }

        //username found in db and it is someone else's username
        if (userRepository.findByUsername(changedUserDto.getUsername()) != null) {
            if (!userRepository.findByUsername(changedUserDto.getUsername()).getUsername().equals(user.getUsername())){
                throw new IllegalArgumentException("Tên tài khoản đã tồn tại. Vui lòng xem lại.");
            }
        }


        //phone number found in db and it is someone else's phone number
        if (userRepository.findByPhone(changedUserDto.getPhone()) != null) {
            if (!userRepository.findByPhone(changedUserDto.getPhone()).getPhone().equals(user.getPhone())){
                throw new IllegalArgumentException("Số điện thoại đã tồn tại. Vui lòng xem lại.");
            }
        }

        user.setUsername(changedUserDto.getUsername());
        user.setEmail(changedUserDto.getEmail());
        user.setPhone(changedUserDto.getPhone());

        userRepository.save(user);
        return user;
    }
}
