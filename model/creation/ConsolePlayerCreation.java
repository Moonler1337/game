package model.creation;

import java.util.Scanner;
import model.character.Player;
import model.character.PlayerFactory;
import model.character.PlayerType;

public class ConsolePlayerCreation {

    private final Scanner in = new Scanner(System.in);
    private int playerCounter = 1;

    public Player getPlayer() {
        Player player = null;

        System.out.print("Name for hero " + playerCounter + ": ");
        String name = in.nextLine();

        while (player == null) {

            System.out.print("Choose hero class (");

            PlayerType[] types = PlayerType.values();
            for (int i = 0; i < types.length; i++) {
                System.out.print(types[i].name().toLowerCase());
                if (i < types.length - 1) {
                    System.out.print(" / ");
                }
            }

            System.out.println(")");

            String type = in.nextLine();

            try {
                player = PlayerFactory.create(type, name);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка! Такого класса не существует. Попробуйте ещё раз.");
            }
        }

        playerCounter++;
        return player;
    }
}
