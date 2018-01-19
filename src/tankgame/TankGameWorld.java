package tankgame;

import tankgame.gameobject.*;
import tankgame.gameobject.entity.*;
import tankgame.gameobject.entity.movableentity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class TankGameWorld extends JPanel implements Observer {
  private static TankGameWorld tankGameWorld;
  private Thread thread;

  private int[][] levelArray;
  private boolean isRunning;
  private GameClock clock = new GameClock();

  private Tank playerOne;
  private Tank playerTwo;

  private ArrayList<IndestructibleWall> indestructibleWallList;
  private ArrayList<DestructibleWall> destructibleWallList;
  private ArrayList<Projectile> projectileList;
  private ArrayList<Tank> playerList;

  private KeyboardControl playerOneController;
  private KeyboardControl playerTwoController;

  private Animation bigExplosion;

  private BufferedImage mapImage;
  private Graphics2D mapGraphics;
  private BufferedImage playerOneView;
  private BufferedImage playerTwoView;


  private TankGameWorld( ) {
    super();

    this.loadFiles();

    this.setUpGameClock();

    this.setUpGameObjectLists();

    this.createGameObjects();
  }

  private void loadFiles( ) {
    Assets.initialize();
    this.levelArray = Assets.levelFileArray;
  }

  private void setUpGameClock( ) {
    this.isRunning = true;
    this.clock.addObserver( this );
    this.thread = new Thread( this.clock );
  }

  private void setUpGameObjectLists( ) {
    this.playerList = new ArrayList<Tank>();
    this.indestructibleWallList = new ArrayList<IndestructibleWall>();
    this.destructibleWallList = new ArrayList<DestructibleWall>();
    this.projectileList = new ArrayList<Projectile>();
  }

  private void createGameObjects( ) {
    this.createGameControllers();
    this.createProjectile();

    IndestructibleWall indestructibleWall;
    DestructibleWall destructibleWall;

    for ( int mapWidthIndex = 0; mapWidthIndex < this.levelArray[0].length; mapWidthIndex++ ) {
      for ( int mapLengthIndex = 0; mapLengthIndex < this.levelArray.length; mapLengthIndex++ ) {
        if ( this.levelArray[mapLengthIndex][mapWidthIndex] == 1 ) {
          indestructibleWall = new IndestructibleWall(
              mapWidthIndex * 32,
              mapLengthIndex * 32,
              Assets.indestructibleWall
          );
          indestructibleWall.setIsShowing( true );
          this.indestructibleWallList.add( indestructibleWall );


        } else if ( this.levelArray[mapLengthIndex][mapWidthIndex] == 2 ) {
          destructibleWall = new DestructibleWall(
              mapWidthIndex * 32,
              mapLengthIndex * 32,
              Assets.destructibleWall
          );
          destructibleWall.setIsShowing( true );
          this.clock.addObserver( destructibleWall );
          this.destructibleWallList.add( destructibleWall );


        } else if ( this.levelArray[mapLengthIndex][mapWidthIndex] == 3 ) {
          this.playerOne = new Tank( mapWidthIndex * 32, mapLengthIndex * 32, Assets.tankBlueHeavy );
          this.playerOne.setIsShowing( true );
          this.playerList.add( playerOne );
          this.clock.addObserver( playerOne );


        } else if ( this.levelArray[mapLengthIndex][mapWidthIndex] == 4 ) {
          this.playerTwo = new Tank( mapWidthIndex * 32, mapLengthIndex * 32, Assets.tankRedHeavy );
          this.playerTwo.setIsShowing( true );
          this.playerList.add( playerTwo );
          this.clock.addObserver( playerTwo );
        }
      }
    }
    this.mapImage = new BufferedImage( Assets.MAPWIDTH, Assets.MAPHEIGHT, BufferedImage.TYPE_INT_ARGB );
    this.bigExplosion = new Animation( 0, 0, Assets.bigExplosion );
    this.clock.addObserver( this.bigExplosion );
  }

  private void createGameControllers( ) {
    this.playerOneController = new KeyboardControl( Assets.playerOneControls );
    this.playerOneController.addObserver( this );
    this.playerTwoController = new KeyboardControl( Assets.playerTwoControls );
    this.playerTwoController.addObserver( this );
  }

  private void createProjectile( ) {
    Bullet playerOneProjectile;
    Bullet playerTwoProjectile;

    for ( int numberOfBulletsCreated = 1; numberOfBulletsCreated <= 5; numberOfBulletsCreated++ ) {
      playerOneProjectile = new Bullet( 0, 0, Assets.heavyShell, 10, 0, 1, 1 );
      this.clock.addObserver( playerOneProjectile );
      this.projectileList.add( playerOneProjectile );

      playerTwoProjectile = new Bullet( 0, 0, Assets.heavyShell, 10, 0, 2, 1 );
      this.clock.addObserver( playerTwoProjectile );
      this.projectileList.add( playerTwoProjectile );
    }


  }

  private void checkPlayerKeyPress( Tank player, Tank otherPlayer, KeyboardControl playerController ) {
    if ( playerController.isLeft() ) {
      player.moveLeft();
      checkCollisionOfPlayers( player, otherPlayer );
      checkCollisionOfTanks( player );
    }
    if ( playerController.isUp() ) {
      player.moveUp();
      checkCollisionOfPlayers( player, otherPlayer );
      checkCollisionOfTanks( player );
    }
    if ( playerController.isRight() ) {
      player.moveRight();
      checkCollisionOfPlayers( player, otherPlayer );
      checkCollisionOfTanks( player );
    }
    if ( playerController.isDown() ) {
      player.moveDown();
      checkCollisionOfPlayers( player, otherPlayer );
      checkCollisionOfTanks( player );
    }
    if ( playerController.isFire() ) {
      fireBullet( player );
      playerController.setIsFire( false );
    }
  }

  private void checkPlayerKeyPress( ) {
    if ( this.playerOne.getIsShowing() ) {
      checkPlayerKeyPress( this.playerOne, this.playerTwo, this.playerOneController );
    }
    if ( this.playerTwo.getIsShowing() ) {
      checkPlayerKeyPress( this.playerTwo, this.playerOne, this.playerTwoController );
    }
  }

  private void fireBullet( Tank player ) {
    for ( Projectile projectile : this.projectileList ) {
      if ( !projectile.getIsShowing() && projectile.getPlayerNumber() ==
          player.getIdentificationNumber() ) {
        projectile.setCoordinates(
            player.getXCoordinate() + Assets.TANKBARRELOFFSET,
            player.getYCoordinate() + Assets.TANKBARRELOFFSET
        );
        projectile.updateHitBox();
        projectile.setDirection( player.getDirection() );
        projectile.setCurrentGameObjectImage(
            projectile.getGameObjectSprite().getSpecificSpriteFrame(
                ( ( player.getDirection() + 180 ) % 360 ) / 6 ) );
        projectile.setIsShowing( true );
        return;
      }
    }
  }

  private void checkCollisionOfPlayers( Tank collider, Tank collided ) {
    if ( collider == collided ) {
      return;
    }
    if ( collider.getIsShowing() &&
        collided.getIsShowing() &&
        collider.getHitBox().intersects( collided.getHitBox() ) ) {
      collider.collidingEntities( collided );
    }
  }

  private void checkCollisionOfTanks( Tank tank ) {
    this.checkCollisionOfDestructibleWall( tank );
    this.checkCollisionOfIndestructibleWall( tank );
  }

  private void checkCollisionOfBullets( ) {
    for ( Projectile projectile : projectileList ) {
      if ( projectile.getIsShowing() ) {
        this.checkCollisionOfIndestructibleWall( projectile );
        this.checkCollisionOfDestructibleWall( projectile );
        this.checkCollisionOfTankAndProjectile( projectile );
      }
    }
  }

  private void checkCollisionOfIndestructibleWall( MovableEntity movableEntity ) {
    for ( IndestructibleWall indestructibleWall : indestructibleWallList ) {
      if ( movableEntity.getIsShowing() &&
          movableEntity.getHitBox().intersects( indestructibleWall.getHitBox() ) ) {
        this.bigExplosion.setCoordinates(
            movableEntity.getXCoordinate(),
            movableEntity.getYCoordinate()
        );
        this.bigExplosion.setIsShowing( true );
        if ( movableEntity.equals( this.playerOne ) || movableEntity.equals( this.playerTwo ) ) {
          this.bigExplosion.setIsShowing( false );
        }
        movableEntity.collidingEntities( indestructibleWall );
      }
    }
  }

  private void checkCollisionOfDestructibleWall( MovableEntity movableEntity ) {
    for ( DestructibleWall destructibleWall : destructibleWallList ) {
      if ( destructibleWall.getIsShowing() &&
          movableEntity.getIsShowing() &&
          movableEntity.getHitBox().intersects( destructibleWall.getHitBox() ) ) {

        this.bigExplosion.setCoordinates(
            movableEntity.getXCoordinate(),
            movableEntity.getYCoordinate()
        );
        this.bigExplosion.setIsShowing( true );
        if ( movableEntity.equals( this.playerOne ) || movableEntity.equals( this.playerTwo ) ) {
          this.bigExplosion.setIsShowing( false );
        }
        movableEntity.collidingEntities( destructibleWall );
      }
    }
  }

  private void checkCollisionOfTankAndProjectile( Projectile projectile ) {
    if ( this.playerOne.getIdentificationNumber() != projectile.getPlayerNumber() &&
        this.playerOne.getIsShowing() &&
        projectile.getIsShowing() &&
        this.playerOne.getHitBox().intersects( projectile.getHitBox() ) ) {
      this.bigExplosion.setCoordinates(
          this.playerOne.getXCoordinate(),
          this.playerOne.getYCoordinate()
      );
      this.bigExplosion.setIsShowing( true );
      playerOne.collidingEntities( projectile );
    } else if ( this.playerTwo.getIdentificationNumber() != projectile.getPlayerNumber() &&
        this.playerTwo.getIsShowing() &&
        projectile.getIsShowing() &&
        this.playerTwo.getHitBox().intersects( projectile.getHitBox() ) ) {
      this.bigExplosion.setIsShowing( true );
      this.bigExplosion.setCoordinates(
          this.playerTwo.getXCoordinate(),
          this.playerTwo.getYCoordinate()
      );
      this.playerTwo.collidingEntities( projectile );
    }
  }

  private void updatePlayerNonCollidingLocation( ) {
    for ( Tank player : this.playerList ) {
      if ( !( player.equals( this.playerOne ) ) &&
          ( !( player.getHitBox().intersects( playerOne.getHitBox() ) ) ) ) {
        player.setNonCollidingLocation( player.getHitBox().getLocation() );
      } else if ( !( player.equals( this.playerTwo ) ) &&
          ( !( player.getHitBox().intersects( this.playerTwo.getHitBox() ) ) ) ) {
        player.setNonCollidingLocation( player.getHitBox().getLocation() );
      }
    }

  }

  public static TankGameWorld getInstance( ) {
    if ( tankGameWorld == null ) {
      tankGameWorld = new TankGameWorld();
    }
    return tankGameWorld;
  }

  public boolean isGameRunning( ) {
    return this.isRunning;
  }

  public Thread getThread( ) {
    return this.thread;
  }

  public KeyboardControl getPlayerOneController( ) {
    return this.playerOneController;
  }

  public KeyboardControl getPlayerTwoController( ) {
    return this.playerTwoController;
  }

  public synchronized void stop( ) {
    if ( !this.isRunning ) {
      return;
    }
    this.isRunning = false;
    this.clock.deleteObservers();
    this.playerOneController.deleteObservers();
    this.playerTwoController.deleteObservers();
  }

  private void drawBackgroundImage( Graphics2D graphics2D ) {
    for ( int widthPixelIndex = 0; widthPixelIndex < Assets.MAPWIDTH; widthPixelIndex += Assets.background.getWidth() ) {
      for ( int heightPixelIndex = 0; heightPixelIndex < Assets.MAPHEIGHT; heightPixelIndex += Assets.background.getHeight() ) {
        graphics2D.drawImage(
            Assets.background,
            widthPixelIndex,
            heightPixelIndex,
            Assets.background.getWidth(),
            Assets.background.getHeight(),
            null
        );
      }
    }
  }

  private void drawDestructibleWallImage( Graphics2D graphics2D ) {
    for ( DestructibleWall destructibleWall : this.destructibleWallList ) {
      if ( destructibleWall.getIsShowing() &&
          this.playerOne.getHitBox().intersects( destructibleWall.getHitBox() ) ||
          destructibleWall.getIsShowing() &&
              this.playerTwo.getHitBox().intersects( destructibleWall.getHitBox() ) ) {
        destructibleWall.setIsShowing( false );
      } else if ( destructibleWall.getIsShowing() ) {
        graphics2D.drawImage(
            destructibleWall.getCurrentGameObjectImage(),
            destructibleWall.getXCoordinate(),
            destructibleWall.getYCoordinate(),
            destructibleWall.getWidth(),
            destructibleWall.getLength(),
            null
        );
      }
    }
  }

  private void drawIndestructibleWallImage( Graphics2D graphics2D ) {
    for ( IndestructibleWall indestructibleWall : this.indestructibleWallList ) {
      graphics2D.drawImage(
          indestructibleWall.getCurrentGameObjectImage(),
          indestructibleWall.getXCoordinate(),
          indestructibleWall.getYCoordinate(),
          indestructibleWall.getWidth(),
          indestructibleWall.getLength(),
          null
      );
    }
  }

  private void drawBulletsImage( Graphics2D graphics2D ) {
    for ( Projectile projectile : this.projectileList ) {
      if ( projectile.getIsShowing() ) {
        graphics2D.drawImage( projectile.getCurrentGameObjectImage(),
            projectile.getXCoordinate(),
            projectile.getYCoordinate(),
            projectile.getWidth(), projectile.getLength(),
            null );
      }
    }
  }

  private void drawPlayersImage( Graphics2D graphics2D ) {
    for ( Tank player : this.playerList ) {
      if ( player.getIsShowing() ) {
        graphics2D.drawImage( player.getCurrentGameObjectImage(),
            player.getXCoordinate(),
            player.getYCoordinate(),
            player.getWidth(),
            player.getLength(),
            null );
        player.setIsShowing( true );
      }
    }
  }

  private int playerViewXOffset( Tank player ) {
    int xOffset = ( player.getXCoordinate() + ( player.getWidth() / 2 - ( Assets.WINDOWWIDTH / 2 ) ) );
    if ( player.getXCoordinate() + xOffset <= 0 ) {
      return 0;
    } else if ( player.getXCoordinate() + xOffset > 0 &&
        player.getXCoordinate() + xOffset < this.mapImage.getWidth() + Assets.WINDOWWIDTH / 4 ) {
      return ( player.getXCoordinate() + xOffset ) / 2;
    } else if ( player.getXCoordinate() + xOffset >= this.mapImage.getWidth() + Assets.WINDOWWIDTH / 4 ) {
      return this.mapImage.getWidth() - Assets.WINDOWWIDTH / 2;
    }
    return 0;
  }

  private int playerViewYOffset( Tank player ) {
    int yOffset = ( player.getYCoordinate() + ( player.getLength() / 2 ) - ( Assets.WINDOWHEIGHT ) );
    if ( player.getYCoordinate() + yOffset <= 0 ) {
      return 0;
    } else if ( player.getYCoordinate() + yOffset > 0 &&
        player.getYCoordinate() + yOffset < this.mapImage.getHeight() - Assets.WINDOWHEIGHT ) {
      return ( player.getYCoordinate() + yOffset );
    } else if ( player.getYCoordinate() + yOffset >= this.mapImage.getHeight() - Assets.WINDOWHEIGHT ) {
      return this.mapImage.getHeight() - Assets.WINDOWHEIGHT;
    }
    return 0;
  }

  private void drawMap( ) {
    this.mapGraphics = this.mapImage.createGraphics();
    drawBackgroundImage( this.mapGraphics );
    drawDestructibleWallImage( this.mapGraphics );
    drawIndestructibleWallImage( this.mapGraphics );
    drawBulletsImage( this.mapGraphics );
    drawPlayersImage( this.mapGraphics );
    if ( this.bigExplosion.getIsShowing() ) {
      this.mapGraphics.drawImage(
          this.bigExplosion.getCurrentGameObjectImage(),
          this.bigExplosion.getXCoordinate(),
          this.bigExplosion.getYCoordinate(),
          null
      );
    }
    this.mapGraphics.dispose();
  }

  private void drawPlayerSplitScreen( Graphics graphics ) {
    this.playerOneView = this.mapImage.getSubimage(
        playerViewXOffset( this.playerOne ),
        playerViewYOffset( this.playerOne ),
        Assets.WINDOWWIDTH / 2,
        Assets.WINDOWHEIGHT
    );
    graphics.drawImage( this.playerOneView, 0, 0, null );

    this.playerTwoView = this.mapImage.getSubimage(
        playerViewXOffset( this.playerTwo ),
        playerViewYOffset( this.playerTwo ),
        Assets.WINDOWWIDTH / 2,
        Assets.WINDOWHEIGHT
    );
    graphics.drawImage( this.playerTwoView, Assets.WINDOWWIDTH / 2, 0, null );

  }

  private void drawLives( Graphics graphics ) {
    if ( this.playerOne.getLives() > 0 ) {
      for ( int numberOfLives = this.playerOne.getLives(); numberOfLives > 0; numberOfLives-- ) {
        graphics.drawImage(
            Assets.ballStrip.getSpecificSpriteFrame( getHitPointColor( this.playerOne ) ),
            numberOfLives * 29 - 29,
            0,
            null
        );
      }
    }
    if ( this.playerTwo.getLives() > 0 ) {
      for ( int numberOfLives = this.playerTwo.getLives(); numberOfLives > 0; numberOfLives-- ) {
        graphics.drawImage(
            Assets.ballStrip.getSpecificSpriteFrame( getHitPointColor( this.playerTwo ) ),
            ( numberOfLives * 29 - 29 ) + ( Assets.WINDOWWIDTH / 2 ),
            0,
            null
        );
      }
    }
  }

  private int getHitPointColor( Tank player ) {
    if ( player.getHitPoints() >= 5 ) {
      return 7;
    } else if ( player.getHitPoints() == 4 ) {
      return 1;
    } else if ( player.getHitPoints() == 3 ) {
      return 2;
    } else if ( player.getHitPoints() == 2 ) {
      return 0;
    } else if ( player.getHitPoints() == 1 ) {
      return 6;
    } else {
      return 8;
    }
  }

  @Override
  public void paintComponent( Graphics graphics ) {
    super.paintComponent( graphics );
    drawMap();
    drawPlayerSplitScreen( graphics );
    drawLives( graphics );

    int xOffset = ( Assets.WINDOWWIDTH - Assets.MINIMAPWIDTH ) / 2;
    int yOffset = Assets.WINDOWHEIGHT - Assets.MINIMAPHEIGHT - 25;
    graphics.drawImage( this.mapImage.getScaledInstance(
        Assets.MINIMAPWIDTH, Assets.MINIMAPHEIGHT, 0 ),
        xOffset,
        yOffset,
        null
    );
    xOffset = ( Assets.WINDOWWIDTH - Assets.gameOver.getWidth() ) / 2;
    yOffset = Assets.WINDOWHEIGHT / 2;
    if ( this.playerOne.getLives() <= 0 || this.playerTwo.getLives() <= 0 ) {
      graphics.drawImage( Assets.gameOver, xOffset, yOffset, null );
    }
  }

  @Override
  public void update( Observable o, Object arg ) {
    if ( this.playerOne.getLives() <= 0 || this.playerTwo.getLives() <= 0 ) {
      repaint();
      stop();
    }
    this.checkPlayerKeyPress();
    this.checkCollisionOfBullets();
    this.repaint();
    this.updatePlayerNonCollidingLocation();
  }

}
