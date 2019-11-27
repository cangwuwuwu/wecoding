package work.niter.wecoding.electric.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.electric.entity.BuildRoom;
import work.niter.wecoding.electric.entity.EleAccount;
import work.niter.wecoding.electric.service.ElectricService;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/10/6 11:40
 * @Description:
 */
@RestController
@RequestMapping("/ele")
public class ElectricController {
    @Autowired
    private ElectricService electricService;

    @GetMapping
    public ResponseEntity<List<BuildRoom>> getAllBuildAndRoomInfo() {
        return ResponseEntity.ok(electricService.findAllBuildRoom());
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCountEleAccount() {
        return ResponseEntity.ok(electricService.findEleCount());
    }

    @PostMapping("/nowCheck")
    public ResponseEntity<String> getRoomElectricNumber(String buildname, String roomname) {
        return ResponseEntity.ok(electricService.nowCheckMethod(buildname, roomname));
    }

    @PostMapping("/addAccount")
    public ResponseEntity<Void> addBuildRoomAccount(EleAccount account) {
        electricService.insertBuildRoomAccount(account);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Void> removeBuildRoomAccount(
            @PathVariable(value = "id") String id) {
        electricService.deleteBuildRoomAccount(id);
        return ResponseEntity.ok().build();
    }
}
