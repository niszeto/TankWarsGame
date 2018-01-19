package tankgame.gameobject.entity;

import tankgame.Sprite;
import tankgame.gameobject.entity.movableentity.*;

public class IndestructibleWall extends Entity {
  public IndestructibleWall( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
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
    bullet.setIsShowing( false );
  }

  @Override
  public void collidingEntities( Projectile projectile ) {
    projectile.setIsShowing( false );

  }
}
