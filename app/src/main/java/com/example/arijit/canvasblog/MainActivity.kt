package com.example.arijit.canvasblog

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.arijit.canvasblog.views.Board

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val boardView: Board = findViewById(R.id.board_view)
        val pencil: CardView = findViewById(R.id.pencil_tool)
        val erase: CardView = findViewById(R.id.erase_tool)
        pencil.setOnClickListener {
            pencil.setCardBackgroundColor(resources.getColor(R.color.teal_200, null))
            erase.setCardBackgroundColor(resources.getColor(android.R.color.transparent, null))
            boardView.setPen()
        }
        erase.setOnClickListener {
            erase.setCardBackgroundColor(resources.getColor(R.color.teal_200, null))
            pencil.setCardBackgroundColor(resources.getColor(android.R.color.transparent, null))
            boardView.setEraser()
        }
    }
}