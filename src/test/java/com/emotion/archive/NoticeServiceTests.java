package com.emotion.archive;

import com.emotion.archive.model.dto.ResultDTO;
import com.emotion.archive.service.notice.NoticeService;
import com.emotion.archive.utils.GsonUtils;
import com.emotion.archive.utils.LoggerUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NoticeServiceTests extends LoggerUtils {

    @Autowired
    private NoticeService noticeService;

    @Test
    @Ignore
    public void sendMailTest() {
        String recvMail = "96bohyun@naver.com";
        String title = "[테스트]";
        String content = "test";
        System.out.println(noticeService.sendMail(recvMail, title, content));
    }

    @Test
    @Ignore
    public void gsonTest() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResultYn("Y");
        resultDTO.setId(1l);
        String result = GsonUtils.convertToJsonStr(resultDTO);
        System.out.println(result);
    }

}
