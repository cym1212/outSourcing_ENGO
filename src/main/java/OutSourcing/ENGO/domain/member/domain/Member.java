package OutSourcing.ENGO.domain.member.domain;


import OutSourcing.ENGO.global.domain.BaseEntity;
import OutSourcing.ENGO.global.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {


    @Setter
    private String refreshToken;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private Role role;

    private String password;

    private String email;

    private String phoneNumber;

    private String name;




    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

}
