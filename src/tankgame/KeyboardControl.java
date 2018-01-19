package tankgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

public class KeyboardControl extends Observable implements KeyListener {
  private Assets.PlayerControls[] playerControls;
  private boolean[] keyCodes;
  private boolean isLeft;
  private boolean isUp;
  private boolean isRight;
  private boolean isDown;
  private boolean isFire;

  public KeyboardControl( Assets.PlayerControls[] playerControls ) {
    this.playerControls = playerControls;
    this.keyCodes = new boolean[256];
  }

  @Override
  public void keyTyped( KeyEvent e ) {

  }

  @Override
  public void keyPressed( KeyEvent e ) {
    keyCodes[e.getKeyCode()] = true;
    this.isLeft = keyCodes[playerControls[0].getKEYCODE()];
    this.isUp = keyCodes[playerControls[1].getKEYCODE()];
    this.isRight = keyCodes[playerControls[2].getKEYCODE()];
    this.isDown = keyCodes[playerControls[3].getKEYCODE()];
    this.isFire = keyCodes[playerControls[4].getKEYCODE()];

    setChanged();
    notifyObservers();
  }

  @Override
  public void keyReleased( KeyEvent e ) {
    keyCodes[e.getKeyCode()] = false;
    this.isLeft = keyCodes[playerControls[0].getKEYCODE()];
    this.isUp = keyCodes[playerControls[1].getKEYCODE()];
    this.isRight = keyCodes[playerControls[2].getKEYCODE()];
    this.isDown = keyCodes[playerControls[3].getKEYCODE()];
    this.isFire = keyCodes[playerControls[4].getKEYCODE()];

    setChanged();
    notifyObservers();
  }

  public boolean isLeft( ) {
    return this.isLeft;
  }

  public boolean isUp( ) {
    return this.isUp;
  }

  public boolean isRight( ) {
    return this.isRight;
  }

  public boolean isDown( ) {
    return this.isDown;
  }

  public boolean isFire( ) {
    return this.isFire;
  }

  public void setIsFire( boolean isFire ) {
    this.isFire = isFire;
  }
}