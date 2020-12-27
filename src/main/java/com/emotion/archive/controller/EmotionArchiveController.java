package com.emotion.archive.controller;

import com.emotion.archive.constants.ConstValue;
import com.emotion.archive.service.archive.EmotionArchiveService;
import com.emotion.archive.utils.LoggerUtils;
import com.emotion.archive.utils.StaticHelper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 감정보관소 API
 */
@RestController
@RequestMapping(value = "/archive")
public class EmotionArchiveController extends LoggerUtils {

    private EmotionArchiveService emotionArchiveService;

    @Autowired
    public EmotionArchiveController(EmotionArchiveService emotionArchiveService) {
        this.emotionArchiveService = emotionArchiveService;
    }

    @PostMapping(value = "/{userId}")
    public ResponseEntity<?> addMemo(@PathVariable Long userId, @RequestBody String payload) {
        logger.info("addMemo request : " + payload);
        JSONObject request = StaticHelper.getJsonObject(payload);
        return new ResponseEntity<>(emotionArchiveService.addMemo(userId, request), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> updateMemo(@RequestBody String payload) {
        logger.info("updateMemo request : " + payload);
        JSONObject request = StaticHelper.getJsonObject(payload);
        return new ResponseEntity<>(emotionArchiveService.updateMemo(request), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getMemo(@PathVariable Long id) {
        logger.info("getMemo id : " + id);
        return new ResponseEntity<>(emotionArchiveService.getMemo(id), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMemo(@RequestBody List<Long> ids) {
        logger.info("deleteMemo id count : " + ids.size());
        return new ResponseEntity<>(emotionArchiveService.deleteMemo(ids), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/{filter}/{q}")
    public ResponseEntity<?> getMemoWithFilter(@PathVariable Long userId, @PathVariable int filter, @PathVariable String q) {
        logger.info("getMemoWithFilter userId = " + userId + ", filter = " + filter + ", q = " + q);

        if (filter == ConstValue.FILTER_CALENDAR) {
            return new ResponseEntity<>(emotionArchiveService.getMemoByDateOfMonth(userId, q), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(emotionArchiveService.getMemoByArchiveType(userId, q), HttpStatus.OK);
        }
    }

}
