package aa.engine.helpers

class Position(private var x: Int, private var y: Int) {

    fun getX(): Int {
        return this.x;
    }

    fun getY(): Int {
        return this.y;
    }

    fun setPosition(x: Int, y: Int){
        this.x = x;
        this.y = y;
    }
};

