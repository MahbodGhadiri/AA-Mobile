package aa.engine.config

import aa.engine.helpers.Position

enum class EngineStatus {
    RUNNING,
    WIN,
    GAMEOVER,
    READY
}

object AppConfig {
    private var screenWidth: Float = 0.0f;
    private var screenHeight: Float = 0.0f;

    private val spawningSpeed: Int = 3;
    private lateinit var spawnBallPos: Position;

    private lateinit var mainCirclePos: Position;
    private var mainCircleRadius: Float = 0F;
    private var mainCircleOrbit: Float = 0F;

    private var smallBallRadius: Float = 0F;

    private var engineStatus: EngineStatus = EngineStatus.RUNNING;

    private var gamePageElementsColor: String = "#76ABAE";

    private const val levelsCount = 6

    private var textSize: Float = 50F


    fun initialize(width: Float, height: Float) {
        screenWidth = width;
        screenHeight = height;
        spawnBallPos =
            Position(screenWidth / 2, screenHeight - screenHeight / 10);
        mainCirclePos = Position(
            screenWidth / 2,
            screenHeight / 3
        );
        mainCircleRadius = screenWidth / 8;
        mainCircleOrbit = screenWidth / 2.5F;

        smallBallRadius = screenWidth / 30;

    }


    // getters
    fun getScrWidth(): Float = screenWidth;
    fun getScrHeight(): Float = screenHeight;
    fun getSpawnBallPos(): Position = spawnBallPos;
    fun getSpawningSpeed(): Int = spawningSpeed;
    fun getMainCirclePos(): Position = mainCirclePos;
    fun getMainCircleRadius(): Float = mainCircleRadius;
    fun getMainCircleOrbit(): Float = mainCircleOrbit;
    fun getSmallBallRadius(): Float = smallBallRadius;
    fun getTextSize(): Float = textSize;
    fun getEngineStatus(): EngineStatus = engineStatus;
    fun getGamePageElementsColor(): String = gamePageElementsColor;
    fun getLevelCount(): Int = levelsCount

    // setters
    fun setEngineStatus(status: EngineStatus) {
        engineStatus = status;
    }

}



