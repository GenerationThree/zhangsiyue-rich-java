package rich;

import rich.command.Command;
import rich.commander.GameControl;
import rich.commander.Player;

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
        while (gameControl.getStatus() != Status.END_GAME) {
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
                gameControl.startGame();
            }
            if(gameControl.getStatus() == Status.IN_PROGRESS){
                gameControl.startTurn();
                Player currentPlayer = gameControl.getCurrentPlayer();
                Printer.printMap(gameControl.getMap(), currentPlayer);
                Printer.printCurrentPlayerInfo(currentPlayer);

                while(currentPlayer.getStatus() != Status.END_TURN){
                    input = bufferedReader.readLine();

                    Command command = CommandConvertor.convert(input, gameControl);

                    if (command != null)
                        currentPlayer.execute(command);
                    else
                        System.out.println("无效命令.");
                    Printer.printCurrentPlayerInfo(currentPlayer);
                }
            }
        }

    }
}
