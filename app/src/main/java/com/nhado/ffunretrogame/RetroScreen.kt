package com.nhado.ffunretrogame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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

    var testX =  5
    var testY =  5

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
/*        for(x in 0 until col){
            for(y in 0 until row){
                canvas?.apply {
                    drawOneCell(this, y, x)
                }
            }
        }*/
        canvas?.apply {
            drawOneCell(this, testY, testX)
        }

    }

    fun drawOneCell(canvas: Canvas, indexRow: Int, indexCol: Int){
        val leftTop = determineLeftTopCellWithoutPadding(indexRow, indexCol)
        canvas.drawRect(leftTop.first + paddingPerCell, leftTop.second + paddingPerCell,
            leftTop.first + paddingPerCell + cellWidthWithoutPadding,
            leftTop.second + paddingPerCell + cellHeightWithoutPadding, cellPaint)
    }

    fun determineLeftTopCellWithoutPadding(indexRow: Int, indexCol: Int) : Pair<Float, Float> {
        val x = firstXIncludePaddingEachCell + indexCol * cellWidthIncludePadding
        val y = firstYIncludePaddingEachCell + indexRow * cellHeightIncludePadding
        return Pair(x,y)
    }

    fun up(){
        testY--
        invalidate()
    }
    fun down(){
        testY++
        invalidate()
    }
    fun left(){
        testX--
        invalidate()
    }
    fun right(){
        testX++
        invalidate()
    }
}