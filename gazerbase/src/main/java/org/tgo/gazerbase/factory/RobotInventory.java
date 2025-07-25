package org.tgo.gazerbase.factory;

import org.springframework.stereotype.Component;
import org.tgo.gazerbase.tech.robot.ControllableRobot;

import java.util.HashMap;
import java.util.Map;

/**
 * 机器人库存类，单例静态
 */
public class RobotInventory {
    /**
     * 不需要对象，直接私有化构造函数
     */
    private RobotInventory() {}

    /**
     * 全局唯一的库存 Map
     */
    private final static Map<Integer,ControllableRobot> robots = new HashMap<>();

    /**
     * 根据序列号获取机器人
     * @param n 序列号
     * @return 机器人
     */
    public static ControllableRobot getRobot(int n) {return robots.get(n);}

    /**
     * 添加特定序列号的机器人
     * @param serialNumber 序列号
     * @param robot 机器人
     */
    public static void addRobot(Integer serialNumber,ControllableRobot robot) {
        robots.put(serialNumber,robot);
    }
}
