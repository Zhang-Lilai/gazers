package org.tgo.gazerbase.tech.robot;

import lombok.Setter;
import org.tgo.gazerbase.common.receiver.SignalReceiver;
import org.tgo.gazerbase.standard.message.TgoStandardMessage;

/**
 * 标准化的可控机器人
 */
public class StandardControllableRobot implements ControllableRobot {

    /**
     * 当前消息
     */
    private TgoStandardMessage message;

    /**
     * 信号接收器
     */
    private SignalReceiver signalReceiver;

    /**
     * 机器人昵称
     */
    private String name = "夏先生2号";

    /**
     * 无参构造函数
     */
    public StandardControllableRobot(String name) {
        this.name = name;
    }

    /**
     * 安装接收器
     * @param signalReceiver 接收器
     */
    @Override
    public void setSignalReceiver(SignalReceiver signalReceiver) {
        this.signalReceiver = signalReceiver;
        this.signalReceiver.setMessageConsumer(this);
    }


    /**
     * 返回该机器人的昵称
     */
    public String whoAmI(){
        return name;
    }

    /**
     * 从接收器处获取消息
     * @param message 消息
     */
    @Override
    public void receiveMessage(TgoStandardMessage message) {
        this.message = message;
        // TODO:下面方法是单线程场景下的临时模拟方法，以后 run()实现后要删除
        whenMessageReceived();
    }

    /**
     * 行动
     * @param order 指令
     */
    @Override
    public void move(String order) {
        System.out.println("["+this.name+"] **行走**:  "+order);
    }

    /**
     * 做动作
     * @param order 指令
     */
    @Override
    public void act(String order) {
        System.out.println("["+this.name+"] **做动作**:  "+order);
    }

    @Override
    public void speak(String order) {
        System.out.println("["+this.name+"] **发言**:  "+order);
    }

    /**
     * 机器人启动
     */
    @Override
    public void run() {
        // TODO: 异步的逻辑
    }

    /**
     * 命令处理，将消息转换成结构化的命令
     * @return 命令对象
     */
    private Command commandParser(){

        if(this.message==null){return null;}

        String commandType = this.message.getContext().poll();
        Command command = commandType!=null?switch (commandType.toUpperCase()) {
            case "MOVE" -> Command.MOVE;
            case "ACT" -> Command.ACT;
            case "SPEAK" -> Command.SPEAK;
            default -> null;}:null;
        if(command!=null){
            command.order = !this.message.getContext().isEmpty() ?this.message.getContext().poll():"Wait";
        }
        return command;
    }

    /**
     * 机器人的内部命令枚举类
     */
    private enum Command{
        MOVE,
        ACT,
        SPEAK;


        /**
         * 指令的具体信息
         */
        public String order;
    }

    /**
     * 临时模拟的方法
     * 单线程场景中临时模拟的消息处理方案。
     */
    private void whenMessageReceived(){
        Command command = commandParser();
        if(command!=null){
            switch (command.name()){
                case "MOVE": this.move(command.order); break;
                case "ACT": this.act(command.order); break;
                case "SPEAK": this.speak(command.order); break;
            }
        }
    }
}
