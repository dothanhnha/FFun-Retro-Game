package com.nhado.ffunretrogame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class RetroScreen(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val DEF_ROW = 1000
    private val DEF_COL = 1000
    private val DEF_PADDING_EACH_CELL = 5f // as DP
    var row: Int
    var col: Int

    var firstXIncludePaddingEachCell: Float = 0f
    var firstYIncludePaddingEachCell: Float = 0f

    var cellHeightIncludePadding: Float = 0f
    var cellWidthIncludePadding: Float = 0f
    var cellHeightWithoutPadding: Float = 0f
    var cellWidthWithoutPadding: Float = 0f
    var cellPaint: Paint = Paint()

    // per cell have this paddingPerCell padding => between 2 cell => have 2*paddingPerCell distance
    var paddingPerCell: Float // as pixel

    var listBricks: ArrayList<Brick> = ArrayList()
    var fallingBrick: Brick? = null

    lateinit var matrix: ArrayList<ArrayList<Boolean>>

    init {
        context?.theme?.obtainStyledAttributes(
                attrs,
                R.styleable.RetroScreen,
                0, 0
        ).apply {
            row = this?.getInt(R.styleable.RetroScreen_row, DEF_ROW) ?: DEF_ROW
            col = this?.getInt(R.styleable.RetroScreen_col, DEF_COL) ?: DEF_COL
            paddingPerCell =
                    (this?.getDimension(R.styleable.RetroScreen_paddingEachCell, DEF_PADDING_EACH_CELL)
                            ?: DEF_PADDING_EACH_CELL).dpToPx(resources) / 2f

            initMatrix()

        }

        configurePaint()

        genNewBrick()
    }

    private fun initMatrix() {
        var colTemp: ArrayList<Boolean>

        matrix = ArrayList()
        for (x in 0 until col) {
            colTemp = ArrayList()
            for (y in 0 until row) {
                colTemp.add(y == 0 || y == row - 1 || x == 0 || x == col - 1)
            }
            matrix.add(colTemp)
        }

        //make true border
        for (x in 0 until col) {
            colTemp = ArrayList()
            for (y in 0 until row) {
                colTemp.add(false)
            }
            matrix.add(colTemp)
        }
    }

    private fun configurePaint() {
        cellPaint.color = Color.BLACK
        cellPaint.style = Paint.Style.FILL_AND_STROKE
        cellPaint.setStrokeWidth(0f)
    }

    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        cellHeightIncludePadding = (height - (paddingBottom + paddingTop) - 2 * paddingPerCell).toFloat() / row.toFloat()
        cellWidthIncludePadding = (width - (paddingLeft + paddingRight) - 2 * paddingPerCell).toFloat() / col.toFloat()
        cellWidthWithoutPadding = cellWidthIncludePadding - 2 * paddingPerCell
        cellHeightWithoutPadding = cellHeightIncludePadding - 2 * paddingPerCell
        firstXIncludePaddingEachCell = paddingLeft + paddingPerCell
        firstYIncludePaddingEachCell = paddingTop + paddingPerCell
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        matrix.forEachIndexed { x: Int, arrayList: ArrayList<Boolean> ->
            arrayList.forEachIndexed { y: Int, isPaint: Boolean ->
                if (isPaint)
                    canvas?.apply {
                        drawOneCell(this, y, x)
                    }
            }
        }
    }

    fun drawOneCell(canvas: Canvas, indexRow: Int, indexCol: Int) {
        val leftTop = determineLeftTopCellWithoutPadding(indexRow, indexCol)
        canvas.drawRect(leftTop.first + paddingPerCell, leftTop.second + paddingPerCell,
                leftTop.first + paddingPerCell + cellWidthWithoutPadding,
                leftTop.second + paddingPerCell + cellHeightWithoutPadding, cellPaint)
    }

    fun determineLeftTopCellWithoutPadding(indexRow: Int, indexCol: Int): Pair<Float, Float> {
        val x = firstXIncludePaddingEachCell + indexCol * cellWidthIncludePadding
        val y = firstYIncludePaddingEachCell + indexRow * cellHeightIncludePadding
        return Pair(x, y)
    }

    fun move(movement: Brick.Movement) {
        if (!checkImpact(movement)) {
            clearBrick(fallingBrick)

            fallingBrick?.move(movement)

            fixedBrick(fallingBrick)
        }
        else if(movement == Brick.Movement.DOWN){
            genNewBrick()
        }

        invalidate()
    }

    fun fixedBrick(brick: Brick?) {
        brick?.listCells?.forEach {
            matrix[it.x][it.y] = true
        }
    }

    fun clearBrick(brick: Brick?) {
        brick?.listCells?.forEach {
            matrix[it.x][it.y] = false
        }
    }

    private fun genNewBrick() {
        fallingBrick = Brick.Builder(Brick.Type.L_SHAPE)
                .x(5)
                .y(5)
                .build()
        fallingBrick?.apply {
            fixedBrick(this)
        }
    }

    fun checkImpact(movement: Brick.Movement): Boolean {
        clearBrick(fallingBrick)
        fallingBrick?.apply {
            Brick.clone(this).move(movement).listCells.forEach {
                if (matrix[it.x][it.y]) {
                    fixedBrick(fallingBrick)
                    return true
                }
            }
        }
        fixedBrick(fallingBrick)
        return false
    }
}