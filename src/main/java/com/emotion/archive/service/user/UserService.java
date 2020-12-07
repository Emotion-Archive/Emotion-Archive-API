package com.emotion.archive.service.user;

import org.json.simple.JSONObject;

public interface UserService {

    /**
     * 회원가입
     */
    String registration(JSONObject request);

    /**
     * 비밀번호 체크
     */
    boolean checkPassword(String phone, String password);

    /**
     * 회원정보 수정
     */
    String updateUserInfo(Long id, JSONObject request);

    /**
     * 회원탈퇴
     */
    boolean deleteUserInfo(Long id);

    /**
     * 비밀번호 찾기 (랜덤 비밀번호 생성 후 메일로 전송
     */
    String findPassword(String phone, String email);

    /**
     * 권한 변경
     */
    boolean changeRole(Long adminId, Long changeUserId);

    /**
     * 사용자 정보 조회
     */
    String getUserInfo(Long id);

    /**
     * 사용자 전체 조회 (관리자)
     */
    String getAllUserInfo(Long adminId);

    /**
     * 관리자 권한 부여
     */
    boolean changeToAdmin(Long id);

}
