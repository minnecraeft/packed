#!/bin/bash

BASE="$(dirname "$(readlink -fn "$0")")"

if [[ -z $ASSETS || -z $DATA ]]; then
  printf "The environment variable 'ASSETS' or 'DATA' is not set"
  exit 1
fi

BLOCK_STATES="${ASSETS}/blockstates"
MODELS_BLOCK="${ASSETS}/models/block"
MODELS_ITEM="${ASSETS}/models/item"

[[ ! -d "${BLOCK_STATES}" ]] && mkdir -p "${BLOCK_STATES}"
[[ ! -d "${MODELS_BLOCK}" ]] && mkdir -p "${MODELS_BLOCK}"
[[ ! -d "${MODELS_ITEM}" ]] && mkdir -p "${MODELS_ITEM}"

RECIPES="${DATA}/recipes"
RECIPE_ADVANCEMENTS="${DATA}/advancements/recipes"

[[ ! -d "${RECIPES}" ]] && mkdir -p "${RECIPES}"
[[ ! -d "${RECIPE_ADVANCEMENTS}" ]] && mkdir -p "${RECIPE_ADVANCEMENTS}"

STORAGE_TYPE='barrel'
VARIANTS=('acacia' 'birch' 'dark_oak' 'jungle' 'oak' 'spruce' 'crimson' 'warped')
TIERS=('default' 'tier1' 'tier2' 'tier3')

for VARIANT in "${VARIANTS[@]}"; do
  printf " +---> json for %s with variant '%-8s' is generating\n" "$STORAGE_TYPE" "$VARIANT"

  export VARIANT
  for TIER in "${TIERS[@]}"; do
    export TIER
    export PREVIOUS_TIER

    file="${VARIANT}_${STORAGE_TYPE}_${TIER}"

    envsubst <"${BASE}/presets/${STORAGE_TYPE}/blockstate.json" >"${BLOCK_STATES}/${file}.json"

    envsubst <"${BASE}/presets/${STORAGE_TYPE}/model_block.json" >"${MODELS_BLOCK}/${file}.json"
    envsubst <"${BASE}/presets/${STORAGE_TYPE}/model_block_open.json" >"${MODELS_BLOCK}/${file}_open.json"
    envsubst <"${BASE}/presets/${STORAGE_TYPE}/model_item.json" >"${MODELS_ITEM}/${file}.json"

    [[ ! -d "${RECIPES}/${VARIANT}" ]] && mkdir -p "${RECIPES}/${VARIANT}"
    envsubst <"${BASE}/presets/${STORAGE_TYPE}/recipe_${TIER}.json" >"${RECIPES}/${VARIANT}/${file}.json"

    [[ ! -d "${RECIPE_ADVANCEMENTS}/${VARIANT}" ]] && mkdir -p "${RECIPE_ADVANCEMENTS}/${VARIANT}"
    envsubst <"${BASE}/presets/${STORAGE_TYPE}/advancement_${TIER}.json" >"${RECIPE_ADVANCEMENTS}/${VARIANT}/${file}.json"

    PREVIOUS_TIER="${TIER}"
  done
done
