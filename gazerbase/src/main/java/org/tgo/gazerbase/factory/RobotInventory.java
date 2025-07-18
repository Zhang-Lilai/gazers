package org.tgo.gazerbase.factory;

import org.springframework.stereotype.Component;
import org.tgo.gazerbase.tech.robot.ControllableRobot;

import java.util.HashMap;
import java.util.Map;


@Component
public class RobotInventory {
    private final Map<Integer,ControllableRobot> robots = new HashMap();

    public ControllableRobot getRobot(int n) {return robots.get(Math.max(n,robots.size()-1));}
    public void addRobot(Integer serialNumber,ControllableRobot robot) {
        robots.put(serialNumber,robot);
    }
}
