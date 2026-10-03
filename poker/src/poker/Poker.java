/*
 * PokerSimulator
 *
 * Implements a simplified Texas Hold’em poker engine with
 * basic AI decision-making and Monte Carlo-style hand evaluation.
 *
 * Focuses on correctness, clarity, and extensibility rather than UI.
 */

package poker;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;
public class Poker {
    //DECK OF CARDS
    static ArrayList<String> cards = new ArrayList<String>();
    static ArrayList<String> deckChanger;
    //Players' Hole Cards and Community Cards
    //Player hole cards
    static String str1 = "";
    static String str2 = "";
    //Computer hole cards
    static String str3 = "";
    static String str4 = "";
    //Community Cards
    static String str5;
    static String str6;
    static String str7;
    static String str8;
    static String str9;
    
    //ALL IN VARIABLES
    static boolean PlayerAllInPreFlop = false;
    static boolean PlayerAllInFlop = false;
    static boolean PlayerAllInTurn = false;
    static boolean PlayerAllInRiver = false;
    static boolean OpponentAllInPreFlop = false;
    static boolean OpponentAllInFlop = false;
    static boolean OpponentAllInTurn = false;
    static boolean OpponentAllInRiver = false;
    
    //VARIABLE FOR RESETTING DECK OF CARDS
    static int deckReset = 0;
    
    //VARIABLES FOR DETERMINING HAND STRENGTHS AND SHOWDOWN RESULTS
    static int PlayerHearts = 0;
    static int PlayerDiamonds = 0;
    static int PlayerSpades = 0;
    static int PlayerClubs = 0;
    static int OpponentHearts = 0;
    static int OpponentDiamonds = 0;
    static int OpponentSpades = 0;
    static int OpponentClubs = 0;
    static boolean PlayerRoyalFlush = false;
    static boolean OpponentRoyalFlush = false;
    static boolean PlayerStraightFlush = false;
    static int PlayerStraightFlushHigh = 0;
    static boolean OpponentStraightFlush = false;
    static int OpponentStraightFlushHigh = 0;
    static boolean PlayerFourOfAKind = false;
    static int PlayerFourOfAKindHigh = 0;
    static boolean OpponentFourOfAKind = false;
    static int OpponentFourOfAKindHigh = 0;
    static boolean PlayerFullHouse = false;
    static int PlayerFullHouseHigh1 = 0;
    static int PlayerFullHouseHigh2 = 0;
    static boolean OpponentFullHouse = false;
    static int OpponentFullHouseHigh1 = 0;
    static int OpponentFullHouseHigh2 = 0;
    static boolean PlayerFlush = false;
    static int PlayerFlushHigh = 0;
    static int PlayerFlushHighHeart;
    static int PlayerFlushHighDiamond;
    static int PlayerFlushHighSpade;
    static int PlayerFlushHighClub;
    static boolean OpponentFlush = false;
    static int OpponentFlushHigh;
    static int OpponentFlushHighHeart;
    static int OpponentFlushHighDiamond;
    static int OpponentFlushHighSpade;
    static int OpponentFlushHighClub;
    static int FlushHigh;
    static boolean PlayerStraight = false;
    static int PlayerStraightHigh = 0;
    static boolean OpponentStraight = false;
    static int OpponentStraightHigh = 0;
    static int StraightHigh;
    static boolean PlayerThreeOfAKind = false;
    static int PlayerThreeOfAKindHigh = 0;
    static boolean OpponentThreeOfAKind = false;
    static int OpponentThreeOfAKindHigh = 0;
    static boolean PlayerTwoPair = false;
    static int PlayerTwoPairHigh = 0;
    static int PlayerTwoPairSecondHigh = 0;
    static boolean OpponentTwoPair = false;
    static int OpponentTwoPairHigh = 0;
    static int OpponentTwoPairSecondHigh = 0;
    static boolean PlayerOnePair = false;
    static int PlayerOnePairHigh = 0;
    static boolean OpponentOnePair = false;
    static int OpponentOnePairHigh = 0;
    static boolean PlayerHighCard = false;
    static int PlayerHighCardValue = 0;
    static boolean OpponentHighCard = false;
    static int OpponentHighCardValue = 0;
    static ArrayList<Integer> PlayerHighCardKickers = new ArrayList<>();
    static ArrayList<Integer> PlayerOnePairKickers = new ArrayList<>();
    static ArrayList<Integer> PlayerTwoPairKickers = new ArrayList<>();
    static ArrayList<Integer> PlayerThreeOfAKindKickers = new ArrayList<>();
    static ArrayList<Integer> PlayerFourOfAKindKickers = new ArrayList<>();
    static ArrayList<Integer> OpponentHighCardKickers = new ArrayList<>();
    static ArrayList<Integer> OpponentOnePairKickers = new ArrayList<>();
    static ArrayList<Integer> OpponentTwoPairKickers = new ArrayList<>();
    static ArrayList<Integer> OpponentThreeOfAKindKickers = new ArrayList<>();
    static ArrayList<Integer> OpponentFourOfAKindKickers = new ArrayList<>();
    static boolean Chop = false;
    static boolean PlayerWin = false;
    static boolean OpponentWin = false;
    static int decision;
    static double PlayerWins = 0;
    static double OpponentWins = 0;
    static double Chops = 0;
    //used to determine how strong the computer's hand is, so that it can make decisions accordingly
    static int OpponentHandStrength = 0;
    
    //VARIABLES FOR VALUES OF ALL THE CARDS (4 hole cards among the player and computer, and 5 community cards)
    //Player Hole Cards
    static int one;
    static int two;
    //Computer Hole Cards
    static int three;
    static int four;
    //Community Cards
    static int five;
    static int six;
    static int seven;
    static int eight;
    static int nine;
    
    //VARIABLES FOR KEEPING TRACK OF PLAYER AND COMPUTER'S STACKS AND BETS
    //Player's Stack
    static int PlayerStack;
    //Computer's Stack
    static int OpponentStack;
    //Amount Player Bets
    static int PlayerBet = 0;
    //Keeps track of the total amount that the player has bet
    static int PlayerBetTotal = 0;
    //Amount Computer can Bet
    static int OpponentBet = 0;
    ////Keeps track of the total amount that the computer has bet
    static int OpponentBetTotal = 0;
    //used to tell the user how much they are able to bet
    static int PlayerBetRange = 0;
    //used to tell the user how much they are able to raise by, should the situation arise
    static int PlayerRaise = 0;
    //the blind that gets automatically bet by the player and the computer each round
    static int blind = 0;
    static int blindAllInScannerFlusher = 0;


