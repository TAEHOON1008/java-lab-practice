import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack{
	public static void main(String[] args) {
		int seed = Integer.parseInt(args[0]);
        int numOfPlayers = Integer.parseInt(args[1]);
        Deck deck = new Deck();
        deck.shuffle(seed);
        
        
        // 플레이어, 컴퓨터, 하우스 생성
        Player player = new Player();
        Computer[] computers = new Computer[numOfPlayers-1];
        for(int i=0; i<computers.length;i++) {
        	computers[i] = new Computer(i+2);
        }
        House house = new House();
        
        // 카드 분배
        for(int i=0; i<2; i++) {
        	player.receiveCard(deck.dealCard());
        	for(Computer computer : computers) {
        		computer.receiveCard(deck.dealCard());
        	}
        	house.receiveCard(deck.dealCard());
        }
        
        // 카드 분배 결과 출력
        System.out.println(house);
        System.out.println(player);  
        for (Computer computer : computers) {
            System.out.println(computer);  
        }
        
        // 하우스의 카드 합이 21이면 즉시 게임 종료, 아니면 게임 진행.
        if(house.getHandValue() == 21) {
        	System.out.println();
        	System.out.println();
        	System.out.println("--- Game Result ---");
        	System.out.println(house);
            int houseValue = house.getHandValue();
            int playerValue = player.getHandValue();
            String result;
            result = "Lose";
            System.out.printf("[" + result + "] ");
            System.out.println(player);
            for (int i = 0; i < computers.length; i++) {
                result = "Lose";
                System.out.printf("[" + result + "] ");
                System.out.println(computers[i]);
            }
        }else{
        	System.out.println();
        	System.out.println("--- Player1 turn ---");
        	System.out.println(player);
        	player.makeDecision(deck);
        	for(Computer computer : computers) {
        		System.out.println();
        		System.out.println("---"+ computer.getName() +" turn ---");
        		System.out.println(computer); 
        		computer.makeDecision(deck);
        	}
        	System.out.println();
        	System.out.println("--- House turn ---");
        	System.out.println(house);
        	house.makeDecision(deck);
        
        	System.out.println();
        	System.out.println();
        	System.out.println("--- Game Result ---");
        
        	System.out.println(house);
        	
        	int houseValue = house.getHandValue();
        	int playerValue = player.getHandValue();
        	String result;
        	if (player.isBusted()) {
        		result = "Lose";
        	} else if (playerValue > houseValue || house.isBusted()) {
        		result = "Win";
        	} else if (playerValue == houseValue) {
        		result = "Draw";
        	} else {
        		result = "Lose";
        	}
        	System.out.printf("[" + result + "] ");
        	System.out.println(player);
        	for (int i = 0; i < computers.length; i++) {
        		int computerValue = computers[i].getHandValue();
        		if (computers[i].isBusted()) {
        			result = "Lose";
        		} else if (computerValue > houseValue || house.isBusted()) {
        			result = "Win";
        		} else if (computerValue == houseValue) {
        			result = "Draw";
        		} else {
        			result = "Lose";
        		}
        		System.out.printf("[" + result + "] ");
        		System.out.println(computers[i]);
        	}
        
        }
	}
}


class Card {
	int value;
	int suit;
	public Card() {}
	public Card(int theValue, int theSuit) {
		this.value = theValue;
		this.suit = theSuit;
	}
	
	public String toString() {
        String[] suitSymbols = {"c", "h", "d", "s"};
        String[] valueSymbols = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        return valueSymbols[this.value-1] + suitSymbols[this.suit];
    }
	
	public int getGameValue() {
		// J, Q, K 인 경우 10으로 계산
        if (value > 10) {
            return 10;
        } else if (value == 1) {
            return 11;
        } else {
            return value;
        }
    }
}

class Deck{
	private Card[] deck;
	private int cardUsed;
	
	public Deck() {
        deck = new Card[52];
        int cardCt = 0; 
        for (int value = 1; value <= 13; value++) {
            for (int suit = 0; suit <= 3; suit++) {
                deck[cardCt] = new Card(value, suit);
                cardCt++;
            }
        }
    }
	
	public void shuffle(int seed) {
		Random random = new Random(seed);
		for(int i=deck.length-1;i>0;i--) {
			int rand = (int)(random.nextInt(i+1));
			Card temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardUsed = 0;
	}
	public Card dealCard() {
		if(cardUsed == deck.length)
			throw new IllegalStateException("No cards are left in the deck");
		cardUsed++;
		return deck[cardUsed-1];
	}
}
// Set of cards in your hand
class Hand{
	protected ArrayList<Card> hand = new ArrayList<>();
    protected boolean stand = false;
    protected boolean busted = false;
    
