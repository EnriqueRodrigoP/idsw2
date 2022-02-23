import structures.classes.Maze;
import structures.classes.Position;
import structures.classes.players.*;
import structures.classes.surfaces.Surface;
import structures.enums.PlayerType;
import structures.interfaces.Drawable;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Scanner;

public class Program {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";


    public static void main(String[] args) {
        try {
            Player player = Player.getPlayerType(PlayerType.WALKER, new Position(2, 33));
            Maze maze = new Maze(generateMaze(), player);

            game(maze);
        } catch (InputMismatchException exception) {
            System.out.println(ANSI_RED_BACKGROUND + ANSI_BLACK + exception.getMessage() + ANSI_RESET);
        }
    }

    public static void game(Maze maze) {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String nextMovementText = ANSI_GREEN_BACKGROUND + "Next movement: [w] [a] [s] [d] [q] [e] [z] [c] " + ANSI_RESET + "    " + "\n" + "    " + ANSI_CYAN_BACKGROUND;
            final Player player = maze.getPlayer();
            String selection = "";
            maze.printMaze();

            if (player instanceof Walker)
                System.out.print(nextMovementText + "(b:boat) (f:carpet) (h:horse) (x:exit)" + ANSI_RESET + " ");
            else if (player instanceof Boat)
                System.out.println(nextMovementText + "(l:walk) (f:carpet) (h:horse) (x:exit)" + ANSI_RESET + " ");
            else if (player instanceof Horse)
                System.out.println(nextMovementText + "(l:walk) (f:carpet) (b:boat) (x:exit)" + ANSI_RESET + " ");
            else if (player instanceof Carpet)
                System.out.println(nextMovementText + "(l:walk) (h:horse) (b:boat) (x:exit)" + ANSI_RESET + " ");
            else
                System.exit(1);

            selection = scanner.nextLine();

            if (selection.equals("w") || selection.equals("a") || selection.equals("s") || selection.equals("d") || selection.equals("q") || selection.equals("e") || selection.equals("z") || selection.equals("c")) {
                maze.tryAdvance(selection, 1);
            } else if (selection.equals("b") || selection.equals("f") || selection.equals("h") || selection.equals("l")) {
                if (selection.equals("b")) maze.setPlayer(Player.getPlayerType(PlayerType.BOAT, new Position(player.getPosition().getX(), player.getPosition().getY())));
                else if (selection.equals("f")) maze.setPlayer(Player.getPlayerType(PlayerType.CARPET, new Position(player.getPosition().getX(), player.getPosition().getY())));
                else if (selection.equals("l")) maze.setPlayer(Player.getPlayerType(PlayerType.WALKER, new Position(player.getPosition().getX(), player.getPosition().getY())));
                else if (selection.equals("h")) maze.setPlayer(Player.getPlayerType(PlayerType.HORSE, new Position(player.getPosition().getX(), player.getPosition().getY())));
            } else if (selection.equals("x")) {
                System.exit(0);
            } else {
                System.out.println(ANSI_RED_BACKGROUND + ANSI_BLACK + "WRONG INPUT" + ANSI_RESET + "\n");
            }
        }
    }

    public static List<List<Drawable>> generateMaze() {

        //0 -> Ground, 1 -> LowGrass, 2 -> MidGrass, 3 -> HighGrass, 4 -> Sand, 5 -> Mountain, 6 -> Wall, 7 -> Water, 8 -> TroubledWaters

        final int[][] mazeTemplate = {
                {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
                {6,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,6},
                {6,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,6},
                {6,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,6},
                {6,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,6},
                {6,2,2,2,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,6},
                {6,2,2,2,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,4,7,7,7,7,7,7,6,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,1,1,1,1,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,0,0,0,0,6},
                {6,2,2,2,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,4,7,7,7,7,7,7,6,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6},
                {6,2,2,2,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,4,4,7,7,7,7,7,7,6,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6},
                {6,2,2,2,6,6,6,6,6,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,6,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6},
                {6,2,2,2,2,2,2,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,6,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6},
                {6,2,2,2,2,2,2,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,6,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6},
                {6,2,2,2,2,2,2,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,6,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,0,0,0,0,6},
                {6,2,2,2,2,2,2,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,4,4,1,1,1,6,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,6,6,6,6,6,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,6,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,6,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,6,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,6,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,6,0,0,0,0,0,0,0,6,6,6,6,6,6,6,6,6,6,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,7,7,7,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,6,6,6,6,6,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,1,1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,6,1,1,6,6,6,6,6,6,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,6,6,6,6,6,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,6,0,0,0,6,0,0,0,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6},
                {6,0,0,0,0,0,6,0,0,6,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,6,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,6,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,6,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,6,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,6,1,1,1,1,1,1,1,1,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,7,7,7,7,7,4,4,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,6,6,6,6,6,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,6,6,6,6,6,6,6,6,6,6,1,1,1,1,4,4,4,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,1,1,1,1,1,1,4,4,4,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,1,1,1,1,1,1,4,4,4,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,4,4,4,4,4,4,4,4,4,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,1,1,1,1,1,1,4,4,4,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,4,4,4,4,4,4,4,4,4,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,6,6,6,6,6,6,6,6,6,6,1,1,1,1,1,1,4,4,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,4,4,4,4,4,4,4,4,4,1,1,6,6,6,6,6,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,1,1,1,1,1,1,4,4,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,1,1,1,1,1,1,4,4,4,4,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,1,1,1,1,1,1,4,4,4,4,7,7,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,1,1,1,1,1,1,4,4,4,4,7,7,6,0,0,0,0,5,5,5,5,5,5,5,5,5,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,6,6,6,6,6,6,6,6,6,6,1,1,1,6,6,6,6,6,7,7,7,7,6,0,0,0,0,5,5,5,5,5,5,5,5,5,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,1,1,1,6,0,0,4,4,7,7,7,7,6,0,0,0,0,5,5,5,5,5,5,5,5,5,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,0,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,1,1,1,6,0,0,4,4,7,7,7,7,6,0,0,0,0,5,5,5,5,5,5,5,5,5,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,6,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,6,6,6,6,0,0,6,6,7,7,7,7,6,0,0,0,0,5,5,5,5,5,5,5,5,5,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,6,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,6,6,6,6,6,6,6,0,0,0,0,5,5,5,5,5,5,5,5,5,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,4,4,1,1,6,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,1,1,6,0,0,0,0,6,0,0,0,0,6,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,1,1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,5,5,5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6},
                {6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6}
        };

        return IntStream.range(0, mazeTemplate.length).mapToObj(el -> {
            return IntStream.of(mazeTemplate[el]).mapToObj(elem -> {
                return (Drawable) Surface.getSurface(elem);
            }).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }
}




