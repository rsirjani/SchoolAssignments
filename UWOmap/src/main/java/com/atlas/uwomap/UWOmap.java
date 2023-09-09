package com.atlas.uwomap;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.SwingConstants;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Creates a multi-user login interface 
 * 
 * @version 1.0
 * @author Nandhitha Krishnan
 * @author Alysha Aul
 * @author Hirbod Hosseini
 * @author Jacqueline Kahnert
 * @author Ramtin Sirjani
 */
public class UWOmap extends JFrame implements ActionListener{
    /** Used to test credentials and launch app*/
    JButton LoginButton = new JButton();
    /** Used view what is being typed in password text*/
    JButton ShowPassword = new JButton();
    /** opens a JFrame that prompts user for e-mail in exchange for login info*/
    JButton ForgotPassword = new JButton();
    /** Opens a JFram where user can create a new account*/
    JButton SignUp = new JButton();
    /** Type in password here*/
    JPasswordField Password = new JPasswordField();
    /** Type in username here*/
    JTextField Username = new JTextField();
    /** Password character for hiding password*/
    char dots;
    ImageIcon EyeImageIcon;
    ImageIcon ClosedEyeImageIcon;
    JPanel screen = new JPanel();
    
    /** 
     * Constructor
     *
     * Makes JFrame with relevant components to log in.
     */
    private UWOmap() throws IOException{
        //import images 
        ImageIcon image = new ImageIcon("W.png");
        ImageIcon Tin = new ImageIcon("Tin.gif");
        ImageIcon Servos = new ImageIcon("servos profile3.png");
        ImageIcon ServosR = new ImageIcon("servosreverse.png");
        
        //set colour and panel
        screen.setBackground(new Color(79,38,131));
        screen.setLayout(null);
        this.getContentPane().add(screen);
        
        //Login button
        LoginButton.setBounds(200,375,100,50);
        LoginButton.setText("Login");
        LoginButton.setFont(new Font("calibri", Font.BOLD, 30));
        LoginButton.addActionListener(this);
        screen.add(LoginButton);
        
        //Show Password Button
        ShowPassword.setBounds(405,316,25,20);
        ShowPassword.addActionListener(this);
        ShowPassword.setOpaque(false);
        ShowPassword.setContentAreaFilled(false);
        ShowPassword.setBorderPainted(false);
        BufferedImage EyeImage = ImageIO.read(new File("passwordEyeOpen.png"));
        Image scaledEyeImage = EyeImage.getScaledInstance(ShowPassword.getWidth(), ShowPassword.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage ClosedEyeImage = ImageIO.read(new File("ClosedEyeImage.png"));
        Image scaledClosedEyeImage = ClosedEyeImage.getScaledInstance(ShowPassword.getWidth(), ShowPassword.getHeight(), Image.SCALE_SMOOTH);
        EyeImageIcon = new ImageIcon(scaledEyeImage);
        ClosedEyeImageIcon = new ImageIcon(scaledClosedEyeImage);
        ShowPassword.setIcon(EyeImageIcon);
        screen.add(ShowPassword);
        
        //Forgot Password button
        ForgotPassword.setBounds(350,445,150,20);
        ForgotPassword.setText("Forgot Password");
        ForgotPassword.setFont(new Font("calibri", Font.BOLD, 12));
        ForgotPassword.setForeground(Color.white);
        ForgotPassword.setOpaque(false);
        ForgotPassword.setContentAreaFilled(false);
        ForgotPassword.setBorderPainted(false);
        ForgotPassword.addActionListener(this);
        screen.add(ForgotPassword);
        
        //SignUp button
        SignUp.setBounds(0,445,100,20);
        SignUp.setHorizontalAlignment(SwingConstants.LEFT);
        SignUp.setText("Sign Up");
        SignUp.setFont(new Font("calibri", Font.BOLD, 12));
        SignUp.setForeground(Color.white);
        SignUp.setOpaque(false);
        SignUp.setContentAreaFilled(false);
        SignUp.setBorderPainted(false);
        SignUp.addActionListener(this);
        screen.add(SignUp);
        
        //Text Fields for input
        Username.setBounds(100, 200, 300,50);
        Password.setBounds(100, 300, 300,50);
        Username.setFont(new Font("Calibri",Font.PLAIN, 28));
        Password.setFont(new Font("Calibri",Font.PLAIN, 28));
        Username.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Password.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dots = Password.getEchoChar();
        screen.add(Username);
        screen.add(Password);
        
        //LOGO
        JLabel Logo = new JLabel(Tin);
        Logo.setBounds(200,25,120,100);
        screen.add(Logo);
        
        //Username Text
        JLabel username_text = new JLabel();
        username_text.setText("Username");
        username_text.setBounds(185, 160, 300, 50);
        username_text.setFont(new Font("Calibri",Font.BOLD, 25));
        username_text.setForeground(Color.WHITE);
        screen.add(username_text);
        
        //Password Text
        JLabel password_text = new JLabel();
        password_text.setText("Password");
        password_text.setBounds(185, 260, 300, 50);
        password_text.setFont(new Font("Calibri",Font.BOLD, 25));
        password_text.setForeground(Color.white);
        screen.add(password_text);
        
        //Servos
        JLabel servosLabel = new JLabel(Servos);
        servosLabel.setBounds(350,1,150,200);
        screen.add(servosLabel);
        
        //ServosReverse
        JLabel servosreverse = new JLabel(ServosR);
        servosreverse.setBounds(1,1,150,200);
        screen.add(servosreverse);
        
        //Set JFrame Properties
        this.setVisible(true);
        this.setTitle("Login - UWO Map Viewer"); //Sets title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(image.getImage());//Change Icon of frame
        this.setResizable(false);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
    }
    
    /** 
     * Main Method, Runs Log-in frame on software start.
     */
    public static void main(String[] args) throws Exception {
        new UWOmap();              
    }
    
    
    /** 
     * Algorithms for button actions.
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == LoginButton){
            try {
                BufferedReader reader;
                reader = new BufferedReader(new FileReader("Users.txt"));
                String line = reader.readLine();
                String User = "";
                boolean Exists = false;
                String passText = new String (Password.getPassword());
                while (line != null){
                    String line_array[] = line.split(",");
                    if(Username.getText().equals(line_array[0]) && passText.equals(line_array[1])){
                        Exists = true;
                        User = Username.getText();
                    }
                    // read next line
                    if(Exists == true){
                        break;
                    }
                    line = reader.readLine();
                }
                reader.close();
                if(Exists == true){
                    if(User.equals("DEV")){
                        MainGUI uwoMAP = new MainGUI("BuiltinPOIs.txt");
                        uwoMAP.setVisible(true);
                        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                    }
                    else{
                        MainGUI uwoMAP = new MainGUI(User + "POIs.txt");
                        uwoMAP.setVisible(true);
                        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                    }
                }
                else{
                    JLabel message = new JLabel("Invalid Username or Password");
                    message.setForeground(Color.WHITE);
                    message.setBounds(157, 313, 200, 100);
                    screen.add(message);
                    screen.repaint();
                }
            } catch (HeadlessException ex) {
                Logger.getLogger(UWOmap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(UWOmap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource() == ShowPassword){
            if(Password.getEchoChar() != (char)0){
                Password.setEchoChar((char)0);
                ShowPassword.setIcon(ClosedEyeImageIcon);
                screen.repaint();
            }
            else{
                Password.setEchoChar(dots);
                ShowPassword.setIcon(EyeImageIcon);
                screen.repaint();
            }
        }
        
        if (e.getSource() == ForgotPassword){
            JLabel message = new JLabel(); //result of user/password search
            JFrame Dialogue_box = new JFrame();
            Dialogue_box.setLocationRelativeTo(null);
            Dialogue_box.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            Dialogue_box.setSize(400,110);
            Dialogue_box.setResizable(false);
            Dialogue_box.setVisible(true);
            JPanel panel = new JPanel();
            panel.setLayout(null);
            Dialogue_box.add(panel);
            //Email Text
            JTextField Email = new JTextField();
            Email.setBounds(140, 7, 230,30);
            panel.add(Email);
            JLabel Email_text = new JLabel();
            Email_text.setText("E-mail:");
            Email_text.setBounds(79, 5, 300, 30);
            Email_text.setFont(new Font("Calibri",Font.BOLD, 14));
            panel.add(Email_text);
            //Sign-Up Button (write user to file)
            JButton showPass = new JButton();
            showPass.setBounds(200, 41, 180, 25);
            showPass.setText("Show Account Info");
            panel.add(showPass);
            showPass.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    try {
                        BufferedReader reader;
                        reader = new BufferedReader(new FileReader("Users.txt"));
                        String line = reader.readLine();
                        boolean Exists = false;
                        String user = "";
                        String pass = "";
                        while (line != null) {
                            String line_array[] = line.split(",");
                            if(Email.getText().equals(line_array[2])){
                                Exists = true;
                                user = line_array[0];
                                pass = line_array[1];
                            }
                            // read next line
                            if(Exists == true){
                                break;
                            }
                            line = reader.readLine();
                        }
                        if(Exists == true){
                            message.setText("<html>Username: "+ user + "<br>Password: " + pass + "</html>");
                            message.setBounds(5,0,300,100);
                            panel.add(message);
                            panel.repaint();
                        }
                        else{
                            message.setText("<html>No Account<br>Associated with E-mail</html>");
                            message.setBounds(5,0,300,100);
                            panel.add(message);
                            panel.repaint();
                        }
                        reader.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(UWOmap.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(UWOmap.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } );
        }
        
        if (e.getSource() == SignUp){
            //SignUp window
            JFrame Dialogue_box = new JFrame();
            Dialogue_box.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            Dialogue_box.setLocationRelativeTo(null);
            Dialogue_box.setSize(400,200);
            Dialogue_box.setResizable(false);
            Dialogue_box.setVisible(true);
            JPanel panel = new JPanel();
            panel.setLayout(null);
            Dialogue_box.add(panel);
                       
            //Enter Username Password and Email
            JTextField Username = new JTextField();
            Username.setBounds(120, 10, 230,30);
            JTextField Password = new JTextField();
            Password.setBounds(120, 50, 230,30);
            JTextField Email = new JTextField();
            Email.setBounds(120, 90, 230,30);
            panel.add(Username);
            panel.add(Password);
            panel.add(Email);
            
            //Username Text
            JLabel username_text = new JLabel();
            username_text.setText("Username:");
            username_text.setBounds(20, 8, 300, 30);
            username_text.setFont(new Font("Calibri",Font.BOLD, 14));
            panel.add(username_text);

            //Password Text
            JLabel password_text = new JLabel();
            password_text.setText("Password:");
            password_text.setBounds(20, 48, 300, 30);
            password_text.setFont(new Font("Calibri",Font.BOLD, 14));
            panel.add(password_text);
            
            //Email Text
            JLabel Email_text = new JLabel();
            Email_text.setText("E-mail:");
            Email_text.setBounds(39, 88, 300, 30);
            Email_text.setFont(new Font("Calibri",Font.BOLD, 14));
            panel.add(Email_text);
            
            //Sign-Up Button (write user to file)
            JButton signup = new JButton();
            signup.setBounds(170, 130, 130, 25);
            signup.setText("Create Account");
            panel.add(signup);
            JLabel message = new JLabel();
            signup.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e) { 
                    //Was anything input in the text fields?
                    if(Username.getText().equals("") || Password.getText().equals("") || Email.getText().equals("")){
                            message.setText("Empty Field. Try again!");
                            message.setBounds(10, 92, 150, 100);
                            panel.add(message);
                            panel.repaint();
                        }
                    //Check if username/Email exists
                    else{
                        try {
                            BufferedReader reader;
                            reader = new BufferedReader(new FileReader("Users.txt"));
                            String line = reader.readLine();
                            boolean Exists = false;
                            while (line != null) {
                                String line_array[] = line.split(",");
                                if(Username.getText().equals(line_array[0]) || Email.getText().equals(line_array[2])){
                                    Exists = true;
                                }
                                // read next line
                                if(Exists == true){
                                    break;
                                }
                                line = reader.readLine();
                            }
                            reader.close();
                            //If it exists show error
                            if(Exists == true){
                                message.setText("Username/E-mail Taken.");
                                message.setBounds(10, 92, 180, 100);
                                panel.add(message);
                                panel.repaint();
                            }
                            else{
                                StringBuilder userInput = new StringBuilder();
                                userInput.append(Username.getText()).append(",");
                                userInput.append(Password.getText()).append(",");
                                userInput.append(Email.getText()).append(",User\n");
                                try {
                                    Files.write(Paths.get("Users.txt"), userInput.toString().getBytes(), StandardOpenOption.APPEND);
                                    File myObj = new File(Username.getText() + "POIs.txt");
                                    File myObj1 = new File(Username.getText() + "Favorites.txt");
                                    myObj.createNewFile();
                                    myObj1.createNewFile();
                                } catch (IOException ex) {
                                    Logger.getLogger(UWOmap.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Dialogue_box.dispatchEvent(new WindowEvent(Dialogue_box, WindowEvent.WINDOW_CLOSING));
                            }
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(UWOmap.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(UWOmap.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } );                 
        }
    }
}
