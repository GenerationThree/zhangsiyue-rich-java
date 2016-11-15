package rich;

import rich.command.Command;
import rich.command.command.StartGameCommand;
import rich.commander.GameControl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        GameControl gameControl = new GameControl();

        while (!input.equalsIgnoreCase("rich")) {
            System.out.println("输入rich开始游戏:");
            input = bufferedReader.readLine();
        }
        boolean f =true;
        while (gameControl.getStatus() != Status.END_GAME && f) {
            if (gameControl.getStatus() == Status.WAIT_INIT_BALANCE) {

                System.out.println("设置玩家初始资金(1000～50000):");
                input = bufferedReader.readLine();

                Command command = CommandConvertor.convert(input, gameControl);

                if (command != null)
                    gameControl.execute(command);
                else
                    System.out.println("无效命令.");
            }
            if (gameControl.getStatus() == Status.WAIT_INIT_PLAYER) {

                System.out.println("请选择2~4位不重复玩家，输入编号即可。(1.钱夫人; 2.阿土伯; 3.孙小美; 4.金贝贝):");
                input = bufferedReader.readLine();

                Command command = CommandConvertor.convert(input, gameControl);

                if (command != null)
                    gameControl.execute(command);
                else
                    System.out.println("无效命令.");
            }
            if (gameControl.getStatus() == Status.WAIT_START){
                gameControl.execute(new StartGameCommand());
            }
            if(gameControl.getStatus() == Status.IN_PROGRESS){
                gameControl.startTurn();
                Printer.printMap(gameControl.getMap(), gameControl.getCurrentPlayer());
                f = false;
                break;
            }
        }

    }
}
