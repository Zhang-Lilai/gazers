package org.tgo.gazerbase.tech.robot;

import org.tgo.gazerbase.common.consumer.MessageConsumer;

/**
 * 可控机器人
 */
public interface ControllableRobot extends MessageConsumer, Runnable{

    /**
     * 行走
     * @param order 指令
     */
    void move(String order);

    /**
     * 做动作
     * @param order 指令
     */
    void act(String order);

    /**
     * 说话
     * @param order 指令
     */
    void speak(String order);
}
