package org.tgo.gazerbase.common.receiver;

import org.tgo.gazerbase.common.consumer.MessageConsumer;

public interface SignalReceiver {
    void setMessageConsumer(MessageConsumer consumer);

    /**
     * 发送数据
     */
    void send();

    /**
     * 清空缓冲区
     */
    void clear();
}