    public ArrayList<Card> getHand() {
        return new ArrayList<>(hand); // 현재 패의 복사본 생성
    }
    
    public void receiveCard(Card card) {
    	hand.add(card);
    }
    
    public void hit(Card card) {
        receiveCard(card);
    }

    public void stand() {
        stand = true;
    }

    public boolean isStanding() {
        return stand;
    }
    
    public void busted() {
    	busted = true;
    }
    
    public boolean isBusted() {
    	return busted;
    }

    public int getHandValue() {
        int handValue = 0;
        int aceCount = 0; // 에이스 카드의 수를 세기 위한 변수

        for (Card card : hand) {
            int cardValue = card.getGameValue();
            if (cardValue == 11) { // Ace를 찾으면
                aceCount++;
            }
            handValue += cardValue;
        }

        while (handValue > 21 && aceCount > 0) { // 총합이 21 초과이고 에이스 카드가 있다면
            handValue -= 10; // Ace 하나를 11에서 1로 계산
            aceCount--; // 처리된 에이스 카드 수 감소
        }

        return handValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            sb.append(card.toString()).append(", ");
        }
        if (hand.size() > 0) {
            sb.delete(sb.length() - 2, sb.length()); // 마지막 쉼표 제거
        }
        sb.append(" (").append(getHandValue()).append(")");
        if(getHandValue() > 21)
        	sb.append(" - Bust!");
        return sb.toString();
    }
}

// Player automatically participates
class Computer extends Hand{
	private String name;
	int number;
	public Computer(int number) {
		this.name = "Player" + number;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void makeDecision(Deck deck) {
        while (true) {
            int handValue = getHandValue(); 
            if (handValue < 14) {
                hit(deck.dealCard());
                System.out.println("Hit");
                if (getHandValue() > 21) {
                    busted = true;
                    System.out.println(this);
                    break;
                }
                System.out.println(this);
            } else if (handValue > 17) {
                stand();
                System.out.println("Stand");
                System.out.println(this);
                break;
            } else {
            	Random random = new Random();
                if (random.nextInt(2) == 1) {
                    hit(deck.dealCard());
                    System.out.println("Hit");
                    if (getHandValue() > 21) {
                        busted = true;
                        System.out.println(this);
                        break;
                    }
                    System.out.println(this);
                } else {
                    stand();
                    System.out.println("Stand");
                    System.out.println(this);
                    break;
                }
            }
        }
    }
	public String toString() {
        return name + ": " + super.toString();
    }
}
// Player you control
class Player extends Hand{
	private String name = "Player1";

    public String getName() {
    	return this.name;
    }

    public void makeDecision(Deck deck) {
        while(!isBusted()&&!isStanding()) {
        	Scanner sc = new Scanner(System.in);
        	String input = sc.nextLine();
        	if(input.equals("Hit")) {
        		hit(deck.dealCard());
        		if (getHandValue() > 21) {
                    busted = true;
                    System.out.println(this);
                    break;
                }
        		System.out.println(this);
        	}else if(input.equals("Stand")) {
        		stand();
        		System.out.println(this);
        		break;
        	}
        }
    }

    public String toString() {
        return name + ": " + super.toString();
    }
}

class House extends Hand{
	String name = "House";
	private boolean initialDisplay = true;
	public String getName() {
		return this.name;
	}
	
	public void makeDecision(Deck deck) {
        while (true) {
            if(getHandValue() < 17) {
            	hit(deck.dealCard());
            	System.out.println("Hit");
            	if (getHandValue() > 21) {
            		busted = true;
            		System.out.println(this);
            		break;
            	}
            	System.out.println(this);
            }else {
            	stand();
            	System.out.println("Stand");
            	System.out.println(this);
            	break;
            }
        }
	}
	public String toString() {
		StringBuilder sb = new StringBuilder("House: ");
        if (hand.size() > 0) {
            if (initialDisplay) {
                sb.append("HIDDEN, "); // 첫 번째 카드 숨김
            } else {
                sb.append(hand.get(0).toString()).append(", "); // 첫 번째 카드 표시
            }
            for (int i = 1; i < hand.size(); i++) {
                sb.append(hand.get(i).toString());
                if (i < hand.size() - 1) {
                    sb.append(", ");
                }
            }
            if(initialDisplay) {
            	initialDisplay = false; // 첫 번째 카드를 한 번 숨긴 후 플래그 변경
            	return sb.toString();
            }
            else {
            	sb.append(" (").append(getHandValue()).append(")");
            	if(getHandValue()>21) 
            		sb.append(" - Bust!");
            }
        }
	    return sb.toString();
	}
}