package org.tgo.gazerbase.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tgo.gazerbase.common.ship.FarCaller;
import org.tgo.gazerbase.factory.RobotFactory;
import org.tgo.gazerbase.factory.RobotInventory;
import org.tgo.gazerbase.mocker.SophonMocker;
import org.tgo.gazerbase.tech.robot.ControllableRobot;
import org.tgo.gazerbase.tech.robot.StandardControllableRobot;
import java.util.Map;

/**
 * 机器人测试的 api
 */
@RestController
@RequestMapping("api/robot")
public class RobotTestController {

    /**
     * 模拟信号源
     */
    @Autowired
    private SophonMocker sophonMocker;

    /**
     * 生产机器人的 api
     * @param request 请求体
     * @return 200
     */
    @PostMapping("/produce")
    public ResponseEntity<?> produce(@RequestBody Map<String, String> request) {
        ControllableRobot robot = RobotFactory.produceRobot(StandardControllableRobot.class);
        return ResponseEntity.ok(((StandardControllableRobot)robot).whoAmI());
    }

    /**
     * 将机器人与信号源绑定的 api
     * @param request 请求体，包含 serialNumber 机器人序列号
     * @return 200
     */
    @PostMapping("/assign")
    public ResponseEntity<?> assign(@RequestBody Map<String, String> request) {
        String result = (String)this.sophonMocker.call("绑定目标机器人"+request.get("serialNumber")+"号");
        return ResponseEntity.ok(result);
    }


    /**
     * 将机器人与信号源绑定的 api
     * @param request 请求体，包含 serialNumber 机器人序列号
     * @return 200
     */
    @PostMapping("/visitShip")
    public ResponseEntity<?> visitShip(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(FarCaller.visitThisShip().logo);
    }


}
