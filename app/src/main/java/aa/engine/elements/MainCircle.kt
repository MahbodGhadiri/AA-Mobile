package aa.engine.elements

import aa.engine.helpers.Position

open class MainCircle constructor(
    private val position: Position,
    private var radius: Int,
    private var orbit: Int
) {
    fun setPosition(x: Int, y: Int) {
        this.position.setPosition(x, y);
    }

    fun setRadius(radius: Int) {
        this.radius = radius;
    }

    fun setOrbit(orbit: Int) {
        this.orbit = orbit;
    }

    fun getPosition(): Position {
        return this.position;
    }

    fun getRadius(): Int {
        return this.radius;
    }

    fun getOrbit(): Int {
        return this.orbit;
    }


}