    public static void main(String[] args) {
        //Deck of Cards definition starts
        //adds 2-10 for all suits to the deck
        for(int i = 2; i < 38; i++) {
            if(i <= 10) {
                cards.add(String.valueOf(i) + " hearts");
            }
            else if(i <= 19) {
                cards.add(String.valueOf(i+1-10) + " diamonds");
            }
            else if(i <= 28) {
                cards.add(String.valueOf(i+2-20) + " spades");
            }
            else if(i <= 37) {
                cards.add(String.valueOf(i+3-30) + " clubs");
            }
        }
        //manually adds J-A for all suits to the deck
        cards.add("J hearts");
        cards.add("J diamonds");
        cards.add("J spades");
        cards.add("J clubs");
        cards.add("Q hearts");
        cards.add("Q diamonds");
        cards.add("Q spades");
        cards.add("Q clubs");
        cards.add("K hearts");
        cards.add("K diamonds");
        cards.add("K spades");
        cards.add("K clubs");
        cards.add("A hearts");
        cards.add("A diamonds");
        cards.add("A spades");
        cards.add("A clubs");
        //Deck of Cards definition ends
        
        //Backup Deck to reset deck after each round
        deckChanger = new ArrayList<>(cards);
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Choose an option:");
        System.out.println("\n1: Full Poker Game");
        System.out.println("2: Monte Carlo Simulator");
        decision = scanner.nextInt();
        
        //Full Poker Game
        if(decision == 1) {
        //for ai computer's decisions
        Random r = new Random();
        
        //User enters Buy-In amount
        System.out.println("\nEnter Buy In:");
        //Player's Buy In is there stack
        PlayerStack = scanner.nextInt();
        if(PlayerStack <= 0) {
            System.out.println("Please enter a valid Buy In above 0.");
            System.exit(0);
        }
            
        //Computer's stack is the same as user's
        OpponentStack = PlayerStack;      
        System.out.println("\nYour stack: $" + PlayerStack);
        System.out.println("Opponent's stack: $" + OpponentStack);
            
        //blind for each round
        blind = (int)(PlayerStack * 0.02);
        if(blind > 0) System.out.println("\nThe blind for each round is $" + blind + ".");
        
        //variable used to skip the program from saying "Another Round" during the first loop of the while loop.
        boolean isFirstRound = false;
            
        //new rounds will keep starting until either user or computer runs out of money, or the user decides to quit
        while(true) {
            //the following logic is in case either the user or the computer goes all in. In that case, no more bets will happen, and showdown will happen automatically

            //ScannerFlusher is a variable that eventually flushes the scanner, if needed. If ScannerFlusher = 1, then it will trigger a scanner flush.
            int ScannerFlusher = 0;
            
            //Logic for dealing the rest of the cards and showdown after someone goes All In.
            if(PlayerAllInPreFlop || PlayerAllInFlop || PlayerAllInTurn || PlayerAllInRiver || OpponentAllInPreFlop || OpponentAllInFlop || OpponentAllInTurn || OpponentAllInRiver) {
                //if the All In happened Pre-flop:
                if(PlayerAllInPreFlop || OpponentAllInPreFlop) {
                    
                    str5 = deal();
                    str6 = deal();
                    str7 = deal();
                    
                    System.out.println("\n@@@@@@@@@@@@");
                    System.out.println("    FLOP");
                    System.out.println("@@@@@@@@@@@@");

                    System.out.println("\n" + str5 + ", " + str6 + ", " + str7);
                    System.out.println("\nYour Hand: " + str1 + ", " + str2);
                    System.out.println("\nPress p to proceed:");
                    if(blindAllInScannerFlusher == 0) {
                        scanner.nextLine();
                    }
                    else {
                        blindAllInScannerFlusher = 0;
                    }
                    ScannerFlusher++;
                    if(!scanner.nextLine().equals("p")) {
                        System.out.println("\nPlease enter a valid response.");
                        System.exit(0);
                    }
                }
                //if the All In happened after the Flop
                if(PlayerAllInFlop || OpponentAllInFlop || PlayerAllInPreFlop || OpponentAllInPreFlop) {
                    System.out.println("\n@@@@@@@@@@@@");
                    System.out.println("    TURN");
                    System.out.println("@@@@@@@@@@@@");
                    
                    str8 = deal();

                    System.out.println("\n" + str5 + ", " + str6 + ", " + str7 + ", " + str8);
                    System.out.println("\nYour Hand: " + str1 + ", " + str2);
                    System.out.println("\nPress p to proceed:");
                    if(PlayerAllInFlop || OpponentAllInFlop) {
                        scanner.nextLine();
                        ScannerFlusher++;
                    }
                    if(!scanner.nextLine().equals("p")) {
                        System.out.println("\nPlease enter a valid response.");
                        System.exit(0);
                    }
                    
                }
                //if the All In happened after the Turn
                if(PlayerAllInTurn || OpponentAllInTurn || PlayerAllInFlop || OpponentAllInFlop || PlayerAllInPreFlop || OpponentAllInPreFlop) {
                    System.out.println("\n@@@@@@@@@@@@@");
                    System.out.println("    RIVER");
                    System.out.println("@@@@@@@@@@@@@");
                    
                    str9 = deal();

                    System.out.println("\n" + str5 + ", " + str6 + ", " + str7 + ", " + str8 + ", " + str9);
                    System.out.println("\nYour Hand: " + str1 + ", " + str2);
                    System.out.println("\nPress p to proceed:");
                    if(PlayerAllInTurn || OpponentAllInTurn) {
                        scanner.nextLine();
                        ScannerFlusher++;
                    }
                    if(!scanner.nextLine().equals("p")) {
                        System.out.println("\nPlease enter a valid response.");
                        System.exit(0);
                    }
                }
                //After displaying all the community cards, Showdown starts
                Showdown(str1, str2, str3, str4, str5, str6, str7, str8, str9, PlayerBetTotal, OpponentBetTotal);
            }
            
            //Reset all the static variables used for Betting Rounds, All Ins, and Hand-Strength Determiner Logic
            resetHandVariables();
            
            //if a new round is starting (not including the first round), then ask the user if they want to play or not
            if(isFirstRound) {
                //if ScannerFlusher is triggered then flush the scanner
                if(ScannerFlusher == 0) {
                    scanner.nextLine();
                }
                //otherwise don't flush the scanner
                else {
                    ScannerFlusher = 0;
                }
                System.out.println("\nAnother round? (Y/N)");
                String input = scanner.nextLine();
                if(input.equals("N")) {
                    System.out.println("\nYou leave with $" + PlayerStack + ". Thanks for playing!");
                    System.exit(0);
                }
            }
            isFirstRound = true;
            
            // Simulates a full round of Texas Hold’em, including dealing,
            // betting decisions, and winner evaluation.
            System.out.println("\n@@@@@@@@@@@@@@@@@");
            System.out.println("    NEW ROUND");
            System.out.println("@@@@@@@@@@@@@@@@@");
            System.out.println("\nDealing hole cards...");
            //Player's hole cards
            str1 = deal();
            str2 = deal();
            System.out.println("\nYour cards: " + str1 + ", " + str2);
            //Opponent's hole cards
            str3 = deal();
            str4 = deal();
            
            //calculates the computer's hand strength
            opponentHandStrength(str3, str4);
            
            //add blinds to the pot, if there are blinds
            if(blind > 0) {
                int totalBlinds;
                if(PlayerStack - blind <= 0) {
                    PlayerBetTotal = PlayerStack;
                    OpponentBetTotal = PlayerBetTotal;
                    PlayerAllInPreFlop = true;
                    blindAllInScannerFlusher++;
                    totalBlinds = PlayerBetTotal*2;
                    System.out.println("\n$" + totalBlinds + " in blinds has been added to the pot.");
                    System.out.println("\nYou're All In!");
                    continue;
                }
                else if(OpponentStack - blind <= 0) {
                    OpponentBetTotal = OpponentStack;
                    PlayerBetTotal = OpponentBetTotal;
                    OpponentAllInPreFlop = true;
                    blindAllInScannerFlusher++;
                    totalBlinds = OpponentBetTotal*2;
                    System.out.println("\n$" + totalBlinds + " in blinds has been added to the pot.");
                    System.out.println("\nOpponent's All In!");
                    continue;
                }
                else {
                    PlayerBetTotal += blind;
                    OpponentBetTotal += blind;
                    totalBlinds = blind*2;
                    System.out.println("\n$" + totalBlinds + " in blinds has been added to the pot.");
                }
            }
            
            //variable to signal which betting round it is (pre-flop, flop, etc.) so that the bettingRound method can trigger the correct All In variables
            int BettingRoundSignaler = 1;
            
            //Plays through Pre-Flop
            if(bettingRound(scanner, r, BettingRoundSignaler)) continue;
            
            System.out.println("\n@@@@@@@@@@@@");
            System.out.println("    FLOP");
            System.out.println("@@@@@@@@@@@@");
            //deals the flop
            str5 = deal();
            str6 = deal();
            str7 = deal();
            
            System.out.println("\n" + str5 + ", " + str6 + ", " + str7);
            System.out.println("\nYour cards: " + str1 + ", " + str2);
            
            BettingRoundSignaler++;
            //Plays through Flop
            if(bettingRound(scanner, r, BettingRoundSignaler)) continue;
            
            System.out.println("\n@@@@@@@@@@@@");
            System.out.println("    TURN");
            System.out.println("@@@@@@@@@@@@");
            //deals the turn
            str8 = deal();
            
            System.out.println("\n" + str5 + ", " + str6 + ", " + str7 + ", " + str8);
            System.out.println("\nYour cards: " + str1 + ", " + str2);
            
            BettingRoundSignaler++;
            //Plays through Turn
            if(bettingRound(scanner, r, BettingRoundSignaler)) continue;
            
            System.out.println("\n@@@@@@@@@@@@@");
            System.out.println("    RIVER");
            System.out.println("@@@@@@@@@@@@@");
            //deals the river
            str9 = deal();
            
            System.out.println("\n" + str5 + ", " + str6 + ", " + str7 + ", " + str8 + ", " + str9);
            System.out.println("\nYour cards: " + str1 + ", " + str2);
            
            BettingRoundSignaler++;
            //Plays through River
            if(bettingRound(scanner, r, BettingRoundSignaler)) continue;
            
            //calls showdown method, which ends the round
            Showdown(str1, str2, str3, str4, str5, str6, str7, str8, str9, PlayerBetTotal, OpponentBetTotal);
            

        }
        
    }
    //Monte Carlo Simulator
    else if(decision == 2) {
        /*in this commented scenario, I want to test the equity of pocket Aces vs pocket Kings, so I'm manually
        setting the hole cards for both the players while keeping the community cards random. Given 10,000
        trials, the program will output somewhere from ~81-82% equity for the pocket Aces and ~18-19% equity
        for the Kings (which is roughly equal to the actual equity of these hands). The more trials you do,
        the more accurate your percentages will be, but the program will take longer to run (you'll really start
        noticing long wait times once you get to 100k+ trials--100k trials takes ~2 seconds, and 1M trials takes
        ~20 seconds to run).

        To set up a situation, if you want to manually set a card, set it equal to a string with the format
        as shown below for str1-str4. Put write number/letter abbreviation of the card (for 10, use the numerical
        value "10" instead of the letter "T"), and then write the suit of the card all lowercase and plural. If
        you want a card to remain random, set it equal to deal(), as shown for str1-str9 in the for loop.
        
        *NOTE* If you want to manually set a card, you must do so outside the for loop and then remove the card from
        the deck manually via cards.remove(), as seen below for removing str1-str4 from the deck. This way, when you
        go to randomly generate the other cards, they cannot be duplicates of the card you manually chose.
        */
        
        str1 = "A hearts";
        str2 = "A diamonds";
        str3 = "K clubs";
        str4 = "K spades";
        
        cards.remove(str1);
        cards.remove(str2);
        cards.remove(str3);
        cards.remove(str4);
        
        ArrayList<String> simulationDeck = new ArrayList<>(cards);
        
        int trials = 100000;
        
        for(int i = 0; i < trials; i++) {
            resetHandVariables();
            cards = new ArrayList<>(simulationDeck);
            deckReset = 0;
            
            str5 = deal(); 
            str6 = deal();
            str7 = deal();
            str8 = deal();
            str9 = deal();
            
            Showdown(str1, str2, str3, str4, str5, str6, str7, str8, str9, PlayerBetTotal, OpponentBetTotal);
        }
        //displaying data
        System.out.println("\nPlayer Hand: " + str1 + ", " + str2);
        System.out.println("Opponent Hand: " + str3 + ", " + str4);
        System.out.println("Trials: " + trials);
        System.out.println("\nPlayer Wins: " + PlayerWins);
        System.out.println("Opponent Wins: " + OpponentWins);
        System.out.println("Chops: " + Chops);
        double PlayerEquity = ((PlayerWins + (0.5*Chops))*100) / (PlayerWins + OpponentWins + Chops);
        double OpponentEquity = ((OpponentWins + (0.5*Chops))*100) / (PlayerWins + OpponentWins + Chops);
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("\nPlayer Equity: " + df.format(PlayerEquity) + "%");
        System.out.println("Opponent Equity: " + df.format(OpponentEquity) + "%");
    }
    else {
        System.out.println("\nPlease choose a valid option.");
        System.exit(0);
    }
        
  }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// END OF MAIN METHOD /////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    
    //method to deal cards
    static String deal() {
        if(deckReset == 1) {
            cards = new ArrayList<>(deckChanger);
            deckReset = 0;
        }
        Random random1 = new Random();
        int x = random1.nextInt(cards.size());
        //stores the selected card as another string before removing it from the deck
        String str = String.valueOf(cards.get(x));
        //removes card from deck
        cards.remove(x);
        //return the selected card
        return str;
        
        }
    
    //////////START OF HAND-STRENGTH DETERMINER LOGIC//////////
    
    //method inspects the contents of a card's String value and returns its suit, to be used by other methods
    static String SuitChecker(String str) {
        String j = "J";
        String q = "Q";
        String k = "K";
        String a = "A";
        
        if(!str.replace(" hearts", "").equals(str)) {
            String str1change = str.replace("hearts", "").trim();
            try{
            FlushHigh = Integer.parseInt(str1change);
            }
            catch(NumberFormatException e) {
                if(str1change.equals(j)) {
                    FlushHigh = 11;
                }
                else if(str1change.equals(q)) {
                    FlushHigh = 12;
                }
                else if(str1change.equals(k)) {
                    FlushHigh = 13;
                }
                else if(str1change.equals(a)) {
                    FlushHigh = 14;
                }
            }
            return "H";
        }
        else if(!str.replace(" diamonds", "").equals(str)) {
            String str2change = str.replace("diamonds", "").trim();
            try{
            FlushHigh = Integer.parseInt(str2change);
            }
            catch(NumberFormatException e) {
                if(str2change.equals(j)) {
                    FlushHigh = 11;
                }
                else if(str2change.equals(q)) {
                    FlushHigh = 12;
                }
                else if(str2change.equals(k)) {
                    FlushHigh = 13;
                }
                else if(str2change.equals(a)) {
                    FlushHigh = 14;
                }
            }
            return "D";
        }
        else if(!str.replace(" spades", "").equals(str)) {
            String str3change = str.replace("spades", "").trim();
            try{
            FlushHigh = Integer.parseInt(str3change);
            }
            catch(NumberFormatException e) {
                if(str3change.equals(j)) {
                    FlushHigh = 11;
                }
                else if(str3change.equals(q)) {
                    FlushHigh = 12;
                }
                else if(str3change.equals(k)) {
                    FlushHigh = 13;
                }
                else if(str3change.equals(a)) {
                    FlushHigh = 14;
                }
            }
            return "S";
        }
        else {
            String str4change = str.replace("clubs", "").trim();
            try{
            FlushHigh = Integer.parseInt(str4change);
            }
            catch(NumberFormatException e) {
                if(str4change.equals(j)) {
                    FlushHigh = 11;
                }
                else if(str4change.equals(q)) {
                    FlushHigh = 12;
                }
                else if(str4change.equals(k)) {
                    FlushHigh = 13;
                }
                else if(str4change.equals(a)) {
                    FlushHigh = 14;
                }
            }
            return "C";
        }
        
    }
    
