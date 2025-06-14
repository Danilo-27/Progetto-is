# Nome del file principale (senza estensione)
MAIN = main

# Estensione PDF
PDF = $(MAIN).pdf

# File inclusi da monitorare
INCLUDES = $(wildcard capitoli/**/*.tex) \
           $(wildcard capitoli/*.tex) \
           $(wildcard scenari/*.tex)

# Estensioni dei file temporanei da rimuovere
CLEAN_EXT = aux log out toc lof lot fls fdb_latexmk synctex.gz bbl blg

# Regola predefinita
all: $(PDF)

# Compilazione del PDF
$(PDF): $(MAIN).tex $(INCLUDES)
	pdflatex $(MAIN).tex
	pdflatex $(MAIN).tex

# Pulizia dei file temporanei (anche nelle sottocartelle)
clean:
	@echo "Cleaning temporary LaTeX files..."
	@rm -f $(foreach ext,$(CLEAN_EXT),$(MAIN).$(ext))
	@find capitoli scenari -type f \( \
		-name "*.aux" -o -name "*.log" -o -name "*.out" -o -name "*.toc" -o \
		-name "*.lof" -o -name "*.lot" -o -name "*.fls" -o -name "*.fdb_latexmk" -o \
		-name "*.synctex.gz" -o -name "*.bbl" -o -name "*.blg" \) -exec rm -f {} +

# Pulizia completa
cleanall: clean
	@echo "Removing generated PDF..."
	@rm -f $(PDF)

# Regole fittizie
.PHONY: all clean cleanall