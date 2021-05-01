package kim.sihwan.daangnclone.exception;



import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_NULL(1, "필수 값이 누락되었습니다."),
    DUPLICATED_USERNAME(2, "이미 존재하는 회원입니다."),
    INVALID_LOGIN_INFO(3, "아이디 혹은 비밀번호를 확인해주세요."),
    INVALID_TOKEN(4, "잘못된 토큰정보입니다. 다시 로그인을 진행해주세요."),
    EXPIRED_TOKEN(5, "만료된 토큰입니다. 다시 로그인을 진행해주세요."),
    NON_LOGIN(6, "로그인이 필요합니다.");


    private int code;
    private String description;

    ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}