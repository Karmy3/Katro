package com.example.katro.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.katro.viewModel.JeuViewModel

@Composable
fun ApplicationJeuNoix(monViewModel: JeuViewModel) {
    var ecranActuel by remember { mutableIntStateOf(0) }
    var colonnesParJoueur by remember { mutableIntStateOf(4) }

    when (ecranActuel) {
        0 -> MenuAccueil(onContinuer = { ecranActuel = 1 })

        1 -> MenuConfiguration(
            monViewModel = monViewModel,
            onValider = { cols, noix ->
                colonnesParJoueur = cols
                // On lance la création du graphe sur le serveur Python
                monViewModel.demarrerPartie(cols, noix, "Robot")
                ecranActuel = 2
            }
        )

        // Ici, DessinPlateau ne reçoit que 2 arguments, comme défini précédemment
        2 -> DessinPlateau(monViewModel, colonnesParJoueur)
    }
}