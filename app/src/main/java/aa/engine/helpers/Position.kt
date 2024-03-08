package aa.engine.helpers

class Position(private var x: Float, private var y: Float) {

    fun getX(): Float {
        return this.x;
    }

    fun getY(): Float {
        return this.y;
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x;
        this.y = y;
    }
};

