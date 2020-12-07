package com.emotion.archive.controller;

import com.emotion.archive.service.user.UserService;
import com.emotion.archive.utils.LoggerUtils;
import com.emotion.archive.utils.StaticHelper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController extends LoggerUtils {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> registration(@RequestBody String payload) {
        logger.info("registration request : " + payload);
        JSONObject request = StaticHelper.getJsonObject(payload);
        return new ResponseEntity<>(userService.registration(request), HttpStatus.OK);
    }

    @GetMapping(value = "/{phone}/{password}")
    public ResponseEntity<?> checkPassword(@PathVariable String phone, @PathVariable String password) {
        logger.info("checkPassword request : phone = " + phone);
        return new ResponseEntity<>(userService.checkPassword(phone, password), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable Long id, @RequestBody String payload) {
        logger.info("updateUserInfo request : id = " + id + ", data = " + payload);
        JSONObject request = StaticHelper.getJsonObject(payload);
        return new ResponseEntity<>(userService.updateUserInfo(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUserInfo(@PathVariable Long id) {
        logger.info("deleteUserInfo request : id = " + id);
        return new ResponseEntity<>(userService.deleteUserInfo(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long id) {
        logger.info("getUserInfo request : id = " + id);
        return new ResponseEntity<>(userService.getUserInfo(id), HttpStatus.OK);
    }

    @PostMapping(value = "/lost/{phone}")
    public ResponseEntity<?> findPassword(@PathVariable String phone, @RequestParam(defaultValue = "") String email) {
        logger.info("findPassword request : phone = " + phone + ", email = " + email);
        return new ResponseEntity<>(userService.findPassword(phone, email), HttpStatus.OK);
    }

    /**
     * 관리자
     */
    @GetMapping(value = "/all/{adminId}")
    public ResponseEntity<?> getAllUserInfo(@PathVariable Long adminId) {
        logger.info("getAllUserInfo request : adminId = " + adminId);
        return new ResponseEntity<>(userService.getAllUserInfo(adminId), HttpStatus.OK);
    }

    @PatchMapping(value = "/role/{adminId}/{changeUserId}")
    public ResponseEntity<?> changeRole(@PathVariable Long adminId, @PathVariable Long changeUserId) {
        logger.info("changeRole request : adminId = " + adminId + ", changeUserId = " + changeUserId);
        return new ResponseEntity<>(userService.changeRole(adminId, changeUserId), HttpStatus.OK);
    }

    /**
     * 관리자 권한으로 수정
     */
    @PatchMapping(value = "/role/admin/{id}")
    public ResponseEntity<?> changeToAdmin(@PathVariable Long id) {
        logger.info("changeToAdmin request : id = " + id);
        return new ResponseEntity<>(userService.changeToAdmin(id), HttpStatus.OK);

    }

}
