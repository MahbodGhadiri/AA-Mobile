package aa.engine.config

import aa.engine.helpers.Position
import android.content.Context

enum class EngineStatus {
    RUNNING,
    STOPPED
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

    fun initialize(context: Context) {
        screenWidth =
            context.resources.displayMetrics.widthPixels.toFloat();
        screenHeight =
            context.resources.displayMetrics.heightPixels.toFloat();
        spawnBallPos =
            Position(screenWidth / 2, screenHeight - screenHeight / 10);
        mainCirclePos = Position(
            screenWidth / 2,
            screenHeight / 3
        );
        mainCircleRadius = screenWidth / 6;
        mainCircleOrbit = screenWidth / 3;

        smallBallRadius = screenWidth / 24;
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
    fun getEngineStatus(): EngineStatus = engineStatus;


    // setters
    fun setEngineStatus(status: EngineStatus) {
        engineStatus = status;
    }
}
