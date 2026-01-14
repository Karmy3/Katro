package com.example.katro.model

// Dans un nouveau fichier nommé JeuData.kt
data class ConfigurationJeu(
    val nbColonnes: Int,
    val nbNoix: Int,
    val adversaire: String
)

data class ReponseServeur(
    val status: String,
    val plateauInitial: List<Int>
)

// 1. Pour envoyer le clic au Python
data class CoupJoueur(
    val index: Int,
    val joueur: Int
)

// 2. Pour recevoir le résultat du calcul Python
data class ReponseCoup(
    val etapes: List<List<Int>>,
    val gagnant: Int,// Liste de tous les états du plateau
    val score_j1: Int,
    val score_j2: Int,
    val tour_suivant: Int
)