    //method scans the user's cards (user's hole cards and community cards) and determines how many cards of each suit there are
    //and what the value of the highest-value card of each suit is
    static void PlayersuitCounter (String str) {
        String h = "H";
        String d = "D";
        String s = "S";
        String c = "C";
        if(str.equals(h)) {
                PlayerHearts++;
                if(FlushHigh > PlayerFlushHighHeart) {
                    PlayerFlushHighHeart = FlushHigh;
                }
            }
            else if(str.equals(d)) {
                PlayerDiamonds++;
                if(FlushHigh > PlayerFlushHighDiamond) {
                    PlayerFlushHighDiamond = FlushHigh;
                }
            }
            else if(str.equals(s)) {
                PlayerSpades++;
                if(FlushHigh > PlayerFlushHighSpade) {
                    PlayerFlushHighSpade = FlushHigh;
                }
            }
            else if(str.equals(c)) {
                PlayerClubs++;
                if(FlushHigh > PlayerFlushHighClub) {
                    PlayerFlushHighClub = FlushHigh;
                }
            }
    }
    
    //does the same as the previous method, but for the computer's cards
    static void OpponentsuitCounter (String str) {
        String h = "H";
        String d = "D";
        String s = "S";
        String c = "C";
            
        if(str.equals(h)) {
                OpponentHearts++;
                if(FlushHigh > OpponentFlushHighHeart) {
                    OpponentFlushHighHeart = FlushHigh;
                }
            }
            else if(str.equals(d)) {
                OpponentDiamonds++;
                if(FlushHigh > OpponentFlushHighDiamond) {
                    OpponentFlushHighDiamond = FlushHigh;
                }
            }
            else if(str.equals(s)) {
                OpponentSpades++;
                if(FlushHigh > OpponentFlushHighSpade) {
                    OpponentFlushHighSpade = FlushHigh;
                }
            }
            else if(str.equals(c)) {
                OpponentClubs++;
                if(FlushHigh > OpponentFlushHighClub) {
                    OpponentFlushHighClub = FlushHigh;
                }
            }
    }
    
    //converts card into its numeric value to be used by other methods
    static int NumConverter(String str) {
        str = str.replace(" hearts", ""); 
        str = str.replace(" diamonds", "");
        str = str.replace(" spades", "");
        str = str.replace(" clubs", "");
        String j = "J";
        String q = "Q";
        String k = "K";
        String a = "A";
        int num = 0;
        
        try {
            num = Integer.parseInt(str);
            return num;
        }
        catch(NumberFormatException e) {
            if(str.equals(j)) {
                return 11;
            }
            else if(str.equals(q)) {
                return 12;
            }
            else if(str.equals(k)) {
                return 13;
            }
            else if(str.equals(a)) {
                return 14;
            }
        }
        return -1;
    }
    
    //checks for straights
    static boolean Straight(int one, int two, int three, int four, int five) {
        int[] nums = {one, two, three, four, five};
        Arrays.sort(nums);
        
        for(int i = 0; i < nums.length - 1; i++) {
            if(nums[i + 1] - nums[i] != 1) {
                if(nums[4] != 14) {
                    return false;
                }
                else {
                    nums[4] = 1;
                    Arrays.sort(nums);
                    for(i = 0; i < nums.length - 1; i++) {
                        if(nums[i + 1] - nums[i] != 1) {
                            return false;
                        }
                    }
                }
            }
        }
        Arrays.sort(nums);
        StraightHigh = nums[4];
        return true;
    }
    
