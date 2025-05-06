package jamol.socialmedia.controller;

import jamol.socialmedia.dto.RewardDTO;
import jamol.socialmedia.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    // Foydalanuvchiga mukofot berish
    @PostMapping("/award/{userId}")
    public ResponseEntity<RewardDTO> awardReward(@PathVariable Long userId) {
        RewardDTO rewardDTO = rewardService.awardReward(userId);
        return new ResponseEntity<>(rewardDTO, HttpStatus.CREATED);
    }
}
