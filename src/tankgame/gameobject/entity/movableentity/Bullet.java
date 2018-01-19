package tankgame.gameobject.entity.movableentity;

import tankgame.*;
import tankgame.gameobject.entity.*;

import java.util.Observable;
import java.util.Observer;

public class Bullet extends Projectile implements Observer {

  public Bullet( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int playerNumber, int damage ) {
    super( xCoordinates, yCoordinates, gameObjectSprite, playerNumber, damage );
  }

  public Bullet( int xCoordinates, int yCoordinates, Sprite gameObjectSprite, int speed, int direction, int playerNumber, int damage ) {
    super( xCoordinates, yCoordinates, gameObjectSprite, speed, direction, playerNumber, damage );
  }

  @Override
  public void collidingEntities( Entity entityObject ) {
    this.destroy();
  }

  @Override
  public void collidingEntities( IndestructibleWall indestructibleWall ) {
    this.destroy();
  }

  @Override
  public void collidingEntities( DestructibleWall destructibleWall ) {
    destructibleWall.destroy();
    this.destroy();
  }

  @Override
  public void collidingEntities( Tank tank ) {
    tank.setHitPoints( tank.getHitPoints() - this.getDamage() );
    this.destroy();
    System.out.println( tank.getHitPoints() );
    if ( tank.getHitPoints() - this.getDamage() < 0 ) {
      tank.destroy();
    }
  }

  @Override
  public void collidingEntities( Bullet bullet ) {

  }

  @Override
  public void collidingEntities( Projectile projectile ) {

  }

  @Override
  public void destroy( ) {
    this.setCoordinates( 0, 0 );
    this.updateHitBox();
    this.setIsShowing( false );
  }

  @Override
  public void update( Observable o, Object arg ) {
    if ( this.getIsShowing() ) {
      this.move( 1 );
    }
  }
}
