package tankgame.gameobject.entity.movableentity;

import tankgame.*;
import tankgame.gameobject.IDestroy;

public abstract class Projectile extends MovableEntity implements IDestroy {
  int playerNumber;
  int damage;


  Projectile( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int playerNumber, int damage ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.playerNumber = playerNumber;
    this.damage = damage;
  }

  Projectile( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int speed, int direction, int playerNumber, int damage ) {
    super( xCoordinates, yCoordinates, gameObjectSprite, speed, direction );
    this.playerNumber = playerNumber;
    this.damage = damage;
  }

  public void setDamage( int damage ) {
    this.damage = damage;
  }

  public int getPlayerNumber( ) {
    return this.playerNumber;
  }

  public int getDamage( ) {
    return this.damage;
  }

}
