package tankgame.gameobject.entity;

import tankgame.*;
import tankgame.gameobject.*;
import tankgame.gameobject.entity.movableentity.*;

import java.awt.*;

public abstract class Entity extends GameObject {

  private Rectangle hitBox;

  public Entity( int xCoordinates, int yCoordinates, Sprite gameObjectSprite ) {
    super( xCoordinates, yCoordinates, gameObjectSprite );
    this.hitBox = new Rectangle( xCoordinates, yCoordinates, this.getWidth(), this.getLength() );
  }


  public Rectangle getHitBox( ) {
    return this.hitBox;
  }

  public void updateHitBox( ) {
    this.hitBox.setLocation( getXCoordinate(), getYCoordinate() );
  }

  public abstract void collidingEntities( Entity entityObject );

  public abstract void collidingEntities( IndestructibleWall indestructibleWall );

  public abstract void collidingEntities( DestructibleWall destructibleWall );

  public abstract void collidingEntities( Tank tank );

  public abstract void collidingEntities( Bullet bullet );

  public abstract void collidingEntities( Projectile projectile );

}
