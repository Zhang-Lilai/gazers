package org.tgo.gazerbase.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tgo.gazerbase.factory.RobotFactory;
import org.tgo.gazerbase.factory.RobotInventory;
import org.tgo.gazerbase.mocker.SophonMocker;
import java.nio.charset.StandardCharsets;

import org.tgo.gazerbase.tech.robot.ControllableRobot;
import org.tgo.gazerbase.tech.robot.StandardControllableRobot;

import java.util.Map;

/**
 * 机器人测试的 api
 */
@RestController
@RequestMapping("api/robot")
public class RobotTestController {

    @Autowired
    RobotInventory robotInventory;

    @Autowired
    RobotFactory robotFactory;

    @Autowired
    private SophonMocker sophonMocker;


    @PostMapping("/produce")
    public ResponseEntity<?> produce(@RequestBody Map<String, String> request) {
        ControllableRobot robot = robotFactory.produceRobot(StandardControllableRobot.class);
        return ResponseEntity.ok(((StandardControllableRobot)robot).whoAmI());
    }



    @PostMapping("/assign")
    public ResponseEntity<?> assign(@RequestBody Map<String, String> request) {
        ControllableRobot robot = robotInventory.getRobot(request.get("serialNumber")==null?0:Integer.parseInt(request.get("serialNumber")));
        this.sophonMocker.call("初始设置信号接收器",robot);
        return ResponseEntity.ok("200");
    }



    @PostMapping("/act")
    public ResponseEntity<?> act(@RequestBody Map<String, String> request) {
        String input = request.get("input");
        // 1. 将字符串转换为UTF-8字节数组
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        // 2. 创建布尔数组（每个字节对应8个布尔值）
        boolean[] boolArray = new boolean[bytes.length * 8];
        // 3. 将每个字节转换为8个比特位
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            // 处理每个字节的8个比特位（从最高位到最低位）
            for (int j = 7; j >= 0; j--) {
                // 计算当前比特位在布尔数组中的索引
                int index = i * 8 + (7 - j);
                // 使用位运算检查特定位是否为1
                boolArray[index] = ((b >> j) & 1) == 1;
            }
        }
        for(boolean bool : boolArray){
            sophonMocker.act(bool?"1":"0");
        }
        sophonMocker.act("p");
        sophonMocker.act("k");
        return ResponseEntity.ok("200");
    }
}
