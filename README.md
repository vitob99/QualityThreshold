# 🔬 Quality Threshold Clustering

Progetto universitario che implementa l'**algoritmo Quality Threshold** per il clustering gerarchico, esposto tramite una **REST API documentata con Swagger** e completa di documentazione Javadoc e guida utente.

---

## 📌 Cos'è il Quality Threshold

Il Quality Threshold (QT) è un algoritmo di clustering che non richiede di specificare a priori il numero di cluster. Invece, raggruppa i punti garantendo che ogni cluster rispetti una **soglia di qualità** (threshold) definita dall'utente — tipicamente la distanza massima tra un punto e il centroide del suo cluster.

**Vantaggi rispetto a K-Means:**
- Non è necessario specificare K in anticipo
- Ogni cluster rispetta una coesione garantita
- Più intuitivo per dati biologici, medici o scientifici

---

## 🏗️ Architettura

Il sistema è strutturato in due layer principali:

```
┌──────────────────────────────────────────┐
│              REST API Layer              │
│         Spring Boot · Swagger UI         │
│                                          │
│  POST /cluster   GET /results  ...       │
└─────────────────┬────────────────────────┘
                  │
┌─────────────────▼────────────────────────┐
│           Core Algorithm Layer           │
│                                          │
│  QualityThreshold.java                   │
│  - Calcolo distanze                      │
│  - Costruzione cluster candidati         │
│  - Selezione cluster ottimale            │
│  - Iterazione fino a convergenza         │
└──────────────────────────────────────────┘
                  │
┌─────────────────▼────────────────────────┐
│             Persistence Layer            │
│                SQL · JDBC                │
│  - Salvataggio dataset                   │
│  - Salvataggio risultati clustering      │
└──────────────────────────────────────────┘
```

---

## 🛠️ Stack Tecnologico

| Componente | Tecnologia |
|---|---|
| Backend | Java · Spring Boot |
| API | REST · Swagger / OpenAPI |
| Documentazione codice | Javadoc |
| Database | SQL (script incluso) |
| Build | Maven |
| Distribuzione | JAR eseguibile |

---

## 🚀 Avvio rapido

### Prerequisiti
- Java 17+
- MySQL (o database compatibile)

### Setup database

```bash
# Eseguire lo script SQL incluso nella cartella "Jar + Bat + Script SQL"
mysql -u root -p < script.sql
```

### Avvio dell'applicazione

```bash
# Tramite JAR precompilato
java -jar QualityThreshold.jar

# Oppure tramite il file .bat (Windows)
run.bat
```

L'API sarà disponibile su `http://localhost:8080`
La documentazione Swagger su `http://localhost:8080/swagger-ui.html`

---

## 📡 API REST

Tutta la documentazione interattiva degli endpoint è disponibile su **Swagger UI** a runtime.

Principali operazioni:
- Caricamento del dataset
- Esecuzione del clustering con soglia configurabile
- Recupero dei cluster risultanti
- Visualizzazione dei dettagli per cluster

---

## 📁 Struttura del repository

```
QualityThreshold/
├── Sorgente/               # Codice sorgente Java
├── Jar + Bat + Script SQL/ # Distribuzione eseguibile + setup DB
│   ├── QualityThreshold.jar
│   ├── run.bat
│   └── script.sql
├── Javadoc/                # Documentazione API generata
├── UML/                    # Diagrammi UML delle classi
└── Guida Utente.pdf        # Manuale d'uso completo
```

---

## 📄 Documentazione

| Risorsa | Descrizione |
|---|---|
| [`Guida Utente.pdf`](./Guida%20Utente.pdf) | Manuale completo per l'utilizzo del sistema |
| [`Javadoc/`](./Javadoc/) | Documentazione generata di tutte le classi e metodi |
| [`UML/`](./UML/) | Diagrammi delle classi e architettura |
| Swagger UI | Documentazione interattiva REST (disponibile a runtime) |

---

## 👤 Autore

**Vito Bondanese**
- GitHub: [@vitob99](https://github.com/vitob99)
- LinkedIn: [vito-bondanese](https://www.linkedin.com/in/vito-bondanese-24b673360/)
