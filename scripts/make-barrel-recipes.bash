#!/bin/bash

if [[ ! -d './src' ]]; then
  echo "Please make sure the current working directory is the root of the project"
  exit 1
fi

PATH_BLOCK_STATES='./src/main/resources/data/packed/recipes'

variants=(
'acacia'
'birch'
'dark_oak'
'jungle'
'oak'
'spruce'
'crimson'
'warped')

tiers=(
'default'
'tier1'
'tier2'
'tier3')

for variant in "${variants[@]}"; do
  for tier in "${tiers[@]}"; do
    # block state default
    cp "$(dirname "$0")/templates/template_barrel_${tier}.json" "${PATH_BLOCK_STATES}/${variant}_barrel_${tier}.json"
    sed -i "s/TMP/${variant}/g" "${PATH_BLOCK_STATES}/${variant}_barrel_${tier}.json"
  done
done
