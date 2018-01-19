package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Launcher {
  public static void main( String[] args ) {
    TankGameWorld tankGame = TankGameWorld.getInstance();
    JFrame gameDisplayWindow = new JFrame( "Tank Wars" );
    gameDisplayWindow.add( tankGame );

    gameDisplayWindow.addKeyListener( tankGame.getPlayerOneController() );
    gameDisplayWindow.addKeyListener( tankGame.getPlayerTwoController() );

    gameDisplayWindow.setFocusable( true );
    gameDisplayWindow.requestFocusInWindow();

    gameDisplayWindow.pack();
    gameDisplayWindow.addWindowListener( new WindowAdapter() {
      public void windowGainedFocus( WindowEvent e ) {
        tankGame.requestFocusInWindow();
      }
    } );
    gameDisplayWindow.setSize( new Dimension( Assets.WINDOWWIDTH, Assets.WINDOWHEIGHT ) );
    gameDisplayWindow.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    gameDisplayWindow.setVisible( true );
    gameDisplayWindow.setFocusable( true );
    gameDisplayWindow.setResizable( false );
    gameDisplayWindow.setLocationRelativeTo( null );
    tankGame.getThread().start();
  }
}
