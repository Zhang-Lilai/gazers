package org.tgo.gazerbase.common.buffer;

import org.tgo.gazerbase.standard.message.TgoStandardMessage;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 消息缓冲区
 */
public class MessageBuffer {

    /**
     * 缓冲区队列
     */
    private final Queue<TgoStandardMessage> messageList = new ArrayDeque<>();

    /**
     * 向缓冲区队列写入消息
     * @param message 消息
     */
    public void add(TgoStandardMessage message){
        this.messageList.add(message);
    }

    /**
     * 获取最久的消息
     * @return 队列里最久的消息
     */
    public TgoStandardMessage getMessage(){
        return this.messageList.poll();
    }

    /**
     * 判断是否非空
     * @return 布尔值，如果缓冲区队列为空，则返回rue，否则返回false
     */
    public boolean isEmpty(){
        return this.messageList.isEmpty();
    }

    /**
     * 清空缓冲区队列
     */
    public void clear(){
        this.messageList.clear();
    }
}
