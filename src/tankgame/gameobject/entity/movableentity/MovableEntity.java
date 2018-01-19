package tankgame.gameobject.entity.movableentity;

import tankgame.gameobject.entity.*;
import tankgame.*;

public abstract class MovableEntity extends Entity {
  private int speed;
  public static final int shiftDirection = 6;
  private int direction;

  public MovableEntity( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.speed = 5;
    this.direction = 180;
  }

  public MovableEntity( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int speed, int direction ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.speed = speed;
    this.direction = direction;
  }

  public int getSpeed( ) {
    return this.speed;
  }

  public int getDirection( ) {
    return this.direction;
  }

  public void setSpeed( int newSpeed ) {
    this.speed = newSpeed;
  }

  public void move( int direction ) {
    int newXCoordinate = ( int ) ( this.speed * direction * Math.sin( Math.toRadians( this.direction + 90 ) ) );
    int newYCoordinate = ( int ) ( this.speed * direction * Math.cos( Math.toRadians( this.direction + 90 ) ) );
    this.setCoordinates( newXCoordinate + this.getXCoordinate(), newYCoordinate + this.getYCoordinate() );
    this.updateHitBox();
  }

  public void setDirection( int changeDirection ) {
    this.direction = changeDirection;
  }

}
