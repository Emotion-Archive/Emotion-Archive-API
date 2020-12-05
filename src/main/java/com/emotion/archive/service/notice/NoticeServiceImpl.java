package com.emotion.archive.service.notice;

import com.emotion.archive.model.repository.NoticeRepository;
import com.emotion.archive.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class NoticeServiceImpl extends LoggerUtils implements NoticeService {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String sendMail;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttls;

    private JavaMailSender javaMailSender;
    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(JavaMailSender javaMailSender, NoticeRepository noticeRepository) {
        this.javaMailSender = javaMailSender;
        this.noticeRepository = noticeRepository;
    }

    @Override
    public boolean sendMail(String recvMail, String title, String content) {
        Properties props = new Properties();

        // smtp에 필요한 인증부
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.auth", auth);

        // 호스트 / 포트
        props.put("mail.smtp.host", host);
        if (port != null) {
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }

        // 인증을 포함한 메시지 만들기
        MimeMessage message = new MimeMessage(Session.getInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendMail, password);
            }
        }));

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(new InternetAddress(sendMail, "감정보관소"));
            helper.setTo(InternetAddress.parse(recvMail));
            helper.setSubject(title);
            helper.setText(content, true);
            helper.setSentDate(new Date());

            javaMailSender.send(message);
            logger.info("Send mail to " + recvMail + ", result = true");
            return true;
        } catch (Exception e) {
            logger.error("Send mail to " + recvMail + ", result = false, error = " + e.getMessage());
            return false;
        }
    }

}
