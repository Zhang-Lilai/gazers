package org.tgo.gazerbase.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tgo.gazerbase.mocker.SophonMocker;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 模拟 模拟信号源 行动的 api
 * 直接模拟 模拟信号源的 内部操作，因此这里面肯定调用不到 call
 * 调用这个接口的人，其自身就相当于 模拟信号源 的思考和执行机制
 * 这个 api 存在的唯一意义，就是能够让模拟人员自由地调用 SophonMocker 对象 内部方法，假装是信号源在搞事情
 */
@RestController
@RequestMapping("api/mocker")
public class SophonMockerActionController {

    /**
     * 模拟信号源
     */
    @Autowired
    private SophonMocker sophonMocker;

    /**
     * 模拟信号源发送指令的 api
     * @param request 请求体，包含一个 request ，需要先转换为二进制流才行
     * @return 200
     */
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
        for(boolean bool : boolArray){sophonMocker.act(bool?"1":"0");}
        sophonMocker.act("p");
        sophonMocker.act("k");
        return ResponseEntity.ok("200");
    }
}
