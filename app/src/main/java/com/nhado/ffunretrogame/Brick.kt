package com.nhado.ffunretrogame

import android.graphics.Point

class Brick(xLeft: Int, yTop: Int, var listCells : List<Point>){

    init {
        listCells.forEach {
            it.x += xLeft
            it.y += yTop
        }
    }

    fun down(){
        listCells.forEach {
            it.y++
        }
    }

    fun left(){
        listCells.forEach {
            it.x--
        }
    }

    fun right(){
        listCells.forEach {
            it.x++
        }
    }

    enum class Type{
        I_SHAPE, L_SHAPE, T_SHAPE, SQUARE_SHAPE
    }


    class Builder(var type : Type){
        var x = 0
        var y = 0

        fun x(x: Int) = apply { this.x = x }
        fun y(y: Int) = apply { this.y = x }
        fun build() = Brick(x, y, genListCells())

        private fun genListCells() : ArrayList<Point>{
            when(type){
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