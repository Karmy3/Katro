package com.example.katro.api

import com.example.katro.model.ConfigurationJeu
import com.example.katro.model.CoupJoueur
import com.example.katro.model.ReponseCoup
import com.example.katro.model.ReponseServeur
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/initialiser")
    suspend fun envoyerConfiguration(@Body config: ConfigurationJeu): ReponseServeur

    @POST("/jouer")
    suspend fun jouerCoup(@Body coup: CoupJoueur): ReponseCoup
}