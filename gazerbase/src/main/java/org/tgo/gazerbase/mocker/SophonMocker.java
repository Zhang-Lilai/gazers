package org.tgo.gazerbase.mocker;

import org.tgo.alienware.Sophon;
import org.tgo.gazerbase.common.receiver.SignalReceiver;
import org.tgo.gazerbase.common.receiver.SophonDrum.DrumHead;
import org.tgo.gazerbase.common.receiver.SophonDrum.SophonDrum;
import org.tgo.gazerbase.factory.RobotInventory;
import org.tgo.gazerbase.tech.robot.ControllableRobot;
import org.tgo.gazerbase.tech.robot.StandardControllableRobot;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.regex.*;

public class SophonMocker implements Sophon {

    DrumHead drumHead;

    @Override
    public Object call(Object... o) {
        for(Object o1 : o){
            if(o1 instanceof String){
                System.out.print("\uD83D\uDC7D 对于请求 “"+o1+"”，我打算进行操作:");
                Scanner scanner = new Scanner(System.in);
                // 让模拟人员决定干什么事情
                String []whatIWouldDo = scanner.nextLine().split(" ");
                if(whatIWouldDo.length == 0){continue;}
                switch(whatIWouldDo[0].toUpperCase().charAt(0)){
                    case 'C':this.connect(whatIWouldDo[whatIWouldDo.length-1]);break;
                    case 'A':this.act(whatIWouldDo[whatIWouldDo.length-1]);break;
                }
            }
        }
        Scanner scanner = new Scanner(System.in);
        // 这是给模拟人员看的
        System.out.println("\uD83D\uDC7D 我打算对以上操作做个总结，并给出这样的回复:");
        // 这是模拟人员回复请求者的内容
        return scanner.nextLine();
    }

    public void act(String action){
        if(this.drumHead == null){
            System.out.println("\uD83D\uDC7D 我发现命令下发失败，因为模拟信号源未绑定任何信号接收器。");
            return;
        }
        action = action.toUpperCase();
        if(action.contains("0")){
            action = "0";
        }else if(action.contains("1")){
            action = "1";
        }else if(action.contains("P")){
            action = "P";
        }else if(action.contains("Q")){
            action = "Q";
        }else{
            System.out.println("\uD83D\uDC7D 我会向信号接收器发送如下命令:");
            Scanner scanner = new Scanner(System.in);
            action = scanner.nextLine();
            if(action == null|| action.isEmpty()){
                System.out.println("\uD83D\uDC7D 我不会下任何命令。");
                return;
            }
        }
        switch(action.toUpperCase().charAt(0)){
            case '0': this.drumHead.beat(false); break;
            case '1': this.drumHead.beat(true); break;
            case 'P': this.drumHead.push(); break;
            case 'K': this.drumHead.knock(); break;
        }
    }

    private void connect(String whichRobotShouldIConnect) {
        Integer serialNumber = getLastNumber(whichRobotShouldIConnect);
        if(serialNumber == null){
            System.out.println("\uD83D\uDC7D 我会与下面的机器人连接:");
            Scanner scanner = new Scanner(System.in);
            serialNumber = getLastNumber(scanner.nextLine());
            if(serialNumber == null){
                System.out.println("\uD83D\uDC7D 我不打算与任何机器人连接");
                return;
            }
        }

        ControllableRobot robot = RobotInventory.getRobot(serialNumber);
        if(robot==null){
            System.out.println("\uD83D\uDC7D 我没有找到该机器人，我打算放弃连接。");
            return;
        }
        if(!(robot instanceof StandardControllableRobot standardRobot)){
            System.out.println("\uD83D\uDC7D 我找到了该机器人，但它并非我所兼容的机器人。我无法连接。");
            return;
        }

        System.out.println("\uD83D\uDC7D 目标机器人 ["+ standardRobot.whoAmI() +"] 已找到。我需要对建立连接做出最后确认。我决定:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if(!(input.equals("1")||
             input.charAt(0)=='连'||
             input.charAt(0)=='确'||
             input.charAt(0)=='是'||
             input.charAt(0)=='好'||
             input.charAt(0)=='可'||
             input.charAt(0)=='行'||
             input.equalsIgnoreCase("Y")||
             input.equalsIgnoreCase("YES")||
             input.equalsIgnoreCase("OK")||
             ((!input.toUpperCase().contains("CANCEL"))&&input.toUpperCase().charAt(0)=='C'))){
            System.out.println("\uD83D\uDC7D 放弃链接。");
            return;
        }

        try{
            Field field = StandardControllableRobot.class.getDeclaredField("signalReceiver");
            field.setAccessible(true);
            SignalReceiver signalReceiver = (SignalReceiver) field.get(standardRobot);
            if(!(signalReceiver instanceof SophonDrum sophonDrum)){
                System.out.println("\uD83D\uDC7D 我发现连接失败，因为目标机器人 ["+standardRobot.whoAmI()+"] 的接收器为 ["+signalReceiver.getClass().getName()+"] 类型，我作为一个 Mocker 无法向其发送信号");
                return;
            }
            Field drumHeadField = sophonDrum.getClass().getDeclaredField("drumHead");
            drumHeadField.setAccessible(true);
            this.drumHead = (DrumHead) drumHeadField.get(sophonDrum);
            System.out.println("\uD83D\uDC7D 目标机器人连接成功！");
        } catch (Exception e) {
            System.out.println("\uD83D\uDC7D 目标机器人连接失败，我发现原因是: "+e.getMessage());
        }
    }


    // 纯纯的工具类，获取最后的字符
    public static Integer getLastNumber(String text) {
        Matcher matcher = Pattern.compile("\\d+").matcher(text);
        Integer last = null;
        while (matcher.find()) {
            last = Integer.parseInt(matcher.group());
        }
        return last;
    }


}
