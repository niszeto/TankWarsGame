package tankgame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Assets {

  public static final int MAPWIDTH = 1248;
  public static final int MAPHEIGHT = 1309;

  public static final int WINDOWHEIGHT = 1000;
  public static final int WINDOWWIDTH = 1000;

  public static final int MINIMAPWIDTH = 300;
  public static final int MINIMAPHEIGHT = 300;

  public static final int TANKBARRELOFFSET = 20;

  public static final int PLAYERONEXCOORDINATERESPAWN = 544;
  public static final int PLAYERONEYCOORDINATERESPAWN = 64;

  public static final int PLAYERTWOXCOORDINATERESPAWN = 544;
  public static final int PLAYERTWOYCOORDINATERESPAWN = 1184;

  private static BufferedImage[] imagesShiftedArray = new BufferedImage[60];

  public static Sprite ballStrip;
  public static Sprite indestructibleWall;
  public static Sprite destructibleWall;
  public static Sprite basicShell;
  public static Sprite heavyShell;
  public static Sprite lightShell;
  public static Sprite tankBlueBase;
  public static Sprite tankBlueBasic;
  public static Sprite tankBlueHeavy;
  public static Sprite tankBlueLight;
  public static Sprite tankGreyBasic;
  public static Sprite tankGreyHeavy;
  public static Sprite tankGreyLight;
  public static Sprite tankRedBase;
  public static Sprite tankRedBasic;
  public static Sprite tankRedHeavy;
  public static Sprite tankRedLight;
  public static Sprite weaponStrip;

  public static int[][] levelFileArray;

  public static Sprite smallExplosion;
  public static Sprite bigExplosion;

  public static BufferedImage gameOver;
  public static BufferedImage background;


  public static final String BACKGROUNDIMAGE = "Tank/Resources/Tank Graphics/Background.png";
  public static final String BALLSTRIP = "Tank/Resources/Tank Graphics/Ball_strip9.png";
  public static final String DESTRUCTIBLEWALL = "Tank/Resources/Tank Graphics/Blue_wall2.png";
  public static final String INDESTRUCTIBLEWALL = "Tank/Resources/Tank Graphics/Blue_wall1.png";
  public static final String BASICSHELL = "Tank/Resources/Tank Graphics/Shell_basic_strip60.png";
  public static final String HEAVYSHELL = "Tank/Resources/Tank Graphics/Shell_heavy_strip60.png";
  public static final String LIGHTSHELL = "Tank/Resources/Tank Graphics/Shell_light_strip60.png";
  public static final String TANKBLUEBASE = "Tank/Resources/Tank Graphics/Tank_blue_base_strip60.png";
  public static final String TANKBLUEBASIC = "Tank/Resources/Tank Graphics/Tank_blue_basic_strip60.png";
  public static final String TANKBLUEHEAVY = "Tank/Resources/Tank Graphics/Tank_blue_heavy_strip60.png";
  public static final String TANKBLUELIGHT = "Tank/Resources/Tank Graphics/Tank_blue_light_strip60.png";
  public static final String TANKGREYBASIC = "Tank/Resources/Tank Graphics/Tank_grey_basic.png";
  public static final String TANKGREYHEAVY = "Tank/Resources/Tank Graphics/Tank_grey_heavy.png";
  public static final String TANKGREYLIGHT = "Tank/Resources/Tank Graphics/Tank_grey_light.png";
  public static final String TANKREDBASE = "Tank/Resources/Tank Graphics/Tank_red_base_strip60.png";
  public static final String TANKREDBASIC = "Tank/Resources/Tank Graphics/Tank_red_basic_strip60.png";
  public static final String TANKREDHEAVY = "Tank/Resources/Tank Graphics/Tank_red_heavy_strip60.png";
  public static final String TANKREDLIGHT = "Tank/Resources/Tank Graphics/Tank_red_light_strip60.png";
  public static final String WEAPONSTRIP = "Tank/Resources/Tank Graphics/Weapon_strip4.png";
  public static final String SMALLEXPLOSIONFRAMEONE = "Tank/Resources/Tank Graphics/explosion1_1.png";
  public static final String SMALLEXPLOSIONFRAMETWO = "Tank/Resources/Tank Graphics/explosion1_2.png";
  public static final String SMALLEXPLOSIONFRAMETHREE = "Tank/Resources/Tank Graphics/explosion1_3.png";
  public static final String SMALLEXPLOSIONFRAMEFOUR = "Tank/Resources/Tank Graphics/explosion1_4.png";
  public static final String SMALLEXPLOSIONFRAMEFIVE = "Tank/Resources/Tank Graphics/explosion1_5.png";
  public static final String SMALLEXPLOSIONFRAMESIX = "Tank/Resources/Tank Graphics/explosion1_6.png";
  public static final String BIGEXPLOSIONFRAMEONE = "Tank/Resources/Tank Graphics/explosion2_1.png";
  public static final String BIGEXPLOSIONFRAMETWO = "Tank/Resources/Tank Graphics/explosion2_2.png";
  public static final String BIGEXPLOSIONFRAMETHREE = "Tank/Resources/Tank Graphics/explosion2_3.png";
  public static final String BIGEXPLOSIONFRAMEFOUR = "Tank/Resources/Tank Graphics/explosion2_4.png";
  public static final String BIGEXPLOSIONFRAMEFIVE = "Tank/Resources/Tank Graphics/explosion2_5.png";
  public static final String BIGEXPLOSIONFRAMESIX = "Tank/Resources/Tank Graphics/explosion2_6.png";
  public static final String GAMEOVER = "Tank/Resources/Tank Graphics/gameover.png";
  public static final String LEVELFILE = "Tank/Resources/level.txt";


  public static final String[] smallExplosionImagePath = {
      SMALLEXPLOSIONFRAMEONE,
      SMALLEXPLOSIONFRAMETWO,
      SMALLEXPLOSIONFRAMETHREE,
      SMALLEXPLOSIONFRAMEFOUR,
      SMALLEXPLOSIONFRAMEFIVE,
      SMALLEXPLOSIONFRAMESIX
  };

  public static final String[] bigExplosionImagePath = {
      BIGEXPLOSIONFRAMEONE,
      BIGEXPLOSIONFRAMETWO,
      BIGEXPLOSIONFRAMETHREE,
      BIGEXPLOSIONFRAMEFOUR,
      BIGEXPLOSIONFRAMEFIVE,
      BIGEXPLOSIONFRAMESIX
  };

  public static BufferedImage[] smallExplosions = new BufferedImage[6];
  public static BufferedImage[] bigExplosions = new BufferedImage[6];

  public static void imagePathParser( String[] imagePath, BufferedImage[] imageLocation ) {
    BufferedImage importedImage = null;
    for ( int imageImportedIndex = 0; imageImportedIndex < imagePath.length; imageImportedIndex++ ) {
      try {
        importedImage = ImageIO.read( new File( imagePath[imageImportedIndex] ) );
      } catch ( IOException e ) {
        e.printStackTrace();
      }
      imageLocation[imageImportedIndex] = importedImage;
    }
  }

  public static void initialize( ) {
    imagePathParser( smallExplosionImagePath, smallExplosions );
    imagePathParser( bigExplosionImagePath, bigExplosions );
    try {
      ballStrip = new Sprite( BALLSTRIP, 29 );
      destructibleWall = new Sprite( DESTRUCTIBLEWALL, 32 );
      indestructibleWall = new Sprite( INDESTRUCTIBLEWALL, 32 );
      basicShell = new Sprite( BASICSHELL, 24 );
      heavyShell = new Sprite( HEAVYSHELL, 24 );
      lightShell = new Sprite( LIGHTSHELL, 24 );

      tankBlueBase = new Sprite( TANKBLUEBASE, 64 );
      tankBlueBase.setSpriteFrames( shiftContents( tankBlueBase.getSpriteFrames(), 30 ) );
      tankBlueBasic = new Sprite( TANKBLUEBASIC, 64 );
      tankBlueBasic.setSpriteFrames( shiftContents( tankBlueBasic.getSpriteFrames(), 30 ) );
      tankBlueHeavy = new Sprite( TANKBLUEHEAVY, 64 );
      tankBlueHeavy.setSpriteFrames( shiftContents( tankBlueHeavy.getSpriteFrames(), 30 ) );
      tankBlueLight = new Sprite( TANKBLUELIGHT, 64 );
      tankBlueLight.setSpriteFrames( shiftContents( tankBlueLight.getSpriteFrames(), 30 ) );

      tankGreyBasic = new Sprite( TANKGREYBASIC, 64 );
      tankGreyHeavy = new Sprite( TANKGREYHEAVY, 64 );
      tankGreyLight = new Sprite( TANKGREYLIGHT, 64 );

      tankRedBase = new Sprite( TANKREDBASE, 64 );
      tankRedBase.setSpriteFrames( shiftContents( tankRedBase.getSpriteFrames(), 30 ) );
      tankRedBasic = new Sprite( TANKREDBASIC, 64 );
      tankRedBasic.setSpriteFrames( shiftContents( tankRedBasic.getSpriteFrames(), 30 ) );
      tankRedHeavy = new Sprite( TANKREDHEAVY, 64 );
      tankRedHeavy.setSpriteFrames( shiftContents( tankRedHeavy.getSpriteFrames(), 30 ) );
      tankRedLight = new Sprite( TANKREDLIGHT, 64 );
      tankRedLight.setSpriteFrames( shiftContents( tankRedLight.getSpriteFrames(), 30 ) );

      weaponStrip = new Sprite( WEAPONSTRIP, 64 );
      levelFileArray = parseLevelFile( LEVELFILE );

      smallExplosion = new Sprite( smallExplosions, 32 );
      bigExplosion = new Sprite( bigExplosions, 32 );

      gameOver = ImageIO.read( new File( GAMEOVER ) );
      background = ImageIO.read( new File( BACKGROUNDIMAGE ) );

    } catch ( IOException exception ) {
      exception.printStackTrace();
    }
  }

  private static BufferedImage[] shiftContents( BufferedImage[] arrayToBeShifted, int numberOfShifts ) {
    for ( int imagesShifted = 0; imagesShifted < numberOfShifts; imagesShifted++ ) {
      imagesShiftedArray[imagesShifted] = arrayToBeShifted[imagesShifted + numberOfShifts];
    }
    for ( int imagesShifted = numberOfShifts; imagesShifted < arrayToBeShifted.length; imagesShifted++ ) {
      imagesShiftedArray[imagesShifted] = arrayToBeShifted[imagesShifted - numberOfShifts];
    }
    for ( int numberOfImagesSavedBack = 0; numberOfImagesSavedBack < arrayToBeShifted.length; numberOfImagesSavedBack++ ) {
      arrayToBeShifted[numberOfImagesSavedBack] = imagesShiftedArray[numberOfImagesSavedBack];
    }
    return arrayToBeShifted;
  }


  public static int[][] parseLevelFile( String resourceLocation ) {
    Path path = FileSystems.getDefault().getPath( resourceLocation );
    int[][] levelMap = new int[0][0];

    try {
      java.util.List<String> levelRows = Files.readAllLines( path );
      levelMap = new int[levelRows.size()][levelRows.get( 0 ).length()];

      for ( int i = 0; i < levelRows.size(); i++ ) {
        String row = levelRows.get( i );

        for ( int j = 0; j < row.length(); j++ ) {
          String currentCharacter = String.valueOf( row.charAt( j ) );
          int value;

          if ( currentCharacter.matches( "\\d+" ) ) {
            value = Integer.parseInt( currentCharacter );
          } else {
            value = 0;
          }
          levelMap[i][j] = value;
        }
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    return levelMap;
  }

  public enum PlayerControls {
    left( 37 ),
    up( 38 ),
    right( 39 ),
    down( 40 ),
    fire( 10 ),
    a( 65 ),
    w( 87 ),
    d( 68 ),
    s( 83 ),
    space( 32 );

    private final int KEYCODE;

    PlayerControls( int keyCode ) {
      KEYCODE = keyCode;
    }

    public int getKEYCODE( ) {
      return KEYCODE;
    }

  }

  public static PlayerControls[] playerOneControls = new PlayerControls[]{
      PlayerControls.a,
      PlayerControls.w,
      PlayerControls.d,
      PlayerControls.s,
      PlayerControls.space
  };

  public static PlayerControls[] playerTwoControls = new PlayerControls[]{
      PlayerControls.left,
      PlayerControls.up,
      PlayerControls.right,
      PlayerControls.down,
      PlayerControls.fire
  };

  public static int getTankSpawnXCoordinate( int tankIdentificationNumber ) {
    if ( tankIdentificationNumber == 1 ) {
      return PLAYERONEXCOORDINATERESPAWN;
    } else if ( tankIdentificationNumber == 2 ) {
      return PLAYERTWOXCOORDINATERESPAWN;
    } else {
      return 0;
    }
  }

  public static int getTankSpawnYCoordinate( int tankIdentificationNumber ) {
    if ( tankIdentificationNumber == 1 ) {
      return PLAYERONEYCOORDINATERESPAWN;
    } else if ( tankIdentificationNumber == 2 ) {
      return PLAYERTWOYCOORDINATERESPAWN;
    } else {
      return 0;
    }
  }


}
