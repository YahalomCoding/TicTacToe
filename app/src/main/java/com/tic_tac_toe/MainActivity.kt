package com.tic_tac_toe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tic_tac_toe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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

    for (gameButton in gameButtons) {
      gameButton.setOnClickListener {
        if (gameButton.text.isEmpty() && isGameActive) {
          gameButton.text = currentPlayer
          currentPlayer = if (currentPlayer == "X") "O" else "X"
          binding.gameStatusText.text = "Current Turn: Player $currentPlayer"
        }
      }
    }
  }
}
