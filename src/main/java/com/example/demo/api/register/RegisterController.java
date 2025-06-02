package com.example.demo.api.register;

import com.example.demo.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    //render page
    private final String renderRegisterPage = "dangnhap&dangky/dangky";
    private final String registerDto = "registerDto";
    private final String successRegistered = "dangkyThanhcong";

    @Autowired
    private RegisterRepository registerRepository;

    // create new model when the web is loaded the first time
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if(model.containsAttribute(registerDto)){
            model.addAttribute(registerDto);
        }
        return renderRegisterPage;
    }

    @PostMapping("/register")
    public String getNewUser(@ModelAttribute("registerDto") @Valid UserRegisterDto registerDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        // validation
        if (bindingResult.hasErrors()) {
            return renderRegisterPage;
        }
        if (registerRepository.findByEmail(registerDto.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.registerDto.email", "Đã tồn tại email. Vui lòng thử lại");
        } 
        if (registerRepository.findByUsername(registerDto.getUserName()) != null){
            bindingResult.rejectValue("userName","error.registerDto.userName", "Đã tồn tại tên. Vui lòng thử lại");
        }
        if (!registerDto.isPasswordMatched()) {
            bindingResult.rejectValue("confirmPassword", "error.registerDto.confirmPassword", "Mật khẩu xác nhận sai. Vui lòng thử lại");
        }
        if (bindingResult.hasErrors()) {
            return renderRegisterPage;
        }
        
        //add registered User
        else {
            User newUser = new User(registerDto.getUserName(),
                                    // TODO: MÃ HÓA MẬT KHẨU TRƯỚC KHI LƯU!
                                    registerDto.getPassword(), 
                                    registerDto.getEmail(), 
                                    registerDto.getPhone());
            registerRepository.save(newUser);
            redirectAttributes.addFlashAttribute(successRegistered, "Đăng ký thành công!");

            return "redirect:/dangky-thanhcong";
        }
    }
    @GetMapping("/dangky-thanhcong")
    public String registrationSuccess(Model model) {
        if (!model.containsAttribute(successRegistered)) {
            model.addAttribute(successRegistered, "Đăng ký thành công!");
        }
        return "dangky-thanhcong";
    }
}
