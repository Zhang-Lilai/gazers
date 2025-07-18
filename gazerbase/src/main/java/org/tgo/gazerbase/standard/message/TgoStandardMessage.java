package org.tgo.gazerbase.standard.message;

import lombok.Data;
import java.util.Queue;

@Data
public class TgoStandardMessage{
    /**
     * 消息 ID
     */
    private String id;

    /**
     * 消息内容
     */
    private Queue<String> context;

    /**
     * 对象数据
     */
    private Object[] freeData;
}
