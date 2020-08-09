#!/bin/bash

if [[ ! -d './src' ]]; then
  echo "Please make sure the current working directory is the root of the project"
  exit 1
fi

PATH_BLOCK_STATES='./src/main/resources/assets/packed/blockstates'
PATH_MODELS_BLOCK='./src/main/resources/assets/packed/models/block'
PATH_MODELS_ITEM='./src/main/resources/assets/packed/models/item'

FROM_TIER='default'

variants=(
'acacia_barrel_'
'birch_barrel_'
'dark_oak_barrel_'
'jungle_barrel_'
'oak_barrel_'
'spruce_barrel_'
'crimson_barrel_'
'warped_barrel_')

tiers=(
'tier1'
'tier2'
'tier3')

for variant in "${variants[@]}"; do
  for tier in "${tiers[@]}"; do
    # block state default
    cp "${PATH_BLOCK_STATES}/${variant}${FROM_TIER}.json" "${PATH_BLOCK_STATES}/${variant}${tier}.json"
    sed -i "s/${FROM_TIER}/${tier}/g" "${PATH_BLOCK_STATES}/${variant}${tier}.json"
    # model
    cp "${PATH_MODELS_BLOCK}/${variant}${FROM_TIER}.json" "${PATH_MODELS_BLOCK}/${variant}${tier}.json"
    sed -i "s/${FROM_TIER}/${tier}/g" "${PATH_MODELS_BLOCK}/${variant}${tier}.json"
    # model open
    cp "${PATH_MODELS_BLOCK}/${variant}${FROM_TIER}_open.json" "${PATH_MODELS_BLOCK}/${variant}${tier}_open.json"
    sed -i "s/${FROM_TIER}/${tier}/g" "${PATH_MODELS_BLOCK}/${variant}${tier}_open.json"
    # model item
    cp "${PATH_MODELS_ITEM}/${variant}${FROM_TIER}.json" "${PATH_MODELS_ITEM}/${variant}${tier}.json"
    sed -i "s/${FROM_TIER}/${tier}/g" "${PATH_MODELS_ITEM}/${variant}${tier}.json"
  done
done
