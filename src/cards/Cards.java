/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
/**
 *
 * @author hofmannb
 */
public class Cards implements Runnable, ActionListener{

    /**
     * @param args the command line arguments
     */
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newgameMenuItem;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem exitMenuItem;
    MainPane mp=new MainPane();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Cards());
        
    }

    @Override
    public void run() {
        frame=new JFrame("Cards");
        menuBar=new JMenuBar();
        gameMenu=new JMenu("Game");
        newgameMenuItem=new JMenuItem("New Game");
        newgameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newgameMenuItem.addActionListener(this);
        undoMenuItem=new JMenuItem("Undo");
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undoMenuItem.addActionListener(this);
        redoMenuItem=new JMenuItem("Redo");
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        redoMenuItem.addActionListener(this);
        saveMenuItem=new JMenuItem("Save");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(this);
        openMenuItem=new JMenuItem("Open");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openMenuItem.addActionListener(this);
        exitMenuItem=new JMenuItem("Exit");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitMenuItem.addActionListener(this);
        
        gameMenu.add(newgameMenuItem);
        gameMenu.add(undoMenuItem);
        gameMenu.add(redoMenuItem);
        gameMenu.add(saveMenuItem);
        gameMenu.add(openMenuItem);
        gameMenu.add(exitMenuItem);
        menuBar.add(gameMenu);
        
        frame.setJMenuBar(menuBar);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280,800);
        frame.setMinimumSize(new Dimension(1280,800));
        
        frame.add(mp);
        frame.setVisible((true));    
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd=ae.getActionCommand();
        if(cmd=="New Game"){
            
            frame.remove(mp);
            mp=new MainPane();
            frame.add(mp);
            frame.setVisible(true);
        }
        if(cmd=="Undo"){
            mp.Undo();
        }
        if(cmd=="Redo"){
            mp.Redo();
        }
        if(cmd=="Save"){
            
            JFileChooser jf=new JFileChooser("."+File.separator+"saves");
            if(jf.showSaveDialog(jf)==JFileChooser.APPROVE_OPTION){
                FileOutputStream fos=null;
                try {
                    File f=jf.getSelectedFile();
                    fos = new FileOutputStream(f);
                    ObjectOutputStream oos=new ObjectOutputStream(fos);
                    oos.writeObject(mp);
                    fos.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Cards.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Cards.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Cards.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        }
        if(cmd=="Open"){
            JFileChooser jf=new JFileChooser("."+File.separator+"saves");
            if(jf.showOpenDialog(jf)==JFileChooser.APPROVE_OPTION){
                FileInputStream fis=null;
                try{
                    File f=jf.getSelectedFile();
                    fis = new FileInputStream(f);
                    ObjectInputStream ois=new ObjectInputStream(fis);
                    MainPane obj=(MainPane) ois.readObject();
                    mp.piles=obj.piles;
                    mp.talons=obj.talons;
                    mp.redos=obj.redos;
                    mp.undos=obj.undos;
                    
                    
                    
                    mp.rePos();
                } catch (IOException ex) {
                    Logger.getLogger(Cards.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Cards.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        if(cmd=="Exit"){
            System.exit(0);
        }
    }
    
}
