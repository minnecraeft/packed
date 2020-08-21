ASSETS  := "./src/main/resources/assets/packed"
DATA    := "./src/main/resources/data/packed"

default: barrels chests crates

help:
	@echo "This file generates a ton of json files for all kind of data driven stuff in minecraft"
	@echo "It can generate also only for one storage type: use [barrels | chests | crates] as parameter"

barrels:
	@echo "+---> BARRELS"
	@ASSETS=$(ASSETS) DATA=$(DATA) ./scripts/barrel.sh

chests:
	@echo "+---> CHESTS"
	@ASSETS=$(ASSETS) DATA=$(DATA) ./scripts/chest.sh

crates:
	@echo "+---> CRATES"
	@ASSETS=$(ASSETS) DATA=$(DATA) ./scripts/crate.sh

.PHONY: default barrels chests crates help