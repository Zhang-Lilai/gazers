package org.tgo.gazerbase.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tgo.gazerbase.common.reciever.SignalReciever;
import org.tgo.gazerbase.common.reciever.SophonDrum.SophonDrum;
import org.tgo.gazerbase.tech.robot.ControllableRobot;
import org.tgo.gazerbase.tech.robot.StandardControllableRobot;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@Component
public class RobotFactory {
    @Autowired
    private RobotInventory robotInventory;

    private final static AtomicInteger serialNumber = new AtomicInteger(0);

    public RobotFactory() {};
    public ControllableRobot produceRobot(Class<? extends ControllableRobot> robotClass){
        ControllableRobot robot = null;
        if(robotClass == StandardControllableRobot.class){
            Integer currentSerialNumber = serialNumber.getAndIncrement();
            SignalReciever signalReciever = new SophonDrum();
            robot = new StandardControllableRobot("夏先生"+currentSerialNumber+"号");
            robot.setSignalReciever(signalReciever);
            robotInventory.addRobot(currentSerialNumber,robot);
            return robot;
        }
        System.out.println("[机器人生产失败]:暂无法生产其他类型的机器人:"+robotClass.getName());
        return robot;
    }
}
