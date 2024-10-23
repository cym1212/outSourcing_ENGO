package OutSourcing.ENGO.global.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDTO {



    @NotBlank
    @Email(message = "잘못된 이메일 형식입니다")
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNum;

    //todo 실제 배포 전에는 주석 풀기
    @NotBlank
    @Pattern(message = "잘못된 비밀번호 형식입니다."
            , regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}")
    private String password;


    @NotBlank
    @Pattern(message = "잘못된 비밀번호 형식입니다."
            , regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}")
    private String confirmPassword;


}
