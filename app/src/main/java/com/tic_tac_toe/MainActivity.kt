package com.tic_tac_toe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.tic_tac_toe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    fun hasWon(gameButtons: Array<MaterialButton>): String? {
        val values = gameButtons.map { b -> b.text.toString() }

        // Check rows
        if (values[0].isNotEmpty() && values[0] == values[1] && values[1] == values[2]) return values[0]
        if (values[3].isNotEmpty() && values[3] == values[4] && values[4] == values[5]) return values[3]
        if (values[6].isNotEmpty() && values[6] == values[7] && values[7] == values[8]) return values[6]

        // Check columns
        if (values[0].isNotEmpty() && values[0] == values[3] && values[3] == values[6]) return values[0]
        if (values[1].isNotEmpty() && values[1] == values[4] && values[4] == values[7]) return values[1]
        if (values[2].isNotEmpty() && values[2] == values[5] && values[5] == values[8]) return values[2]

        // Check diagonals
        if (values[0].isNotEmpty() && values[0] == values[4] && values[4] == values[8]) return values[0]
        if (values[2].isNotEmpty() && values[2] == values[4] && values[4] == values[6]) return values[2]

        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val isGameActive = true
        var currentPlayer = "X"

        val gameButtons =
            arrayOf(
                binding.firstButton,
                binding.secondButton,
                binding.thirdButton,
                binding.fourthButton,
                binding.fifthButton,
                binding.sixthButton,
                binding.seventhButton,
                binding.eighthButton,
                binding.ninthButton,
            )

        binding.gameEndMessageLayout.visibility = View.GONE

        binding.restartButton.setOnClickListener {
            for (gameButton in gameButtons) {
                gameButton.text = ""
                gameButton.isEnabled = true
            }
            binding.gameEndMessageLayout.visibility = View.GONE
            currentPlayer = "X"
        }

        for (gameButton in gameButtons) {
            gameButton.setOnClickListener {
                if (gameButton.text.isEmpty() && isGameActive) {
                    gameButton.text = currentPlayer
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    val playerWon = hasWon(gameButtons)
                    if (playerWon != null) {
                        binding.gameEndMessageTextView.text = "Player $playerWon Has Won!"
                        binding.gameEndMessageLayout.visibility = View.VISIBLE
                        for (gameButton in gameButtons) {
                            gameButton.isEnabled = false
                        }
                    }
                    else if (gameButtons.all { b -> b.text.isNotEmpty() }) {
                        binding.gameEndMessageTextView.text = "It's a Draw!"
                        binding.gameEndMessageLayout.visibility = View.VISIBLE
                        for (gameButton in gameButtons) {
                            gameButton.isEnabled = false
                        }
                    } else {
                        binding.gameStatusText.text = "Current Turn: Player $currentPlayer"
                    }
                }
            }
        }
    }
}
