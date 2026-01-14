package com.example.katro.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.katro.viewModel.JeuViewModel

@Composable
fun DessinPlateau(monViewModel: JeuViewModel, nbCols: Int) {
    val plateau = monViewModel.plateau
    val tour = monViewModel.tourActuel
    val tailleAttendue = nbCols * 4

    if (monViewModel.plateau.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // PROTECTION ANTI-CRASH : On attend que le plateau soit prêt
    if (plateau.size < tailleAttendue) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color.Red)
                Text("Initialisation du Katro...", modifier = Modifier.padding(top = 8.dp))
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (tour == 1) "À VOTRE TOUR" else "TOUR ADVERSAIRE",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (tour == 1) Color(0xFF1976D2) else Color(0xFFD32F2F)
            )

            Spacer(Modifier.height(16.dp))

            // --- TERRAIN J2 (Haut) ---
            Column(
                modifier = Modifier
                    .background(if (tour == 2) Color(0xFFFFEBEE) else Color.Transparent)
                    .padding(4.dp)
            ) {
                // Ligne externe J2 (Tout en haut)
                // On affiche de 15 à 12 pour que le 12 soit à DROITE (au-dessus du 8)
                Row {
                    for (i in (nbCols * 4 - 1) downTo (nbCols * 3)) { // 15 downTo 12
                        TrouVisuel(plateau.getOrElse(i) { 0 }) {
                            if (tour == 2) monViewModel.envoyerCoupAuPython(i, 2)
                        }
                    }
                }
                // Ligne interne J2 (Milieu J2)
                // On affiche de 11 à 8 pour que le 8 soit à DROITE (au-dessus du 4)
                Row {
                    for (i in (nbCols * 3 - 1) downTo (nbCols * 2)) { // 11 downTo 8
                        TrouVisuel(plateau.getOrElse(i) { 0 }) {
                            if (tour == 2) monViewModel.envoyerCoupAuPython(i, 2)
                        }
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            // --- TERRAIN J1 (Bas) ---
            Column(Modifier.background(if(tour == 1) Color(0xFFE3F2FD) else Color.Transparent).padding(4.dp)) {
                Row {
                    for (i in (nbCols * 2 - 1) downTo nbCols) { // de 7 à 4
                        TrouVisuel(plateau.getOrElse(i) { 0 }) {
                            if (tour == 1) monViewModel.envoyerCoupAuPython(i, 1)
                        }
                    }
                }
                Row { // Ligne externe J1 : On va de DROITE à GAUCHE (Indices 3 downTo 0)
                    for (i in (nbCols - 1) downTo 0) {
                        TrouVisuel(plateau.getOrElse(i){0}) {
                            if(tour == 1) monViewModel.envoyerCoupAuPython(i, 1)
                        }
                    }
                }
            }
        }
    }

    // --- CETTE PARTIE AFFICHE LE MESSAGE DE VICTOIRE ---
    if (monViewModel.showDialogGagnant) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { /* Empêche de fermer en cliquant à côté */ },
            confirmButton = {
                androidx.compose.material3.Button(
                    onClick = {
                        monViewModel.showDialogGagnant = false
                        //monViewModel.recommencer() // Relance une partie
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4DD0E1) // Ton bleu Teal des icônes
                    )
                ) {
                    Text("REJOUER", fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text(
                    text = "  VICTOIRE ! ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            text = {
                val message = if (monViewModel.gagnantParFini == 1) {
                    "Félicitations ! Le Joueur 1 a remporté la partie."
                } else {
                    "Le Joueur 2 a gagné ! Meilleure chance la prochaine fois."
                }
                Text(message, fontSize = 18.sp)
            },
            containerColor = Color.White,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun TrouVisuel(quantite: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(55.dp)
            .padding(2.dp)
            .background(Color(0xFF795548), shape = CircleShape)
            .border(1.dp, Color.Black, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Dessin des grains de litchi (petits cercles rouges)
        if (quantite > 0) {
            Box(Modifier.fillMaxSize().padding(6.dp)) {
                val maxGrains = if (quantite > 8) 8 else quantite
                for (i in 0 until maxGrains) {
                    Box(
                        Modifier
                            .offset(x = (i % 3 * 8).dp, y = (i / 3 * 8).dp)
                            .size(10.dp)
                            .background(Color.Red, CircleShape)
                            .border(0.5.dp, Color.White, CircleShape)
                    )
                }
                Text(
                    "$quantite",
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}