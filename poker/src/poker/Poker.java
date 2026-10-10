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
    static ArrayList<Integer> PlayerFlushHearts = new ArrayList<>();
    static ArrayList<Integer> PlayerFlushDiamonds = new ArrayList<>();
    static ArrayList<Integer> PlayerFlushSpades = new ArrayList<>();
    static ArrayList<Integer> PlayerFlushClubs = new ArrayList<>();
    static boolean OpponentFlush = false;
    static int OpponentFlushHigh;
    static ArrayList<Integer> OpponentFlushHearts = new ArrayList<>();
    static ArrayList<Integer> OpponentFlushDiamonds = new ArrayList<>();
    static ArrayList<Integer> OpponentFlushSpades = new ArrayList<>();
    static ArrayList<Integer> OpponentFlushClubs = new ArrayList<>();
    static boolean isHeartFlush = false;
    static boolean isDiamondFlush = false;
    static boolean isSpadeFlush = false;
    static boolean isClubFlush = false;
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
    static int PlayerOnePairSecondHigh = 0;
    static boolean OpponentOnePair = false;
    static int OpponentOnePairHigh = 0;
    static int OpponentOnePairSecondHigh = 0;
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
        str3 = "K spades";
        str4 = "K clubs";
        
        cards.remove(str1);
        cards.remove(str2);
        cards.remove(str3);
        cards.remove(str4);
        
        ArrayList<String> simulationDeck = new ArrayList<>(cards);
        
        int trials = 1000000;
        
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
        if(!str.replace(" hearts", "").equals(str)) {
            return "H";
        }
        else if(!str.replace(" diamonds", "").equals(str)) {
            return "D";
        }
        else if(!str.replace(" spades", "").equals(str)) {
            return "S";
        }
        else {
            return "C";
        }
    }
    
    //method scans the user's cards (user's hole cards and community cards) and determines how many cards of each suit there are
    //and what the value of the highest-value card of each suit is
    static void PlayersuitCounter (String suit, String str) {
        String h = "H";
        String d = "D";
        String s = "S";
        String c = "C";
        
        if(suit.equals(h)) {
            PlayerHearts++;
            PlayerFlushHearts.add(NumConverter(str));
        }
        else if(suit.equals(d)) {
            PlayerDiamonds++;
            PlayerFlushDiamonds.add(NumConverter(str));
        }
        else if(suit.equals(s)) {
            PlayerSpades++;
            PlayerFlushSpades.add(NumConverter(str));
        }
        else if(suit.equals(c)) {
            PlayerClubs++;
            PlayerFlushClubs.add(NumConverter(str));
        }
    }
    
    //does the same as the previous method, but for the computer's cards
    static void OpponentsuitCounter (String suit, String str) {
        String h = "H";
        String d = "D";
        String s = "S";
        String c = "C";
            
        if(suit.equals(h)) {
            OpponentHearts++;
            OpponentFlushHearts.add(NumConverter(str));
        }
        else if(suit.equals(d)) {
            OpponentDiamonds++;
            OpponentFlushDiamonds.add(NumConverter(str));
        }
        else if(suit.equals(s)) {
            OpponentSpades++;
            OpponentFlushSpades.add(NumConverter(str));
        }
        else if(suit.equals(c)) {
            OpponentClubs++;
            OpponentFlushClubs.add(NumConverter(str));
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
        int num;
        
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
        // Sort the input values to make it easier to check for straights
        int[] nums = {one, two, three, four, five, six, seven};
        Arrays.sort(nums);
        if(nums[6] == 14 && nums[5] != 13) {
            nums[6] = 1;
            Arrays.sort(nums);
        }
        else if(nums[6] == 14 && nums[5] == 13 && Straight(nums[3],nums[2],nums[1],nums[0],1)) {
            nums[6] = 1;
            Arrays.sort(nums);
        }
        
        // Check for the highest possible straights first (Ace-high down to 5-high)
        if (Straight(nums[6], nums[5], nums[4], nums[3], nums[2])) {  // Highest 5 cards (e.g., 10-J-Q-K-A)
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[4], nums[3], nums[1])) {  // Skip the 2nd lowest card
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[4], nums[3], nums[0])) {  // Skip the lowest card
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[4], nums[2], nums[1])) {  // Skip the 3rd lowest card
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[4], nums[2], nums[0])) {  // Skip the 2nd and 3rd lowest
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[4], nums[1], nums[0])) {  // Skip the 2nd and 4th lowest
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[3], nums[2], nums[1])) {  // Skip the 3rd highest card
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[3], nums[2], nums[0])) {  // Skip the 3rd highest and lowest
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[3], nums[1], nums[0])) {  // Skip the 3rd highest and 2nd lowest
            return true;
        }
        else if (Straight(nums[6], nums[5], nums[2], nums[1], nums[0])) {  // Skip the 3rd and 4th highest
            return true;
        }
        else if (Straight(nums[6], nums[4], nums[3], nums[2], nums[1])) {  // Skip the 2nd highest card
            return true;
        }
        else if (Straight(nums[6], nums[4], nums[3], nums[2], nums[0])) {  // Skip the 2nd highest and lowest
            return true;
        }
        else if (Straight(nums[6], nums[4], nums[3], nums[1], nums[0])) {  // Skip the 2nd highest and 2nd lowest
            return true;
        }
        else if (Straight(nums[6], nums[4], nums[2], nums[1], nums[0])) {  // Skip the 2nd highest and 3rd lowest
            return true;
        }
        else if (Straight(nums[6], nums[3], nums[2], nums[1], nums[0])) {  // Skip the 2nd and 3rd highest
            return true;
        }
        else if (Straight(nums[5], nums[4], nums[3], nums[2], nums[1])) {  // Skip the highest card
            return true;
        }
        else if (Straight(nums[5], nums[4], nums[3], nums[2], nums[0])) {  // Skip the highest and lowest
            return true;
        }
        else if (Straight(nums[5], nums[4], nums[3], nums[1], nums[0])) {  // Skip the highest and 2nd lowest
            return true;
        }
        else if (Straight(nums[5], nums[4], nums[2], nums[1], nums[0])) {  // Skip the highest and 3rd lowest
            return true;
        }
        else if (Straight(nums[5], nums[3], nums[2], nums[1], nums[0])) {  // Skip the highest and 3rd highest
            return true;
        }
        else if (Straight(nums[4], nums[3], nums[2], nums[1], nums[0])) {  // Skip the 2 highest cards
            return true;
        }
        else {
            return false;
        }
    }
    
    //checks if the player has a straight flush
    static boolean PlayerStraightFlush(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        if(PlayerStraight && PlayerFlush) {
            ArrayList<String> cards = new ArrayList<>();
            cards.add(str1);
            cards.add(str2);
            cards.add(str3);
            cards.add(str4);
            cards.add(str5);
            cards.add(str6);
            cards.add(str7);
            if(isHeartFlush) {
                for(int i = 0; i < cards.size(); i++) {
                    if(!SuitChecker(cards.get(i)).equals("H")) {
                        cards.remove(i);
                        i--;
                    }
                }
            }
            else if(isDiamondFlush) {
                for(int i = 0; i < cards.size(); i++) {
                    if(!SuitChecker(cards.get(i)).equals("D")) {
                        cards.remove(i);
                        i--;
                    }
                }
            }
            else if(isSpadeFlush) {
                for(int i = 0; i < cards.size(); i++) {
                    if(!SuitChecker(cards.get(i)).equals("S")) {
                        cards.remove(i);
                        i--;
                    }
                }
            }
            else if(isClubFlush) {
                for(int i = 0; i < cards.size(); i++) {
                    if(!SuitChecker(cards.get(i)).equals("C")) {
                        cards.remove(i);
                        i--;
                    }
                }
            }
            int one = NumConverter(cards.get(0));
            int two = NumConverter(cards.get(1));
            int three = NumConverter(cards.get(2));
            int four = NumConverter(cards.get(3));
            int five = NumConverter(cards.get(4));
            int six = NumConverter(cards.get(4));
            int seven = NumConverter(cards.get(4));
            if(cards.size() == 5) {
                if(StraightChecker(one, two, three, four, five, six, seven)) {
                    PlayerStraightFlushHigh = StraightHigh;
                    return true;
                }
            }
            if(cards.size() == 6) {
                six = NumConverter(cards.get(5));
                if(StraightChecker(one, two, three, four, five, six, seven)) {
                    PlayerStraightFlushHigh = StraightHigh;
                    return true;
                }
            }
            if(cards.size() == 7) {
                six = NumConverter(cards.get(5));
                seven = NumConverter(cards.get(6));
                if(StraightChecker(one, two, three, four, five, six, seven)) {
                    PlayerStraightFlushHigh = StraightHigh;
                    return true;
                }
            }
        }
            
        return false;
    }
    
    //checks if the computer has a straight flush
    static boolean OpponentStraightFlush(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        if(OpponentStraight && OpponentFlush) {
            ArrayList<String> cards = new ArrayList<>();
            cards.add(str1);
            cards.add(str2);
            cards.add(str3);
            cards.add(str4);
            cards.add(str5);
            cards.add(str6);
            cards.add(str7);
            if(isHeartFlush) {
                for(int i = 0; i < cards.size(); i++) {
                    if(!SuitChecker(cards.get(i)).equals("H")) {
                        cards.remove(i);
                        i--;
                    }
                }
            }
            else if(isDiamondFlush) {
                for(int i = 0; i < cards.size(); i++) {
                    if(!SuitChecker(cards.get(i)).equals("D")) {
                        cards.remove(i);
                        i--;
                    }
                }
            }
            else if(isSpadeFlush) {
                for(int i = 0; i < cards.size(); i++) {
                    if(!SuitChecker(cards.get(i)).equals("S")) {
                        cards.remove(i);
                        i--;
                    }
                }
            }
            else if(isClubFlush) {
                for(int i = 0; i < cards.size(); i++) {
                    if(!SuitChecker(cards.get(i)).equals("C")) {
                        cards.remove(i);
                        i--;
                    }
                }
            }
            int one = NumConverter(cards.get(0));
            int two = NumConverter(cards.get(1));
            int three = NumConverter(cards.get(2));
            int four = NumConverter(cards.get(3));
            int five = NumConverter(cards.get(4));
            int six = NumConverter(cards.get(4));
            int seven = NumConverter(cards.get(4));
            if(cards.size() == 5) {
                if(StraightChecker(one, two, three, four, five, six, seven)) {
                    OpponentStraightFlushHigh = StraightHigh;
                    return true;
                }
            }
            if(cards.size() == 6) {
                six = NumConverter(cards.get(5));
                if(StraightChecker(one, two, three, four, five, six, seven)) {
                    OpponentStraightFlushHigh = StraightHigh;
                    return true;
                }
            }
            if(cards.size() == 7) {
                six = NumConverter(cards.get(5));
                seven = NumConverter(cards.get(6));
                if(StraightChecker(one, two, three, four, five, six, seven)) {
                    OpponentStraightFlushHigh = StraightHigh;
                    return true;
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
        int value1 = 0;
        int value2 = 0;
        int counter2 = 0;
        int value3 = 0;
        int counter3 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                if(i == 6) {
                    counter1++;
                    value1 = nums[i];
                }
                else if(nums[i] == value1) {
                    counter1++;
                }
                else if(value1 == 0) {
                    value1 = nums[i];
                    counter1++;
                }
                else if(value2 == 0) {
                    value2 = nums[i];
                    counter2++;
                }
                else if(nums[i] == value2) {
                    counter2++;
                }
                else if(value3 == 0) {
                    value3 = nums[i];
                    counter3++;
                }
                else if(nums[i] == value3) {
                    counter3++;
                }
                if(counter1 == 2 || counter2 == 2 || counter3 == 2) {
                    break;
                }
            }
        }
        if(counter1 == 2 || counter2 == 2 || counter3 == 2) {
            if(counter1 == 2) {
                PlayerThreeOfAKindHigh = value1;
            }
            else if(counter2 == 2) {
                PlayerThreeOfAKindHigh = value2;
            }
            else if(counter3 == 2) {
                PlayerThreeOfAKindHigh = value3;
            }
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] != PlayerThreeOfAKindHigh) {
                    PlayerThreeOfAKindKickers.add(nums[j]);
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
        int value1 = 0;
        int value2 = 0;
        int counter2 = 0;
        int value3 = 0;
        int counter3 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                if(i == 6) {
                    counter1++;
                    value1 = nums[i];
                }
                else if(nums[i] == value1) {
                    counter1++;
                }
                else if(value1 == 0) {
                    value1 = nums[i];
                    counter1++;
                }
                else if(value2 == 0) {
                    value2 = nums[i];
                    counter2++;
                }
                else if(nums[i] == value2) {
                    counter2++;
                }
                else if(value3 == 0) {
                    value3 = nums[i];
                    counter3++;
                }
                else if(nums[i] == value3) {
                    counter3++;
                }
                if(counter1 == 2 || counter2 == 2 || counter3 == 2) {
                    break;
                }
            }
        }
        if(counter1 == 2 || counter2 == 2 || counter3 == 2) {
            if(counter1 == 2) {
                OpponentThreeOfAKindHigh = value1;
            }
            else if(counter2 == 2) {
                OpponentThreeOfAKindHigh = value2;
            }
            else if(counter3 == 2) {
                OpponentThreeOfAKindHigh = value3;
            }
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] != OpponentThreeOfAKindHigh) {
                    OpponentThreeOfAKindKickers.add(nums[j]);
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
        int counter = 0;
        int kicker = 0;
        int kicker2 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                counter++;
                if(nums[i] > kicker) {
                    kicker2 = kicker;
                    kicker = nums[i];
                }
                else if(nums[i] > kicker2) {
                    kicker2 = nums[i];
                }
                if(counter == 2) break;
            }
        }
        if(counter == 2) {
            PlayerTwoPairHigh = kicker;
            PlayerTwoPairSecondHigh = kicker2;
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
        int kicker = 0;
        int kicker2 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                counter1++;
                if(nums[i] > kicker) {
                    kicker2 = kicker;
                    kicker = nums[i];
                }
                else if(nums[i] > kicker2) {
                    kicker2 = nums[i];
                }
                if(counter1 == 2) break;
            }
        }
        if(counter1 == 2) {
            OpponentTwoPairHigh = kicker;
            OpponentTwoPairSecondHigh = kicker2;
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
        int value1 = 0;
        int counter2 = 0;
        int value2 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                if(value1 == 0) {
                    counter1++;
                    value1 = nums[i];
                }
                else if(value2 == 0 && nums[i] != value1) {
                    counter2++;
                    value2 = nums[i];
                }
                if(counter2 == 1) {
                    break;
                }
            }
        }
        if(counter1 == 1) {
            PlayerOnePairHigh = value1;
            if(counter2 == 1) {
                PlayerOnePairSecondHigh = value2;
            }
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] != PlayerOnePairHigh && nums[j] != PlayerOnePairSecondHigh) {
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
        int value1 = 0;
        int counter2 = 0;
        int value2 = 0;
        for(int i = 6; i > 0; i--) {
            if(nums[i] - nums[i - 1] == 0) {
                if(value1 == 0) {
                    counter1++;
                    value1 = nums[i];
                }
                else if(value2 == 0 && nums[i] != value1) {
                    counter2++;
                    value2 = nums[i];
                }
                if(counter2 == 1) {
                    break;
                }
            }
        }
        if(counter1 == 1) {
            OpponentOnePairHigh = value1;
            if(counter2 == 1) {
                OpponentOnePairSecondHigh = value2;
            }
            for(int j = 0; j < nums.length; j++) {
                if(nums[j] != OpponentOnePairHigh && nums[j] != OpponentOnePairSecondHigh) {
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
            if(PlayerOnePairHigh == PlayerThreeOfAKindHigh) {
                if(PlayerOnePairSecondHigh == 0) {
                    return false;
                }
                else {
                    PlayerFullHouseHigh1 = PlayerThreeOfAKindHigh;
                    PlayerFullHouseHigh2 = PlayerOnePairSecondHigh;
                }
            }
            else {
                PlayerFullHouseHigh1 = PlayerThreeOfAKindHigh;
                PlayerFullHouseHigh2 = PlayerOnePairHigh;
            }
            return true;
        }
        return false;
    }
    
    //checks if the computer has a full house
    static boolean OpponentFullHouse(String str1, String str2, String str3, String str4, String str5, String str6, String str7) {
        if(OpponentOnePairHigh > 0 && OpponentThreeOfAKindHigh > 0) {
            if(OpponentOnePairHigh == OpponentThreeOfAKindHigh) {
                if(OpponentOnePairSecondHigh == 0) {
                    return false;
                }
                else {
                    OpponentFullHouseHigh1 = OpponentThreeOfAKindHigh;
                    OpponentFullHouseHigh2 = OpponentOnePairSecondHigh;
                }
            }
            else {
                OpponentFullHouseHigh1 = OpponentThreeOfAKindHigh;
                OpponentFullHouseHigh2 = OpponentOnePairHigh;
            }
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
        PlayersuitCounter(str1Suit, str1);
        one = NumConverter(str1);

        String str2Suit = SuitChecker(str2);
        PlayersuitCounter(str2Suit, str2);
        two = NumConverter(str2);

        String str3Suit = SuitChecker(str3);
        OpponentsuitCounter(str3Suit, str3);
        three = NumConverter(str3);

        String str4Suit = SuitChecker(str4);
        OpponentsuitCounter(str4Suit, str4);
        four = NumConverter(str4);

        String str5Suit = SuitChecker(str5);
        OpponentsuitCounter(str5Suit, str5);
        PlayersuitCounter(str5Suit, str5);
        five = NumConverter(str5);

        String str6Suit = SuitChecker(str6);
        OpponentsuitCounter(str6Suit, str6);
        PlayersuitCounter(str6Suit, str6);
        six = NumConverter(str6);

        String str7Suit = SuitChecker(str7);
        OpponentsuitCounter(str7Suit, str7);
        PlayersuitCounter(str7Suit, str7);
        seven = NumConverter(str7);

        String str8Suit = SuitChecker(str8);
        OpponentsuitCounter(str8Suit, str8);
        PlayersuitCounter(str8Suit, str8);
        eight = NumConverter(str8);

        String str9Suit = SuitChecker(str9);
        OpponentsuitCounter(str9Suit, str9);
        PlayersuitCounter(str9Suit, str9);
        nine = NumConverter(str9);

        /* Calls upon all the hand checker methods and determines whether or not the player and computer
        have these hands. If the player or computer is determined to qualify for multiple hands (e.g.
        both one pair and two pair), then the program will decipher which hand is stronger and only return
        the strongest hand. At the end, if the player or computer has a stronger hand, then they will
        respectively be deemed the winner. Otherwise, if they have the same hand, then program will evaluate
        kickers/high cards to determine the winner. If the player and computer have the same kickers/high cards
        as well, then they will chop the pot. */
        
        if(PlayerOnePair(str1, str2, str5, str6, str7, str8, str9)) {
            PlayerOnePair = true;
        }
        if(OpponentOnePair(str3, str4, str5, str6, str7, str8, str9)) {
            OpponentOnePair = true;
        }
        
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
        
        //Player straight checker
        if(StraightChecker(one, two, five, six, seven, eight, nine)) {
            PlayerStraight = true;
            PlayerThreeOfAKind = false;
            PlayerTwoPair = false;
            PlayerOnePair = false;
            PlayerStraightHigh = StraightHigh;
        }
        //Opponent straight checker
        if(StraightChecker(three, four, five, six, seven, eight, nine)) {
            OpponentStraight = true;
            OpponentThreeOfAKind = false;
            OpponentTwoPair = false;
            OpponentOnePair = false;
            OpponentStraightHigh = StraightHigh;
        }
        Collections.sort(PlayerFlushHearts, Collections.reverseOrder());
        Collections.sort(PlayerFlushDiamonds, Collections.reverseOrder());
        Collections.sort(PlayerFlushSpades, Collections.reverseOrder());
        Collections.sort(PlayerFlushClubs, Collections.reverseOrder());
        if(PlayerHearts >= 5 || PlayerDiamonds >= 5 || PlayerSpades >= 5 || PlayerClubs >= 5) {
            PlayerFlush = true;
            PlayerThreeOfAKind = false;
            PlayerTwoPair = false;
            PlayerOnePair = false;
            if(PlayerHearts >= 5) {
                PlayerFlushHigh = PlayerFlushHearts.get(0);
                isHeartFlush = true;
            }
            else if(PlayerDiamonds >= 5) {
                PlayerFlushHigh = PlayerFlushDiamonds.get(0);
                isDiamondFlush = true;
            }
            else if(PlayerSpades >= 5) {
                PlayerFlushHigh = PlayerFlushSpades.get(0);
                isSpadeFlush = true;
            }
            else if(PlayerClubs >= 5) {
                PlayerFlushHigh = PlayerFlushClubs.get(0);
                isClubFlush = true;
            }
        }
        Collections.sort(OpponentFlushHearts, Collections.reverseOrder());
        Collections.sort(OpponentFlushDiamonds, Collections.reverseOrder());
        Collections.sort(OpponentFlushSpades, Collections.reverseOrder());
        Collections.sort(OpponentFlushClubs, Collections.reverseOrder());
        if(OpponentHearts >= 5 || OpponentDiamonds >= 5 || OpponentSpades >= 5 || OpponentClubs >= 5) {
            OpponentFlush = true;
            OpponentThreeOfAKind = false;
            OpponentTwoPair = false;
            OpponentOnePair = false;
            if(OpponentHearts >= 5) {
                OpponentFlushHigh = OpponentFlushHearts.get(0);
                isHeartFlush = true;
            }
            else if(OpponentDiamonds >= 5) {
                OpponentFlushHigh = OpponentFlushDiamonds.get(0);
                isDiamondFlush = true;
            }
            else if(OpponentSpades >= 5) {
                OpponentFlushHigh = OpponentFlushSpades.get(0);
                isSpadeFlush = true;
            }
            else if(OpponentClubs >= 5) {
                OpponentFlushHigh = OpponentFlushClubs.get(0);
                isClubFlush = true;
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
                    for (int i = 0; i < 5; i++) {
                        if(isHeartFlush) {
                            if(PlayerFlushHearts.get(i) > OpponentFlushHearts.get(i)) {
                                PlayerWin = true;
                                break;
                            }
                            else if(PlayerFlushHearts.get(i) < OpponentFlushHearts.get(i)) {
                                OpponentWin = true;
                                break;
                            }
                            else if(i == 4) {
                                Chop = true;
                            }
                        }
                        else if(isDiamondFlush) {
                            if(PlayerFlushDiamonds.get(i) > OpponentFlushDiamonds.get(i)) {
                                PlayerWin = true;
                                break;
                            }
                            else if(PlayerFlushDiamonds.get(i) < OpponentFlushDiamonds.get(i)) {
                                OpponentWin = true;
                                break;
                            }
                            else if(i == 4) {
                                Chop = true;
                            }
                        }
                        else if(isSpadeFlush) {
                            if(PlayerFlushSpades.get(i) > OpponentFlushSpades.get(i)) {
                                PlayerWin = true;
                                break;
                            }
                            else if(PlayerFlushSpades.get(i) < OpponentFlushSpades.get(i)) {
                                OpponentWin = true;
                                break;
                            }
                            else if(i == 4) {
                                Chop = true;
                            }
                        }
                        else if(isClubFlush) {
                            if(PlayerFlushClubs.get(i) > OpponentFlushClubs.get(i)) {
                                PlayerWin = true;
                                break;
                            }
                            else if(PlayerFlushClubs.get(i) < OpponentFlushClubs.get(i)) {
                                OpponentWin = true;
                                break;
                            }
                            else if(i == 4) {
                                Chop = true;
                            }
                        }
                    }
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
        PlayerFlushHearts = new ArrayList<>();
        PlayerFlushDiamonds = new ArrayList<>();
        PlayerFlushSpades = new ArrayList<>();
        PlayerFlushClubs = new ArrayList<>();
        OpponentFlush = false;
        OpponentFlushHigh = 0;
        OpponentFlushHearts = new ArrayList<>();
        OpponentFlushDiamonds = new ArrayList<>();
        OpponentFlushSpades = new ArrayList<>();
        OpponentFlushClubs = new ArrayList<>();
        isHeartFlush = false;
        isDiamondFlush = false;
        isSpadeFlush = false;
        isClubFlush = false;
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
        PlayerOnePairSecondHigh = 0;
        OpponentOnePair = false;
        OpponentOnePairHigh = 0;
        OpponentOnePairSecondHigh = 0;
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
