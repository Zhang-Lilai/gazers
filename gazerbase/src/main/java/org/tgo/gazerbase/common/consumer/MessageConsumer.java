package org.tgo.gazerbase.common.consumer;

import org.tgo.gazerbase.common.receiver.SignalReceiver;
import org.tgo.gazerbase.standard.message.TgoStandardMessage;

/**
 * 消息消费者
 */
public interface MessageConsumer {

    /**
     * 将信号接收器与自身(信号消费者)绑定
     * @param signalReceiver 需要绑定的信号接收器
     */
    void setSignalReceiver(SignalReceiver signalReceiver);

    /**
     * 获取消息
     * @param message 消息
     */
    void receiveMessage(TgoStandardMessage message);
}
