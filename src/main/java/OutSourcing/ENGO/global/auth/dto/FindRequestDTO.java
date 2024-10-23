package OutSourcing.ENGO.global.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindRequestDTO {

    @NotNull
    private String phoneNumber;
    @NotNull
    private String name;

    @Getter
    @Setter
    public static class Password extends FindRequestDTO {

        @NotNull
        @Email
        private String email;
    }

    @Getter
    @Setter
    public static class ResetPassword  {

        @NotNull
        @Email
        private String email;

        @NotBlank
        @Pattern(message = "잘못된 비밀번호 형식입니다."
                , regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}")
        private String password;
        @NotBlank
        @Pattern(message = "잘못된 비밀번호 형식입니다."
                , regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}")
        private String confirmPassword;
    }

}
