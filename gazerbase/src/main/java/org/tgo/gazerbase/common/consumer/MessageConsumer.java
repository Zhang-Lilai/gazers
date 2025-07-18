package org.tgo.gazerbase.common.consumer;

import org.tgo.gazerbase.common.reciever.SignalReciever;
import org.tgo.gazerbase.standard.message.TgoStandardMessage;

/**
 * 消息消费者
 */
public interface MessageConsumer {

    void setSignalReciever(SignalReciever signalReciever);

    /**
     * 获取消息
     * @param message 消息
     */
    void recieveMessage(TgoStandardMessage message);
}
