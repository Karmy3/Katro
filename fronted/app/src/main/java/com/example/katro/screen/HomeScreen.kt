package com.example.katro.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuAccueil(onContinuer: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("KATRO", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onContinuer) {
            Text("Lancer le jeu")
        }
    }
}

/*
@Composable
fun MenuAccueil(onLancerJeu: (Int, Int, String) -> Unit) {
    // Variables pour stocker les choix de l'utilisateur
    var nbColonnes by remember { mutableStateOf("4") }
    var nbNoix by remember { mutableStateOf("3") }
    var adversaire by remember { mutableStateOf("Robot") }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Mon Jeu de Noix", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(30.dp))

        // Choix de l'adversaire
        Text("Contre qui voulez-vous jouer ?")
        Row {
            Button(onClick = { adversaire = "Humain" },
                enabled = adversaire != "Humain") { Text("Humain") }
            Spacer(Modifier.width(10.dp))
            Button(onClick = { adversaire = "Robot" },
                enabled = adversaire != "Robot") { Text("Robot") }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Configuration des colonnes
        OutlinedTextField(
            value = nbColonnes,
            onValueChange = { nbColonnes = it },
            label = { Text("Nombre de colonnes") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Configuration des noix
        OutlinedTextField(
            value = nbNoix,
            onValueChange = { nbNoix = it },
            label = { Text("Noix par trou (Défaut: 3)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Bouton de validation
        Button(
            onClick = {
                onLancerJeu(nbColonnes.toInt(), nbNoix.toInt(), adversaire)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("VALIDER ET LANCER")
        }
    }
}

 */


