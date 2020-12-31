#!/usr/bin/env bash
SWD="$(dirname "$(readlink -fn "$0")")"

SOURCE_DIR="${SWD}/files"
TARGET_DIR="$(dirname "${SWD}")"

NAMESPACE='packed'

TYPES=('chest' 'barrel' 'crate')
WOODS=('acacia' 'birch' 'dark_oak' 'jungle' 'oak' 'spruce' 'crimson' 'warped')
TIERS=('default' 'tier1' 'tier2' 'tier3')

ensure_dir_exists() {
  [[ ! -d "$1" ]] && mkdir -p "$1"
}

ASSETS_DIR="${TARGET_DIR}/assets/${NAMESPACE}"
DATA_DIR="${TARGET_DIR}/data/${NAMESPACE}"

assets() {
  for STORAGE_TYPE in "${TYPES[@]}"; do
    for WOOD_TYPE in "${WOODS[@]}"; do

      if [[ "${WOOD_TYPE}" == "crimson" || "${WOOD_TYPE}" == "warped" ]]; then
        WOOD_LOG_TYPE="${WOOD_TYPE}_stem"
      else
        WOOD_LOG_TYPE="${WOOD_TYPE}_log"
      fi

      for TIER_TYPE in "${TIERS[@]}"; do
        NAME="${WOOD_TYPE}_${STORAGE_TYPE}_${TIER_TYPE}"

        local -a files
        num=-1
        files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/blockstate.json;${ASSETS_DIR}/blockstates/${NAME}.json"

        if [[ "${STORAGE_TYPE}" != "crate" ]]; then
          files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/models/block.json;${ASSETS_DIR}/models/block/${NAME}.json"
        else
          files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/models/block_${TIER_TYPE}.json;${ASSETS_DIR}/models/block/${NAME}.json"
        fi

        if [[ "${STORAGE_TYPE}" == "barrel" ]]; then
          files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/models/block_open.json;${ASSETS_DIR}/models/block/${NAME}_open.json"
        fi

        files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/models/item.json;${ASSETS_DIR}/models/item/${NAME}.json"

        files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/loot_tables/block.json;${DATA_DIR}/loot_tables/blocks/${NAME}.json"
        files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/advancements/${TIER_TYPE}.json;${DATA_DIR}/advancements/recipes/${NAME}.json"
        files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/recipes/${TIER_TYPE}.json;${DATA_DIR}/recipes/${NAME}.json"

        files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/advancements/convert.json;${DATA_DIR}/advancements/recipes/${NAME}_convert.json"
        files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/recipes/convert.json;${DATA_DIR}/recipes/${NAME}_convert.json"

        if [[ -n "${PREVIOUS_TIER_TYPE}" ]]; then
          files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/advancements/upgrade.json;${DATA_DIR}/advancements/recipes/${NAME}_upgrade.json"
          files[$((num += 1))]="${SOURCE_DIR}/${STORAGE_TYPE}/recipes/upgrade.json;${DATA_DIR}/recipes/${NAME}_upgrade.json"
        fi

        for file in "${files[@]}"; do
          IFS=";" read -ra file <<<"$file"
          SOURCE_FILE="${file[0]}"
          TARGET_FILE="${file[1]}"

          if [[ -f "${SOURCE_FILE}" ]]; then
            ensure_dir_exists "$(dirname "${TARGET_FILE}")"
            set -x
            echo "$TARGET_FILE"
            LAST_TIER="${PREVIOUS_TIER_TYPE}" TIER="${TIER_TYPE}" \
              TYPE="${STORAGE_TYPE}" \
              WOOD="${WOOD_TYPE}" WOOD_LOG="${WOOD_LOG_TYPE}" \
              envsubst <"${SOURCE_FILE}" >"${TARGET_FILE}"
            set +x
          fi

        done

        # reset files so it does not come to conflicts
        files=()

        PREVIOUS_TIER_TYPE="${TIER_TYPE}"
      done # with tier types
      PREVIOUS_TIER_TYPE=''
    done # with wood types
  done # with storage types
}

clean() {
  for STORAGE_TYPE in "${TYPES[@]}"; do
    for WOOD_TYPE in "${WOODS[@]}"; do
      for TIER_TYPE in "${TIERS[@]}"; do
        NAME="${WOOD_TYPE}_${STORAGE_TYPE}_${TIER_TYPE}"

        declare -a files
        files[0]="${ASSETS_DIR}/blockstates/${NAME}.json"
        files[1]="${ASSETS_DIR}/models/block/${NAME}.json"
        files[2]="${ASSETS_DIR}/models/item/${NAME}.json"
        files[3]="${ASSETS_DIR}/models/block/${NAME}_open.json"
        files[4]="${DATA_DIR}/advancements/recipes/${NAME}.json"
        files[5]="${DATA_DIR}/advancements/recipes/${NAME}_convert.json"
        files[6]="${DATA_DIR}/advancements/recipes/${NAME}_upgrade.json"
        files[7]="${DATA_DIR}/recipes/${NAME}.json"
        files[8]="${DATA_DIR}/recipes/${NAME}_convert.json"
        files[9]="${DATA_DIR}/recipes/${NAME}_upgrade.json"
        files[10]="${DATA_DIR}/loot_tables/blocks/${NAME}.json"

        for file in "${files[@]}"; do
          rm "$file" &>/dev/null
          folder="$(dirname "$file")"

          while rmdir "$folder" &>/dev/null && [[ "$folder" != '*resources' ]]; do
            echo "$folder"
            folder="$(dirname folder)"
          done

        done

      done
    done
  done
}

case "$1" in
clean)
  clean
  exit 0
  ;;
assets)
  assets
  ;;
*)
  printf "{clean|assets}\n"
  ;;
esac
