import aa.engine.helpers.Position
import android.content.Context

object AppConfig {
    private var scrWidth: Float = 0.0f
    private var scrHeight: Float = 0.0f
    private lateinit var spawnBallPos: Position;

    fun initialize(context: Context) {
        scrWidth =
            context.resources.displayMetrics.widthPixels.toFloat();
        scrHeight =
            context.resources.displayMetrics.heightPixels.toFloat();
        spawnBallPos =
            Position(this.scrWidth / 2, this.scrHeight - this.scrHeight / 10);
    }


    fun getScrWidth(): Float = scrWidth
    fun getScrHeight(): Float = scrHeight
    fun getSpawnBallPos(): Position = spawnBallPos
}

