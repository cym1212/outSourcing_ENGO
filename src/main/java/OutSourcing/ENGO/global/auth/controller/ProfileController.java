package OutSourcing.ENGO.global.auth.controller;


import OutSourcing.ENGO.global.auth.dto.*;
import OutSourcing.ENGO.global.auth.service.ProfileService;
import OutSourcing.ENGO.global.dto.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class ProfileController {

    private final ProfileService profileService;


    @GetMapping("/user/details")
    @Operation(summary = "회원 정보 확인", description = "현재 로그인한 회원의 정보를 확인합니다.")
    public ResponseEntity<ResponseWrapper<MemberInfromationDTO>> getCurrentUserDetails() {
        MemberInfromationDTO infromationDTO = profileService.getCurrentUserDetails();
        ResponseWrapper<MemberInfromationDTO> resultData = ResponseWrapper.<MemberInfromationDTO>builder()
                .status(HttpStatus.OK.value())
                .data(infromationDTO)
                .build();
        return ResponseEntity.ok().body(resultData);
    }


}
