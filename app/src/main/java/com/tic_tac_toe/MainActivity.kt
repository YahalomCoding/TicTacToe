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
    val winningCombinations =
        arrayOf(
            // horizontals
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            // verticals
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            // diagonals
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6),
        )

    for (combination in winningCombinations) {
      val a = combination[0]
      val b = combination[1]
      val c = combination[2]
      if (values[a].isNotEmpty() && values[a] == values[b] && values[b] == values[c]) {
        return values[a]
      }
    }
    return null
  }

  fun getGameStatusText(player: String): String {
    return "Current Turn: Player $player"
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

    val startingPlayer = "X"
    val isGameActive = true
    var currentPlayer = startingPlayer

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
      currentPlayer = startingPlayer
      binding.gameStatusText.text = getGameStatusText(currentPlayer)
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
          } else if (gameButtons.all { b -> b.text.isNotEmpty() }) {
            binding.gameEndMessageTextView.text = "It's a Draw!"
            binding.gameEndMessageLayout.visibility = View.VISIBLE
            for (gameButton in gameButtons) {
              gameButton.isEnabled = false
            }
          } else {
            binding.gameStatusText.text = getGameStatusText(currentPlayer)
          }
        }
      }
    }
  }
}
