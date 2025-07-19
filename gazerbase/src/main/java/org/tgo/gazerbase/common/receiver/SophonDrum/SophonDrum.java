package org.tgo.gazerbase.common.receiver.SophonDrum;

import org.tgo.alienware.Sophon;
import org.tgo.gazerbase.common.buffer.MessageBuffer;
import org.tgo.gazerbase.common.consumer.MessageConsumer;
import org.tgo.gazerbase.common.receiver.SignalReceiver;
import org.tgo.gazerbase.standard.message.TgoStandardMessage;
import java.util.ArrayDeque;
import java.util.List;

/**
 * 信号接收器的一种实现
 */
public final class SophonDrum implements SignalReceiver {

    /**
     * 字符数据的分隔符，用以将字符串拆分成多个子串
     */
    private static final String SEPARATOR = "::";

    /**
     * 数据缓冲区
     */
    private final MessageBuffer messageBuffer = new MessageBuffer();

    /**
     * 数据接收面
     */
    private final DrumHead drumHead = new DrumHead(this);

    /**
     * 数据消费者
     */
    private MessageConsumer consumer;

    /**
     * 无参构造函数
     */
    public SophonDrum() {}

    /**
     * 有参构造函数
     * @param consumer 消费者
     */
    public SophonDrum(MessageConsumer consumer){
        this.consumer = consumer;
    }

    public void setMessageConsumer(MessageConsumer consumer){
        this.consumer = consumer;
    }

    /**
     * 向绑定的消息消费者发送缓冲区里的最久消息
     */
    public void send(){
        this.consumer.receiveMessage(this.messageBuffer.getMessage());
    }

    /**
     * 清空消息缓冲区
     */
    public void clear(){
        this.messageBuffer.clear();
    }

    /**
     * 唤醒，从消息面处获取消息
     */
    public void wake(boolean send){
        String []fetchStrings = drumHead.getData().split(SEPARATOR);
        TgoStandardMessage message = new TgoStandardMessage();
        message.setContext(new ArrayDeque<>(List.of(fetchStrings)));
        message.setId(Long.toString(System.currentTimeMillis()));
        this.messageBuffer.add(message);
        if(send){
            send();
        }
    }
}
