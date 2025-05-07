# Nome del file principale (senza estensione)
MAIN = main

# Estensione PDF
PDF = $(MAIN).pdf

# File inclusi da monitorare (anche nelle sottocartelle)
INCLUDES = $(wildcard capitoli/**/*.tex) $(wildcard capitoli/*.tex)

# Estensioni dei file temporanei da rimuovere
CLEAN_EXT = aux log out toc lof lot fls fdb_latexmk synctex.gz bbl blg

# Regola predefinita: compila il file con pdflatex
all: $(PDF)

$(PDF): $(MAIN).tex $(INCLUDES)
	pdflatex $(MAIN).tex
	pdflatex $(MAIN).tex  # Esegui due volte per aggiornare la bibliografia e le referenze

# Pulisce i file temporanei
clean:
	@echo "Cleaning temporary LaTeX files..."
	rm -f $(foreach ext, $(CLEAN_EXT), $(MAIN).$(ext))

# Pulisce tutto, inclusi PDF e output finali
cleanall: clean
	@echo "Also removing the generated PDF..."
	rm -f $(PDF)

# Regole fittizie
.PHONY: all clean cleanall