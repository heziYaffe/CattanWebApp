package entities;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager {

    private String[] playerColors = {"red", "blue", "green", "yellow"};

    private static final int STATE_SELECTING_START_POSITIONS = 0;
    private static final int STATE_GAME_RUNNING = 1;
    private static final int STATE_GAME_OVER = 2;

    private static final int SELECTING_POSITIONS_FIRST_STAGE = 1;
    private static final int SELECTING_POSITIONS_SECOND_STAGE = 2;


    private boolean isGameRunning = false;
    // Define constants for game states

    // Initialize game state
    private static int gameState = STATE_SELECTING_START_POSITIONS;
    private int SelectingStartingPositionsStage = SELECTING_POSITIONS_FIRST_STAGE;
    private Map map;
    private DevelopmentCardPack developmentCardPack;
    private Player[] players;

    int turn = 0;


    public GameManager(int numPlayers) {
        map = new Map();
        developmentCardPack = new DevelopmentCardPack();
        players = new Player[numPlayers];
        setPLayersIdentifiers();
        this.runGame();

        // Initialize players, game board, etc.

    }


    // Method to set the game state
    public void setGameState(int state) {
        gameState = state;
        System.out.println("New Game State is: " + gameState);
    }

    // Method to get the current game state
    public int getGameState() {
        return gameState;
    }

    // Example method to handle game logic based on the current state
    public void handleGameState() {
        switch (gameState) {
            case STATE_SELECTING_START_POSITIONS:
                // Implement logic for selecting starting positions
                break;
            case STATE_GAME_RUNNING:
                // Implement game logic for when the game is running
                break;
            case STATE_GAME_OVER:
                // Implement logic for when the game is over
                break;
            default:
                // Handle unexpected game state
                System.out.println("Unknown game state");
        }
    }

    public boolean handleSelectingStartingPositions(String command, java.util.Map<String, String> values) {

        Player p = players[turn];
        int t_id = Integer.parseInt(values.get("t_id"));

        System.out.println("Command is " + command);
        switch (command) {
            case "buildSettlement":
                int v_id = Integer.parseInt(values.get("v_id"));
                if (p.getSettlements().isEmpty()
                        && SelectingStartingPositionsStage == SELECTING_POSITIONS_FIRST_STAGE) {
                    return p.buildPrimarySettlement(t_id, v_id);
                } else if (p.getSettlements().size() == 1
                        && SelectingStartingPositionsStage == SELECTING_POSITIONS_SECOND_STAGE) {
                    return p.buildPrimarySettlement(t_id, v_id);
                } else {
                    return false;
                }
            case "buildRoad":
                int e_id = Integer.parseInt(values.get("e_id"));
                if (p.getSettlements().size() == 1
                        && SelectingStartingPositionsStage == SELECTING_POSITIONS_FIRST_STAGE) {
                    List<Pair<Integer,Integer>> roadPossibilities = p.getSettlements().get(0).getRoadsPossibilities();
                    System.out.println("Roads Possibilities: " + roadPossibilities);
                    if (roadPossibilities.contains(new Pair<>(t_id, e_id))) {
                        return p.buildRoad(t_id, e_id);
                    }
                    return false;
                } else if (p.getSettlements().size() == 2
                        && SelectingStartingPositionsStage == SELECTING_POSITIONS_SECOND_STAGE) {
                    List<Pair<Integer,Integer>> roadPossibilities = p.getSettlements().get(1).getRoadsPossibilities();
                    System.out.println("Roads Possibilities: " + roadPossibilities);
                    if (roadPossibilities.contains(new Pair<>(t_id, e_id))) {
                        return p.buildRoad(t_id, e_id);
                    }
                    return false;
                } else {
                    System.out.println("Set Your Settlement First And Then Build Road");
                    return false;
                }

            default:
                return false;
        }


    }

    // Example method to start the game
    public void startGame() {
        // Set the game state to start selecting starting positions
        setGameState(STATE_SELECTING_START_POSITIONS);

        // Example: Call a method to handle game state logic
        handleGameState();
    }

    // Example method to end the game
    public void endGame() {
        // Set the game state to game over
        setGameState(STATE_GAME_OVER);

        // Example: Call a method to handle game state logic
        handleGameState();
    }

    public Map getMap() {
        return this.map;
    }

    public int getTurn() {
        return this.turn;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public void stopGame() {
        this.isGameRunning = false;
    }

    public void endTurn() {
        this.updateTurn();
    }

    private void updateTurn() {
        if (gameState == STATE_SELECTING_START_POSITIONS) {
            if (SelectingStartingPositionsStage == SELECTING_POSITIONS_FIRST_STAGE) {
                if (turn == this.players.length - 1) {
                    this.SelectingStartingPositionsStage = SELECTING_POSITIONS_SECOND_STAGE;
                    return;
                }
                turn++;

            } else if (SelectingStartingPositionsStage == SELECTING_POSITIONS_SECOND_STAGE) {
                if (turn == 0) {
                    this.setGameState(STATE_GAME_RUNNING);
                    return;
                }
                turn--;
            }
        } else if (gameState == STATE_GAME_RUNNING) {
            this.turn++;
        } else {
            this.turn++;
        }
    }

    public void runGame() {
        this.isGameRunning = true;
        Thread gameThread = new Thread(this::gameLoop);
        gameThread.start();
    }


    public boolean handleInputFromClients(String command, java.util.Map<String, String> values) {
        if (gameState == STATE_SELECTING_START_POSITIONS) {
            System.out.println("Getting request from front end\n");
            System.out.println("Command is " + command);
            return handleSelectingStartingPositions(command, values);
        } else if (gameState == STATE_GAME_RUNNING) {
            for (Player p : this.players) {
                System.out.println(p);
            }

            int t_id;
            Player p = players[turn % players.length];
            boolean returnValue = false;
            System.out.println("Command is " + command);
            switch (command) {
                case "buildSettlement":
                    t_id = Integer.parseInt(values.get("t_id"));
                    int v_id = Integer.parseInt(values.get("v_id"));
                    returnValue = p.buildSettlement(t_id, v_id);
                    break;
                case "rollDice":
                    int d1 = Integer.parseInt(values.get("dice1"));
                    int d2 = Integer.parseInt(values.get("dice2"));
                    distributeResources(d1 + d2);
                    returnValue = true;
                    break;

                case "drawCard":
                    Card c = developmentCardPack.getTopCard();
                    if (c != null) {
                        if (p.drawCard(c)) {
                            drawCard();
                            returnValue = true;
                        }
                    } else {
                        returnValue = false;
                        //NOTIFY FRONT THAT THERE ARE NO MORE CARDS
                    }
                    break;
                case "buildRoad":
                    t_id = Integer.parseInt(values.get("t_id"));
                    int e_id = Integer.parseInt(values.get("e_id"));
                    returnValue = p.buildRoad(t_id, e_id);
                    break;
                default:
                    break;


            }
            return returnValue;
        }
        return false;
    }


    private final Object lock = new Object(); // Lock object for synchronization

    private void gameLoop() {
        while (isGameRunning) {

            //System.out.println(turn);

            // 1. Process Input
            //handleInputFromClients();

            // 2. Update Game State
            //updateGameState();

            // 3. Send Game State to Clients
            //sendGameStateToClients();

            // 4. Control Frame Rate (Optional)
            //controlFrameRate();

            // Synchronize access to isGameRunning and turn variables
            synchronized (lock) {
                try {
                    // Wait until the turn changes or until interrupted
                    lock.wait();
                } catch (InterruptedException e) {
                    // Handle interruption if needed
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // Method to notify the game loop when the turn changes
    public void notifyTurnChange() {
        synchronized (lock) {
            // Notify the waiting thread that the turn has changed
            lock.notify();
        }
    }


    private void setPLayersIdentifiers() {
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        setPlayersIds();
        setPlayersColors();
    }

    private void setPlayersIds() {

        for (int i = 0; i < players.length; i++) {
            players[i].setId(i);
        }
    }

    private void setPlayersColors() {

        for (int i = 0; i < players.length; i++) {
            players[i].setColor(playerColors[i]);
        }
    }

    // keys: id, <q,r>, tile
    public TwoKeyHashMap<Integer, Pair<Integer, Integer>, Tile> getTiles() {
        return map.getTiles();
    }

    public Card drawCard() {
        return developmentCardPack.drawCard();
    }


    public void distributeResources(int diceSum) {

        for (Player player : players) {
            for (Settlement s : player.getSettlements()) {
                for (Tile tile : s.getTiles()) {
                    if (tile.getNumber() == diceSum) {
                        String correspondingResource = tile.getResource();
                        int quantity = player.getResources().get(correspondingResource);
                        //int distributionQuantity = getResourceDistributionQuantity
                        player.getResources().replace(correspondingResource, quantity + 1);
                    }
                }
            }
        }
        System.out.println("distribute Resources");
    }
    // Add methods for handling turns, events, etc.
}
