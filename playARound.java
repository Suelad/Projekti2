package javaapplication35;

import java.util.*;

class Card {
    private char suit;
    private char rank;
    public Card(String representation) {
        if (representation.length() != 2) {
            throw new IllegalArgumentException("Invalid card representation: " + representation);
        }
        this.suit = representation.charAt(0);
        this.rank = representation.charAt(1);
    }
    public int getValue() {
        switch (rank) {
            case 'A': return 14;
            case 'K': return 13;
            case 'Q': return 12;
            case 'J': return 11;
            case 'T': return 10;
            default: return rank - '0';
        }
    }
 
    @Override
    public String toString() {
        return "" + suit + rank;
    }
}


public class playARound {
    private Deque<Card> faceDownCardsPlayer1;
    private Deque<Card> faceDownCardsPlayer2;
    private int winner = -1;
    public playARound() {
        faceDownCardsPlayer1 = new ArrayDeque<>();
        faceDownCardsPlayer2 = new ArrayDeque<>();
    }
 
    public playARound(Deque<Card> pile1, Deque<Card> pile2) {
        faceDownCardsPlayer1 = pile1;
        faceDownCardsPlayer2 = pile2;
    }
 
    public Deque<Card> getFaceDownCardsPlayer1() {
        return faceDownCardsPlayer1;
    }
 
    public Deque<Card> getFaceDownCardsPlayer2() {
        return faceDownCardsPlayer2;
    }
 
    public void aRound() {
        if (faceDownCardsPlayer1.isEmpty() || faceDownCardsPlayer2.isEmpty()) {
            if (faceDownCardsPlayer1.isEmpty() && faceDownCardsPlayer2.isEmpty()) {
                winner = 0;
            }
            else if (faceDownCardsPlayer1.isEmpty()) {
                winner = 2;
            }
            else if (faceDownCardsPlayer2.isEmpty()) {
                winner = 1;
            }
            return;
        }
        
        List<Card> faceUpCardsPlayer1  = new ArrayList<>();
        List<Card> faceUpCardsPlayer2 = new ArrayList<>();
        Card player1Cards = faceDownCardsPlayer1.removeFirst();
        Card player2Cards = faceDownCardsPlayer2.removeFirst();
        faceUpCardsPlayer1.add(player1Cards);
        faceUpCardsPlayer2.add(player2Cards);
        while (player1Cards.getValue() == player2Cards.getValue()) {
            if (faceDownCardsPlayer1.isEmpty() || faceDownCardsPlayer2.isEmpty()) {
                winner = 0;
                return;
            }
            player1Cards = faceDownCardsPlayer1.removeFirst();
            player2Cards = faceDownCardsPlayer2.removeFirst();
            faceUpCardsPlayer1.add(player1Cards);
            faceUpCardsPlayer2.add(player2Cards);
        }
 
        if (player1Cards.getValue() > player2Cards.getValue()) {
            faceDownCardsPlayer1.addAll(faceUpCardsPlayer1);
            faceDownCardsPlayer1.addAll(faceUpCardsPlayer2);
        }
        else {
            faceDownCardsPlayer2.addAll(faceUpCardsPlayer1);
            faceDownCardsPlayer2.addAll(faceUpCardsPlayer2);
        }
        if (faceDownCardsPlayer1.isEmpty() && faceDownCardsPlayer2.isEmpty()) {
            winner = 0;
        }
        else if (faceDownCardsPlayer1.isEmpty()) {
            winner = 2;
        }
        else if (faceDownCardsPlayer2.isEmpty()) {
            winner = 1;
        }
    }
 
    public int getWinner() {
        return winner;
    }
 
    public void dealingCards(Stack<Card> deck) {
        faceDownCardsPlayer1.clear();
        faceDownCardsPlayer2.clear();
        while (!deck.isEmpty()) {
            faceDownCardsPlayer1.addFirst(deck.pop());
            if (!deck.isEmpty()) {
                faceDownCardsPlayer2.addFirst(deck.pop());
            }
        }
    }
 
    public Stack<Card> dealingCards() {
        Stack<Card> deck = new Stack<>();
        List<String> cardStrings = Arrays.asList("♦A", "♦A", "♥8", "♥8", "♥F", "♥F");
        for (String cardStr : cardStrings) {
            deck.add(new Card(cardStr));
        }
 
        Collections.shuffle(deck);
        Stack<Card> deckClone = new Stack<>();
        Stack<Card> deck1 = new Stack<>();
        while (!deck.isEmpty()) {
            deckClone.push(deck.peek());
            deck1.push(deck.pop());
        }
 
        dealingCards(deck1);
        return deckClone;
    }
 
    @Override
    public String toString() {
        return "Player 1: " + faceDownCardsPlayer1 + "\nPlayer 2: " + faceDownCardsPlayer2;
    }
 
    public static void main(String[] args) {
        Stack<Card> deck = new Stack<>();
        deck.push(new Card("♦A"));
        deck.push(new Card("♦A"));
        deck.push(new Card("♥8"));
        deck.push(new Card("♥8"));
        deck.push(new Card("♥F"));
        deck.push(new Card("♥F"));
        playARound game = new playARound();
        game.dealingCards(deck);
        System.out.println("Gjendja para fillimit te lojes (letrat siper terhiqen te parat):\n" + game);
        
        int round = 0;
        while (round < 1000000 && game.getWinner() < 0) {
            round++;
            game.aRound();
            System.out.println("After the " + round + " round: \n" + game);
        }

        if (game.getWinner() >= 1)
            System.out.println("Player " + game.getWinner() + " won.");
        else
            System.out.println("Its a draw");
    }
}