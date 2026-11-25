package tn.esprit.autoloc.web.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Traduit les exceptions de la couche service en réponses HTTP lisibles
 * (sinon toute exception renvoie 500 Internal Server Error).
 */
@RestControllerAdvice
public class RestExceptionHandler {

    // id inexistant -> 404 Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleNotFound(EntityNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // DTO invalide (@Valid) : nom ou prénom vide, email mal formé... -> 400 avec le détail par champ
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> erreurs.putIfAbsent(e.getField(), e.getDefaultMessage()));
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Données invalides");
        pd.setProperty("erreurs", erreurs);
        return pd;
    }

    // règle métier non respectée (employé déjà affecté, équipement déjà installé...) -> 409 Conflict
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleRegleMetier(IllegalStateException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    // contrainte SQL violée (champ obligatoire absent, valeur unique en double, clé étrangère) -> 409 Conflict
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleConflict(DataIntegrityViolationException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Contrainte de la base violée : " + ex.getMostSpecificCause().getMessage());
    }
}
