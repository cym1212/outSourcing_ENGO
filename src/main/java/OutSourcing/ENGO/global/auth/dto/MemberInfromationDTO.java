package OutSourcing.ENGO.global.auth.dto;


import OutSourcing.ENGO.global.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfromationDTO {

    private Long memberId;
    private String email;
    private String name;
    private String phoneNum;
    private Role role;
    private String assignedCoach;
    private String lessonSongUrl;

    // 필요 시 별도의 메서드를 추가하여 새로운 인스턴스를 반환하는 방식 사용
    public MemberInfromationDTO withAssignedCoach(String assignedCoach) {
        return MemberInfromationDTO.builder()
                .memberId(this.memberId)
                .email(this.email)
                .name(this.name)
                .phoneNum(this.phoneNum)
                .role(this.role)
                .assignedCoach(assignedCoach)
                .lessonSongUrl(this.lessonSongUrl)
                .build();
    }

    public MemberInfromationDTO withLessonSongUrl(String lessonSongUrl) {
        return MemberInfromationDTO.builder()
                .memberId(this.memberId)
                .email(this.email)
                .name(this.name)
                .phoneNum(this.phoneNum)
                .role(this.role)
                .assignedCoach(this.assignedCoach)
                .lessonSongUrl(lessonSongUrl)
                .build();
    }
}
