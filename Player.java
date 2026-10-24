package model;

public class Player {

    private String playerName;
    private int empathyScore;
    private int angerScore;
    

    public Player(String playerName) {

        this.playerName = playerName;

        empathyScore = 0;

        angerScore = 0;

        
    }

    public void addEmpathy(int value) {

        empathyScore += value;

    }

    public void addAnger(int value) {

        angerScore += value;

    }

    

    public String getPersonality() {

        if (empathyScore >= angerScore )

            return "Kind Person";

        else 

            return "Aggressive Person";
	

       
    }

    public String getPlayerName() {

        return playerName;

    }

}