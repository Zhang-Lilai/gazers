package org.tgo.gazerbase.mocker;

import org.tgo.alienware.sophon.Sophon;
import org.tgo.gazerbase.common.reciever.SignalReciever;
import org.tgo.gazerbase.common.reciever.SophonDrum.DrumHead;
import org.tgo.gazerbase.common.reciever.SophonDrum.SophonDrum;
import org.tgo.gazerbase.tech.robot.ControllableRobot;
import org.tgo.gazerbase.tech.robot.StandardControllableRobot;

import java.lang.reflect.Field;
import java.util.Scanner;

public class SophonMocker implements Sophon {

    DrumHead drumHead;

    @Override
    public Object call(Object... o) {
        for(Object o1 : o){
            if(o1 instanceof String){
                System.out.println(o1.toString());
            }
            else if(o1 instanceof StandardControllableRobot){
                System.out.println("是否与机器人 "+ ((StandardControllableRobot)o1).whoAmI() +" 建立连接？");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if(input.equals("1")||
                   input.equalsIgnoreCase("Y")||
                   input.equalsIgnoreCase("YES")||
                   input.equalsIgnoreCase("OK")){
                    assign((StandardControllableRobot) o1);
                }
            }
        }
        return null;
    }

    public void act(String action){
        switch(action){
            case "0": this.drumHead.beat(false); break;
            case "1": this.drumHead.beat(true); break;
            case "p": this.drumHead.push(); break;
            case "k": this.drumHead.knock(); break;
        }
    }

    private void assign(StandardControllableRobot robot) {
        if(robot==null){return;}
        try{
            Field field = StandardControllableRobot.class.getDeclaredField("signalReciever");
            field.setAccessible(true);
            SignalReciever signalReciever = (SignalReciever) field.get(robot);
            if(!(signalReciever instanceof SophonDrum sophonDrum)){
                System.out.println("[机器人连接失败]: 目标接收器不兼容");
                return;
            }
            Field drumHeadField = sophonDrum.getClass().getDeclaredField("drumHead");
            drumHeadField.setAccessible(true);
            this.drumHead = (DrumHead) drumHeadField.get(sophonDrum);
            System.out.println("[机器人连接成功]");
        } catch (NoSuchFieldException e) {
            System.out.println("[机器人连接失败]: "+e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("[机器人连接失败]: "+e.getMessage());
        } catch (Exception e) {
            System.out.println("[机器人连接失败]: "+e.getMessage());
        }
    }
}