    //logic for Straight method
    static boolean StraightChecker(int one, int two, int three, int four, int five, int six, int seven) {
        if(Straight(one, two, three, four, five)) {
            return true;
        }
        else if(Straight(one, two, three, four, six)) {
            return true;
        }
        else if(Straight(one, two, three, four, seven)) {
            return true;
        }
        else if(Straight(one, two, three, five, six)) {
            return true;
        }
        else if(Straight(one, two, three, five, seven)) {
            return true;
        }
        else if(Straight(one, two, three, six, seven)) {
            return true;
        }
        else if(Straight(one, two, four, five, six)) {
            return true;
        }
        else if(Straight(one, two, four, five, seven)) {
            return true;
        }
        else if(Straight(one, two, four, six, seven)) {
            return true;
        }
        else if(Straight(one, two, five, six, seven)) {
            return true;
        }
        else if(Straight(one, three, four, five, six)) {
            return true;
        }
        else if(Straight(one, three, four, five, seven)) {
            return true;
        }
        else if(Straight(one, three, four, six, seven)) {
            return true;
        }
        else if(Straight(one, three, five, six, seven)) {
            return true;
        }
        else if(Straight(one, four, five, six, seven)) {
            return true;
        }
        else if(Straight(two, three, four, five, six)) {
            return true;
        }
        else if(Straight(two, three, four, six, seven)) {
            return true;
        }
        else if(Straight(two, three, five, six, seven)) {
            return true;
        }
        else if(Straight(two, four, five, six, seven)) {
            return true;
        }
        else if(Straight(three, four, five, six, seven)) {
            return true;
        }
        else if(Straight(two, three, four, five, seven)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    //another suit checker method, used for the player and computer Straight Flush checker logic
    static String SuitChecker2(String str) {
        if(!str.replace(" hearts", "").equals(str)) {
            return "heart";
        }
        else if(!str.replace(" diamonds", "").equals(str)) {
            return "diamond";
        }
        else if(!str.replace(" spades", "").equals(str)) {
            return "spade";
        }
        else if(!str.replace(" clubs", "").equals(str)) {
            return "club";
        }
        else {
            return "";
        }
    }
    
    //checks if the player has a straight flush
    static boolean PlayerStraightFlush(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        if(PlayerStraight && PlayerFlush) {
            int[] nums = {NumConverter(str1), NumConverter(str2), NumConverter(str3), NumConverter(str4), NumConverter(str5), NumConverter(str6), NumConverter(str7)};
            Arrays.sort(nums);
            String[] strings = new String[nums.length];
            for(int i = 0; i < strings.length; i++) {
                strings[i] = String.valueOf(nums[i]);
            }
            for(int i = 0; i < nums.length; i++) {
                if(nums[i] == NumConverter(str1)) {
                    strings[i] = SuitChecker2(str1);
                }
                else if(nums[i] == NumConverter(str2)) {
                    strings[i] = SuitChecker2(str2);
                }
                else if(nums[i] == NumConverter(str3)) {
                    strings[i] = SuitChecker2(str3);
                }
                else if(nums[i] == NumConverter(str4)) {
                    strings[i] = SuitChecker2(str4);
                }
                else if(nums[i] == NumConverter(str5)) {
                    strings[i] = SuitChecker2(str5);
                }
                else if(nums[i] == NumConverter(str6)) {
                    strings[i] = SuitChecker2(str6);
                }
                else if(nums[i] == NumConverter(str7)) {
                    strings[i] = SuitChecker2(str7);
                }
            }
            
            String String7 = strings[6];
            String String6 = strings[5];
            String String5 = strings[4];
            String String4 = strings[3];
            String String3 = strings[2];
            String String2 = strings[1];
            String String1 = strings[0];
            if(nums[6] == PlayerStraightHigh) {
                if(PlayerHearts >= 5) {
                    if(String7.equals("heart") && String6.equals("heart") && String5.equals("heart") && String4.equals("heart") && String3.equals("heart")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerDiamonds >= 5) {
                    if(String7.equals("diamond") && String6.equals("diamond") && String5.equals("diamond") && String4.equals("diamond") && String3.equals("diamond")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerSpades >= 5) {
                    if(String7.equals("spade") && String6.equals("spade") && String5.equals("spade") && String4.equals("spade") && String3.equals("spade")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerClubs >= 5) {
                    if(String7.equals("club") && String6.equals("club") && String5.equals("club") && String4.equals("club") && String3.equals("club")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else if(nums[5] == PlayerStraightHigh) {
                if(PlayerHearts >= 5) {
                    if(String6.equals("heart") && String5.equals("heart") && String4.equals("heart") && String3.equals("heart") && String2.equals("heart")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerDiamonds >= 5) {
                    if(String6.equals("diamond") && String5.equals("diamond") && String4.equals("diamond") && String3.equals("diamond") && String2.equals("diamond")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerSpades >= 5) {
                    if(String6.equals("spade") && String5.equals("spade") && String4.equals("spade") && String3.equals("spade") && String2.equals("spade")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerClubs >= 5) {
                    if(String6.equals("club") && String5.equals("club") && String4.equals("club") && String3.equals("club") && String2.equals("club")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else if(nums[4] == PlayerStraightHigh) {
                if(PlayerHearts >= 5) {
                    if(String5.equals("heart") && String4.equals("heart") && String3.equals("heart") && String2.equals("heart") && String1.equals("heart")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerDiamonds >= 5) {
                    if(String5.equals("diamond") && String4.equals("diamond") && String3.equals("diamond") && String2.equals("diamond") && String1.equals("diamond")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerSpades >= 5) {
                    if(String5.equals("spade") && String4.equals("spade") && String3.equals("spade") && String2.equals("spade") && String1.equals("spade")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(PlayerClubs >= 5) {
                    if(String5.equals("club") && String4.equals("club") && String3.equals("club") && String2.equals("club") && String1.equals("club")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
            return false;
    }
    
    //checks if the computer has a straight flush
    static boolean OpponentStraightFlush(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        if(OpponentStraight && OpponentFlush) {
            int[] nums = {NumConverter(str1), NumConverter(str2), NumConverter(str3), NumConverter(str4), NumConverter(str5), NumConverter(str6), NumConverter(str7)};
            Arrays.sort(nums);
            String[] strings = new String[nums.length];
            for(int i = 0; i < strings.length; i++) {
                strings[i] = String.valueOf(nums[i]);
            }
            for(int i = 0; i < nums.length; i++) {
                if(nums[i] == NumConverter(str1)) {
                    strings[i] = SuitChecker2(str1);
                }
                else if(nums[i] == NumConverter(str2)) {
                    strings[i] = SuitChecker2(str2);
                }
                else if(nums[i] == NumConverter(str3)) {
                    strings[i] = SuitChecker2(str3);
                }
                else if(nums[i] == NumConverter(str4)) {
                    strings[i] = SuitChecker2(str4);
                }
                else if(nums[i] == NumConverter(str5)) {
                    strings[i] = SuitChecker2(str5);
                }
                else if(nums[i] == NumConverter(str6)) {
                    strings[i] = SuitChecker2(str6);
                }
                else if(nums[i] == NumConverter(str7)) {
                    strings[i] = SuitChecker2(str7);
                }
            }
            
            String String7 = strings[6];
            String String6 = strings[5];
            String String5 = strings[4];
            String String4 = strings[3];
            String String3 = strings[2];
            String String2 = strings[1];
            String String1 = strings[0];
            if(nums[6] == OpponentStraightHigh) {
                if(OpponentHearts >= 5) {
                    if(String7.equals("heart") && String6.equals("heart") && String5.equals("heart") && String4.equals("heart") && String3.equals("heart")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentDiamonds >= 5) {
                    if(String7.equals("diamond") && String6.equals("diamond") && String5.equals("diamond") && String4.equals("diamond") && String3.equals("diamond")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentSpades >= 5) {
                    if(String7.equals("spade") && String6.equals("spade") && String5.equals("spade") && String4.equals("spade") && String3.equals("spade")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentClubs >= 5) {
                    if(String7.equals("club") && String6.equals("club") && String5.equals("club") && String4.equals("club") && String3.equals("club")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else if(nums[5] == OpponentStraightHigh) {
                if(OpponentHearts >= 5) {
                    if(String6.equals("heart") && String5.equals("heart") && String4.equals("heart") && String3.equals("heart") && String2.equals("heart")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentDiamonds >= 5) {
                    if(String6.equals("diamond") && String5.equals("diamond") && String4.equals("diamond") && String3.equals("diamond") && String2.equals("diamond")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentSpades >= 5) {
                    if(String6.equals("spade") && String5.equals("spade") && String4.equals("spade") && String3.equals("spade") && String2.equals("spade")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentClubs >= 5) {
                    if(String6.equals("club") && String5.equals("club") && String4.equals("club") && String3.equals("club") && String2.equals("club")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            else if(nums[4] == OpponentStraightHigh) {
                if(OpponentHearts >= 5) {
                    if(String5.equals("heart") && String4.equals("heart") && String3.equals("heart") && String2.equals("heart") && String1.equals("heart")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentDiamonds >= 5) {
                    if(String5.equals("diamond") && String4.equals("diamond") && String3.equals("diamond") && String2.equals("diamond") && String1.equals("diamond")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentSpades >= 5) {
                    if(String5.equals("spade") && String4.equals("spade") && String3.equals("spade") && String2.equals("spade") && String1.equals("spade")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                if(OpponentClubs >= 5) {
                    if(String5.equals("club") && String4.equals("club") && String3.equals("club") && String2.equals("club") && String1.equals("club")) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
            return false;
    }
    
    //checks if the player has a four of a kind
    static boolean PlayerFourOfAKind(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        for(int i = 6; i >= 3; i--) {
            if(nums[i] - nums[i - 1] == 0 && nums[i - 1] - nums[i - 2] == 0 && nums[i - 2] - nums[i - 3] == 0) {
                PlayerFourOfAKindHigh = nums[i];
                for(int j = 0; j < nums.length; j++) {
                    if(nums[j] != PlayerFourOfAKindHigh) {
                        PlayerFourOfAKindKickers.add(nums[j]);
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    //checks if the computer has a four of a kind
    static boolean OpponentFourOfAKind(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        for(int i = 6; i >= 3; i--) {
            if(nums[i] - nums[i - 1] == 0 && nums[i - 1] - nums[i - 2] == 0 && nums[i - 2] - nums[i - 3] == 0) {
                OpponentFourOfAKindHigh = nums[i];
                for(int j = 0; j < nums.length; j++) {
                    if(nums[j] != OpponentFourOfAKindHigh) {
                        OpponentFourOfAKindKickers.add(nums[j]);
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    //checks if the player has a three of a kind
    static boolean PlayerThreeOfAKind(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        int counter1 = 0;
        int value = 0;
        int value2 = 0;
        int counter2 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                if(i == 6) {
                    counter1++;
                    value = nums[i];
                }
                else if(nums[i] == value) {
                    counter1++;
                }
                else if(value == 0) {
                    value = nums[i];
                    counter1++;
                }
                else if(value2 == 0) {
                    value2 = nums[i];
                    counter2++;
                }
                else if(nums[i] == value2) {
                    counter2++;
                }
            }
        }
        if(counter1 == 2 || counter2 == 2) {
            if(value > value2) {
                PlayerThreeOfAKindHigh = value;
                for(int j = 0; j < nums.length; j++) {
                    if(nums[j] != PlayerThreeOfAKindHigh) {
                        PlayerThreeOfAKindKickers.add(nums[j]);
                    }
                }
            }
            else {
                PlayerThreeOfAKindHigh = value2;
                for(int j = 0; j < nums.length; j++) {
                    if(nums[j] != PlayerThreeOfAKindHigh) {
                        PlayerThreeOfAKindKickers.add(nums[j]);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    //chekcs if the computer has a three of a kind
    static boolean OpponentThreeOfAKind(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        int counter1 = 0;
        int value = 0;
        int value2 = 0;
        int counter2 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                if(i == 6) {
                    counter1++;
                    value = nums[i];
                }
                else if(nums[i] == value) {
                    counter1++;
                }
                else if(value == 0) {
                    value = nums[i];
                    counter1++;
                }
                else if(value2 == 0) {
                    value2 = nums[i];
                    counter2++;
                }
                else if(nums[i] == value2) {
                    counter2++;
                }
            }
        }
        if(counter1 == 2 || counter2 == 2) {
            if(value > value2) {
                OpponentThreeOfAKindHigh = value;
                for(int j = 0; j < nums.length; j++) {
                    if(nums[j] != OpponentThreeOfAKindHigh) {
                        OpponentThreeOfAKindKickers.add(nums[j]);
                    }
                }
            }
            else {
                OpponentThreeOfAKindHigh = value2;
                for(int j = 0; j < nums.length; j++) {
                    if(nums[j] != OpponentThreeOfAKindHigh) {
                        OpponentThreeOfAKindKickers.add(nums[j]);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    //checks if the player has a two pair
    static boolean PlayerTwoPair(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        int counter1 = 0;
        int value = 0;
        int value2 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                counter1++;
                if(nums[i] > value) {
                    if(value == 0) {
                        value = nums[i];
                    }
                    else {
                        value2 = value;
                        value = nums[i];
                    }
                }
                else {
                    value2 = nums[i];
                }
                if(counter1 == 2) break;
            }
        }
        if(counter1 == 2) {
            PlayerTwoPairHigh = value;
            PlayerTwoPairSecondHigh = value2;
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] != PlayerTwoPairHigh && nums[j] != PlayerTwoPairSecondHigh) {
                    PlayerTwoPairKickers.add(nums[j]);
                }
            }
            return true;
        }
        return false;
    }
    
    //checks if the computer has a two pair
    static boolean OpponentTwoPair(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        int counter1 = 0;
        int value = 0;
        int value2 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                counter1++;
                if(nums[i] > value) {
                    if(value == 0) {
                        value = nums[i];
                    }
                    else {
                        value2 = value;
                        value = nums[i];
                    }
                }
                else {
                    value2 = nums[i];
                }
                if(counter1 == 2) break;
            }
        }
        if(counter1 == 2) {
            OpponentTwoPairHigh = value;
            OpponentTwoPairSecondHigh = value2;
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] != OpponentTwoPairHigh && nums[j] != OpponentTwoPairSecondHigh) {
                    OpponentTwoPairKickers.add(nums[j]);
                }
            }
            return true;
        }
        return false;
    }
    
    //chekcs if the player has a one pair
    static boolean PlayerOnePair(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        int counter1 = 0;
        int value = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                counter1++;
                if(nums[i] > value && nums[i] != PlayerThreeOfAKindHigh) {
                    value = nums[i];
                }
            }
        }
        if(counter1 >= 1) {
            PlayerOnePairHigh = value;
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] != PlayerOnePairHigh) {
                    PlayerOnePairKickers.add(nums[j]);
                }
            }
            return true;
        }
        return false;
    }
    
    //checks if the computer has a one pair
    static boolean OpponentOnePair(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        int counter1 = 0;
        int value = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                counter1++;
                if(nums[i] > value && nums[i] != OpponentThreeOfAKindHigh) {
                    value = nums[i];
                }
            }
        }
        if(counter1 >= 1) {
            OpponentOnePairHigh = value;
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] != OpponentOnePairHigh) {
                    OpponentOnePairKickers.add(nums[j]);
                }
            }
            return true;
        }
        return false;
    }
    
    //checks if the player has a full house
    static boolean PlayerFullHouse(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        if(PlayerOnePairHigh > 0 && PlayerThreeOfAKindHigh > 0) {
            PlayerFullHouseHigh1 = PlayerThreeOfAKindHigh;
            PlayerFullHouseHigh2 = PlayerOnePairHigh;
            return true;
        }
        return false;
    }
    
    //checks if the computer has a full house
    static boolean OpponentFullHouse(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        if(OpponentOnePairHigh > 0 && OpponentThreeOfAKindHigh > 0) {
            OpponentFullHouseHigh1 = OpponentThreeOfAKindHigh;
            OpponentFullHouseHigh2 = OpponentOnePairHigh;
            return true;
        }
        return false;
    }
    
    //checks the player's high card
    static void PlayerHighCard(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        PlayerHighCardValue = nums[6];
        for(int j = 0; j <= 5; j++) {
            PlayerHighCardKickers.add(nums[j]);
        }
    }
    
    //checks the computer's high card
    static void OpponentHighCard(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        int one1 = NumConverter(str1);
        int two2 = NumConverter(str2);
        int three3 = NumConverter(str3);
        int four4 = NumConverter(str4);
        int five5 = NumConverter(str5);
        int six6 = NumConverter(str6);
        int seven7 = NumConverter(str7);
        int[] nums = {one1, two2, three3, four4, five5, six6, seven7};
        Arrays.sort(nums);
        OpponentHighCardValue = nums[6];
        for(int j = 0; j <= 5; j++) {
            OpponentHighCardKickers.add(nums[j]);
        }
    }
    
    //presents the showdown: who won (or chop), what hand they won with, and new total balances
    static void Showdown(String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int PlayerBetTotal, int OpponentBetTotal) {
        if(decision != 2) {
            System.out.println("\n@@@@@@@@@@@@@@@@");
            System.out.println("    SHOWDOWN");
            System.out.println("@@@@@@@@@@@@@@@@");
            System.out.println("\nThe Board: " + str5 + ", " + str6 + ", " + str7 + ", " + str8 + ", " + str9);
            System.out.println("\nYour Hand: " + str1 + ", " + str2);
            System.out.println("Opponent's Hand: " + str3 + ", " + str4);
        }


        String str1Suit = SuitChecker(str1);
        PlayersuitCounter(str1Suit);
        one = NumConverter(str1);

        String str2Suit = SuitChecker(str2);
        PlayersuitCounter(str2Suit);
        two = NumConverter(str2);

        String str3Suit = SuitChecker(str3);
        OpponentsuitCounter(str3Suit);
        three = NumConverter(str3);

        String str4Suit = SuitChecker(str4);
        OpponentsuitCounter(str4Suit);
        four = NumConverter(str4);

        String str5Suit = SuitChecker(str5);
        OpponentsuitCounter(str5Suit);
        PlayersuitCounter(str5Suit);
        five = NumConverter(str5);

        String str6Suit = SuitChecker(str6);
        OpponentsuitCounter(str6Suit);
        PlayersuitCounter(str6Suit);
        six = NumConverter(str6);

        String str7Suit = SuitChecker(str7);
        OpponentsuitCounter(str7Suit);
        PlayersuitCounter(str7Suit);
        seven = NumConverter(str7);

        String str8Suit = SuitChecker(str8);
        OpponentsuitCounter(str8Suit);
        PlayersuitCounter(str8Suit);
        eight = NumConverter(str8);

        String str9Suit = SuitChecker(str9);
        OpponentsuitCounter(str9Suit);
        PlayersuitCounter(str9Suit);
        nine = NumConverter(str9);

        /* Calls upon all the hand checker methods and determines whether or not the player and computer
        have these hands. If the player or computer is determined to qualify for multiple hands (e.g.
        both one pair and two pair), then the program will decipher which hand is stronger and only return
        the strongest hand. At the end, if the player or computer has a stronger hand, then they will
        respectively be deemed the winner. Otherwise, if they have the same hand, then program will evaluate
        kickers/high cards to determine the winner. If the player and computer have the same kickers/high cards
        as well, then they will chop the pot. */
        
        if(PlayerTwoPair(str1, str2, str5, str6, str7, str8, str9)) {
            PlayerTwoPair = true;
            PlayerOnePair = false;
        }
        if(OpponentTwoPair(str3, str4, str5, str6, str7, str8, str9)) {
            OpponentTwoPair = true;
            OpponentOnePair = false;
        }

        if(PlayerThreeOfAKind(str1, str2, str5, str6, str7, str8, str9)) {
            PlayerThreeOfAKind = true;
            PlayerTwoPair = false;
            PlayerOnePair = false;
        }
        if(OpponentThreeOfAKind(str3, str4, str5, str6, str7, str8, str9)) {
            OpponentThreeOfAKind = true;
            OpponentTwoPair = false;
            OpponentOnePair = false;
        }

        if(PlayerOnePair(str1, str2, str5, str6, str7, str8, str9)) {
            if(!PlayerTwoPair && !PlayerThreeOfAKind) {
                PlayerOnePair = true;
            }
        }
        if(OpponentOnePair(str3, str4, str5, str6, str7, str8, str9)) {
            if(!OpponentTwoPair && !OpponentThreeOfAKind) {
                OpponentOnePair = true;
            }
        }

        if(StraightChecker(one, two, five, six, seven, eight, nine)) {
            PlayerStraight = true;
            PlayerThreeOfAKind = false;
            PlayerTwoPair = false;
            PlayerOnePair = false;
            if(StraightHigh > PlayerStraightHigh) {
                PlayerStraightHigh = StraightHigh;
            }
        }
        if(StraightChecker(three, four, five, six, seven, eight, nine)) {
            OpponentStraight = true;
            OpponentThreeOfAKind = false;
            OpponentTwoPair = false;
            OpponentOnePair = false;
            if(StraightHigh > OpponentStraightHigh) {
                OpponentStraightHigh = StraightHigh;
            }
        }

        if(PlayerHearts >= 5 || PlayerDiamonds >= 5 || PlayerSpades >= 5 || PlayerClubs >= 5) {
            PlayerFlush = true;
            PlayerStraight = false;
            PlayerThreeOfAKind = false;
            PlayerTwoPair = false;
            PlayerOnePair = false;
            if(PlayerHearts >= 5) {
                PlayerFlushHigh = PlayerFlushHighHeart;
            }
            else if(PlayerDiamonds >= 5) {
                PlayerFlushHigh = PlayerFlushHighDiamond;
            }
            else if(PlayerSpades >= 5) {
                PlayerFlushHigh = PlayerFlushHighSpade;
            }
            else if(PlayerClubs >= 5) {
                PlayerFlushHigh = PlayerFlushHighClub;
            }
        }
        if(OpponentHearts >= 5 || OpponentDiamonds >= 5 || OpponentSpades >= 5 || OpponentClubs >= 5) {
            OpponentFlush = true;
            OpponentStraight = false;
            OpponentThreeOfAKind = false;
            OpponentTwoPair = false;
            OpponentOnePair = false;
            if(OpponentHearts >= 5) {
                OpponentFlushHigh = OpponentFlushHighHeart;
            }
            else if(OpponentDiamonds >= 5) {
                OpponentFlushHigh = OpponentFlushHighDiamond;
            }
            else if(OpponentSpades >= 5) {
                OpponentFlushHigh = OpponentFlushHighSpade;
            }
            else if(OpponentClubs >= 5) {
                OpponentFlushHigh = OpponentFlushHighClub;
            }
        }

        if(PlayerFullHouse(str1, str2, str5, str6, str7, str8, str9)) {
            PlayerFullHouse = true;
            PlayerFlush = false;
            PlayerStraight = false;
            PlayerThreeOfAKind = false;
            PlayerTwoPair = false;
            PlayerOnePair = false;
        }
        if(OpponentFullHouse(str3, str4, str5, str6, str7, str8, str9)) {
            OpponentFullHouse = true;
            OpponentFlush = false;
            OpponentStraight = false;
            OpponentThreeOfAKind = false;
            OpponentTwoPair = false;
            OpponentOnePair = false;
        }

        if(PlayerFourOfAKind(str1, str2, str5, str6, str7, str8, str9)) {
            PlayerFourOfAKind = true;
            PlayerFullHouse = false;
            PlayerFlush = false;
            PlayerStraight = false;
            PlayerThreeOfAKind = false;
            PlayerTwoPair = false;
            PlayerOnePair = false;
        }
        if(OpponentFourOfAKind(str3, str4, str5, str6, str7, str8, str9)) {
            OpponentFourOfAKind = true;
            OpponentFullHouse = false;
            OpponentFlush = false;
            OpponentStraight = false;
            OpponentThreeOfAKind = false;
            OpponentTwoPair = false;
            OpponentOnePair = false;
        }

        if(PlayerStraightFlush(str1, str2, str5, str6, str7, str8, str9)) {
            PlayerStraightFlush = true;
            PlayerFourOfAKind = false;
            PlayerFullHouse = false;
            PlayerFlush = false;
            PlayerStraight = false;
            PlayerThreeOfAKind = false;
            PlayerTwoPair = false;
            PlayerOnePair = false;
            PlayerStraightFlushHigh = PlayerStraightHigh;
            if(PlayerStraightFlushHigh == 14) {
                PlayerRoyalFlush = true;
                PlayerStraightFlush = false;
            }
        }
        if(OpponentStraightFlush(str3, str4, str5, str6, str7, str8, str9)) {
            OpponentStraightFlush = true;
            OpponentFourOfAKind = false;
            OpponentFullHouse = false;
            OpponentFlush = false;
            OpponentStraight = false;
            OpponentThreeOfAKind = false;
            OpponentTwoPair = false;
            OpponentOnePair = false;
            OpponentStraightFlushHigh = OpponentStraightHigh;
            if(OpponentStraightFlushHigh == 14) {
                OpponentRoyalFlush = true;
                OpponentStraightFlush = false;
            }
        }

        if(!PlayerOnePair && !PlayerTwoPair && !PlayerThreeOfAKind && !PlayerStraight && !PlayerFlush && !PlayerFullHouse && !PlayerFourOfAKind && !PlayerStraightFlush && !PlayerRoyalFlush) {
            PlayerHighCard = true;
            PlayerHighCard(str1, str2, str5, str6, str7, str8, str9);
        }
        if(!OpponentOnePair && !OpponentTwoPair && !OpponentThreeOfAKind && !OpponentStraight && !OpponentFlush && !OpponentFullHouse && !OpponentFourOfAKind && !OpponentStraightFlush && !OpponentRoyalFlush) {
            OpponentHighCard = true;
            OpponentHighCard(str3, str4, str5, str6, str7, str8, str9);
        }


        int PlayerValue = 0;
        int OpponentValue = 0;

        if(PlayerHighCard) {
            PlayerValue = 1;
        }
        if(OpponentHighCard) {
            OpponentValue = 1;
        }
        if(PlayerOnePair) {
            PlayerValue = 2;
        }
        if(OpponentOnePair) {
            OpponentValue = 2;
        }
        if(PlayerTwoPair) {
            PlayerValue = 3;
        }
        if(OpponentTwoPair) {
            OpponentValue = 3;
        }
        if(PlayerThreeOfAKind) {
            PlayerValue = 4;
        }
        if(OpponentThreeOfAKind) {
            OpponentValue = 4;
        }
        if(PlayerStraight) {
            PlayerValue = 5;
        }
        if(OpponentStraight) {
            OpponentValue = 5;
        }
        if(PlayerFlush) {
            PlayerValue = 6;
        }if(OpponentFlush) {
            OpponentValue = 6;
        }
        if(PlayerFullHouse) {
            PlayerValue = 7;
        }
        if(OpponentFullHouse) {
            OpponentValue = 7;
        }
        if(PlayerFourOfAKind) {
            PlayerValue = 8;
        }
        if(OpponentFourOfAKind) {
            OpponentValue = 8;
        }
        if(PlayerStraightFlush) {
            PlayerValue = 9;
        }
        if(OpponentStraightFlush) {
            OpponentValue = 9;
        }
        if(PlayerRoyalFlush) {
            PlayerValue = 10;
        }
        if(OpponentRoyalFlush) {
            OpponentValue = 10;
        }

        if(PlayerValue > OpponentValue) {
            PlayerWin = true;
        }
        else if(PlayerValue < OpponentValue) {
            OpponentWin = true;
        }
        else if(PlayerValue == OpponentValue) {
            if(PlayerValue == 1) {
                if(PlayerHighCardValue > OpponentHighCardValue) {
                    PlayerWin = true;
                }
                else if(PlayerHighCardValue < OpponentHighCardValue){
                    OpponentWin = true;
                }
                else {
                    Collections.sort(PlayerHighCardKickers);
                    Collections.sort(OpponentHighCardKickers);
                    for(int i = 1; i <= 4; i++) {
                        int a = PlayerHighCardKickers.get(PlayerHighCardKickers.size() - i);
                        int b = OpponentHighCardKickers.get(OpponentHighCardKickers.size() - i);
                        if(a > b) {
                            PlayerWin = true;
                            break;
                        }
                        if(b > a) {
                            OpponentWin = true;
                            break;
                        }
                        if(i == 4) {
                            Chop = true;
                        }
                    }
                }
            }
            else if(PlayerValue == 2) {
                if(PlayerOnePairHigh > OpponentOnePairHigh) {
                    PlayerWin = true;
                }
                else if(PlayerOnePairHigh < OpponentOnePairHigh){
                    OpponentWin = true;
                }
                else {
                    Collections.sort(PlayerOnePairKickers);
                    Collections.sort(OpponentOnePairKickers);
                    for(int i = 1; i <= 3; i++) {
                        int a = PlayerOnePairKickers.get(PlayerOnePairKickers.size() - i);
                        int b = OpponentOnePairKickers.get(OpponentOnePairKickers.size() - i);
                        if(a > b) {
                            PlayerWin = true;
                            break;
                        }
                        if(b > a) {
                            OpponentWin = true;
                            break;
                        }
                        if(i == 3) {
                            Chop = true;
                        }
                    }
                }
            }
            else if(PlayerValue == 3) {
                if(PlayerTwoPairHigh > OpponentTwoPairHigh) {
                    PlayerWin = true;
                }
                else if(PlayerTwoPairHigh < OpponentTwoPairHigh){
                    OpponentWin = true;
                }
                else {
                    if(PlayerTwoPairSecondHigh > OpponentTwoPairSecondHigh) {
                        PlayerWin = true;
                    }
                    else if(PlayerTwoPairSecondHigh < OpponentTwoPairSecondHigh) {
                        OpponentWin = true;
                    }
                    else {
                        Collections.sort(PlayerTwoPairKickers);
                        Collections.sort(OpponentTwoPairKickers);
                        for(int i = 1; i <= 1; i++) {
                            int a = PlayerTwoPairKickers.get(PlayerTwoPairKickers.size() - i);
                            int b = OpponentTwoPairKickers.get(OpponentTwoPairKickers.size() - i);
                            if(a > b) {
                                PlayerWin = true;
                                break;
                            }
                            if(b > a) {
                                OpponentWin = true;
                                break;
                            }
                            if(i == 1) {
                                Chop = true;
                            }
                        }
                    }
                }
            }
            else if(PlayerValue == 4) {
                if(PlayerThreeOfAKindHigh > OpponentThreeOfAKindHigh) {
                    PlayerWin = true;
                }
                else if(PlayerThreeOfAKindHigh < OpponentThreeOfAKindHigh){
                    OpponentWin = true;
                }
                else {
                    Collections.sort(PlayerThreeOfAKindKickers);
                    Collections.sort(OpponentThreeOfAKindKickers);
                    for(int i = 1; i <= 2; i++) {
                        int a = PlayerThreeOfAKindKickers.get(PlayerThreeOfAKindKickers.size() - i);
                        int b = OpponentThreeOfAKindKickers.get(OpponentThreeOfAKindKickers.size() - i);
                        if(a > b) {
                            PlayerWin = true;
                            break;
                        }
                        if(b > a) {
                            OpponentWin = true;
                            break;
                        }
                        if(i == 2) {
                            Chop = true;
                        }
                    }
                }
            }
            else if(PlayerValue == 5) {
                if(PlayerStraightHigh > OpponentStraightHigh) {
                    PlayerWin = true;
                }
                else if(PlayerStraightHigh < OpponentStraightHigh){
                    OpponentWin = true;
                }
                else {
                    Chop = true;
                }
            }
            else if(PlayerValue == 6) {
                if(PlayerFlushHigh > OpponentFlushHigh) {
                    PlayerWin = true;
                }
                else if(PlayerFlushHigh < OpponentFlushHigh){
                    OpponentWin = true;
                }
                else {
                    Chop = true;
                }
            }
            else if(PlayerValue == 7) {
                if(PlayerFullHouseHigh1 > OpponentFullHouseHigh1) {
                    PlayerWin = true;
                }
                else if(PlayerFullHouseHigh1 < OpponentFullHouseHigh1){
                    OpponentWin = true;
                }
                else if(PlayerFullHouseHigh2 > OpponentFullHouseHigh2) {
                    PlayerWin = true;
                }
                else if(PlayerFullHouseHigh2 < OpponentFullHouseHigh2) {
                    OpponentWin = true;
                }
                else {
                    Chop = true;
                }
            }
            else if(PlayerValue == 8) {
                if(PlayerFourOfAKindHigh > OpponentFourOfAKindHigh) {
                    PlayerWin = true;
                }
                else if(PlayerFourOfAKindHigh < OpponentFourOfAKindHigh){
                    OpponentWin = true;
                }
                else {
                    Collections.sort(PlayerFourOfAKindKickers);
                    Collections.sort(OpponentFourOfAKindKickers);
                    for(int i = 1; i <= 1; i++) {
                        int a = PlayerFourOfAKindKickers.get(PlayerFourOfAKindKickers.size() - i);
                        int b = OpponentFourOfAKindKickers.get(OpponentFourOfAKindKickers.size() - i);
                        if(a > b) {
                            PlayerWin = true;
                            break;
                        }
                        if(b > a) {
                            OpponentWin = true;
                            break;
                        }
                        if(i == 1) {
                            Chop = true;
                        }
                    }
                }
            }
            else if(PlayerValue == 9) {
                if(PlayerStraightFlushHigh > OpponentStraightFlushHigh) {
                    PlayerWin = true;
                }
                else if(PlayerStraightFlushHigh < OpponentStraightFlushHigh){
                    OpponentWin = true;
                }
                else {
                    Chop = true;
                }
            }
        }

        String[] win = {"High Card", "One Pair", "Two Pair", "Three Of A Kind", "Straight", "Flush", "Full House", "Four Of A Kind", "Straight Flush", "Royal Flush"};

        //If the player has won:
        if(PlayerWin) {
            //if we are in Monte Carlo Simulator Mode:
            if(decision == 2) {
                PlayerWins++;
                deckReset++;
            }
            //otherwise add and subtract total bets from the player and computer's stacks and reset the deck
            else {
                PlayerStack += PlayerBetTotal;
                OpponentStack -= OpponentBetTotal;
                System.out.println("\nYou win with a " + win[PlayerValue - 1] + "!");
                System.out.println("You won $" + PlayerBetTotal + "!");
                System.out.println("Your stack is now $" + PlayerStack + ".");
                System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                deckReset++;
            }
        }
        //If the computer has won:
        else if(OpponentWin) {
            if(decision == 2) {
                OpponentWins++;
                deckReset++;
            }
            else {
                PlayerStack -= PlayerBetTotal;
                OpponentStack += OpponentBetTotal;
                System.out.println("\nYou lose to your opponent's " + win[OpponentValue - 1] + ".");
                System.out.println("You lost $" + PlayerBetTotal + ".");
                System.out.println("Your stack is now $" + PlayerStack + ".");
                System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                deckReset++;
            }
        }
        //if the player and computer chop:
        else if(Chop) {
            if(decision == 2) {
                Chops++;
                deckReset++;
            }
            else {
                System.out.println("\nYou chop the pot with a " + win[PlayerValue - 1] + ".");
                System.out.println("Your stack is still $" + PlayerStack + ".");
                System.out.println("Opponent's stack is still $" + OpponentStack + ".");
                deckReset++;
            }
        }
        //If we are in Full Poker Game mode, then if either the player or computer is out of money, end the program
        if(decision == 1) {
            if(PlayerStack == 0) {
                System.out.println("\nYou're out of money. Game Over.");
                System.exit(0);
            }
            if(OpponentStack == 0) {
                System.out.println("\nOpponent is out of money. You win!");
                System.exit(0);
            }
        }
        //otherwise, if both the player and computer have some money left, then start another round
    }
    //Method that plays through each betting round (pre-flop, flop, turn, and river)
    /*this returns boolean ONLY because in certain scenarios when this code is run, I want
    the while loop in the main method to continue. I cannot do this simply from this method
    itself, so I am returning a boolean to do this. Whenever I return true, that's a flag
    for the while loop in the main method to continue. Otherwise, if I return false, nothing
    happens and everything continues as normal.
    */
    static boolean bettingRound(Scanner scanner, Random r, int BettingRoundSignaler) {
        System.out.println("\nChoose an option:");
            System.out.println("-------------------");
            System.out.println("1: Fold");
            System.out.println("2: Check");
            System.out.println("3: Bet");
            int option = scanner.nextInt();
            //if user chooses 1, they fold
            if(option == 1) {
                PlayerStack -= PlayerBetTotal;
                OpponentStack += OpponentBetTotal;
                System.out.println("\nYou fold. You lose.");
                System.out.println("You lost $" + PlayerBetTotal + ".");
                System.out.println("Your stack is now $" + PlayerStack + ".");
                System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                deckReset++;
                return true;
            }
            //if user chooses 2, they check
            else if(option == 2) {
                System.out.println("\nYou check.");
            }
            //if user chooses 3, they bet
            else if(option == 3) {
                PlayerBetRange = PlayerStack - PlayerBetTotal;
                OpponentBet = OpponentStack - OpponentBetTotal;
                //determine the max amount user can bet
                if(PlayerBetRange <= OpponentBet) {
                    System.out.println("How much do you want to bet? (You can bet up to $" + PlayerBetRange + ")");
                }
                else {
                    System.out.println("How much do you want to bet? (You can bet up to $" + OpponentBet + ")");
                }
                //store player's bet in PlayerBet
                PlayerBet = scanner.nextInt();
                //if the user places a bet outside the given range, terminate the program
                if(PlayerBet <= 0) terminate();
                if(PlayerBetRange <= OpponentBet && PlayerBet > PlayerBetRange) terminate();
                if(PlayerBetRange > OpponentBet && PlayerBet > OpponentBet) terminate();
                PlayerBetTotal += PlayerBet;
                //if player's bet is all of their stack or their opponent's stack, trigger the all in call
                if(PlayerBetTotal == PlayerStack || PlayerBet + OpponentBetTotal == OpponentStack) {
                    AllInFlagger(BettingRoundSignaler, true, true);
                    if(PlayerStack <= OpponentStack) System.out.println("\nYou're All In!");
                    else if(PlayerStack > OpponentStack) System.out.println("\nYou put your opponent All In!");
                    //generate computer's response based on its hand strength to fold or call
                    int RandomNum = 0;
                    if(OpponentHandStrength >= 22) {
                        RandomNum = 2;
                    }
                    else if(OpponentHandStrength >= 18) {
                        int x = r.nextInt(10) + 1;
                        if(x < 3) RandomNum = 1;
                        else RandomNum = 2;
                    }
                    else if(OpponentHandStrength >= 14) {
                        int x = r.nextInt(10) + 1;
                        if(x < 8) RandomNum = 1;
                        else RandomNum = 2;
                    }
                    else if(OpponentHandStrength < 14) {
                        RandomNum = 1;
                    }
                        //if computer folds:
                        if(RandomNum == 1) {
                            PlayerStack += PlayerBetTotal - PlayerBet;
                            OpponentStack -= OpponentBetTotal;
                            System.out.println("\nOpponent folds. You win!");
                            System.out.println("You win $" + PlayerBetTotal + "!");
                            System.out.println("Your stack is now $" + PlayerStack + ".");
                            System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                            AllInFlagger(BettingRoundSignaler, true, false);
                            return true;
                        }
                        //if computer calls:
                        else {
                            System.out.println("\nOpponent calls.");
                            OpponentBetTotal += PlayerBet;
                            return true;
                        }
                }
            }
            else terminate();
            
            //if player checked:
            if(option == 2) {
                //computer makes a choice based on its hand strength to check or bet
                int RandomNum = 0;
                if(OpponentHandStrength >= 26) {
                    int x = r.nextInt(10) + 1;
                    if(x < 3) RandomNum = 1;
                    else RandomNum = 2;
                }
                else if(OpponentHandStrength >= 22) {
                    int x = r.nextInt(10) + 1;
                    if(x < 4) RandomNum = 1;
                    else RandomNum = 2;
                }
                else if(OpponentHandStrength >= 18) {
                    int x = r.nextInt(10) + 1;
                    if(x < 6) RandomNum = 1;
                    else RandomNum = 2;
                }
                else if(OpponentHandStrength >= 14) {
                    int x = r.nextInt(10) + 1;
                    if(x < 6) RandomNum = 1;
                    else RandomNum = 2;
                }
                else if(OpponentHandStrength < 14) {
                    int x = r.nextInt(10) + 1;
                    if(x < 8) RandomNum = 1;
                    else RandomNum = 2;
                }
                //if computer checks, tell the user
                if(RandomNum == 1) {
                    System.out.println("\nOpponent checks back.");
                }
                //otherwise, if the computer bets:
                else if(RandomNum == 2) {
                    int RandomNum2;
                    //if computer has more money in play, generate a random bet between 1 and the amount of money the player has in play
                    if(OpponentStack - OpponentBetTotal > PlayerStack - PlayerBetTotal) {
                        OpponentBet = PlayerStack - PlayerBetTotal;
                        RandomNum2 = r.nextInt(OpponentBet) + 1;
                    }
                    //otherwise do the opposite: generate a random bet between 1 and the amount of money the computer has in play
                    else {
                        OpponentBet = OpponentStack - OpponentBetTotal;
                        RandomNum2 = r.nextInt(OpponentBet) + 1;
                    }
                    //if computer bets the max amount possible, trigger All In flags
                    if(RandomNum2 == OpponentBet) {
                        AllInFlagger(BettingRoundSignaler, false, true);
                        System.out.println("\nOpponent bets $" + RandomNum2 + ".");
                        if(OpponentStack > PlayerStack) {
                            System.out.println("\nOpponent puts you All In!");
                        }
                        else {
                            System.out.println("Opponent's All In!");
                        }
                        //player now has to respond to computer's action by either folding or calling
                        System.out.println("\nChoose an option:");
                        System.out.println("-------------------");
                        System.out.println("1: Fold");
                        System.out.println("2: Call");
                        int input = scanner.nextInt();
                        //if player folds:
                        if(input == 1) {
                            PlayerStack -= PlayerBetTotal;
                            OpponentStack += OpponentBetTotal;
                            System.out.println("\nYou fold. You lose.");
                            System.out.println("You lost $" + PlayerBetTotal + ".");
                            System.out.println("Your stack is now $" + PlayerStack + ".");
                            System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                            AllInFlagger(BettingRoundSignaler, false, false);
                            return true;
                        }
                        //otherwise if player calls:
                        else if(input == 2) {
                            OpponentBetTotal += RandomNum2;
                            PlayerBetTotal += RandomNum2;
                            return true;
                        }
                        else {
                            terminate();
                        }
                    }
                    //otherwise if there's no All Ins
                    if(OpponentHandStrength >= 18) {
                        int x = r.nextInt(10) + 1;
                        if(x < 4) RandomNum = 1;
                        else RandomNum = 2;
                    }
                    else if(OpponentHandStrength < 18) {
                        int x = r.nextInt(10) + 1;
                        if(x < 8) RandomNum = 1;
                        else RandomNum = 2;
                    }
                    if(RandomNum == 1) {
                        RandomNum2 = (int)(RandomNum2 * 0.25);
                    }
                    else if(RandomNum == 2) {
                        RandomNum2 = (int)(RandomNum2 * 0.5);
                    }
                    if(RandomNum2 == 0) RandomNum2++;
                    System.out.println("\nOpponent bets $" + RandomNum2 + ".");
                    OpponentBetTotal += RandomNum2;
                    //prompt player to respond to computer's bet by either folding, calling, or raising
                    System.out.println("\nChoose an option:");
                    System.out.println("-------------------");
                    System.out.println("1: Fold");
                    System.out.println("2: Call");
                    System.out.println("3: Raise");
                    int input = scanner.nextInt();
                    //if player folds:
                    if(input == 1) {
                        OpponentBetTotal -= RandomNum2;
                        PlayerStack -= PlayerBetTotal;
                        OpponentStack += OpponentBetTotal;
                        System.out.println("\nYou fold. You lose.");
                        System.out.println("You lost $" + PlayerBetTotal + ".");
                        System.out.println("Your stack is now $" + PlayerStack + ".");
                        System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                        deckReset++;
                        return true;
                    }
                    //if player calls:
                    else if(input == 2) {
                        PlayerBetTotal += RandomNum2;
                    }
                    //if player raises:
                    else if(input == 3) {
                            PlayerBetTotal += RandomNum2;
                            //calculates how much the player is able to raise
                            PlayerRaise = PlayerStack - PlayerBetTotal;
                            if(PlayerRaise <= OpponentStack - OpponentBetTotal) {
                                System.out.println("How much do you want to raise? (You can raise up to $" + PlayerRaise + ")");
                            }
                            else {
                                PlayerRaise = OpponentStack - OpponentBetTotal;
                                System.out.println("How much do you want to raise? (You can raise up to $" + PlayerRaise + ")");
                            }
                            PlayerBet = scanner.nextInt();
                            if(PlayerBet <= 0 || PlayerBet > PlayerRaise) terminate();
                            //if player raises to the max, then trigger All In flags
                            if(PlayerBet == PlayerRaise) {
                                AllInFlagger(BettingRoundSignaler, true, true);
                                if(PlayerRaise == PlayerStack - PlayerBetTotal) {
                                    System.out.println("\nYou're All In!");
                                }
                                else {
                                    System.out.println("\nYou raise your opponent All In!");
                                }
                                //generate computer's response to the All In raise: either fold or call 
                                if(OpponentHandStrength >= 22) {
                                    RandomNum = 2;
                                }
                                else if(OpponentHandStrength >= 18) {
                                    int x = r.nextInt(10) + 1;
                                    if(x < 3) RandomNum = 1;
                                    else RandomNum = 2;
                                }
                                else if(OpponentHandStrength >= 14) {
                                    int x = r.nextInt(10) + 1;
                                    if(x < 8) RandomNum = 1;
                                    else RandomNum = 2;
                                }
                                else if(OpponentHandStrength < 14) {
                                    RandomNum = 1;
                                }
                                    //computer folds:
                                    if(RandomNum == 1) {
                                        PlayerStack += PlayerBetTotal;
                                        OpponentStack -= OpponentBetTotal;
                                        System.out.println("\nOpponent folds. You win!");
                                        System.out.println("You win $" + PlayerBetTotal + "!");
                                        System.out.println("Your stack is now $" + PlayerStack + ".");
                                        System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                                        AllInFlagger(BettingRoundSignaler, true, false);
                                        return true;
                                    }
                                    //computer calls:
                                    else {
                                        System.out.println("\nOpponent calls.");
                                        PlayerBetTotal += PlayerBet;
                                        OpponentBetTotal += PlayerBet;
                                        return true;
                                    }
                            }
                            //other wise if the player's raise doesn't cause any All Ins:
                            PlayerBetTotal += PlayerBet;
                            //generate computer's response: either fold or call
                            if(OpponentHandStrength >= 22) {
                                RandomNum = 2;
                            }
                            else if(OpponentHandStrength >= 18) {
                                int x = r.nextInt(10) + 1;
                                if(x < 4) RandomNum = 1;
                                else RandomNum = 2;
                            }
                            else if(OpponentHandStrength >= 14) {
                                int x = r.nextInt(10) + 1;
                                if(x < 7) RandomNum = 1;
                                else RandomNum = 2;
                            }
                            else if(OpponentHandStrength < 14) {
                                int x = r.nextInt(10) + 1;
                                if(x < 9) RandomNum = 1;
                                else RandomNum = 2;
                            }
                            //if computer folds:
                            if(RandomNum == 1) {
                                PlayerBetTotal -= PlayerBet;
                                PlayerStack += PlayerBetTotal;
                                OpponentStack -= OpponentBetTotal;
                                System.out.println("\nOpponent folds. You win!");
                                System.out.println("You win $" + PlayerBetTotal + "!");
                                System.out.println("Your stack is now $" + PlayerStack + ".");
                                System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                                deckReset++;
                                return true;
                            }
                            //if computer calls:
                            else {
                                OpponentBetTotal += PlayerBet;
                                System.out.println("\nOpponent Calls");
                            }
                    }
                    else {
                        terminate();
                    }
                }
            }
            //if we initially bet and didn't go to an All In situation:
            else if(option == 3) {
                //generate computer's response: fold, call, or raise
                int RandomNum = 0;
                if(OpponentHandStrength >= 22) {
                    int x = r.nextInt(10) + 1;
                    if(x < 6) RandomNum = 2;
                    else RandomNum = 3;
                }
                else if(OpponentHandStrength >= 18) {
                    int x = r.nextInt(10) + 1;
                    if(x < 2) RandomNum = 1;
                    else if(x < 6) RandomNum = 2;
                    else RandomNum = 3;
                }
                else if(OpponentHandStrength >= 14) {
                    int x = r.nextInt(10) + 1;
                    if(x < 4) RandomNum = 1;
                    else if(x < 9) RandomNum = 2;
                    else RandomNum = 3;
                }
                else if(OpponentHandStrength < 14) {
                    int x = r.nextInt(10) + 1;
                    if(x < 9) RandomNum = 1;
                    else RandomNum = 2;
                }
                //if computer folds:
                if(RandomNum == 1) {
                    PlayerBetTotal -= PlayerBet;
                    PlayerStack += PlayerBetTotal;
                    OpponentStack -= OpponentBetTotal;
                    System.out.println("\nOpponent folds. You win!");
                    System.out.println("You win $" + PlayerBetTotal + "!");
                    System.out.println("Your stack is now $" + PlayerStack + ".");
                    System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                    deckReset++;
                    return true;
                }
                //if computer calls:
                else if (RandomNum == 2) {
                    System.out.println("\nOpponent calls.");
                    OpponentBetTotal += PlayerBet;
                }
                //if computer raises:
                else {
                    OpponentBetTotal += PlayerBet;
                    int RandomNum2;
                    //if computer has more money free money, then max amount computer can raise is player's total free money
                    if(OpponentStack > PlayerStack) {
                        RandomNum2 = r.nextInt(PlayerStack - PlayerBetTotal) + 1;
                    }
                    //vise versa if opposite
                    else {
                        RandomNum2 = r.nextInt(OpponentStack - OpponentBetTotal) + 1;
                    }
                    //if computer bets the max, then trigger All In flags
                    if(RandomNum2 == OpponentStack - OpponentBetTotal || RandomNum2 == PlayerStack - PlayerBetTotal) {
                        AllInFlagger(BettingRoundSignaler, false, true);
                        System.out.println("\nOpponent raises by $" + RandomNum2 + ".");

                        if(RandomNum2 == PlayerStack - PlayerBetTotal) {
                            System.out.println("\nOpponent raises you All In!");
                        }
                        else {
                            System.out.println("\nOpponent's All In!");
                        }
                        //player responds to All In
                        System.out.println("\nChoose an option:");
                        System.out.println("-------------------");
                        System.out.println("1: Fold");
                        System.out.println("2: Call");
                        int input = scanner.nextInt();
                        //if player folds:
                        if(input == 1) {
                            PlayerStack -= PlayerBetTotal;
                            OpponentStack += OpponentBetTotal;
                            System.out.println("\nYou fold. You lose.");
                            System.out.println("You lost $" + PlayerBetTotal + ".");
                            System.out.println("Your stack is now $" + PlayerStack + ".");
                            System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                            AllInFlagger(BettingRoundSignaler, false, false);
                            return true;
                        }
                        //if player calls:
                        else if(input == 2) {
                            OpponentBetTotal += RandomNum2;
                            PlayerBetTotal += RandomNum2;
                            return true;
                        }
                    }
                    //if computer didn't trigger any All In situations with its raise:
                    if(OpponentHandStrength >= 18) {
                        int x = r.nextInt(10) + 1;
                        if(x < 4) RandomNum = 1;
                        else RandomNum = 2;
                    }
                    else if(OpponentHandStrength < 18) {
                        int x = r.nextInt(10) + 1;
                        if(x < 8) RandomNum = 1;
                        else RandomNum = 2;
                    }
                    if(RandomNum == 1) {
                        RandomNum2 = (int)(RandomNum2 * 0.25);
                    }
                    else if(RandomNum == 2) {
                        RandomNum2 = (int)(RandomNum2 * 0.5);
                    }
                    if(RandomNum2 == 0) RandomNum2++;
                    System.out.println("\nOpponent raises by $" + RandomNum2 + ".");
                    OpponentBetTotal += RandomNum2;
                    //player chooses response:
                    System.out.println("\nChoose an option:");
                    System.out.println("-------------------");
                    System.out.println("1: Fold");
                    System.out.println("2: Call");
                    System.out.println("3: Raise");
                    int input = scanner.nextInt();
                    //if player folds:
                    if(input == 1) {
                        OpponentBetTotal -= RandomNum2;
                        PlayerStack -= PlayerBetTotal;
                        OpponentStack += OpponentBetTotal;
                        System.out.println("\nYou fold. You lose.");
                        System.out.println("You lost $" + PlayerBetTotal + ".");
                        System.out.println("Your stack is now $" + PlayerStack + ".");
                        System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                        deckReset++;
                        return true;
                    }
                    //if player calls:
                    else if(input == 2) {
                        PlayerBetTotal += RandomNum2;
                    }
                    //if player re-raises:
                    else if(input == 3) {
                        PlayerBetTotal += RandomNum2;
                        //calculates how much the player is able to raise
                        PlayerRaise = PlayerStack - PlayerBetTotal;
                        if(PlayerRaise <= OpponentStack - OpponentBetTotal) {
                            System.out.println("How much do you want to raise? (You can raise up to $" + PlayerRaise + ")");
                        }
                        else {
                            PlayerRaise = OpponentStack - OpponentBetTotal;
                            System.out.println("How much do you want to raise? (You can raise up to $" + PlayerRaise + ")");
                        }
                        PlayerBet = scanner.nextInt();
                        if(PlayerBet <= 0 || PlayerBet > PlayerRaise) terminate();
                        
                        //if player raises to the max, then trigger All In flags
                        if(PlayerBet == PlayerRaise) {
                            AllInFlagger(BettingRoundSignaler, true, true);
                            if(PlayerRaise == PlayerStack - PlayerBetTotal) {
                                System.out.println("\nYou're All In!");
                            }
                            else {
                                System.out.println("\nYou raise your opponent All In!");
                            }
                            //generate computer's response to the All In raise: fold or call
                            RandomNum = 0;
                            if(OpponentHandStrength >= 22) {
                                RandomNum = 2;
                            }
                            else if(OpponentHandStrength >= 18) {
                                int x = r.nextInt(10) + 1;
                                if(x < 3) RandomNum = 1;
                                else RandomNum = 2;
                            }
                            else if(OpponentHandStrength >= 14) {
                                int x = r.nextInt(10) + 1;
                                if(x < 8) RandomNum = 1;
                                else RandomNum = 2;
                            }
                            else if(OpponentHandStrength < 14) {
                                RandomNum = 1;
                            }
                                //computer folds:
                                if(RandomNum == 1) {
                                    PlayerStack += PlayerBetTotal;
                                    OpponentStack -= OpponentBetTotal;
                                    System.out.println("\nOpponent folds. You win!");
                                    System.out.println("You win $" + PlayerBetTotal + "!");
                                    System.out.println("Your stack is now $" + PlayerStack + ".");
                                    System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                                    AllInFlagger(BettingRoundSignaler, true, false);
                                    return true;
                                }
                                //computer calls:
                                else {
                                    System.out.println("\nOpponent calls.");
                                    PlayerBetTotal += PlayerBet;
                                    OpponentBetTotal += PlayerBet;
                                    return true;
                                }
                        }
                        //other wise if the player's raise doesn't cause any All Ins:
                        PlayerBetTotal += PlayerBet;
                        //generate computer's response: fold or call
                        if(OpponentHandStrength >= 22) {
                            RandomNum = 2;
                        }
                        else if(OpponentHandStrength >= 18) {
                            int x = r.nextInt(10) + 1;
                            if(x < 3) RandomNum = 1;
                            else RandomNum = 2;
                        }
                        else if(OpponentHandStrength >= 14) {
                            int x = r.nextInt(10) + 1;
                            if(x < 9) RandomNum = 1;
                            else RandomNum = 2;
                        }
                        else if(OpponentHandStrength < 14) {
                            RandomNum = 1;
                        }
                        //if computer folds:
                        if(RandomNum == 1) {
                            PlayerBetTotal -= PlayerBet;
                            PlayerStack += PlayerBetTotal;
                            OpponentStack -= OpponentBetTotal;
                            System.out.println("\nOpponent folds. You win!");
                            System.out.println("You win $" + PlayerBetTotal + "!");
                            System.out.println("Your stack is now $" + PlayerStack + ".");
                            System.out.println("Opponent's stack is now $" + OpponentStack + ".");
                            deckReset++;
                            return true;
                        }
                        //if computer calls:
                        else {
                            OpponentBetTotal += PlayerBet;
                            System.out.println("\nOpponent Calls");
                        }
                    }
                    else {
                        terminate();
                    }
                }
            }
        return false;
    }
    /*this method accounts for every possible trigger of any of the AllIn flags,
    so that these flags can be called in the bettingRound method.
    
    this method is called like this: the first parameter determines the betting
    round (pre-flop, flop, etc.),the second parameter determines whether the AllIn
    is by the player or the computer, and the third paramter determines whether
    the AllIn was caused by the player's action or opponent's action.
    */
    static void AllInFlagger(int BettingRoundSignaler, boolean isPlayer, boolean isTrue) {
        if(BettingRoundSignaler == 1) {
            if(isTrue) {
                if(isPlayer) PlayerAllInPreFlop = true;
                else OpponentAllInPreFlop = true;
            }
            else {
                if(isPlayer) PlayerAllInPreFlop = false;
                else OpponentAllInPreFlop = false;
            }
        }
        if(BettingRoundSignaler == 2) {
            if(isTrue) {
                if(isPlayer) PlayerAllInFlop = true;
                else OpponentAllInFlop = true;
            }
            else {
                if(isPlayer) PlayerAllInFlop = false;
                else OpponentAllInFlop = false;
            }
        }
        if(BettingRoundSignaler == 3) {
            if(isTrue) {
                if(isPlayer) PlayerAllInTurn = true;
                else OpponentAllInTurn = true;
            }
            else {
                if(isPlayer) PlayerAllInTurn = false;
                else OpponentAllInTurn = false;
            }
        }
        if(BettingRoundSignaler == 4) {
            if(isTrue) {
                if(isPlayer) PlayerAllInRiver = true;
                else OpponentAllInRiver = true;
            }
            else {
                if(isPlayer) PlayerAllInRiver = false;
                else OpponentAllInRiver = false;
            }
        }
    }
    
    //ends the program if the user doesn't follow instructions
    static void terminate() {
        System.out.println("\nDon't Cheat!");
        System.exit(0);
    }
    
    static void resetHandVariables() {
        PlayerAllInPreFlop = false;
        PlayerAllInFlop = false;
        PlayerAllInTurn = false;
        PlayerAllInRiver = false;
        OpponentAllInPreFlop = false;
        OpponentAllInFlop = false;
        OpponentAllInTurn = false;
        OpponentAllInRiver = false;
        PlayerHearts = 0;
        PlayerDiamonds = 0;
        PlayerSpades = 0;
        PlayerClubs = 0;
        OpponentHearts = 0;
        OpponentDiamonds = 0;
        OpponentSpades = 0;
        OpponentClubs = 0;
        PlayerRoyalFlush = false;
        OpponentRoyalFlush = false;
        PlayerStraightFlush = false;
        PlayerStraightFlushHigh = 0;
        OpponentStraightFlush = false;
        OpponentStraightFlushHigh = 0;
        PlayerFourOfAKind = false;
        PlayerFourOfAKindHigh = 0;
        OpponentFourOfAKind = false;
        OpponentFourOfAKindHigh = 0;
        PlayerFullHouse = false;
        PlayerFullHouseHigh1 = 0;
        PlayerFullHouseHigh2 = 0;
        OpponentFullHouse = false;
        OpponentFullHouseHigh1 = 0;
        OpponentFullHouseHigh2 = 0;
        PlayerFlush = false;
        PlayerFlushHigh = 0;
        PlayerFlushHighHeart = 0;
        PlayerFlushHighDiamond = 0;
        PlayerFlushHighSpade = 0;
        PlayerFlushHighClub = 0;
        OpponentFlush = false;
        OpponentFlushHigh = 0;
        OpponentFlushHighHeart = 0;
        OpponentFlushHighDiamond = 0;
        OpponentFlushHighSpade = 0;
        OpponentFlushHighClub = 0;
        FlushHigh = 0;
        PlayerStraight = false;
        PlayerStraightHigh = 0;
        OpponentStraight = false;
        OpponentStraightHigh = 0;
        StraightHigh = 0;
        PlayerThreeOfAKind = false;
        PlayerThreeOfAKindHigh = 0;
        OpponentThreeOfAKind = false;
        OpponentThreeOfAKindHigh = 0;
        PlayerTwoPair = false;
        PlayerTwoPairHigh = 0;
        PlayerTwoPairSecondHigh = 0;
        OpponentTwoPair = false;
        OpponentTwoPairHigh = 0;
        OpponentTwoPairSecondHigh = 0;
        PlayerOnePair = false;
        PlayerOnePairHigh = 0;
        OpponentOnePair = false;
        OpponentOnePairHigh = 0;
        PlayerHighCard = false;
        PlayerHighCardValue = 0;
        OpponentHighCard = false;
        OpponentHighCardValue = 0;
        PlayerHighCardKickers = new ArrayList<>();
        PlayerOnePairKickers = new ArrayList<>();
        PlayerTwoPairKickers = new ArrayList<>();
        PlayerThreeOfAKindKickers = new ArrayList<>();
        PlayerFourOfAKindKickers = new ArrayList<>();
        OpponentHighCardKickers = new ArrayList<>();
        OpponentOnePairKickers = new ArrayList<>();
        OpponentTwoPairKickers = new ArrayList<>();
        OpponentThreeOfAKindKickers = new ArrayList<>();
        OpponentFourOfAKindKickers = new ArrayList<>();
        Chop = false;
        PlayerWin = false;
        OpponentWin = false;
        one = 0;
        two = 0;
        three = 0;
        four = 0;
        five = 0;
        six = 0;
        seven = 0;
        eight = 0;
        nine = 0;
        PlayerBet = 0;
        PlayerBetTotal = 0;
        OpponentBet = 0;
        OpponentBetTotal = 0;
        PlayerBetRange = 0;
        PlayerRaise = 0;
        OpponentHandStrength = 0;
    }
    //determines the strength of the computer's hand
    static void opponentHandStrength(String str1, String str2) {
        int card1 = NumConverter(str1);
        int card2 = NumConverter(str2);
        OpponentHandStrength += card1 + card2;
        
        if(card1 == card2) {
            OpponentHandStrength += 15;
        }
        if(card1 >= 11 && card2 >= 11) {
            OpponentHandStrength += 8;
        }
        else if(card1 >= 11) {
            OpponentHandStrength += 3;
        }
        else if(card2 >= 11) {
            OpponentHandStrength += 3;
        }
        if(Math.abs(card1 - card2) == 1) {
            OpponentHandStrength += 1;
        }
        if(SuitChecker(str1).equals(SuitChecker(str2))) {
            OpponentHandStrength += 2;
        }
    }

    //END OF PROGRAM//
    
}
