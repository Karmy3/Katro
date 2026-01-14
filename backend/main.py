from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import uvicorn
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()
app.add_middleware(CORSMiddleware, allow_origins=["*"], allow_methods=["*"], allow_headers=["*"])

# --- MODÈLES DE DONNÉES ---
class ConfigurationJeu(BaseModel):
    nbColonnes: int
    nbNoix: int
    adversaire: str

class CoupJoueur(BaseModel):
    index: int
    joueur: int

# --- LOGIQUE DU JEU ---
class NoeudTrou:
    def __init__(self, id, proprietaire):
        self.id = id
        self.proprietaire = proprietaire 
        self.noix = 0
        self.suivant = None       
        self.face_a_face = None   

class GrapheKatro:
    def __init__(self, nb_cols, nb_noix):
        self.nb_cols = nb_cols
        self.total = nb_cols * 4
        self.noeuds = [NoeudTrou(i, 1 if i < (nb_cols * 2) else 2) for i in range(self.total)]
        
        n = nb_cols
        for i in range(self.total):
            self.noeuds[i].noix = nb_noix
            
            # --- LOGIQUE DE CAPTURE EN MIROIR VERTICAL ---
            # J1 Interne (4 à 7) -> J2 Interne (8 à 11)
            # Le 4 est en face du 8, le 7 en face du 11.
            if n <= i < 2 * n:  # Ligne interne J1
                self.noeuds[i].face_a_face = self.noeuds[i + n]
            elif 2 * n <= i < 3 * n: # Ligne interne J2
                self.noeuds[i].face_a_face = self.noeuds[i - n]
            
            # --- LOGIQUE DE ROTATION (Gardez votre code actuel, il est bon) ---
            if i < n: # Externe J1
                self.noeuds[i].suivant = self.noeuds[n] if i == 0 else self.noeuds[i - 1]
            elif i < 2 * n: # Interne J1
                self.noeuds[i].suivant = self.noeuds[n - 1] if i == 2*n - 1 else self.noeuds[i + 1]
            elif i < 3 * n: # Interne J2
                self.noeuds[i].suivant = self.noeuds[3*n] if i == 2*n else self.noeuds[i - 1]
            else: # Externe J2
                self.noeuds[i].suivant = self.noeuds[3*n - 1] if i == 4*n - 1 else self.noeuds[i + 1]

    def simuler_katro(self, start_id, joueur_id):
        noeud = self.noeuds[start_id]
        
        # --- AUTORISER MEME AVEC 1 SEULE GRAINE ---
        if noeud.noix < 1: return [self.etat()] 

        main, noeud.noix = noeud.noix, 0
        etapes = [self.etat()]

        while main > 0:
            while main > 0:
                noeud = noeud.suivant
                noeud.noix += 1
                main -= 1
                etapes.append(self.etat())

            # Logique de Capture et Repiquage
            # Si on termine dans un trou qui contient maintenant plus de 1 graine
            if noeud.noix > 1:
                # On vérifie si on est sur la ligne interne (ceux qui ont un face_a_face)
                if noeud.face_a_face and noeud.face_a_face.noix > 0:
                    # CAPTURE MIROIR
                    main = noeud.noix + noeud.face_a_face.noix
                    noeud.noix = 0
                    noeud.face_a_face.noix = 0
                else:
                    # REPIQUAGE SIMPLE
                    main = noeud.noix
                    noeud.noix = 0
                etapes.append(self.etat())
                
        return etapes

    def etat(self):
        return [n.noix for n in self.noeuds]

    def verifier_blocage(self, joueur_id):
        # Un joueur perd s'il n'a plus de trous avec au moins 2 graines
        trous = [n.noix for n in self.noeuds if n.proprietaire == joueur_id]
        return not any(nb > 1 for nb in trous)

# --- INSTANCE GLOBALE ---
moteur = None

# --- ROUTES API ---

@app.post("/initialiser")
async def init(d: ConfigurationJeu):
    global moteur
    moteur = GrapheKatro(int(d.nbColonnes), int(d.nbNoix))
    return {"status": "success", "plateauInitial": moteur.etat()}

@app.post("/jouer")
async def jouer(d: CoupJoueur):
    if not moteur: 
        raise HTTPException(status_code=400, detail="Moteur non initialisé")
    
    # 1. Simulation du coup
    etapes = moteur.simuler_katro(d.index, d.joueur)
    
    # 2. Déterminer l'état de fin
    adversaire = 2 if d.joueur == 1 else 1
    est_fini = moteur.verifier_blocage(adversaire)
    
    # 3. Réponse
    return {
        "etapes": etapes,
        "tour_suivant": 0 if est_fini else adversaire,
        "gagnant": d.joueur if est_fini else 0,
        "score_j1": sum(n.noix for n in moteur.noeuds if n.proprietaire == 1),
        "score_j2": sum(n.noix for n in moteur.noeuds if n.proprietaire == 2)
    }

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)