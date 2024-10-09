package OutSourcing.ENGO.global.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class DuplicateDTO {

    @Getter
    @Setter
    public static class Email {
        @JsonProperty("email")
        private String email;
    }


}
