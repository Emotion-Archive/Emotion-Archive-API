package com.emotion.archive.controller;

import com.emotion.archive.service.emotion.AddEmotionService;
import com.emotion.archive.utils.LoggerUtils;
import com.emotion.archive.utils.StaticHelper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 감정 설정 API
 */
@RestController
@RequestMapping(value = "/emotion")
public class AddEmotionController extends LoggerUtils {

    private AddEmotionService addEmotionService;

    @Autowired
    public AddEmotionController(AddEmotionService addEmotionService) {
        this.addEmotionService = addEmotionService;
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<?> addEmotion(@PathVariable Long userId, @RequestBody String payload) {
        logger.info("addEmotion request : " + payload);
        JSONObject request = StaticHelper.getJsonObject(payload);
        return new ResponseEntity<>(addEmotionService.addEmotion(userId, request), HttpStatus.OK);
    }

    @PatchMapping(value = "/{userId}")
    public ResponseEntity<?> updateEmotion(@PathVariable Long userId, @RequestBody String payload) {
        logger.info("updateEmotion request : " + payload);
        JSONObject request = StaticHelper.getJsonObject(payload);
        return new ResponseEntity<>(addEmotionService.updateEmotion(userId, request), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEmotion(@RequestBody List<Long> ids) {
        logger.info("deleteEmotion ids count : " + ids.size());
        return new ResponseEntity<>(addEmotionService.deleteEmotion(ids), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/{archiveType}")
    public ResponseEntity<?> getEmotionList(@PathVariable Long userId, @PathVariable String archiveType) {
        logger.info("getEmotionList userId : " + userId + ", archiveType : " + archiveType);
        return new ResponseEntity<>(addEmotionService.getEmotionList(userId, archiveType), HttpStatus.OK);
    }

}
