package com.nhado.ffunretrogame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
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
    var fallingBrick : Brick? = null
    var matrix : ArrayList<ArrayList<Boolean>>

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
                    ?: DEF_PADDING_EACH_CELL).dpToPx(resources)/2f

            var colTemp: ArrayList<Boolean>

            matrix = ArrayList()
            for(y in 0 until row){
                colTemp = ArrayList(col)
                for(x in 0 until col){
                    matrix.add(colTemp)
                }
            }
        }

        configurePaint()
    }

    private fun configurePaint() {
        cellPaint.color = Color.BLACK
        cellPaint.style = Paint.Style.FILL_AND_STROKE
        cellPaint.setStrokeWidth(0f)
    }

    override fun onSizeChanged(width: Int, height: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(width, height, oldw, oldh)
        cellHeightIncludePadding = (height - (paddingBottom + paddingTop) - 2*paddingPerCell).toFloat() / row.toFloat()
        cellWidthIncludePadding = (width - (paddingLeft + paddingRight) - 2*paddingPerCell).toFloat() / col.toFloat()
        cellWidthWithoutPadding = cellWidthIncludePadding - 2*paddingPerCell
        cellHeightWithoutPadding = cellHeightIncludePadding - 2*paddingPerCell
        firstXIncludePaddingEachCell = paddingLeft + paddingPerCell
        firstYIncludePaddingEachCell = paddingTop + paddingPerCell
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        listBricks.forEach {
            canvas?.apply {
                drawOneBrick(this, it)
            }
        }
    }

    fun drawOneCell(canvas: Canvas, indexRow: Int, indexCol: Int){
        val leftTop = determineLeftTopCellWithoutPadding(indexRow, indexCol)
        canvas.drawRect(leftTop.first + paddingPerCell, leftTop.second + paddingPerCell,
            leftTop.first + paddingPerCell + cellWidthWithoutPadding,
            leftTop.second + paddingPerCell + cellHeightWithoutPadding, cellPaint)
    }

    fun drawOneBrick(canvas: Canvas, brick: Brick){
        brick.listCells.forEach {
            drawOneCell(canvas, it.y, it.x)
        }
    }

    fun determineLeftTopCellWithoutPadding(indexRow: Int, indexCol: Int) : Pair<Float, Float> {
        val x = firstXIncludePaddingEachCell + indexCol * cellWidthIncludePadding
        val y = firstYIncludePaddingEachCell + indexRow * cellHeightIncludePadding
        return Pair(x,y)
    }

    fun down(){
        fallingBrick?.down()
        invalidate()
    }

    fun left(){
        fallingBrick?.left()
        invalidate()
    }

    fun right(){
        fallingBrick?.right()
        invalidate()
    }

/*    fun newFallingBrick(){
        fallingBrick =
    }*/

    fun fixedBrick(brick: Brick){
        brick.listCells.forEach {
            matrix[it.x][it.y] = true
        }
    }

/*    fun checkImpact(): Boolean{
        fallingBrick?.listCells?.forEach {
            if(matrix[it.x][it.y])
        }
    }*/
}