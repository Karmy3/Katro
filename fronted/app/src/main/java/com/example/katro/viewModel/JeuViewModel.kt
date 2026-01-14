package com.example.katro.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.katro.RetrofitClient
import com.example.katro.model.ConfigurationJeu
import com.example.katro.model.CoupJoueur
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JeuViewModel : ViewModel() {
    private val api = RetrofitClient.instance

    var plateau by mutableStateOf(listOf<Int>())
    var scoreJ1 by mutableIntStateOf(0)
    var scoreJ2 by mutableIntStateOf(0)
    var tourActuel by mutableIntStateOf(1)

    var gagnantParFini by mutableStateOf(0) // 0: personne, 1: J1, 2: J2
    var showDialogGagnant by mutableStateOf(false)

    fun demarrerPartie(cols: Int, noix: Int, adv: String) {
        viewModelScope.launch {
            try {
                // On vide le plateau avant de charger le nouveau pour éviter l'erreur d'index
                plateau = emptyList()
                val reponse = api.envoyerConfiguration(ConfigurationJeu(cols, noix, adv))
                plateau = reponse.plateauInitial
                tourActuel = 1
            } catch (e: Exception) {
                println("Erreur Init: ${e.message}")
            }
        }
    }

    fun envoyerCoupAuPython(idx: Int, joueur: Int) {
        viewModelScope.launch {
            try {
                val reponse = api.jouerCoup(CoupJoueur(idx, joueur))

                // Animation de chaque étape du graphe
                reponse.etapes.forEach { etat ->
                    plateau = etat
                    delay(500)
                }

                scoreJ1 = reponse.score_j1
                scoreJ2 = reponse.score_j2
                tourActuel = reponse.tour_suivant

                // VERIFICATION DU GAGNANT
                if (reponse.gagnant != 0) {
                    gagnantParFini = reponse.gagnant
                    showDialogGagnant = true
                }
            } catch (e: Exception) {
                println("Erreur Coup: ${e.message}")
            }
        }
    }
}