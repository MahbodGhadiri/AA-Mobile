package aa.engine.elements

import aa.engine.helpers.Position

open class MainCircle constructor(
    private val position: Position,
    private var radius: Float,
    private var orbit: Float
) {
    fun setPosition(x: Float, y: Float) {
        this.position.setPosition(x, y);
    }

    fun setRadius(radius: Float) {
        this.radius = radius;
    }

    fun setOrbit(orbit: Float) {
        this.orbit = orbit;
    }

    fun getPosition(): Position {
        return this.position;
    }

    fun getRadius(): Float {
        return this.radius;
    }

    fun getOrbit(): Float {
        return this.orbit;
    }


}