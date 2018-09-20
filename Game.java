
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * Handles the whole Game:
 *  Draws the UI
 *  Calculates the game
 *  Manages mouse clicks
 *
 * @author Lucas & Filip
 * @version 18.09.2018
 */

public class Game extends JPanel
{    
    private Dealer dealer = new Dealer();
    private Player player = new Player(dealer);

    int chipHeight=87;
    int chipWidth=87;
    int buttonHeight=78;
    int buttonWidth=78;
    private JLabel labelChips;
    private JLabel labelBetCoin;
    private JLabel labelBetText;

    private int state = 1; // 1 = Bet; 2 = Players Turn; 3 = Win; 4 = Dealers Turn

    private Image background;

    public Game()
    {   
        dealer.createCards();
        game();

        initComponents();
    }

    /**
     * Implements a mouse handling method
     */
    private void game()
    {

        ImageIcon iiBackground = new ImageIcon("src/resources/background.png");
        background = iiBackground.getImage();

        int w = background.getWidth(this);
        int h = background.getHeight(this);
        setPreferredSize(new Dimension(w, h));
        setLayout(null);

        drawChips();
        if (player.getBet() != 0)
        {
            drawBet();
        }

        add(new Automation(this));
    }

    private JButton buttonCreator(int x, int y, String src, boolean isButton){
        int h;
        int w;
        if(isButton){
            h=buttonHeight;
            w=buttonWidth;
        } else{
            h=chipHeight;
            w=chipWidth; 
        }
        JButton button=new JButton();
        button.setBounds(x, y, w, h);
        ImageIcon iicon = new ImageIcon(new ImageIcon("src/resources/" + src).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
        button.setIcon(iicon);
        button.setVisible(true);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        add(button);
        return button;
    }

    private void initComponents(){
        int chipValue[]=new int[4];
        String chipIcon[]=new String[4];
        JButton buttonReset;
        JButton buttonAllIn;
        JButton buttonDeal;
        JLabel reset;
        JLabel allIn;
        JLabel deal;
        JButton button1;
        JButton button2;
        JButton button3;
        JButton button4;
        JLabel lChipBar;
        JLabel bet;

        buttonReset=buttonCreator(30, 380, "button_Reset.png", true);
        buttonReset.setBorderPainted(false);
        buttonReset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.reset();
                    gameloop(-1);
                }
            });
        reset= new JLabel("Reset");
        reset.setBounds(52,450, 100, 30);
        reset.setForeground(new Color(255,255,255));
        add(reset);
            
        buttonAllIn=buttonCreator(145,440, "button_all_in.png", true);
        buttonAllIn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.allIn();
                    gameloop(-1);
                }
            });
        /*allIn= new JLabel("All In");
        allIn.setBounds(167,515, 100, 30);
        allIn.setForeground(new Color(255,255,255));
        add(allIn);*/
        
        buttonDeal=buttonCreator(265,375,"button_deal.png", true);
        buttonDeal.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {

                    if (player.deal() == 0)
                    {
                        removeAll();
                        dealer.deal();
                        drawCards();
                        initComponents2();
                        state = 2;
                    }
                    gameloop(-1);
                }
            });
        /*deal= new JLabel("Deal");
        deal.setBounds(287,450, 100, 30);
        deal.setForeground(new Color(255,255,255));
        add(deal);*/
        
        int i=0;
        if(player.getChips()>=5000 && i<4){
            chipValue[i]=5000;
            chipIcon[i]= "chip5000.png";
            i++;
        }
        if(player.getChips()>=1000 && i<4){
            chipValue[i]=1000;
            chipIcon[i]= "chip1000.png";
            i++;
        }
        if(player.getChips()>=500 && i<4){
            chipValue[i]=500;
            chipIcon[i]= "chip500.png";
            i++;
        }
        if(player.getChips()>=100 && i<4){
            chipValue[i]=100;
            chipIcon[i]= "chip100.png";
            i++;
        }
        if(player.getChips()>=25 && i<4){
            chipValue[i]=25;
            chipIcon[i]= "chip25.png";
            i++;
        }
        if(player.getChips()>=5 && i<4){
            chipValue[i]=5;
            chipIcon[i]= "chip5.png";
            i++;
        }
        if(player.getChips()>=1 && i<4){
            chipValue[i]=1;
            chipIcon[i]= "chip1.png";
            i++;
        }
        int chipX=17;
        int xPadding=85;
        int chipY=565;
        button1=buttonCreator(chipX,chipY,chipIcon[0], false);
        button1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.bet(chipValue[0]);
                    gameloop(-1);
                }
            });

        button2=buttonCreator(chipX+xPadding-1,chipY,chipIcon[1], false);
        button2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.bet(chipValue[1]);
                    gameloop(-1);
                }
            });

        button3=buttonCreator(chipX+(2*xPadding),chipY,chipIcon[2], false);
        button3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.bet(chipValue[2]);
                    gameloop(-1);
                }
            });

        button4=buttonCreator(chipX+(3*xPadding),chipY,chipIcon[3], false);
        button4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.bet(chipValue[3]);
                    gameloop(-1);
                }
            });

        lChipBar= new JLabel();
        lChipBar.setBounds(5,561,375,105);
        ImageIcon iiChipBar = new ImageIcon("src/resources/chipBar.png");

        lChipBar.setIcon(iiChipBar);
        add(lChipBar);

    }

    public void initComponents2(){
        Image white;
        Image iDouble;
        Image iHit;
        Image iStand;
        JButton buttonHit;
        JButton buttonStand;
        JButton buttonDouble;
        JLabel hit;
        JLabel stand;
        JLabel doubble;
        JPanel cardGame;
        JPanel cardMenu;
        JPanel cards;
        JLabel littleChip;
        
        littleChip= new JLabel();
        littleChip.setBounds(15,628,25,25);
        littleChip.setIcon(new ImageIcon("src/resources/littleChip.png"));
        add(littleChip);
        
        buttonHit=buttonCreator(15, 465, "button_hit.png", true);
        buttonHit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.hit();
                }
            });
        hit= new JLabel("Hit");
        hit.setBounds(45,540, 100, 30);
        hit.setForeground(new Color(255,255,255));
        add(hit);

        buttonStand= buttonCreator(140,525,"button_stand.png", true);
        buttonStand.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.stand();
                }
            });
        stand= new JLabel("Stand");
        stand.setBounds(160,600, 100, 30);
        stand.setForeground(new Color(255,255,255));
        add(stand);

        buttonDouble=buttonCreator(275,465,"button_double.png", true);
        buttonDouble.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player.doubble();
                }
            });
        doubble= new JLabel("Double");
        doubble.setBounds(295,540, 100, 30);
        doubble.setForeground(new Color(255,255,255));
        add(doubble);
            
    }

    public void gameloop(int element)
    {
        try{//updated non-static Elements
            remove(labelChips);
            remove(labelBetCoin);
            remove(labelBetText);
        } catch(Exception e){//if a label hasn´t been added
        }
        if (state == 3)
        {
            state = 1;
        }
        if (!player.getFinished())
        {
            if (state == 1)
            {

                if (element == 2)
                {
                    if (player.deal() == 0)
                    {
                        dealer.deal();
                        drawCards();
                        state = 2;
                    }
                }

            }
            else if (state == 2)
            {
                JLabel label;
                if (element == 0)
                {
                    player.hit();
                    label = new JLabel("HIT");
                }
                if (element == 1)
                {
                    player.stand();
                    label = new JLabel("STAND");
                    state = 4;
                }
                if (element == 2)
                {
                    if (player.doubble() == 0)
                    {
                        label = new JLabel("DOUBLE");
                        state = 4;
                    }
                }
                if (player.getBusted())
                {
                    label = new JLabel("BUST");
                    state = 4;
                }
                else
                {
                    label = new JLabel();
                }
                label.setFont(new Font("Calibri",Font.BOLD , 24));
                label.setForeground(Color.white);
                add(label);
                label.setBounds(300, 2, 150, 50);

                if (player.getFinished())
                {
                    state = 4;
                }
            }
        }
        else if (!dealer.getFinished())
        {
            dealer.turn();
        }
        else if (player.getFinished() && dealer.getFinished())
        {
            roundEnd();
        }

        // Draw UI
        drawChips();
        if (player.getBet() != 0)
        {
            drawBet();
        }
        if (state == 2 || state == 4)
        {
            drawCards();
            drawPlayersHandValue();
            drawDealersHandValue();
        }
        repaint();
    }

    public void roundEnd()
    {
        if ((player.getHandValue() > dealer.getHandValue() && !player.getBusted()) || (!player.getBusted() && dealer.getBusted()))
        {
            player.setChips(player.getBet()*2);
            state = 3;
            drawWin();
        }
        if ((player.getHandValue() < dealer.getHandValue() && !dealer.getBusted()) || (player.getBusted() && !dealer.getBusted()))
        {
            state = 1;
        }
        if (player.getHandValue() == dealer.getHandValue() || (player.getBusted() && dealer.getBusted()))
        {
            player.setChips(player.getBet());
            state = 1;

            JLabel label = new JLabel("PUSH");
            label.setFont(new Font("Calibri",Font.BOLD , 24));
            label.setForeground(Color.white);
            add(label);
            label.setBounds(300, 2, 150, 50);
        }

        player.writeChips();

        player.flushHand();
        dealer.flushHand();
    }

    public void drawCards()
    {
        for (int i = 0; i < 2; i++)
        {
            Card[] hand;
            if (i == 0)
            {
                hand = player.getHand();
            }
            else
            {
                hand = dealer.getHand();
            }

            int c1 = 0;
            for (int j = hand.length-1; j >= 0; j--)
            {
                if (hand[j] != null)
                {
                    c1++;
                }
            }

            int c2 = 0;
            for (int j = hand.length-1; j >= 0; j--)
            {
                if (hand[j] != null)
                {
                    JLabel icon = new JLabel(hand[j].getIcon());
                    add(icon);
                    int x = 190 - (60 + c2 * 30) + (((c1 - 1) / 2) * 30);
                    int y;
                    c2++;
                    if (i == 0){y = 340;}
                    else {y = 100;}
                    icon.setBounds(x, y, 120, 180);
                }
            }
        } 
    }

    public void drawChips()
    {
        labelChips = new JLabel("$" + Integer.toString(player.getChips()));
        labelChips.setFont(new Font("Calibri",Font.BOLD , 18));
        labelChips.setForeground(Color.white);
        add(labelChips);
        if (state == 1)
        {
            labelChips.setBounds(45, 520, 250, 50);
        }
        if (state == 2 || state == 3 || state == 4)
        {
            labelChips.setBounds(40, 615, 250, 50);
        }
    }

    public void drawWin()
    {
        JLabel label = new JLabel("$" + Integer.toString(player.getBet()*2));
        label.setFont(new Font("Calibri",Font.BOLD , 42));
        label.setForeground(Color.white);
        add(label);
        label.setBounds(130, 120, 250, 50);
    }

    public void drawBet()
    {
        labelBetText = new JLabel("$" + Integer.toString(player.getBet()));
        labelBetText.setFont(new Font("Calibri",Font.BOLD , 18));
        labelBetText.setForeground(Color.white);
        add(labelBetText);

        ImageIcon iicon;
        if (player.getBet() - 5000 >= 0){iicon =new ImageIcon("src/resources/chip5000.png");}
        else if (player.getBet() - 500  >= 0){iicon = new ImageIcon("src/resources/chip500.png");}           
        else if (player.getBet() - 100  >= 0){iicon = new ImageIcon("src/resources/chip100.png");}
        else if (player.getBet() - 10   >= 0){iicon = new ImageIcon("src/resources/chip25.png");}
        else {iicon = new ImageIcon("");}
        labelBetCoin=new JLabel(iicon);
        add(labelBetCoin);

        if (state == 1)
        {
            labelBetText.setBounds(160, 425, 250, 50);
            labelBetCoin.setBounds(148, 350, iicon.getIconWidth(), iicon.getIconHeight());
        }
        if (state == 2 || state == 4)
        {
            labelBetText.setBounds(20, 230, 250, 50);
            labelBetCoin.setBounds(8, 152, 78, 78);
        }
    }

    public void drawPlayersHandValue()
    {
        JLabel label = new JLabel(Integer.toString(player.getHandValue()));
        label.setIcon(new ImageIcon("src/resources/box.png"));
        label.setFont(new Font("Calibri",Font.BOLD , 16));
        label.setForeground(Color.black);
        add(label);
        label.setBounds(158, 289, 50, 50);
    }

    public void drawDealersHandValue()
    {
        JLabel label;
        JLabel box1; 
        label= new JLabel();
        label.setIcon(new ImageIcon("src/resources/box.png"));
        label.setFont(new Font("Calibri",Font.BOLD , 18));
        label.setForeground(Color.black);
        if (player.getFinished()){label = new JLabel(Integer.toString(dealer.getHandValue()));}
        else {label.setText(Integer.toString(dealer.getFirstCardValue()));}
        add(label);
        label.setBounds(158, 30, 40, 30);
    }

    /**
     * Overwritten to draw the background pictures
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);

        Toolkit.getDefaultToolkit().sync();
    }
}
