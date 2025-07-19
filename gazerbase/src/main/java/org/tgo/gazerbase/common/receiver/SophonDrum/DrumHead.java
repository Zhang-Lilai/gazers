package org.tgo.gazerbase.common.receiver.SophonDrum;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 信号接收器的接受面，接收比特流或者完整的字符数据
 */
public final class DrumHead {
    /**
     * 比特流的最大长度
     */
    private static final int MAX_BITARRAY_SIZE = 1024;

    /**
     * 比特流
     */
    private final boolean[] bitArray = new boolean[MAX_BITARRAY_SIZE];

    /**
     * 比特流指针，自增的原子类型整数
     */
    private final AtomicInteger pointer = new AtomicInteger(0);

    /**
     * 字符数据
     */
    private final StringBuilder stringData = new StringBuilder();

    /**
     * 接收器
     */
    private SophonDrum sophonDrum;

    /**
     * 无参构造函数
     */
    public DrumHead() {}

    /**
     * 有参构造函数，定义上层组件的引用
     * @param sophonDrum 上层组件，信号接收器
     */
    public DrumHead(SophonDrum sophonDrum) {
        this();
        this.sophonDrum = sophonDrum;
    }

    /**
     * “推入”操作，往比特流中推入比特
     * 每 8 个比特凑成一个字节，剩下凑不整的残余比特的被转移到比特流最前面
     */
    public void push() {
        synchronized (pointer){
            int byteCount = pointer.get()/8;
            int leftoverCount = pointer.get() % 8;
            int lastLegalPoint = pointer.get()-leftoverCount;
            this.stringData.append(new String(convertBitsToBytes(byteCount), StandardCharsets.UTF_8));
            pointer.set(0);
            while(pointer.get()<leftoverCount) {
                bitArray[pointer.get()] = bitArray[lastLegalPoint+pointer.get()];
                pointer.incrementAndGet();
            }
            this.pointer.set(0);
        }
    }

    /**
     * 指针置 0，删除残余比特
     */
    public void discard(){
        pointer.set(0);
    }

    /**
     * “敲击” 操作
     * 向比特流中写数据
     * @param bit 二进制比特
     */
    public void beat(boolean bit){
        synchronized (pointer) {
            bitArray[pointer.getAndIncrement()] = bit;
            if(pointer.get() == MAX_BITARRAY_SIZE) {
                this.push();
            }
        }
    }

    /**
     * “注入” 操作
     * 向字符数据中直接写内容
     * @param string 待注入的字符串
     */
    public void inject(String string){
        stringData.append(string);
    }
    /**
     * “敲门” 操作
     * 提醒接收器收取并下发数据
     */
    public void knock(){
        this.sophonDrum.wake(true);
    }

    /**
     * “敲门” 操作的重载
     * 提醒接收器收取数据，但是否下发数据要看入参
     * @param send 是否需要让信号接收器向发送数据
     */
    public void knock(boolean send){
        this.sophonDrum.wake(send);
    }



    /**
     * 删除当前字符数据的内容
     */
    public void cancel(){
        this.stringData.setLength(0);
    }

    /**
     * 获取字符数据
     */
    public String getData(){
        String result = stringData.toString();
        this.stringData.setLength(0);
        return result;
    }

    /**
     * 工具函数，将比特流转换成字节流
     * @param byteCount 字节数量
     * @return 字节流
     */
    private byte[] convertBitsToBytes(int byteCount) {
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; i++) {
            byte b = 0;
            for (int j = 0; j < 8; j++) {
                if (this.bitArray[i * 8 + j]) {
                    b |= (byte) (1 << (7 - j));
                }
            }
            bytes[i] = b;
        }
        return bytes;
    }

}
