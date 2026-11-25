# Activité DTO — guide de test (Swagger)

Swagger : `http://localhost:8089/swagger-ui.html`

## 0. Une seule fois avant de tester : rendre email et numPermis facultatifs en base

L'entité `Client` a changé (email et numPermis ne sont plus obligatoires), mais
`ddl-auto=update` **n'enlève jamais** une contrainte NOT NULL déjà créée.
Dans phpMyAdmin → `autoloc_db` → onglet **SQL** :

```sql
ALTER TABLE client MODIFY email VARCHAR(100) NULL;
ALTER TABLE client MODIFY num_permis VARCHAR(30) NULL;
```

Sans ça, « ajouter un client sans email » renvoie **409** (contrainte de la base violée).

---

## Scénario 1 — Clients

### Test 1 : client avec toutes les informations — `POST /api/clients` → **201**
```json
{ "nom": "Mansour", "prenom": "Leila", "email": "leila.mansour@mail.tn", "telephone": "98111222" }
```
Réponse attendue : `idClient`, `nom`, `prenom`, `telephone` — **sans** `email`.

### Test 2 : client sans email ni téléphone — `POST /api/clients` → **201**
```json
{ "nom": "Jebali", "prenom": "Omar" }
```
Réponse attendue : `"telephone": null`.

### Test 3 : consulter un client — `GET /api/clients/{id}` → **200**
id = celui renvoyé au test 1. Un id inexistant (ex. 999) → **404**.

### Tests bonus (validation)
- `{ "prenom": "Omar" }` → **400**, `"erreurs": { "nom": "Le nom est obligatoire" }`
- `{ "nom": "A", "prenom": "B", "email": "pas-un-email" }` → **400**, `"email": "Format d'email invalide"`

---

## Scénario 2 — Réservations

### Préparer des données

1. Créer 2 réservations — `POST /api/reservations` :
```json
{ "dateDebut": "2026-12-01", "dateFin": "2026-12-05", "statut": "CONFIRMEE" }
```
```json
{ "dateDebut": "2026-12-10", "dateFin": "2026-12-12", "statut": "EN_ATTENTE" }
```

2. Les rattacher à un client et un véhicule (l'API n'a pas encore d'endpoint pour ça).
   phpMyAdmin → onglet **SQL** (adapter les ids à ta base) :
```sql
UPDATE reservation SET client_id = 1, vehicule_id = 1 WHERE id_reservation = 1;
UPDATE reservation SET client_id = 1, vehicule_id = 2 WHERE id_reservation = 2;
```

### Test 1 : toutes les réservations — `GET /api/reservations` → **200**
```json
[
  {
    "idReservation": 1,
    "dateDebut": "2026-12-01",
    "dateFin": "2026-12-05",
    "statut": "CONFIRMEE",
    "nomClient": "Mansour",
    "prenomClient": "Leila",
    "marqueVehicule": "Citroen",
    "modeleVehicule": "C3",
    "immatriculationVehicule": "210 TU 1001"
  }
]
```

### Test 2 : réservations d'un client — `GET /api/reservations/client/{idClient}` → **200**
- idClient = 1 → les 2 réservations
- idClient = 2 (client sans réservation) → `[]`
- idClient = 999 → **404** « Client introuvable »

### Test 3 : pas d'objets Client / Vehicule dans le JSON
Dans les réponses ci-dessus, il n'y a **aucun** champ `"client": { ... }` ni `"vehicule": { ... }` :
seulement des champs simples (`nomClient`, `marqueVehicule`…).
