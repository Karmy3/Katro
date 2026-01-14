package com.example.katro.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.katro.viewModel.JeuViewModel

@Composable
fun MenuConfiguration(
    // 1. On ajoute le ViewModel ici pour pouvoir l'utiliser
    monViewModel: JeuViewModel,
    onValider: (Int, Int) -> Unit
) {
    var txtCols by remember { mutableStateOf("4") } // Par défaut 4
    var txtNoix by remember { mutableStateOf("3") }
    // Ajoutons le choix de l'adversaire (par défaut Robot)
    var adversaireChoisi by remember { mutableStateOf("Robot") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Configuration", fontSize = 24.sp)

        TextField(value = txtCols, onValueChange = { txtCols = it },
            label = { Text("Nombre de colonnes") })

        TextField(value = txtNoix, onValueChange = { txtNoix = it },
            label = { Text("Noix par trou") })

        // Bouton pour changer l'adversaire (facultatif mais utile)
        Button(onClick = {
            adversaireChoisi = if (adversaireChoisi == "Robot") "Humain" else "Robot"
        }) {
            Text("Adversaire : $adversaireChoisi")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val c = txtCols.toIntOrNull() ?: 4
            val n = txtNoix.toIntOrNull() ?: 3

            // C'EST ICI QUE TOUT SE PASSE :
            // On envoie les infos au Python via le ViewModel
            monViewModel.demarrerPartie(c, n, adversaireChoisi)

            // On change d'écran vers le plateau de jeu
            onValider(c, n)
        }) {
            Text("Valider l'emplacement")
        }
    }
}