#!/bin/bash

BASE="$(dirname "$(readlink -fn "$0")")"

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

VARIANTS=('acacia' 'birch' 'dark_oak' 'jungle' 'oak' 'spruce' 'crimson' 'warped')
TIERS=('default' 'tier1' 'tier2' 'tier3')

for VARIANT in "${VARIANTS[@]}"; do
  export VARIANT
  export VARIANT_LOG
  if [[ "${VARIANT}" == "crimson" || "${VARIANT}" == "warped" ]]; then
    VARIANT_LOG="${VARIANT}_stem"
  else
    VARIANT_LOG="${VARIANT}_log"
  fi
  for TIER in "${TIERS[@]}"; do
    export TIER
    export PREVIOUS_TIER
    file="${VARIANT}_crate_${TIER}"
    envsubst <"${BASE}/presets/crate/blockstate.json" >"${BLOCK_STATES}/${file}.json"

    envsubst <"${BASE}/presets/crate/model_block_${TIER}.json" >"${MODELS_BLOCK}/${file}.json"
    envsubst <"${BASE}/presets/crate/model_item.json" >"${MODELS_ITEM}/${file}.json"

    [[ ! -d "${RECIPES}/${VARIANT}" ]] && mkdir -p "${RECIPES}/${VARIANT}"
    envsubst <"${BASE}/presets/crate/recipe_${TIER}.json" >"${RECIPES}/${VARIANT}/${file}.json"

    [[ ! -d "${RECIPE_ADVANCEMENTS}/${VARIANT}" ]] && mkdir -p "${RECIPE_ADVANCEMENTS}/${VARIANT}"
    envsubst < "${BASE}/presets/crate/advancement_${TIER}.json" > "${RECIPE_ADVANCEMENTS}/${VARIANT}/${file}.json"

    PREVIOUS_TIER="${TIER}"
  done
done
