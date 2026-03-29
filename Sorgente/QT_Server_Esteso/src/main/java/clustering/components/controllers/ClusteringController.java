package clustering.components.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import clustering.components.services.ClusteringService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller per la gestione delle operazioni di clustering.
 * Espone endpoint per l'apprendimento da file e dal database.
 */
@RestController
@RequestMapping("api/clustering")
@Tag(name = "Clustering API", description = "Endpoint per il clustering dei dati")
public class ClusteringController {
    /** 
     * Service responsabile della logica di clustering.
     */
    @Autowired
    private ClusteringService clustering_service;

    /**
     * Avvia il clustering leggendo i dati da file.
     * 
     * @param fileName nome del file da elaborare.
     * @return risultato del clustering in formato testo.
     */
    @Operation(
        summary = "Clustering da file",
        description = "Esegue il clustering leggendo i dati da un file",
        responses = {
            @ApiResponse(responseCode = "200", description = "File letto con successo", content = @Content),
            @ApiResponse(responseCode = "404", description = "File non trovato o non valido", content = @Content)
        }
    )
    @GetMapping(value = "learningfromfile", produces = "text/plain")
    private ResponseEntity<String> getClusteringFromFile(
        @Parameter(description = "Nome del file da cui leggere") @RequestParam(name = "file") String fileName) {
    	String file = clustering_service.learningfromfile(fileName);
        if(file == null) {
        	return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(file);
    }

    /**
     * Avvia il clustering leggendo i dati dal database.
     * 
     * @param tableName nome del file da elaborare.
     * @param qt_radius raggio con cui eseguire il clustering.
     * @param fileName nome del file in cui salvare il clustering.
     * @return risultato del clustering da database in formato testo.
     */
    @Operation(
        summary = "Clustering da database",
        description = "Esegue il clustering leggendo i dati da una tabella del database e salva i risultati su file",
        responses = {
            @ApiResponse(responseCode = "200", description = "Clustering eseguito con successo", content = @Content),
            @ApiResponse(responseCode = "400", description = "Parametri non validi o tabella non trovata", content = @Content)
        }
    )
    @GetMapping(value = "learningfromdb", produces = "text/plain")
    private ResponseEntity<String> getClusteringFromDb(
        @Parameter(description = "Nome della tabella del database") @RequestParam(name ="table") String tableName,
        @Parameter(description = "Raggio del clustering") @RequestParam(name ="radius") int qt_radius,
        @Parameter(description = "Nome del file su cui salvare i risultati") @RequestParam(name = "file") String fileName) {
        String db_result = clustering_service.learningfromdb(tableName, qt_radius, fileName);
    	if(db_result == null) {
    		return ResponseEntity.notFound().build();
    	}
        return ResponseEntity.ok(db_result);
    }

    
    /**
     * Restituisce la lista dei file da cui e' possibile 
     * leggere clustering precedentemente salvati.
     * 
     * @return lista dei file disponibili.
     */
    @Operation(
        summary = "Lista dei file di clustering",
        description = "Restituisce la lista dei file di clustering disponibili nella directory di esecuzione",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista dei file restituita con successo", content = @Content)
        }
    )
    @GetMapping()
    private List<String> getFileList() {
        return clustering_service.getfilelist();
    }
}
