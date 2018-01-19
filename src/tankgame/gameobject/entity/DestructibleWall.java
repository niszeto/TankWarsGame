package tankgame.gameobject.entity;

import tankgame.*;
import tankgame.gameobject.IDestroy;
import tankgame.gameobject.entity.movableentity.*;

import java.util.Observable;
import java.util.Observer;

public class DestructibleWall extends Entity implements IDestroy, Observer {
  private int regenerationTime;

  public DestructibleWall( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.regenerationTime = 0;
  }

  @Override
  public void destroy( ) {
    this.setIsShowing( false );
  }

  @Override
  public void collidingEntities( Entity entityObject ) {

  }

  @Override
  public void collidingEntities( IndestructibleWall indestructibleWall ) {

  }

  @Override
  public void collidingEntities( DestructibleWall destructibleWall ) {

  }

  @Override
  public void collidingEntities( Tank tank ) {

  }

  @Override
  public void collidingEntities( Bullet bullet ) {

  }

  @Override
  public void collidingEntities( Projectile projectile ) {

  }

  @Override
  public void update( Observable o, Object arg ) {
    if ( !this.getIsShowing() ) {
      this.regenerationTime++;
      if ( this.regenerationTime >= 100 ) {
        this.regenerationTime = 0;
        this.setIsShowing( true );
      }
    }

  }
}
