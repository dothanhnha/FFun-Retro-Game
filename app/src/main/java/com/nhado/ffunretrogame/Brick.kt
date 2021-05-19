package com.nhado.ffunretrogame

import android.graphics.Point

class Brick(xLeft: Int, yTop: Int, var listCells: List<Point>) {

    companion object {
        fun clone(brick: Brick): Brick {
            var listTemp: ArrayList<Point> = ArrayList()
            brick.listCells.forEach {
                listTemp.add(Point(it.x, it.y))
            }
            return Brick(0, 0, listTemp)
        }
    }

    init {
        listCells.forEach {
            it.x += xLeft
            it.y += yTop
        }
    }

    fun move(movement: Movement): Brick {
        when (movement) {
            Movement.DOWN -> return down()
            Movement.LEFT -> return left()
            Movement.RIGHT -> return right()
        }
    }

    fun down(): Brick {
        listCells.forEach {
            it.y++
        }
        return this
    }

    fun left(): Brick {
        listCells.forEach {
            it.x--
        }
        return this
    }

    fun right(): Brick {
        listCells.forEach {
            it.x++
        }
        return this
    }

    enum class Type {
        I_SHAPE, L_SHAPE, T_SHAPE, SQUARE_SHAPE
    }

    enum class Movement {
        LEFT, RIGHT, DOWN
    }


    class Builder(var type: Type) {
        var x = 0
        var y = 0

        fun x(x: Int) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = x }
        fun build() = Brick(x, y, genListCells())

        private fun genListCells(): ArrayList<Point> {
            when (type) {
                Type.I_SHAPE -> {
                    return arrayListOf(
                            Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3),
                            Point(0, 4), Point(0, 5), Point(0, 6), Point(0, 7)
                    )
                }
                Type.L_SHAPE -> {
                    return arrayListOf(
                            Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3),
                            Point(1, 3), Point(2, 3)
                    )
                }
                Type.T_SHAPE -> {
                    return arrayListOf(
                            Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0), Point(4, 0),
                            Point(2, 1), Point(2, 2), Point(2, 3)
                    )
                }
                Type.SQUARE_SHAPE -> {
                    return arrayListOf(
                            Point(0, 0), Point(1, 0),
                            Point(1, 0), Point(1, 2)
                    )
                }
            }
        }
    }
}