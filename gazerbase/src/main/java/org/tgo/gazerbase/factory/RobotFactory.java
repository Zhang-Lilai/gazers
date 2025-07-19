package org.tgo.gazerbase.factory;

import org.tgo.gazerbase.common.receiver.SignalReceiver;
import org.tgo.gazerbase.common.receiver.SophonDrum.SophonDrum;
import org.tgo.gazerbase.tech.robot.ControllableRobot;
import org.tgo.gazerbase.tech.robot.StandardControllableRobot;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 机器人工厂类
 */
public class RobotFactory {
    /**
     * 自增的序列号
     */
    private final static AtomicInteger serialNumber = new AtomicInteger(0);

    /**
     * 无参构造函数
     */
    private RobotFactory() {};

    /**
     * 生产一个机器人，并加到库存中
     * @param robotClass 机器人类型
     * @return 机器人实例
     */
    public static ControllableRobot produceRobot(Class<? extends ControllableRobot> robotClass){
        ControllableRobot robot = null;
        if(robotClass == StandardControllableRobot.class){
            Integer currentSerialNumber = serialNumber.getAndIncrement();
            SignalReceiver signalReceiver = new SophonDrum();
            robot = new StandardControllableRobot("夏先生"+currentSerialNumber+"号");
            robot.setSignalReceiver(signalReceiver);
            RobotInventory.addRobot(currentSerialNumber,robot);
            return robot;
        }
        System.out.println("[机器人生产失败]:暂无法生产其他类型的机器人:"+robotClass.getName());
        return robot;
    }
}
