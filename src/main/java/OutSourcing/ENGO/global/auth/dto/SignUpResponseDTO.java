package OutSourcing.ENGO.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class SignUpResponseDTO {

    private String email;
    private String name;
    private String  phoneNum;
    private int status;

}
