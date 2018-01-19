package tankgame.gameobject.entity.movableentity;

import tankgame.gameobject.IDestroy;
import tankgame.gameobject.entity.DestructibleWall;
import tankgame.gameobject.entity.Entity;
import tankgame.*;
import tankgame.gameobject.entity.IndestructibleWall;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Tank extends MovableEntity implements IDestroy, Observer {
  public static int tanksCreated = 0;
  private int hitPoints;
  private int identificationNumber;
  private int regenerationTime;
  private int lives;
  private Point nonCollidingLocation;

  public Tank( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.nonCollidingLocation = new Point( this.getCoordinates() );
    this.identificationNumber = ++tanksCreated;
    this.hitPoints = 5;
    this.regenerationTime = 0;
    this.lives = 3;
  }

  public Tank( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int speed, int direction ) {
    super( xCoordinates, yCoordinates, gameObjectSprite, speed, direction );
    this.hitPoints = 5;
  }

  public Tank( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int speed, int direction, int hitPoints ) {
    super( xCoordinates, yCoordinates, gameObjectSprite, speed, direction );
    this.hitPoints = hitPoints;
  }

  public void moveUp( ) {
    this.move( 1 );
  }

  public void moveDown( ) {
    this.move( -1 );
  }

  public void moveLeft( ) {
    int newDirection = this.getDirection() + shiftDirection;
    setDirection( newDirection );
    int currentFrame = ( ( ( newDirection + 180 ) % 360 ) / 6 );
    this.setCurrentGameObjectImage( this.getGameObjectSprite().getSpecificSpriteFrame( currentFrame ) );
  }

  public void moveRight( ) {
    int newDirection = this.getDirection() - shiftDirection;
    if ( newDirection == 0 ) {
      newDirection = 360;
    }
    setDirection( newDirection );
    int currentFrame = ( ( ( newDirection + 180 ) % 360 ) / 6 );
    this.setCurrentGameObjectImage( this.getGameObjectSprite().getSpecificSpriteFrame( currentFrame ) );
  }

  public Point getNonCollidingLocation( ) {
    return this.nonCollidingLocation;
  }

  public int getLives( ) {
    return this.lives;
  }

  public int getHitPoints( ) {
    return this.hitPoints;
  }

  public void setHitPoints( int hitPoints ) {
    this.hitPoints = hitPoints;
  }

  public void setNonCollidingLocation( Point nonCollidingLocation ) {
    this.nonCollidingLocation = nonCollidingLocation;
  }

  public int getIdentificationNumber( ) {
    return this.identificationNumber;
  }

  @Override
  public void collidingEntities( Entity entityObject ) {
  }

  @Override
  public void collidingEntities( IndestructibleWall indestructibleWall ) {
    this.setCoordinates( ( int ) this.getNonCollidingLocation().getX(),
        ( int ) this.getNonCollidingLocation().getY() );
    this.updateHitBox();
  }

  @Override
  public void collidingEntities( DestructibleWall destructibleWall ) {
    this.setCoordinates( ( int ) this.getNonCollidingLocation().getX(),
        ( int ) this.getNonCollidingLocation().getY() );
    this.updateHitBox();

  }

  @Override
  public void collidingEntities( Tank tank ) {
    this.setCoordinates( ( int ) this.getNonCollidingLocation().getX(),
        ( int ) this.getNonCollidingLocation().getY() );
    this.updateHitBox();
  }

  @Override
  public void collidingEntities( Bullet bullet ) {

  }

  @Override
  public void collidingEntities( Projectile projectile ) {
    this.hitPoints -= projectile.getDamage();
    projectile.destroy();
    if ( this.hitPoints - projectile.getDamage() < 0 ) {
      this.destroy();
    }
  }


  @Override
  public void destroy( ) {
    this.setCoordinates(
        Assets.getTankSpawnXCoordinate( this.getIdentificationNumber() ),
        Assets.getTankSpawnYCoordinate( this.getIdentificationNumber() )
    );
    this.updateHitBox();
    this.lives--;
    this.setIsShowing( false );
    this.hitPoints = 5;
  }

  @Override
  public void update( Observable o, Object arg ) {
    if ( !this.getIsShowing() ) {
      this.regenerationTime++;
      if ( this.regenerationTime >= 125 ) {
        this.regenerationTime = 0;
        this.setCoordinates(
            Assets.getTankSpawnXCoordinate( this.getIdentificationNumber() ),
            Assets.getTankSpawnYCoordinate( this.getIdentificationNumber() )
        );
        this.updateHitBox();
        this.setIsShowing( true );
      }
    }
  }
}
